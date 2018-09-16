package com.ldw.xyz.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	
	private static Toast toast;

	public static void showToast(Context context, String content) {

		if(toast == null){
			toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
		}else{
			toast.setText(content);
		}
		toast.show();
	}
	
	
	public static void showToast(Context context, int content) {
		
		if(toast == null){
			//ontext.getApplicationContext() 能解决内存泄露的问题
			toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
		}else{
			toast.setText(content);
		}
		toast.show();
	}
//	public static void showToastByAddWindows(Context context , String str){
//		ToastInWindow.makeText(context, str, ToastInWindow.LENGTH_LONG).show();
//	}
}
