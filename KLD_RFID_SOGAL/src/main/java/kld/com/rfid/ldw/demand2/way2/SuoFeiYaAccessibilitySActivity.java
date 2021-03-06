package kld.com.rfid.ldw.demand2.way2;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.UHFDemo_install.Sub3TabActivity;
import com.example.UHFDemo_install.Sub4TabActivity;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.PreferenceUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.ToastUtil;
import com.ldw.xyz.util.exception.ExceptionUtil;
import com.ldw.xyz.util.string.StringHelper2;
import com.maning.mndialoglibrary.MProgressBarDialog;
import com.uhf.uhf.Common.Comm;
import com.uhf.uhf.UHF1.UHF1Function.AndroidWakeLock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.R;
import kld.com.rfid.ldw.RFIDApplication;
import kld.com.rfid.ldw.SuoFeiYaAdapter;
import kld.com.rfid.ldw.SuoFeiYaBaseActivity;
import kld.com.rfid.ldw.demand2.CheckSwitchButton;
import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

import static com.example.UHFDemo_install.Sub4TabActivity.tabHost_set;
import static com.uhf.uhf.Common.Comm.Awl;
import static com.uhf.uhf.Common.Comm.isQuick;
import static com.uhf.uhf.Common.Comm.tagListSize;

//总共读到的标签个数

//import com.example.UHFDemo_install.MyAdapter;


//import com.supoin.wireless.WirelessManager;

@SuppressWarnings("deprecation")
public class SuoFeiYaAccessibilitySActivity extends SuoFeiYaBaseActivity {
    //liudongwen
    Set<String> uploadSet;
    SuoFeiYaAdapter adapter;
    //    protected Object cancelTag = this;
    private String strMsg = "";
    protected Object cancelTag = this;


    TextView tv_state, tv_tags, textView_time;
    Button button_read, button_stop, button_clean, button_set, butSetUrl, btnUpload;
    CheckBox cb_is6btag, cb_readtid;
    private ListView listView;
    TabHost tabHost;
    public long exittime;
    List<Byte> LB = new ArrayList();
    int scanCode = 0;
    //    private Timer timer = null;
//    private TimerTask task = null;
    private Message msg = null;
    private long mlCount = 0;
    //liudongwen , 原本是10 ,我改为50
//    private long mlTimerUnit = 10;
    private int totalSec, yushu, min, sec;
//    private String strMsg = "";

    TabWidget tw;
    Context mContext;

    Receiver receiver;
    ReceiverStopDialog receiverStopDialog;

    @Override
    protected void onStart() {
        super.onStart();
//        InitDevice();
        Log.d("Activity", "onStart()");


        if (true) {
            //liudognwen 更新界面
            receiver = new Receiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("$_UpdateAdapter");
            getApplicationContext().registerReceiver(receiver, filter);
        }


//        uiFresh();


        if (true) {
            receiverStopDialog = new ReceiverStopDialog();
            IntentFilter filter2 = new IntentFilter();
            filter2.addAction("$_CloseDialog");
            getApplicationContext().registerReceiver(receiverStopDialog, filter2);
        }
    }

//    @Override
//    public void onStop() {
//        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
//
//
//        button_stop.performClick();
//
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
////        release();
//        Log.d("Activity","onStop()");
//    }
//
//    public class MyEpListAdapter extends ArrayAdapter<Object> {
//        @SuppressWarnings("unchecked")
//        public MyEpListAdapter(Context context, int resource,
//                               int textViewResourceId,
//                               @SuppressWarnings("rawtypes") List objects) {
//            super(context, resource, textViewResourceId, objects);
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        OkHttpUtils.getInstance().cancelTag(cancelTag);
        //避免内存泄露
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
//        if(connectH!=null){
//            connectH.removeCallbacksAndMessages(null);
//        }
//        if(uhfhandler!=null){
//            uhfhandler.removeCallbacksAndMessages(null);
//        }
//        if(handlerTimerTask!=null){
//            handlerTimerTask.removeCallbacksAndMessages(null);
//        }

        //liudongwen
//        release();

