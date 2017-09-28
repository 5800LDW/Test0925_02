package com.ldw.xyz.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by LDW10000000 on 20/06/2017.
 */

public class InputMethodUtil {
    /**
     * 强制隐藏
     *
     * @param context
     * @param views
     */
    public static void forceHideKeyBoard(Context context, View... views) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        for (int i = 0; i < views.length; i++) {
            View v = views[i];
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

    }

    /**
     * 强制显示
     *
     * @param context
     * @param view
     */
    public static void forceShowKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 获取输入法打开的状态
     */
    public static boolean getKeyBoardState(Context context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        return isOpen;
    }

}
