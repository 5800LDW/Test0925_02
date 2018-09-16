package kld.com.huizhan;

import android.content.Context;

import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.ExceptionUtil;
import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by LDW10000000 on 14/11/2017.
 */

public class HuIZhanApplication extends LitePalApplication implements
        Thread.UncaughtExceptionHandler {

    public static HuIZhanApplication instance;

    public String lName = "";


    @Override
    public void onCreate() {
        super.onCreate();
         LitePal.initialize(this);
        instance = this;
        Thread.setDefaultUncaughtExceptionHandler(this);


        if(Controller.isRelease==false){
            LeakCanary.install(this);
        }

    }

    public static String getLName(Context context){
        return  ((HuIZhanApplication)context.getApplicationContext()).lName;
    }



    @Override
    public void uncaughtException(Thread t, Throwable e) {
        ExceptionUtil.printAndSendEmail(e);
    }
}
