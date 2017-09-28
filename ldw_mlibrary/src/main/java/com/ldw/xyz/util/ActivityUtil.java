package com.ldw.xyz.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ldw.xyz.R;

public class ActivityUtil {

	/***
	 * 无需带intent  或 带
	 *
	 * @param context
	 * @param cls
     * @param i
     */
	public static void startActivity(Context context, Class<?> cls, Intent i) {
		if (i == null) {
			Intent intent = new Intent(context, cls);
			context.startActivity(intent);
		} else if (i != null) {
			context.startActivity(i);
			
		}
		if(context instanceof Activity)
			((Activity)context).overridePendingTransition(R.anim.push_left_in,
					R.anim.push_left_out);
	}

	/**
	 * 
	 * @param activity
	 * @param cls
	 * @param i
	 * @param enter
	 *            0:default animation
	 * @param exit
	 *            0:default animation
	 */
	public static void startActivity(Activity activity, Class<?> cls, Intent i, int enter, int exit) {
		if (i == null) {
			Intent intent = new Intent(activity, cls);
			activity.startActivity(intent);
		} else if (i != null) {
			activity.startActivity(i);
		}

		if (enter == 0 || exit == 0) {
			animation(activity);
		} else {
			activity.overridePendingTransition(enter, exit);
		}
	}

	private static void animation(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_buttom_to_top_02enter, R.anim.slide_top_to_bottom_02exit);
	}

	/***
	 * 启动activity 的时候 带过去String
	 *
	 * @param activity
	 * @param cls
	 * @param key
	 * @param value
     * @param ani 动画, 两个动画的int 值 例如:R.anim.slide_
     */
	public static void startActivityWithString(Activity activity, Class<?> cls, String key,String value, int... ani){
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		intent.putExtra(key,value);
		activity.startActivity(intent);
		if (ani.length==0) {
			animation(activity);
		} else {
			int enter = ani[0] ;
			int exit ;
			if(ani.length>=2){
				exit = ani[1];
				activity.overridePendingTransition(enter,exit);
			}
			else{
				activity.overridePendingTransition(enter,R.anim.slide_top_to_bottom_02exit);//后面的这个是随意了;
			}
		}
	}




}
