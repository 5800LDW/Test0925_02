package com.ldw.xyz.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author dongwenliu
 * 
 */
public class PreferenceUtil {

	static SharedPreferences preferences;
	static Editor editor;

	public static boolean set(Context context, String key, String value) {
		setObjectNotNull(context);
		try {
			editor.putString(key, value);
			editor.commit();
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
			// fail 
			return false;
		}
		// success
		return true;
	}

	public static String get(Context context, String key, String value) {
		setObjectNotNull(context);
		String str = preferences.getString(key, value);
		return str;
	}
	
	
	private static void setObjectNotNull(Context context) {
		if (preferences == null) {
			preferences = getPreferences(context);
			editor = preferences.edit();
		}
	}

	private static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences("$_ConnectekSetting$", Context.MODE_PRIVATE);
	}



	/**
	 * 清除指定key 的内容
	 * @param key
	 */
	public static void remove(Context context,String key){
		setObjectNotNull(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(key).commit();
	}






	// private static void editorCommit(Context context, String name,
	// String content) {
	// try {
	// SharedPreferences preferences = getPreferences(context);
	// Editor editor = preferences.edit();
	// editor.putString(name, content);
	// editor.commit();
	// } catch (Exception e) {
	// ExceptionUtil.handleException(e);
	// }
	// }
}
