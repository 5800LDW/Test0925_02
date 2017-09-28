package com.ldw.xyz.util.network.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * Created by LDW10000000 on 05/09/2017.
 */

public class MyNetWorkReceiver extends BroadcastReceiver {

    public interface NetWorkConnectivityListener{

        void connectivityChangeCallBacks(boolean wifiIsConnected , boolean mobileIsConnected);

    }

    NetWorkConnectivityListener listener;

    public MyNetWorkReceiver(NetWorkConnectivityListener lis){
        listener = lis;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean wifiIsConnected = false;
        boolean mobileIsConnected = false;

        //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            wifiIsConnected = wifiNetworkInfo.isConnected();
            mobileIsConnected = dataNetworkInfo.isConnected();

            listener.connectivityChangeCallBacks(wifiIsConnected,mobileIsConnected);

        }else {
            //("API level 大于21"
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //通过循环将网络信息逐个取出来
            for (int i=0; i < networks.length; i++){
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                if(networkInfo.getTypeName().equals("WIFI")){
                    wifiIsConnected = networkInfo.isConnected();
                }
                else if(networkInfo.getTypeName().equals("MOBILE")){
                    mobileIsConnected =  networkInfo.isConnected();
                }
            }
            listener.connectivityChangeCallBacks(wifiIsConnected,mobileIsConnected);



//            //这里的就不写了，前面有写，大同小异
//            System.out.println("API level 大于21");
//            //获得ConnectivityManager对象
//            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            //获取所有网络连接的信息
//            Network[] networks = connMgr.getAllNetworks();
//            //用于存放网络连接信息
//            StringBuilder sb = new StringBuilder();
//            //通过循环将网络信息逐个取出来
//            for (int i=0; i < networks.length; i++){
//                //获取ConnectivityManager对象对应的NetworkInfo对象
//                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
//                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
//            }
//            Toast.makeText(context, sb.toString(),Toast.LENGTH_SHORT).show();
        }
    }







}
