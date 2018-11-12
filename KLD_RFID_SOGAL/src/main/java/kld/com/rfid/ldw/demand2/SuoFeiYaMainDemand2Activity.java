package kld.com.rfid.ldw.demand2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.ldw.xyz.util.InputMethodUtil;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.R;
import kld.com.rfid.ldw.RFIDApplication;
import kld.com.rfid.ldw.SuoFeiYaAdapter;
import kld.com.rfid.ldw.SuoFeiYaBaseActivity;
import kld.com.rfid.ldw.adapter.ItemShowAdapter2;
import kld.com.rfid.ldw.demand2.baseService.MyBaseService;
import kld.com.rfid.ldw.demand2.channel.ChannelCheckActivity;
import kld.com.rfid.ldw.demand2.update.UpdateUtil;
import kld.com.rfid.ldw.demand2.way3.MyAccessibilityService;
import kld.com.rfid.ldw.test.jiewen.DataSort;
import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

import static com.example.UHFDemo_install.Sub4TabActivity.tabHost_set;
import static com.uhf.uhf.Common.Comm.Awl;
import static com.uhf.uhf.Common.Comm.isQuick;
import static com.uhf.uhf.Common.Comm.isrun;
import static com.uhf.uhf.Common.Comm.setAntPower;
import static com.uhf.uhf.Common.Comm.tagListSize;


//总共读到的标签个数

//import com.example.UHFDemo_install.MyAdapter;


//import com.supoin.wireless.WirelessManager;

@SuppressWarnings("deprecation")
public class SuoFeiYaMainDemand2Activity extends SuoFeiYaBaseActivity {

    static long time1 = 0;
    private static final String TAG = "SuoFeiYaMainDemand2Activity";
    //liudongwen
    Set<String> uploadSet;
    SuoFeiYaAdapter adapter;
    //    protected Object cancelTag = this;
    private String strMsg = "";
    protected Object cancelTag = this;


    TextView tv_state, tv_tags, textView_time, tv_Title;
    Button button_read, button_stop, button_clean, button_set, butSetUrl, btnUpload, btCheckUpdate;
    public Button btSetPower;
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


    Context mContext;


    Receiver receiver;
    ReceiverStopDialog receiverStopDialog;

    RecyclerView rv;

    TabWidget tw;

    TextView tvChannel;


    EditText etSuffix;
    Button btSureSuffix, btClearAll, btSortList;
    TextView tvUnPAStart, tvSuffixNum, tvUnSuffixNum, tvAAA, tvUnAAA;


    public void initTestJieWenWrite() {
        LogUtil.e(TAG, "PreferenceUtil.get(mContext,Const.KEY_IS_CAN_JIE_WEN_TEST) = " + PreferenceUtil.get(mContext, Const.KEY_IS_CAN_JIE_WEN_TEST));

        if (Const.IsCanTest == true && PreferenceUtil.get(mContext, Const.KEY_IS_CAN_JIE_WEN_TEST).equals(Const.CONST_TRUE)) {

            findViewById(R.id.llTestShow).setVisibility(View.VISIBLE);
            etSuffix.setText(PreferenceUtil.get(mContext, Const.KEY_JIE_WEN_TEST_SUFFIX));

        } else {
            findViewById(R.id.llTestShow).setVisibility(View.GONE);
        }

    }


    public void JieWenWriteShow() {
        if (Const.IsCanTest == true && PreferenceUtil.get(mContext, Const.KEY_IS_CAN_JIE_WEN_TEST).equals(Const.CONST_TRUE)) {
            tvUnPAStart.setText(FloatService.unPAStartNum + "");
            tvSuffixNum.setText(FloatService.AAAStartNum + "");
            tvUnSuffixNum.setText(FloatService.unAAAStartNum + "");
            tvAAA.setText(PreferenceUtil.get(mContext, Const.KEY_JIE_WEN_TEST_SUFFIX).toUpperCase() + "结尾的数量:");
            tvUnAAA.setText("不是" + PreferenceUtil.get(mContext, Const.KEY_JIE_WEN_TEST_SUFFIX).toUpperCase() + "结尾的数量:");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
//        RFIDApplication.instance.suoFeiYaMainDemand2Activity = this;

        LogUtil.e(TAG, "onStart()");
        LogUtil.e(TAG, "------------------------------");
        LogUtil.e(TAG, "initTestJieWenWrite()");
        initTestJieWenWrite();

        //这里主要是为了显示UI
        String name = PreferenceUtil.get(mContext, Const.KEY_STAGE_NMAE, "");
        if (StringHelper2.isEmpty(name)) {
            selectChannel();

        } else {
            tvChannel.setText(name);
            tvChannel.setTextColor(getResources().getColor(R.color.black));
        }

//        //todo
//        uiFresh();

        //这里主要是为了显示UI
        if (RFIDApplication.instance.floatService != null && RFIDApplication.instance.serviceIsStop == false) {
//            button_read.performClick();
            //开始按钮变灰
            ReadHandleUI();
        }

//        if(PreferenceUtil.get(mContext,"ISREAD").equals("TRUE")){
//            //开始按钮变灰
//            ReadHandleUI();
//        };


//        // 检查更新;
//        if(RFIDApplication.getIsCanCheckUpdate()){
//            UpdateUtil.check(this, HttpRequestMethod.GET);
//        }


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

        RFIDApplication.instance.suoFeiYaMainDemand2Activity = null;

        //        OkHttpUtils.getInstance().cancelTag(cancelTag);
        //避免内存泄露
//        if (mHandler != null) {
//            mHandler.removeCallbacksAndMessages(null);
//        }
//        if(opeHandler!=null){
//            opeHandler.removeCallbacksAndMessages(null);
//        }
//        if(opeHandler!=null){
//            opeHandler.removeCallbacksAndMessages(null);
//        }

        try {
            getApplicationContext().unregisterReceiver(receiver);
            getApplicationContext().unregisterReceiver(receiverStopDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (setPowerDialog != null) {
            setPowerDialog.dismiss();
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
        }

        super.onDestroy();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.MyAppTheme);
        setContentView(R.layout.activity_main_suofeiya_demand2_);


        // 检查更新;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (RFIDApplication.getIsCanCheckUpdate()) {
                    LogUtil.e("TAG", "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) = " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));

                    UpdateUtil.check(SuoFeiYaMainDemand2Activity.this, HttpRequestMethod.GET, null);

//        checkUpdate();
                }
            }
        }, 10);


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
        btSetPower = (Button) findViewById(R.id.btSetPower);
        btSetPower.setEnabled(false);
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

