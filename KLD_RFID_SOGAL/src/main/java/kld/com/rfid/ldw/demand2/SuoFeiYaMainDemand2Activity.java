package kld.com.rfid.ldw.demand2;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
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

import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.example.UHFDemo_install.Sub3TabActivity;
import com.example.UHFDemo_install.Sub4TabActivity;
import com.ldw.xyz.util.ActivityUtil;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.PreferenceUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.ToastUtil;
import com.ldw.xyz.util.exception.ExceptionUtil;
import com.ldw.xyz.util.service.AccessibilityServiceUtil;
import com.ldw.xyz.util.string.StringHelper2;
import com.ldw.xyz.util.version.VersionUtil;
import com.maning.mndialoglibrary.MProgressBarDialog;
import com.uhf.uhf.Common.Comm;
import com.uhf.uhf.UHF1.UHF1Function.AndroidWakeLock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.R;
import kld.com.rfid.ldw.RFIDApplication;
import kld.com.rfid.ldw.SuoFeiYaAdapter;
import kld.com.rfid.ldw.SuoFeiYaBaseActivity;
import kld.com.rfid.ldw.demand2.channel.ChannelCheckActivity;
import kld.com.rfid.ldw.demand2.update.UpdateUtil;
import kld.com.rfid.ldw.demand2.way3.MyAccessibilityService;
import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

import static com.example.UHFDemo_install.Sub4TabActivity.tabHost_set;
import static com.uhf.uhf.Common.Comm.Awl;
import static com.uhf.uhf.Common.Comm.isQuick;
import static com.uhf.uhf.Common.Comm.isrun;
import static com.uhf.uhf.Common.Comm.tagListSize;

//总共读到的标签个数

//import com.example.UHFDemo_install.MyAdapter;


//import com.supoin.wireless.WirelessManager;

@SuppressWarnings("deprecation")
public class SuoFeiYaMainDemand2Activity extends SuoFeiYaBaseActivity {
    private static final String TAG = "SuoFeiYaMainDemand2Activity";
    //liudongwen
    Set<String> uploadSet;
    SuoFeiYaAdapter adapter;
    //    protected Object cancelTag = this;
    private String strMsg = "";
    protected Object cancelTag = this;


    TextView tv_state, tv_tags, textView_time,tv_Title;
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

    TextView tvChannel;

    Receiver receiver;
    ReceiverStopDialog receiverStopDialog;

