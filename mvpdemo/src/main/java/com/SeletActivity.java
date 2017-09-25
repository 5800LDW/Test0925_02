package com;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.antonioleiva.mvpexample.app.CameraActivity;
import com.antonioleiva.mvpexample.app.Login.LoginActivity;
import com.antonioleiva.mvpexample.app.R;
import com.antonioleiva.mvpexample.app.WebViewActivity;
import com.antonioleiva.mvpexample.app.main.RecordStartActivity;
import com.chaychan.androidnadaption.CameraActivity2;
import com.devices.DevicesInfoActivity;
import com.example.android.api24adaptation.CameraActivity3;
import com.example.chenfengyao.installapkdemo.UpdateActivity3;
import com.example.exmassets.AssetsActivity;
import com.example.notifications.NotificationActivity2;
import com.example.servicebestpractice.BreakPointResumeActivity2;
import com.example.wsj.splashdemo.activities.SplashActivity;
import com.fragment.TestFragmentActivity;
import com.fragment2.TestBackFragmentActivity;
import com.github.phoenix.activity.UpdateActivity4;
import com.hss01248.notifyutildemo.NotificationActivity;
import com.lanou3g.downdemo.BreakPointResumeActivity;
import com.ldw.xyz.util.ActivityUtil;
import com.ldw.xyz.util.ToastUtil;
import com.my.phonesingle.SignActivity;
import com.test.TestActvity;
import com.test.TestMyRunnableActivity;
import com.test.honeywell.HoneWellTestActivity;
import com.test.so.TestSOActivity;
import com.ywj.svgdemo.SVGActivity1;

import scut.carson_ho.check_net.CheckNetWorkActivity;

/**
 * Created by LDW10000000 on 31/07/2017.
 */

public class SeletActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        findViewById(R.id.bt_DevicesInfo).setOnClickListener(this);
        findViewById(R.id.bt_Fruit).setOnClickListener(this);
        findViewById(R.id.bt_RecordStart).setOnClickListener(this);
        findViewById(R.id.bt_Camera).setOnClickListener(this);
        findViewById(R.id.bt_Camera2).setOnClickListener(this);
        findViewById(R.id.bt_Camera3).setOnClickListener(this);

        findViewById(R.id.bt_webView).setOnClickListener(this);
        findViewById(R.id.bt_Splash).setOnClickListener(this);
        findViewById(R.id.bt_Fragment).setOnClickListener(this);
        findViewById(R.id.bt_TestFragmentBack).setOnClickListener(this);
        findViewById(R.id.bt_CommonAdapter).setOnClickListener(this);
        findViewById(R.id.bt_Update).setOnClickListener(this);
        findViewById(R.id.bt_Update2).setOnClickListener(this);
        findViewById(R.id.bt_Update3).setOnClickListener(this);
        findViewById(R.id.bt_Update4).setOnClickListener(this);
        findViewById(R.id.bt_notification).setOnClickListener(this);
        findViewById(R.id.bt_notification2).setOnClickListener(this);
        findViewById(R.id.bt_assets).setOnClickListener(this);
        findViewById(R.id.bt_test).setOnClickListener(this);
        findViewById(R.id.bt_BreakpointResume).setOnClickListener(this);
        findViewById(R.id.bt_BreakpointResume2).setOnClickListener(this);
        findViewById(R.id.bt_RxDownload).setOnClickListener(this);
        findViewById(R.id.bt_ThreadPool).setOnClickListener(this);
        findViewById(R.id.bt_Thread).setOnClickListener(this);
        findViewById(R.id.bt_TestMyRunnable).setOnClickListener(this);
        findViewById(R.id.bt_CheckNetWork).setOnClickListener(this);
        findViewById(R.id.bt_SVG1).setOnClickListener(this);

        findViewById(R.id.bt_WifiSign).setOnClickListener(this);

        findViewById(R.id.bt_TestSO).setOnClickListener(this);

        findViewById(R.id.bt_HoneyWellTest).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_DevicesInfo:
                ActivityUtil.startActivity(this, DevicesInfoActivity.class, null);
                break;
            case R.id.bt_Fruit:
                ActivityUtil.startActivity(this, LoginActivity.class, null);
                break;
            case R.id.bt_RecordStart:
                ActivityUtil.startActivity(this, RecordStartActivity.class, null);
                break;
            case R.id.bt_Camera:
                ActivityUtil.startActivity(this, CameraActivity.class, null);
                break;
            case R.id.bt_Camera2:
                ActivityUtil.startActivity(this, CameraActivity2.class, null);
                break;
            case R.id.bt_webView:
                ActivityUtil.startActivity(this, WebViewActivity.class, null);
                break;
            case R.id.bt_Splash:
                ActivityUtil.startActivity(this, SplashActivity.class, null);
                break;
            case R.id.bt_Fragment:
                ActivityUtil.startActivity(this, TestFragmentActivity.class, null);
                break;

            case R.id.bt_TestFragmentBack:
                ActivityUtil.startActivity(this, TestBackFragmentActivity.class, null);
                break;
            case R.id.bt_CommonAdapter:
                ActivityUtil.startActivity(this, com.zhy.sample.MainActivity.class, null);
                break;
            case R.id.bt_Update:
                ActivityUtil.startActivity(this, com.tutor.update.UpadateActivity.class, null);
                break;

            case R.id.bt_Update2:
                ActivityUtil.startActivity(this, com.tutor.update2.UpdateActivity2.class, null);
                break;

            case R.id.bt_Update3:
                ActivityUtil.startActivity(this, UpdateActivity3.class, null);
                break;

            case R.id.bt_Update4:
                ActivityUtil.startActivity(this, UpdateActivity4.class, null);
                break;

            case R.id.bt_notification:
                ActivityUtil.startActivity(this, NotificationActivity.class, null);
                break;

            case R.id.bt_notification2:
                ActivityUtil.startActivity(this, NotificationActivity2.class, null);
                break;

            case R.id.bt_assets:
                ActivityUtil.startActivity(this, AssetsActivity.class, null);
                break;

            case R.id.bt_Camera3:
                ActivityUtil.startActivity(this, CameraActivity3.class, null);
                break;

            case R.id.bt_test:
                ActivityUtil.startActivity(this, TestActvity.class, null);
                break;

            case R.id.bt_BreakpointResume:
                ActivityUtil.startActivity(this, BreakPointResumeActivity.class, null);
                break;

            case R.id.bt_BreakpointResume2:
                ActivityUtil.startActivity(this, BreakPointResumeActivity2.class, null);
                break;
            case R.id.bt_RxDownload:
//                ActivityUtil.startActivity(this, RxDownloadActivity.class, null);
                ToastUtil.showToast(this,"暂时没有内容");
                break;

            case R.id.bt_ThreadPool:

                break;

            case R.id.bt_Thread:
                break;

            case R.id.bt_TestMyRunnable:
                ActivityUtil.startActivity(this, TestMyRunnableActivity.class, null);
                break;

            case R.id.bt_CheckNetWork:
                ActivityUtil.startActivity(this, CheckNetWorkActivity.class,null);
                break;

            case R.id.bt_WifiSign:
                ActivityUtil.startActivity(this, SignActivity.class,null);
                break;

            case R.id.bt_SVG1:
                ActivityUtil.startActivity(this, SVGActivity1.class,null);
                break;

            case R.id.bt_TestSO:
                ActivityUtil.startActivity(this, TestSOActivity.class,null);
                break;

            case R.id.bt_HoneyWellTest:
                ActivityUtil.startActivity(this, HoneWellTestActivity.class,null);
                break;



        }
    }
}
