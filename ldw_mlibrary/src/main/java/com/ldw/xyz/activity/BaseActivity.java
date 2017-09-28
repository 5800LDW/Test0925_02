package com.ldw.xyz.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.ldw.xyz.collector.ActivityCollector;
import com.ldw.xyz.listener.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {
    private Receiver receiver;
    protected LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
        ActivityCollector.addActivity(this);
        init();

    }

    private void init() {
        setContentView();
        findViews();
        getData();
        showConent();

        new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        };

    }

    public abstract void setContentView();

    public abstract void findViews();

    public abstract void getData();

    public abstract void showConent();

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new Receiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("ExitApp");
        getApplicationContext().registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);

        try {
            getApplicationContext().unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            exitApp();
        }
    }

    protected void exitApp() {
        this.finish();
    }


    private static PermissionListener mListener;

    public static void requestRuntimePermission(String[] permissions, PermissionListener listener) {
        Activity topActivity = ActivityCollector.getTopActivity();
        if (topActivity == null) {
            return;
        }
        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(topActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
//		if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
//			permissionList.add(Manifest.permission.CALL_PHONE);
//		}
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(topActivity, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            mListener.onGranted();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;

        }
    }
}