//                PreferenceUtil.set(mContext,"ISREAD","TRUE");

                LogUtil.e(TAG, "*** button_read start *** -----------");

                String name = PreferenceUtil.get(mContext, Const.KEY_STAGE_NMAE, "");

                if (Comm.isrun) {
                    return;
                }


                if (Const.IsCanTest == true) {
                    //测试的情况下不需要通道号;
                } else if (StringHelper2.isEmpty(name)) {
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

//                    if (RFIDApplication.instance.floatService == null) {
                    promptDialog.viewAnimDuration = 10;
                    promptDialog = new PromptDialog(SuoFeiYaMainDemand2Activity.this);
                    promptDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(1000);
                    promptDialog.showLoading("正在开启服务...");

//                    }

//                    if(opeHandler!=null){
////                        opeHandler.removeCallbacksAndMessages(null);
//                    }
//
//                    if(opeHandler==null){
//                         initOpeHandler();
//                        Comm.mOtherHandler = mHandler;
//                    }
                    if (Comm.mOtherHandler == null) {
                        Comm.mOtherHandler = RFIDApplication.mApplicationHandler;
                        ;
                    }


                    // begin
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        if (Settings.canDrawOverlays(RFIDApplication.instance)) {
//                            // begin
//                            startService();
//                            ReadHandleUI();
//                        } else {
//                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                    } else {
                    startService();
                    //开始按钮变灰
                    ReadHandleUI();
//                    }


//                    if (promptDialog != null) {
//                        promptDialog.dismiss();
//                    }

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (promptDialog != null) {
                                promptDialog.dismiss();
                            }

                        }
                    }, 500);


                } catch (Exception ex) {
                    Toast.makeText(SuoFeiYaMainDemand2Activity.this,
                            "ERR：" + ex.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    //liudongwen
                    ExceptionUtil.handleException(ex);

                }

                LogUtil.e(TAG, "*** button_read stop *** -----------");

            }
        });

        button_stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if ((System.currentTimeMillis() - time1) < 500) {
                    return;
                }

//                button_stop.setClickable(false);

//                PreferenceUtil.set(mContext,"ISREAD","FALSE");

                time1 = System.currentTimeMillis();
                if (etNum != null) {
                    InputMethodUtil.forceHideKeyBoard(RFIDApplication.instance, etNum);
                }

                if (RFIDApplication.instance.floatService != null && RFIDApplication.instance.floatService.isCanRunRead == false) {
                    ToastUtil.showToast(mContext, "正在上传数据,请稍后重试");
                    return;
                }

                if (false) {
                    if (Comm.isrun && RFIDApplication.instance.floatService != null) {
                        RFIDApplication.instance.floatService.stopButtonClick();
                        return;
                    }
                }

                if (Comm.isrun && RFIDApplication.instance.floatService != null) {
                    MyAccessibilityService.doBiz();
                    return;
                }

                RFIDApplication.instance.isCanRead = false;

//                button_stop.setClickable(true);


                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        promptDialog.viewAnimDuration = 10;
                        promptDialog = new PromptDialog(SuoFeiYaMainDemand2Activity.this);
                        promptDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(1500);
                        promptDialog.showLoading("正在停止服务...");


                        if (isrun && RFIDApplication.instance.floatService != null) {

                            RFIDApplication.instance.floatService.stopScan();
                            RFIDApplication.instance.floatService.list.clear();
                            RFIDApplication.instance.floatService.uploadSet.clear();

                        }

                        stopService();
                        StopHandleUI();

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (list != null && uploadSet != null && itemShowAdapter != null) {
                                    itemShowAdapter.notifyDataSetChanged();
                                    if (uploadSet != null && uploadSet.size() != 0) {
                                        tv_tags.setText(String.valueOf(uploadSet.size()));
                                    }

                                }
                            }
                        }, 100);

                        if (promptDialog != null) {
                            promptDialog.dismiss();
                        }

                    }
                }, 100);


//                    if (RFIDApplication.instance.floatService != null) {
//                        RFIDApplication.instance.floatService.release();
//                    }


//                if (false) {
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        if (adapter != null) {
////                            adapter.notifyDataSetChanged();
////                            tv_tags.setText(String.valueOf(uploadSet.size()));
////                        }
//
//                        if (list != null && uploadSet != null && itemShowAdapter != null) {
//                            itemShowAdapter.notifyDataSetChanged();
//                            if (uploadSet != null && uploadSet.size() != 0) {
//                                tv_tags.setText(String.valueOf(uploadSet.size()));
//                            }
//
//                        }
//
//
////                        RFIDApplication.instance.floatService = null;
//                    }
//                }, 100);

//                }


//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        list = new ArrayList<>();
//                        uploadSet = new HashSet<>();
//                        if (adapter == null) {
//                            adapter = new SuoFeiYaAdapter(
//                                    SuoFeiYaMainDemand2Activity.this,
//                                    list,
//                                    R.layout.listitemview_inv_suofeiya,
//                                    Coname,
//                                    new int[]{
//                                            R.id.textView_readsort,
//                                            R.id.textView_readepc,
//                                            R.id.textView_readcnt});
//
//                            // layout为listView的布局文件，包括三个TextView，用来显示三个列名所对应的值
//                            // ColumnNames为数据库的表的列名
//                            // 最后一个参数是int[]类型的，为view类型的id，用来显示ColumnNames列名所对应的值。view的类型为TextView
//                            listView.setAdapter(adapter);
//                        }
//                        else{
//                            adapter.notifyDataSetChanged();
//                        }
//
//                        tv_tags.setText(String.valueOf(uploadSet.size()));
//                        RFIDApplication.instance.floatService = null;
//
//                    }
//                }, 50);

//                if (promptDialog != null) {
//                    promptDialog.dismiss();
//                }


            }
        });

        button_clean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                promptDialog.viewAnimDuration = 100;
                promptDialog = new PromptDialog(SuoFeiYaMainDemand2Activity.this);
                promptDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(1000);
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

        mCheckSwithcButton = (CheckSwitchButton) findViewById(R.id.mCheckSwithcButton);
        tv_Title = (TextView) findViewById(R.id.tv_Title);
