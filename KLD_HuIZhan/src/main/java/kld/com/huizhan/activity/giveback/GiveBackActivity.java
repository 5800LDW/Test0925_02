package kld.com.huizhan.activity.giveback;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.string.StringHelper2;
import com.ldw.xyz.util.time.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kld.com.huizhan.HuIZhanApplication;
import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseWithSoundActivity;
import kld.com.huizhan.adapter.giveback.GiveBckAdapter2;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.download.giveback.GiveBackAssets;
import kld.com.huizhan.bean.download.giveback.GiveBackBill;
import kld.com.huizhan.bean.upload.giveback.UploadGiveBack;
import kld.com.huizhan.bean.upload.giveback.UploadGiveBackAssets;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.EdittextEnterUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 24/11/2017.
 */

public class GiveBackActivity extends HuiZhanBaseWithSoundActivity implements GiveBckAdapter2.OnContentClickListener, Runnable {

    private String TAG = "GiveBackActivity";

    TextView tvDealPerson, tvBillNo, tvRequestDate, tvDepartment, tvUserName, tvUse;
    EditText etCode;
    RecyclerView rv;
    LinearLayout llBillNo;
    private View[] views;

    GiveBackBill gbb = new GiveBackBill();
    List<GiveBackAssets> giveBackAssetsList = new ArrayList<>();
//    HashSet<String> checkList = new HashSet<>();

    HashMap<String, Integer> checkHashMap = new HashMap<>();

    GiveBckAdapter2 adapter;

