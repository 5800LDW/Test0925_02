package com.ldw.xyz.util.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by LDW10000000 on 16/08/2017.
 */

public class AppUtil {
    /**
     * 获取当前应用版本号
     * @param context
     * @return version
     * @throws Exception
     */
    public static String getAppVersion(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        String versionName = packInfo.versionName;
        return versionName;
    }
}
