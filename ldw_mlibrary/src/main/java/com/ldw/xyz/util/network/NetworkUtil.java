package com.ldw.xyz.util.network;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.network.receiver.MyNetWorkReceiver;

public class NetworkUtil {

    @Deprecated
    public static void checkNetworkState(final Context context) {
        try {
            //判断有没有网
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                //没网显示一个dialog
                Builder dialog = new Builder(context);
                dialog.setMessage("亲，现在没网");
                //打开网络
                dialog.setPositiveButton("打开网络", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //回调 callback
                        try {
                            //判断android的版本
                            int version = android.os.Build.VERSION.SDK_INT;
                            if (version > 10) {
                                Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                context.startActivity(intent);
                                dialog.cancel();
                            }
                        } catch (Exception e) {
                            ExceptionUtil.handleException(e);
                        }
                    }
                });
                dialog.setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
    }


    /**
     * 跟unRegisterNetWorkStateReceiver配套使用
     *
     * @param context
     * @param listener 一旦连接的状态发生改变,
     * @return MyNetWorkReceiver 调用
     */
    public static MyNetWorkReceiver registerNetWorkConnectivityReceiver(Context context, MyNetWorkReceiver.NetWorkConnectivityListener listener) {
        MyNetWorkReceiver netWorkStateReceiver = new MyNetWorkReceiver(listener);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(netWorkStateReceiver, filter);
        return netWorkStateReceiver;
    }

    /**
     * 跟registerNetWorkStateReceiver配套使用
     *
     * @param context
     * @param netWorkStateReceiver
     */
    public static void unRegisterNetWorkConnectivityReceiver(Context context, MyNetWorkReceiver netWorkStateReceiver) {
        context.unregisterReceiver(netWorkStateReceiver);
    }


    /**
     * 检测wifi是否连接了
     * @param context
     * @return
     */
    public static boolean wifiIsConnect(Context context) {
        //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiNetworkInfo.isConnected();
        } else {
            //("API level 大于21"
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            boolean wifiIsConnected = false;
            //通过循环将网络信息逐个取出来
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                if (networkInfo.getTypeName().equals("WIFI")) {
                    wifiIsConnected = networkInfo.isConnected();
                    break;
                }
            }
            return wifiIsConnected;
        }
    }

    /**
     * 检测移动数据是否连接了;
     *
     * @param context
     * @return
     */
    public static boolean mobileIsConnected(Context context) {
        //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return dataNetworkInfo.isConnected();
        } else {
            //("API level 大于21"
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            boolean mobileIsConnected = false;
            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //通过循环将网络信息逐个取出来
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                if (networkInfo.getTypeName().equals("MOBILE")) {
                    mobileIsConnected = networkInfo.isConnected();
                    break;
                }
            }
            return mobileIsConnected;
        }
    }


}