//        //默认是发货
//        if(StringHelper2.isEmpty(RFIDApplication.getModeIsBuHuo())){
//            RFIDApplication.setModeBuHuo(Const.CONST_FALSE);
//        }
//        boolean check ;
//        if(RFIDApplication.getModeIsBuHuo()==Const.CONST_TRUE){
//            check = true;
//            tv_Title.setText("取\t\t\t消");
//            uiBuHuo();
//        }
//        else{
//            check = false;
//            tv_Title.setText("发\t\t\t货");
//            uiFaHuo();
//        }

        LogUtil.e("TAG", "RFIDApplication.getModeIsBuHuo() = " + RFIDApplication.getModeIsBuHuo());
        //默认是发货
        if (StringHelper2.isEmpty(RFIDApplication.getModeIsBuHuo())) {
            RFIDApplication.setModeBuHuo(Const.CONST_FALSE);
        }
        boolean check;
        if (RFIDApplication.getModeIsBuHuo().equals(Const.CONST_FALSE)) {
            check = true;
            tv_Title.setText("发\t\t\t货");
            uiFaHuo();
            mCheckSwithcButton.setChecked(check);
        } else {

            check = false;
            tv_Title.setText("取\t\t\t消");
            uiBuHuo();
            mCheckSwithcButton.setChecked(check);
        }


//       LogUtil.e("TAG","RFIDApplication.getModeIsBuHuo() = "+RFIDApplication.getModeIsBuHuo());


        mCheckSwithcButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                LogUtil.e(TAG, "*** isChecked *** ----------------");
                LogUtil.e(TAG, "isChecked = " + isChecked);

//                if(isChecked){
//                    tv_Title.setText("取\t\t\t消");
//                    RFIDApplication.setModeBuHuo(Const.CONST_TRUE);
//                    uiBuHuo();
//
//                }
//                else{
//                    tv_Title.setText("发\t\t\t货");
//                    RFIDApplication.setModeBuHuo(Const.CONST_FALSE);
//                    uiFaHuo();
//                }
                if (isChecked) {

                    tv_Title.setText("发\t\t\t货");
                    RFIDApplication.setModeBuHuo(Const.CONST_FALSE);
                    uiFaHuo();
                } else {

                    tv_Title.setText("取\t\t\t消");
                    RFIDApplication.setModeBuHuo(Const.CONST_TRUE);
                    uiBuHuo();
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
        if (Const.IsCanTest == true) {
            tvVersionName.setText("当前版本:" + VersionUtil.getVersionName(RFIDApplication.instance) + "(仅供本人开发测试,生产环境不使用!)");
        } else {
            tvVersionName.setText("当前版本:" + VersionUtil.getVersionName(RFIDApplication.instance));
        }


        if (Const.IsCanTest == true
                &&
                StringHelper2.isEmpty(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_UPDATE))) {

            PreferenceUtil.set(RFIDApplication.instance, Const.KEY_URL_UPDATE,
                    ResHelper.getString(this, R.string.url_server_update));

            LogUtil.e("TAG", "-----------------------------------------------------");
            LogUtil.e("TAG", "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_UPDATE)" + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_UPDATE));
        }


        btCheckUpdate = (Button) findViewById(R.id.btCheckUpdate);
        btCheckUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUpdate();
            }
        });


        etSuffix = (EditText) findViewById(R.id.etSuffix);
        btSureSuffix = (Button) findViewById(R.id.btSureSuffix);

        tvUnPAStart = (TextView) findViewById(R.id.tvUnPAStart);
        tvSuffixNum = (TextView) findViewById(R.id.tvSuffixNum);
        tvUnSuffixNum = (TextView) findViewById(R.id.tvUnSuffixNum);
        btClearAll = (Button) findViewById(R.id.btClearAll);

        btSureSuffix.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.set(mContext, Const.KEY_JIE_WEN_TEST_SUFFIX, etSuffix.getText().toString().trim());
                ToastUtil.showToast(mContext, "设置成功!");
                JieWenWriteShow();
            }
        });
        btClearAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatService.initJieWenTest();
            }
        });

        tvAAA = (TextView) findViewById(R.id.tvAAA);
        tvUnAAA = (TextView) findViewById(R.id.tvUnAAA);
        btSortList = (Button) findViewById(R.id.btSortList);
        btSortList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list != null && list.size() != 0) {
                    DataSort.sortList(list);
                    uiFresh();
                    ToastUtil.showToast(mContext, "请稍候");
                } else {
                    ToastUtil.showToast(mContext, "没有数据!");
                }

            }
        });


        rv = (RecyclerView) findViewById(R.id.rv);


//        initAdapter();

        initRecycleViewAdapter();


        RFIDApplication.instance.suoFeiYaMainDemand2Activity = this;


//        Comm.mOtherHandler = opeHandler;


//        setPower();


        btSetPower.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e(TAG, "---------------------");
                LogUtil.e(TAG, " powerSetDialog()");
                powerSetDialog();
            }
        });


        checkSetPowerPreference();
        setDefaultFolateButton();

    }


    ItemShowAdapter2 itemShowAdapter;

    private void initRecycleViewAdapter() {
        list = MyBaseService.list;
        uploadSet = MyBaseService.uploadSet;
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        itemShowAdapter = new ItemShowAdapter2(list);
        rv.setAdapter(itemShowAdapter);
        itemShowAdapter.notifyDataSetChanged();
    }


