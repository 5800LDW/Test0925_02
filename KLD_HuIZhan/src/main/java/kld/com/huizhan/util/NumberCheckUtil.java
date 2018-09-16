package kld.com.huizhan.util;

import com.ldw.xyz.util.string.StringHelper2;

/**
 * Created by LDW10000000 on 29/11/2017.
 */

public class NumberCheckUtil {


    public static boolean notLessThan1(String num){
        if(StringHelper2.isEmpty(num) || num.startsWith("0")){
            return true;
        }
        else {
            return false;
        }

    }




}
