package com.ldw.xyz.util.install;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by LDW10000000 on 11/08/2017.
 */

public class MyInstallUtil {

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
    public static void installNormal(Context context, String apkPath, String authorities) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //版本在7.0以上是不能直接通过uri访问的
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            File file = (new File(apkPath));
//            // 由于没有在Activity环境下启动Activity,设置下面的标签
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//            Uri apkUri = FileProvider.getUriForFile(context,authorities, file);
//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//        } else {
//
//            // 由于没有在Activity环境下启动Activity,设置下面的标签
//            //LDW 这句话是必须的:
////            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
//        }
        Intent intent = getInstallNormalIntent(context,apkPath,authorities);
        context.startActivity(intent);
    }


    public static Intent getInstallNormalIntent(Context context, String apkPath, String authorities) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context,authorities, file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            //LDW 这句话是必须的:
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        }
        return intent;
    }





}