//    protected void initAdapter() {
//        list = MyBaseService.list;
//        uploadSet = MyBaseService.uploadSet;
//        if (adapter == null) {
//            adapter = new SuoFeiYaAdapter(
//                    SuoFeiYaMainDemand2Activity.this,
//                    list,
//                    R.layout.listitemview_inv_suofeiya,
//                    Coname,
//                    new int[]{
//                            R.id.textView_readsort,
//                            R.id.textView_readepc,
//                            R.id.textView_readcnt});
//
//            // layout为listView的布局文件，包括三个TextView，用来显示三个列名所对应的值
//            // ColumnNames为数据库的表的列名
//            // 最后一个参数是int[]类型的，为view类型的id，用来显示ColumnNames列名所对应的值。view的类型为TextView
//            listView.setAdapter(adapter);
//        }
//    }


    public void checkUpdate() {
        PromptDialog.viewAnimDuration = 10;
        final PromptDialog promptDialog = new PromptDialog(SuoFeiYaMainDemand2Activity.this);
        promptDialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(10);
        promptDialog.showLoading("正在检查更新...");

        // 检查更新;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (RFIDApplication.getIsCanCheckUpdate()) {
                    LogUtil.e("TAG", "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_UPDATE) = " +
                            PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_UPDATE));
                    UpdateUtil.check(SuoFeiYaMainDemand2Activity.this, HttpRequestMethod.GET, new UpdateUtil.UpdateCheckListener() {
                        @Override
                        public void checkSuccess() {
                            if (promptDialog != null) {
                                promptDialog.dismiss();
                                LogUtil.e("TAG", "-----------------------------------------------------");
                                LogUtil.e("TAG", "-----------------   promptDialog.dismiss()   ------------------");
                            }
                        }

                        @Override
                        public void checkFail() {
                            if (promptDialog != null) {
                                promptDialog.dismiss();
                                LogUtil.e("TAG", "-----------------------------------------------------");
                                LogUtil.e("TAG", "-----------------   promptDialog.dismiss()   ------------------");
                            }
                        }

                        @Override
                        public void cannotCheck() {
                            if (promptDialog != null) {
                                promptDialog.dismiss();
                                LogUtil.e("TAG", "-----------------------------------------------------");
                                LogUtil.e("TAG", "-----------------   promptDialog.dismiss()   ------------------");
                            }
                        }
                    });
                }
            }
        }, 10);
    }


    private void uiBuHuo() {
        ((TextView) findViewById(R.id.tvBuHuo)).setTextColor(ResHelper.getColor(mContext, R.color.black));
        ((TextView) findViewById(R.id.tvFaHuo)).setTextColor(ResHelper.getColor(mContext, R.color.gray));
    }

    private void uiFaHuo() {
        ((TextView) findViewById(R.id.tvBuHuo)).setTextColor(ResHelper.getColor(mContext, R.color.gray));
        ((TextView) findViewById(R.id.tvFaHuo)).setTextColor(ResHelper.getColor(mContext, R.color.black));
    }


    private PromptDialog promptDialog;
    private PromptDialog setPowerDialog;
    CheckSwitchButton mCheckSwithcButton;

    private void startService() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                RFIDApplication.setPowerTimesZero();
                if (RFIDApplication.instance.floatService == null) {
                    Intent service = new Intent();
                    service.setClass(SuoFeiYaMainDemand2Activity.this, FloatService.class);
                    startService(service);
                } else {
                    RFIDApplication.instance.serviceIsStop = false;
                    RFIDApplication.instance.floatService.showFloatView();

                }

//                Intent service = new Intent();
//                service.setClass(SuoFeiYaMainDemand2Activity.this, FloatService.class);
//                startService(service);


            }
        }, 50);

    }

    private void stopService() {
        RFIDApplication.instance.serviceIsStop = true;
        if (RFIDApplication.instance.floatService != null) {
            RFIDApplication.instance.floatService.hideFloatView();
//            RFIDApplication.instance.floatService.release();

        }

//        Intent serviceStop = new Intent();
//        serviceStop.setClass(SuoFeiYaMainDemand2Activity.this, FloatService.class);
//        stopService(serviceStop);
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


    private Dialog getDialog(Context context) {
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


        String urlUpdate = PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_UPDATE, "");
        Button btSure = (Button) dialog.findViewById(R.id.btSure);
        final TextInputEditText etUrlUpdate = (TextInputEditText) dialog.findViewById(R.id.etUrlUpdate);
        etUrlUpdate.setText(urlUpdate);
        btSure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etUrlUpdate.getText().toString().trim();
                if (StringHelper2.judeIsLegalURL(url)) {
                    PreferenceUtil.set(RFIDApplication.instance, Const.KEY_URL_UPDATE, url);
                    LogUtil.i("TAG", "软件更新地址 = " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_UPDATE));
                    ToastUtil.showToast(RFIDApplication.instance, "修改成功!");
                } else {
                    ToastUtil.showToast(RFIDApplication.instance, "修改失败!地址格式不正确!");
                }
            }
        });


        CheckSwitchButton checkSwitchButton = (CheckSwitchButton) dialog.findViewById(R.id.mCheckSwithcButton);
        LogUtil.e("TAG", "RFIDApplication.getModeIsBuHuo() = " + RFIDApplication.getModeIsBuHuo());
        //默认是发货
        if (StringHelper2.isEmpty(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_FLOAT_BUTTON))) {
            PreferenceUtil.set(RFIDApplication.instance, Const.KEY_FLOAT_BUTTON, Const.CONST_TRUE);
        }
        boolean check;
        //左边是true  右边是false
        if (PreferenceUtil.get(RFIDApplication.instance, Const.KEY_FLOAT_BUTTON).equals(Const.CONST_FALSE)) {
            check = false;
            checkSwitchButton.setChecked(check);
        } else {

            check = true;
            checkSwitchButton.setChecked(check);
        }

        checkSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PreferenceUtil.set(RFIDApplication.instance, Const.KEY_FLOAT_BUTTON, Const.CONST_TRUE);

                } else {
                    PreferenceUtil.set(RFIDApplication.instance, Const.KEY_FLOAT_BUTTON, Const.CONST_FALSE);
                }
            }
        });


        if (false) {
            ArrayAdapter arradp_pow = new ArrayAdapter<>(this,
                    R.layout.my_item_simple_spinner, Comm.spipow);
            final Spinner spinner_ant1pow = (Spinner) dialog.findViewById(R.id.spinner_ant1pow);

            LogUtil.e(TAG, "--------------------------");
            if (arradp_pow == null) {
                LogUtil.e(TAG, "arradp_pow = null");
            } else {
                LogUtil.e(TAG, "arradp_pow != null");
            }
            LogUtil.e(TAG, "--------------------------");
            if (spinner_ant1pow == null) {
                LogUtil.e(TAG, "spinner_ant1pow = null");
            } else {
                LogUtil.e(TAG, "spinner_ant1pow != null");
            }

            spinner_ant1pow.setAdapter(arradp_pow);
            Button btSetPower = (Button) dialog.findViewById(R.id.btSetPower);
            btSetPower.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ant1pow = spinner_ant1pow.getSelectedItemPosition();
                    mySetPower(ant1pow);
                    PreferenceUtil.set(RFIDApplication.instance, Const.KEY_POWER, ant1pow + "");
                    Toast.makeText(RFIDApplication.instance,
                            "设置成功!", Toast.LENGTH_SHORT)
                            .show();
//                ToastUtil.showToast(mContext,"设置成功!");
                }
            });

            int selectPower = 5;
            try {
                selectPower = Integer.parseInt(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER));

            } catch (Exception e) {
                e.printStackTrace();
            }

            spinner_ant1pow.setSelection(selectPower);
        }


        if (Const.IsCanTest == true) {
            setJieWenTest(dialog);

        }


        Button btPower = (Button) dialog.findViewById(R.id.btPower);
        btPower.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtil.startActivity(SuoFeiYaMainDemand2Activity.this,Sub4TabActivity.class,null);


                LogUtil.e(TAG, "---------------------");
                LogUtil.e(TAG, " powerSetDialog()");
                powerSetDialog();
            }
        });

        int lastSetPower = 30;


        tvShowPower = (TextView) dialog.findViewById(R.id.tvShowPower);
        setUIShowPower();

        dialog.show();
        return dialog;
    }

    TextView tvShowPower;

    private void setUIShowPower() {
        if (tvShowPower != null) {
            if (!StringHelper2.isEmpty(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER))) {
                tvShowPower.setText("设置功率: (当前功率 " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER) + ")");
            } else {
                tvShowPower.setText("设置功率: (当前功率30)");
            }
        }

    }


    public void hideAlerDialog() {
        if (alertDialog != null) {
            alertDialog.hide();
        }
    }

    AlertDialog alertDialog;
    EditText etNum;

    public void powerSetDialog() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View changeView = factory.inflate(R.layout.dialog_input_num, null);
        etNum = (EditText) changeView.findViewById(R.id.etNum);


        int lastSetPower = 30;
        if (!StringHelper2.isEmpty(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER))) {
            etNum.setHint("请输入: (当前功率:" + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER) + ")");
        } else {
            etNum.setHint("请输入: (当前功率:" + String.valueOf(lastSetPower) + ")");
        }


