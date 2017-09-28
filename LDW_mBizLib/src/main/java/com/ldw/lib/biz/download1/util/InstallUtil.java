package com.ldw.lib.biz.download1.util;//package com.ldw.xyz.download.util;

import android.content.Context;

import com.ldw.xyz.util.install.MyInstallUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * If there is no bug, then it is created by ChenFengYao on 2017/4/19,
 * otherwise, I do not know who create it either.
 */
public class InstallUtil {
    /**
     *
     * @param context
     * @param apkPath 要安装的APK
     * @param rootMode 是否是Root模式
     */
    public static void install(Context context, String apkPath,boolean rootMode,String authorities){
        if (rootMode){
            installRoot(context,apkPath,authorities);
        }else {
            installNormal(context,apkPath,authorities);
        }
    }

    /**
     * 通过非Root模式安装
     * @param context
     * @param apkPath
     */
    public static void install(Context context,String apkPath,String authorities){
        install(context,apkPath,false,authorities);
    }

    /**
     * 普通安装
     *     <provider
     *          android:name="android.support.v4.content.FileProvider"
     *          android:authorities="com.antonioleiva.mvpexample.app.fileprovider"
     *          android:exported="false"
     *          android:grantUriPermissions="true">
     *          <meta-data
     *              android:name="android.support.FILE_PROVIDER_PATHS"
     *              android:resource="@xml/file_paths" />
     *      </provider>
     * @param context
     * @param apkPath
     */
    private static void installNormal(Context context,String apkPath,String authorities) {
        MyInstallUtil.installNormal(context,apkPath,authorities);
    }

    //通过Root方式安装
    private static void installRoot(Context context, String apkPath,String authorities) {
        Observable.just(apkPath)
                .map(mApkPath -> "pm install -r " + mApkPath)
                .map(SystemManager::RootCommand)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer == 0) {
//                        Toast.makeText(context, "安装成功", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(context, "root权限获取失败,尝试普通安装", Toast.LENGTH_SHORT).show();
                        install(context,apkPath,authorities);
                    }
                });
    }



}
