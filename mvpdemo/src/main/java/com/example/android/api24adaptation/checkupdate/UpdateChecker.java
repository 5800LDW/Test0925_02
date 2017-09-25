package com.example.android.api24adaptation.checkupdate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.example.android.api24adaptation.common.MsgDialog;
import com.example.android.api24adaptation.common.NetworkUtils;

public class UpdateChecker extends Fragment {

    private FragmentActivity mContext;
    private MsgDialog msgDialog;
    private MsgDialog   ensuremsgDialog;
    /**
     * Show a Dialog if an update is available for download. Callable in a
     * FragmentActivity. Number of checks after the dialog will be shown:
     * default, 5
     *
     * @param fragmentActivity Required.
     */
    public static void checkForDialog(FragmentActivity fragmentActivity) {
        FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
        UpdateChecker updateChecker = new UpdateChecker();
        content.add(updateChecker, null).commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (FragmentActivity) activity;
        //请求最新apk的信息
        checkForUpdates();
    }

    /**
     * Heart of the library. Check if an update is available for download
     * parsing the desktop Play Store page of the app
     */
    private void checkForUpdates() {
        //开始请求接口信息
        //...
        //假设请求返回提示更新
        showDialog("测试更新，假设请求返回，apk测试地址为网易新闻apk的下载（随机选取的）", "http://file.ws.126.net/3g/client/netease_newsreader_android.apk");


    }

    /**
     * Show dialog
     */
    public void showDialog(String content, final String apkUrl) {

        msgDialog = new MsgDialog(getActivity(),"发现新版本" , content, new MsgDialog.SubmitListener() {
            @Override
            public void onClick() {
                if (NetworkUtils.isWifi(getActivity())){
                    goToDownload(apkUrl);
                }else{
                    showEnsureDialog(apkUrl);
                }
                msgDialog.dismiss();

            }
        },new MsgDialog.CanclListener(){

            @Override
            public void onClick() {
                msgDialog.dismiss();
            }
        });
        msgDialog.setCancelable(false);
        msgDialog.show();
    }

    private void showEnsureDialog(final String apkUrl) {
        ensuremsgDialog = new MsgDialog(getActivity(),"温馨提示" , "您现在处于非WiFi条件下，确定要下载吗？", new MsgDialog.SubmitListener() {
            @Override
            public void onClick() {
                goToDownload(apkUrl);
                ensuremsgDialog.dismiss();
            }
        }, new MsgDialog.CanclListener() {
            @Override
            public void onClick() {
                ensuremsgDialog.dismiss();
            }
        });
        ensuremsgDialog.show();
    }

    private void goToDownload(final String apkUrl) {

        //权限申请
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(mContext,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        Intent intent=new Intent(getActivity().getApplicationContext(),DownloadService.class);
                        intent.putExtra("url", apkUrl);
                        getActivity().startService(intent);

                    }

                    @Override
                    public void onDenied(String permission) {
                        Toast.makeText(mContext, "获取权限失败，请点击后允许获取", Toast.LENGTH_SHORT).show();
                    }
                }, true);



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

}
