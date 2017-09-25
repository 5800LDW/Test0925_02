package com.test;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.antonioleiva.mvpexample.app.R;
import com.ldw.lib.biz.download2.DownloadManager;
import com.ldw.lib.biz.download2.DownloadRunnable;
import com.ldw.xyz.activity.BaseActivity;
import com.ldw.xyz.util.ToastUtil;
import com.ldw.xyz.util.file.MyFileUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LDW10000000 on 30/08/2017.
 */

public class TestMyRunnableActivity extends BaseActivity implements View.OnClickListener {

    private Button downloadBtn1, downloadBtn2, downloadBtn3;
    private Button cancelBtn1, cancelBtn2, cancelBtn3;
    private ProgressBar progress1, progress2, progress3;
    private String url1 = "http://101.28.249.94/apk.r1.market.hiapk.com/data/upload/apkres/2017/4_11/15/com.baidu.searchbox_034250.apk";
    private String url2 = "http://softfile.3g.qq.com:8080/msoft/179/24659/43549/qq_hd_mini_1.4.apk";
    private String url3 = "http://www.zhaoshangdai.com/file/android.apk";

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_breakpointresume);
    }

    @Override
    public void findViews() {

        downloadBtn1 = (Button) findViewById(R.id.main_btn_down1);
        downloadBtn2 = (Button) findViewById(R.id.main_btn_down2);
        downloadBtn3 = (Button) findViewById(R.id.main_btn_down3);

        cancelBtn1 = (Button) findViewById(R.id.main_btn_cancel1);
        cancelBtn2 = (Button) findViewById(R.id.main_btn_cancel2);
        cancelBtn3 = (Button) findViewById(R.id.main_btn_cancel3);

        progress1 = (ProgressBar) findViewById(R.id.main_progress1);
        progress2 = (ProgressBar) findViewById(R.id.main_progress2);
        progress3 = (ProgressBar) findViewById(R.id.main_progress3);

        downloadBtn1.setOnClickListener(this);
        downloadBtn1.setText("下载");
        downloadBtn2.setOnClickListener(this);
        downloadBtn3.setOnClickListener(this);

        cancelBtn1.setOnClickListener(this);
        cancelBtn2.setOnClickListener(this);
        cancelBtn3.setOnClickListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void showConent() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.main_btn_down1:
                if(downloadBtn1.getText().equals("暂停")){
                    DownloadManager.INSTANCE.pauseDownload(url1);
                    downloadBtn1.setText("下载");
                }
                else{
                    download();
                    downloadBtn1.setText("暂停");
                }

                break;
            case R.id.main_btn_down2:

                break;
            case R.id.main_btn_down3:
                break;
            case R.id.main_btn_cancel1:
                DownloadManager.INSTANCE.cancelDownload(url1);

                break;
            case R.id.main_btn_cancel2:
                break;
            case R.id.main_btn_cancel3:
                break;


        }

    }

    private  void download(){
        DownloadManager.INSTANCE.download(new DownloadRunnable(new DownloadRunnable.DownloadListener() {
            @Override
            public void onProgress(int progress) {
                progress1.setProgress(progress);
            }

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(TestMyRunnableActivity.this,"下载成功!");
                    }
                });
            }

            @Override
            public void onFailed(@Nullable IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(TestMyRunnableActivity.this,e+"");
                    }
                });
            }

            @Override
            public void onPaused() {
                downloadBtn1.setText("下载");
            }

            @Override
            public void onCanceled() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(TestMyRunnableActivity.this,"取消下载成功!");
                    }
                });
            }

            @Nullable
            @Override
            public String downloadName() {
                return 1.1 + ".apk";
            }

            @Nullable
            @Override
            public String downloadDirectory() {
                return MyFileUtil.getMyExternalStorageDirectory().toString() + MyFileUtil.separator + "Test20170831"+MyFileUtil.separator;
            }

            @NonNull
            @Override
            public String downloadUrl() {
                return url1;
            }
        }) {});
    }



}
