package kld.com.huizhan.activity.check;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.ActivityUtil;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.ToastUtil;
import com.ldw.xyz.util.device.ScreenUtil;
import com.ldw.xyz.util.number.NumberUtil2;
import com.ldw.xyz.util.string.StringHelper2;
import com.ldw.xyz.util.time.TimeUtil;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kld.com.huizhan.HuIZhanApplication;
import kld.com.huizhan.R;
import kld.com.huizhan.activity.check.look.LookCheckedActivity;
import kld.com.huizhan.activity.check.look.LookUnCheckedActivity;
import kld.com.huizhan.activity.output.look.BaseActivity;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.download.user.User;
import kld.com.huizhan.bean.local.AssetsCheck;
import kld.com.huizhan.bean.upload.check.UploadIsCheckList;
import kld.com.huizhan.bean.upload.check.local.UploadCheck;
import kld.com.huizhan.bean.upload.check.local.UploadCheckList;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.callback.UploadIsCheckListCallBack;
import kld.com.huizhan.biz.callback.listener.OnUploadCallBackListener;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.EdittextEnterUtil;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 08/12/2017.
 */

public class CheckActivity extends BaseActivity {
    private String TAG = "CheckActivity";
    TextView tvDate, tvPerson, tvStorage, btModify, tvCheckedNum, tvUnCheckedNum, tvUpLoad, tvGoodsName, tvModel, tvOriginalNum, tvCheckNumber;
    EditText etCode, etCheckNumber;
    private View[] views;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_check);
        setToolBar("物资盘点", "初始盘点");
    }

    @Override
    public void findViews() {
        super.findViews();
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvPerson = (TextView) findViewById(R.id.tvPerson);
        tvStorage = (TextView) findViewById(R.id.tvStorage);
        etCode = (EditText) findViewById(R.id.etCode);
//        tvContent = (TextView) findViewById(R.id.tvContent);
        etCheckNumber = (EditText) findViewById(R.id.etCheckNumber);

        btModify = (TextView) findViewById(R.id.btModify);


        tvCheckedNum = (TextView) findViewById(R.id.tvCheckedNum);
        tvUnCheckedNum = (TextView) findViewById(R.id.tvUnCheckedNum);
        tvUpLoad = (TextView) findViewById(R.id.tvUpLoad);

        tvCheckedNum.setOnClickListener(this);
        tvUnCheckedNum.setOnClickListener(this);
        tvUpLoad.setOnClickListener(this);
        btModify.setOnClickListener(this);

        views = new View[]{etCode, etCheckNumber};

        tvGoodsName = (TextView) findViewById(R.id.tvGoodsName);
        tvModel = (TextView) findViewById(R.id.tvModel);
        tvOriginalNum = (TextView) findViewById(R.id.tvOriginalNum);

        tvCheckNumber = (TextView) findViewById(R.id.tvCheckNumber);

        TextView btnRight = (TextView) findViewById(R.id.btnRight);
        if (btnRight != null) {
            btnRight.setOnClickListener(this);
        }


    }

    @Override
    public void getData() {
        super.getData();
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
//        User user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.instance.lName);
        User user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.getLName(mContext));

//        tvPerson.setText(HuIZhanApplication.instance.lName);
        tvPerson.setText(HuIZhanApplication.getLName(mContext));


        tvDate.setText(TimeUtil.getCurrentDateAccordingToFormat("yyyy-MM-dd"));
        tvStorage.setText(user.getStorageName());


