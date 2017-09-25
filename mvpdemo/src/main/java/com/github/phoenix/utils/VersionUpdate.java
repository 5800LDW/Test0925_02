package com.github.phoenix.utils;


import com.github.phoenix.helper.presenter.VersionUpdateImpl;

public class VersionUpdate {

    /**
     * 请求服务器，检查版本是否可以更新
     *
     * @param versionUpdate
     */
     public static void checkVersion(final VersionUpdateImpl versionUpdate) {
         //从网络请求获取到的APK下载路径，此处是随便找的链接
//         versionUpdate.bindService("http://www.zhaoshangdai.com/file/android.apk");//http://101.28.249.94/apk.r1.market.hiapk.com/data/upload/apkres/2017/4_11/15/com.baidu.searchbox_034250.apk
         versionUpdate.bindService("http://softfile.3g.qq.com:8080/msoft/179/24659/43549/qq_hd_mini_1.4.apk");//
     }
}
