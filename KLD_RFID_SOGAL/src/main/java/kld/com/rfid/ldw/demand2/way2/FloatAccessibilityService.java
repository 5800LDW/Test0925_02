package kld.com.rfid.ldw.demand2.way2;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.R;
import kld.com.rfid.ldw.RFIDApplication;
import kld.com.rfid.ldw.bean.ResponseObj;
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

@TargetApi(23)
public class FloatAccessibilityService extends AccessibilityService implements OnClickListener , Runnable{



    public  boolean isFloatViewIsShowing = false;
    public  boolean isCanRunRead = true;
    public static List<Map<String, ?>> list = new ArrayList<>();
    public static Set<String> uploadSet = new HashSet<>();


    protected Object cancelTag = this;
    private Message msg = null;
    private long mlCount = 0;
    private int totalSec, yushu, min, sec;
    //liudongwen
    String[] Coname = new String[]{"NO", "                    EPC ID ", "Count"};
    //liudongwen
    public int uploadSetStartSize;

    //    public FloatService floatService;
    public static Handler mHandler = new Handler();
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

    private Handler handler = new Handler();
//    private Runnable task = new Runnable() {
//        public void run() {
//            // TODO Auto-generated method stub
//            dataRefresh();
//            handler.postDelayed(this, delaytime);
//            wm.updateViewLayout(view, wmParams);
//        }
//    };


    private static final String TAG = "FloatService ";





    @SuppressLint("HandlerLeak")
    public Handler connectH = new Handler() {
        @SuppressLint("SetTextI18n")
        @SuppressWarnings({"unchecked", "unused"})
        @Override
        public void handleMessage(Message msg) {
            try {
                Comm.mInventoryHandler = uhfhandler;
                Comm.SetInventoryTid(false);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("connectH", e.getMessage());
            }
        }
    };

    @SuppressLint("HandlerLeak")
    public Handler uhfhandler = new Handler() {
        @SuppressWarnings({"unchecked", "unused"})
        @Override
        public void handleMessage(Message msg) {
            try {
                if (Comm.is6BTag)
                    tagListSize = lsTagList6B.size();
                else
                    tagListSize = lsTagList.size();
                Bundle bd = msg.getData();

                int readCount = bd.getInt("readCount");


                if (readCount > 0){

                    //tv_state.setText(String.valueOf(readCount));
                }

//                Log.e("tagListSize", "tagListSize : " + tagListSize);//lsTagList
//                Log.e("lsTagList", "lsTagList.size() : " + lsTagList.size());//lsTagList
//                Log.e("lsTagList", "lsTagList.size() : " + lsTagList);//lsTagList
//                Log.e("lsTagList", "lsTagList : " + lsTagList.toString());//lsTagList


                if (tagListSize > 0)
                    showlist();


                Log.e("uhfhandler", "readCount : " + String.valueOf(readCount));

            } catch (Exception e) {
                e.printStackTrace();
                com.ldw.xyz.util.exception.ExceptionUtil.handleException(e);
                LogUtil.e("Tag", e.getMessage());
            }
        }
    };


    @Override
    public void onCreate() {
        Log.d("FloatService", "onCreate");
        super.onCreate();
        view = LayoutInflater.from(this).inflate(R.layout.floating, null);
        iv = (ImageView) view.findViewById(R.id.img2);
        imageView1 = (ImageView)view.findViewById(R.id.img1);
        tvTips = view.findViewById(R.id.tvTips);
        tvTotalScan = (TextView)view.findViewById(R.id.tvTotalScan);
        alertDialog = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.alert_dialog_demand2, null);
        dialog_window_background = view.findViewById(R.id.dialog_window_background);
        iv.setVisibility(View.GONE);
//        handler.postDelayed(task, delaytime);
        initWindowManager();
//        createFloatButton();

        startForeground();

        InitDevice();

        LogUtil.e("TAG","onCreate===================>");

        //liudognwen
        mContext =this;
        RFIDApplication.instance.floatAccessibilityService = this;


//       isFloatViewIsShowing = false;
//       isCanRunRead = true;
//       list = new ArrayList<>();
//       uploadSet = new HashSet<>();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        //初始化RFID模块
//        InitDevice();

        //初始化声音
//        initSoundPool();

        //关闭提示"初始化提示框"
//        try {
//            Intent mIntent = new Intent();
//            mIntent.setAction("$_CloseDialog");
//            getApplicationContext().sendBroadcast(mIntent);
//        } catch (Exception e) {
//            ExceptionUtil.handleException(e);
//        }

