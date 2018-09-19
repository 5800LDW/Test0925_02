package com.ldw.xyz.util.exception;

/**
 * Created by liudongwen on 06/02/2018.
 */


import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.compress.Zipper;
import com.ldw.xyz.util.device.DeviceUtil;
import com.ldw.xyz.util.exit.ExitUtil;
import com.ldw.xyz.util.file.MyFileUtil;
import com.ldw.xyz.util.version.VersionUtil;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import mail.util.EmailUtil;
import mail.util.EmailUtil.OnSendEmailListener;

/**
 * 异常统一处理
 *
 * @author ldw
 */
public class ExceptionUtil {

    public static Context context;

    public final static String crashAbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash.zip";

    public static void handleException(Throwable e) {


        printAndSendEmail(e);

        if (!Controller.isRelease) {
            if (e != null) {
                e.printStackTrace();
            } else {
                LogUtil.i("ExceptionUtil", "e = null");
            }
        }
    }


    public static void handleExceptionAndExit(Throwable e){
        if(e!=null){
            e.printStackTrace();
        }

        ExceptionUtil.recordAndSendEmailWithAdjunct(context,e);
    }





    /**
     * 发送邮件,不带附件,直接发送
     *
     * @param e
     */
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


    /**
     * 带附件,发送邮件
     * <p>
     *     你要先自己手动记录错误信息
     *      例如:
     *          @Override
     *          public void uncaughtException(Thread thread, Throwable throwable) {
     *                  RecordCrash2SDUtil.record(this,throwable);
     *          }
     *
     *     你要先要自己处理好要发送的文件(这个文件要不要压缩, 发送之前要不要先判断存不存在, 存在的话要不要先进行删除)
     *
     * @param e
     * @param file 这个文件的绝对路径
     */
    public static void sendWithAdjunct(Throwable e, File file) {
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
        EmailUtil.sendMessageWithAttachments("" + e.getClass().getName(),
                "DeviceModel 手机型号 = " + DeviceModel + "\n" + "DeviceSystemVersion  系统版本号 = " + DeviceSystemVersion + "\n" + string,
                file,
                new OnSendEmailListener() {
                    @Override
                    public void onSucceed() {}
                    @Override
                    public void onFailed() {}
                });
    }


    /**
     * 记录到本地保存,带附件,发送邮件;
     * 1. 有网络就记录本地然后发送;
     * 2. 没网络就记录本地;
     * 3. 有网络的时候会把10天内的记录发送;
     *
     *
     * 使用方法, 一话搞定: ExceptionUtil.recordAndSendEmailWithAdjunct(this,throwable);
     *
     * 错误信息是记录在RecordCrash2SDUtil.getGlobalpath() 这个路径下
     *
     *
     * @param context
     * @param e
     * @param path 要保存的文件的路径例如:Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash.zip"
     * @param listener 发送成功或发送失败会回调
     *
     *                 参考写法,例子如下:
     *                     @Override
     *                     public void uncaughtException(Thread thread, Throwable throwable) {
     *                          final String TAG = "TAG";
     *                          //使用Toast来显示异常信息
     *                          new Thread() {
     *                          @Override
     *                          public void run() {
     *                              Looper.prepare();
     *                              DialogUtil.appCrashDialog(context);
     *                              Looper.loop();
     *                              }
     *                          }.start();
     *
     *                      ExceptionUtil.recordAndSendEmailWithAdjunct(context, throwable, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash.zip",
     *                          new EmailUtil.OnSendEmailListener() {
     *                          @Override
     *                          public void onSucceed() {
     *
     *                          //删除log信息
     *                          MyFileUtil.deleteDir(RecordCrash2SDUtil.getGlobalpath());
     *                          Log.e("TAG", "==发送成功 下面进行自动退出==");
     *                          //删除压缩文件
     *                          new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash.zip").delete();
     *                          //强制退出程序
     *                          ExitUtil.normalExit();
     *                       }
     *
     *                      @Override
     *                      public void onFailed() {
     *                          try {
     *                              Thread.sleep(1000);
     *                          //  mDefaultHandler.uncaughtException(thread, ex);
     *                          } catch (InterruptedException e) {
     *                              Log.e(TAG, "error : ", e);
     *                          }
     *                          Log.e("TAG", "==下面进行自动退出==");
     *                          //删除压缩文件
     *                          new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash.zip").delete();
     *                          //强制退出程序
     *                          ExitUtil.normalExit();
     *                          }
     *                      });
     *                  }
     *
     *
     *
     *
     *
     *
     *
     */
    public static void recordAndSendEmailWithAdjunct(Context context, Throwable e,String path,OnSendEmailListener listener) {

        //记录错误信息
        RecordCrash2SDUtil.record(context, e);
        //365天前的信息要删除
        RecordCrash2SDUtil.autoClear(365);


        //下面进行压缩
        if (MyFileUtil.isFileExists(path)) {
            Log.e("TAG", "存在,要删除");
            new File(path).delete();
        } else {
            Log.e("TAG", "不存在,要生成");
        }
        try {
            Zipper.zip(crashAbsolutePath,
                    RecordCrash2SDUtil.getGlobalpath());
        } catch (final Exception exception) {
            Log.e("TAG", "出错了   " + exception.toString());
        }


        // 手机型号
        String DeviceModel = DeviceUtil.getDeviceModel() + "";
        // 系统版本
        String DeviceSystemVersion = DeviceUtil.getDeviceSystemVersion() + "";
        //把异常信息变成字符串
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String string = stringWriter.toString();

        //begin 20180919 新增
        String appInfo = getAppInfo(context);
        //end




        //发邮件，发到服务器
        EmailUtil.sendMessageWithAttachments("" + e.getClass().getName(),
                "DeviceModel 手机型号 = " + DeviceModel + "\n" + "DeviceSystemVersion  系统版本号 = " + DeviceSystemVersion + "\n" +
                        appInfo + string,
                new File((path)),listener);

    }

