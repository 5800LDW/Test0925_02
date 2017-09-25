//package com.example.chenfengyao.installapkdemo;
//
//import android.content.ComponentName;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.Switch;
//import android.widget.Toast;
//
//import com.antonioleiva.mvpexample.app.R;
//import com.ldw.lib.biz.download.UseDownLoadManagerActivity;
//import com.ldw.lib.biz.download.NotificationService;
//
//import io.reactivex.disposables.Disposable;
//
///**
// * Created by LDW10000000 on 10/08/2017.
// */
//
//public class UpdateActivity3 extends UseDownLoadManagerActivity {
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
//                long downloadId = mDownloadBinder.startDownload(APK_URL,"myApkName.apk",null);
//                startCheckProgress(downloadId);
//                Toast.makeText(this, "mDownloadBinder!= null", Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Toast.makeText(this, "mDownloadBinder是null", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public String setFilePathsAuthorities() {
//        return null;
//    }
//
//    @Override
//    public void setContentView() {
//
//    }
//
//    @Override
//    public void findViews() {
//
//    }
//
//    @Override
//    public void getData() {
//
//    }
//
//    @Override
//    public void showConent() {
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
////    //开始监听进度
////    private void startCheckProgress(long downloadId) {
////        Observable
////                .interval(100, 200, TimeUnit.MILLISECONDS, Schedulers.io())//无限轮询,准备查询进度,在io线程执行
////                .filter(times -> mDownloadBinder != null)
////                .map(i -> mDownloadBinder.getProgress(downloadId))//获得下载进度
////                .takeUntil(progress01 -> progress01 >= 100)//返回true就停止了,当进度>=100就是下载完成了
////                .distinct()//去重复
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new ProgressObserver());
////    }
////
////
////    //观察者
////    private class ProgressObserver implements Observer<Integer> {
////
////        @Override
////        public void onSubscribe(Disposable d) {
////            mDisposable = d;
////        }
////
////        @Override
////        public void onNext(Integer progress01) {
////            mProgressBar.setProgress(progress01);//设置进度
////        }
////
////        @Override
////        public void onError(Throwable throwable) {
////            throwable.printStackTrace();
////            Toast.makeText(UpdateActivity3.this, "出错", Toast.LENGTH_SHORT).show();
////        }
////
////        @Override
////        public void onComplete() {
////            mProgressBar.setProgress(100);
////            Toast.makeText(UpdateActivity3.this, "下载完成", Toast.LENGTH_SHORT).show();
////        }
////    }
//
//}









//
package com.example.chenfengyao.installapkdemo;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.antonioleiva.mvpexample.app.R;
import com.ldw.lib.biz.download1.UseDownLoadManagerActivity;

/**
 * Created by LDW10000000 on 10/08/2017.
 */

public class UpdateActivity3 extends UseDownLoadManagerActivity {

    private static final String APK_URL = "http://101.28.249.94/apk.r1.market.hiapk.com/data/upload/apkres/2017/4_11/15/com.baidu.searchbox_034250.apk";
    private Switch installModeSwitch;
    private ProgressBar mProgressBar;
    private Button mDownBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update3);

        installModeSwitch = (Switch) findViewById(R.id.install_mode_switch);
        mProgressBar = (ProgressBar) findViewById(R.id.down_progress);
        mDownBtn = (Button) findViewById(R.id.down_btn);

        mDownBtn.setOnClickListener(v -> {
//            if (mDownloadBinder != null) {
//                long downloadId = mDownloadBinder.startDownload(APK_URL,"myApkName.apk");
//                startCheckProgress(downloadId);
//                Toast.makeText(this, "mDownloadBinder!= null", Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Toast.makeText(this, "mDownloadBinder是null", Toast.LENGTH_SHORT).show();
//            }
            startDownload(false, APK_URL, "test",null);
            showProgress();
        });


//        startDownload(false, APK_URL, "test", new downloadStateImpl() {
//            @Override
//            public void onNext(Integer progress01) {
//                mProgressBar.setProgress(progress01);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                ToastUtil.showToast(UpdateActivity3.this,"发生错误"+ throwable.toString());
//            }
//
//            @Override
//            public void onComplete() {
//                mProgressBar.setProgress(100);
//                ToastUtil.showToast(UpdateActivity3.this,"下载完成");
//
//            }
//        });





    }

    Handler mHandler = new Handler();

    private  void showProgress(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int progress = getProgress();
                if(progress<100){
                    mProgressBar.setProgress(progress);
                    showProgress();
                }
                else{
                    mProgressBar.setProgress(100);
                }

            }
        },100);
    }

    @Override
    public void setContentView() {

    }

    @Override
    public void findViews() {

    }

    @Override
    public void getData() {

    }

    @Override
    public void showConent() {

    }

}
