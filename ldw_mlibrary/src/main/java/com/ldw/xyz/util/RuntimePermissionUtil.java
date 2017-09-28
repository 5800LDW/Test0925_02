package com.ldw.xyz.util;

import android.Manifest;

import com.ldw.xyz.activity.BaseActivity;
import com.ldw.xyz.listener.PermissionListener;
import com.ldw.xyz.util.array.ArrayUtil;

/**
 * 测试是否已经获得权限
 * Created by LDW10000000 on 29/12/2016.
 */
//使用方法:
//   if(Build.VERSION.SDK_INT>=23){
//         RuntimePermissionUtil.test(new String[]{ Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.RECORD_AUDIO
//         },
//         new PermissionListener(){
//            @Override
//          public void onGranted(){}
//            @Override
//          public void onDenied(List<String> deniedPermissions){
//   }
public class RuntimePermissionUtil {
    public static void test(String[] permissions, PermissionListener listener) {
        BaseActivity.requestRuntimePermission(permissions, listener);
    }


    /**
     * 对申请多个危险权限的数组进行合并
     *
     * @param first
     * @param rest
     * @param <T>
     * @return
     */
    public static <T> T[] concatAll(T[] first, T[]... rest) {
        return ArrayUtil.concatAll(first,rest);
    }

    //group:android.permission-group.CONTACTS
    public static String[] getContactsPermissionList() {
        return new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.GET_ACCOUNTS
        };
    }

    //group:android.permission-group.PHONE
    public static String[] getPhonePermissionList() {
        return new String[]{
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.USE_SIP,
                Manifest.permission.PROCESS_OUTGOING_CALLS,
                Manifest.permission.ADD_VOICEMAIL
        };
    }

    //group:android.permission-group.CALENDAR
    public static String[] getCalendarPermissionList() {
        return new String[]{
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR
        };
    }

    //group:android.permission-group.CAMERA
    public static String[] getCameraPermissionList() {
        return new String[]{
                Manifest.permission.CAMERA
        };
    }

    //group:android.permission-group.SENSORS
    public static String[] getSensorsPermissionList() {
        return new String[]{
                Manifest.permission.BODY_SENSORS
        };
    }


    //group:android.permission-group.LOCATION
    public static String[] getLocationPermissionList() {
        return new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
    }

    //group:android.permission-group.STORAGE
    public static String[] getStoragePermissionList() {
        return new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }

    //group:android.permission-group.MICROPHONE
    public static String[] getMicrophonePermissionList() {
        return new String[]{
                Manifest.permission.RECORD_AUDIO
        };
    }

    //group:android.permission-group.SMS
    public static String[] getSMSPermissionList() {
        return new String[]{
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_WAP_PUSH,
                Manifest.permission.RECEIVE_MMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS
        };
    }


}