    @Override
    protected void onStart() {
        super.onStart();

        // 检查更新;
        if(RFIDApplication.getIsCanCheckUpdate()){
            UpdateUtil.check(this, HttpRequestMethod.GET);
        }



        Log.d("Activity", "onStart()");

        if (true) {
            //liudognwen 更新界面
            receiver = new Receiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("$_UpdateAdapter");
            getApplicationContext().registerReceiver(receiver, filter);
        }


        if (true) {
            receiverStopDialog = new ReceiverStopDialog();
            IntentFilter filter2 = new IntentFilter();
            filter2.addAction("$_CloseDialog");
            getApplicationContext().registerReceiver(receiverStopDialog, filter2);
        }


        if (!isAccessibilitySettingsOn(mContext)) {

//            dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
//                    .setTitleText("提示")
//                    .setConfirmText("立刻开启")
//                    .setContentText("需要开启: 无障碍 ---> RFID扫描 "+"\n"+"才能使用物理按键触发扫描")
//                    .setCancelText("取消")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//                            startActivity(intent);
//
//                            if (sDialog != null) {
//                                sDialog.dismiss();
//                            }
//                        }
//                    });
//            dialog.show();
            addViewAndShowInfo("提示", "需要开启: 无障碍 ---> RFID扫描 " + "\n" + "才能使用物理按键触发扫描");

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        OkHttpUtils.getInstance().cancelTag(cancelTag);
        //避免内存泄露
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }

        try {
            getApplicationContext().unregisterReceiver(receiver);
            getApplicationContext().unregisterReceiver(receiverStopDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
        if (test) {
            Awl = new AndroidWakeLock((PowerManager) getSystemService(Context.POWER_SERVICE));
        }

        exittime = System.currentTimeMillis();


        //liudongwen
//        clearAllData();


        button_read.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unused")
            @Override
            public void onClick(View arg0) {


                String name = PreferenceUtil.get(mContext, Const.KEY_STAGE_NMAE, "");
                if (StringHelper2.isEmpty(name)) {
                    ToastUtil.showToast(mContext, "请选择通道!");
                    return;
                }

                //liudongwen
                //隐藏dialog
                if (promptDialog != null) {
                    promptDialog.dismiss();
                }


                try {
                    //button_clean.performClick();
                    tv_state.setText(R.string.read_state);

                    if (RFIDApplication.instance.floatService == null) {
                        promptDialog.viewAnimDuration = 10;
                        promptDialog = new PromptDialog(SuoFeiYaMainDemand2Activity.this);
                        promptDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(3500);
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
                    Toast.makeText(SuoFeiYaMainDemand2Activity.this,
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

                if (RFIDApplication.instance.floatService != null && RFIDApplication.instance.floatService.isCanRunRead == false) {
                    ToastUtil.showToast(mContext, "正在上传数据,请稍后重试");
                    return;
                }

                if (Comm.isrun && RFIDApplication.instance.floatService != null) {
                    RFIDApplication.instance.floatService.stopButtonClick();
                    return;
                }


                promptDialog.viewAnimDuration = 10;
                promptDialog = new PromptDialog(SuoFeiYaMainDemand2Activity.this);
                promptDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(1000);
                promptDialog.showLoading("正在停止服务...");


                if (isrun && RFIDApplication.instance.floatService != null) {
                    RFIDApplication.instance.floatService.list.clear();
                    RFIDApplication.instance.floatService.uploadSet.clear();
                    RFIDApplication.instance.floatService.stopScan();

                }

                stopService();
                StopHandleUI();

                if (RFIDApplication.instance.floatService != null) {
                    RFIDApplication.instance.floatService.release();
                }


                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter != null) {
                            list = new ArrayList<>();
                            uploadSet = new HashSet<>();
                            adapter = new SuoFeiYaAdapter(
                                    SuoFeiYaMainDemand2Activity.this,
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
                            RFIDApplication.instance.floatService = null;
                        }
                    }
                }, 200);

                if (promptDialog != null) {
                    promptDialog.dismiss();
                }


            }
        });

        button_clean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                promptDialog.viewAnimDuration = 100;
                promptDialog = new PromptDialog(SuoFeiYaMainDemand2Activity.this);
                promptDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(3000);
                final PromptButton confirm = new PromptButton("确定",
                        new PromptButtonListener() {
                            @Override
                            public void onClick(PromptButton button) {

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
                        Toast.makeText(SuoFeiYaMainDemand2Activity.this, "RealTime mode set success!", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        isQuick = true;
                        button_set.setText(R.string.fast_time);
                        Toast.makeText(SuoFeiYaMainDemand2Activity.this, "Quick mode set success!", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else
                    Toast.makeText(SuoFeiYaMainDemand2Activity.this, "set false!", Toast.LENGTH_SHORT)
                            .show();
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
                    Toast.makeText(SuoFeiYaMainDemand2Activity.this, "set success!", Toast.LENGTH_SHORT)
                            .show();
                else
                    Toast.makeText(SuoFeiYaMainDemand2Activity.this, "set fail!", Toast.LENGTH_SHORT)
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
        initSoundPool();

        butSetUrl = (Button) findViewById(R.id.butSetUrl);
        butSetUrl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog(SuoFeiYaMainDemand2Activity.this);
            }
        });


        //liudongwen
        mContext = this;


        tv_Title = (TextView)findViewById(R.id.tv_Title);
        //默认是发货
        if(StringHelper2.isEmpty(RFIDApplication.getModeIsBuHuo())){
            RFIDApplication.setModeBuHuo(Const.CONST_FALSE);
        }
        boolean check ;
        if(RFIDApplication.getModeIsBuHuo()==Const.CONST_TRUE){
            check = true;
            tv_Title.setText("取\t\t\t消");
            uiBuHuo();
        }
        else{
            check = false;
            tv_Title.setText("发\t\t\t货");
            uiFaHuo();
        }

        mCheckSwithcButton = (CheckSwitchButton) findViewById(R.id.mCheckSwithcButton);
        mCheckSwithcButton.setChecked(check);
        mCheckSwithcButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                LogUtil.e(TAG,"*** isChecked *** ----------------");
                LogUtil.e(TAG,"isChecked = " + isChecked);

                if(isChecked){
                    tv_Title.setText("取\t\t\t消");
                    RFIDApplication.setModeBuHuo(Const.CONST_TRUE);
                    uiBuHuo();

                }
                else{
                    tv_Title.setText("发\t\t\t货");
                    RFIDApplication.setModeBuHuo(Const.CONST_FALSE);
                    uiFaHuo();
                }

//                ToastUtil.showToast(mContext,PreferenceUtil.get(mContext,Const.KEY_MODE_BU_HUO));

            }
        });



