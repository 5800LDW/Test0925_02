package com.ldw.xyz.util.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.ldw.xyz.util.file.MyFileUtil;
import com.ldw.xyz.util.time.DateTimeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 说明: 经过测试,没有问题;
 *
 *   1).record(Context context, Throwable ex); 直接在uncaughtException里面调用, 记录设备信息和报错信息;
 *      如果在MyCrash里面有当天的log,最会在整个log追加内容,不会覆盖;
 *   2).autoClear(final int autoClearDay); 清除多少天前的log信息;默认填正数(填负数也行)
 *   3).setCrashFolderName(String name); 设置保存crash记录的文件夹的名字, 路径还是在根目录下;
 *
 *
 * Created by liudongwen on 05/02/2018.
 */

public final class RecordCrash2SDUtil {

    public static String TAG = "RecordCrash2SDUtil";

    // 用来存储设备信息和异常信息
    private static Map<String, String> infos = new HashMap<String, String>();

    /**
     * 分隔符.
     */
    private final static String FILE_EXTENSION_SEPARATOR = ".";

    // 用于格式化日期,作为日志文件名的一部分
    private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static String crashFolderName = "MyCrash";

    /***设置保存crash记录的文件夹的名字*/
    public static void setCrashFolderName(String name) {
        crashFolderName = name;
    }

    /**
     * 主要方法, 执行记录
     *
     * @param context
     * @param ex
     */
    public static void record(Context context, Throwable ex) {
        try {
            // 收集设备参数信息
            collectDeviceInfo(context);
            // 保存日志文件
            saveCrashInfoFile(ex);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件删除
     * @param autoClearDay 文件保存天数
     */
    public static void autoClear(final int autoClearDay) {
        MyFileUtil.delete(getGlobalpath(), new FilenameFilter() {
            @Override
            public boolean accept(File file, String filename) {
                String s = MyFileUtil.getFileNameWithoutExtension(filename);
                int day = autoClearDay < 0 ? autoClearDay : -1 * autoClearDay;
                String date = "crash-" + DateTimeUtil.getDateStrBeforeOrAfterNum(day, DateTimeUtil.DEFAULT_FORMAT_DATE);
                return date.compareTo(s) >= 0;
            }
        });
    }


    public static String getGlobalpath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + crashFolderName
                + File.separator;
    }














    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    private static void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName + "";
                String versionCode = pi.versionCode + "";
                String packageName = pi.packageName + "";

                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
                infos.put("packageName", packageName);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     * @throws Exception
     */
    private static String saveCrashInfoFile(Throwable ex) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sDateFormat.format(new java.util.Date());
            sb.append("\r\n" + date + "\n");
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\n");
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            String fileName = writeFile(sb.toString());
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
            sb.append("an error occured while writing file...\r\n");
            writeFile(sb.toString());
        }
        return null;
    }


    /**
     * 判断SD卡是否可用
     *
     * @return SD卡可用返回true
     */
    private static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }

    private static String writeFile(String sb) throws Exception {
        String time = formatter.format(new Date());
        String fileName = "crash-" + time + ".log";
        if (hasSdcard()) {
            String path = getGlobalpath();
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            FileOutputStream fos = new FileOutputStream(path + fileName, true);
            fos.write(sb.getBytes());
            fos.flush();
            fos.close();
        }
        return fileName;
    }

}
