package com.ldw.xyz.util;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *  请使用MyFileUtil
 */
@Deprecated
public class SdcardTools {
	/**
	 * 从sdcard上读一个文件
	 * @param filePath
	 * @return
	 */
	public static byte[] readFileFromSdcard
	(String filePath) {
		byte[] data = null;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(filePath);
			int size = fileInputStream.available();
			data = new byte[size];
			fileInputStream.read(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return data;
	}
	/**
	 * 向sdcard写数据
	 * 
	 * @param context
	 * @param path
	 * @param fileName
	 * @param data
	 */
	public static void writeToSdcard
	(Context context, 
			String path, 
			String fileName,
			byte[] data) {
		FileOutputStream fileOutputStream = null;
		try {
			// 判断sdcard的状态
			String sdcardState = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				// MOUNTED有sdcard

				// 判断path有没有
				File filePath = new File(path);
				if (!filePath.exists()) {
					filePath.mkdirs();
				}

				// 判断file有没有

				File file = new File(path, fileName);
				if (file.exists()) {
					file.delete();
				}

				// 写数据
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(data);
				fileOutputStream.flush();
			}
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		} finally {
			try {
				if (fileOutputStream != null)
					fileOutputStream.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

	public static String getCurrentVersion(Context context)
			throws NameNotFoundException {
		PackageManager packageManager = context.getPackageManager();
		String packageName = context.getPackageName();
		PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
		return packageInfo.versionName;

	}

	public static void showInfo(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		// Toast toast=new Toast(context);
		// toast.setView(view);
	}

	private static ProgressDialog progressDialog;

	/**
	 * 显示进度条
	 * 
	 * @param activity
	 */
	public static void showProgressDialog(Activity activity, String msg) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMessage(msg);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
		}
	}

	public static void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.cancel();
			progressDialog = null;
			System.gc();
		}
	}

}
