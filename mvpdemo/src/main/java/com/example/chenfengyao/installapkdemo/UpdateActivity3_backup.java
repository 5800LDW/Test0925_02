//package com.example.chenfengyao.installapkdemo;
//
//import android.content.ComponentName;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.Switch;
//import android.widget.Toast;
//
//import com.antonioleiva.mvpexample.app.R;
//import com.ldw.lib.biz.download.NotificationService;
//
//import java.util.concurrent.TimeUnit;
//
//import io.reactivex.Observable;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//
///**
// * Created by LDW10000000 on 10/08/2017.
// */
//
//public class UpdateActivity3_backup extends AppCompatActivity {
//
//    private static final String APK_URL = "http://101.28.249.94/apk.r1.market.hiapk.com/data/upload/apkres/2017/4_11/15/com.baidu.searchbox_034250.apk";
//    private Switch installModeSwitch;
//    private ProgressBar mProgressBar;
//    private Button mDownBtn;
//    private NotificationService.DownloadBinder mDownloadBinder;
//    private Disposable mDisposable;//可以取消观察者
//
//    private ServiceConnection mConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mDownloadBinder = (NotificationService.DownloadBinder) service;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mDownloadBinder = null;
//        }
//    };
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update3);
//
//        installModeSwitch = (Switch) findViewById(R.id.install_mode_switch);
//        mProgressBar = (ProgressBar) findViewById(R.id.down_progress);
//        mDownBtn = (Button) findViewById(R.id.down_btn);
//
//        Intent intent = new Intent(this, NotificationService.class);
//        startService(intent);
//        bindService(intent, mConnection, BIND_AUTO_CREATE);//绑定服务
//
//
//        installModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                buttonView.setText("root模式");
//            } else {
//                buttonView.setText("普通模式");
//            }
//            if (mDownloadBinder != null) {
//                mDownloadBinder.setInstallMode(isChecked);
//            }
//        });
//
//        mDownBtn.setOnClickListener(v -> {
//            if (mDownloadBinder != null) {
//                long downloadId = mDownloadBinder.startDownload(APK_URL,"myApkName.apk");
//                startCheckProgress(downloadId);
//                Toast.makeText(this, "mDownloadBinder!= null", Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Toast.makeText(this, "mDownloadBinder是null", Toast.LENGTH_SHORT).show();
//            }
//
//
//        });
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (mDisposable != null) {
//            //取消监听
//            mDisposable.dispose();
//        }
//        super.onDestroy();
//    }
//
//    //开始监听进度
//    private void startCheckProgress(long downloadId) {
//        Observable
//                .interval(100, 200, TimeUnit.MILLISECONDS, Schedulers.io())//无限轮询,准备查询进度,在io线程执行
//                .filter(times -> mDownloadBinder != null)
//                .map(i -> mDownloadBinder.getProgress(downloadId))//获得下载进度
//                .takeUntil(progress01 -> progress01 >= 100)//返回true就停止了,当进度>=100就是下载完成了
//                .distinct()//去重复
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ProgressObserver());
//    }
//
//
//    //观察者
//    private class ProgressObserver implements Observer<Integer> {
//
//        @Override
//        public void onSubscribe(Disposable d) {
//            mDisposable = d;
//        }
//
//        @Override
//        public void onNext(Integer progress01) {
//            mProgressBar.setProgress(progress01);//设置进度
//        }
//
//        @Override
//        public void onError(Throwable throwable) {
//            throwable.printStackTrace();
//            Toast.makeText(UpdateActivity3_backup.this, "出错", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onComplete() {
//            mProgressBar.setProgress(100);
//            Toast.makeText(UpdateActivity3_backup.this, "下载完成", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//}