package com.ldw.xyz.util.exit;

/**
 * Created by liudongwen on 23/02/2018.
 *
 * 说明: 列出退出程序的方法
 *
 */

public class ExitUtil {


    /**
     * 经测试,可用
     */
    public static void normalExit(){
        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }







}