        try {
            getApplicationContext().unregisterReceiver(receiver);
            getApplicationContext().unregisterReceiver(receiverStopDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//
//    @SuppressLint("HandlerLeak") //todo
//    public Handler uhfhandler = new Handler() {
//        @SuppressWarnings({"unchecked", "unused"})
//        @Override
//        public void handleMessage(Message msg) {
//            try {
//                if (Comm.is6BTag)
//                    tagListSize = lsTagList6B.size();
//                else
//                    tagListSize = lsTagList.size();
//                Bundle bd = msg.getData();
//
//                int readCount = bd.getInt("readCount");
//
//
//                if (readCount > 0)
//                    tv_state.setText(String.valueOf(readCount));
//
////                if (tagListSize > 0 && (System.currentTimeMillis() - exittime) > 2000) {
////                    showlist();
////                    exittime = System.currentTimeMillis();
////                }
//                if (tagListSize > 0)
//                   showlist();
//                Log.e("uhfhandler", "readCount : " + String.valueOf(readCount));
//            } catch (Exception e) {
//                e.printStackTrace();
//                ExceptionUtil.handleException(e);
//                LogUtil.e("Tag", e.getMessage());
//            }
//        }
//    };


//
//
//    @SuppressLint("HandlerLeak")
//    public Handler connectH = new Handler() {
//        @SuppressLint("SetTextI18n")
//        @SuppressWarnings({"unchecked", "unused"})
//        @Override
//        public void handleMessage(Message msg) {
//            try {
//                if (Comm.moduleType == Comm.Module.UHF005)
//                    cb_is6btag.setEnabled(true);
//                else
//                    cb_is6btag.setEnabled(false);
////                UHF001.mhandler = uhfhandler;
////                if (null != rfidOperate) {
////                    rfidOperate.mHandler = uhfhandler;
////                    cb_is6btag.setEnabled(true);
////                }
////                if (null != Comm.uhf6)
////                    Comm.uhf6.UHF6handler = uhfhandler;
//                Comm.mInventoryHandler = uhfhandler;
//
//                Bundle bd = msg.getData();
//                strMsg = bd.get("Msg").toString();
//                if (!TextUtils.isEmpty(strMsg)) {
//                    tv_state.setText(strMsg);
//                    Comm.SetInventoryTid(cb_readtid.isChecked());
//
//                } else
//                    tv_state.setText(R.string.device_msg);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("connectH", e.getMessage());
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setTheme(R.style.MyAppTheme);
        setContentView(R.layout.activity_main_suofeiya_demand2_);

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabSpec tp = tabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.ReadTags))
                .setContent(R.id.tab2);
        tabHost.addTab(tp);

        tabHost.addTab(tabHost
                .newTabSpec("tab2")
                .setIndicator(getResources().getString(R.string.RWLock))
                .setContent(new Intent(this, Sub3TabActivity.class)));

        tabHost.addTab(tabHost
                .newTabSpec("tab3")
                .setIndicator(getResources().getString(R.string.Set_title))
                .setContent(new Intent(this, Sub4TabActivity.class)));

        Comm.supoinTabHost = tabHost;
        tw = Comm.supoinTabHost.getTabWidget();

        button_read = (Button) findViewById(R.id.button_start);
        button_stop = (Button) findViewById(R.id.button_stop);
        button_stop.setEnabled(false);
        button_clean = (Button) findViewById(R.id.button_readclear);
        button_set = (Button) findViewById(R.id.button_set);
        listView = (ListView) findViewById(R.id.listView_epclist);

        tv_state = (TextView) findViewById(R.id.textView_invstate);
        tv_tags = (TextView) findViewById(R.id.textView_readallcnt);
        textView_time = (TextView) findViewById(R.id.textView_time);
        textView_time.setText("00:00:00");
        cb_readtid = (CheckBox) findViewById(R.id.cb_readtid);
        cb_is6btag = (CheckBox) findViewById(R.id.cb_is6btag);
        //liudongwen
        btnUpload = (Button) findViewById(R.id.btnUpload);

        //cb_is6btag.setEnabled(false);

        button_set.setText(R.string.Real_time);
        tv_state.setText(R.string.device_msg);
        Awl = new AndroidWakeLock((PowerManager) getSystemService(Context.POWER_SERVICE));
        exittime = System.currentTimeMillis();


        //liudongwen
//        clearAllData();