    /**
     * 记录到本地保存,带附件,发送邮件,并且有默认的提示;
     *
     * @param context
     * @param e
     */
    public static void recordAndSendEmailWithAdjunct(Context context, Throwable e) {
        //压缩文件的绝对路径
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash.zip";

        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "很抱歉,程序出现异常,正在收集错误信息,即将自动退出.", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

//        //下面的没用, 经测试, 不知道为什么;
//        DialogUtil.appCrashDialog(context);//来显示信息, 这工具类来自 compile project(':ldw_mstylelib')


        recordAndSendEmailWithAdjunct(context,
                e,
                path,
                new EmailUtil.OnSendEmailListener() {
                    @Override
                    public void onSucceed() {
                        //进行删除
//                        MyFileUtil.deleteDir(RecordCrash2SDUtil.getGlobalpath());
                        Log.e("TAG", "==发送成功 下面进行自动退出==");
                        //删除压缩文件
                        new File(path).delete();
                        //强制退出程序//todo
                        ExitUtil.normalExit();
                    }

                    @Override
                    public void onFailed() {
                        try {
                            Thread.sleep(1000);
                            //  mDefaultHandler.uncaughtException(thread, ex);
                        } catch (InterruptedException e) {
                            Log.e("TAG", "error : ", e);
                        }
                        Log.e("TAG", "==下面进行自动退出==");
                        //删除压缩文件
                        new File(path).delete();
                        //强制退出程序//todo
                        ExitUtil.normalExit();
                    }
                });



    }



    public static void handleException(Exception e, String appName) {
        if (true) {
//		if (Controller.isRelease) {
            printAndSendEmail(e, appName);
        }
        if (!Controller.isRelease) {
            if (e != null) {
                e.printStackTrace();
            } else {
                LogUtil.i("ExceptionUtil", "e = null");
            }
        }
    }


    public static void printAndSendEmail(Throwable e, String appName) {

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
        EmailUtil.sendMessage("" +
                e.getClass().getName() +
                "   软件名称:" + appName,
                "DeviceModel 手机型号 = " + DeviceModel + "\n" +
                "DeviceSystemVersion  系统版本号 = " + DeviceSystemVersion + "\n" +
                string

        );
    }


    public static String getAppInfo(Context context){
        StringBuffer sb = new StringBuffer();

        sb.append("PackageName:");
        sb.append(VersionUtil.getPackageName(context));
        sb.append("\n");

        sb.append("VersionCode:");
        sb.append(VersionUtil.getVersionCode(context));
        sb.append("\n");

        sb.append("VersionName:");
        sb.append(VersionUtil.getVersionName(context));
        sb.append("\n");

        return sb.toString();

    }


}





















