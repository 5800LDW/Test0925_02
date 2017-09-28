package com.ldw.xyz.util;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class ResHelper {
    /**array 的 index*/
    public static String getArrayIndex(Context context, int name, int index) {
        return context.getResources().getStringArray(name)[index];
    }
    /**array 的全部*/
    public static String[] getArray(Context context, int name) {
        return context.getResources().getStringArray(name);
    }

    public static String getString(Context context, int i) {
        return context.getResources().getString(i);
    }

    public static int getInteger(Context context, int i) {
        return context.getResources().getInteger(i);
    }

    public static void setText(Context context, TextView tv, String a, String b, int r) {
        String str = ResHelper.getString(context, r);
        String p = "";
//		if(b.equals(null)){
//			p = String.format(str, a);
//			
//		}else {
//			p = String.format(str, a,b);
//		}
        p = String.format(str, a);
        tv.setText(p);
    }

    public static int getColor(Context context, int i) {
        return context.getResources().getColor(i);
    }

    /**
     *
     * @param context
     * @param i drawable
     * @return Drawable
     */
    public static Drawable getDrawable(Context context, int i) {
        return context.getResources().getDrawable(i);
    }


    /**
     * @param context
     * @param fileName 例如 1.  txt/libai.txt   txt是文件夹是在assets里面, libai.txt是在这个文件夹下面的文件
     *                      2.  li.txt         li.txt是在assets文件夹下面的文件
     * @return
     * @throws IOException
     */
    public static InputStream getAssetsInputStream(Context context, String fileName) throws IOException{
        return  context.getAssets().open(fileName);
    }






}
