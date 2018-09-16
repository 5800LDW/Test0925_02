package kld.com.rfid.ldw;

import com.ldw.xyz.util.PreferenceUtil;

/**
 * Created by liudongwen on 2018/8/23.
 */

public class Const {



    public static final boolean IsCanRecordEpcID = true;
    public static final boolean IsCanTest = true;

    public static final String KEY_URL_SERVER = "$_url";
    public static final String KEY_STAGE_NMAE = "$_STAGE_NMAE";
    public static final String KEY_STAGE_CODE = "$_KEY_STAGE_CODE";
    public static final String KEY_ORGANIZATION = "$_KEY_ORGANIZATION";

    public static final int KEY_SCAN_BUTTON = 261;

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


    public static String getURL_MATERIALS(){
        return getURL_BASE() + "/organization/materials";
    }


    public static String getURL_ORG_ID(String id){
        return getURL_BASE() + "/wmsrfidstagev/materials?ORG_ID="+id;
    }

    public static String getURL_DownloadAPK(){
//        return getURL_BASE() ;//TODO
        return "http://test-1251233192.coscd.myqcloud.com/1_1.apk";
    }

    public static String getURL_Version(){
//        return getURL_BASE() ;//TODO
        return "https://www.baidu.com";
    }

}
