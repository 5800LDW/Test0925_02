package com.github.phoenix.base;

import android.content.Context;

import com.antonioleiva.mvpexample.app.MyApplication;


public class BaseApplication  {
//    public static Context context;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
////        context = getApplicationContext();
////
////        //初始化Toast
////        DisplayToast.getInstance().init(getApplicationContext());
//
//    }

    /**
     * 获取上下文
     * @return Context
     */
    public static Context getContext() {
        return MyApplication.applicationContext;
    }
}