        button_read.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unused")
            @Override
            public void onClick(View arg0) {

                //liudongwen 清空数据;
//                if(uploadSet==null){
//                    uploadSet = new HashSet<>();
//                }
//                if(list==null){
//                    list = new ArrayList<>();
//                }
//                list.clear();
//                uploadSet.clear();

                //liudongwen
                //隐藏dialog
                if (promptDialog != null) {
                    promptDialog.dismiss();
                }


                try {
//                    startTimerTask();
                    //todo liudongwen ,取消
                    //button_clean.performClick();
                    tv_state.setText(R.string.read_state);
//                    Awl.WakeLock();
//                    Comm.startScan();
//                    ReadHandleUI();

                    LogUtil.e("TAG","button_read");
                    LogUtil.e("TAG","isFloatViewIsShowing="+ RFIDApplication.instance.floatAccessibilityService.isFloatViewIsShowing);
                    LogUtil.e("TAG","Comm.isrun ="+ Comm.isrun);

                    //悬浮按钮已经显示了,就不用再显示;
                    if (RFIDApplication.instance.floatAccessibilityService.isFloatViewIsShowing == true) {
                        ReadHandleUI();
                        return;
                    }

                    if(RFIDApplication.instance.floatAccessibilityService!=null){
                    promptDialog.viewAnimDuration = 10;
                    promptDialog = new PromptDialog(SuoFeiYaAccessibilitySActivity.this);
                    promptDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(2500);
                    promptDialog.showLoading("正在开启服务...");
                    }

                    // begin
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (Settings.canDrawOverlays(RFIDApplication.instance)) {
                            // begin
                            startService();
                            ReadHandleUI();
                        } else {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    } else {
                        startService();
                        ReadHandleUI();
                    }

                    if (promptDialog != null) {
                        promptDialog.dismiss();
                    }

                } catch (Exception ex) {
                    Toast.makeText(SuoFeiYaAccessibilitySActivity.this,
                            "ERR：" + ex.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    //liudongwen
                    ExceptionUtil.handleException(ex);

                }


            }
        });

        button_stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
//
//                Awl.ReleaseWakeLock();
//                //todo
////                stopTimerTask();
//                tv_state.setText(R.string.stop_read_msg);
//                Comm.stopScan();
////                showlist();
//                StopHandleUI();
//
//                if(RFIDApplication.instance.floatAccessibilityService!=null){
//                    RFIDApplication.instance.floatService.stopScan();
//                }

                LogUtil.e("TAG","button_stop");
                LogUtil.e("TAG","Comm.isrun ="+ Comm.isrun);

                if(RFIDApplication.instance.floatAccessibilityService!=null&&RFIDApplication.instance.floatAccessibilityService.isCanRunRead==false){
                    ToastUtil.showToast(mContext,"正在上传数据,请稍后重试");
                    return;
                }


                promptDialog.viewAnimDuration = 10;
                promptDialog = new PromptDialog(SuoFeiYaAccessibilitySActivity.this);
                promptDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(1000);
                promptDialog.showLoading("正在停止服务...");

                LogUtil.e("TAG", "Comm.isrun = " + Comm.isrun);

//                if(Comm.isrun && RFIDApplication.instance.floatAccessibilityService!=null){
//                    RFIDApplication.instance.floatAccessibilityService.activityStopButtonOn();
//                }

                if (RFIDApplication.instance.floatAccessibilityService != null) {
                    RFIDApplication.instance.floatAccessibilityService.activityStopButtonOn();
                }

                LogUtil.e("TAG", "activityStopButtonOn = ");

                if (RFIDApplication.instance.floatAccessibilityService != null) {
                    RFIDApplication.instance.floatAccessibilityService.list.clear();
                    RFIDApplication.instance.floatAccessibilityService.uploadSet.clear();
                }

//                stopService();
                StopHandleUI();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter != null) {
                            list = new ArrayList<>();
                            uploadSet = new HashSet<>();
                            adapter = new SuoFeiYaAdapter(
                                    SuoFeiYaAccessibilitySActivity.this,
                                    list,
                                    R.layout.listitemview_inv_suofeiya,
                                    Coname,
                                    new int[]{
                                            R.id.textView_readsort,
                                            R.id.textView_readepc,
                                            R.id.textView_readcnt});

                            // layout为listView的布局文件，包括三个TextView，用来显示三个列名所对应的值
                            // ColumnNames为数据库的表的列名
                            // 最后一个参数是int[]类型的，为view类型的id，用来显示ColumnNames列名所对应的值。view的类型为TextView
                            listView.setAdapter(adapter);
                            tv_tags.setText(String.valueOf(uploadSet.size()));

                            if (promptDialog != null) {
                                promptDialog.dismiss();
                            }
                        }
                    }
                }, 200);


            }
        });

        button_clean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                promptDialog.viewAnimDuration = 100;
                promptDialog = new PromptDialog(SuoFeiYaAccessibilitySActivity.this);
                promptDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(3000);
                final PromptButton confirm = new PromptButton("确定",
                        new PromptButtonListener() {
                            @Override
                            public void onClick(PromptButton button) {

//
//                        Comm.lv.clear();
//                        tv_tags.setText("0");
//                        tv_state.setText(R.string.clearAll_Wait);//清空完成,等待操作...
//                        Comm.clean();
//
//                        //liudongwen 清空
//                        clearAllData();
//
//                        //demo
//                        showlist();

                                buttonClearOn();


                            }
                        });
                confirm.setTextColor(Color.parseColor("#528DCC"));
