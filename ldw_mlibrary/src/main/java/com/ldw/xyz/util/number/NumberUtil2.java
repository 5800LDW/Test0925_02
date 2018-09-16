package com.ldw.xyz.util.number;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.StringHelper;
import com.ldw.xyz.util.string.StringHelper2;

import java.util.regex.Pattern;

/**
 * Created by LDW10000000 on 24/10/2017.
 */

public class NumberUtil2 {

    /**
     * 对结果进行四舍五入
     *
     * @param value  :原始数
     * @param resLen :所要的精度
     * @return
     */
    public static Number getRealVaule(double value, int resLen) {
        if (resLen == 0)
            //原理:123.456*10=1234.56+5=1239.56/10=123
            //原理:123.556*10=1235.56+5=1240.56/10=124
            return Math.round(value * 10 + 5) / 10;
        double db = Math.pow(10, resLen);
        return Math.round(value * db) / db;
    }


    public static int strParse2Int(String str) {
        int number = 0;
        try {
            if (!StringHelper.isEmpty(str)) {
                number = Integer.parseInt(str);
            }
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
        return number;
    }

    /**
     *
     * @param num
     * @return false 小于1或为空  true 正常
     */
    public static boolean atLeastOne(String num){
        if(StringHelper2.isEmpty(num) || num.startsWith("0")){
            return false;
        }
        else {
            return true;
        }

    }

    /**向上取整*/
    public static int ceil(Double d){
        return  (int)Math.ceil(d);
    }


    /*方法二：推荐，速度最快
  * 判断是否为整数
  * @param str 传入的字符串
  * @return 是整数返回true,否则返回false
*/

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


//    舍掉小数取整:Math.floor(2)=2
//    舍掉小数取整:Math.floor(2.1)=2
//    舍掉小数取整:Math.floor(-2.1)=-3
//    舍掉小数取整:Math.floor(-2.5)=-3
//    舍掉小数取整:Math.floor(-2.9)=-3
//    四舍五入取整:Math.rint(2)=2
//    四舍五入取整:Math.rint(2.1)=2
//    四舍五入取整:Math.rint(-2.5)=-2
//    四舍五入取整:Math.rint(2.5)=2
//    四舍五入取整:Math.rint(2.9)=3
//    四舍五入取整:Math.rint(-2.9)=-3
//    四舍五入取整:Math.rint(-2.49)=-2
//    四舍五入取整:Math.rint(-2.51)=-3
//    凑整:Math.ceil(2)=2
//    凑整:Math.ceil(2.1)=3
//    凑整:Math.ceil(2.5)=3
//    凑整:Math.ceil(2.9)=3
//    舍掉小数取整:Math.floor(-2)=-2
//    舍掉小数取整:Math.floor(-2.1)=-3
//    舍掉小数取整:Math.floor(-2.5)=-3
//    舍掉小数取整:Math.floor(-2.9)=-3
//    凑整:Math.ceil(-2)=-2
//    凑整:Math.ceil(-2.1)=-2
//    凑整:Math.ceil(-2.5)=-2
//    凑整:Math.ceil(-2.9)=-2
//            Math.round(3.14)3
//            Math.round(3.5)4
//            Math.round(-3.14)-3
//            Math.round(-3.5)-3
//
//    总结：floor向下取整，ceil向上取整；round和rint四舍五入，取绝对值后舍入，然后加上符号，遇到.5的时候向绝对值小的方向舍之。


}