    Boolean isAllReadySave = true;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_give_back);
        setToolBar("物资归还");
    }

    @Override
    public void findViews() {
        super.findViews();
        rv = (RecyclerView) findViewById(R.id.rv);
        llBillNo = (LinearLayout) findViewById(R.id.llBillNo);
        tvDealPerson = (TextView) findViewById(R.id.tvDealPerson);
        tvBillNo = (TextView) findViewById(R.id.tvBillNo);
        tvRequestDate = (TextView) findViewById(R.id.tvRequestDate);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUse = (TextView) findViewById(R.id.tvUse);

        etCode = (EditText) findViewById(R.id.etCode);

        views = new View[]{etCode};

        llBillNo.setOnClickListener(this);

        findViewById(R.id.btnRight).setOnClickListener(this);

        EdittextEnterUtil.setListener(etCode, new EdittextEnterUtil.OnEnterListener() {
            @Override
            public void event() {
                LogUtil.e(TAG, "Enter");
                if(StringHelper2.isEmpty(tvBillNo.getText().toString().trim())){
                    showNoticeView("请选择出库单号!");
//                    ToastUtil.showToast(mContext,"请选择出库单号!");
                    etCode.setText("");
                }
                else{
                    String code = etCode.getText().toString().trim();
                    searchCode(code);
                }
            }
        });

    }

    @Override
    public void getData() {
        super.getData();
        refreshRecycleView();
    }

    private void refreshRecycleView() {
        adapter = new GiveBckAdapter2(giveBackAssetsList, checkHashMap, this);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showConent() {
        super.showConent();
    }

    private void test() {
        if (Controller.isRelease == false) {
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.llBillNo:
                Intent intent = new Intent(this, SelectOutPutBillNoActivity.class);
                startActivityForResult(intent, Const.KEY_CODE_101);
                overridePendingTransition(com.ldw.xyz.R.anim.push_left_in,
                        com.ldw.xyz.R.anim.push_left_out);

                break;

            case R.id.btnRight:

                if(StringHelper2.isEmpty(tvBillNo.getText().toString().trim())){
                    showNoticeView("请选择出库单号!");
//                    ToastUtil.showToast(mContext,"请选择出库单号!");
                    return;
                }


                SweetAlertDialog dialog = ProgressUtil.show(mContext, "正在进行保存", null, new ProgressUtil.OnFinishListener() {
                    @Override
                    public void onFinish() {

                    }
                });

                int count = DataFunctionUtil.findUploadGiveBackCountByID(gbb.getOutDepotID());
                if(count!=0){
                    int deleteCount = DataFunctionUtil.deleteUploadGiveBackCountByID(gbb.getOutDepotID());
                }

                UploadGiveBack ugb = new UploadGiveBack();
                ugb.setOutDepotID(gbb.getOutDepotID());
//                ugb.setReturnPerson(HuIZhanApplication.instance.lName);
                ugb.setReturnPerson(HuIZhanApplication.getLName(mContext));
                ugb.setReturnDate(TimeUtil.getCurrentDateAccordingToFormat("yyyy-MM-dd"));

                List<UploadGiveBackAssets> Assets = new ArrayList<>();
                for (int i = 0; i < giveBackAssetsList.size(); i++) {
                    UploadGiveBackAssets ugba = new UploadGiveBackAssets();
                    ugba.setAssetsID(giveBackAssetsList.get(i).getAssetsID());
                    ugba.setReturnCheck(giveBackAssetsList.get(i).getReturnCheck());
                    //todo 添加归还数量
                    LogUtil.e("TAG","giveBackAssetsList.get(i).getReturnNum() = " + giveBackAssetsList.get(i).getReturnNum());
                    ugba.setReturnNum(giveBackAssetsList.get(i).getReturnNum());

                    Assets.add(ugba);
                }


                //修改初始表的returnCheck 20180106
                for (int i = 0; i < giveBackAssetsList.size(); i++) {
                    GiveBackAssets gba = giveBackAssetsList.get(i);
                    DataFunctionUtil.updateGiveBackAssets(gba.getAssetsID(),gba.getReturnCheck()+"",gba.getReturnNum()+"");
                }

                try {
                    DataFunctionUtil.saveAll(Assets);
                    ugb.setAssets(Assets);
                    ugb.save();
                    ProgressUtil.saveSucceed(dialog, mHandler);
                    isAllReadySave = true;
                } catch (Exception e) {
                    isAllReadySave = false;
                    ProgressUtil.errorDialog(dialog, "保存失败!");
                    ExceptionUtil.handleException(e);
                }

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.e(TAG, "keyCode = " + keyCode);
        switch (keyCode) {
            case Const.KEY_SCAN_CODE_2:
            case Const.KEY_SCAN_CODE_3:
            case Const.KEY_SCAN_CODE_4:

                etCode.requestFocus();
                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }


    private void searchCode(String code) {

        LogUtil.e(TAG, "code = " + code);
        Integer integer = checkHashMap.get(code);


        //05/12/2017  做兼容,如果入库是小写, 打印是大写, 代码里都转为小写;
        if (integer == null) {
            //小写
            integer = checkHashMap.get(code.toLowerCase());
        }


        if (integer == null) {
            soundError();
            showNoticeView("该条码不存在!");
//            ToastUtil.showToast(mContext, "该条码不存在!");
            etCode.setText("");
        } else {

            GiveBackAssets gba = giveBackAssetsList.get(integer);

            if (StringHelper2.isEmpty(gba.getReturnCheck()) || !StringHelper2.isEmpty(gba.getReturnCheck()) && gba.getReturnCheck().equals("false")) {
                isAllReadySave = false;
            }

            gba.setReturnCheck("true");
            etCode.setText("");
            adapter.notifyDataSetChanged();

            rv.scrollToPosition(integer);
        }

        hideKeyBoard(views);
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideKeyBoard(views);
    }


    //
    @Override
    public void onBackPressed() {
        if (isAllReadySave == false) {
            finishWithAnimation();
        } else {
            super.onBackPressed();
        }
    }

    Dialog quitdDialog;
    @Override
    protected void finishWithAnimation() {

        if (isAllReadySave == false) {
            //todo
            quitdDialog = ProgressUtil.reminderDialog(this, "你有数据没有保存,确定退出?", new ProgressUtil.OnReminderDialogListenter() {
                @Override
                public void cancel(Dialog dialog) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void confirm(Dialog dialog) {
                    isAllReadySave = true;
                    GiveBackActivity.super.finishWithAnimation();
                }
            });
//            showDialog(0);
        } else {
            super.finishWithAnimation();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogUtil.i(TAG, "requestCode = " + requestCode);
//        ToastUtil.showToast(mContext,"requestCode = " + requestCode);

        switch (requestCode) {

            case Const.KEY_CODE_101:
                if (resultCode == Activity.RESULT_OK) {

                    SweetAlertDialog dialog = ProgressUtil.show(mContext, "正在刷新数据...", null, new ProgressUtil.OnFinishListener() {
                        @Override
                        public void onFinish() {

                        }
                    });

                    String billNo = data.getStringExtra(Const.KEY_VALUE_01);
                    List<GiveBackBill> list = DataFunctionUtil.findGiveBackBillByNo(billNo);
                    LogUtil.e(TAG, "List<GiveBackBill> list = " + list.toString());

                    if (list.size() != 0) {
                        gbb = list.get(0);
                        giveBackAssetsList = gbb.getAssets();

                        LogUtil.e(TAG, "giveBackAssetsList = " + giveBackAssetsList.toString());
                        updateShowInfo();

                        checkHashMap.clear();

                        for (int i = 0; i < giveBackAssetsList.size(); i++) {
                            GiveBackAssets giveBackAssets = giveBackAssetsList.get(i);
                            checkHashMap.put(giveBackAssets.getAssetCode(), i);
                        }

                        // 05/12/2017  geiassets添加名字和规格
                        for(int i =0;i<giveBackAssetsList.size();i++){
                            GiveBackAssets gba = giveBackAssetsList.get(i);
                            String assetId = gba.getAssetsID();
                            List<Assets> l = DataFunctionUtil.findAssetsNameAndModelById(assetId);
                            if(l.size()!=0){
                                gba.setName(l.get(0).getName());
                                gba.setModel(l.get(0).getModel());
                            }
                        }

                        refreshRecycleView();

                        isAllReadySave = false;

                        if(dialog!=null){
                            dialog.dismiss();
                        }
                    }
                    else{
                        showNoticeView("没有找到数据!");
//                        ToastUtil.showToast(mContext,"没有找到数据!");
                        if(dialog!=null){
                            dialog.dismiss();
                        }

                    }

                }
                break;
        }
    }


    private void updateShowInfo() {
        tvBillNo.setText(gbb.getOutDepotNo());
        tvDealPerson.setText(gbb.getBillPerson());
        tvRequestDate.setText(gbb.getBillDate());
        tvDepartment.setText(gbb.getUseDepartment());
        tvUserName.setText(gbb.getUserPerson());
        tvUse.setText(gbb.getUseful());

        LogUtil.e(TAG, "giveBackAssetsList = " + giveBackAssetsList.toString());
        LogUtil.e(TAG, "List<GiveBackBill> list = " + gbb.toString());

    }


    @Override
    public void contentClick(int position) {
//        GiveBackAssets gba = giveBackAssetsList.get(position);
//        if (gba.getReturnCheck().equals("false")) {
//            gba.setReturnCheck("true");
//        } else {
//            gba.setReturnCheck("false");
//        }
//        isAllReadySave = false;


        GiveBackAssets gba = giveBackAssetsList.get(position);
        gba.setReturnCheck("true");

        isAllReadySave = false;


        Dialog dialog = getDialog(this);
        ((TextView)dialog.findViewById(R.id.tvCode)).setText(gba.getAssetCode());
        ((TextView)dialog.findViewById(R.id.tvStandards)).setText(gba.getModel());
        ((TextView)dialog.findViewById(R.id.tvGoodsName)).setText(gba.getName());//tvActualNum
        ((TextView)dialog.findViewById(R.id.tvActualNum)).setText(gba.getOutDepotNum());//tvActualNum

        EditText etOutPutNum = (EditText)dialog.findViewById(R.id.etOutPutNum);

        etOutPutNum.setText(gba.getReturnNum());
        etOutPutNum.setSelection(etOutPutNum.getText().length());

        dialog.findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btSure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = etOutPutNum.getText().toString().trim();
                if(StringHelper2.isEmpty(num)){
                    num = "0";
                }
                gba.setReturnNum(num);
                //更新
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemChanged(position);
                    }
                },10);
                dialog.dismiss();

            }
        });

    }




    private static  Dialog getDialog(Context context){
        Dialog dialog = new Dialog(context,R.style.Dialog_Fullscreen);
        dialog.setContentView(R.layout.dialog_return_modify);

//        Window window = dialog.getWindow();
//        window.setGravity(Gravity.CENTER|Gravity.BOTTOM);
//        window.getDecorView().setPadding(0, 0, 0, 0);// 消除边距
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(lp);
//        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
        return dialog;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(quitdDialog!=null){
            quitdDialog.dismiss();
        }
    }

    //    @Override
//    protected Dialog onCreateDialog(int id) {
//        Dialog dialog = null;
//        if(id==0){
//             dialog = ProgressUtil.reminderDialog(this, "你有数据没有保存,确定退出?", new ProgressUtil.OnReminderDialogListenter() {
//                @Override
//                public void cancel(Dialog dialog) {
//                    if (dialog != null) {
//                        dialog.dismiss();
//                    }
//                }
//
//                @Override
//                public void confirm(Dialog dialog) {
//                    isAllReadySave = true;
//                    GiveBackActivity.super.finishWithAnimation();
//                }
//            });
//        }
//
//
//        return  dialog;
////        return super.onCreateDialog(id);
//    }
}
























