package kld.com.huizhan.util;

import com.ldw.xyz.util.time.TimeUtil;

/**
 * Created by LDW10000000 on 27/11/2017.
 */

public class OddNumbersUtil {


    public static String getIntPut(){
        String date = TimeUtil.getCurrentDateAccordingToFormat("yyyy-MM-dd");

//        date = "IP"+date + (int)((Math.random()*9+1)*100000);
        date = "IP"+date + (int)(Math.random()*100000);
        return date;
    }

    public static String getOutPut(){
        String date = TimeUtil.getCurrentDateAccordingToFormat("yyyy-MM-dd");
        date = "OP"+date + (int)(Math.random()*100000);
//        date = "OP"+date + (int)((Math.random()*9+1)*100000);
        return date;
    }


}