//                confirm.setFocusBacColor(Color.parseColor("#B0000000"));
                confirm.setDelyClick(true);//点击后，是否再对话框消失后响应按钮的监听事件
                promptDialog.showWarnAlert("清除数据吗？", new PromptButton("取消", new PromptButtonListener() {
                    @Override
                    public void onClick(PromptButton button) {
                    }
                }), confirm);

            }
        });

        button_set.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (Comm.setParameters()) {
                    if (isQuick) {//是否快速模式 true为是 false为否
                        isQuick = false;
                        button_set.setText(R.string.Real_time);
                        Toast.makeText(SuoFeiYaAccessibilitySActivity.this, "RealTime mode set success!", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        isQuick = true;
                        button_set.setText(R.string.fast_time);
                        Toast.makeText(SuoFeiYaAccessibilitySActivity.this, "Quick mode set success!", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else
                    Toast.makeText(SuoFeiYaAccessibilitySActivity.this, "set false!", Toast.LENGTH_SHORT)
                            .show();
            }
        });
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                arg1.setBackgroundColor(Color.YELLOW);


                for (int i = 0; i < listView.getCount(); i++) {
                    if (i != arg2) {
                        View v = listView.getChildAt(i);
                        if (v != null) {
                            ColorDrawable cd = (ColorDrawable) v
                                    .getBackground();
                            if (Color.YELLOW == cd.getColor()) {
                                int[] colors = {Color.WHITE,
                                        Color.rgb(219, 238, 244)};// RGB颜色
                                v.setBackgroundColor(colors[i % 2]);// 每隔item之间颜色不同
                            }
                        }
                    }
                }
            }

        });
        cb_is6btag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    Comm.Is6BTag();
                } else {
                    Comm.is6BTag = false;
                }
            }
        });

        cb_readtid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (Comm.SetInventoryTid(isChecked))
                    Toast.makeText(SuoFeiYaAccessibilitySActivity.this, "set success!", Toast.LENGTH_SHORT)
                            .show();
                else
                    Toast.makeText(SuoFeiYaAccessibilitySActivity.this, "set fail!", Toast.LENGTH_SHORT)
                            .show();

            }

        });
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {
                int j = tabHost.getCurrentTab();

                tw.getChildAt(j).setBackgroundColor(Color.parseColor("#FF8C00"));
                if (j != 0) {
                    tw.getChildAt(0).setBackgroundColor(Color.parseColor("#FFF0F0"));
                }
                if (j != 1) {
                    tw.getChildAt(1).setBackgroundColor(Color.parseColor("#FFF0F0"));
                }
                if (j != 2) {
                    tw.getChildAt(2).setBackgroundColor(Color.parseColor("#FFF0F0"));
                }
                if (j == 1) {
                    if (Comm.lsTagList6B.size() > 0)
                        Sub3TabActivity.Get6BList();

                    if (tagListSize > 0)
                        Sub3TabActivity.GetTagList();
                } else if (j == 2) {
                    if (tabHost_set != null) {
                        int tabHost_set_Index = tabHost_set.getCurrentTab();
                        if (tabHost_set_Index == 0) {
                            Comm.opeT = Comm.operateType.getPower;
                            Comm.getAntPower();
                        }
                    }
                }
            }
        });
        tw.getChildAt(0).setBackgroundColor(Color.parseColor("#FF8C00"));


        //liudongwen
//        initSoundPool();

        butSetUrl = (Button) findViewById(R.id.butSetUrl);
        butSetUrl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtil.startActivity(SuoFeiYaMainDemand2Activity.this, SettingActivity.class, null);
                getDialog(SuoFeiYaAccessibilitySActivity.this);
            }
        });

        //liudongwen
        mContext = this;












    }

    private PromptDialog promptDialog;
    CheckSwitchButton mCheckSwithcButton;
    private static boolean isCheck;

    private void startService() {

        LogUtil.e("TAG","startService() ");
        if (RFIDApplication.instance.floatAccessibilityService != null) {
            RFIDApplication.instance.floatAccessibilityService.activityStartButtonOn();
            LogUtil.e("TAG","activityStartButtonOn() ");
        }


    }
