package kld.com.rfid.ldw;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.ldw.xyz.util.PreferenceUtil;
import com.ldw.xyz.util.exception.ExceptionUtil;
import com.uhf.uhf.UHF1.UHF1Application;

import kld.com.rfid.ldw.demand2.FloatService;
import kld.com.rfid.ldw.demand2.SuoFeiYaMainDemand2Activity;
import kld.com.rfid.ldw.demand2.way2.FloatAccessibilityService;

/**
 * Created by liudongwen on 2018/8/20.
 */

public class RFIDApplication extends UHF1Application implements
        Thread.UncaughtExceptionHandler {

//    public static final boolean isCanRecordEpcID = true;

    public static boolean getIsCanRecordEpcID(){
        return  Const.IsCanRecordEpcID;
    }


    public static boolean getIsCanTest(){
        return Const.IsCanTest;
    }

    public static boolean getIsCanCheckUpdate(){
        return  Const.IsCanUpdate;
    }


    public static boolean unShowPowerToast = true;
    private static int SetPowerTimes = 0;
    public static void setPowerTimesZero(){
        SetPowerTimes = 0;
    }
    public static void addSetPowerTimes(){
        SetPowerTimes +=1;
    }
    public static int getPowerTimes(){
        return SetPowerTimes;
    }


    private static boolean isCanShowToast = true;

    public static void setIsCanShowToastTrue(){
        isCanShowToast = true;
    }
    public static void setIsCanShowToastFalse(){
        isCanShowToast = false;
    }
    public static boolean getIsCanShowToastTrue(){
        return isCanShowToast;
    }

    public volatile static boolean isCanRead = true;


    public static  boolean serviceIsStop = false;


    public FloatService floatService;//FloatAccessibilityService
    public SuoFeiYaMainDemand2Activity suoFeiYaMainDemand2Activity;//FloatAccessibilityService


    public FloatAccessibilityService floatAccessibilityService;//FloatAccessibilityService


    public static RFIDApplication instance ;
    private WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();
    public WindowManager.LayoutParams getMywmParams(){
        return wmParams;
    }



//    public static Handler mHandler = new Handler();



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ExceptionUtil.context = this;
        Thread.setDefaultUncaughtExceptionHandler(this);

        initHandler();

//        if(Controller.isRelease==false){
//            LeakCanary.install(this);
//        }

//        if(Controller.isRelease==false){
//            LeakCanary.install(this);
//        }
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        RFIDApplication.setIsCanInstallFALSE();
        ExceptionUtil.handleExceptionAndExit(e);
        //20181009
        if(floatService!=null){
//            floatService.stopButtonClick();
//            Intent serviceStop = new Intent();
//            serviceStop.setClass(this, FloatService.class);
//            stopService(serviceStop);
//            if(floatService!=null){
//                floatService.release();
//            }
//            floatService = null;
        }

    }


    public static void setIsCanInstallFALSE(){
        PreferenceUtil.set(RFIDApplication.instance,Const.KEY_IS_CAN_INSTALL,Const.CONST_FALSE);
    }
    public static void setIsCanInstallTRUE(){
        PreferenceUtil.set(RFIDApplication.instance,Const.KEY_IS_CAN_INSTALL,Const.CONST_TRUE);
    }

    public static String  getModeIsBuHuo(){
        return PreferenceUtil.get(RFIDApplication.instance,Const.KEY_MODE_BU_HUO);
    }

    /**
     *
     * @param str  Const.CONST_FALSE or Const.CONST_TRUE
     */
    public static void  setModeBuHuo(String str){
         PreferenceUtil.set(RFIDApplication.instance,Const.KEY_MODE_BU_HUO,str);
    }



    public static boolean getIsDefaultFloatButton(){
        if(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_FLOAT_BUTTON).equals(Const.CONST_TRUE)){
            return true;
        }
        else {
            return false;
        }
    }

    public  static Handler mApplicationHandler;

    private void initHandler(){
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        mApplicationHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e("TAG","uiThread2------"+Thread.currentThread());//子线程
                try{

                    Thread.sleep(100);
                }catch (Exception e){
                    Log.e("TAG",e.toString());//子线程
                }
            }
        };

        Log.e("TAG","uiThread1------"+Thread.currentThread());//主线程
    }







}