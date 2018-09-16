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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.uhf.uhf.Common.Comm;
import com.uhf.uhf.Common.StringTool;
import com.uhf.uhf.UHF1.UHF001;
import com.uhf.uhf.UHF1.UHF1Application;

import kld.com.rfid.ldw.R;

import static com.uhf.uhf.Common.Comm.lsTagList;
import static com.uhf.uhf.Common.Comm.moduleType;
import static com.uhf.uhf.Common.Comm.opeT;
import static com.uhf.uhf.Common.Comm.operateType.nullOperate;
import static com.uhf.uhf.Common.Comm.strPwd;
import static com.uhf.uhf.Common.Comm.tagListSize;

public class Sub3TabActivity extends Activity {

    String[] spibank = {"RESERVE", "EPC", "TID", "USER"};
    String[] spifbank = {"EPC", "TID", "USER"};
    String[] spilockbank = {"访问密码", "销毁密码", "EPC", "TID", "USER"};
    String[] spilocktype = {"解锁/开放", "暂时锁定", "永久锁定"};

    static Button button_readop, button_read6b, button_writeop, button_writeepcop, button_lockop, button_lock6b, button_get_lock6b_state;
    static Spinner spinner_bankr, spinner_bankw, spinner_6btagl,spinner_check_tag,
            spinner_lockbank, spinner_locktype, spinner_6btagr, spinner_6btagw;
    TabHost tabHost_op;
    RadioGroup gr_opant, gr_wdatatype;
    CheckBox cb_pwd;
    static EditText etPWD, etCountR, etStatrAddR, etDataR, etCountW, etStatrAddW, etDataW, editText_checkadd, editText_lockadd;
    TextView tv_addatate;
    static String[] spi6btag = null,spichecktag=null;
    static Context m_Context;


    @SuppressWarnings("rawtypes")
    private static ArrayAdapter arradp_checktag, arradp_6btag, arradp_bank, arradp_fbank, arradp_lockbank, arradp_locktype;
    UHF1Application myapp;


