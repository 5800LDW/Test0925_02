package com.ldw.xyz;

import android.app.Application;

import com.ldw.xyz.util.ExceptionUtil;

/**
 * Created by LDW10000000 on 09/08/2017.
 */

public class LDWApplication extends Application implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        ExceptionUtil.printAndSendEmail(e);
    }
}