        findViewById(R.id.llMore).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.startActivity(mContext, ChannelCheckActivity.class, null);
            }
        });

        tvChannel = (TextView) findViewById(R.id.tvChannel);

        TextView tvVersionName = (TextView) findViewById(R.id.tvVersionName);
        tvVersionName.setText("当前版本:" + VersionUtil.getVersionName(RFIDApplication.instance));
    }

    private void uiBuHuo(){
        ((TextView)findViewById(R.id.tvBuHuo)).setTextColor(ResHelper.getColor(mContext,R.color.black));
        ((TextView)findViewById(R.id.tvFaHuo)).setTextColor(ResHelper.getColor(mContext,R.color.gray));
    }
    private void uiFaHuo(){
        ((TextView)findViewById(R.id.tvBuHuo)).setTextColor(ResHelper.getColor(mContext,R.color.gray));
        ((TextView)findViewById(R.id.tvFaHuo)).setTextColor(ResHelper.getColor(mContext,R.color.black));
    }





    private PromptDialog promptDialog;
    CheckSwitchButton mCheckSwithcButton;

    private void startService() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent service = new Intent();
                service.setClass(SuoFeiYaMainDemand2Activity.this, FloatService.class);
                startService(service);
            }
        }, 50);

    }

    private void stopService() {
        Intent serviceStop = new Intent();
        serviceStop.setClass(SuoFeiYaMainDemand2Activity.this, FloatService.class);
        stopService(serviceStop);
    }


    private void buttonClearOn() {

        Comm.lv.clear();
        tv_tags.setText("0");
        tv_state.setText(R.string.clearAll_Wait);//清空完成,等待操作...
        Comm.clean();

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


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mProgressBarDialog.showProgress(currentProgress, "正在上传：" + currentProgress + "%", animal);

        }
    };


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


    public void release() {
        try {
            if (isrun)
                Comm.stopScan();
            Comm.Exit();
            Comm.powerDown();
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.handleException(e);
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        try {
//            scanCode = e.getScanCode();
//            Log.d("UHF","getScanCode "+scanCode);
            return super.dispatchKeyEvent(e);
        } catch (Exception ex) {

            ex.printStackTrace();
            ExceptionUtil.handleException(ex);
            return false;
        }
    }


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


    private void selectChannel() {
        tvChannel.setText("请选择通道");
        tvChannel.setTextColor(getResources().getColor(R.color.red));
    }


    boolean test = false;

    //    SweetAlertDialog dialog;
    @Override
    protected void onResume() {
        super.onResume();


        String name = PreferenceUtil.get(mContext, Const.KEY_STAGE_NMAE, "");
        if (StringHelper2.isEmpty(name)) {
            selectChannel();
            //tood
        } else {
            tvChannel.setText(name);
            tvChannel.setTextColor(getResources().getColor(R.color.black));
        }


        //todo
        uiFresh();
        if (RFIDApplication.instance.floatService != null) {
            button_read.performClick();
        }


    }

    @Override
    protected void onStop() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        super.onStop();
    }

    public void uiFresh() {

        if (RFIDApplication.instance.floatService != null) {
            uploadSet = RFIDApplication.instance.floatService.uploadSet;
            list = RFIDApplication.instance.floatService.list;
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        if (uploadSet == null) {
            uploadSet = new HashSet<>();
        }


//        if(adapter==null){
        adapter = new SuoFeiYaAdapter(
                SuoFeiYaMainDemand2Activity.this,
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

        listView.post(runToLast);
        tv_tags.setText(String.valueOf(uploadSet.size()));
    }


    private boolean isAccessibilitySettingsOn(Context mContext) {
        return AccessibilityServiceUtil.isAccessibilitySettingsOn(mContext, MyAccessibilityService.class);

    }


    Dialog mDialog;

    public void addViewAndShowInfo(String title, String content) {
        LogUtil.e("TAG", "addViewAndShowInfo=============>1");

        if(mDialog!=null){
            mDialog.dismiss();
        }

        mDialog = new Dialog(this, R.style.Dialog_Fullscreen2);
        mDialog.setContentView(R.layout.alert_dialog_demand3);

        TextView title_text = (TextView) mDialog.findViewById(R.id.title_text);//content_text
        TextView content_text = (TextView) mDialog.findViewById(R.id.content_text);//content_text
        title_text.setText(title);
        content_text.setText(content);
        Button btnClear = (Button) mDialog.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });

        Button confirm_button = (Button) mDialog.findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);

                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });
        LogUtil.e("TAG", "addViewAndShowInfo=============>2");

        mDialog.show();

    }


}