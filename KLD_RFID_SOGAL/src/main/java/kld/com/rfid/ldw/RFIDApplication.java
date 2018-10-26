package kld.com.rfid.ldw;

import android.view.WindowManager;

import com.ldw.xyz.util.PreferenceUtil;
import com.ldw.xyz.util.exception.ExceptionUtil;
import com.uhf.uhf.UHF1.UHF1Application;

import kld.com.rfid.ldw.demand2.SuoFeiYaMainDemand2Activity;
import kld.com.rfid.ldw.demand2.baseService.MyBaseService;
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


    public MyBaseService floatService;//FloatAccessibilityService
    public SuoFeiYaMainDemand2Activity suoFeiYaMainDemand2Activity;//FloatAccessibilityService


    public FloatAccessibilityService floatAccessibilityService;//FloatAccessibilityService


    public static RFIDApplication instance ;
    private WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();
    public WindowManager.LayoutParams getMywmParams(){
        return wmParams;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ExceptionUtil.context = this;
        Thread.setDefaultUncaughtExceptionHandler(this);


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








}