package kld.com.rfid.ldw.demand2.way3;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.PreferenceUtil;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.RFIDApplication;
import kld.com.rfid.ldw.demand2.SuoFeiYaMainDemand2Activity;

/**
 * Created by liudongwen on 2018/9/2.
 */

public class MyAccessibilityService extends AccessibilityService {


    private static final String TAG = "MyAccessibilityService";

    Map<Integer, Boolean> handledMap = new HashMap<>();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {


//        PackageInstaller


        LogUtil.e(TAG, "---------调用了---------");
        LogUtil.e(TAG, "RFIDApplication.getIsCanCheckUpdate() = " + RFIDApplication.getIsCanCheckUpdate());
        LogUtil.e(TAG, "PreferenceUtil.get(RFIDApplication.instance,Const.KEY_IS_CAN_INSTALL) = " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));
        LogUtil.e(TAG, "-----------------------------------------------------");


        if (RFIDApplication.getIsCanCheckUpdate() == false) {
            return;
        }
        if (PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL).toUpperCase().equals(Const.CONST_FALSE)) {
            return;
        }


        AccessibilityNodeInfo nodeInfo = event.getSource();


        LogUtil.e(TAG, "***  *** ------------------");
        if (event != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                LogUtil.e(TAG, "event.getClassName() = " + event.getClassName());
                LogUtil.e(TAG, "event.getPackageName() = " + event.getPackageName());
                LogUtil.e(TAG, "event = " + event.toString());
            }
        }
        if (nodeInfo != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                LogUtil.e(TAG, "nodeInfo.getClassName() = " + nodeInfo.getClassName());
                LogUtil.e(TAG, "nodeInfo.getPackageName() = " + nodeInfo.getPackageName());
            }
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            LogUtil.e(TAG, " getRootInActiveWindow().getPackageName()  = " +  getRootInActiveWindow().getPackageName());
//            LogUtil.e(TAG, " getRootInActiveWindow().getClassName()  = " +  getRootInActiveWindow().getClassName());
//        }

        LogUtil.e(TAG, "***  *** ------------------ ***  ***");


        if (nodeInfo != null) {
            int eventType = event.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
                    eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (handledMap.get(event.getWindowId()) == null) {
                    boolean handled = iterateNodesAndHandle(nodeInfo);
                    if (handled) {
                        handledMap.put(event.getWindowId(), true);
                    }
                }
            }
        }


    }

    private boolean iterateNodesAndHandle(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            int childCount = nodeInfo.getChildCount();
            //nodeInfo.getViewIdResourceName();
            LogUtil.e(TAG, "***  *** ------------------");
            LogUtil.e(TAG, "nodeInfo.getClassName() " + nodeInfo.getClassName());

            if (nodeInfo.getText() != null) {
                LogUtil.e(TAG, "content is " + nodeInfo.getText());

            }
            LogUtil.e(TAG, "***  *** ------------------ ***  ***");

            if ("android.widget.Button".equals(nodeInfo.getClassName())) {
                String nodeContent = nodeInfo.getText().toString();
                LogUtil.e(TAG, "content is " + nodeContent);


                if (PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL).toUpperCase().equals(Const.CONST_FALSE)) {

                } else if ("安装".equals(nodeContent) || "确定".equals(nodeContent)) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);

//                    LogUtil.e(TAG, "content is " + nodeContent);

//                    if ("打开".equals(nodeContent)) {
//                        Toast.makeText(this, "更新完成!", Toast.LENGTH_SHORT).show();
//                        setCanInstallFalse();
//                        LogUtil.e("TAG", "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) = " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));
//                        handledMap.clear();
//                    }

