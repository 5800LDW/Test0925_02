package com.example.android.api24adaptation.common;

import java.lang.reflect.Method;

/**
 * Created by serena on 2016-08-25.
 * describe:
 */

public class DebugUtils {
    private static final String PROPERTY_DEBUG = "alidebug";
    public static boolean DBG = getInt("alidebug", 0) == 1;
    private static Class<?> mClassType = null;
    private static Method mGetMethod = null;
    private static Method mGetIntMethod = null;

    public DebugUtils() {
    }

    public static String get(String var0) {
        init();
        String var1 = null;

        try {
            var1 = (String)mGetMethod.invoke(mClassType, new Object[]{var0});
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return var1;
    }

    public static int getInt(String var0, int var1) {
        init();
        int var2 = var1;

        try {
            Integer var3 = (Integer)mGetIntMethod.invoke(mClassType, new Object[]{var0, Integer.valueOf(var1)});
            var2 = var3.intValue();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return var2;
    }

    private static void init() {
        try {
            if(mClassType == null) {
                mClassType = Class.forName("android.os.SystemProperties");
                mGetMethod = mClassType.getDeclaredMethod("get", new Class[]{String.class});
                mGetIntMethod = mClassType.getDeclaredMethod("getInt", new Class[]{String.class, Integer.TYPE});
            }
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }
}
