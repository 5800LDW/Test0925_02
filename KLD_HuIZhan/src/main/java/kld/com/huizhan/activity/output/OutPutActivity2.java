package kld.com.huizhan.activity.output;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.string.StringHelper2;
import com.ldw.xyz.util.time.TimeUtil;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kld.com.huizhan.HuIZhanApplication;
import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseWithSoundActivity;
import kld.com.huizhan.adapter.output.OutPutUploadAdapter;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.download.user.User;
import kld.com.huizhan.bean.upload.output.OutPutUpLoad;
import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;
import kld.com.huizhan.util.CBCheckUtil;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.EdittextEnterUtil;
import kld.com.huizhan.util.OddNumbersUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.DatePickUtil;
import kld.com.huizhan.util.ui.InfoDialogUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 28/11/2017.
 */

public class OutPutActivity2 extends HuiZhanBaseWithSoundActivity implements OutPutUploadAdapter.OnContentClickListener {
    private String TAG = "OutPutActivity2";
    TextView tvBillNo, tvApplyDate, tvDepartment,tvCutOffDate;
    EditText etCode, etValidity,etUserName;
    CheckBox cbSure, cbCancel;
    View ll_CutOffDate;
    private View[] views;
    private CheckBox[] cbViews;

    User user;

    OutPutUpLoadList outPutUpLoadList = new OutPutUpLoadList();

    List<Assets> assetsList = new ArrayList<>();
    HashMap<String,Integer> assetsIDPositionList = new HashMap<>();

