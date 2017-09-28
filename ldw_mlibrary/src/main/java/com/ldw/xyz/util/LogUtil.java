package com.ldw.xyz.util;

import android.util.Log;

import com.ldw.xyz.control.Controller;

/**
 * 统一处理日志
 * 
 */
public class LogUtil {

	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int NOTHING = 6;

//	public static final int level = VERBOSE;
	public static final int level ;

	/***
	 * 控制的代码
	 *
	 * 当 level = NOTHING 代表不打印任何内容;
	 *
	 */
	static {
		if(com.ldw.xyz.control.Controller.isRelease){
			level = NOTHING;
		}
		else {
			level = VERBOSE;
		}
	}

	public static void v(String tag,String msg){
		if(level <= VERBOSE){
			Log.v(tag,msg);
		}
	}

	public static void d(String tag,String msg){
		if(level <= DEBUG){
			Log.v(tag,msg);
		}
	}

	public static void i(String tag, Object msg) {
		if(level <= INFO){
			Log.i(tag, String.valueOf(msg));
		}
	}

	public static void w(String tag,String msg){
		if(level <= WARN){
			Log.v(tag,msg);
		}
	}


	public static void e(String tag, Object msg) {
        if(level <= ERROR){
			Log.e(tag, String.valueOf(msg));
		}
	}


	public static void m(Object msg) {
		if (Controller.isRelease) {
//			back;
		}else{
			Log.e("TAG", String.valueOf(msg));
		}
	}

}