//    private void stopService() {
//        Intent serviceStop = new Intent();
//        serviceStop.setClass(SuoFeiYaAccessibilitySActivity.this, FloatService.class);
//        stopService(serviceStop);
//    }


    private void buttonClearOn() {

        Comm.lv.clear();
        tv_tags.setText("0");
        tv_state.setText(R.string.clearAll_Wait);//清空完成,等待操作...
        Comm.clean();

//        //liudongwen 清空
//        clearAllData();
//
//        //demo
//        showlist();
    }


    //当前进度
    private int currentProgress;
    //是否开启动画：平滑过度，默认开启
    private boolean animal = true;

    private void startProgress(boolean animal) {
        this.animal = animal;
        mHandler.post(runnable);
    }

    private MProgressBarDialog mProgressBarDialog;
    ;

    /**
     * --------------------MProgressBarDialog start -------------------
     */
    private void configProgressbarCircleDialog() {
        //新建一个Dialog
        mProgressBarDialog = new MProgressBarDialog.Builder(this).setStyle(MProgressBarDialog.MProgressBarDialogStyle_Circle)
                .build();
    }

    //    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            if (currentProgress < 100) {
//                mProgressBarDialog.showProgress(currentProgress, "正在上传：" + currentProgress + "%", animal);
//                currentProgress += 5;
//                mHandler.postDelayed(runnable, 200);
//            } else {
//                mHandler.removeCallbacks(runnable);
//                currentProgress = 0;
//                mProgressBarDialog.showProgress(100, "完成", animal);
//                //关闭
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mProgressBarDialog.dismiss();
//                    }
//                }, 1000);
//            }
//        }
//    };
//
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mProgressBarDialog.showProgress(currentProgress, "正在上传：" + currentProgress + "%", animal);

        }
    };


//    //liudongwen
//    public void clearAllData(){
//        //liudongwen 清空数据;
//        if(uploadSet==null){
//            uploadSet = new HashSet<>();
//        }
//        if(list==null){
//            list = new ArrayList<>();
//        }
//        list.clear();
//        uploadSet.clear();
//    }


    private static Dialog getDialog(Context context) {
        final Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        dialog.setContentView(R.layout.dialog_url_modify);
        ImageView btnBack = (ImageView) dialog.findViewById(R.id.btnBack);
        Button login_btn = (Button) dialog.findViewById(R.id.login_btn);
        final TextInputEditText mUserMobile = (TextInputEditText) dialog.findViewById(R.id.user_mobile);
        String address = PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_SERVER,
                ResHelper.getString(RFIDApplication.instance, R.string.url_server_address)
        );
        mUserMobile.setText(address);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        login_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = mUserMobile.getText().toString().trim();

                if (StringHelper2.judeIsLegalURL(address)) {
                    PreferenceUtil.set(RFIDApplication.instance, Const.KEY_URL_SERVER, address);

                    LogUtil.i("TAG", "SettingActivity = " +
                            PreferenceUtil.get(RFIDApplication.instance,
                                    Const.KEY_URL_SERVER,
                                    ResHelper.getString(RFIDApplication.instance, R.string.url_server_address)));

                    ToastUtil.showToast(RFIDApplication.instance, "修改成功!");
                } else {
                    ToastUtil.showToast(RFIDApplication.instance, "修改失败!地址格式不正确!");
                }
            }
        });
        dialog.show();
        return dialog;
    }


//    //开始计时
//    public void startTimerTask() {
//        if (null == timer) {
//            if (null == task) {
//                task = new TimerTask() {
//                    @Override
//                    public void run() {
//                        if (null == msg) {
//                            msg = new Message();
//                        } else {
//                            msg = Message.obtain();
//                        }
//                        msg.what = 1;
//                        handlerTimerTask.sendMessage(msg);
//                    }
//                };
//            }
//            timer = new Timer(true);
//            timer.schedule(task, mlTimerUnit, mlTimerUnit); // set timer duration
//        }
//    }

//    //停止计时
//    public void stopTimerTask() {
//        if (null != timer) {
//            task.cancel();
//            task = null;
//            timer.cancel(); // Cancel timer
//            timer.purge();
//            timer = null;
//            handlerTimerTask.removeMessages(msg.what);
//        }
//        mlCount = 0;
//    }

