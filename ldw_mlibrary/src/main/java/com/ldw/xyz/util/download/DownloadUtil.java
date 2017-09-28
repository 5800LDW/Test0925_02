package com.ldw.xyz.util.download;

import com.ldw.xyz.util.ExceptionUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LDW10000000 on 11/08/2017.
 */

public class DownloadUtil {


    public interface downloadStateListener {
        /**
         * 更新进度
         *
         * @param currentProgress
         */
        void updateProgress(int currentProgress);

        /**
         * 下载完成后会调用
         */
        void downloadOver();

        /**
         * 取消下载
         * <p>
         * false 取消下载
         * <p>
         * true 继续下载
         *
         * @return
         */
        boolean interceptFlag();

    }


    /**
     * 通过 HttpURLConnection 下载文件
     *
     *   注意: 不支持断点下载, 每次下载会重新下载,直接覆盖之前下载好的;
     *
     * @param apkUrl               安装包url 例如:http://101.28.249.94/apk.r1.market.hiapk.com/data/upload/apkres/2017/4_11/15/com.baidu.searchbox_034250.apk
     * @param savePath             下载包安装路径 例如:/sdcard/updatedemo/
     * @param saveFileAbsolutePath 绝对路径 例如:/sdcard/updatedemo/UpdateDemoRelease.apk
     * @param listener
     */
    public static void downloadByHttpURLConnection(String apkUrl, String savePath, String saveFileAbsolutePath, downloadStateListener listener) {
        try {
            URL url = new URL(apkUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            int length = conn.getContentLength();
            InputStream is = conn.getInputStream();

            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdir();
            }
            String apkFile = saveFileAbsolutePath;
            File ApkFile = new File(apkFile);
            FileOutputStream fos = new FileOutputStream(ApkFile);

            int count = 0;
            byte buf[] = new byte[1024];

            do {
                int numread = is.read(buf);
                count += numread;
                int progress = (int) (((float) count / length) * 100);
//                //更新进度
//                mHandler.sendEmptyMessage(DOWN_UPDATE);
                listener.updateProgress(progress);

                if (numread <= 0) {
                    //下载完成通知安装
//                   mHandler.sendEmptyMessage(DOWN_OVER);
                    listener.downloadOver();

                    break;
                }

                fos.write(buf, 0, numread);
            } while (!listener.interceptFlag());//点击取消就停止下载.

            fos.close();
            is.close();
        } catch (MalformedURLException e) {
            ExceptionUtil.handleException(e);
        } catch (IOException e) {
            ExceptionUtil.handleException(e);
        }
    }

}
