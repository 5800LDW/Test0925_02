//package kld.com.huizhan.activity.output;
//
//import android.app.Dialog;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.ldw.xyz.control.Controller;
//import com.ldw.xyz.util.LogUtil;
//import com.ldw.xyz.util.ToastUtil;
//import com.ldw.xyz.util.string.StringHelper2;
//import com.ldw.xyz.util.time.TimeUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import kld.com.huizhan.HuIZhanApplication;
//import kld.com.huizhan.R;
//import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
//import kld.com.huizhan.adapter.output.OutPutUploadAdapter;
//import kld.com.huizhan.adapter.SelectStorageAdapter;
//import kld.com.huizhan.bean.download.assets.Assets;
//import kld.com.huizhan.bean.download.user.User;
//import kld.com.huizhan.bean.upload.output.OutPutUpLoad;
//import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;
//import kld.com.huizhan.util.CBCheckUtil;
//import kld.com.huizhan.util.db.DataFunctionUtil;
//import kld.com.huizhan.util.EdittextEnterUtil;
//import kld.com.huizhan.util.NumberCheckUtil;
//import kld.com.huizhan.util.OddNumbersUtil;
//import kld.com.huizhan.util.ui.ProgressUtil;
//
///**
// * Created by LDW10000000 on 28/11/2017.
// */
//
//public class OutPutActivity extends HuiZhanBaseActivity implements OutPutUploadAdapter.OnContentClickListener {
//
//    TextView tvBillNo,tvApplyDate ,tvDepartment,tvUserName , tvGoodsName , tvStandards,tvClassify,tvStorage,tvUnit;
//    EditText etCode,etPutInNum,etValidity;
//    CheckBox cbSure , cbCancel;
//    private View[] views = {etCode,etPutInNum,etValidity};
//    private CheckBox[] cbViews = {cbSure,cbCancel};
//
//    User user;
//    Assets assets;
//    OutPutUpLoadList outPutUpLoadList  = new OutPutUpLoadList();
//    private String TAG = "OutPutActivity";
//
//    private List<OutPutUpLoad> outPutUpLoadlist = new ArrayList<>();
//    List<Assets> assetsList = new ArrayList<>();
//    OutPutUploadAdapter adapter;
//    RecyclerView rv;
//
//    @Override
//    public void setContentView() {
//        super.setContentView();
//        setContentView(R.layout.activity_output);
//        setToolBar("物资出库");
//    }
//
//    @Override
//    public void findViews() {
//        super.findViews();
//        tvBillNo = (TextView)findViewById(R.id.tvBillNo);
//        tvApplyDate = (TextView)findViewById(R.id.tvApplyDate);
//        tvDepartment = (TextView)findViewById(R.id.tvDepartment);
//        tvUserName = (TextView)findViewById(R.id.tvUserName);
//
//        etCode = (EditText) findViewById(R.id.etCode);
//        tvGoodsName = (TextView)findViewById(R.id.tvGoodsName);
//        tvStandards = (TextView)findViewById(R.id.tvStandards);
//        tvClassify = (TextView)findViewById(R.id.tvClassify);
//        tvStorage = (TextView)findViewById(R.id.tvStorage);
//        tvUnit = (TextView)findViewById(R.id.tvUnit);
//
//
//        etPutInNum = (EditText)findViewById(R.id.etPutInNum);
//        etValidity = (EditText)findViewById(R.id.etValidity);
//        cbSure = (CheckBox)findViewById(R.id.cbSure);
//        cbCancel = (CheckBox)findViewById(R.id.cbCancel);
//
//        cbCancel.setOnClickListener(this);
//        cbSure.setOnClickListener(this);
//
//        rv = (RecyclerView) findViewById(R.id.rv);
//
//    }
//
//    @Override
//    public void getData() {
//        super.getData();
//        user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.instance.lName);
//
//        EdittextEnterUtil.setListener(etCode, new EdittextEnterUtil.OnEnterListener() {
//            @Override
//            public void event() {
//                LogUtil.e(TAG, "Enter");
//                String code = etCode.getText().toString().trim();
//                searchCode(code);
//            }
//        });
//
//    }
//
//    @Override
//    public void showConent() {
//        super.showConent();
//        tvBillNo.setText(OddNumbersUtil.getOutPut());
//        tvApplyDate.setText(TimeUtil.getCurrentDateAccordingToFormat("yyyy-MM-dd"));
//        tvDepartment.setText(user.getSectionName());
//        tvUserName.setText(HuIZhanApplication.instance.lName);
//        etPutInNum.setText("1");
//        test();
//
//        setCursorRight(etPutInNum);
//
//
//        adapter = new OutPutUploadAdapter(outPutUpLoadlist,assetsList, this);
//        LogUtil.e(TAG,"outPutUpLoadlist = " + outPutUpLoadlist.toString());
//        LogUtil.e(TAG,"assetsList = " + assetsList.toString());
//
//        rv.setLayoutManager(new LinearLayoutManager(mContext));
//        rv.setAdapter(adapter);
//
//    }
//
//    private void test() {
//        if (Controller.isRelease == false) {
//        }
//    }
//
//
//    private void searchCode(String code) {
//        if (!StringHelper2.isEmpty(code)) {
//            assets = null;
//            assets = finAssets(code);
//            if (assets != null) {
//                tvGoodsName.setText(assets.getName());
//                tvStandards.setText(assets.getModel());
//                // TODO: 29/11/2017  类别是什么呢?
//                tvClassify.setText(assets.getCateCode());
//                //todo 所在仓库是这个吗?
//                tvStorage.setText(assets.getStorageName());
//                tvUnit.setText(assets.getUnit1());
//                etCode.setHint(etCode.getText().toString().trim());
//                etCode.setText("");
//
//            } else {
//                tvGoodsName.setText("");
//                tvStandards.setText("");
//                tvClassify.setText("");
//                tvClassify.setText("");
//                tvStorage.setText("");
//                tvUnit.setText("");
//                ToastUtil.showToast(mContext, "该条码不存在!");
//                etCode.setText("");
//                etCode.requestFocus();
//            }
//        }
//        hideKeyBoard(views);
//    }
//
//    private Assets finAssets(String code) {
//        return DataFunctionUtil.findFirstAssetsByCode(code);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        LogUtil.e(TAG,"keyCode = " + keyCode);
//        switch (keyCode) {
//            case 261:
//            case 0:
//                etCode.requestFocus();
//                break;
//
//            default:
//                break;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        switch (v.getId()){
//
//            case R.id.llMore:
//
//                break;
//
//            case R.id.btSearch:
//                searchCode(etCode.getText().toString().trim());
//                break;
//
//
//            case R.id.cbCancel:
//                CBCheckUtil.singleCBisCheck(cbCancel,cbViews);
//                break;
//
//            case R.id.cbSure:
//                CBCheckUtil.singleCBisCheck(cbSure,cbViews);
//                break;
//
//
//            case R.id.btnRight:
//
//                if (assets == null) {
//                    showNoticeView("请扫描条码!");
//                    return;
//                }
//                if (NumberCheckUtil.notLessThan1(etPutInNum.getText().toString().trim())) {
//                    showNoticeView("请扫输入数量!");
//                    return;
//                }
//
//                if(cbCancel.isChecked()==false && cbSure.isChecked()==false){
//                    showNoticeView("请选择是否需要归还!");
//                    return;
//                }
//
//
//                ProgressUtil.confirmSaveCommonUseDialog(mContext, new ProgressUtil.OnConfirmSaveListener() {
//                    @Override
//                    public void confirm(Dialog dialog) {
//                                 OutPutUpLoad u = new OutPutUpLoad();
//                                u.setBillNo(tvBillNo.getText().toString());
//                                u.setBillPerson(tvName.getText().toString());
//                                u.setBillDate(tvPutInTime.getText().toString());
//                                u.setStockID(storkId);
//                                u.setStockName(tvStorage.getText().toString());
//                                //注意之前进行非空判断;
//                                u.setAssetsID(assets.getAssetID());
//                                u.setInStockNum(etPutInNum.getText().toString());
//                                u.setInDate(tvValidity.getText().toString());
//
//
//
//                                boolean b = u.save();
//
//                                LogUtil.e(TAG, "保存 u.toString() = " + u.toString());
//
//                                if (b) {
//                                    ToastUtil.showToast(mContext, "保存成功!");
//                                } else {
//                                    ToastUtil.showToast(mContext, "保存失败!" + "\n" + u.toString());
//                                }
//
//                                if (sDialog != null) {
//                                    sDialog.dismiss();
//                                }
//
//
//                    }
//                });
//
//
//
////                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
////                        .setTitleText("确认保存?")
//////                        .setContentText("Won't be able to recover this file!")
////                        .setConfirmText("是的")
////                        .showContentText(false)
////                        .setCancelText("取消")
////                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
////                            @Override
////                            public void onClick(SweetAlertDialog sDialog) {
////
////                                Upload u = new Upload();
////                                u.setBillNo(tvBillNo.getText().toString());
////                                u.setBillPerson(tvName.getText().toString());
////                                u.setBillDate(tvPutInTime.getText().toString());
////                                u.setStockID(storkId);
////                                u.setStockName(tvStorage.getText().toString());
////                                //注意之前进行非空判断;
////                                u.setAssetsID(assets.getAssetID());
////                                u.setInStockNum(etPutInNum.getText().toString());
////                                u.setInDate(tvValidity.getText().toString());
////
////
////
////                                boolean b = u.save();
////
////                                LogUtil.e(TAG, "保存 u.toString() = " + u.toString());
////
////                                if (b) {
////                                    ToastUtil.showToast(mContext, "保存成功!");
////                                } else {
////                                    ToastUtil.showToast(mContext, "保存失败!" + "\n" + u.toString());
////                                }
////
////                                if (sDialog != null) {
////                                    sDialog.dismiss();
////                                }
////
////
////                            }
////                        })
////                        .show();
//                break;
//
//                default:
//
//                    break;
//
//
//        }
//    }
//
//
//    @Override
//    public void contentClick(int position) {
//
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
