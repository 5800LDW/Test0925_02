package com.ldw.xyz.util;

/**
 * Created by LDW10000000 on 05/12/2016.
 */
@Deprecated
public class NumberUtil {

    /**
     * 对结果进行四舍五入
     *
     * @param value  :原始数
     * @param resLen :所要的精度
     * @return
     */
    public static   Number getRealVaule(double value,int resLen) {
        if(resLen==0)
            //原理:123.456*10=1234.56+5=1239.56/10=123
            //原理:123.556*10=1235.56+5=1240.56/10=124
            return Math.round(value*10+5)/10;
        double db  = Math.pow(10, resLen);
        return Math.round(value*db)/db;
    }
}
