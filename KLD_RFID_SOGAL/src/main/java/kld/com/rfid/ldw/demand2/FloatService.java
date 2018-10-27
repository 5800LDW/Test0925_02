package kld.com.rfid.ldw.demand2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.PreferenceUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.ToastUtil;
import com.ldw.xyz.util.string.StringHelper2;
import com.maning.mndialoglibrary.view.MCircularProgressBar;
import com.uhf.uhf.Common.Comm;
import com.uhf.uhf.UHF1.UHF1Application;
import com.uhf.uhf.UHF1.UHF1Function.AndroidWakeLock;
import com.uhf.uhf.UHF1.UHF1Function.SPconfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.R;
import kld.com.rfid.ldw.RFIDApplication;
import kld.com.rfid.ldw.bean.ResponseObj;
import kld.com.rfid.ldw.demand2.baseService.MyBaseService;
import kld.com.rfid.ldw.demand2.logepc.LogEpcID;
import kld.com.rfid.ldw.demand2.logepc2.LogEpcID2;
import kld.com.rfid.ldw.demand2.sound.SoundUtil;
import kld.com.rfid.ldw.util.DataDoWithUtil;
import kld.com.rfid.ldw.util.GsonUtil;
import okhttp3.Call;
import okhttp3.Request;

import static com.uhf.uhf.Common.Comm.Awl;
import static com.uhf.uhf.Common.Comm.Connect;
import static com.uhf.uhf.Common.Comm.ConnectModule;
import static com.uhf.uhf.Common.Comm.checkDevice;
import static com.uhf.uhf.Common.Comm.context;
import static com.uhf.uhf.Common.Comm.isQuick;
import static com.uhf.uhf.Common.Comm.isrun;
import static com.uhf.uhf.Common.Comm.lsTagList;
import static com.uhf.uhf.Common.Comm.lsTagList6B;
import static com.uhf.uhf.Common.Comm.soundPool;
import static com.uhf.uhf.Common.Comm.tagListSize;
import static kld.com.rfid.ldw.demand2.sound.SoundUtil.initExecutorSoundUtil;

@TargetApi(23)
public class FloatService extends MyBaseService implements OnClickListener, Runnable {

    public static Set<String> unUPploadSet = new HashSet<>();

    private static final String TAG = "FloatService";

    boolean isTest = RFIDApplication.getIsCanTest();


    protected Object cancelTag = this;
    private Message msg = null;
    private long mlCount = 0;
    private int totalSec, yushu, min, sec;
    //liudongwen
    String[] Coname = Const.Coname;
    //liudongwen
    public int uploadSetStartSize = 0;

    //    public FloatService floatService;

    Context mContext;
    public int totalTagsSum = 0;


    WindowManager wm = null;
    WindowManager.LayoutParams wmParams = null;
    View view;
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    int state;
    ImageView iv;
    ImageView imageView1;
    View tvTips;
    RelativeLayout alertDialog;
    TextView tvTotalScan;
    View dialog_window_background;
    private float StartX;
    private float StartY;
    int delaytime = 1000;

    ActionUpReceiver actionUpReceiver;

    // begin
    private int scanCount = 0;

    //20181012
    private int updateState = Const.CONST_NOT_UPDATE;

    public static int unPAStartNum = 0;
    public static int unAAAStartNum = 0;
    public static int AAAStartNum = 0;

    public static void initJieWenTest() {
        unPAStartNum = 0;
        unAAAStartNum = 0;
        AAAStartNum = 0;
    }


//    private static final String TAG = "FloatService ";
//	private static boolean isAutoScanning = false;

    // end
    public void InitDevice() {
        //盘点到重复标签是否发出声音
        Comm.repeatSound = false;
        //getApplication()迷惑客户，往往导致出错
//        Comm.app = getApplication();
        Comm.app = new UHF1Application();
        Comm.spConfig = new SPconfig(this);
        context = RFIDApplication.instance;

//        Comm.soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
//        Comm.soundPool.load(this, R.raw.beep51, 1);

        Comm.soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        Comm.soundPool.load(RFIDApplication.instance, R.raw.beep51, 1);


        LogUtil.e(TAG, "*** soundPool *** ----------------");
        if (soundPool == null) {
            LogUtil.e(TAG, " soundPool == null");
        } else {
            LogUtil.e(TAG, " soundPool == " + soundPool);
        }
        LogUtil.e(TAG, "soundPool");

        checkDevice();
        Comm.initWireless(Comm.app);
        Comm.connecthandler = connectH;
//        Comm.moduleType = null;
        if (Comm.moduleType != null)
            ConnectModule();
        else
            Connect();
        LogUtil.e(TAG, "-----------------------------------");
        LogUtil.e(TAG, "connect");


    }

    @SuppressLint("HandlerLeak")
    public Handler connectH = new Handler() {
        @SuppressLint("SetTextI18n")
        @SuppressWarnings({"unchecked", "unused"})
        @Override
        public void handleMessage(Message msg) {
            try {

                connectHTimes++;

                Comm.mInventoryHandler = uhfhandler;

                Comm.SetInventoryTid(false);

                //20181026
//                Bundle bd = msg.getData();
//                String strMsg = bd.get("Msg").toString();
//                if(!strMsg.equals("")&&strMsg!=null){
//                    Comm.setSes(1);
//                }
                Comm.setSes(1);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("connectH", e.getMessage());
            }
        }
    };


