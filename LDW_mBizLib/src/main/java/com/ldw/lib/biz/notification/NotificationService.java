//package com.ldw.lib.biz.notification;//package com.ldw.xyz.download;
////
//
//import android.app.DownloadManager;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.Uri;
//import android.os.Binder;
//import android.os.Environment;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.util.LongSparseArray;
//import android.widget.Toast;
//
//import com.ldw.lib.biz.download.DownloadService;
//import com.ldw.lib.biz.download.util.IOUtils;
//import com.ldw.lib.biz.download.util.InstallUtil;
//import com.ldw.lib.biz.download.util.SystemManager;
//
//import java.io.File;
//
///**
// * If there is no bug, then it is created by ChenFengYao on 2017/4/20,
// * otherwise, I do not know who create it either.
// */
//
///**
// * Created by LDW10000000 on 14/08/2017.
// */
//public class NotificationService extends Service {
//
//    /** Notification管理 */
//    public NotificationManager mNotificationManager;
//    private ClickBroadcastReceiver mReceiver;
//    /** 通知栏按钮点击事件对应的ACTION */
//    public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
//    public final static String INTENT_BUTTONID_TAG = "ButtonId";
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder;
//    }
//
//    @Override
//    public void onDestroy() {
//        unregisterReceiver(mReceiver);//取消注册广播接收者
//        super.onDestroy();
//    }
//
//    public class ClickBinder extends Binder {
//
//
//    }
//
//    /**
//     *	 广播监听按钮点击时间
//     */
//    public class ClickBroadcastReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if(action.equals(ACTION_BUTTON)) {
//                //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
//                int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
//            }
//        }
//    }
//}