//    //异步处理计时数据
//    @SuppressLint("HandlerLeak")
//    public Handler handlerTimerTask = new Handler() {
//        @SuppressLint({"SetTextI18n", "DefaultLocale"})
//        @Override
//        public void handleMessage(Message msg) {
//
//            switch (msg.what) {
//                case 1:
//                    mlCount++;
//                    totalSec = 0;
//                    // 100 millisecond
//                    totalSec = (int) (mlCount / 100);
//                    yushu = (int) (mlCount % 100);
//                    // Set time display
//                    min = (totalSec / 60);
//                    sec = (totalSec % 60);
//                    try {
//                        // 100 millisecond
//                        textView_time.setText(String.format("%1$02d:%2$02d:%3$d", min, sec, yushu));
//                    } catch (Exception e) {
//                        textView_time.setText("" + min + ":" + sec + ":" + yushu);
//                        e.printStackTrace();
//                        Log.e("MyTimer onCreate", "Format string error.");
//                    }
//                    break;
//                default:
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//
//    };


//    public void InitDevice() {
//        //盘点到重复标签是否发出声音
//        Comm.repeatSound = true;
//        //getApplication()迷惑客户，往往导致出错
////        Comm.app = getApplication();
//        Comm.app = new UHF1Application();
//        Comm.spConfig = new SPconfig(this);
//        context = SuoFeiYaMainDemand2Activity.this;
//        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
//        soundPool.load(this, R.raw.beep51, 1);
//        Log.d("test", "soundPool");
//
//        checkDevice();
//        Comm.initWireless(Comm.app);
//        Comm.connecthandler = connectH;
////        Comm.moduleType = null;
//        if (Comm.moduleType!=null)
//            ConnectModule();
//        else
//            Connect();
//        Log.d("test", "connect");
//    }

    String[] Coname = new String[]{"NO", "                    EPC ID ", "Count"};


    private Runnable runToLast = new Runnable() {
        @Override
        public void run() {
            // Select the last row so it will scroll into view...
            listView.setSelection(adapter.getCount() - 1);
        }
    };


    private void ReadHandleUI() {
        this.button_read.setEnabled(false);
        this.button_stop.setEnabled(true);
        this.button_set.setEnabled(false);
        //liudongwen
        this.butSetUrl.setEnabled(false);
        this.button_clean.setEnabled(false);
        TabWidget tw = Comm.supoinTabHost.getTabWidget();
        tw.getChildAt(1).setEnabled(false);
        tw.getChildAt(2).setEnabled(false);
    }

    private void StopHandleUI() {
        button_read.setEnabled(true);
        button_stop.setEnabled(false);
        this.button_set.setEnabled(true);
        //liudongwen
        this.butSetUrl.setEnabled(true);
        this.button_clean.setEnabled(true);
        TabWidget tw = Comm.supoinTabHost.getTabWidget();
        tw.getChildAt(1).setEnabled(true);
        tw.getChildAt(2).setEnabled(true);

    }

//        @Override
//        public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//            //针对promptDialog
//
//                if (keyCode == KeyEvent.KEYCODE_BACK
//                        && event.getAction() == KeyEvent.ACTION_DOWN) {
////                    if (promptDialog!=null&& promptDialog.onBackPressed()){
////
////                        if ((System.currentTimeMillis() - exittime) > 2000) {
////                            Toast.makeText(getApplicationContext(), "再按一次退出",
////                                    Toast.LENGTH_SHORT).show();
////                            exittime = System.currentTimeMillis();
////                        } else {
////                            release();
////                            //finish();
////                            System.exit(0);
////                        }
////                    }
////                    else{
////                        if ((System.currentTimeMillis() - exittime) > 2000) {
////                            Toast.makeText(getApplicationContext(), "再按一次退出",
////                                    Toast.LENGTH_SHORT).show();
////                            exittime = System.currentTimeMillis();
////                        } else {
////                            release();
////                            //finish();
////                            System.exit(0);
////                        }
////                    }
//
//                    if (promptDialog!=null&& !promptDialog.onBackPressed()){
//
////                        if ((System.currentTimeMillis() - exittime) > 2000) {
////                            Toast.makeText(getApplicationContext(), "再按一次退出",Toast.LENGTH_SHORT).show();
////                            exittime = System.currentTimeMillis();
////                        } else {
////                            release();
////                            //finish();
////                            System.exit(0);
////                        }
//                    }
//                    else{
//                        if ((System.currentTimeMillis() - exittime) > 2000) {
//                            Toast.makeText(getApplicationContext(), "再按一次退出",
//                                    Toast.LENGTH_SHORT).show();
//                            exittime = System.currentTimeMillis();
//                        } else {
//                            release();
//                            //finish();
//                            System.exit(0);
//                        }
//                    }
//
//
//                    return true;
//                }
//
//
//        return super.onKeyDown(keyCode, event);
//    }

