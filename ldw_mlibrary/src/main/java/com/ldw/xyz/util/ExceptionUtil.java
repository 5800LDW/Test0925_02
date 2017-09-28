package com.ldw.xyz.util;

//import com.ldw.xyz.mail.util.EmailUtil;

import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.device.DeviceUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

import mail.util.EmailUtil;

/**
 * 异常统一处理
 *
 * @author tarena
 */
public class ExceptionUtil {
    public static void handleException(Exception e) {
        if (true) {
//		if (Controller.isRelease) {
            printAndSendEmail(e);
        }
        if (!Controller.isRelease) {
            if (e != null) {
                e.printStackTrace();
            } else {
                LogUtil.i("ExceptionUtil", "e = null");
            }
        }
    }

    public static void printAndSendEmail(Throwable e) {
        // 手机型号
        String DeviceModel = DeviceUtil.getDeviceModel() + "";
        // 系统版本
        String DeviceSystemVersion = DeviceUtil.getDeviceSystemVersion() + "";
        //把异常信息变成字符串
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String string = stringWriter.toString();
//		LogUtil.i("ExceptionUtil", string);
        //发邮件，发到服务器
        EmailUtil.sendMessage("" + e.getClass().getName(), "DeviceModel 手机型号 = " + DeviceModel + "\n" +
                "DeviceSystemVersion  系统版本号 = " + DeviceSystemVersion + "\n" +
                string);
    }


    public static void handleException(Exception e,String appName) {
        if (true) {
//		if (Controller.isRelease) {
            printAndSendEmail(e,appName);
        }
        if (!Controller.isRelease) {
            if (e != null) {
                e.printStackTrace();
            } else {
                LogUtil.i("ExceptionUtil", "e = null");
            }
        }
    }


    public static void printAndSendEmail(Throwable e,String appName) {

        // 手机型号
        String DeviceModel = DeviceUtil.getDeviceModel() + "";
        // 系统版本
        String DeviceSystemVersion = DeviceUtil.getDeviceSystemVersion() + "";
        //把异常信息变成字符串
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String string = stringWriter.toString();
//		LogUtil.i("ExceptionUtil", string);
        //发邮件，发到服务器
        EmailUtil.sendMessage("" + e.getClass().getName() + "   软件名称:"+appName, "DeviceModel 手机型号 = " + DeviceModel + "\n" +
                "DeviceSystemVersion  系统版本号 = " + DeviceSystemVersion + "\n" +
                string);
    }



}
