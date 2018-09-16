package com.ldw.xyz.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public class CommonUtils {

	public static final double PHOTO_SIZE_X = 0.4;
	public static final double PHOTO_SIZE_Y = 2;

	public static boolean IS_CHATTING = false;

	public static final int SP_MOBILE = 0;

	public static final int SP_TELECOM = 2;

	public static final int SP_UNICOM = 1;

	public static final int SP_UNKNOWN = -1;

	public static final String DOWNLOAD_PATH = getSdCardDir()
			+ "/InnShareZone/";

	public static final boolean IF_MATERIAL = true;

	public static boolean IF_CHECK_VERSION = true;
	public static boolean isRestart = true;
	/**
	 * 特殊字符
	 */
	public static final String SPEC_STRING_REGEX = "[`*~*!*@*#*$*%*\\^*&*\\**(*)*+*=*|*{*}*'*:*;*'*,*//[*//]*.*<*>*/*?*~*！*@*#*￥*%*…*&*（*）*—*+*|*{*}*【*】*‘*；*：*”*“*’*。*，*、*？*]";

	public static int screenWidth = 0;
	public static int screenHeight = 0;

	/**
	 * 获得本机IMEI
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getImei(Context ctx) {
		TelephonyManager telephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		if (null == imei) {
			return "";
		}
		return imei;
	}

//	/**
//	 * 获取设备IMEI
//	 *
//	 * @param ctx
//	 * @back
//	 */
//	public static String getIMEI(Context ctx) {
//		TelephonyManager localTelephonyManager = (TelephonyManager) ctx
//				.getSystemService("phone");
//		String str = "";
//		if (localTelephonyManager != null)
//			str = localTelephonyManager.getDeviceId();
//		back str;
//	}

	/**
	 * 获得本机IMSI
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getImsi(Context ctx) {
		TelephonyManager telephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telephonyManager.getSubscriberId();
		return imsi;
	}

	/**
	 * 获取运营商信息 SP_MOBILE 中国移动 SP_UNICOM 中国联通 SP_TELECOM 中国电信 SP_UNKNOWN 未知运营商
	 * 
	 * @param ctx
	 * @return
	 */
	public static int getMobileOperator(Context ctx) {
		TelephonyManager telephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);

		if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
			String flag = telephonyManager.getNetworkOperator();
			if (flag.equals("46000") || flag.equals("46002")) {
				return SP_MOBILE;
			} else if (flag.equals("46001")) {
				return SP_UNICOM;
			} else if (flag.equals("46003")) {
				return SP_TELECOM;
			}
		}

		return SP_UNKNOWN;
	}

	/**
	 * 获取设备型号
	 * 
	 * @return
	 */
	public static String getModel() {
		return Build.MODEL;
	}

	/**
	 * 获取系统版本
	 * 
	 * @return
	 */
	public static String getOsVersion() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 获取本机号码
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getPhoneNumber(Context ctx) {
		TelephonyManager telephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String number = telephonyManager.getLine1Number();

		return number;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param ctx
	 * @return
	 */
	public static int getScreenHeight(Context ctx) {
		WindowManager windowManager = (WindowManager) ctx
				.getSystemService(Context.WINDOW_SERVICE);
		screenHeight = windowManager.getDefaultDisplay().getHeight();
		return screenHeight;
	}

//	/**
//	 * 获取屏幕宽度
//	 *
//	 * @param ctx
//	 * @back
//	 */
//	public static int getScreenWidth(Context ctx) {
//		if (null != ctx) {
//			WindowManager windowManager = (WindowManager) ctx
//					.getSystemService("window");
//			screenWidth = windowManager.getDefaultDisplay().getWidth();
//		}
//		back screenWidth;
//	}

	/**
	 * 获取应用版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppVersion(Context context) {
		String result = "";
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			if (packageInfo != null) {
				result = result + packageInfo.versionCode;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取设备id
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceToken(Context context) {

		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String androidID = Settings.Secure.getString(
				context.getContentResolver(), Settings.Secure.ANDROID_ID);
		String deviceId = telephonyManager.getDeviceId();
		UUID uuid = null;
		SharedPreferences preferences = context.getSharedPreferences(
				"deviceToken", Context.MODE_WORLD_READABLE);
		String deviceToken = preferences.getString("deviceToken", "");
		if (!"".equals(deviceToken)) {
			return deviceToken;
		}
		try {
			if (androidID.equals("9774d56d682e549c") == false) {
				uuid = UUID.nameUUIDFromBytes(androidID.getBytes("UTF-8"));
			} else {
				if (deviceId != null
						&& deviceId.equals("000000000000000") == false) {
					uuid = UUID.nameUUIDFromBytes(deviceId.getBytes("UTF-8"));
				} else {
					uuid = UUID.randomUUID();
				}
			}
		} catch (UnsupportedEncodingException e) {
			uuid = UUID.randomUUID();
		}
		deviceToken = uuid.toString().replace("-", "");
		preferences.edit().putString("deviceToken", deviceToken).commit();

		return deviceToken;
	}

	/**
	 * 通过反射机制获取字段
	 * 
	 * @param <T>
	 * @param <T>
	 * @param c
	 */
	public static <T> String getClassFields(Class<T> c) {

		StringBuilder builder = new StringBuilder();

		Field[] fieldArray = c.getDeclaredFields();
		if (null != fieldArray) {
			for (Field f : fieldArray) {
				builder.append(f.getName());
				builder.append(",");
			}
		} else {
			builder.append(",");
		}

		String fieldsStr = builder.toString();
		fieldsStr = fieldsStr.substring(0, fieldsStr.length() - 1);

		return fieldsStr;
	}

	public static String getSign(Map<String, String> params, String secretKey,
			String charset) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte[] array = null;
		try {
			for (String key : params.keySet()) {
				byteStream.write(params.get(key).getBytes(charset));
			}
			byteStream.write(secretKey.getBytes(charset));
			array = byteStream.toByteArray();
			byteStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return MD5.makeMd5Sum(array);
	}

	public static String getChannelId(Context context) {
		String channel = "";
		try {
			InputStream input = context.getAssets().open("config.ini");
			Properties config = new Properties();
			config.load(input);
			channel = config.getProperty("CHANNEL", "-1");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// 无需处理
		}
		return channel;
	}

	/**
	 * SIM卡判断
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isCanUseSim(Context context) {
		TelephonyManager mgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
	}

	/***
	 * 
	 * 判断是否为null
	 * **/
	public static boolean notNull(Object obj) {
		if (null != obj && obj != "") {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否有SD卡
	 * 
	 * @return
	 */
	public static boolean isSDCardAvaiable() {

		String status = Environment.getExternalStorageState();

		return Environment.MEDIA_MOUNTED.equals(status);

	}

	/**
	 * sd卡地址
	 * 
	 * @return
	 */
	public static String getSdCardDir() {
		File sdCardDir = Environment.getExternalStorageDirectory();
		return sdCardDir.getPath();
	}

	/**
	 * 
	 * 获取文件保存目录
	 * 
	 * */
	public static String getFileDir(Context context) {
		String path = "";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
			path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ File.separator + "InnShareZone";
		} else {// 如果SD卡不存在，就保存到本应用的目录下
			path = context.getFilesDir().getAbsolutePath() + File.separator
					+ "InnShareZone";
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	
	/**
	 * 
	 * 获取文件保存目录
	 * 
	 * */
	public static String getFileDir(Context context, String projectName) {
		String path = "";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
			path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ File.separator + projectName;
		} else {// 如果SD卡不存在，就保存到本应用的目录下
			path = context.getFilesDir().getAbsolutePath() + File.separator
					+ projectName;
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}

	public static String getConfigFilePath(Context context) {
		String filePath = getFileDir(context) + "/config.ini";
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}

	/**
	 * 
	 * 获取aquery文件保存目录
	 * 
	 * */
	public static String getAqueryDir(Context context) {
		String path = getFileDir(context) + File.separator + "aquery";
		File file = new File(path);
		if (file.exists()) {
			file.mkdirs();
		}
		return path;
	}

	/**
	 * 根据activity的上下文执行activity的方法
	 * 
	 * @param context
	 * @param methodName
	 * @param parametarTypes
	 * @param objs
	 */
	public static void invokeActivityMethod(Context context, String methodName,
			Class<?>[] parametarTypes, Object... objs) {
		try {
			context.getClass().getMethod(methodName, parametarTypes)
					.invoke(context, objs);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据fragment的实例fragment的方法
	 * 
	 * @param //context
	 * @param methodName
	 * @param parametarTypes
	 * @param objs
	 */
	public static void invokeActivityMethod(Fragment fragment,
			String methodName, Class<?>[] parametarTypes, Object... objs) {
		try {
			fragment.getClass().getMethod(methodName, parametarTypes)
					.invoke(fragment, objs);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
//	/**
//	 * 获取meta-data值
//	 * @param context
//	 * @param name
//	 * @back
//	 */
//	public static String getMetaDataValue(Context context, String name) {
//	    Object value = null;
//	    PackageManager packageManager = context.getPackageManager();
//	    ApplicationInfo applicationInfo;
//	    try {
//	        applicationInfo = packageManager.getApplicationInfo(context
//	        .getPackageName(), 128);
//	        if (applicationInfo != null && applicationInfo.metaData != null) {
//	            value = applicationInfo.metaData.get(name);
//	        }
//	    } catch (NameNotFoundException e) {
//	        throw new RuntimeException(
//	        "Could not read the name in the manifest file.", e);
//	    }
//	    if (value == null) {
//	        throw new RuntimeException("The name '" + name
//	        + "' is not defined in the manifest file's meta data.");
//	    }
//	    back value.toString();
//	}

}
