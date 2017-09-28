package com.ldw.lib.biz.download2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LDW10000000 on 31/08/2017.
 *
 * 说明:
 *      1. 支持断点下载;
 *      2. 文件名为空就会按照最后一个斜杠的后面的 为文件名;
 *      3. 下载地址为空就默认下载到根目录;
 *      4. 提供的下载地址,最后要加入 斜杠, 或 File.separator  ;
 *      5. 使用方法:
 *       DownloadManager.INSTANCE.download(new DownloadRunnable(new DownloadRunnable.DownloadListener() {
 *          @Override
 *          public void onProgress(int progress) {}
 *
 *          @Override
 *          public void onSuccess() {}
 *
 *           @Override
 *          public void onFailed(@Nullable IOException e) {}
 *
 *          @Override
 *          public void onPaused() {}
 *
 *          @Override
 *          public void onCanceled() {}
 *
 *          @Nullable
 *          @Override
 *          public String downloadName() {}
 *
 *          @Nullable
 *          @Override
 *          public String downloadDirectory() {}
 *
 *          @NonNull
 *          @Override
 *          public String downloadUrl(){}}) {});
 *
 */
public enum DownloadManager {
    INSTANCE {

        private HashMap<String, DownloadRunnable> downCalls = new HashMap<>();//用来存放各个下载的请求
        private final ExecutorService executorService = Executors.newFixedThreadPool(2);

        /**
         * 开始下载
         */
        @Override
        public void download(DownloadRunnable runnable) {
            //// TODO: 31/08/2017  如果含有这个url ,就是正在下载,不用再次下载
            if(!downCalls.containsKey(runnable.listener.downloadUrl())){
                executorService.submit(runnable);
                downCalls.put(runnable.listener.downloadUrl(), runnable);//把这个添加,方便取消
            }
        }

        /**
         *  暂停下载(本地文件保留了,等待下一次继续下载);
         *
         * @param url
         * @return
         */
        @Override
        public DownloadRunnable pauseDownload(String url) {
            DownloadRunnable runnable = downCalls.get(url);
            if (runnable != null) {
                runnable.isPaused = true;
                if (runnable.call != null) {
                    runnable.call.cancel();//取消
                }
            }
            downCalls.remove(url);
            return runnable;
        }

        /**
         * 暂停下载 和 删除本地已下载文件;
         *
         * @param url
         */
        @Override
        public void cancelDownload(String url) {
            DownloadRunnable runnable = pauseDownload(url);
            if (runnable != null) {
                runnable.isCanceled = true;
                //取消下载时需要将文件删除，并将通知关闭
                String fileName = runnable.listener.downloadName();
                String directory = runnable.listener.downloadDirectory();
                if (fileName != null && directory != null) {
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }

        /**
         *  暂停所有下载
         *
         *  注意:暂未测试过,
         *
         */
        @Override
        public void pauseAll() {
            List<String> list = new ArrayList<>();

            Iterator iterator = downCalls.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object url = entry.getKey();
                if(url instanceof  String){
                    list.add((String)url);
                }
            }

            for(int i =0;i<list.size();i++){
                pauseDownload(list.get(i));
            }

        }
    };

    public abstract void download(DownloadRunnable runnable);
    public abstract DownloadRunnable pauseDownload(String url);
    public abstract void cancelDownload(String url);
    public abstract void pauseAll();



}