    OutPutUploadAdapter adapter;
    RecyclerView rv;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_output2);
        setToolBar("领用出库");
    }

    @Override
    public void findViews() {
        super.findViews();
        tvBillNo = (TextView) findViewById(R.id.tvBillNo);
        tvApplyDate = (TextView) findViewById(R.id.tvApplyDate);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etCode = (EditText) findViewById(R.id.etCode2);
        etValidity = (EditText) findViewById(R.id.etValidity2);

        cbSure = (CheckBox) findViewById(R.id.cbSure);
        cbCancel = (CheckBox) findViewById(R.id.cbCancel);
        cbCancel.setOnClickListener(this);
        cbSure.setOnClickListener(this);
        rv = (RecyclerView) findViewById(R.id.rv);
        findViewById(R.id.btnRight).setOnClickListener(this);

        tvCutOffDate = (TextView)findViewById(R.id.tvCutOffDate);
        tvCutOffDate.setOnClickListener(this);

        ll_CutOffDate = findViewById(R.id.ll_CutOffDate);
        ll_CutOffDate.setVisibility(View.GONE);

        views = new View[]{etValidity, etCode};
        cbViews = new CheckBox[]{cbSure, cbCancel};
    }

    @Override
    public void getData() {
        super.getData();
//        user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.instance.lName);
        user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.getLName(mContext));

        EdittextEnterUtil.setListener(etCode, new EdittextEnterUtil.OnEnterListener() {
            @Override
            public void event() {
                LogUtil.e(TAG, "Enter");
                String code = etCode.getText().toString().trim();
                searchCode(code);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyBoard(views);
    }

    @Override
    public void showConent() {
        super.showConent();
        setDefaultContent();
        test();
        adapter = new OutPutUploadAdapter(outPutUpLoadList, assetsList, this);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);

    }
    private void setDefaultContent(){

        //出库单号不能重复
        String billNo = OddNumbersUtil.getOutPut();
        while (DataFunctionUtil.findOutPutLoadListByBillNo(billNo)>0){
            billNo = OddNumbersUtil.getOutPut();
        }
        tvBillNo.setText(billNo);


        tvApplyDate.setText(TimeUtil.getCurrentDateAccordingToFormat("yyyy-MM-dd"));
        tvDepartment.setText(user.getSectionName());
//        etUserName.setText(HuIZhanApplication.instance.lName);
        etUserName.setText(HuIZhanApplication.getLName(mContext));
//        outPutUpLoadList.setBillPerson(HuIZhanApplication.instance.lName);
        outPutUpLoadList.setBillPerson(HuIZhanApplication.getLName(mContext));
        outPutUpLoadList.setBillDate(tvApplyDate.getText().toString());
        outPutUpLoadList.setUserDepartment(tvDepartment.getText().toString());

        outPutUpLoadList.setUserPerson(etUserName.getText().toString().trim());
        outPutUpLoadList.setUseful(etValidity.getText().toString());

        outPutUpLoadList.setPredictReturnDate("");


        LogUtil.e(TAG, "outPutUpLoadList = " + outPutUpLoadList.toString());
        LogUtil.e(TAG, "assetsList = " + assetsList.toString());

        etValidity.setText("");

        //todo
        cbIsSure();
        tvCutOffDate.setText("");

    }

    private void test() {
        if (Controller.isRelease == false) {
        }
    }


    private void searchCode(String code) {
        if (!StringHelper2.isEmpty(code)) {
            Assets assets ;
            assets = finAssets(code);

            // TODO: 05/12/2017  做兼容,如果入库是小写, 打印是大写, 代码里都转为小写;
            if(assets==null){
                assets = finAssets(code.toLowerCase());
            }



            if (assets != null) {
                if(assets.getState().equals(Const.STATE_EXIST)){
                    boolean b = assetsIDPositionList.containsKey(assets.getAssetID());
                    //已经存在了;
                    if(b){
                       int position =  assetsIDPositionList.get(assets.getAssetID());
                        contentClick(position);
                        rv.scrollToPosition(position);
                        showNoticeView("该条码已经扫描!");
//                        ToastUtil.showToast(mContext,"该条码已经扫描!");

                    }
                    else{
                        showDialogAddNotDelete(assets, new OutPutUpLoad(), outPutUpLoadList, assetsList,assetsIDPositionList);
//                        etCode.setText("");
                    }
                }
                else {
                    showNoticeView("该物资非在库状态!不能领用.");
                    soundError();
                }

            } else {
                showNoticeView("该条码不存在!");
//                ToastUtil.showToast(mContext, "该条码不存在!");
                soundError();
            }
        }

        etCode.setText("");
        etCode.requestFocus();


        hideKeyBoard(views);
    }

    private Assets finAssets(String code) {
        return DataFunctionUtil.findFirstAssetsByCode(code);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.e(TAG, "keyCode = " + keyCode);
        switch (keyCode) {
            case Const.KEY_SCAN_CODE_2:
            case Const.KEY_SCAN_CODE_3:
            case Const.KEY_SCAN_CODE_4:
//                etCode.setText("");
                etCode.requestFocus();
                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }


    private void cbIsCancel(){
        CBCheckUtil.singleCBisCheck(cbCancel, cbViews);
        outPutUpLoadList.setNeedReturn("不需归还");
        //TODO  不需要归还的话,日期写成啥?
        outPutUpLoadList.setPredictReturnDate("");
        tvCutOffDate.setText("");
        ll_CutOffDate.setVisibility(View.GONE);
    }

    private void cbIsSure(){
        CBCheckUtil.singleCBisCheck(cbSure, cbViews);
        outPutUpLoadList.setNeedReturn("需要归还");
        ll_CutOffDate.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.llMore:
                break;

            case R.id.btSearch:
                searchCode(etCode.getText().toString().trim());
                break;

            case R.id.cbCancel:
                cbIsCancel();
                break;

            case R.id.cbSure:
                cbIsSure();
                //break;

            case R.id.tvCutOffDate:
                //TODO  需要归还的话,需要指定日期
                DatePickUtil.show(mContext, new DatePickUtil.OnDatePickListener() {
                    @Override
                    public void cancel() {}
                    @Override
                    public void sure(int year, int month, int day, int hour, int minter, long time) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String date = dateFormat.format(time);
//                        String date = year + "-" + month+"-"+day;
                        tvCutOffDate.setText(date);
                        outPutUpLoadList.setPredictReturnDate(date);
                    }
                });
                break;


            case R.id.btnRight:

                if (cbCancel.isChecked() == false && cbSure.isChecked() == false) {
                    showNoticeView("请选择是否需要归还!");
                    return;
                }

                if(cbSure.isChecked() == true && StringHelper2.isEmpty(outPutUpLoadList.getPredictReturnDate())){
                    showNoticeView("请选择归还日期!");
                    return;
                }



                if(outPutUpLoadList.getAssets().size()==0){
                    showNoticeView("请扫描条码!");
                    return;
                }

                ProgressUtil.confirmSaveCommonUseDialog(mContext, new ProgressUtil.OnConfirmSaveListener() {
                    @Override
                    public void confirm(Dialog dialog) {



                        //设置使用人
                        if(StringHelper2.isEmpty(etUserName.getText().toString().trim())){
                            showNoticeView("请输入使用人");
//                            ToastUtil.showToast(mContext,"请输入使用人");
                            return;
                        }
                        outPutUpLoadList.setUserPerson(etUserName.getText().toString().trim());
                        //设置使用方向
                        outPutUpLoadList.setUseful(etValidity.getText().toString().trim());
                        // TODO: 02/12/2017 录入单号
                        outPutUpLoadList.setBillNo(tvBillNo.getText().toString());




                        LogUtil.e(TAG,"outPutUpLoadList.toString() = " + outPutUpLoadList.toString());
                        //保存
                        boolean b = outPutUpLoadList.save();
                        LogUtil.e(TAG,"b = " + b);


                        //20180106
                        if(outPutUpLoadList.getAssets()!=null){
                            if(outPutUpLoadList.getAssets().size()!=0){
                                for(int i=0;i<outPutUpLoadList.getAssets().size();i++){
                                    outPutUpLoadList.getAssets().get(i).setBillNo(tvBillNo.getText().toString());
                                }
                            }


                            DataFunctionUtil.saveAll(outPutUpLoadList.getAssets());

                            LogUtil.e(TAG,"outPutUpLoadList.getAssets() !=null");
                        }
                        else{
                            LogUtil.e(TAG,"outPutUpLoadList.getAssets() == null" );
                        }


                        //更改为 领用出库
                        for(int i=0;i<assetsList.size();i++){
                            Assets assets = assetsList.get(i);
                            assets.setState(Const.STATE_APPLYING);
                            assets.updateAll("assetID = ? ", assets.getAssetID());
                        }


                        if(dialog instanceof SweetAlertDialog){
                            ProgressUtil.saveSucceed(((SweetAlertDialog)dialog),mHandler);
                        }


                        //恢复默认,开始下一张单
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // TODO: 05/12/2017 清空
                        startNewBills(outPutUpLoadList,assetsList,assetsIDPositionList);
                            }
                        },100);


                    }
                });

                break;

            default:

                break;
        }
    }

    @Override
    public void contentClick(int position) {
        showDialogDelete(assetsList, outPutUpLoadList, position, adapter,assetsIDPositionList);
    }

    private void showDialogAddNotDelete(Assets assets, OutPutUpLoad outPutUpLoad, OutPutUpLoadList outPutUpLoadList, List<Assets> assetsList,HashMap<String,Integer> assetsIDPositionList ) {
        InfoDialogUtil.showDialogAddNotDelete(mContext, assets, outPutUpLoad, outPutUpLoadList, assetsList, adapter,assetsIDPositionList);
    }

    private void showDialogDelete(List<Assets> assetsList, OutPutUpLoadList outPutUpLoadList, int position, OutPutUploadAdapter adapter,HashMap<String,Integer> assetsIDPositionList ) {
        InfoDialogUtil.showDialogDelete(mContext, assetsList, outPutUpLoadList, position, adapter,assetsIDPositionList);
    }


    // TODO: 30/11/2017 清空
    private void startNewBills(OutPutUpLoadList outPutUpLoadList,List<Assets> assetsList,HashMap<String,Integer> assetsIDPositionList){
        setDefaultContent();
        LogUtil.e(TAG,"清空完成");
        outPutUpLoadList.getAssets().clear();
        assetsList.clear();
        assetsIDPositionList.clear();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        if(assetsList.size()!=0){
            finishWithAnimation();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected void finishWithAnimation() {
        if(assetsList.size()!=0){
            ProgressUtil.reminderDialog(this, ResHelper.getString(mContext,R.string.reminder_are_you_exit), new ProgressUtil.OnReminderDialogListenter() {
                @Override
                public void cancel(Dialog dialog) {
                    if(dialog!=null){
                        dialog.dismiss();
                    }
                }

                @Override
                public void confirm(Dialog dialog) {
                    assetsList.clear();
                    OutPutActivity2.super.finishWithAnimation();
                }
            });
        }
        else{
            super.finishWithAnimation();
        }
    }
}



















