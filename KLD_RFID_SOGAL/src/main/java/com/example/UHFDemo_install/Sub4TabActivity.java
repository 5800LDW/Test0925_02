package com.example.UHFDemo_install;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.ldw.xyz.util.LogUtil;
import com.uhf.uhf.Common.Comm;
import com.uhf.uhf.UHF1.UHF1Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import kld.com.rfid.ldw.R;

import static com.uhf.uhf.Common.Comm.getAntFre;
import static com.uhf.uhf.Common.Comm.getAntPower;
import static com.uhf.uhf.Common.Comm.getAntReg;
import static com.uhf.uhf.Common.Comm.getQValue;
import static com.uhf.uhf.Common.Comm.getSes;
import static com.uhf.uhf.Common.Comm.moduleType;
import static com.uhf.uhf.Common.Comm.opeT;
import static com.uhf.uhf.Common.Comm.operateType.getSession;
import static com.uhf.uhf.Common.Comm.operateType.nullOperate;
import static com.uhf.uhf.Common.Comm.operateType.setQ;
import static com.uhf.uhf.Common.Comm.operateType.setReg;
import static com.uhf.uhf.Common.Comm.setANTCheck;
import static com.uhf.uhf.Common.Comm.setAntPower;
import static com.uhf.uhf.Common.Comm.setAntReg;
import static com.uhf.uhf.Common.Comm.setQValue;
import static com.uhf.uhf.Common.Comm.setSes;
import static com.uhf.uhf.UHF1.UHF001.er;

public class Sub4TabActivity extends  Activity {


//    String[] spireg = {"China", "North America", "Japan", "Korea", "Europe", "Europe2", "Europe3", "India",
//            "Canada", "Full-Band", "China2"};
//    String[] uhf5spireg = {"CHN(China)", "FCC(America)", "ETSI(Europe)", "OTHER"};
//    String[] uhf6spireg = {"CHN(China)","CHN(China)1", "ETSI(Europe)", "OTHER", "FCC(America)"};

    String[] spireg = {"CHN(China)", "FCC(America)", "ETSI(Europe)", "OTHER"};

    String[] spibank = {"save area", "EPC area", "TID area", "user area"};
    String[] spifbank = {"EPC area", "TID area", "user area"};
    String[] spises = {"S0", "S1", "S2", "S3"};

    String[] spiq = {"AUTO", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15"};
    String[] spinvmo = {"General model", "Super-speed model"};
    String[] spiblf = {"40", "250", "400", "640"};
    String[] spimlen = {"96", "496"};
    String[] spitget = {"A", "B", "A-B", "B-A"};
    String[] spigcod = {"FM0", "M2", "M4", "M8"};
    String[] spitari = {"25碌Ms", "12.5碌Ms", "6.25碌Ms"};
    String[] spiwmod = {"written byte", "written lump"};
    String[] spi6btzsd = {"99percent", "11percent"};
    String[] spidelm = {"Delimiter1", "Delimiter4"};

    CheckBox cb_allsel;
    RadioGroup rg_emdenable, rg_antcheckenable;

    private ArrayAdapter<String> arrdp_ses,
            arradp_pow, arrdp_q, arradp_reg;
    Spinner spinner_ant1pow, spinner_ant2pow, spinner_ant3pow,
            spinner_ant4pow, spinner_sesion, spinner_q, spinner_region;
    public static TabHost tabHost_set;

    Button button_getantpower, button_setantpower, button_getantcheck,
            button_setantcheck, button_getgen2ses, button_setgen2ses,
            button_getgen2q, button_setgen2q, button_getreg, button_setreg, button_getfre, button_setfre,
            button_intervaltimeset, button_intervaltimeget, button_runtimeget, button_runtimeset ,button_device_no_get, button_templerature_get;
    EditText et_intervaltime, et_runtime,et_device_no,et_templerature;
    ListView elist;
    Switch switch_repleat_sound;
    private List<String> mFreqStartList = new ArrayList<String>();
    private List<String> mFreqEndList = new ArrayList<String>();

    UHF1Application myapp;
    int tabHostIndex = 0;

    private View createIndicatorView(Context context, TabHost tabHost,
                                     String title) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tabIndicator = inflater.inflate(R.layout.tab_indicator_vertical,
                tabHost.getTabWidget(), false);
        final TextView titleView = (TextView) tabIndicator
                .findViewById(R.id.tv_indicator);
        titleView.setText(title);
        return tabIndicator;
    }

    private void showlist(String[] fres) {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(fres));
        MyAdapter2 mAdapter = new MyAdapter2(list, this);

