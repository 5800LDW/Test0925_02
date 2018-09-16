package kld.com.huizhan.activity.putin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.string.StringHelper2;
import com.ldw.xyz.util.time.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kld.com.huizhan.HuIZhanApplication;
import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseWithSoundActivity;
import kld.com.huizhan.activity.storage.SelectStorageActivity;
import kld.com.huizhan.adapter.repair.MyCostCenterAdapter;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.download.supplier.Supplier;
import kld.com.huizhan.bean.download.user.User;
import kld.com.huizhan.bean.upload.putin.Upload;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.EdittextEnterUtil;
import kld.com.huizhan.util.NumberCheckUtil;
import kld.com.huizhan.util.OddNumbersUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.DatePickUtil;
import kld.com.huizhan.util.ui.ProgressUtil;
import kld.com.huizhan.view.ListViewInPopWindow;

/**
 * Created by LDW10000000 on 24/11/2017.
 */

public class PutInActivity extends HuiZhanBaseWithSoundActivity {

    private String TAG = "PutInActivity";
    TextView tvBillNo, tvStorage, tvName, tvPutInTime, tvGoodsName, tvStandards, tvClassify, tvUnit, tvValidity;
    EditText etPutInNum, etCode ,etSupplier;
    private View[] views;
    User user;
    Assets assets;
    String storkId = "0";

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_putin);
        setToolBar("物资入库");
    }

    @Override
    public void findViews() {
        super.findViews();
        tvBillNo = (TextView) findViewById(R.id.tvBillNo);
        tvStorage = (TextView) findViewById(R.id.tvStorage);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPutInTime = (TextView) findViewById(R.id.tvPutInTime);
        tvGoodsName = (TextView) findViewById(R.id.tvGoodsName);
        tvStandards = (TextView) findViewById(R.id.tvStandards);
        tvClassify = (TextView) findViewById(R.id.tvClassify);
        tvUnit = (TextView) findViewById(R.id.tvUnit);
        etPutInNum = (EditText) findViewById(R.id.etPutInNum);
        tvValidity = (TextView) findViewById(R.id.tvValidity);
        etCode = (EditText) findViewById(R.id.etCode);

        etSupplier = (EditText)findViewById(R.id.etSupplier);

        findViewById(R.id.tvMore).setOnClickListener(this);

        findViewById(R.id.btSearch).setOnClickListener(this);
        findViewById(R.id.btnRight).setOnClickListener(this);
        findViewById(R.id.llMore).setOnClickListener(this);
        tvValidity.setOnClickListener(this);

        views = new View[]{etCode,etPutInNum,etSupplier};

    }

    @Override
    public void getData() {
        super.getData();
//        user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.instance.lName);
        user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.getLName(mContext));

        if (user != null) {
            storkId = user.getStorageID();
        }

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
    public void showConent() {
        super.showConent();

        //tvBillNo.setText(OddNumbersUtil.getIntPut());
        showNewBillNo();

        tvStorage.setText(user.getStorageName());
        tvName.setText(user.getlName());
        tvPutInTime.setText(TimeUtil.getCurrentDateAccordingToFormat("yyyy-MM-dd"));

        etPutInNum.setText("1");
        test();

        setCursorRight(etPutInNum);
    }

    private void showNewBillNo(){
        //单号不能重复;
        String billNo = OddNumbersUtil.getIntPut();
        while (DataFunctionUtil.findUploadByBillNo(billNo)>0){
            billNo = OddNumbersUtil.getIntPut();
        }
        tvBillNo.setText(billNo);
    }


    private void test() {
        if (Controller.isRelease == false) {
//            tvValidity.setText("2017-12-27");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.llMore:
                Intent intent = new Intent(this, SelectStorageActivity.class);
                startActivityForResult(intent, Const.KEY_CODE_101);
                overridePendingTransition(com.ldw.xyz.R.anim.push_left_in,
                        com.ldw.xyz.R.anim.push_left_out);

                break;

            case R.id.btSearch:
                searchCode(etCode.getText().toString().trim());
                break;

            case R.id.btnRight:

                if (assets == null) {
                    showNoticeView("请扫描条码!");
                    return;
                }
                if (NumberCheckUtil.notLessThan1(etPutInNum.getText().toString().trim())) {
                    showNoticeView("请扫输入数量!");
                    return;
                }

                if(StringHelper2.isEmpty(etSupplier.getText().toString().trim())){
                    showNoticeView("请输入或选择供应商!");
                    return;
                }

                // 08/12/2017 非共用条码不能扫
                if(!assets.getCodeType().equals(Const.TYPE_CODE_COMMON)){
                    showNoticeView(assets.getCodeType() + "不能入库!");
//                    ToastUtil.showToast(mContext,assets.getCodeType() + "不能入库!");
                    return;
                }





                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确认保存?")
//                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("是的")
                        .showContentText(false)
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
//                                // reuse previous dialog instance
//                                sDialog.setTitleText("Deleted!")
//                                        .setContentText("Your imaginary file has been deleted!")
//                                        .setConfirmText("OK")
//                                        .setConfirmClickListener(null)
//                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                Upload u = new Upload();
                                u.setBillNo(tvBillNo.getText().toString());
                                u.setBillPerson(tvName.getText().toString());
                                u.setBillDate(tvPutInTime.getText().toString());
                                u.setStockID(storkId);
                                u.setStockName(tvStorage.getText().toString());
                                //注意之前进行非空判断;
                                u.setAssetsID(assets.getAssetID());
                                u.setInstockNum(etPutInNum.getText().toString());
                                u.setInDate(tvValidity.getText().toString());

                                u.setSupplierName(etSupplier.getText().toString().trim());


                                //20180730 upload添加其他的部分
                                u.setAssetName(assets.getName());
                                u.setSpecModel(assets.getModel());
                                u.setCataCode(assets.getCateCode());
                                u.setCateName(assets.getCateName());
                                u.setInstockCode(assets.getCode());






                                boolean b = u.save();

                                LogUtil.e(TAG, "保存 u.toString() = " + u.toString());

                                if (b) {
                                    showNoticeView("保存成功!");
//                                    ToastUtil.showToast(mContext, "保存成功!");
                                    showNewBillNo();
                                    setAssetsNullAndData2Default();
                                } else {
                                    showNoticeView("保存失败!" + "\n" + u.toString());
//                                    ToastUtil.showToast(mContext, "保存失败!" + "\n" + u.toString());
                                }

                                if (sDialog != null) {
                                    sDialog.dismiss();
                                }


                            }
                        })
                        .show();
                break;

            case R.id.tvValidity:
                DatePickUtil.show(mContext, new DatePickUtil.OnDatePickListener() {
                    @Override
                    public void cancel() {}
                    @Override
                    public void sure(int year, int month, int day, int hour, int minter, long time) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String date = dateFormat.format(time);
//                        String date = year + "-" + month+"-"+day;

                        tvValidity.setText(date);
                    }
                });

                break;

            case R.id.tvMore:

                List<String> listForSupplier = new ArrayList<>();
                List<Supplier> supplierList =  DataFunctionUtil.getAllSupplier();

                for(int i=0;i<supplierList.size();i++){
                    String name  = supplierList.get(i).getName();
                    listForSupplier.add(name);
                }

                hideKeyBoard(views);
                showPopupWindow(listForSupplier, etSupplier, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int positionForRepair, long id) {
                        String name = listForSupplier.get(positionForRepair);

                        etSupplier.setText(name);
                        popupWindow.dismiss();

                    }
                });



                break;


            default:
                break;
        }
    }
    private PopupWindow popupWindow;

    private void showPopupWindow(List list, TextView tv, AdapterView.OnItemClickListener listener) {
        MyCostCenterAdapter adapter = new MyCostCenterAdapter(mContext, list);
        popupWindow = ListViewInPopWindow.showpopwindow(this, tv, listener, adapter, popupWindow);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.e(TAG,"keyCode = " + keyCode);
        switch (keyCode) {
            case Const.KEY_SCAN_CODE_2:
            case Const.KEY_SCAN_CODE_3:
            case Const.KEY_SCAN_CODE_4:
                etCode.setText("");
                etCode.requestFocus();
                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }


    private Assets finAssets(String code) {
        return DataFunctionUtil.findFirstAssetsByCode(code);
    }

    private void searchCode(String code) {
        if (!StringHelper2.isEmpty(code)) {
            assets = null;
            assets = finAssets(code);

            //  05/12/2017  做兼容,如果入库是小写, 打印是大写, 代码里都转为小写;
            if(assets==null){
                assets = finAssets(code.toLowerCase());
            }

            if (assets != null) {

                //  08/12/2017 非共用条码不能扫
                if(!assets.getCodeType().equals(Const.TYPE_CODE_COMMON)){
                    showNoticeView(assets.getCodeType() + "不能入库!");
//                    ToastUtil.showToast(mContext,assets.getCodeType() + "不能入库!");
                    soundError();
                    return;
                }


                tvGoodsName.setText(assets.getName());
                tvStandards.setText(assets.getModel());
                tvClassify.setText(assets.getCateName());
                tvUnit.setText(assets.getUnit1());

                etCode.setHint(etCode.getText().toString().trim());
//                etCode.setText("");
            } else {
                setAssetsNullAndData2Default();
                showNoticeView("该条码不存在!");
//                ToastUtil.showToast(mContext, "该条码不存在!");
//              etCode.setText("");
                etCode.requestFocus();
                soundError();
            }
        }
        etCode.setText("");
        hideKeyBoard(views);
    }

    private void setAssetsNullAndData2Default(){
        assets = null;
        tvGoodsName.setText("");
        tvStandards.setText("");
        tvClassify.setText("");
        tvUnit.setText("");

    }



    @Override
    protected void onResume() {
        super.onResume();
        hideKeyBoard(views);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Const.KEY_CODE_101:
                if (resultCode == Activity.RESULT_OK) {
                    String name = data.getStringExtra(Const.KEY_VALUE_01);
                    storkId = data.getStringExtra(Const.KEY_VALUE_02);
                    tvStorage.setText(name);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(assets!=null){
            finishWithAnimation();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected void finishWithAnimation() {
        if(assets!=null){
            ProgressUtil.reminderDialog(this, ResHelper.getString(mContext,R.string.reminder_are_you_exit), new ProgressUtil.OnReminderDialogListenter() {
                @Override
                public void cancel(Dialog dialog) {
                    if(dialog!=null){
                        dialog.dismiss();
                    }
                }
                @Override
                public void confirm(Dialog dialog) {
                    assets = null;
                    PutInActivity.super.finishWithAnimation();
                }
            });
        }
        else{
            super.finishWithAnimation();
        }
    }
}
























