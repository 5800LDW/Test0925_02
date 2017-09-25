package com.tutor.update2;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.antonioleiva.mvpexample.app.MyApplication;
import com.antonioleiva.mvpexample.app.R;

/**
 * @author czy
 * @time 2015-10-21 10:37:42
 * @path activity.update.com.MainActivity;
 * @name 主页面
 * <p>
 * 之前一直都是自己写的自动更新下载，这次试了一下系统提供的DownloadManager下载类，发现非常方便也非常简单。
 * 就整理了一下分享给大家
 * <p>
 * 需要注意的几点
 * 1、添加权限
 * <uses-permission android:name="android.permission.INTERNET" />
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * 2、注册广播  也可以动态注册，但是这样一旦应用关闭，广播就无法接收到
 * <receiver android:name=".CompleteReceiver">
 * <intent-filter>
 * <action android:name="android.intent.action.DOWNLOAD_COMPLETE"/>
 * <action android:name="android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"/>
 * </intent-filter>
 * </receiver>
 */
public class UpdateActivity2 extends AppCompatActivity {

    private Button btn_update;

    //测试APK地址为QQ官方网站的下载地址，如果不可用请替换为自己应用的下载地址
//    private String path = "http://119.90.16.195:9999/123.125.110.14/sqdd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk?mkey=5626de6e8ed9249f&f=d688&p=.apk";

    private String path = "http://softfile.3g.qq.com:8080/msoft/179/24659/43549/qq_hd_mini_1.4.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update2);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerCode(UpdateActivity2.this);//TODO 获取版本号 用于对比服务器版本
                getVerName(UpdateActivity2.this);//TODO 获取版本名字 用于对比服务器版本
                //在此之前需要访问服务器判断服务器版本号，在这里就不写了
                showUpdateDialog(path);//TODO 弹出自动更新dialog
            }
        });
    }

    /**
     * 版本更新Dialog
     */
    private void showUpdateDialog(final String downPath) {
        new AlertDialog.Builder(this).setTitle("提示").setMessage("发现新版本，是否更新？")
                .setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        //使用系统下载类
                        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(downPath);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        // 设置自定义下载路径和文件名
//                                    String apkName =  "yourName" + DateUtils.getCurrentMillis() + ".apk";
//                                    request.setDestinationInExternalPublicDir(yourPath, apkName);
//                                    MyApplication.getInstance().setApkName(apkName);
                        //设置允许使用的网络类型，这里是移动网络和wifi都可以
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

                        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
                        //request.setShowRunningNotification(false);

                        //不显示下载界面
                        request.setVisibleInDownloadsUi(false);
                        // 设置为可被媒体扫描器找到
                        request.allowScanningByMediaScanner();
                        // 设置为可见和可管理
                        request.setVisibleInDownloadsUi(true);
                        request.setMimeType("application/cn.trinea.download.file");
        /*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件
        在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个  目录下面*/
                        //request.setDestinationInExternalFilesDir(this, null, "tar.apk");
                        long id = downloadManager.enqueue(request);//TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
                        MyApplication.getInstance().setApkId(Long.toString(id));//TODO 把id存储在Preferences里面
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }

                }).show();
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verName;
    }

}