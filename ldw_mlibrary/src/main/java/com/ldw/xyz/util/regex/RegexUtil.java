package com.ldw.xyz.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LDW10000000 on 26/04/2017.
 *
 *
 * 正则表达式工具类, 具体请查看笔记的常用java正则表达式大全
 *
 */

public class RegexUtil {

    /**
     * 是否含有数字;
     *
     * @param str
     * @return
     */
    public static boolean isContainDigit(String str){
        boolean isDigit = false;// 定义一个boolean值，用来表示是否包含数字
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {// 用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
        }
        return isDigit;
    }

    /**
     * 是否含有字母; 例如:s S
     *
     * @param str
     * @return
     */
    public static boolean isContainLetter(String str){
        boolean isLetter = false;// 定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {// 用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        return isLetter;
    }

    /**
     * 是否含有中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static boolean onlyHasLetterAndDigit(String str){
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        boolean matB = match.matches();
        return matB;
    }

}