    private View createIndicatorView(Context context, TabHost tabHost, String title) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tabIndicator = inflater.inflate(R.layout.tab_indicator_vertical, tabHost.getTabWidget(), false);
        final TextView titleView = (TextView) tabIndicator.findViewById(R.id.tv_indicator);
        titleView.setText(title);
        return tabIndicator;
    }


    private void getPassword() {
        String strP = etPWD.getText().toString();
        if (cb_pwd.isChecked()) {
            if (strP == null || strP.equals(""))
                Toast.makeText(Sub3TabActivity.this, "访问密码为空", Toast.LENGTH_SHORT).show();
            else
                strPwd = etPWD.getText().toString();
        } else
            strPwd = "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_tablelayout);
        m_Context = Sub3TabActivity.this;

        Application app = getApplication();
        myapp = (UHF1Application) app;

        // 获取TabHost对象
        // 得到TabActivity中的TabHost对象
        tabHost_op = (TabHost) findViewById(R.id.tabhost3);
        // 如果没有继承TabActivity时，通过该种方法加载启动tabHost
        tabHost_op.setup();
        tabHost_op.getTabWidget().setOrientation(LinearLayout.VERTICAL);

        tabHost_op.addTab(tabHost_op.newTabSpec("tab1").setIndicator(createIndicatorView(this, tabHost_op, "READ"))
                .setContent(R.id.tab3_sub_read));
        tabHost_op.addTab(tabHost_op.newTabSpec("tab2").setIndicator(createIndicatorView(this, tabHost_op, "WRITE"))
                .setContent(R.id.tab3_sub_write));
        tabHost_op.addTab(tabHost_op.newTabSpec("tab3").setIndicator(createIndicatorView(this, tabHost_op, "LOCK"))
                .setContent(R.id.tab3_sub_lockkill));

        tabHost_op.setCurrentTab(tabHostIndex);
        TabWidget tw = tabHost_op.getTabWidget();
        tw.getChildAt(tabHostIndex).setBackgroundColor(Color.BLUE);

        spinner_bankr = (Spinner) findViewById(R.id.spinner_bankr);
        spinner_bankw = (Spinner) findViewById(R.id.spinner_bankw);
        arradp_bank = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spibank);
        arradp_bank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arradp_fbank = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spifbank);
        arradp_fbank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bankr.setAdapter(arradp_bank);
        spinner_bankw.setAdapter(arradp_bank);
        spinner_6btagr = (Spinner) findViewById(R.id.spinner_6btagr);
        spinner_6btagw = (Spinner) findViewById(R.id.spinner_6btagw);
        spinner_6btagl = (Spinner) findViewById(R.id.spinner_6btagl);
        spinner_6btagr.setEnabled(false);
        spinner_6btagw.setEnabled(false);
        spinner_6btagl.setEnabled(false);

        spinner_check_tag= (Spinner) findViewById(R.id.spinner_check_tag);

        spinner_lockbank = (Spinner) findViewById(R.id.spinner_lockbank);
        //将可选内容与ArrayAdapter连接起来
        arradp_lockbank = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spilockbank);
        //设置下拉列表的风格
        arradp_lockbank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner_lockbank.setAdapter(arradp_lockbank);

        spinner_locktype = (Spinner) findViewById(R.id.spinner_locktype);
        arradp_locktype = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spilocktype);
        arradp_locktype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_locktype.setAdapter(arradp_locktype);

        button_readop = (Button) findViewById(R.id.button_read);
        //button_read6b = (Button) findViewById(R.id.button_read6b);
        button_writeop = (Button) findViewById(R.id.button_write);
        button_writeepcop = (Button) findViewById(R.id.button_write_epcid);
        button_lockop = (Button) findViewById(R.id.button_lock);
        button_lock6b = (Button) findViewById(R.id.button_lock6b);
        button_get_lock6b_state = (Button) findViewById(R.id.button_get_lock6b_state);

        tv_addatate = (TextView) findViewById(R.id.tv_addatate);

        gr_opant = (RadioGroup) findViewById(R.id.radioGroup_opant);
        gr_wdatatype = (RadioGroup) findViewById(R.id.radioGroup_datatype);
        cb_pwd = (CheckBox) findViewById(R.id.checkBox_opacepwd);

        etPWD = (EditText) findViewById(R.id.editText_password);
        etCountR = (EditText) findViewById(R.id.editText_opcountr);
        etStatrAddR = (EditText) findViewById(R.id.editText_startaddr);
        etDataR = (EditText) findViewById(R.id.editText_datar);

        etCountW = (EditText) findViewById(R.id.editText_opcountw);
        etStatrAddW = (EditText) findViewById(R.id.editText_startaddrw);
        etDataW = (EditText) findViewById(R.id.editText_dataw);

        editText_checkadd = (EditText) findViewById(R.id.editText_checkadd);
        editText_lockadd = (EditText) findViewById(R.id.editText_lockadd);

        spinner_bankr.setSelection(1);
        spinner_bankw.setSelection(1);
        spinner_lockbank.setSelection(2);
        spinner_locktype.setSelection(1);
        // gr_opant.check(gr_opant.getChildAt(myapp.Rparams.opant - 1).getId());