    int uhfhandlerTimes = 0;
    int connectHTimes = 0;

    int testInt = 100000;


    @SuppressLint("HandlerLeak") //todo
    public Handler uhfhandler = new Handler() {
        @SuppressWarnings({"unchecked", "unused"})
        @Override
        public void handleMessage(Message msg) {

            uhfhandlerTimes++;


            try {
                if (Comm.is6BTag) {
                    tagListSize = lsTagList6B.size();
                } else {
                    tagListSize = lsTagList.size();
                }

                Bundle bd = msg.getData();

                int readCount = bd.getInt("readCount");


                if (readCount > 0) {
                    //显示数量
                    //tv_state.setText(String.valueOf(readCount));
                }

//                Log.e("tagListSize", "uhfhandler tagListSize  : " + tagListSize);//lsTagList
//                Log.e("lsTagList", "lsTagList.size() : " + lsTagList.size());//lsTagList
//                LogUtil.e("lsTagList", "lsTagList.size() : " + lsTagList);//lsTagList
//                Log.e("lsTagList", "lsTagList : " + lsTagList.toString());//lsTagList
                //todo  重点关注这个;

//				if(tagListSize>0){
//					for(int i=0;i<tagListSize;i++){
//						container.put(lsTagList.get(i));
//					}
//				}


                if (tagListSize > 0 && tagListSize > uploadSet.size()) {


                    if (false) {
                        LogUtil.e(TAG, "------------------------------------");
                        LogUtil.e(TAG, "uploadSet.size()  : " + uploadSet.size());
                        LogEpcID2.put("uploadSet.size()  : " + uploadSet.size(), testInt++);
                        LogUtil.e(TAG, "tagListSize  : " + tagListSize);
                        LogUtil.e(TAG, "------------------------------------");
                        LogUtil.e(TAG, "uploadSet   =  " + uploadSet);
                        LogEpcID2.put("uploadSet  : " + uploadSet, testInt++);
                        LogUtil.e(TAG, "lsTagList   =  " + lsTagList);
                        LogEpcID2.put("lsTagList  : " + lsTagList, testInt++);

                        LogUtil.e(TAG, "------ readCount : " + String.valueOf(readCount));
                        for (int i = 0; i < lsTagList.size(); i++) {
                            String epcstr = lsTagList.get(i).strEPC;

//                    LogUtil.e(TAG,"lsTagList.get(ListIndex).strEPC  epcstr ="+epcstr);

                            if (Comm.dt == Comm.DeviceType.supoin_JT && Comm.To433Index < index) {
                                Comm.mWirelessMg.writeTo433(epcstr + "\n");
                                Comm.To433Index++;
                            }

                            if (epcstr != null && !epcstr.equals("")) {
                                epcstr = lsTagList.get(i).strEPC.replace(" ", "");
                                if (epcstr.length() > 24) {
                                    epcstr = epcstr.substring(0, 24) + "\r\n" + epcstr.substring(24);
                                }
                                //TODO 数据处理
//                            epcstr = DataDoWithUtil.dataConversion(epcstr);
                                Log.e(TAG, "------------------------------------");
                                LogUtil.e(TAG, "  epcstr =" + epcstr);

                                LogEpcID2.put("------------------------------------", testInt++);
                                LogEpcID2.put("  epcstr =" + epcstr, testInt++);
                                LogEpcID2.put(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", testInt++);
                            }
                        }
                        LogUtil.e(TAG, "------------------------------------");
                        LogUtil.e(TAG, "uhfhandler times  : " + uhfhandlerTimes);
                        LogUtil.e(TAG, "------------------------------------");
                        LogUtil.e(TAG, "connectHTimes times  : " + connectHTimes);
                    }


                    showlist();

                }

//                Log.e("uhfhandler", "readCount : " + String.valueOf(readCount));

            } catch (Exception e) {
                e.printStackTrace();
                com.ldw.xyz.util.exception.ExceptionUtil.handleException(e);
                LogUtil.e(TAG, e.getMessage());
            }
        }
    };


    @Override
    public void onCreate() {
        LogUtil.e(TAG, "onCreate");
        super.onCreate();

        if (RFIDApplication.getIsDefaultFloatButton() == false) {
            view = LayoutInflater.from(this).inflate(R.layout.floating2, null);
        } else {
            view = LayoutInflater.from(this).inflate(R.layout.floating, null);
        }


        iv = (ImageView) view.findViewById(R.id.img2);
        imageView1 = (ImageView) view.findViewById(R.id.img1);
        tvTips = view.findViewById(R.id.tvTips);
        tvTotalScan = (TextView) view.findViewById(R.id.tvTotalScan);
        alertDialog = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.alert_dialog_demand2, null);
        dialog_window_background = view.findViewById(R.id.dialog_window_background);
        iv.setVisibility(View.GONE);
        handler.postDelayed(task, delaytime);
        createView();


        //liudognwen
        mContext = this;
        RFIDApplication.instance.floatService = this;

        Awl = new AndroidWakeLock((PowerManager) getSystemService(Context.POWER_SERVICE));

        //180910
        initAllSoundPool();

        //180911
        if (RFIDApplication.getIsCanRecordEpcID()) {
            LogEpcID.initExecutor();
        }

//        initHandler();

//        InitDevice();

//        InitDevice();

//        initSoundPool();

//        initExecutorSoundUtil();

        if (true) {
            //liudognwen 0902
            actionUpReceiver = new ActionUpReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("$_ActionUp");
            getApplicationContext().registerReceiver(actionUpReceiver, filter);
        }

        //开始每隔一段时间看是否需要刷新界面;
        updateListSurface();

        initJieWenTest();


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //liudongwen

        InitDevice();
//
        initSoundPool();

        initExecutorSoundUtil();
//
//        if(true){
//            //liudognwen 0902
//            actionUpReceiver = new ActionUpReceiver();
//            IntentFilter filter = new IntentFilter();
//            filter.addAction("$_ActionUp");
//            getApplicationContext().registerReceiver(actionUpReceiver, filter);
//        }

        // // begin
        try {
            Intent mIntent = new Intent();
            mIntent.setAction("$_CloseDialog");
            getApplicationContext().sendBroadcast(mIntent);
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
        // end

        LogUtil.e(TAG, "onStartCommand");

        LogEpcID2.initExecutor();


        if(unUPploadSet == null){
            unUPploadSet = new HashSet<>();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    // end
    private void createView() {
        // begin
        // SharedPreferences shared = getSharedPreferences("float_flag",
        // Activity.MODE_PRIVATE);
        // SharedPreferences.Editor editor = shared.edit();
        // editor.putInt("float", 1);
        // editor.commit();
        // end
        // 获取WindowManager
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);//
//		wm = (WindowManager) getApplicationContext().getSystemService("window");//android.view.WindowManager
        // 设置LayoutParams(全局变量）相关参数
        wmParams = ((RFIDApplication) getApplication()).getMywmParams();


        if (RFIDApplication.getIsDefaultFloatButton() == false) {

//            wmParams.type = 2002;
            wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            wmParams.flags |= 8;

            wmParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;

            wmParams.flags = wmParams.flags | WindowManager.LayoutParams.FLAG_FULLSCREEN;

            wmParams.flags = wmParams.flags | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        } else {
            wmParams.type = 2002;
            wmParams.flags |= 8;
            wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
        }


        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;
        // 设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.format = 1;

        wm.addView(view, wmParams);

        view.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // 获取相对屏幕的坐标，即以屏幕左上角为原点
                x = event.getRawX();
                y = event.getRawY() - 25; // 25是系统状态栏的高度
                Log.i("currP", "currX" + x + "====currY" + y);// 调试信息
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        state = MotionEvent.ACTION_DOWN;
                        StartX = x;
                        StartY = y;
                        // 获取相对View的坐标，即以此View左上角为原点
                        mTouchStartX = event.getX();
                        mTouchStartY = event.getY();
                        Log.i("startP", "startX" + mTouchStartX + "====startY" + mTouchStartY);// 调试信息
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // begin
                        if (!isClick()) {
                            state = MotionEvent.ACTION_MOVE;
                            updateViewPosition();
                        }
                        // end
                        break;

                    case MotionEvent.ACTION_UP:
                        state = MotionEvent.ACTION_UP;

                        if (isClick()) {
                            scanBiz();
                        } else {
                            updateViewPosition();
                        }


                        mTouchStartX = mTouchStartY = 0;
                        break;
                }
                return true;
            }
        });

        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent serviceStop = new Intent();
                serviceStop.setClass(FloatService.this, FloatService.class);
                stopService(serviceStop);
            }
        });

    }

    public void stopScan() {

        Awl.ReleaseWakeLock();
        Comm.stopScan();
        LogUtil.e(TAG, "Comm.stopScan();");

        //Comm.powerDown();
        showlist();
        ToastUtil.showToast(mContext, "已结束扫描");
        //todo 提示正在上传;
        //todo 上传操作;
    }


    public void startScan() {

        unUPploadSet.clear();

        Comm.lv.clear();
        Comm.clean();

        try {

            Awl.WakeLock();
            Comm.startScan();

            LogUtil.e(TAG, "Comm.startScan()");
            ToastUtil.showToast(mContext, "开始RFID扫描");
        } catch (Exception ex) {
            //liudongwen
            com.ldw.xyz.util.exception.ExceptionUtil.handleException(ex);
        }


//        Comm.lv.clear();
//        Comm.clean();
//
//        try {
//            Awl.WakeLock();
//            Comm.startScan();
//            LogUtil.e(TAG, "Comm.startScan()");
//            ToastUtil.showToast(mContext, "开始RFID扫描");
//        } catch (Exception ex) {
//            //liudongwen
//            com.ldw.xyz.util.exception.ExceptionUtil.handleException(ex);
//        }
    }


    public void showImg() {
        if (Math.abs(x - StartX) < 1.5 && Math.abs(y - StartY) < 1.5 && !iv.isShown()) {
            iv.setVisibility(View.VISIBLE);
        } else if (iv.isShown()) {
            iv.setVisibility(View.GONE);
        }
    }

    @Override
    public void scanBiz() {
        // Decoding is enable


        if (isCanRunRead == true) {
            view.setVisibility(View.VISIBLE);
//			wmParams.width = WindowManager.LayoutParams.FILL_PARENT;
//			wmParams.height = WindowManager.LayoutParams.FILL_PARENT;
//			wmParams.format = 1;
//			wm.addView(mHsmView, wmParams);
//			wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//			wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //正在运行
            if (Comm.isrun) {
                LogUtil.e(TAG, "*** STOP *** 开始进行停止---------");
                stopButtonClick();
                LogUtil.e(TAG, "停止后是否正在运行 Comm.isrun = " + Comm.isrun);
            } else if (!Comm.isrun) {
                //todo 运行
                startUI();
                startScan();
                LogUtil.e(TAG, "是否已经开始正在运行 Comm.isrun = " + Comm.isrun);
            }
            // todo 如果正在上传 ,提示等待上传成功;

        } else {
            updateViewPosition();
        }

        LogUtil.e(TAG, "*** scanBiz =");
        LogUtil.e(TAG, "*** isCanRunRead  =" + isCanRunRead);
    }


    @Override
    public void stopButtonClick() {
        Awl.ReleaseWakeLock();
        Comm.stopScan();
        LogUtil.e(TAG, "Comm.stopScan();");


        if (tagListSize == 0 || uploadSet.size() == 0) {

        } else {
            while (tagListSize > uploadSet.size()) {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.toString());
                }
            }
        }


        stopScan();

        //ui展示
        stopUI();


        //todo
        postString();
    }


    public void addViewAndShowInfo(String title, String content) {
        LogUtil.e(TAG, "addViewAndShowInfo=============>1");
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.format = 1;
        wmParams.gravity = Gravity.CENTER; // 调整悬浮窗口至左上角
        wm.addView(alertDialog, wmParams);
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView title_text = (TextView) alertDialog.findViewById(R.id.title_text);//content_text
        TextView content_text = (TextView) alertDialog.findViewById(R.id.content_text);//content_text
        title_text.setText(title);
        content_text.setText(content);
        Button btnClear = (Button) alertDialog.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(alertDialog);
                isCanRunRead = true;
                buttonClearOn();

            }
        });

        Button confirm_button = (Button) alertDialog.findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(alertDialog);
                isCanRunRead = true;
                postString();
            }
        });
        LogUtil.e(TAG, "addViewAndShowInfo=============>2");


    }


    public void stopUI() {
        imageView1.setBackground(getDrawable(R.drawable.svg_rfid_stop));
        tvTotalScan.setTextColor(getResources().getColor(R.color.white));
    }

    public void startUI() {
        imageView1.setBackground(getDrawable(R.drawable.svg_rfid_read));
        tvTotalScan.setTextColor(getResources().getColor(R.color.green));
    }


    // begin
    private boolean isClick() {
        return Math.abs(x - StartX) < 5 && Math.abs(y - StartY) < 5;
    }
    // end

    private Handler handler = new Handler();
    private Runnable task = new Runnable() {
        public void run() {
//            // TODO Auto-generated method stub
//            dataRefresh();
//            handler.postDelayed(this, delaytime);
//            wm.updateViewLayout(view, wmParams);
        }
    };

    public void dataRefresh() {
        // tx.setText("" + memInfo.getmem_UNUSED(this) + "KB");
        // tx1.setText("" + memInfo.getmem_TOLAL() + "KB");
    }

    private void updateViewPosition() {
        if (RFIDApplication.getIsDefaultFloatButton() == false) {

        } else {
            LogUtil.e("updateViewPosition", "updateViewPosition()");
            // 更新浮动窗口位置参数
            wmParams.x = (int) (x - mTouchStartX);
            wmParams.y = (int) (y - mTouchStartY);
            wm.updateViewLayout(view, wmParams);
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        LogUtil.e(TAG, "onStart");

        startForeground();

        super.onStart(intent, startId);

    }


    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (updateState == Const.CONST_NOT_UPDATE) {
                updateListSurface();

                return;
            }

            if (updateState == Const.CONST_UPDATE) {

                //刷新界面;
                notifyActivityUpdateAdapter();
                //更新读到的标签总数;
                tvTotalScan.setText("总数:" + totalTagsSum);

                updateState = Const.CONST_NOT_UPDATE;

                updateListSurface();

                return;
            }

            if (updateState == Const.CONST_NOT_CHECK) {

                return;
            }
        }
    };


    public void updateListSurface() {
        mHandler.postDelayed(updateRunnable, 50);

    }


    @Override
    public void onDestroy() {
        try {
            getApplicationContext().unregisterReceiver(actionUpReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //停止handler的自我调用;
        updateState = Const.CONST_NOT_CHECK;

        handler.removeCallbacks(task);
        LogUtil.i(TAG, "onDestroy");
        LogUtil.i(TAG, "onDestroy");
        wm.removeView(view);

        OkHttpUtils.getInstance().cancelTag(cancelTag);
        //避免内存泄露
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
//        if (connectH != null) {
//            connectH.removeCallbacksAndMessages(null);
//        }
//        if (uhfhandler != null) {
//            uhfhandler.removeCallbacksAndMessages(null);
//        }

        //liudongwen
        release();

        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


//    // begin
//    private int scanCount = 0;


    Intent mIntent = new Intent();//

    private void sendMessage(String barCode) {
        // Intent mIntent = new Intent();//
        // mIntent.setAction("idatachina.RFID.BARCODE.SCANINFO");
        mIntent.putExtra("idatachina.SCAN_DATA", barCode);
        RFIDApplication.instance.sendBroadcast(mIntent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }

    private static final int NOTIFY_FAKEPLAYER_ID = 1339;

    private void startForeground() {
//        Intent i = new Intent(this, SuoFeiYaMainDemand2Activity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

        Notification myNotify = new Notification.Builder(this).setSmallIcon(R.drawable.ic_launcher_sl)
                // .setTicker("Fake Player: " )
                .setContentTitle(ResHelper.getString(RFIDApplication.instance, R.string.app_name))
                .setContentText(ResHelper.getString(RFIDApplication.instance, R.string.scan_service_running))
//                .setContentIntent(pi)
                .getNotification();
        myNotify.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(NOTIFY_FAKEPLAYER_ID, myNotify);
    }

    int inSupplement = 0;

    public void setSupplementZero() {
        inSupplement = 0;
    }

    public void supplementAdd() {
        inSupplement = inSupplement + 1;
    }

    public int getSupplement() {
        return inSupplement;
    }


    private void showlist() {

        //liudongwen
        uploadSetStartSize = uploadSet.size();
        Log.e(TAG, "------------------------------------");
        LogUtil.e(TAG, "showlist()");

        try {
//            int index = 1;

            //liudongwen
            int index = list.size();
            if (index == 0) {
                index = 1;
                Map<String, String> h = new HashMap<String, String>();
                for (int i = 0; i < Coname.length; i++)
                    h.put(Coname[i], Coname[i]);
                list.add(h);
            }

            int ReadCnt = 0;//个数

            String epcstr = "";//epc

//            int ListIndex = 0;
            int ListIndex = uploadSetStartSize;
            tagListSize = tagListSize - uploadSetStartSize;
            if (ListIndex < 0) {
                ListIndex = 0;
            }

//            LogUtil.e(TAG,"----------------------------------");
//            LogUtil.e(TAG,"tagListSize = " + tagListSize);


            if (!isQuick || !Comm.isrun) {
                while (tagListSize > 0) {


                    epcstr = lsTagList.get(ListIndex).strEPC;

//                    LogUtil.e(TAG,"----------------------------------");
//                    LogUtil.e(TAG,"lsTagList.get(ListIndex).strEPC  epcstr ="+epcstr);

                    if (Comm.dt == Comm.DeviceType.supoin_JT && Comm.To433Index < index) {
                        Comm.mWirelessMg.writeTo433(epcstr + "\n");
                        Comm.To433Index++;
                    }

                    if (epcstr != null && !epcstr.equals("")) {

                        epcstr = lsTagList.get(ListIndex).strEPC.replace(" ", "");
                        if (epcstr.length() > 24) {
                            epcstr = epcstr.substring(0, 24) + "\r\n" + epcstr.substring(24);
                        }

                        if (Const.IsCanTest == true && PreferenceUtil.get(mContext, Const.KEY_IS_CAN_JIE_WEN_TEST).equals(Const.CONST_TRUE)) {
                            //todo
                            if (epcstr.toUpperCase().endsWith(PreferenceUtil.get(mContext, Const.KEY_JIE_WEN_TEST_SUFFIX))) {
                                AAAStartNum++;
                            } else {
                                unAAAStartNum++;
                            }

                            LogUtil.e(TAG, "---------------------");
                            LogUtil.e(TAG, "AAAStartNum = " + AAAStartNum);
                            LogUtil.e(TAG, "---------------------");
                            LogUtil.e(TAG, "unAAAStartNum = " + unAAAStartNum);
                        }


                        boolean b = false;

                        if (epcstr.startsWith("5041") && !epcstr.toUpperCase().endsWith("FFFFF")) {


                            b = true;

                        }
//                        else {
                        //数据处理
                        epcstr = DataDoWithUtil.dataConversion(epcstr);

                        if (b) {
                            unUPploadSet.add(epcstr);
                        }

                        if (Const.IsCanTest == true && PreferenceUtil.get(mContext, Const.KEY_IS_CAN_JIE_WEN_TEST).equals(Const.CONST_TRUE)) {
                            //todo
                            if (!epcstr.toUpperCase().startsWith("PA")) {
                                unPAStartNum++;
                            }
                        }


//                        LogUtil.e(TAG,"----------------------------------");
//                        LogUtil.e(TAG,"DataDoWithUtil.dataConversion(epcstr)  ="+epcstr);
//
//                        LogUtil.e(TAG,"----------------------------------");
//                        LogUtil.e(TAG,"uploadSet.contains(epcstr)  ="+uploadSet.contains(epcstr));

                        //liudongwen
//                            if (uploadSet.contains(epcstr) == false) {


                        //liudongwen
                        uploadSet.add(epcstr);

                        //demo
                        Map<String, String> m = new HashMap<String, String>();
                        m.put(Coname[0], String.valueOf(index));
                        m.put(Coname[1], epcstr);


                        if(b){
                            logUnRightEpcIDString(epcstr, index);
                        }else {
                            // 20180910 添加记录读到的数据;
                            logScanEpcIDString(epcstr, index);
                        }



                        ////demo该标签读取的次数;
                        ReadCnt = lsTagList.get(ListIndex).nReadCount;
                        m.put(Coname[2], String.valueOf(ReadCnt));
                        //int mRSSI=Integer.parseInt(lsTagList.get(ListIndex).strRSSI);
                        index++;
                        if (!b) {
                            list.add(m);
                        }

//                            }

//                        }
//                            }
//                        }
                    }
                    ListIndex++;
                    tagListSize--;
                }

            }
            //liudongwen 为零就是清空了,所以要刷新,大于原来的就是新加了, 也要刷新;
            if (uploadSet != null && (uploadSet.size() == 0 || (uploadSet.size() > uploadSetStartSize))) {

                LogUtil.e(TAG, "uploadSet.size() ========================> " + uploadSet.size());
                LogUtil.e(TAG, "uploadSet.toString()========================>)" + uploadSet.toString());

                totalTagsSum = uploadSet.size() - unUPploadSet.size();
                if(totalTagsSum<0){
                    totalTagsSum = 0;
                }

//                //刷新界面;
//                notifyActivityUpdateAdapter();
//                //更新读到的标签总数;
//                tvTotalScan.setText("总数:"+totalTagsSum);


                updateState = Const.CONST_UPDATE;
//                LogUtil.e(TAG,"-----------------------------------");
//                LogUtil.e(TAG,"updateState = " + state);

            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
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
            com.ldw.xyz.util.exception.ExceptionUtil.handleException(e);
        }
    }


    public void postString() {
//        test2();

        String url;
        if (RFIDApplication.getModeIsBuHuo() == Const.CONST_TRUE) {
            url = Const.getURL_BU_HUO();
        } else {
            url = Const.getURL_UPLAOD();
        }


        String id = PreferenceUtil.get(mContext, Const.KEY_STAGE_CODE, "");
        if (Const.IsCanTest == true) {
            //测试环境不需要检测;
        } else if (StringHelper2.isEmpty(id)) {
            ToastUtil.showToast(mContext, "stage ID 为空,请重新获取通道!");
            return;
        }

        String organizationID = PreferenceUtil.get(mContext, Const.KEY_ORGANIZATION, "");

        LogUtil.e(TAG, "ORGANIZATION_ID =" + organizationID);

        if (Const.IsCanTest == true) {
            //测试环境不需要检测;
        } else if (uploadSet.size() == 0) {
//            ToastUtil.showToast(mContext,"没有扫描数据");
            return;
        }

        String content = getJsonObject();

//        String url = Const.getURL_UPLAOD();

        LogUtil.e(TAG, "url = " + url);
        LogUtil.e(TAG, "content = " + content);
        LogUtil.e(TAG, "organization = " + organizationID);

        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("json", content)
                .addParams("stage", id)
                .addParams("organization", organizationID)
                .build()
                .connTimeOut(Const.CONN_TIME_OUT)
                .readTimeOut(Const.READ_TIME_OUT)
                .writeTimeOut(Const.WRITE_TIME_OUT)
                .execute(new MyStringCallback());

    }

    private String getJsonObject() {

        LogUtil.e(TAG, "uploadSet.size() = " + uploadSet.size());

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
        // "PA1808010340643","PA1808010340644"]}

        if (uploadSet != null) {

            boolean isFirst = true;
            Iterator<String> it = uploadSet.iterator();
            while (it.hasNext()) {
                String str = it.next();

                if(!unUPploadSet.contains(str)){
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

        }


        sb.append("]}");

//
//        sb.append("],");
//        sb.append("\"stage\":");
//        sb.append("\""+PreferenceUtil.get(mContext,Const.KEY_STAGE_CODE,"")+"\"");
//        sb.append("}");

        return sb.toString();
    }


    private void testAlsert() {
//        soundError();
        isCanRunRead = true;
        buttonClearOn();
    }

    @Override
    public void run() {

    }


    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            //todo 提示正在上传;
            isCanRunRead = false;
//            tvTips.setVisibility(View.VISIBLE);

            //liudongwen
            dialog_window_background.setVisibility(View.VISIBLE);


            if (tvShow == null) {
                tvShow = (TextView) dialog_window_background.findViewById(R.id.tvShow);
            }
            if (circularProgressBar == null) {
                circularProgressBar = (MCircularProgressBar) dialog_window_background.findViewById(com.maning.mndialoglibrary.R.id.circularProgressBar);
            }
            circularProgressBar.setProgress(0);
//            tvShow.setText("上传进度:0%");


            if (LineProgressBar == null) {
                LineProgressBar = (ProgressBar) dialog_window_background.findViewById(com.maning.mndialoglibrary.R.id.horizontalProgressBar);
            }
            LineProgressBar.setProgress(0);


            tvShow.setText("上传进度:0%");

        }

        @Override
        public void onAfter(int id) {
            //todo 取消显示正在上传
//            isCanRunRead =true;
//            tvTips.setVisibility(View.GONE);

        }

        @Override
        public void onError(Call call, Exception e, int id) {

            LogUtil.e(TAG, "e ------------>" + e);

//            isCanRunRead = true;
            //todo 取消显示正在上传
//            tvTips.setVisibility(View.GONE);
            dialog_window_background.setVisibility(View.GONE);

            LogUtil.e(TAG, "onError " + e.toString());

            if (mContext != null) {
                final String info;
                if (e instanceof UnknownHostException) {
                    info = "网络未连接!";
                } else if (e instanceof SocketTimeoutException) {
                    info = "连接网络超时!";
                } else if (e instanceof ConnectException) {
                    info = "连接地址: " + PreferenceUtil.get(mContext, Const.KEY_URL_SERVER, ResHelper.getString(mContext, R.string.url_server_address)) + " 失败";
                } else {
                    info = e.toString();
                }

//                addViewAndShowInfo("网络错误",info);


                testAlsert();
                soundErrorNow(R.raw.alarmbell5s);
                shake();
//                soundError();
//                soundErrorNow(R.raw.sound_ou);

                Toast.makeText(mContext,
                        "Error:" + "\n" + info,
                        Toast.LENGTH_LONG).show();

//                if (isTest) {
//                    onResponse("{未排程;取消;窜货}", 12);
//                }


            }

            Log.e(TAG, "Exception = " + e.toString());

        }

        @Override
        public void onResponse(String response, int id) {


//            VoiceThread mt=new VoiceThread(mContext);


            dialog_window_background.setVisibility(View.GONE);

//            isCanRunRead = true;
//            tvTips.setVisibility(View.GONE);
            LogUtil.e(TAG, "onResponse：complete-------->");


            LogUtil.e(TAG, "response ------------> " + response);
            LogUtil.e(TAG, "id ------------>" + id);

            ResponseObj obj = GsonUtil.toResponseObj(response);

            if (isTest) {
                obj = new ResponseObj();

                obj.setErrormsg("未排程;包状态异常;窜货;取消;漏货");
                obj.setMyCollude("");
                obj.setMyPackage("未排程;包状态异常;窜货;取消;漏货");

            }


            if (obj == null) {
                addViewAndShowInfo("返回信息错误", response);
                soundErrorNow(R.raw.alarmbell5s);
                shake();
//              soundError();

                testAlsert();
                Toast.makeText(mContext,
                        "返回信息错误:" + response,
                        Toast.LENGTH_LONG).show();

            } else {

                boolean isSuccess = true;
                StringBuffer sb = new StringBuffer();
                if (!StringHelper2.isEmpty(obj.getErrormsg())) {

                    String[] strArr = obj.getErrormsg().split(";", -1);
                    int myLength = strArr.length;
                    if (myLength != 0) {
                        for (int i = 0; i < myLength; i++) {


                            if (!StringHelper2.isEmpty(strArr[i])) {
                                isSuccess = false;

                                addSoundNow(strArr[i]);
                                sb.append(strArr[i]);
                                sb.append("  ");

                            }

                            if (i == myLength - 1 && isSuccess == true) {
                                Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
                                soundSuccessNow();
                                buttonClearOn();
                                notifyActivityUpdateAdapter();
                                //todo 清空数据
                                isCanRunRead = true;

                                break;
                            }
                            //出现错误,下面进行播放
                            else if (i == myLength - 1) {
                                testAlsert();
                                notifyActivityUpdateAdapter();
                                //发出声音
                                SoundUtil.playSound();
                                //震动
                                shake();
                                ToastUtil.showToast(mContext, sb.toString());
                                if (!StringHelper2.isEmpty(obj.getMyPackage())) {
                                    Toast.makeText(mContext, obj.getMyPackage(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                } else {
                    //错误
                    testAlsert();
                    notifyActivityUpdateAdapter();
                    soundErrorNow(R.raw.sound_houtaierror);
                    Toast.makeText(mContext, "返回信息格式不正确", Toast.LENGTH_SHORT).show();

                }
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            LogUtil.e(TAG, "inProgress=================>" + progress);
            int currentProgress = (int) (100 * progress);


            if (currentProgress < 100) {
                tvShow.setText("上传进度:" + currentProgress + "%");
                //添加动画平滑过度
                circularProgressBar.setProgress(progress, true);

                if (LineProgressBar != null) {
                    LineProgressBar.setProgress(currentProgress);
                }

            } else {
//				mHandler.removeCallbacks(runnable);
                tvShow.setText("上传进度:100%");
                circularProgressBar.setProgress(100);

                if (LineProgressBar != null) {
                    LineProgressBar.setProgress(100);
                }

                //关闭
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvShow.setText("后台正在处理数据...");

//                        dialog_window_background.setVisibility(View.GONE);
                    }
                }, 500);
            }
        }


    }

    TextView tvShow;
    MCircularProgressBar circularProgressBar;
    ProgressBar LineProgressBar;


    //清空数据;
    private void buttonClearOn() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //liudongwen 清空
                clearAllData();
                tvTotalScan.setText("总数:0");
                Comm.lv.clear();
                Comm.clean();
                //demo
                showlist();
            }
        });

    }

    public void clearAllData() {
        //liudongwen 清空数据;
        if (uploadSet == null) {
            uploadSet = new HashSet<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        list.clear();
        uploadSet.clear();
    }


//    public void soundError() {
//        mHandler.postDelayed(this, 100);
//    }
//    public Thread playSouncThread = new playSouncThread(new playSoundRunnable());
//    @Override
//    public void run() {
//        playSouncThread.run();
//    }
//    public class playSoundRunnable implements Runnable {
//        @Override
//        public void run() {
//            LogUtil.e("TAG", "已经执行");
//            playSound();
//        }
//    }
//    public class playSouncThread extends Thread {
//        public playSouncThread(Runnable target) {
//            super(target);
//        }
//    }


    public void notifyActivityUpdateAdapter() {
//        Intent mIntent = new Intent();
//        mIntent.setAction("$_UpdateAdapter");
//        getApplicationContext().sendBroadcast(mIntent);

        if (RFIDApplication.instance.suoFeiYaMainDemand2Activity != null) {
            RFIDApplication.instance.suoFeiYaMainDemand2Activity.uiFresh();
        }

    }


    public class ActionUpReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

//            scanBiz();
        }
    }


    private void addSoundNow(String msg) {
        LogUtil.e(TAG, "***错误信息*** ----------");
        LogUtil.e(TAG, "msg = " + msg);

        if (StringHelper2.isEmpty(msg)) {
            return;
        }
        //else if(msg.toUpperCase().startsWith("非本生产OU")){
//             SoundUtil.put(R.raw.sound_weipaicheng3,index);
        // index++;
//        }
        else if (msg.startsWith("未排程")) {
            SoundUtil.put(R.raw.sound_weipaicheng3, index);
            index++;
        } else if (msg.startsWith("非已点数")) {
            SoundUtil.put(R.raw.sound_feiyidianshu3, index);
            index++;
        } else if (msg.startsWith("包状态异常")) {
            SoundUtil.put(R.raw.sound_baozhuangtaiyc3, index);
            index++;
        } else if (msg.startsWith("窜货")) {

            SoundUtil.put(R.raw.sound_cuanhuo3, index);
            index++;
        } else if (msg.startsWith("漏货")) {
            SoundUtil.put(R.raw.sound_louhuo3, index);
            index++;
        } else if (msg.startsWith("取消")) {
            SoundUtil.put(R.raw.sound_quxiao3, index);
            index++;
        }
        LogUtil.e("TAG", "soundErrorMsg");
    }

    int index = 0;


//
//    private void addSoundNow(String msg,VoiceThread mt){
//        LogUtil.e("TAG","***错误信息*** ----------");
//        LogUtil.e("TAG","msg = " + msg);
//
//        if(StringHelper2.isEmpty(msg)){
//            return;
//        }
//        else if(msg.startsWith("未排程")){
////            soundErrorNow(R.raw.sound_weipaicheng3);
//            mt.AddList(String.valueOf(R.raw.sound_weipaicheng3));
//        }
//        else if(msg.startsWith("非已点数")){
////            soundErrorNow(R.raw.sound_feiyidianshu3);
//            mt.AddList(String.valueOf(R.raw.sound_feiyidianshu3));
//        }
//
//        else if(msg.startsWith("窜货")){
////            soundErrorNow(R.raw.sound_cuanhuo3);
//            mt.AddList(String.valueOf(R.raw.sound_cuanhuo3));
//        }
//
//        else if(msg.startsWith("漏货")){
////            soundErrorNow(R.raw.sound_louhuo3);
//            mt.AddList(String.valueOf(R.raw.sound_louhuo3));
//        }
//
//        else if(msg.startsWith("取消")){
////            soundErrorNow(R.raw.sound_quxiao3);
//            mt.AddList(String.valueOf(R.raw.sound_quxiao3));
//        }
//        LogUtil.e("TAG","soundErrorMsg");
//    }
//
//    protected void mediaPlayAllSound(VoiceThread mt){
//        new Thread(mt).start();
//    }
//
//    public class VoiceThread implements Runnable {
//        private ArrayList voiceList=new ArrayList();
//        private boolean CanStop=false;
//        private MediaPlayer mediaplay=null;
//        private Context context=null;
//
//        public int getVoiceListSize(){
//            return this.voiceList.size();
//        }
//
//        public VoiceThread(Context _ConText)
//        {
//            context=_ConText;
//        }
//
//        public void AddList(String _str)
//        {
//            voiceList.add(_str);
//        }
//        public void run(){
//            while(!CanStop) {
//                if (!voiceList.isEmpty()) {
//                    Object value = voiceList.get(0);
//                    if(mediaplay!=null)
//                    {
//                        if(mediaplay.isPlaying()) {
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                //e.printStackTrace();
//                            }
//                            continue;
//                        }
//                        else
//                        {
//                            mediaplay.release();
//                            mediaplay=null;
//                        }
//
//                    }
//                    int voiceid = Integer.parseInt(String.valueOf(value));
//                    mediaplay = new MediaPlayer().create(context, voiceid);
//                    mediaplay.start();
//                    voiceList.remove(0);
//                }
//            }
//        }
//    }


//    public void setPower(){
//        try {
//            Comm.opeT = Comm.operateType.setPower;
//            int ant1pow = 1;
//            int ant2pow = 0;
//            int ant3pow = 0;
//            int ant4pow = 0;
//
//
//            setAntPower(ant1pow, ant2pow, ant3pow, ant4pow);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            Toast.makeText(RFIDApplication.instance,
//                    "Set Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
//                    .show();
//
//            LogUtil.e("TAG","----------------------");
//            LogUtil.e("TAG","Set Exception:" + e.getMessage());
//            return;
//        }
//    }


//    public Handler testHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            try {
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("connectH", e.getMessage());
//            }
//        }
//    };
//
//
//    private void test1(){
//        testHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(totalTagsSum>1000 || totalTagsSum==0){
//                    if (RFIDApplication.instance != null && RFIDApplication.instance.floatService != null) {
//                        RFIDApplication.instance.floatService.scanBiz();
//                    }
//                }
//                test1();
//            }
//        },5000);
//    }
//    int times = 0;
//    private void test2(){
//        times++;
//        Toast.makeText(RFIDApplication.instance," 次数:"+ times,Toast.LENGTH_LONG).show();
//    }


}