//    public void release() {
//        try {
//            if (isrun)
//                Comm.stopScan();
//            Comm.Exit();
//            Comm.powerDown();
//        } catch (Exception e) {
//            e.printStackTrace();
//            ExceptionUtil.handleException(e);
//        }
//    }
//
//    /* 释放按键事件 */
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (event.getScanCode() == 261 && isrun)
//            button_stop.performClick();
//        else if (event.getScanCode() == 261 && !isrun)
//            button_read.performClick();
//        return super.onKeyUp(keyCode, event);
//    }
//
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent e) {
//        try {
////            scanCode = e.getScanCode();
////            Log.d("UHF","getScanCode "+scanCode);
//            return super.dispatchKeyEvent(e);
//        } catch (Exception ex) {
//
//            ex.printStackTrace();
//            ExceptionUtil.handleException(ex);
//            return false;
//        }
//    }


//
//    public void postString() {
//        String content = getJsonObject();
//
//        String url = PreferenceUtil.get(this,Const.KEY_URL_SERVER,"http://192.168.1.85:8089/rfidscanned")+"/test?json="+content;
//
////        OkHttpUtils
////                .postString()
////                .url(url)
////                .tag(cancelTag)
////                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//////                .content(new Gson().toJson(new User("zhy", "123")))
////                .build()
////                .execute(new MyStringCallback());
//
//
//        LogUtil.e("TAG","url = " + url);
//        LogUtil.e("TAG","content = " + content);
//
//        OkHttpUtils
//                .post()//
//                .url(url)//
//                .addParams("json",content)
//                .build().execute(new MyStringCallback());
//
//    }

    private String getJsonObject() {

        LogUtil.e("TAG", "uploadSet.size() = " + uploadSet.size());

        StringBuffer sb = new StringBuffer();

        sb.append("{\"data\": [");
        //{"data": ["PA1808010183059","PA1808010411524","PA1808010411522",
        // "PA1808010411525","PA1808010411523","PA1808010411518",
        // "PA1808010411519","PA1808010411520","PA1808010411521",
        // "PA1808010474509","PA1808010736617","PA1808010812269",
        // "PA1808010826457","PA1808010827653","PA1808030096584",
        // "PA1808030134024","PA1808010182927","PA1808010411794",
        // "PA1808010411797","PA1808010411802","PA1808010411801",
        // "PA1808010411792","PA1808010411803","PA1808010411799",
        // "PA1808010411798","PA1808010411800","PA1808010411796",
        // "PA1808010428116","PA1808010474505","PA1808010665842","PA1808010736608","PA1808010812265","PA1808010827624","PA1808010831542","PA1808030049776","PA1808030049775","PA1808030096580","PA1808030134020","PA1808010411581","PA1808010411582","PA1808010411580","PA1808010411578","PA1808010411583","PA1808010411579","PA1808010428112","PA1808010452826","PA1808010452827","PA1808010472792","PA1808010736623","PA1808030049783","PA1808030049784","PA1808030096589","PA1807240483640","PA1807240483639","PA1807240483641",
        // "PA1807240483638","PA1807240483636","PA1807240483637","PA1807240483648","PA1807240483647","PA1807240483646","PA1807240483644","PA1807240483645","PA1807240483643","PA1807240483642","PA1808010340635","PA1808010340636","PA1808010340637","PA1808010340638","PA1808010340639","PA1808010340640","PA1808010340641","PA1808010340642",
        // "PA1808010340643","PA1808010340644"]}

        if (uploadSet != null) {

            boolean isFirst = true;
            Iterator<String> it = uploadSet.iterator();
            while (it.hasNext()) {
                String str = it.next();
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(",");
                }
                sb.append("\"");
                sb.append(str);
                sb.append("\"");
            }

        }

        sb.append("]}");

        return sb.toString();
    }