//        }
        Comm.mRWLHandler = tagOpehandler;
        Get6BList();
        GetTagList();

        spinner_check_tag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Comm.mCheckTag= (String)spinner_check_tag.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_readop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    etDataR.setText("");
                    opeT = Comm.operateType.readOpe;
                    int tagBank = 0;
                    int datatype = Comm.SortGroup(gr_wdatatype);
                    int ant = Comm.SortGroup(gr_opant);

                    String opCount = etCountR.getText().toString();
                    String startAdd = etStatrAddR.getText().toString();
                    getPassword();
                    if (Comm.is6BTag) {
                        String strUID = spinner_6btagr.getSelectedItem().toString();
                        String[] result = StringTool.stringToStringArray(strUID.toUpperCase(), 2);
                        Comm.bt6bAryUID = StringTool.stringArrayToByteArray(result, 8);
                    } else
                        tagBank = spinner_bankr.getSelectedItemPosition();
                    Comm.readTag(ant, tagBank, opCount, startAdd, datatype);
                    //Comm.readTagByte(ant,tagBank,opCount,startAdd,0);
                } catch (Exception e) {
                    Toast.makeText(Sub3TabActivity.this, "Exception:" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_writeop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {//写块区数据
                // TODO Auto-generated method stub
                try {
                    boolean isWrite = true;
                    opeT = Comm.operateType.writeOpe;
                    int datatype = Comm.SortGroup(gr_wdatatype);
                    int ant = Comm.SortGroup(gr_opant);
                    int tagBank = spinner_bankw.getSelectedItemPosition();
                    String opCount = "";
                    String startAdd = etStatrAddW.getText().toString();
                    String strWriteData = etDataW.getText().toString();

                    if (tagBank == 1 && (startAdd.equals("0") || startAdd.equals("1"))) {
                        Toast.makeText(Sub3TabActivity.this, "写操作不能操作EPC区第0块和第1块",
                                Toast.LENGTH_SHORT).show();
                    }

                    etCountW.setText(String.valueOf(2));
                    int len = 0;
                    if (Comm.is6BTag) {
                        String strUID = spinner_6btagr.getSelectedItem().toString();
                        String[] result = StringTool.stringToStringArray(strUID.toUpperCase(), 2);
                        Comm.bt6bAryUID = StringTool.stringArrayToByteArray(result, 8);

                        if (datatype == 0 && strWriteData.length() % 2 == 0) {
                            result = StringTool.stringToStringArray(strWriteData.toUpperCase(), 2);
                            len = result.length;
                        } else if (datatype != 0) {
                            Toast.makeText(Sub3TabActivity.this, "不支持操作",
                                    Toast.LENGTH_SHORT).show();
                            isWrite = false;
                        }
                    } else {
                        if (datatype == 0 && strWriteData.length() % 4 == 0) {
                            String[] result = StringTool.stringToStringArray(strWriteData.toUpperCase(), 2);
                            len = (byte) ((result.length / 2 + result.length % 2) & 0xFF);
                        } else if (datatype == 1 && strWriteData.length() % 2 == 0) {
                            len = strWriteData.length() / 2;
                        } else if (datatype == 2) {
                            len = strWriteData.length();
                        } else {
                            Toast.makeText(Sub3TabActivity.this, "输入的数据长度不对",
                                    Toast.LENGTH_SHORT).show();
                            isWrite = false;
                        }
                    }
                    if (isWrite) {
                        etCountW.setText(String.valueOf(len));
                        opCount = etCountW.getText().toString();
                        getPassword();
                        Comm.writeTag(ant, tagBank, opCount, startAdd, datatype, strWriteData);
                    } else
                        Toast.makeText(Sub3TabActivity.this, "write fail",
                                Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub3TabActivity.this, "Sub3Exception:" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("UHF", e.getMessage());
                }
            }
        });

        button_writeepcop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    boolean isWrite = true;
                    opeT = Comm.operateType.writeepcOpe;
                    int datatype = Comm.SortGroup(gr_wdatatype);
                    int ant = Comm.SortGroup(gr_opant);
                    int tagBank = spinner_bankw.getSelectedItemPosition();
                    String opCount = etCountW.getText().toString();
                    String startAdd = etStatrAddW.getText().toString();
                    String strWriteData = etDataW.getText().toString();

//                    if (tagBank == 1 && (startAdd.equals("0") || startAdd.equals("1"))) {
//                        Toast.makeText(Sub3TabActivity.this, "写操作不能操作EPC区第0块和第1块",
//                                Toast.LENGTH_SHORT).show();
//                    }

                    etCountW.setText(String.valueOf(2));
                    getPassword();

                    int len = 0;

                    if (datatype == 0 && strWriteData.length() % 4 == 0) {
                        String[] result = StringTool.stringToStringArray(strWriteData.toUpperCase(), 2);
                        len = (byte) ((result.length / 2 + result.length % 2) & 0xFF);
                    } else if (datatype == 1 && strWriteData.length() % 2 == 0) {
                        len = strWriteData.length() / 2;
                    } else if (datatype == 2) {
                        len = strWriteData.length();
                    } else {
                        Toast.makeText(Sub3TabActivity.this, "输入的数据长度不对",
                                Toast.LENGTH_SHORT).show();
                        isWrite = false;
                    }

                    if (isWrite) {
                        etCountW.setText(String.valueOf(len));
                        getPassword();
                        UHF001.isWriteEpc = true;
                        Comm.writeTag(ant, tagBank, opCount, startAdd, datatype, strWriteData);
                    } else
                        Toast.makeText(Sub3TabActivity.this, "write fail",
                                Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub3TabActivity.this, "Exception:" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_lockop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    opeT = Comm.operateType.lockOpe;
                    int ant = Comm.SortGroup(gr_opant);
                    int lbank = spinner_lockbank.getSelectedItemPosition();
                    int ltype = spinner_locktype.getSelectedItemPosition();
                    getPassword();
                    Comm.lockTag(ant, lbank, ltype);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub3TabActivity.this, "Exception:" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        button_lock6b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    opeT = Comm.operateType.lockOpe;
                    if (Comm.is6BTag) {
                        String strUID = spinner_6btagr.getSelectedItem().toString();
                        String[] result = StringTool.stringToStringArray(strUID.toUpperCase(), 2);
                        Comm.bt6bAryUID = StringTool.stringArrayToByteArray(result, 8);
                    }
                    byte btWordAdd = (byte) (Integer.parseInt(editText_lockadd.getText().toString(), 16) & 0xFF);
                    Comm.lock6BTag(btWordAdd);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub3TabActivity.this, "Exception:" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
        button_get_lock6b_state.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    opeT = Comm.operateType.getLock6bState;
                    if (Comm.is6BTag) {
                        String strUID = spinner_6btagr.getSelectedItem().toString();
                        String[] result = StringTool.stringToStringArray(strUID.toUpperCase(), 2);
                        Comm.bt6bAryUID = StringTool.stringArrayToByteArray(result, 8);
                    }
                    byte btWordAdd = (byte) (Integer.parseInt(editText_checkadd.getText().toString(), 16) & 0xFF);
                    Comm.GetLock6bState(btWordAdd);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(Sub3TabActivity.this, "Exception:" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        tabHost_op.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {
                // TODO Auto-generated method stub
                int j = tabHost_op.getCurrentTab();
                TabWidget tabIndicator = tabHost_op.getTabWidget();
                View vw = tabIndicator.getChildAt(j);
                vw.setBackgroundColor(Color.BLUE);
                int tc = tabHost_op.getTabContentView().getChildCount();
                for (int i = 0; i < tc; i++) {
                    if (i != j) {
                        View vw2 = tabIndicator.getChildAt(i);//51803
                        vw2.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
                if (j == 0 || j == 1) {
                    tabHostIndex = j;
                    //onCreate(null);
                    Get6BList();
                    GetTagList();
                }
            }
        });

        if (moduleType != Comm.Module.UHF001) {
            button_writeepcop.setEnabled(false);
        }
    }

    int tabHostIndex = 0;
    @SuppressLint("HandlerLeak")
    private android.os.Handler tagOpehandler = new android.os.Handler() {
        @SuppressWarnings({"unchecked", "unused"})
        @Override
        public void handleMessage(Message msg) {
            try {
                String strErr="";
                Bundle b = msg.getData();
                switch (Comm.opeT) {
                    case readOpe:
                        strErr= b.getString("Err");
                        String strEPC = b.getString("readData");
                        if (strEPC!=null&&!strEPC.equals("")&&!strEPC.contains("fail")) {
                            etDataR.setText(strEPC);
                        } else
                            Toast.makeText(Sub3TabActivity.this, "Read Fail:" + strErr, Toast.LENGTH_SHORT).show();
                        break;
                    case writeOpe:
                        boolean isWriteSucceed = b.getBoolean("isWriteSucceed");
                        if (isWriteSucceed) {
                            Toast.makeText(Sub3TabActivity.this, "Write Succeed", Toast.LENGTH_SHORT).show();
                            Log.d("UHF", "Write Succeed");
                        } else {
                            Toast.makeText(Sub3TabActivity.this, "Write Fail", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case writeepcOpe:
                        boolean isWriteEPCSucceed = b.getBoolean("isWriteSucceed");
                        if (isWriteEPCSucceed) {
                            Toast.makeText(Sub3TabActivity.this, "Write Succeed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Sub3TabActivity.this, "Write Fail", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case lockOpe:
                        boolean isLockSucceed = b.getBoolean("isLockSucceed");
                        if (isLockSucceed)
                            Toast.makeText(Sub3TabActivity.this, "Lock Succeed", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(Sub3TabActivity.this, "Lock Fail", Toast.LENGTH_SHORT).show();
                        break;
                    case getLock6bState:
                        boolean isLock = b.getBoolean("LockState");
                        if (isLock)
                            tv_addatate.setText("已锁定");
                        else
                            tv_addatate.setText("未锁定");
                        break;
                    case readOpe_byte:
                        strErr = b.getString("Err");
                        byte[] bData = b.getByteArray("readData");
                        if (bData!=null&&bData.length>0)
                            Toast.makeText(Sub3TabActivity.this, "Read Succeed" + strErr, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(Sub3TabActivity.this, "Read Fail" + strErr, Toast.LENGTH_SHORT).show();
                        break;
                    case writeOpe_byte:
                        boolean isSucceed = b.getBoolean("isWriteSucceed");
                        if (isSucceed) {
                            Toast.makeText(Sub3TabActivity.this, "Write Succeed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Sub3TabActivity.this, "Write Fail", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Sub3TabActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
            Comm.opeT = nullOperate;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - myapp.exittime) > 2000) {
                Toast.makeText(getApplicationContext(), "Touch again to exit program!", Toast.LENGTH_SHORT).show();
                myapp.exittime = System.currentTimeMillis();
            } else {
                finish();
                // System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void Get6BList() {
        spi6btag = new String[Comm.lsTagList6B.size()];
        arradp_6btag = new ArrayAdapter<String>(m_Context, android.R.layout.simple_spinner_item, spi6btag);
        if (Comm.lsTagList6B.size() > 0
                && spinner_6btagr != null && spinner_6btagw != null
                && spinner_bankr != null && spinner_bankw != null) {
            spinner_6btagr.setEnabled(true);
            spinner_6btagw.setEnabled(true);
            spinner_6btagl.setEnabled(true);
            for (int i = 0; i < Comm.lsTagList6B.size(); i++) {
                spi6btag[i] = Comm.lsTagList6B.get(i).strUID;
            }
            arradp_6btag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_6btagr.setAdapter(arradp_6btag);
            spinner_6btagw.setAdapter(arradp_6btag);
            spinner_6btagl.setAdapter(arradp_6btag);
            spinner_bankr.setEnabled(false);
            spinner_bankw.setEnabled(false);
            etCountW.setEnabled(false);
            button_lockop.setEnabled(false);
            spinner_lockbank.setEnabled(false);
            spinner_locktype.setEnabled(false);
        }
    }

    public static void GetTagList() {
        try {
            spichecktag=new String[tagListSize+1];
            arradp_checktag = new ArrayAdapter<String>(m_Context, android.R.layout.simple_spinner_item, spichecktag);
            if (tagListSize>0){
                String tag="";
                spichecktag[0]=Comm.mChouseTag;
                for (int i=0;i<tagListSize;i++) {
                    tag = lsTagList.get(i).strEPC;
                    spichecktag[i+1]=tag;
                }
            }else
                spichecktag[0]=Comm.mChouseTag;
            spinner_check_tag.setAdapter(arradp_checktag);
            arradp_checktag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
