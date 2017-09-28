package com.ldw.xyz.util.notification;

import android.app.NotificationManager;
import android.content.Context;

/**
 * Created by LDW10000000 on 11/08/2017.
 */

public class NotificationUtil {

    /**
     * 清除当前创建的通知栏
     */
    public static void clearNotify(NotificationManager nm, Context context, int notifyId) {
        nm = getNotificationManager(nm, context);
        nm.cancel(notifyId);//删除一个特定的通知ID对应的通知
//		mNotification.cancel(getResources().getString(R.string.app_name));
    }

    /**
     * 清除所有通知栏
     */
    public static void clearAllNotify(NotificationManager nm, Context context) {
        nm = getNotificationManager(nm, context);
        nm.cancelAll();// 删除你发的所有通知
    }

















    private static NotificationManager getNotificationManager(NotificationManager nm, Context context) {
        if (nm == null) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return nm;
    }


}