//        etNum.setHint("请输入");


        alertDialog = new AlertDialog.Builder(mContext).setTitle("设置范围在10到30之间")
                .setView(changeView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if (etNum != null) {
                            InputMethodUtil.forceHideKeyBoard(RFIDApplication.instance, etNum);
                        }

//                        mHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//
////                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////                                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
//
//                            }
//                        },50);


                        if (StringHelper2.isEmpty(etNum.getText().toString().trim())) {
                            return;
                        }
                        if (etNum.getText().toString().trim().startsWith("0")) {
                            Toast.makeText(RFIDApplication.instance, "输入有误", Toast.LENGTH_LONG).show();
                            return;
                        }

                        int power = 30;
                        try {
                            power = Integer.valueOf(etNum.getText().toString().trim());
                        } catch (Exception e) {
                            LogUtil.e(TAG, "--------------");
                            LogUtil.e(TAG, "power Exception e = " + e.toString());
                        }
                        if (power < 10) {
                            Toast.makeText(RFIDApplication.instance, "设置失败!" + "\n" + "不能小于10", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (power > 30) {
                            Toast.makeText(RFIDApplication.instance, "设置失败!" + "\n" + "不能大于30", Toast.LENGTH_LONG).show();
                            return;
                        }

                        PreferenceUtil.set(RFIDApplication.instance, Const.KEY_POWER, power + "");


//                        mySetPower(power);

                        if (RFIDApplication.instance.floatService != null) {
//                            RFIDApplication.setIsCanShowToastTrue();
                            RFIDApplication.setPowerTimesZero();
                            RFIDApplication.instance.floatService.setPower();
                        }


                        setUIShowPower();


                        hideAlerDialog();
                        RFIDApplication.instance.isCanRead = false;

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setPowerDialog.viewAnimDuration = 10;
                                setPowerDialog = new PromptDialog(SuoFeiYaMainDemand2Activity.this);
                                setPowerDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(500);
                                setPowerDialog.showLoading("正在设置...");
                            }
                        }, 100);


                        mHandler.postDelayed(runnable1, 1000);


                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (etNum != null) {
                            InputMethodUtil.forceHideKeyBoard(RFIDApplication.instance, etNum);
                        }
                        hideAlerDialog();
                    }
                }).create();
        alertDialog.show();

    }



    public void setJieWenTest(Dialog dialog) {
        if (true) {
            CheckSwitchButton csbTestData = (CheckSwitchButton) dialog.findViewById(R.id.csbTestData);
            //默认是发货
            if (StringHelper2.isEmpty(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_JIE_WEN_TEST))) {
                PreferenceUtil.set(RFIDApplication.instance, Const.KEY_IS_CAN_JIE_WEN_TEST, Const.CONST_TRUE);
            }
            boolean check;
            //左边是true  右边是false
            if (PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_JIE_WEN_TEST).equals(Const.CONST_FALSE)) {
                check = false;
                csbTestData.setChecked(check);
            } else {

                check = true;
                csbTestData.setChecked(check);
            }

            csbTestData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        PreferenceUtil.set(RFIDApplication.instance, Const.KEY_IS_CAN_JIE_WEN_TEST, Const.CONST_TRUE);

                    } else {
                        PreferenceUtil.set(RFIDApplication.instance, Const.KEY_IS_CAN_JIE_WEN_TEST, Const.CONST_FALSE);
                    }
                    initTestJieWenWrite();
                }
            });
        }

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
        this.btSetPower.setEnabled(true);
        this.button_set.setEnabled(false);
        //liudongwen
        this.butSetUrl.setEnabled(false);
        this.button_clean.setEnabled(false);
        TabWidget tw = Comm.supoinTabHost.getTabWidget();
        tw.getChildAt(1).setEnabled(false);
        tw.getChildAt(2).setEnabled(false);

        btCheckUpdate.setEnabled(false);
    }

    private void StopHandleUI() {
        button_read.setEnabled(true);
        button_stop.setEnabled(false);
        btSetPower.setEnabled(false);
        this.button_set.setEnabled(true);
        //liudongwen
        this.butSetUrl.setEnabled(true);
        this.button_clean.setEnabled(true);
        TabWidget tw = Comm.supoinTabHost.getTabWidget();
        tw.getChildAt(1).setEnabled(true);
        tw.getChildAt(2).setEnabled(true);

        btCheckUpdate.setEnabled(true);

    }

    public void setBtSetPowerTrueEnable() {
        if (btSetPower != null) {
            btSetPower.setEnabled(true);
        }

    }

    public void setBtSetPowerFalseEnable() {
        if (btSetPower != null) {
            btSetPower.setEnabled(false);
        }

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


    volatile List<Map<String, String>> list;

    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //todo 看要不要做些什么
//            uiFresh();

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
        LogUtil.e(TAG, "onResume()");
//        String name = PreferenceUtil.get(mContext, Const.KEY_STAGE_NMAE, "");
//        if (StringHelper2.isEmpty(name)) {
//            selectChannel();
//
//        } else {
//            tvChannel.setText(name);
//            tvChannel.setTextColor(getResources().getColor(R.color.black));
//        }
//
//        //todo
//        uiFresh();
//
//        //这里主要是为了显示UI
//        if (RFIDApplication.instance.floatService != null) {
//            button_read.performClick();
//            ReadHandleUI();
//        }
//
//        if(PreferenceUtil.get(mContext,"ISREAD").equals("TRUE")){
//            ReadHandleUI();
//        };


    }


    @Override
    protected void onStop() {
        if (mDialog != null) {
            mDialog.dismiss();
        }

        super.onStop();
    }

    public void uiFresh() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (Const.IsCanTest == true) {
                    JieWenWriteShow();
                }


                try {

                    if (uploadSet != null
                            && list != null
                            && adapter != null) {

                        //              listView.post(runToLast);

                        tv_tags.setText(String.valueOf(uploadSet.size()));

                        adapter.notifyDataSetChanged();

                        return;
                    }

                    if (uploadSet != null
                            && list != null
                            && itemShowAdapter != null) {

                        tv_tags.setText(String.valueOf(uploadSet.size()));
                        itemShowAdapter.notifyDataSetChanged();

                        return;
                    }

                } catch (Exception e) {
                    LogUtil.e(TAG, "--------------------");
                    LogUtil.e(TAG, e + "");
                }


            }
        });