        elist.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab4_tablelayout);

        Application app = getApplication();
        myapp = (UHF1Application) app;
        // 获取TabHost对象
        // 得到TabActivity中的TabHost对象
        tabHost_set = (TabHost) findViewById(R.id.tabhost4);
        // 如果没有继承TabActivity时，通过该种方法加载启动tabHost
        tabHost_set.setup();
        tabHost_set.getTabWidget().setOrientation(LinearLayout.VERTICAL);

        tabHost_set.addTab(tabHost_set.newTabSpec("tab1")
                .setIndicator(createIndicatorView(this, tabHost_set, "功率设置"))
                .setContent(R.id.tab4_sub2_antpow));
        tabHost_set.addTab(tabHost_set.newTabSpec("tab2")
                .setIndicator(createIndicatorView(this, tabHost_set, "频率设置"))
                .setContent(R.id.tab4_sub3_invfre));
        tabHost_set.addTab(tabHost_set.newTabSpec("tab3")
                .setIndicator(createIndicatorView(this, tabHost_set, "其他设置"))
                .setContent(R.id.tab4_sub4_gen2));

        TabWidget tw = tabHost_set.getTabWidget();
        tw.getChildAt(0).setBackgroundColor(Color.BLUE);

        spinner_ant1pow = (Spinner) findViewById(R.id.spinner_ant1pow);
        spinner_ant2pow = (Spinner) findViewById(R.id.spinner_ant2pow);
        spinner_ant3pow = (Spinner) findViewById(R.id.spinner_ant3pow);
        spinner_ant4pow = (Spinner) findViewById(R.id.spinner_ant4pow);

        arradp_pow = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Comm.spipow);
        arradp_pow
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ant1pow.setAdapter(arradp_pow);
        spinner_ant2pow.setAdapter(arradp_pow);
        spinner_ant3pow.setAdapter(arradp_pow);
        spinner_ant4pow.setAdapter(arradp_pow);

        spinner_ant2pow.setEnabled(false);
        spinner_ant3pow.setEnabled(false);
        spinner_ant4pow.setEnabled(false);

        spinner_sesion = (Spinner) findViewById(R.id.spinner_gen2session);
        arrdp_ses = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spises);
        arrdp_ses
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sesion.setAdapter(arrdp_ses);

        spinner_q = (Spinner) findViewById(R.id.spinner_gen2q);
        arrdp_q = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spiq);
        arrdp_q.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_q.setAdapter(arrdp_q);

        rg_antcheckenable = (RadioGroup) findViewById(R.id.radioGroup_antcheck);
        button_getantpower = (Button) findViewById(R.id.button_antpowget);
        button_setantpower = (Button) findViewById(R.id.button_antpowset);
        button_getantcheck = (Button) findViewById(R.id.button_checkantget);
        button_setantcheck = (Button) findViewById(R.id.button_checkantset);
        button_getgen2ses = (Button) findViewById(R.id.button_gen2sesget);
        button_setgen2ses = (Button) findViewById(R.id.button_gen2sesset);
        button_getgen2q = (Button) findViewById(R.id.button_gen2qget);
        button_setgen2q = (Button) findViewById(R.id.button_gen2qset);
        button_getreg = (Button) findViewById(R.id.button_getregion);
        button_setreg = (Button) findViewById(R.id.button_setregion);
        button_getfre = (Button) findViewById(R.id.button_getfre);
        button_setfre = (Button) findViewById(R.id.button_setfre);
        button_intervaltimeset = (Button) findViewById(R.id.button_intervaltimeset);
        button_intervaltimeget = (Button) findViewById(R.id.button_intervaltimeget);
        button_runtimeget = (Button) findViewById(R.id.button_runtimeget);
        button_runtimeset = (Button) findViewById(R.id.button_runtimeset);
        button_device_no_get= (Button) findViewById(R.id.button_device_no_get);
        button_templerature_get= (Button) findViewById(R.id.button_templerature_get);

        et_intervaltime = (EditText) findViewById(R.id.et_intervaltime);
        et_runtime = (EditText) findViewById(R.id.et_runtime);
        et_device_no = (EditText) findViewById(R.id.et_device_no);
        et_templerature= (EditText) findViewById(R.id.et_templerature);

        cb_allsel = (CheckBox) findViewById(R.id.checkBox_allselect);
        elist = (ListView) this.findViewById(R.id.listView_frequency);

        switch_repleat_sound=(Switch)findViewById(R.id.switch_repleat_sound);

        Comm.mOtherHandler = opeHandler;

        rg_antcheckenable.setEnabled(false);
        button_getantcheck.setEnabled(false);
        button_setantcheck.setEnabled(false);

        spinner_region = (Spinner) findViewById(R.id.spinner_region);
        arradp_reg = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spireg);
        arradp_reg
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_region.setAdapter(arradp_reg);

        elist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
                MyAdapter2.ViewHolder holder = (MyAdapter2.ViewHolder) arg1.getTag();
                // 改变CheckBox的状态
                holder.cb.toggle();
                // 将CheckBox的选中状况记录下来
                MyAdapter2.getIsSelected().put(arg2, holder.cb.isChecked());
            }
        });

        switch_repleat_sound.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Comm.repeatSound=true;
                } else {
                    Comm.repeatSound=false;
                }
            }
        });

        button_getantcheck.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    Comm.opeT = Comm.operateType.getAntCheck;
                    Comm.getAntCheck();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Exception:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        });

        button_setantcheck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    Comm.opeT = Comm.operateType.setAntCheck;
                    int antCheckEnable = Comm.SortGroup(rg_antcheckenable);
                    setANTCheck(antCheckEnable);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        button_getantpower.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    Comm.opeT = Comm.operateType.getPower;
                    getAntPower();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Obtain Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }

            }

        });
        button_setantpower.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    Comm.opeT = Comm.operateType.setPower;
                    int ant1pow = spinner_ant1pow.getSelectedItemPosition();
                    int ant2pow = spinner_ant2pow.getSelectedItemPosition();
                    int ant3pow = spinner_ant3pow.getSelectedItemPosition();
                    int ant4pow = spinner_ant4pow.getSelectedItemPosition();

                    LogUtil.e("TAG","----------------------");
                    LogUtil.e("TAG","ant1pow = "+ant1pow);

                    LogUtil.e("TAG","----------------------");
                    LogUtil.e("TAG","ant2pow = "+ant2pow);

                    LogUtil.e("TAG","----------------------");
                    LogUtil.e("TAG","ant3pow = "+ant3pow);

                    LogUtil.e("TAG","----------------------");
                    LogUtil.e("TAG","ant4pow = "+ant4pow);

                    setAntPower(ant1pow, ant2pow, ant3pow, ant4pow);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Set Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
            }
        });
        button_getreg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Comm.opeT = Comm.operateType.getReg;
                getAntReg();
            }
        });
        button_setreg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    Comm.opeT = setReg;
                    int region = spinner_region.getSelectedItemPosition();
                    setAntReg(region);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Sub4TabActivity.this, "Set false:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_getfre.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                int region = spinner_region.getSelectedItemPosition();
                String[] ssf = getAntFre(region);
                if (ssf != null)
                    showlist(ssf);
            }
        });

        button_setfre.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Comm.opeT = Comm.operateType.setFre;

                Toast.makeText(Sub4TabActivity.this, "Unsupported operation", Toast.LENGTH_SHORT).show();//不支持操作
            }
        });

        button_getgen2ses.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    Comm.opeT = getSession;
                    getSes();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Obtain Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        button_setgen2ses.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    Comm.opeT = Comm.operateType.setSession;
                    int session = spinner_sesion.getSelectedItemPosition();
                    setSes(session);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Set Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }

            }

        });
        button_getgen2q.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    Comm.opeT = Comm.operateType.getQ;
                    getQValue();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Set Fail:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_setgen2q.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    Comm.opeT = setQ;
                    int Q = spinner_q.getSelectedItemPosition() - 1;
                    setQValue(Q);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Set Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }

            }

        });

        button_intervaltimeget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    et_intervaltime.setText(String.valueOf(Comm.rfidSleep));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Set Fail:" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        button_intervaltimeset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    String strIntervaltime = et_intervaltime.getText().toString();
                    int Intervaltime = Integer.parseInt(strIntervaltime);
                    for (int i = strIntervaltime.length(); --i >= 0; ) {
                        if (!Character.isDigit(strIntervaltime.charAt(i))) {
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Fail:is not integer", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                    }
                    Comm.rfidSleep = Intervaltime;

                    Toast.makeText(Sub4TabActivity.this,
                            "Set Succeed", Toast.LENGTH_SHORT)
                            .show();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Set Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }

        });
        button_runtimeget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    int runTime = Comm.getRunTime();
                    if (runTime==-1)
                        Toast.makeText(Sub4TabActivity.this,
                                "Unsupported operation" , Toast.LENGTH_SHORT)
                                .show();

                    else  if (runTime==0)
                        Toast.makeText(Sub4TabActivity.this,
                                "Get Fail" , Toast.LENGTH_SHORT)
                                .show();
                    else
                        et_runtime.setText(String.valueOf(runTime));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Set Fail:" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        button_runtimeset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    String strruntime = et_intervaltime.getText().toString();
                    if (strruntime.equals("")) {
                        int rel = Comm.setRunTime(strruntime);
                        if (rel == 1)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Succeed", Toast.LENGTH_SHORT)
                                    .show();
                        else if (rel == 0)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Fail", Toast.LENGTH_SHORT)
                                    .show();
                        else if (rel == -1)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Unsupported operation", Toast.LENGTH_SHORT)
                                    .show();
                    }else
                        Toast.makeText(Sub4TabActivity.this,
                                "Interval time is null", Toast.LENGTH_SHORT)
                                .show();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub4TabActivity.this,
                            "Set Exception:" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        button_device_no_get.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=Sub4TabActivity.this.getString(R.string.app_activityName);
                s=s.substring(s.length()-6);
                String strDeviceNo= Comm.getDeviceNo()+"|"+s;
                et_device_no.setText(strDeviceNo);
            }
        });
        button_templerature_get.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Comm.opeT = Comm.operateType.getTem;
                Comm.getTemperature();
            }
        });
        cb_allsel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @SuppressWarnings("static-access")
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                 TODO Auto-generated method stub
                if (moduleType == Comm.Module.UHF001) {
                    MyAdapter2 m2 = (MyAdapter2) elist.getAdapter();
                    if (arg1 == true) {
                        HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();

                        for (int m = 0; m < m2.getCount(); m++)
                            isSelected.put(m, true);
                        m2.setIsSelected(isSelected);

                    } else {
                        HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();

                        for (int m = 0; m < m2.getCount(); m++)
                            isSelected.put(m, false);
                        m2.setIsSelected(isSelected);
                    }
                    elist.setAdapter(m2);
                }
            }
        });
        tabHost_set.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {
                // TODO Auto-generated method stub
                int j = tabHost_set.getCurrentTab();
                TabWidget tabIndicator = tabHost_set.getTabWidget();
                View vw = tabIndicator.getChildAt(j);
                vw.setBackgroundColor(Color.BLUE);
                int tc = tabHost_set.getTabContentView().getChildCount();
                for (int i = 0; i < tc; i++) {
                    if (i != j) {
                        View vw2 = tabIndicator.getChildAt(i);
                        vw2.setBackgroundColor(Color.TRANSPARENT);
                    } else {
                        switch (i) {
                            case 0:
                                tabHostIndex = 0;
                                opeT = Comm.operateType.getPower;
                                Comm.getAntPower();
                                rg_antcheckenable.check(rg_antcheckenable
                                        .getChildAt(1).getId());
                                break;
                            case 1:
                                button_getreg.performClick();//获取当前频率区域
                                break;
                            case 2:
                                tabHostIndex = 2;
//                                getParameterThread();
//                                button_intervaltimeget.performClick();
//                                try {
//                                    Thread.sleep(300);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                button_runtimeget.performClick();
                                break;
                        }
                    }
                }
            }
        });
    }

    public void getParameterThread() {
        new Thread() {
            public void run() {
                try {
                    int c = 0;
                    switch (tabHostIndex) {
                        case 0:
//                            opeT = Comm.operateType.getPower;
//                            Comm.getAntPower();
//                            while (Comm.opeT != nullOperate) {
//                                Thread.sleep(300);
//                                if (c > 3)
//                                    break;
//                                c++;
//                            }
//                            opeT = Comm.operateType.getAntCheck;
//                            Comm.getAntCheck();
                            break;
                        case 2:
                            opeT = Comm.operateType.getSession;
                            Comm.getSes();
                            while (Comm.opeT != nullOperate) {
                                Thread.sleep(300);
                                if (c > 3)
                                    break;
                                c++;
                            }
                            opeT = Comm.operateType.getQ;
                            Comm.getQValue();
                            while (Comm.opeT != nullOperate) {
                                Thread.sleep(300);
                                if (c > 3)
                                    break;
                                c++;
                            }
                            break;
                        default:
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }


    @SuppressLint("HandlerLeak")
    private android.os.Handler opeHandler = new android.os.Handler() {
        @SuppressWarnings({"unchecked", "unused"})
        @Override
        public void handleMessage(Message msg) {
            try {
                Bundle b = msg.getData();
                switch (Comm.opeT) {
                    case getAntCheck:
                        int antIndex = b.getInt("antIndex");
                        if (antIndex == 0)
                            rg_antcheckenable.check(rg_antcheckenable
                                    .getChildAt(0).getId());
                        else if (antIndex == 1)
                            rg_antcheckenable.check(rg_antcheckenable
                                    .getChildAt(1).getId());
                        else
                            Toast.makeText(Sub4TabActivity.this,
                                    "Fail:" + er.toString(), Toast.LENGTH_SHORT)
                                    .show();
                        break;
                    case setAntCheck:
                        b = msg.getData();
                        boolean isSetAntCheck =  b.getBoolean("isSetAntCheck");
                        if (isSetAntCheck)
                            Toast.makeText(Sub4TabActivity.this, "Set Success",
                                    Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Fail", Toast.LENGTH_SHORT)
                                    .show();
                        break;
                    case getPower:
                        try {
                            b = msg.getData();
                            int ant1Power = b.getInt("ant1Power");
                            int ant2Power = b.getInt("ant2Power");
                            int ant3Power = b.getInt("ant3Power");
                            int ant4Power = b.getInt("ant4Power");
                            for (int i = 0; i < 4; i++) {
                                if (i == 0)
                                    spinner_ant1pow.setSelection(ant1Power);
                                else if (i == 1)
                                    spinner_ant2pow.setSelection(ant2Power);
                                else if (i == 2)
                                    spinner_ant3pow.setSelection(ant3Power);
                                else
                                    spinner_ant4pow.setSelection(ant4Power);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("uhf6readOp", "Exception" + e.getMessage());
                        }
                        break;
                    case setPower:
                        b = msg.getData();
                        boolean isSetPower =  b.getBoolean("isSetPower");
                        if (isSetPower)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Succeed", Toast.LENGTH_SHORT)
                                    .show();
                        else
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Fail", Toast.LENGTH_SHORT)
                                    .show();
                        break;
                    case getReg:
                        b = msg.getData();
                        int reg =  b.getInt("getReg");
                        if (reg > -1)
                            spinner_region.setSelection(reg);
                        else
                            Toast.makeText(Sub4TabActivity.this,
                                    "Get Fail", Toast.LENGTH_SHORT)
                                    .show();
                        break;
                    case setReg:
                        b = msg.getData();
                        boolean isSetReg = b.getBoolean("isSetReg");
                        if (isSetReg)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Succeed", Toast.LENGTH_SHORT)
                                    .show();
                        else
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Fail", Toast.LENGTH_SHORT)
                                    .show();
                        break;
                    case getFre:
                        break;
                    case setFre:
                        break;
                    case getQ:
                        b = msg.getData();
                        int Q = b.getInt("Q");
                        if (Q == -1)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Unsupported operation", Toast.LENGTH_SHORT)
                                    .show();
                        else
                            spinner_q.setSelection(Q + 1);
                        break;
                    case setQ:
                        b = msg.getData();
                        int isSetQ = b.getInt("isSetQ");
                        if (isSetQ==1)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Succeed", Toast.LENGTH_SHORT)
                                    .show();
                        else if (isSetQ==0)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Fail", Toast.LENGTH_SHORT)
                                    .show();
                        else if (isSetQ==-1)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Unsupported operation", Toast.LENGTH_SHORT)
                                    .show();
                        break;
                    case getSession:
                        b = msg.getData();
                        int session = b.getInt("session");
                        if (session>=0)
                            spinner_sesion.setSelection(session);
                        else
                            Toast.makeText(Sub4TabActivity.this,
                                    "Unsupported operation", Toast.LENGTH_SHORT)
                                    .show();
                        break;
                    case setSession:
                        b = msg.getData();
                        boolean isSetSes = b.getBoolean("isSetSes");
                        if (isSetSes)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Succeed", Toast.LENGTH_SHORT)
                                    .show();
                        else
                            Toast.makeText(Sub4TabActivity.this,
                                    "Set Fail", Toast.LENGTH_SHORT)
                                    .show();
                        break;
                    case getTem:
                        b = msg.getData();
                        String strTemperature=b.getString("Tem");
                        Log.d("UHF","strTemperature:"+strTemperature);
                        if (strTemperature== null || strTemperature.length() == 0)
                            Toast.makeText(Sub4TabActivity.this,
                                    "Get Fail", Toast.LENGTH_SHORT)
                                    .show();
                        else if(strTemperature.equals("0"))
                            Toast.makeText(Sub4TabActivity.this,
                                    "Unsupported operation", Toast.LENGTH_SHORT)
                                    .show();
                        else {
                            et_templerature.setText(strTemperature);
                            Toast.makeText(Sub4TabActivity.this,
                                    "Get Succeed", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Comm.opeT = nullOperate;
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - myapp.exittime) > 2000) {
                Toast.makeText(getApplicationContext(), "Touch again to exit",
                        Toast.LENGTH_SHORT).show();
                myapp.exittime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
