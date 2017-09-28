package com.ldw.lib.biz.download1;//package com.ldw.xyz.download;
//

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ldw.lib.biz.download1.service.DownloadService;
import com.ldw.xyz.activity.BaseActivity;

/**
 * Created by LDW10000000 on 14/08/2017.
 *
 * 说明:
 *      1.调用系统的DownloadManager进行下载;
 *      2.先继承这个activity;
 *      3.然后调用startDownload 方法进行下载;
 *
 *
 */

public abstract class UseDownLoadManagerActivity extends BaseActivity {

    private DownloadService.DownloadBinder mDownloadBinder;
    long downloadId;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDownloadBinder = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);//绑定服务
    }

    /**
     *  调用这个方法就可以进行下载
     *
     * @param isRootMode 是不是启用root模式安装, 如果root模式安装失败,默认启用普通安装
     * @param APK_URL
     * @param apkName  你可以任意设置下载的apk的名字;
     * @param authorities     1. 默认实现了authorities了, xml有了file_paths,在清单配置文件里面默认是"com.ldw.lib.biz.app.fileprovider";
     *                        2. null 或 空, 默认是给你设置authorities 是 "com.ldw.lib.biz.app.fileprovider"
     */
    public void startDownload(boolean isRootMode,String APK_URL,String apkName,@Nullable String authorities){
        if(authorities == null || TextUtils.isEmpty(authorities)){
            authorities =  "com.ldw.lib.biz.app.fileprovider";
        }

        //设置不是root模式
        mDownloadBinder.setInstallMode(isRootMode);
        //开始下载
         downloadId = mDownloadBinder.startDownload(APK_URL,apkName+".apk",authorities);
    }

    public int getProgress(){
        return  mDownloadBinder.getProgress(downloadId);
    }

}
