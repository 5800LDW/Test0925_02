package com.ldw.xyz.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.ldw.xyz.util.device.ScreenUtil;

/**
 * Created by LDW10000000 on 25/11/2016.
 */

public class TextStyleUtil {

//    public static void set(Context mContext, TextView tv , String  content , String index){
//        String text = String.format(content);
//        int z = text.indexOf(index);
//        SpannableStringBuilder style = new SpannableStringBuilder(text);
//        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext,20)), 0, z, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
//        style.setSpan(new ForegroundColorSpan(Color.parseColor("#2A2A2A")), 0, z, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
//        style.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), z, z+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
//        style.setSpan(new ForegroundColorSpan(Color.parseColor("#afafaf")), z+1, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
////      style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext,14)), z+1, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
//        tv.setText(style);
//    }

    public static void seTextSize(Context mContext, TextView tv, String content, int textSize, int start, int end) {
        String text = String.format(content);
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext, textSize)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        tv.setText(style);
    }

    public static void setColor(TextView tv, String content, String color, int start, int end) {
        String text = String.format(content);
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
        tv.setText(style);
    }


}
