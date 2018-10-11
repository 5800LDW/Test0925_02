package kld.com.rfid.ldw;

import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.PreferenceUtil;

/**
 * Created by liudongwen on 2018/8/23.
 */

public final class Const {



    //是否允许测试
    public static final boolean IsCanTest = true;
    //是否允许记录id号到本地
    public static final boolean IsCanRecordEpcID = true;
    //是否允许更新
    public static final boolean IsCanUpdate = true;
    //是不是发布版本
    static {
        Controller.isRelease = false;
    }




    public static final String KEY_URL_SERVER = "$_url";
    public static final String KEY_STAGE_NMAE = "$_STAGE_NMAE";
    public static final String KEY_STAGE_CODE = "$_KEY_STAGE_CODE";
    public static final String KEY_ORGANIZATION = "$_KEY_ORGANIZATION";
    public static final String KEY_IS_CAN_INSTALL = "$_KEY_ISCANINSTALL";
    public static final String KEY_MODE_BU_HUO = "$_KEY_MODEBUHUO";

    public static final String KEY_FLOAT_BUTTON = "$_KEY_FLOAT_BUTTON";

    public static final String KEY_URL_UPDATE = "$_KEY_KEY_URL_UPDATE";


    public static final String CONST_TRUE = "TRUE";
    public static final String CONST_FALSE = "FALSE";

    public static final int KEY_SCAN_BUTTON = 261;

//    public static final int CONN_TIME_OUT = 39;
//    public static final int READ_TIME_OUT = 39;
//    public static final int WRITE_TIME_OUT = 39;
//
    public static final int CONN_TIME_OUT = 39;
    public static final int READ_TIME_OUT = 39;
    public static final int WRITE_TIME_OUT = 39;


    private static final String getURL_BASE(){
        return PreferenceUtil.get(RFIDApplication.instance,KEY_URL_SERVER,
                RFIDApplication.instance.getResources().getString(R.string.url_server_address)
        );
    }



    public static String getURL_UPLAOD(){
        return getURL_BASE() + "/rfidscanned/upload";
    }

    public static String getURL_BU_HUO(){
        return getURL_BASE() + "/rfidscanned/rescanned";
    }

    public static String getURL_MATERIALS(){
        return getURL_BASE() + "/organization/materials";
    }


    public static String getURL_ORG_ID(String id){
        return getURL_BASE() + "/wmsrfidstagev/materials?ORG_ID="+id;
    }

    public static String getURL_DownloadAPK(){
//        return getURL_BASE() ;//TODO
//        return "http://test-1251233192.coscd.myqcloud.com/1_1.apk";
        return "http://192.168.1.66:8080/MyFirstServletDemo/servlet/HelloServlet/getVersionName";
    }

    public static String getURL_Version(){
//        return getURL_BASE() ;//TODO
//        return "https://www.baidu.com"; //https://www.baidu.com
//        return "http://192.168.1.27:8080/MyFirstServletDemo/servlet/HelloServlet/getVersionName";
        return "https://www.baidu.com";
    }


}
