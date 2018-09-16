package com.example.UHFDemo_install;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.uhf.uhf.Common.Comm;
import com.uhf.uhf.UHF1.UHF1Application;
import com.uhf.uhf.UHF1.UHF1Function.AndroidWakeLock;
import com.uhf.uhf.UHF1.UHF1Function.SPconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import kld.com.rfid.ldw.R;

import static com.example.UHFDemo_install.Sub4TabActivity.tabHost_set;
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


//import com.supoin.wireless.WirelessManager;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
    TextView tv_state, tv_tags, textView_time;
    Button button_read, button_stop, button_clean, button_set;
    CheckBox cb_is6btag, cb_readtid;
    private ListView listView;
    TabHost tabHost;
    public long exittime;
    List<Byte> LB = new ArrayList();
    int scanCode = 0;
    private Timer timer = null;
    private TimerTask task = null;
    private Message msg = null;
    private long mlCount = 0;
    private long mlTimerUnit = 10;
    private int totalSec, yushu, min, sec;
    private String strMsg = "";

    TabWidget tw;

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Activity","onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        InitDevice();
        Log.d("Activity","onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        release();
        Log.d("Activity","onStop()");
    }

    public class MyEpListAdapter extends ArrayAdapter<Object> {
        @SuppressWarnings("unchecked")
        public MyEpListAdapter(Context context, int resource,
                               int textViewResourceId,
                               @SuppressWarnings("rawtypes") List objects) {
            super(context, resource, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d("Activity","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Activity","onPause()");
    }

    @Override
    protected void onDestroy() {
        Log.d("Activity", "onDestroy");
        super.onDestroy();
    }

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
                if (readCount > 0)
                    tv_state.setText(String.valueOf(readCount));

//                if (tagListSize > 0 && (System.currentTimeMillis() - exittime) > 2000) {
//                    showlist();
//                    exittime = System.currentTimeMillis();
//                }
                if (tagListSize > 0)
                   showlist();
                Log.e("uhfhandler", "readCount : " + String.valueOf(readCount));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Tag", e.getMessage());
            }
        }
    };


















    @SuppressLint("HandlerLeak")
    public Handler connectH = new Handler() {
        @SuppressLint("SetTextI18n")
        @SuppressWarnings({"unchecked", "unused"})
        @Override
        public void handleMessage(Message msg) {
            try {
                if (Comm.moduleType == Comm.Module.UHF005)
                    cb_is6btag.setEnabled(true);
                else
                    cb_is6btag.setEnabled(false);
//                UHF001.mhandler = uhfhandler;
//                if (null != rfidOperate) {
//                    rfidOperate.mHandler = uhfhandler;
//                    cb_is6btag.setEnabled(true);
//                }
//                if (null != Comm.uhf6)
//                    Comm.uhf6.UHF6handler = uhfhandler;
                Comm.mInventoryHandler = uhfhandler;

                Bundle bd = msg.getData();
                strMsg = bd.get("Msg").toString();
                if (!TextUtils.isEmpty(strMsg)) {
                    tv_state.setText(strMsg);
                    Comm.SetInventoryTid(cb_readtid.isChecked());

                } else
                    tv_state.setText(R.string.device_msg);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("connectH", e.getMessage());
            }
        }
    };

























    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        //cb_is6btag.setEnabled(false);

        button_set.setText(R.string.Real_time);
        tv_state.setText(R.string.device_msg);
        Awl = new AndroidWakeLock((PowerManager) getSystemService(Context.POWER_SERVICE));
        exittime = System.currentTimeMillis();
        button_read.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unused")
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    startTimerTask();
                    button_clean.performClick();
                    tv_state.setText(R.string.read_state);
                    Awl.WakeLock();
                    Comm.startScan();
                    ReadHandleUI();
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this,
                            "ERR：" + ex.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        button_stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Awl.ReleaseWakeLock();
                stopTimerTask();
                tv_state.setText(R.string.stop_read_msg);
                Comm.stopScan();
                showlist();
                StopHandleUI();
            }
        });

        button_clean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                int i = Comm.lv.size();
                Comm.lv.clear();
                tv_tags.setText("0");
                tv_state.setText(R.string.clearAll_Wait);//清空完成,等待操作...
                Comm.clean();
                showlist();
            }
        });

        button_set.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (Comm.setParameters()) {
                    if (isQuick) {//是否快速模式 true为是 false为否
                        isQuick = false;
                        button_set.setText(R.string.Real_time);
                        Toast.makeText(MainActivity.this, "RealTime mode set success!", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        isQuick = true;
                        button_set.setText(R.string.fast_time);
                        Toast.makeText(MainActivity.this, "Quick mode set success!", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else
                    Toast.makeText(MainActivity.this, "set false!", Toast.LENGTH_SHORT)
                            .show();
            }
        });
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                arg1.setBackgroundColor(Color.YELLOW);

                // @SuppressWarnings("unchecked")
//                HashMap<String, String> hm = (HashMap<String, String>) listView
//                        .getItemAtPosition(arg2);
                //String epc = hm.get("EPC ID");
                // myapp.Curepc = epc;

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
                // TODO Auto-generated method stub
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
                // TODO Auto-generated method stub
                if (Comm.SetInventoryTid(isChecked))
                    Toast.makeText(MainActivity.this, "set success!", Toast.LENGTH_SHORT)
                            .show();
                else
                    Toast.makeText(MainActivity.this, "set fail!", Toast.LENGTH_SHORT)
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
    }

    //开始计时
    public void startTimerTask() {
        if (null == timer) {
            if (null == task) {
                task = new TimerTask() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (null == msg) {
                            msg = new Message();
                        } else {
                            msg = Message.obtain();
                        }
                        msg.what = 1;
                        handlerTimerTask.sendMessage(msg);
                    }
                };
            }
            timer = new Timer(true);
            timer.schedule(task, mlTimerUnit, mlTimerUnit); // set timer duration
        }
    }

    //停止计时
    public void stopTimerTask() {
        if (null != timer) {
            task.cancel();
            task = null;
            timer.cancel(); // Cancel timer
            timer.purge();
            timer = null;
            handlerTimerTask.removeMessages(msg.what);
        }
        mlCount = 0;
    }

    //异步处理计时数据
    @SuppressLint("HandlerLeak")
    public Handler handlerTimerTask = new Handler() {
        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    mlCount++;
                    totalSec = 0;
                    // 100 millisecond
                    totalSec = (int) (mlCount / 100);
                    yushu = (int) (mlCount % 100);
                    // Set time display
                    min = (totalSec / 60);
                    sec = (totalSec % 60);
                    try {
                        // 100 millisecond
                        textView_time.setText(String.format("%1$02d:%2$02d:%3$d", min, sec, yushu));
                    } catch (Exception e) {
                        textView_time.setText("" + min + ":" + sec + ":" + yushu);
                        e.printStackTrace();
                        Log.e("MyTimer onCreate", "Format string error.");
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };


    public void InitDevice() {
        Comm.repeatSound = true;
        //getApplication()迷惑客户，往往导致出错
//        Comm.app = getApplication();
        Comm.app = new UHF1Application();
        Comm.spConfig = new SPconfig(this);
        context = MainActivity.this;
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

    String[] Coname = new String[]{"NO", "                    EPC ID ", "Count"};

    private void showlist() {
        try {
            int index = 1;
            int ReadCnt = 0;//个数
            List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
            Map<String, String> h = new HashMap<String, String>();
            for (int i = 0; i < Coname.length; i++)
                h.put(Coname[i], Coname[i]);
            list.add(h);
            String epcstr = "";//epc

            int ListIndex = 0;
            if (tagListSize > 0)
                tv_tags.setText(String.valueOf(tagListSize));
            if (isQuick && !Comm.isrun)
                tv_state.setText(String.valueOf(R.string.please_wait));

            if (!isQuick || !Comm.isrun) {
                while (tagListSize > 0) {
                    if (index < 100) {
                        if (Comm.is6BTag) {
                            epcstr = lsTagList6B.get(ListIndex).strUID;
                            if (epcstr != null && epcstr != "") {
                                epcstr = lsTagList6B.get(ListIndex).strUID.replace(" ", "");
                                Map<String, String> m = new HashMap<String, String>();
                                m.put(Coname[0], String.valueOf(index));
                                m.put(Coname[1], epcstr);
                                ReadCnt = lsTagList6B.get(ListIndex).nTotal;
                                m.put(Coname[2], String.valueOf(ReadCnt));
                                //int mRSSI=Integer.parseInt(lsTagList.get(ListIndex).strRSSI);
                                index++;
                                list.add(m);
                            }
                        } else {
                            epcstr = lsTagList.get(ListIndex).strEPC;
                            if (Comm.dt == Comm.DeviceType.supoin_JT && Comm.To433Index < index) {
                                Comm.mWirelessMg.writeTo433(epcstr + "\n");
                                Comm.To433Index++;
                            }

                            if (epcstr != null && !epcstr.equals("")) {
                                epcstr = lsTagList.get(ListIndex).strEPC.replace(" ", "");
                                if (epcstr.length() > 24) {
                                    epcstr = epcstr.substring(0, 24) + "\r\n" + epcstr.substring(24);
                                }

                                Map<String, String> m = new HashMap<String, String>();
                                m.put(Coname[0], String.valueOf(index));
                                m.put(Coname[1], epcstr);
                                ReadCnt = lsTagList.get(ListIndex).nReadCount;
                                m.put(Coname[2], String.valueOf(ReadCnt));
                                //int mRSSI=Integer.parseInt(lsTagList.get(ListIndex).strRSSI);
                                index++;
                                list.add(m);
                            }
                        }
                    }
                    ListIndex++;
                    tagListSize--;
                }

            }
            if (!isQuick || !Comm.isrun) {
                ListAdapter adapter = new MyAdapter(this, list,
                        R.layout.listitemview_inv, Coname, new int[]{
                        R.id.textView_readsort, R.id.textView_readepc,
                        R.id.textView_readcnt});

                // layout为listView的布局文件，包括三个TextView，用来显示三个列名所对应的值
                // ColumnNames为数据库的表的列名
                // 最后一个参数是int[]类型的，为view类型的id，用来显示ColumnNames列名所对应的值。view的类型为TextView
                listView.setAdapter(adapter);

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (!isrun)
            tv_state.setText(R.string.wait_operate);
    }

    private void ReadHandleUI() {
        this.button_read.setEnabled(false);
        this.button_stop.setEnabled(true);
        this.button_set.setEnabled(false);
        this.button_clean.setEnabled(false);
        TabWidget tw = Comm.supoinTabHost.getTabWidget();
        tw.getChildAt(1).setEnabled(false);
        tw.getChildAt(2).setEnabled(false);
    }

    private void StopHandleUI() {
        button_read.setEnabled(true);
        button_stop.setEnabled(false);
        this.button_set.setEnabled(true);
        this.button_clean.setEnabled(true);
        TabWidget tw = Comm.supoinTabHost.getTabWidget();
        tw.getChildAt(1).setEnabled(true);
        tw.getChildAt(2).setEnabled(true);
    }

    @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exittime) > 2000) {
                Toast.makeText(getApplicationContext(), "Touch again to exit",
                        Toast.LENGTH_SHORT).show();
                exittime = System.currentTimeMillis();
            } else {
                release();
                //finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void release() {
        try {
            if (isrun)
                Comm.stopScan();
            Comm.Exit();
            Comm.powerDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 释放按键事件 */
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getScanCode() == 261 && isrun)
            button_stop.performClick();
        else if (event.getScanCode() == 261 && !isrun)
            button_read.performClick();
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        try {
//            scanCode = e.getScanCode();
//            Log.d("UHF","getScanCode "+scanCode);
            return super.dispatchKeyEvent(e);
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
            return false;
        }
    }
}