        //显示notification的消息
//        startForeground();
        LogUtil.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    private void initWindowManager(){
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
        wmParams.type = 2002;
        wmParams.flags |= 8;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;
        // 设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.format = 1;

    }


    // end
    private void createFloatButton() {
        if(wm==null){
            LogUtil.e("TAG","wm == null");
        }


//        initWindowManager();
        //创建悬浮view;
        if(isFloatViewIsShowing == false){
            wm.addView(view, wmParams);
        }
        isFloatViewIsShowing = true;
        LogUtil.e("TAG","createFloatButton()");
        LogUtil.e("TAG","isFloatViewIsShowing = " + isFloatViewIsShowing);


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

                        scanBiz();

                        mTouchStartX = mTouchStartY = 0;
                        break;
                }
                return true;
            }
        });

    }



    public void InitDevice() {
        //盘点到重复标签是否发出声音
        Comm.repeatSound = false;
        //getApplication()迷惑客户，往往导致出错
//        Comm.app = getApplication();
        Comm.app = new UHF1Application();
        Comm.spConfig = new SPconfig(this);
        context = this;
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.beep51, 1);
        Log.d("test", "soundPool");

        checkDevice();
        Comm.initWireless(Comm.app);
        Comm.connecthandler = connectH;
//        Comm.moduleType = null;
        if (Comm.moduleType!=null)
            ConnectModule();
        else
            Connect();
        Log.d("test", "connect");
    }


    private void startScan(){
        try {
            if(Awl!=null){
                Awl.WakeLock();
            }

            Comm.startScan();
        } catch (Exception ex) {
            com.ldw.xyz.util.exception.ExceptionUtil.handleException(ex);
        }
    }

    private  void stopScan(){
        Awl.ReleaseWakeLock();
        Comm.stopScan();
        showlist();
    }



    public void showImg() {
        if (Math.abs(x - StartX) < 1.5 && Math.abs(y - StartY) < 1.5 && !iv.isShown()) {
            iv.setVisibility(View.VISIBLE);
        } else if (iv.isShown()) {
            iv.setVisibility(View.GONE);
        }
    }


    protected void scanBiz() {
        // Decoding is enable

        if (isClick()&&isCanRunRead) {
            view.setVisibility(View.VISIBLE);
//			wmParams.width = WindowManager.LayoutParams.FILL_PARENT;
//			wmParams.height = WindowManager.LayoutParams.FILL_PARENT;
//			wmParams.format = 1;
//			wm.addView(mHsmView, wmParams);
//			wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//			wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //正在运行
            if(Comm.isrun){
                stopAndSendInfo();
            }
            else if(!Comm.isrun){
                //todo 运行
                startUI();
                ToastUtil.showToast(mContext,"开始RFID扫描");
                startScan();
            }
        } else {
            updateViewPosition();
        }

    }

    protected void scanBizForScanButton() {
        // Decoding is enable

        if (isCanRunRead) {
            view.setVisibility(View.VISIBLE);
            //正在运行
            if(Comm.isrun){
                stopAndSendInfo();
            }
            else if(!Comm.isrun){
                //todo 运行
                startUI();
                ToastUtil.showToast(mContext,"开始RFID扫描");
                startScan();
            }
        }

    }


    private void stopAndSendInfo(){
        ToastUtil.showToast(mContext,"已停止扫描");
        stopUI();
        stopScan();
        postString();
    }


    /**添加悬浮提示框,目前取消这个设计*/
    public void addViewAndShowInfo(String title,String content){
        LogUtil.e("TAG","addViewAndShowInfo=============>1");
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.format = 1;
        wmParams.gravity = Gravity.CENTER ; // 调整悬浮窗口至左上角
        wm.addView(alertDialog, wmParams);
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView title_text = (TextView)alertDialog.findViewById(R.id.title_text);//content_text
        TextView content_text = (TextView)alertDialog.findViewById(R.id.content_text);//content_text
        title_text.setText(title);
        content_text.setText(content);
        Button btnClear = (Button)alertDialog.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(alertDialog);
                isCanRunRead = true;
                buttonClearOn();

            }
        });

        Button confirm_button =(Button)alertDialog.findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(alertDialog);
                isCanRunRead = true;
                postString();
            }
        });
        LogUtil.e("TAG","addViewAndShowInfo=============>2");

    }

    public void stopUI(){
        imageView1.setBackground(getDrawable(R.drawable.svg_rfid_stop));
        tvTotalScan.setTextColor(getResources().getColor(R.color.white));
    }

    public void startUI(){
        imageView1.setBackground(getDrawable(R.drawable.svg_rfid_read));
        tvTotalScan.setTextColor(getResources().getColor(R.color.green));
    }


    //清空数据;
    private void buttonClearOn(){
        Comm.lv.clear();
        Comm.clean();
        //liudongwen 清空
        clearAllData();
        //demo
        showlist();
        tvTotalScan.setText("总数:0");
    }
    private void clearAllData(){
        //liudongwen 清空数据;
        if(uploadSet==null){
            uploadSet = new HashSet<>();
        }
        if(list==null){
            list = new ArrayList<>();
        }
        list.clear();
        uploadSet.clear();
    }



    // begin
    private boolean isClick() {
        return Math.abs(x - StartX) < 5 && Math.abs(y - StartY) < 5;
    }
    // end



    public void dataRefresh() {
        // tx.setText("" + memInfo.getmem_UNUSED(this) + "KB");
        // tx1.setText("" + memInfo.getmem_TOLAL() + "KB");
    }

    private void updateViewPosition() {
        // 更新浮动窗口位置参数
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY);
        wm.updateViewLayout(view, wmParams);
    }


    protected void cancelPostString(){
        OkHttpUtils.getInstance().cancelTag(cancelTag);
    }



    public void activityStopButtonOn(){
        LogUtil.e("TAG","activityStopButtonOn" );
        //停止的UI显示
        stopUI();

        //去除view;
        if( isFloatViewIsShowing == true){
            wm.removeView(view);
        }
        isFloatViewIsShowing = false;

        LogUtil.e("TAG","isFloatViewIsShowing = " + isFloatViewIsShowing);

        //停止发送网络请求
        cancelPostString();

        //关闭连接模块//todo
        release();


        LogUtil.e("TAG","activityStopButtonOn()");

    }




    public void activityStartButtonOn(){
        RFIDApplication.instance.floatAccessibilityService = this;

        //创建悬浮view;
        createFloatButton();


        //初始化RFID模块
        InitDevice();

        //初始化声音模块;
        initSoundPool();

        //activity 可以关闭窗口了;
        try {
            Intent mIntent = new Intent();
            mIntent.setAction("$_CloseDialog");
            getApplicationContext().sendBroadcast(mIntent);
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
        LogUtil.e("TAG","activityStartButtonOn()");

    }



    @Override
    public void onDestroy() {
//        handler.removeCallbacks(task);
        LogUtil.i(TAG, "onDestroy");
//        wm.removeView(view);
//
//        cancelPostString();
//
//        //避免内存泄露
//        if(mHandler!=null){
//            mHandler.removeCallbacksAndMessages(null);
//        }
//        if(connectH!=null){
//            connectH.removeCallbacksAndMessages(null);
//        }
//        if(uhfhandler!=null){
//            uhfhandler.removeCallbacksAndMessages(null);
//        }
//
//        //liudongwen
//        release();

        super.onDestroy();

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }






//    // begin
//    private int scanCount = 0;
//    Intent mIntent = new Intent();//
//    private void sendMessage(String barCode) {
//        // Intent mIntent = new Intent();//
//        // mIntent.setAction("idatachina.RFID.BARCODE.SCANINFO");
//        mIntent.putExtra("idatachina.SCAN_DATA", barCode);
//        RFIDApplication.instance.sendBroadcast(mIntent);
//    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }

    private static final int NOTIFY_FAKEPLAYER_ID = 1339;

    private void startForeground() {
        Intent i = new Intent(this, SuoFeiYaAccessibilitySActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

        Notification myNotify = new Notification.Builder(this).setSmallIcon(R.drawable.ic_launcher_sl)
                // .setTicker("Fake Player: " )
                .setContentTitle(ResHelper.getString(RFIDApplication.instance,R.string.app_name))
                .setContentText(ResHelper.getString(RFIDApplication.instance,R.string.scan_service_running)).setContentIntent(pi)
                .getNotification();
        myNotify.flags |= Notification.FLAG_NO_CLEAR;
        // set service state is foreground , when it is low memory not to kill
        // service
        startForeground(NOTIFY_FAKEPLAYER_ID, myNotify);
    }


    private void showlist() {
        //liudongwen
        uploadSetStartSize = uploadSet.size();

        LogUtil.e("TAG2","showlist");

        try {
//            int index = 1;

            //liudongwen
            int index = list.size();
            if(index==0){
                index=1;
                Map<String, String> h = new HashMap<String, String>();
                for (int i = 0; i < Coname.length; i++)
                    h.put(Coname[i], Coname[i]);
                list.add(h);
            }

            int ReadCnt = 0;//个数

            String epcstr = "";//epc

            int ListIndex = 0;

            if (!isQuick || !Comm.isrun) {
                while (tagListSize > 0) {
                    //这里是控制最多显示100个, 现在不限制;
//                    if (index < 100) {
//                        if (Comm.is6BTag) {
//                            epcstr = lsTagList6B.get(ListIndex).strUID;
//
//                            LogUtil.e("TAG2","lsTagList6B.get(ListIndex).strUID  epcstr ="+epcstr);
//
//                            //liudongwen
//                            if(uploadSet.contains(epcstr)==false){
//
//                                //demo
//                                if (epcstr != null && epcstr != "") {
//                                    epcstr = lsTagList6B.get(ListIndex).strUID.replace(" ", "");
//                                    Map<String, String> m = new HashMap<String, String>();
//                                    m.put(Coname[0], String.valueOf(index));
//                                    m.put(Coname[1], epcstr);
//                                    //liudongwen
//                                    uploadSet.add(epcstr);
//                                    //demo
//                                    ReadCnt = lsTagList6B.get(ListIndex).nTotal;
//                                    m.put(Coname[2], String.valueOf(ReadCnt));
//                                    //int mRSSI=Integer.parseInt(lsTagList.get(ListIndex).strRSSI);
//                                    index++;
//                                    list.add(m);
//                                }
//
//                            }
//                        } else {

                    epcstr = lsTagList.get(ListIndex).strEPC;

//                    LogUtil.e("TAG2","lsTagList.get(ListIndex).strEPC  epcstr ="+epcstr);

                    if (Comm.dt == Comm.DeviceType.supoin_JT && Comm.To433Index < index) {
                        Comm.mWirelessMg.writeTo433(epcstr + "\n");
                        Comm.To433Index++;
                    }

                    if (epcstr != null && !epcstr.equals("")) {

                        epcstr = lsTagList.get(ListIndex).strEPC.replace(" ", "");


                        if (epcstr.length() > 24) {
                            epcstr = epcstr.substring(0, 24) + "\r\n" + epcstr.substring(24);
                        }


                        //TODO 数据处理
                        epcstr = DataDoWithUtil.dataConversion(epcstr);



                        //liudongwen
                        if(uploadSet.contains(epcstr)==false){
                            //demo
                            Map<String, String> m = new HashMap<String, String>();
                            m.put(Coname[0], String.valueOf(index));
                            m.put(Coname[1], epcstr);

                            //liudongwen
                            uploadSet.add(epcstr);

                            ////demo该标签读取的次数;
                            ReadCnt = lsTagList.get(ListIndex).nReadCount;
                            m.put(Coname[2], String.valueOf(ReadCnt));
                            //int mRSSI=Integer.parseInt(lsTagList.get(ListIndex).strRSSI);
                            index++;
                            list.add(m);
                        }

//                            }
//                        }
                    }
                    ListIndex++;
                    tagListSize--;
                }
            }
            //liudongwen 为零就是清空了,所以要刷新,大于原来的就是新加了, 也要刷新;
            if(uploadSet!=null&&(uploadSet.size()==0||(uploadSet.size()>uploadSetStartSize))){

//                LogUtil.e("TAG","uploadSet.size() ========================> " + uploadSet.size());
//                LogUtil.e("TAG","uploadSet.toString()========================>)"+uploadSet.toString());

                //刷新界面;
                notifyActivityUpdateAdapter();
                //更新读到的标签总数;
                totalTagsSum = uploadSet.size();
                tvTotalScan.setText("总数:"+totalTagsSum);

                //demo
                if (!isQuick || !Comm.isrun) {


                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void release() {
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








    private void postString() {

//	    for(int i =0;i<1000;i++){
//
//	        uploadSet.add("testData:"+i);
//
//        }
        if(uploadSet.size()==0){
//            ToastUtil.showToast(mContext,"没有扫描数据");
            return;
        }




        String content = getJsonObject();

        String url = PreferenceUtil.get(this, Const.KEY_URL_SERVER,"http://192.168.137.1:8089/rfidscanned/upload");

//        OkHttpUtils
//                .postString()
//                .url(url)
//                .tag(cancelTag)
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
////                .content(new Gson().toJson(new User("zhy", "123")))
//                .build()
//                .execute(new MyStringCallback());


//        LogUtil.e("TAG","url = " + url);
//        LogUtil.e("TAG","content = " + content);

        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("json",content)
                .build().execute(new MyStringCallback());

    }

    private String getJsonObject(){

        LogUtil.e("TAG","uploadSet.size() = " + uploadSet.size());

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

        if(uploadSet!=null){

            boolean isFirst = true;
            Iterator<String> it = uploadSet.iterator();
            while (it.hasNext()) {
                String str = it.next();
                if(isFirst){
                    isFirst = false;
                }
                else{
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





    private void testAlsert(){
        soundError();
        isCanRunRead = true;
        buttonClearOn();
    }






    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            //todo 提示正在上传;
            isCanRunRead = false;
//            tvTips.setVisibility(View.VISIBLE);

            //liudongwen
            dialog_window_background.setVisibility(View.VISIBLE);


            if(tvShow==null){
                tvShow = (TextView) dialog_window_background.findViewById(R.id.tvShow);
            }
            if(circularProgressBar==null){
                circularProgressBar = (MCircularProgressBar) dialog_window_background.findViewById(com.maning.mndialoglibrary.R.id.circularProgressBar);
            }
            circularProgressBar.setProgress(0);
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

            LogUtil.e("TAG", "e ------------>" + e);

//            isCanRunRead = true;
            //todo 取消显示正在上传
//            tvTips.setVisibility(View.GONE);
            dialog_window_background.setVisibility(View.GONE);

            LogUtil.e("TAG","onError " + e.toString());

            if (mContext != null ) {
                final String info;
                if (e instanceof UnknownHostException) {
                    info = "网络未连接!";
                } else if (e instanceof SocketTimeoutException) {
                    info = "连接网络超时!";
                } else if(e instanceof ConnectException){
                    info = "连接地址: "+PreferenceUtil.get(mContext,Const.KEY_URL_SERVER,ResHelper.getString(mContext,R.string.url_server_address))+" 失败";
                }
                else {
                    info = e.toString();
                }

//                addViewAndShowInfo("网络错误",info);

                testAlsert();
                Toast.makeText(mContext,
                        ""+info,
                        Toast.LENGTH_LONG).show();

            }

            Log.e("TAG","Exception = "+e.toString());

        }

        @Override
        public void onResponse(String response, int id) {

//            isCanRunRead = true;
//            tvTips.setVisibility(View.GONE);
//            LogUtil.e("TAG", "onResponse：complete-------->");
//
//
//            LogUtil.e("TAG", "response ------------> " + response);
//            LogUtil.e("TAG", "id ------------>" + id);

            ResponseObj obj = GsonUtil.toResponseObj(response);
            if(obj==null){
                //addViewAndShowInfo("返回信息错误",response);
                //soundError();

                testAlsert();
                Toast.makeText(mContext,
                        "返回信息错误:"+response,
                        Toast.LENGTH_LONG).show();

            }
//            else {
//                if("0".equals(String.valueOf(obj.getNumber()))){
//                    //todo 提示成功
////					showSuccessUploadDialog();
//                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
////                    ToastUtil.showToast(mContext,"上传成功!");
//                    //todo 清空数据
//                    buttonClearOn();
//                    isCanRunRead = true;
//
//                }
//                else{
//                    soundError();
//                    addViewAndShowInfo("发生串货","串货数量:"+String.valueOf(obj.getNumber()));
//                }
//
//            }
            else {
                if(StringHelper2.isEmpty(obj.getMyCollude())&&StringHelper2.isEmpty(obj.getMyPackage())){
                    //todo 提示成功
//					showSuccessUploadDialog();
                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
//                    ToastUtil.showToast(mContext,"上传成功!");
                    //todo 清空数据
                    buttonClearOn();
                    isCanRunRead = true;
                }
                else if(!StringHelper2.isEmpty(obj.getMyCollude())&&!StringHelper2.isEmpty(obj.getMyPackage())){
//                    soundError();
//                    addViewAndShowInfo("发生串货和包异常",
//                            "串货数量:"+String.valueOf(obj.getMyCollude() + "\n" +
//                                    "异常信息: "+obj.getMyPackage()
//                            ));

                    testAlsert();
                    Toast.makeText(mContext,
                            "发生串货和包异常"+"\n"+"串货数量:"+String.valueOf(obj.getMyCollude()) + "\n" + "异常信息: "+obj.getMyPackage(),
                            Toast.LENGTH_LONG).show();
                }

                else if(!StringHelper2.isEmpty(obj.getMyCollude())){
//                    soundError();
//                    addViewAndShowInfo("发生串货", "串货数量:"+String.valueOf(obj.getMyCollude()));

                    testAlsert();
                    Toast.makeText(mContext,
                            "发生串货"+"\n"+ "串货数量:"+String.valueOf(obj.getMyCollude()),
                            Toast.LENGTH_LONG).show();

                }
                else if(!StringHelper2.isEmpty(obj.getMyPackage())){

//                    soundError();
//                    addViewAndShowInfo("包异常", "异常信息:"+String.valueOf(obj.getMyPackage()));

                    testAlsert();
                    Toast.makeText(mContext,
                            "包异常:"+"\n"+"异常信息:"+String.valueOf(obj.getMyPackage()),
                            Toast.LENGTH_LONG).show();

                }


//                else if(!StringHelper2.isEmpty(obj.getMyPackage())){
//
//                })



//                if("0".equals(String.valueOf(obj.getNumber()))){
//
//
//                }
//                else{
//                    soundError();
//                    addViewAndShowInfo("发生串货","串货数量:"+String.valueOf(obj.getNumber()));
//                }

            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            LogUtil.e("TAG", "inProgress=================>" + progress);
            int currentProgress = (int) (100 * progress);

            if (currentProgress < 100) {
                tvShow.setText("上传进度:"+currentProgress+"%");
                //添加动画平滑过度
                circularProgressBar.setProgress(progress, true);
            } else {
//				mHandler.removeCallbacks(runnable);
                tvShow.setText("上传进度:100%");
                circularProgressBar.setProgress(100);
                //关闭
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog_window_background.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        }
    }
    TextView tvShow;
    MCircularProgressBar circularProgressBar;











    private void soundError() {
        mHandler.postDelayed(this, 100);
    }

     Thread playSouncThread = new playSouncThread(new playSoundRunnable());
    @Override
    public void run() {
        playSouncThread.run();
    }
    public class playSoundRunnable implements Runnable {
        @Override
        public void run() {
            LogUtil.e("TAG", "已经执行");
            playSound();
        }
    }
    public class playSouncThread extends Thread {
        public playSouncThread(Runnable target) {
            super(target);
        }
    }
    private SoundPool sp;
    private int shoot1id = -1;
    private void playSound() {
        initSoundPool();
        LogUtil.e("TAG", "发出声音");
        sp.play(shoot1id, 1f, 1f, 0, 0, 1.0f);
    }
    private void initSoundPool() {
        if (sp == null) {
            sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            shoot1id = sp.load(this, R.raw.alarmbell5s, 1);
        }
    }

    private void notifyActivityUpdateAdapter(){
        Intent mIntent = new Intent();
        mIntent.setAction("$_UpdateAdapter");
        getApplicationContext().sendBroadcast(mIntent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
//        handler.removeCallbacks(task);
//        LogUtil.i(TAG, "onDestroy");
        if(view!=null && view.getVisibility()==View.VISIBLE){
            wm.removeView(view);
        }

        cancelPostString();

        //避免内存泄露
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
        if(connectH!=null){
            connectH.removeCallbacksAndMessages(null);
        }
        if(uhfhandler!=null){
            uhfhandler.removeCallbacksAndMessages(null);
        }
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }

        //liudongwen
        release();

        LogUtil.e("TAG","onUnbind");

        isFloatViewIsShowing = false;
        isCanRunRead = true;
        list = new ArrayList<>();
        uploadSet = new HashSet<>();

        return super.onUnbind(intent);

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {

        LogUtil.e("TAG1","getScanCode=========>"+ event.getScanCode());
        LogUtil.e("TAG1","getKeyCode=========>"+ event.getKeyCode());

//        int key = event.getKeyCode();
        if (isFloatViewIsShowing==true &&event.getScanCode() == Const.KEY_SCAN_BUTTON && event.getAction() == KeyEvent.ACTION_UP){
            scanBizForScanButton();

            LogUtil.e("TAG1","getAction=========>"+ event.getAction());
            return true;
        }
        else{
            return super.onKeyEvent(event);
        }



//      Toast.makeText(mContext,"key=========>"+ key,Toast.LENGTH_LONG).show();

//
//        return true;
    }



}
