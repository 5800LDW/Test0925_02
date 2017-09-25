package com.antonioleiva.mvpexample.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.preference.PreferenceManager;

import com.facebook.stetho.Stetho;
import com.github.phoenix.widget.DisplayToast;
import com.ldw.xyz.util.ExceptionUtil;

/**
 * Created by LDW10000000 on 31/07/2017.
 */

public class MyApplication extends Application implements
        Thread.UncaughtExceptionHandler{

    public static Context applicationContext;
    private static MyApplication instance;
    public static Context sContext;

    private static final String APK_ID = "apk_id";
    private String apk_id = null;


    private static Context mContext;
    private static Thread mMainThread;
    private static long mMainThreadId;
    private static Looper mMainLooper;
    private static Handler mHandler;

    public static Context getContext() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static Handler getHandler() {
        return mHandler;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        // 上下文
        mContext = getApplicationContext();

        // 主线程
        mMainThread = Thread.currentThread();

        // 主线程id
        mMainThreadId = Process.myTid();

        // tid thread
        // uid user
        // pid process

        mMainLooper = getMainLooper();

        mHandler = new Handler();



        // LitePal.initialize(this);
        Thread.setDefaultUncaughtExceptionHandler(this);

        applicationContext = this;
        instance = this;
        sContext = this;


        //初始化Toast
        DisplayToast.getInstance().init(getApplicationContext());

        Stetho.initializeWithDefaults(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        ExceptionUtil.printAndSendEmail(e);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 设置下载APK ID
     * @param id
     * @return
     */
    public void  setApkId(String id){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor editor=preferences.edit();
        if(editor.putString(APK_ID, id).commit()){
            apk_id=id;
        }
    }
    /**
     * 获取下载APK ID
     * @return
     */
    public String  getApkId(){
        apk_id = null;
        if(apk_id==null){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
            apk_id=preferences.getString(APK_ID, null);
        }
        return apk_id;
    }


}































