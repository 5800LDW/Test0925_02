package com.ldw.xyz.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by LDW10000000 on 30/03/2017.
 */

public class DBluetoothDevice {
    BluetoothAdapter myBluetoothAdapter;

    public static String DeviceName = "DeviceName";
    public static String BDAddress = "BDAddress";
    /**
     * 第一步: 判断蓝牙设备是不是存在(设置是不是支持蓝牙)
     * @param context
     * @return
     */
    public boolean judgeDevicesExist(Context context){
        if((myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter())==null) {
//            Toast.makeText(context,"没有找到蓝牙适配器", Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * 第二步: 判断蓝牙设备是否开启
     * @param activity
     * @return
     */
    public boolean doOpen(Activity activity){
        if(!myBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, 2);
        }

        return  myBluetoothAdapter.isEnabled();
    }


    /**
     * 第三步: 拿到已经配对蓝牙设备的名称和地址
     * @return
     */
    public List<Map<String,String>> searchAlreadyPaired(){

        List<Map<String,String>> list=new ArrayList<>();
        Set<android.bluetooth.BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() <= 0){
            return null;
        }

        for (android.bluetooth.BluetoothDevice device : pairedDevices)
        {
            Map<String,String> map=new HashMap<>();
            map.put(DeviceName, device.getName());
            map.put(BDAddress, device.getAddress());
            list.add(map);
        }

        return  list;
    }

//    public void search


    public static BluetoothAdapter getDefaultAdapter(){

        return  BluetoothAdapter.getDefaultAdapter();

    }





}
