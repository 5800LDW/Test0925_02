package com.ldw.lib.biz.download2;


import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ldw.xyz.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class DownloadRunnable implements Runnable {
    /**
     * Created by Administrator on 2017/2/23.
     */
    public interface DownloadListener {
        /**
         * 通知当前的下载进度
         *
         * @param progress
         */
        void onProgress(int progress);

        /**
         * 通知下载成功
         */
        void onSuccess();

        /**
         * 通知下载失败
         *
         * @param e 可以为null
         */
        void onFailed(@Nullable IOException e);

        /**
         * 通知下载暂停
         */
        void onPaused();

        /**
         * 通知下载取消事件
         */
        void onCanceled();

        /**
         * 下载的文件的名称  含后缀
         */
        @Nullable
        String downloadName();

        /**
         * 下载的文件夹名称
         */
        @Nullable
        String downloadDirectory();

        @NonNull
        String downloadUrl();

    }

    public static final long DEFAULT_MILLISECONDS = 10;

    public static final int TYPE_SUCCESS = 0;

    public static final int TYPE_FAILED = 1;

    public static final int TYPE_PAUSED = 2;

    public static final int TYPE_CANCELED = 3;

    public boolean isCanceled = false;

    public boolean isPaused = false;

    public Call call;

    public DownloadListener listener;


    private int lastProgress;

    public DownloadRunnable(DownloadListener downloadListener) {
        listener = downloadListener;
    }

    protected void onProgressUpdate(Integer progress) {
        if (progress > lastProgress) {
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }


    protected void onPostExecute(Integer status, IOException... exceptions) {
        switch (status) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                LogUtil.i("TAG","listener.onSuccess()");
                break;
            case TYPE_FAILED:
                if (exceptions.length != 0) {
                    listener.onFailed(exceptions[0]);
                } else {
                    listener.onFailed(null);
                }
                LogUtil.i("TAG","listener.onFailed");
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                LogUtil.i("TAG","listener.onPaused()");
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                LogUtil.i("TAG","listener.onCanceled()");
                break;
            default:
                break;
        }


    }


    /**
     * 得到下载内容的大小
     *
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.body().close();
                return contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void run() {

        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file;
        long downloadLength = 0;   //记录已经下载的文件长度
        //文件下载地址
        String downloadUrl = listener.downloadUrl();
        //下载文件的名称
        String fileName = listener.downloadName();
        IOException exception = null;
        if (fileName == null) {
            fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        }
        //下载文件存放的目录
        String directory = listener.downloadDirectory();
        if (directory == null) {
            directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        }

        LogUtil.i("TAG", "下载地址:" + directory);

        makeRootDirectory(directory);


        //创建一个文件
        file = new File(directory + fileName);
        if (file.exists()) {
            //如果文件存在的话，得到文件的大小
            downloadLength = file.length();
        }
        //得到下载内容的大小
        long contentLength = getContentLength(downloadUrl);
        if (contentLength == 0) {
            onPostExecute(TYPE_FAILED);
            return;
        } else if (contentLength == downloadLength) {
            //已下载字节和文件总字节相等，说明已经下载完成了
            onPostExecute(TYPE_SUCCESS);
            return;
        }

//      OkHttpClient client = new OkHttpClient();

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(connectTimeout(DEFAULT_MILLISECONDS), TimeUnit.SECONDS).build();

        /**
         * HTTP请求是有一个Header的，里面有个Range属性是定义下载区域的，它接收的值是一个区间范围，
         * 比如：Range:bytes=0-10000。这样我们就可以按照一定的规则，将一个大文件拆分为若干很小的部分，
         * 然后分批次的下载，每个小块下载完成之后，再合并到文件中；这样即使下载中断了，重新下载时，
         * 也可以通过文件的字节长度来判断下载的起始点，然后重启断点续传的过程，直到最后完成下载过程。
         */
        Request request = new Request.Builder()
                .addHeader("RANGE", "bytes=" + downloadLength + "-")  //断点续传要用到的，指示下载的区间
                .url(downloadUrl)
                .build();


        try {
            call = client.newCall(request);

            Response response = call.execute();
            if (response != null) {
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadLength);//跳过已经下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {
                    if (isCanceled) {
                        onPostExecute(TYPE_CANCELED);
                        return;
                    } else if (isPaused) {
                        onPostExecute(TYPE_PAUSED);
                        return;
                    } else {
                        total += len;
                        savedFile.write(b, 0, len);
                        //计算已经下载的百分比
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        //注意：在doInBackground()中是不可以进行UI操作的，如果需要更新UI,比如说反馈当前任务的执行进度，
                        onProgressUpdate(progress);
                    }

                }
                response.body().close();
                onPostExecute(TYPE_SUCCESS);
                return;
            }
        } catch (IOException e) {
            exception = e;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        onPostExecute(TYPE_FAILED, exception);
        return;
    }

    /**
     * 生成文件夹
     */
    public static void makeRootDirectory(String filePath) {
        File file;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    public long connectTimeout(long timeout) {
        return timeout;
    }

}