//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (RFIDApplication.instance.floatService != null) {
//                    uploadSet = RFIDApplication.instance.floatService.uploadSet;
//                    list = RFIDApplication.instance.floatService.list;
//                }
//                if (list == null) {
//                    list = new ArrayList<>();
//                }
//                if (uploadSet == null) {
//                    uploadSet = new HashSet<>();
//                }
//
//
////                if (adapter == null) {
//                    adapter = new SuoFeiYaAdapter(
//                            SuoFeiYaMainDemand2Activity.this,
//                            list,
//                            R.layout.listitemview_inv_suofeiya,
//                            Coname,
//                            new int[]{
//                                    R.id.textView_readsort,
//                                    R.id.textView_readepc,
//                                    R.id.textView_readcnt});
//
//                    // layout为listView的布局文件，包括三个TextView，用来显示三个列名所对应的值
//                    // ColumnNames为数据库的表的列名
//                    // 最后一个参数是int[]类型的，为view类型的id，用来显示ColumnNames列名所对应的值。view的类型为TextView
//                    listView.setAdapter(adapter);
//
////                    listView.post(runToLast);
//                    tv_tags.setText(String.valueOf(uploadSet.size()));
////                }
////                else{
////                    adapter.notifyDataSetChanged();
////                    listView.post(runToLast);
////                    tv_tags.setText(String.valueOf(uploadSet.size()));
////                }
//
//            }
//        });


    }


    private boolean isAccessibilitySettingsOn(Context mContext) {
        return AccessibilityServiceUtil.isAccessibilitySettingsOn(mContext, MyAccessibilityService.class);

    }


    Dialog mDialog;

    public void addViewAndShowInfo(String title, String content) {
        LogUtil.e("TAG", "addViewAndShowInfo=============>1");

        if (mDialog != null) {
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


    @Override
    protected void onPause() {
        LogUtil.e("TAG", "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL) = " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_IS_CAN_INSTALL));

        super.onPause();
    }

    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(false);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    //            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Comm.opeT = Comm.operateType.getPower;
//                    getAntPower();
//                }
//            },2000);


    private void mySetPower(final int ant1pow) {

        RFIDApplication.setIsCanShowToastTrue();

        try {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Comm.opeT = Comm.operateType.setPower;
                    //            int ant1pow = 25;
                    int ant2pow = 0;
                    int ant3pow = 0;
                    int ant4pow = 0;
                    int an1 = (ant1pow - 5);

                    setAntPower(an1, ant2pow, ant3pow, ant4pow);
                }
            }, 20);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(RFIDApplication.instance,
                    "Set Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();

            LogUtil.e("TAG", "----------------------");
            LogUtil.e("TAG", "Set Exception:" + e.getMessage());
            return;
        }
    }


    long exit = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (!RFIDApplication.instance.isCanRead && RFIDApplication.instance.serviceIsStop == true) {

                if ((System.currentTimeMillis() - exit) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出",
                            Toast.LENGTH_SHORT).show();
                    exit = System.currentTimeMillis();

                } else if (RFIDApplication.instance.floatService != null) {

                    RFIDApplication.instance.floatService.release();

                    RFIDApplication.instance.floatService = null;
                    RFIDApplication.instance.suoFeiYaMainDemand2Activity = null;

                    Intent serviceStop = new Intent();
                    serviceStop.setClass(SuoFeiYaMainDemand2Activity.this, FloatService.class);
                    stopService(serviceStop);

                    finish();

                    LogUtil.e("TAG", "----------------------");
                    LogUtil.e("TAG", "   finish()");

                    System.exit(0);


                }

                return true;
            } else {
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addCategory(Intent.CATEGORY_HOME);
                startActivity(i);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    String setP = "" + Const.CONST_SET_POWER;

    @SuppressLint("HandlerLeak")
//    private  android.os.Handler opeHandler = new android.os.Handler() {
//            @SuppressWarnings({"unchecked", "unused"})
//            @Override
//            public void handleMessage(Message msg) {
//                try {
//                    Bundle b = msg.getData();
//
//                    if (promptDialog != null) {
//                        promptDialog.dismiss();
//                    }
//
//                    LogUtil.e(TAG, "--------------->>>>>>>>>--------------");
//                    LogUtil.e(TAG, "Comm.opeT = " + Comm.opeT);
//                    switch (Comm.opeT) {
//
//
//                        case getPower:
//                            try {
//                                b = msg.getData();
//                                int ant1Power = b.getInt("ant1Power");
//                                int ant2Power = b.getInt("ant2Power");
//                                int ant3Power = b.getInt("ant3Power");
//                                int ant4Power = b.getInt("ant4Power");
//                                LogUtil.e(TAG, "-------------------");
//                                LogUtil.e(TAG, "ant1Power = " + ant1Power);
//                                LogUtil.e(TAG, "-------------------");
//                                LogUtil.e(TAG, "ant2Power = " + ant2Power);
//                                LogUtil.e(TAG, "-------------------");
//                                LogUtil.e(TAG, "ant3Power = " + ant3Power);
//                                LogUtil.e(TAG, "-------------------");
//                                LogUtil.e(TAG, "ant4Power = " + ant4Power);
//
//
//
//                                if(ant1Power!=0 && RFIDApplication.getPowerTimes()<=3){
//                                    String original = String.valueOf((ant1Power + 5));
//
//                                    if (!StringHelper2.isEmpty(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER))) {
//                                        setP = PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER);
//                                    }
//                                    LogUtil.e(TAG, "-------------------");
//                                    LogUtil.e(TAG, "setP = " + setP);
//
//                                    setIsUnShowPowerSetToastTrue();
//                                    afterGetPower2SetPower(original);
//                                }
//
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                LogUtil.e(TAG, "-------------------");
//                                LogUtil.e(TAG, "Exception e = " + e.toString());
//                                Log.e("uhf6readOp", "Exception" + e.getMessage());
//                            }
//                            break;
//                        case setPower:
//                            b = msg.getData();
//                            boolean isSetPower = b.getBoolean("isSetPower");
//
//                            LogUtil.e(TAG, "-------------------");
//                            LogUtil.e(TAG, "getIsUnShowPowerToast() = " + getIsUnShowPowerToast());
//                            if (isSetPower) {
//                                /***
//                                 * 打开开始按钮,我检测情况会调用一次,这次不用显示; 第2次就是人工调用的了,所以可以显示了;
//                                 */
//                                if (!getIsUnShowPowerToast()) {
//                                    if(RFIDApplication.getIsCanShowToastTrue()){
//                                        Toast.makeText(RFIDApplication.instance, "设置功率成功!", Toast.LENGTH_SHORT).show();
//                                    }
//
////                            Toast.makeText(mContext, "Set Succeed", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                if (!getIsUnShowPowerToast()) {
////                                Toast.makeText(mContext, "Set Fail!", Toast.LENGTH_SHORT).show();
//                                    if(RFIDApplication.getIsCanShowToastTrue()){
//                                        Toast.makeText(RFIDApplication.instance, "设置功率失败!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                            RFIDApplication.setIsCanShowToastFalse();
//                            setIsUnShowPowerSetToastFalse();
//                            dismissSetPowerDialog();
//
//                            break;
//
//
//                        default:
//                            break;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    LogUtil.e(TAG, "-------------------");
//                    LogUtil.e(TAG, "Exception e = " + e.toString());
//                }
//            }
//        };


//    private  android.os.Handler opeHandler;
//    protected void initOpeHandler(){
//        opeHandler = new android.os.Handler() {
//            @SuppressWarnings({"unchecked", "unused"})
//            @Override
//            public void handleMessage(Message msg) {
////                try {
////                    Bundle b = msg.getData();
////
////                    if (promptDialog != null) {
////                        promptDialog.dismiss();
////                    }
////
////                    LogUtil.e(TAG, "--------------->>>>>>>>>--------------");
////                    LogUtil.e(TAG, "Comm.opeT = " + Comm.opeT);
////                    switch (Comm.opeT) {
////
////
////                        case getPower:
////                            try {
////                                b = msg.getData();
////                                int ant1Power = b.getInt("ant1Power");
////                                int ant2Power = b.getInt("ant2Power");
////                                int ant3Power = b.getInt("ant3Power");
////                                int ant4Power = b.getInt("ant4Power");
////                                LogUtil.e(TAG, "-------------------");
////                                LogUtil.e(TAG, "ant1Power = " + ant1Power);
////                                LogUtil.e(TAG, "-------------------");
////                                LogUtil.e(TAG, "ant2Power = " + ant2Power);
////                                LogUtil.e(TAG, "-------------------");
////                                LogUtil.e(TAG, "ant3Power = " + ant3Power);
////                                LogUtil.e(TAG, "-------------------");
////                                LogUtil.e(TAG, "ant4Power = " + ant4Power);
////
////
////                                if(RFIDApplication.getPowerTimes()>1){
////                                    return;
////                                }
////
////                                Log.e("TAG","ant1Power =" + ant1Power);
////
////                                if(ant1Power!=0 && RFIDApplication.getPowerTimes()<=1){
////                                    String original = String.valueOf((ant1Power + 5));
////
////                                    if (!StringHelper2.isEmpty(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER))) {
////                                        setP = PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER);
////                                    }
////                                    LogUtil.e(TAG, "-------------------");
////                                    LogUtil.e(TAG, "setP = " + setP);
////
////                                    Log.e("TAG","setP =" + setP);
////
////                                    setIsUnShowPowerSetToastTrue();
////                                    afterGetPower2SetPower(original);
////                                }
////
////
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                                LogUtil.e(TAG, "-------------------");
////                                LogUtil.e(TAG, "Exception e = " + e.toString());
////                                Log.e("uhf6readOp", "Exception" + e.getMessage());
////                            }
////                            break;
////                        case setPower:
////
////
////
////                            b = msg.getData();
////                            final boolean isSetPower = b.getBoolean("isSetPower");
////
////                            LogUtil.e(TAG, "-------------------");
////                            LogUtil.e(TAG, "getIsUnShowPowerToast() = " + getIsUnShowPowerToast());
////
////                            if(RFIDApplication.getPowerTimes()>1){
//////                                dismissSetPowerDialog();
////                                return;
////                            }
////
//////                            mHandler.postDelayed(new Runnable() {
//////                                @Override
//////                                public void run() {
//////                                    if (isSetPower) {
//////                                        /***
//////                                         * 打开开始按钮,我检测情况会调用一次,这次不用显示; 第2次就是人工调用的了,所以可以显示了;
//////                                         */
//////                                        if (!getIsUnShowPowerToast()) {
//////                                            if(RFIDApplication.getIsCanShowToastTrue()){
//////                                                Toast.makeText(RFIDApplication.instance, "设置功率成功!", Toast.LENGTH_SHORT).show();
//////                                            }
//////
////////                            Toast.makeText(mContext, "Set Succeed", Toast.LENGTH_SHORT).show();
//////                                        }
//////                                    } else {
//////                                        if (!getIsUnShowPowerToast()) {
////////                                Toast.makeText(mContext, "Set Fail!", Toast.LENGTH_SHORT).show();
//////                                            if(RFIDApplication.getIsCanShowToastTrue()){
//////                                                Toast.makeText(RFIDApplication.instance, "设置功率失败!", Toast.LENGTH_SHORT).show();
//////                                            }
//////                                        }
//////                                    }
//////                                    RFIDApplication.setIsCanShowToastFalse();
//////                                    setIsUnShowPowerSetToastFalse();
//////                                    dismissSetPowerDialog();
//////                                }
//////                            },1);
////
////
////                            break;
////
////
////                        default:
////                            break;
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                    LogUtil.e(TAG, "-------------------");
////                    LogUtil.e(TAG, "Exception e = " + e.toString());
////                }
//            }
//        };
//    }

    public void dismissSetPowerDialog() {
        mHandler.postDelayed(runnable1, 1000);
    }

    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            RFIDApplication.instance.isCanRead = true;
            if (setPowerDialog != null) {
                setPowerDialog.dismiss();
            }
        }
    };


    public void chekPowerIsLastSetOrNot() {
        if (RFIDApplication.getPowerTimes() >= 1) {
            return;
        }

        RFIDApplication.addSetPowerTimes();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int p = 30;
                        try {
                            p = Integer.valueOf(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER, "25"));

                        } catch (Exception e) {
                            LogUtil.e(TAG, "------------");
                            LogUtil.e(TAG, "e = " + e.toString());
                        }
                        LogUtil.e(TAG, "-------------------");
                        LogUtil.e(TAG, "p = " + p);
