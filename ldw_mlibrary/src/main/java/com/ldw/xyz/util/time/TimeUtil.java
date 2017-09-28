package com.ldw.xyz.util.time;

import com.ldw.xyz.util.ExceptionUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LDW10000000 on 30/03/2017.
 */

public class TimeUtil {

    public static long getCurrentTimeMillisFromInternet() {
        URL url ;//取得资源对象
        long ld = 0;
        try {
            url = new URL("http://www.baidu.com");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            ld = uc.getDate(); //取得网站日期时间
            System.out.println(ld);

        } catch (MalformedURLException e) {
            ExceptionUtil.handleException(e);
        } catch (IOException e) {
            ExceptionUtil.handleException(e);
        }
        return ld;
    }

    /**
     *  获取24小时制时间yyyy-MM-dd HH-mm-ss
     *  获取12小时制时间yyyy-MM-dd hh-mm-ss
     * @param dateFormat
     * @return
     */
    public static String getCurrentDateAccordingToFormat(String dateFormat){
        SimpleDateFormat formatter  =  new  SimpleDateFormat(dateFormat);
        Date curDate = new Date(System.currentTimeMillis());
        return  formatter.format(curDate);
    }




}
