package com.ldw.xyz.util;

import android.content.Context;

/**
 * Created by LDW10000000 on 22/11/2016.
 */

public class ActivityNameUtil {

    public static  String getRunningActivityName(Context context) {
        String contextString = context.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }
}