//                    if ("安装".equals(nodeContent)) {
//                        setCanInstallFalse();
//                        LogUtil.e("TAG", "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) = " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));
//                        handledMap.clear();
//                    }

                    return true;
                }
                else if ("打开".equals(nodeContent)) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                    Toast.makeText(this, "更新完成!", Toast.LENGTH_SHORT).show();
                    setCanInstallFalse();
                    LogUtil.e("TAG", "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) = " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));
                    handledMap.clear();

                    return true;
                }


            } else if ("android.widget.CheckedTextView".equals(nodeInfo.getClassName())) {

                if (PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL).toUpperCase().equals(Const.CONST_FALSE)) {

                } else if ("设备内存".equals(nodeInfo.getText())) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    return true;
                }

            } else if ("android.widget.ScrollView".equals(nodeInfo.getClassName())) {
                if (PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL).toUpperCase().equals(Const.CONST_FALSE)) {

                } else {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                }

            } else if ("android.widget.TextView".equals(nodeInfo.getClassName())) {
//                if ("应用安装完成".equals(nodeInfo.getText())) {
//                    setCanInstallFalse();
//                }
//                else if (ResHelper.getString(this, R.string.app_name).equals(nodeInfo.getText())) {
//                    RFIDApplication.setIsCanInstallTRUE();
//                }
                //TODO 如果能检测到"打开",就不需要下面的;
//                else if("正在安装...".equals(nodeInfo.getText())){
//                    setCanInstallFalse();
//                    LogUtil.e("TAG", "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) = " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));
//                    handledMap.clear();
//                }
            }


            for (int i = 0; i < childCount; i++) {
                LogUtil.e(TAG, "childCount >>>>>>>>>>>>>>>>> " + childCount);
                AccessibilityNodeInfo childNodeInfo = nodeInfo.getChild(i);
                if (iterateNodesAndHandle(childNodeInfo)) {
                    return true;
                }
            }
        }
        return false;

    }

    public static void setCanInstallFalse() {

        RFIDApplication.setIsCanInstallFALSE();

    }


    @Override
    protected boolean onKeyEvent(KeyEvent event) {

        LogUtil.e("TAG1", "getScanCode=========>" + event.getScanCode());
        LogUtil.e("TAG1", "getKeyCode=========>" + event.getKeyCode());

//        int key = event.getKeyCode();
        if (event.getScanCode() == Const.KEY_SCAN_BUTTON && event.getAction() == KeyEvent.ACTION_UP) {

//            Intent mIntent = new Intent();
//            mIntent.setAction("$_ActionUp");
//            sendBroadcast(mIntent);

            LogUtil.e("TAG1", "RFIDApplication.instance.isCanRead");
            LogUtil.e("TAG1", RFIDApplication.instance.isCanRead);
            LogUtil.e("TAG1", "RFIDApplication.instance.serviceIsStop");
            LogUtil.e("TAG1", RFIDApplication.instance.serviceIsStop);

            if(!RFIDApplication.instance.isCanRead){
                return super.onKeyEvent(event);
            }

            if(RFIDApplication.instance.serviceIsStop == true){
                return super.onKeyEvent(event);
            }


            doBiz();



            LogUtil.e("TAG1", "getAction=========>" + event.getAction());
            return false;
        } else {
            return super.onKeyEvent(event);
        }
    }

    static  long time1 = 0;

    public static void doBiz(){
        //重复点击不处理;
        if ((System.currentTimeMillis() - time1)<600) {
            return;
        }


        time1 =   System.currentTimeMillis();

        if(!RFIDApplication.instance.isCanRead){
            return ;
        }

        if(RFIDApplication.instance.serviceIsStop == true){
            return ;
        }



        new Handler().postDelayed(runnable,1);


    }






    public static Runnable runnable =new Runnable() {
        @Override
        public void run() {
            if(!RFIDApplication.instance.isCanRead){
                return ;
            }

            if (RFIDApplication.instance != null && RFIDApplication.instance.floatService != null) {
                RFIDApplication.instance.floatService.scanBiz();
            }
        }
    };





    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e(TAG, "onCreate() >>>>>>>>>>>>>.");
        test();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e(TAG, "onStartCommand() >>>>>>>>>>>>>.");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        LogUtil.e(TAG, "onStart() >>>>>>>>>>>>>.");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.e(TAG, "onDestroy() >>>>>>>>>>>>>.");
        super.onDestroy();
    }