//                            RFIDApplication.addSetPowerTimes();
                        Log.e("TAG", "P =" + p);
                        mySetPower(p);
                    }
                }).start();

            }
        }, 20);


//        try {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Comm.opeT = Comm.operateType.getPower;
//                    getAntPower();
//
//                    LogUtil.e(TAG, "-------------------");
//                    LogUtil.e(TAG, "chekPowerIsLastSetOrNot()");
//                }
//            }).start();
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            Toast.makeText(RFIDApplication.instance,
//                    "Obtain Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
//                    .show();
//        }
    }


    private void afterGetPower2SetPower(String original) {
        if (RFIDApplication.getPowerTimes() > 1) {
            return;
        }

        Log.e("TAG", "original =" + original);
        Log.e("TAG", "setP =" + setP);
        if (!original.equals(setP)) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int p = 30;
                            try {
                                p = Integer.valueOf(setP);

                            } catch (Exception e) {
                                LogUtil.e(TAG, "------------");
                                LogUtil.e(TAG, "e = " + e.toString());
                            }
                            LogUtil.e(TAG, "-------------------");
                            LogUtil.e(TAG, "p = " + p);
//                            RFIDApplication.addSetPowerTimes();
                            Log.e("TAG", "P =" + p);
                            mySetPower(p);
                        }
                    }).start();

                }
            }, 20);
        }
        LogUtil.e(TAG, "-------------------");
        LogUtil.e(TAG, "afterGetPower2SetPower(String original)");
    }

    private void checkSetPowerPreference() {
        if (StringHelper2.isEmpty(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER))) {
            PreferenceUtil.set(RFIDApplication.instance, Const.KEY_POWER, "25");
            LogUtil.e(TAG, "-------------------");
            LogUtil.e(TAG, "PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER)  =" + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_POWER));
        }
    }

    private void setDefaultFolateButton() {
        //默认是发货
        if (StringHelper2.isEmpty(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_FLOAT_BUTTON))) {
            PreferenceUtil.set(RFIDApplication.instance, Const.KEY_FLOAT_BUTTON, Const.CONST_TRUE);
        }
    }

    private static boolean unShowPowerToast = RFIDApplication.unShowPowerToast;

    private static void setIsUnShowPowerSetToastTrue() {
        unShowPowerToast = true;
    }

    private static void setIsUnShowPowerSetToastFalse() {
        unShowPowerToast = false;
    }

    private static boolean getIsUnShowPowerToast() {
        return unShowPowerToast;
    }


    public void showUploading() {
        promptDialog.viewAnimDuration = 10;
        promptDialog = new PromptDialog(SuoFeiYaMainDemand2Activity.this);
        promptDialog.getDefaultBuilder().touchAble(false).round(3).loadingDuration(500);
        promptDialog.showLoading("正在上传...");


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (promptDialog != null) {
                    promptDialog.dismiss();
                }

            }
        }, 500);

    }




}