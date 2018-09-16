package kld.com.rfid.ldw;

import android.view.WindowManager;

import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.exception.ExceptionUtil;
import com.squareup.leakcanary.LeakCanary;
import com.uhf.uhf.UHF1.UHF1Application;

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


    public MyBaseService floatService;//FloatAccessibilityService
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

        if(Controller.isRelease==false){
            LeakCanary.install(this);
        }
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        ExceptionUtil.handleExceptionAndExit(e);
    }
}