//    public class MyStringCallback extends StringCallback {
//        @Override
//        public void onBefore(Request request, int id) {
//            configProgressbarCircleDialog();
//            startProgress(true);
//        }
//
//        @Override
//        public void onAfter(int id) {
////            setTitle("Sample-okHttp");
//            if(mProgressBarDialog!=null){
//                mProgressBarDialog.dismiss();
//            }
//        }
//
//        @Override
//        public void onError(Call call, Exception e, int id) {
//            LogUtil.e("TAG", "e ------------>" + e);
//
//            if(mProgressBarDialog!=null){
//                mProgressBarDialog.dismiss();
//            }
//
//            LogUtil.e("TAG","onError " + e.toString());
//
//            if (mContext != null && mContext instanceof Activity) {
//                final String info;
//                if (e instanceof UnknownHostException) {
//                    info = "网络未连接!";
//                } else if (e instanceof SocketTimeoutException) {
//                    info = "连接网络超时!";
//                } else if(e instanceof ConnectException){
//                    info = "连接地址: "+PreferenceUtil.get(mContext,Const.KEY_URL_SERVER,"http://192.168.1.85:8089/rfidscanned/")+" 失败";
//                }
//                else {
//                    info = e.toString();
//                }
//
//                ((Activity) mContext).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ProgressUtil.reminderError(mContext,"网络错误",info);
//                    }
//                });
//            }
//
//            Log.e("TAG","Exception = "+e.toString());
//
//        }
//
//        @Override
//        public void onResponse(String response, int id) {
//            LogUtil.e("TAG", "onResponse：complete-------->");
//
//
//            LogUtil.e("TAG", "response ------------> " + response);
//            LogUtil.e("TAG", "id ------------>" + id);
//
//            ResponseObj obj = GsonUtil.toResponseObj(response);
//            if(obj==null){
//                ProgressUtil.reminderError(mContext,"返回信息错误",response);
//                soundError();
//            }
//            else {
//                if("0".equals(String.valueOf(obj.getNumber()))){
//                   //提示成功
//                    showSuccessUploadDialog();
//                    //清空数据
//                    buttonClearOn();
//                }
//                else{
//                    soundError();
//                    ProgressUtil.reminderError(mContext,"发生串货","串货数量:"+String.valueOf(obj.getNumber()));
//                }
//
//            }
//        }
//
//        @Override
//        public void inProgress(float progress, long total, int id) {
//            LogUtil.e("TAG", "inProgress=================>" + progress);
//            currentProgress = (int) (100 * progress);
//            if (currentProgress < 100) {
//                mProgressBarDialog.showProgress(currentProgress, "正在上传：" + currentProgress + "%", animal);
//            } else {
//                mHandler.removeCallbacks(runnable);
//                currentProgress = 0;
//                mProgressBarDialog.showProgress(100, "100%", animal);
//                //关闭
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mProgressBarDialog.dismiss();
//                    }
//                }, 1000);
//            }
//
//
//        }
//    }


//    @Override
//    public void onBackPressed() {
//        //针对promptDialog
//        if (promptDialog.onBackPressed()){
//            super.onBackPressed();
//        }
//
//    }

    List<Map<String, ?>> list;

    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //todo 看要不要做些什么
            uiFresh();

        }
    }

    public class ReceiverStopDialog extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //todo 看要不要做些什么
            if (promptDialog != null) {
                promptDialog.dismiss();
            }
            LogUtil.e("TAG", "ReceiverStopDialog================>");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiFresh();
        if (RFIDApplication.instance.floatAccessibilityService != null) {
            button_read.performClick();
        }
//        else{
//            button_stop.performClick();
//        }

        LogUtil.e("TAG","onResume()");
    }

    public void uiFresh() {

        LogUtil.e("TAG","uiFresh");

        if (RFIDApplication.instance.floatAccessibilityService != null) {
            uploadSet = RFIDApplication.instance.floatAccessibilityService.uploadSet;
            list = RFIDApplication.instance.floatAccessibilityService.list;
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        if (uploadSet == null) {
            uploadSet = new HashSet<>();
        }

        LogUtil.e("TAG","uploadSet = " + uploadSet);
        LogUtil.e("TAG","list = " + list);


//        if(adapter==null){
        adapter = new SuoFeiYaAdapter(
                SuoFeiYaAccessibilitySActivity.this,
                list,
                R.layout.listitemview_inv_suofeiya,
                Coname,
                new int[]{
                        R.id.textView_readsort,
                        R.id.textView_readepc,
                        R.id.textView_readcnt});

        // layout为listView的布局文件，包括三个TextView，用来显示三个列名所对应的值
        // ColumnNames为数据库的表的列名
        // 最后一个参数是int[]类型的，为view类型的id，用来显示ColumnNames列名所对应的值。view的类型为TextView
        listView.setAdapter(adapter);
//        }
//        else{
//            adapter.notifyDataSetChanged();
//
//            LogUtil.e("TAG"," adapter.notifyDataSetChanged();=======================>");
//        }
        listView.post(runToLast);
        tv_tags.setText(String.valueOf(uploadSet.size()));
    }


}