//    @Override
//    protected void onServiceConnected() {
//        LogUtil.e(TAG, "onServiceConnected() >>>>>>>>>>>>>.");
//        LogUtil.e(TAG, "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) >>>>>>>>>>>>>." +
//                PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));
//
//        if (PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL).toUpperCase().equals(Const.CONST_FALSE)) {
//
//        } else  {
//            LogUtil.e(TAG, "执行我 >>>>>>>>>>>>>.");
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    //方法1
////                    MyAccessibilityService.this.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
////
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            OtherBizUtil.setMyOtherBizUtilInterface(new OtherBizUtil.myOtherBizUtilInter() {
////                                @Override
////                                public void biz1() {
////                                    RFIDApplication.setIsCanInstallTRUE();
////                                }
////                            });
////                            AppUtils.installApk(MyAccessibilityService.this,new File("/storage/emulated/0/VersionPath/kld.com.rfid.ldw.apk"));
////
////                            LogUtil.e(TAG, "AppUtils.installApk");
////                        }
////                    },100);
//
////                    //方法2会崩溃
////                    RFIDApplication.setIsCanInstallTRUE();
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
////                            Uri uri = Uri.fromFile(new File("package:kld.com.rfid.ldw"));
////                            intent.setData(uri);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                            intent.setAction(Intent.ACTION_VIEW);
////                            startActivity(intent);
////                        }
////                    },500);
//
//
//                    //方法3
//                    MyAccessibilityService.this.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent in = new Intent();				//com.ustcinfo.ict.ahhxapp 被启动包名；com.ustcinfo.ict.platform.ui.LoginActivity  被启动指定类全名
//                            in.setClassName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
//                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            in.setAction(Intent.ACTION_VIEW);
//                            startActivity(in);
//                        }
//                    },500);
//
//
//
//
//                }
//            },200);
//
//        }
//
////        if (PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL).toUpperCase().equals(Const.CONST_FALSE)) {
////
////        } else  {
////            new Handler().postDelayed(new Runnable() {
////                @Override
////                public void run() {
////
////                    RFIDApplication.setIsCanInstallTRUE();
////
//////                    Intent intent = new Intent();
////
////
////                    Intent intent = new Intent("com.android.packageinstaller");
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//////                    intent.setAction(Intent.ACTION_VIEW);
////                    startActivity(intent);
////
////
////                    LogUtil.e(TAG, "AppUtils.installApk");
////                }
////            },600);
////
////        }
//
//
//
//        super.onServiceConnected();
//    }


//    @Override
//    protected void onServiceConnected() {
//        LogUtil.e(TAG, "onServiceConnected() >>>>>>>>>>>>>.");
//        LogUtil.e(TAG, "第一次 PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) >>>>>>>>>>>>>." +
//                PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                LogUtil.e(TAG, "第二次 PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) >>>>>>>>>>>>>." +
//                        PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));
//
//                if (PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL).toUpperCase().equals(Const.CONST_FALSE)) {
//
//                } else {
//
//                    LogUtil.e(TAG, "执行我 >>>>>>>>>>>>>.");
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        LogUtil.e(TAG, "----------------------------------------");
//                        LogUtil.e(TAG, "Build.VERSION_CODES.N 执行>>>>>>>>>>>>>.");
//
//                        Intent in = new Intent();                //com.ustcinfo.ict.ahhxapp 被启动包名；com.ustcinfo.ict.platform.ui.LoginActivity  被启动指定类全名
//                        in.setClassName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
//                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        in.setAction(Intent.ACTION_VIEW);
//                        startActivity(in);
//
//                    } else {
//
//                        MyAccessibilityService.this.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                LogUtil.e(TAG, "----------------------------------------");
//                                LogUtil.e(TAG, " 其他 执行>>>>>>>>>>>>>.");
//                                Intent in = new Intent();                //com.ustcinfo.ict.ahhxapp 被启动包名；com.ustcinfo.ict.platform.ui.LoginActivity  被启动指定类全名
//                                in.setClassName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
//                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                in.setAction(Intent.ACTION_VIEW);
//                                startActivity(in);
//                            }
//                        }, 500);
//
//
//                    }
//
//
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
//                    //方法3
////                            MyAccessibilityService.this.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
//
////                    LogUtil.e(TAG, "----------------------------------------");
////                    LogUtil.e(TAG, "Build.VERSION_CODES.N 执行>>>>>>>>>>>>>.");
////
////                    Intent in = new Intent();                //com.ustcinfo.ict.ahhxapp 被启动包名；com.ustcinfo.ict.platform.ui.LoginActivity  被启动指定类全名
////                    in.setClassName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
////                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    in.setAction(Intent.ACTION_VIEW);
////                    startActivity(in);
//
//
////                            new Handler().postDelayed(new Runnable() {
////                                @Override
////                                public void run() {
////
////
////                                    // com.android.packageinstaller   com.android.packageinstaller.InstallAppProgress
////
////                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////                                        LogUtil.e(TAG, "----------------------------------------");
////                                        LogUtil.e(TAG, "Build.VERSION_CODES.N 执行>>>>>>>>>>>>>.");
////
////                                        Intent in = new Intent();				//com.ustcinfo.ict.ahhxapp 被启动包名；com.ustcinfo.ict.platform.ui.LoginActivity  被启动指定类全名
////                                        in.setClassName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
////                                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                        in.setAction(Intent.ACTION_VIEW);
////                                        startActivity(in);
////
////                                    }
////                                    else{
////                                        LogUtil.e(TAG, "----------------------------------------");
////                                        LogUtil.e(TAG, " 其他 执行>>>>>>>>>>>>>.");
////                                        Intent in = new Intent();				//com.ustcinfo.ict.ahhxapp 被启动包名；com.ustcinfo.ict.platform.ui.LoginActivity  被启动指定类全名
////                                        in.setClassName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
////                                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                        in.setAction(Intent.ACTION_VIEW);
////                                        startActivity(in);
////                                    }
////
////
////
////                                }
////                            },10);
//
////                        }
////                    },10);
//
//                }
//            }
//        }, 1300);
//
//
//        super.onServiceConnected();
//    }


    @Override
    protected void onServiceConnected() {
        LogUtil.e(TAG, "onServiceConnected() >>>>>>>>>>>>>.");
        LogUtil.e(TAG, "第一次 PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) >>>>>>>>>>>>>." +
                PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                LogUtil.e(TAG, "第二次 PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) >>>>>>>>>>>>>." +
                        PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));

                if (PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL).toUpperCase().equals(Const.CONST_FALSE)) {

                } else {

                    MyAccessibilityService.this.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);

//                    ActivityUtil.startActivity(MyAccessibilityService.this, SuoFeiYaMainDemand2Activity.class,null);

                    Intent in = new Intent(MyAccessibilityService.this,SuoFeiYaMainDemand2Activity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.setAction(Intent.ACTION_VIEW);
                    startActivity(in);

                    setCanInstallFalse();
                    LogUtil.e("TAG", "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) = " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));
                    handledMap.clear();
                    Toast.makeText(MyAccessibilityService.this, "更新完成!", Toast.LENGTH_SHORT).show();

                }
            }
        }, 1200);


        super.onServiceConnected();
    }


    @Override
    public void onInterrupt() {
        LogUtil.e(TAG, "onInterrupt() >>>>>>>>>>>>>.");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.e(TAG, "onUnbind() >>>>>>>>>>>>>.");
        return super.onUnbind(intent);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    LogUtil.e(TAG, "---------------- 执行了 ----------------");
                    //需要执行的代码放这里
                    break;
            }
        }
    };

    public void test() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        Timer timer = new Timer(true);
//        Date date = strToDateLong("12:37");
//        LogUtil.e(TAG, "   date  = " + date);
//        timer.schedule(task,date);


        timer.schedule(task, strToDateLong("2019-01-09 11:32:00"));
    }

    /**
     * string类型时间转换为date
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//获得的年月日在今天之前就会立刻执行, 在今天之后就不会执行;
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");//获得的年份是1970年的,所以会立刻执行;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


}


