//        String str = String.format(ResHelper.getString(mContext, R.string.text_check_item), "",
//                "", "", "", " ");

        //更新
        updateItemSurface();


        if (DataFunctionUtil.count(AssetsCheck.class) <= 0) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("新建盘点任务?")
                    .setContentText("当前没有盘点任务, 是否新建盘点任务?")
                    .showContentText(true)
                    .setConfirmText("是的")
                    .setCancelText("取消")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            initCheckTask(sDialog);


                            if (sDialog != null) {
                                sDialog.dismiss();
                                LogUtil.e(TAG, "sDialog.dismiss()");
                            }

                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finishWithAnimation();
                        }
                    })
                    .show();


        }

    }


    private void initCheckTask(SweetAlertDialog sDialog) {
        if (sDialog != null) {
            ProgressUtil.show2(mContext, "正在处理...", sDialog, new ProgressUtil.OnFinishListener() {
                @Override
                public void onFinish() {

                }
            });
        }
        DataFunctionUtil.deleteAll(AssetsCheck.class);

//        SQLiteDatabase db = LitePal.getDatabase();
//        String sql;
//        sql = "INSERT INTO assetscheck SELECT * , '1','未盘','未上传' FROM assets";
//        db.execSQL(sql);
//        db.close();

        //INSERT INTO 新表(字段1,字段2,.......) SELECT 字段1,字段2,...... FROM 旧表

        SQLiteDatabase db = LitePal.getDatabase();
        String sql;
        sql = "INSERT INTO assetscheck(" +
                "assetID,code,name,model,cateCode,cateName,price,unit1,number1,unit2,number2,department,controller,places,provider,validity,maxBuy,minBuy,maintenancePeriod,recentMaintain,maintainResult,storageID,storageName,codeType,state,explain" +
                ") SELECT assetID,code,name,model,cateCode,cateName,price,unit1,number1,unit2,number2,department,controller,places,provider,validity,maxBuy,minBuy,maintenancePeriod,recentMaintain,maintainResult,storageID,storageName,codeType,state,explain FROM assets";
        db.execSQL(sql);
//        db.close();

//        private String checkIsSucceed = "未盘"; //"已盘
//        private String actualNum = "1";
//        private String upLoadIsSucceed = "未上传";
        ContentValues values = new ContentValues();
        values.put("checkIsSucceed", "未盘");
        values.put("actualNum", "1");
        values.put("upLoadIsSucceed", "未上传");
        DataSupport.updateAll(AssetsCheck.class, values);

        updateItemSurface();

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.btnRight:

                // TODO: 18/12/2017  有未上传的禁止初始化;
                if (DataFunctionUtil.count(UploadCheck.class) != 0) {
                    showNoticeView("请先上传已盘数据!");
//                    ToastUtil.showToast(mContext,"请先上传已盘数据!");
                } else {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("确认初始化?")
//                            .showContentText(false)
                            .setContentText("所有物资的状态将设为\"未盘点\"")
                            .setConfirmText("是的")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    initCheckTask(sDialog);

                                    if (sDialog != null) {
                                        sDialog.dismiss();
                                    }

                                    ToastUtil.showToast(mContext, "初始化成功!");


                                }
                            }).show();
                }

                break;


            case R.id.btModify:
                if (NumberUtil2.atLeastOne(etCheckNumber.getText().toString())) {
                    String id = getActivityAssetID();
                    if (!StringHelper2.isEmpty(id)) {
                        modifyUploadCheckData(etCheckNumber.getText().toString().trim(), id);
                    } else {
                        //2018
                        showNoticeView("请扫描条码!");
//                        ToastUtil.showToast(mContext,"请扫描条码!");
                    }

                } else {
//                    ToastUtil.showToast(mContext, "请填入正确数量!");

                    showNoticeView("请填入正确数量!");
                }

                break;
            case R.id.tvCheckedNum:
                ActivityUtil.startActivity(mContext, LookCheckedActivity.class, null);
                break;

            case R.id.tvUnCheckedNum:
                ActivityUtil.startActivity(mContext, LookUnCheckedActivity.class, null);
                break;

            case R.id.tvUpLoad:
                new SweetAlertDialog(CheckActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定上传?")
                        .showContentText(false)
                        .setConfirmText("是的")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                List<UploadCheck> uploadCheck = DataFunctionUtil.getAllUploadCheck();

                                UploadCheckList uploadCheckList = new UploadCheckList();
                                uploadCheckList.setData(uploadCheck);

                                LogUtil.e(TAG, "uploadCheckList 要提交的数据: " + uploadCheckList.toString());
                                String str = GsonUtil.toJson(uploadCheckList);
                                UploadIsCheckList uploadIsCheckList = GsonUtil.toUpLoadIsCheckList(str);
                                LogUtil.e(TAG, "uploadIsCheckList 要提交的数据: " + uploadIsCheckList.toString());

                                upLoadIsCheckList(uploadIsCheckList);


                                if (sDialog != null) {
                                    sDialog.dismiss();
                                }

                            }
                        }).show();

                break;

        }
    }

    private void upLoadIsCheckList(UploadIsCheckList uploadIsCheckList) {
        Service.postUploadList(this, Const.URL_UPLOAD_ISCHECK, new UploadIsCheckListCallBack(mContext, "正在上传数据", mHandler, this, new OnUploadCallBackListener() {
            @Override
            public void succeed() {
                //把"未提交"修改为"已提交"
                DataFunctionUtil.updateAssetCheckUnload2Uploaded();

                //清空数据
                DataFunctionUtil.deleteAll(UploadCheck.class);
            }

            @Override
            public void failedCallBack() {

            }
        }), uploadIsCheckList);
    }


    private String mAssetID = "";

    private String getActivityAssetID() {
        return mAssetID;
    }

    private void setActivityAssetID(String id) {
        mAssetID = id;
    }


    private void searchCode(String code) {
        if (!StringHelper2.isEmpty(code)) {
            Assets assets;
            assets = finAssets(code);

            // TODO: 05/12/2017  做兼容,如果入库是小写, 打印是大写, 代码里都转为小写;
            if (assets == null) {
                assets = finAssets(code.toLowerCase());
            }

            if (assets != null) {

                setActivityAssetID(assets.getAssetID());

                String name = assets.getName();
                String asstCode = assets.getCode();
                String model = assets.getModel();
                String physicalNum = assets.getNumber1();
                String actualNum = physicalNum;


                //更改数量
                List<UploadCheck> uploadCheckList = DataFunctionUtil.findUploadCheckByAssetID(assets.getAssetID());
                UploadCheck uploadCheck = null;
                if (uploadCheckList.size() != 0) {
                    uploadCheck = uploadCheckList.get(0);
                }
                if (uploadCheck != null) {
                    actualNum = uploadCheck.getNum();
                }

                //显示界面
//                String str = String.format(ResHelper.getString(mContext, R.string.text_check_item), name,
//                        asstCode, model, physicalNum, actualNum);


                tvGoodsName.setText(name);
                tvModel.setText(model);
                tvOriginalNum.setText(physicalNum);


                if (assets.getCodeType().equals(Const.TYPE_CODE_COMMON)) {
                    etCheckNumber.setText("1");
//                    etCheckNumber.setCursorVisible(true);
//                    etCheckNumber.setFocusableInTouchMode(true);
//                    etCheckNumber.setFocusable(true);
//                    etCheckNumber.requestFocus();
//                    etCheckNumber.setBackground(ResHelper.getDrawable(mContext, R.drawable.shape_round_grey));

                    btModify.setVisibility(View.VISIBLE);
                    etCheckNumber.setVisibility(View.VISIBLE);
                    etCheckNumber.setSelection(etCheckNumber.getText().toString().length());
                    tvCheckNumber.setVisibility(View.GONE);

                } else if (assets.getCodeType().equals(Const.TYPE_CODE_OWN) || assets.getCodeType().equals(Const.TYPE_CODE_INDEX)) {
                    etCheckNumber.setText("1");
//                    etCheckNumber.setCursorVisible(false);
//                    etCheckNumber.setFocusable(false);
//                    etCheckNumber.setFocusableInTouchMode(false);
//                    etCheckNumber.setBackgroundColor(ResHelper.getColor(mContext, R.color.white));
                    btModify.setVisibility(View.GONE);
                    etCheckNumber.setVisibility(View.GONE);
                    tvCheckNumber.setVisibility(View.VISIBLE);
                    tvCheckNumber.setText("1");

                }


                //数据库没有,就插入数据
                if (uploadCheckList.size() == 0) {
                    insertUploadCheckDataToDB(actualNum, assets.getAssetID());
                }
                //共用条码才能修改数量
                else if (assets.getCodeType().equals(Const.TYPE_CODE_COMMON)) {
                    showNoticeView("已经盘点过了, 可以修改实盘数量");
//                    ToastUtil.showToast(mContext,"已经盘点过了, 可以修改实盘数量");
                } else {
                    showNoticeView("已经盘点过了");
//                    ToastUtil.showToast(mContext,"已经盘点过了");
                }

                updateItemSurface();
                updateModifyNum(actualNum);
                etCode.setHint(etCode.getText().toString().trim());

            } else {
                showNoticeView("该条码不存在!");
//                ToastUtil.showToast(mContext, "该条码不存在!");
                etCode.requestFocus();
                soundError();
            }
        }
        etCode.setText("");
        hideKeyBoard(views);
    }

    private void insertUploadCheckDataToDB(String num, String AssetID) {
        UploadCheck uc = new UploadCheck();
        uc.setAssetsID(AssetID);
        uc.setNum(num);
        uc.setPerson(tvPerson.getText().toString());
        uc.setDate(tvDate.getText().toString());
        uc.setResult("已盘");
        boolean b = uc.save();
        if (b) {
            showNoticeView("盘点成功!");
//            ToastUtil.showToast(mContext, "盘点成功!");

            ContentValues values = new ContentValues();
            values.put("checkIsSucceed", "已盘");
            values.put("actualNum", num);
            DataSupport.updateAll(AssetsCheck.class, values, "assetID = ?", AssetID);


        } else {
            showNoticeView("盘点失败!");
//            ToastUtil.showToast(mContext, "盘点失败!");
        }

    }

    private void modifyUploadCheckData(String num, String AssetID) {
        int updateNum = DataFunctionUtil.updateUploadCheckByAssetID(num, AssetID);

        // TODO: 11/12/2017  修改 AssetsCheck表的数量
        ContentValues values = new ContentValues();
        values.put("actualNum", num);
        DataSupport.updateAll(AssetsCheck.class, values, "assetID = ?", AssetID);


        if (updateNum != 0) {
            showNoticeView("修改成功!");
//            ToastUtil.showToast(mContext, "修改成功!");
            hideKeyBoard(views);
        } else {
            showNoticeView("修改失败!");
//            ToastUtil.showToast(mContext, "修改失败!");
        }
    }


    private void updateModifyNum(String num) {
        etCheckNumber.setText(num);
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

                etCode.setText("");
                etCode.requestFocus();
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void updateItemSurface() {
        //更新内容
//        setText(mContext, tvContent, str);
        //更新已盘/未盘
        int totalNum = DataFunctionUtil.count(AssetsCheck.class);


        int checkedNum = DataFunctionUtil.countAssetCheckIsCheck();


        int uncheckedNum = totalNum - checkedNum;
        if (Controller.isRelease == false) {
//            uncheckedNum+=10000;
        }


        String checkedStr = String.format(ResHelper.getString(mContext, R.string.text_checked), checkedNum);
        String uncheckedStr = String.format(ResHelper.getString(mContext, R.string.text_unchecked), uncheckedNum);
        setCheckedText(mContext, tvCheckedNum, checkedStr);
        setUnCheckedText(mContext, tvUnCheckedNum, uncheckedStr);
    }

    public static void setCheckedText(Context mContext, TextView tv, String content) {
        String text = String.format(content);
        int checkBefore = text.indexOf("已盘:");
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext, 18)), 0, checkBefore + 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        tv.setText(style);
    }

    public static void setUnCheckedText(Context mContext, TextView tv, String content) {
        String text = String.format(content);
        int checkBefore = text.indexOf("未盘:");
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext, 18)), 0, checkBefore + 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
//        style.setSpan(new ForegroundColorSpan(Color.parseColor("#c10000")), checkBefore + 3, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
        tv.setText(style);
    }

}
