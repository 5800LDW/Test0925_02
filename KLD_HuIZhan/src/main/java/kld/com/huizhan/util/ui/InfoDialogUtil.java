package kld.com.huizhan.util.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.ToastUtil;

import java.util.HashMap;
import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.adapter.output.OutPutUploadAdapter;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.upload.output.OutPutUpLoad;
import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.NumberCheckUtil;

/**
 * Created by LDW10000000 on 30/11/2017.
 */

public class InfoDialogUtil {

    private static  String TAG = "InfoDialogUtil";

    public static void showDialogAddNotDelete(Context context,Assets assets, OutPutUpLoad outPutUpLoad, OutPutUpLoadList outPutUpLoadList, List<Assets> assetsList,OutPutUploadAdapter adapter,HashMap<String,Integer> assetsIDPositionList ){
        Dialog dialog = getDialog(context);

        TextView tvGoodsName = (TextView)dialog.findViewById(R.id.tvGoodsName);
        TextView tvCode = (TextView)dialog.findViewById(R.id.tvCode);
        TextView tvStandards = (TextView)dialog.findViewById(R.id.tvStandards);
        TextView tvClassify = (TextView)dialog.findViewById(R.id.tvClassify);
        TextView tvStorage = (TextView)dialog.findViewById(R.id.tvStorage);
        TextView tvUnit = (TextView)dialog.findViewById(R.id.tvUnit);
        TextView tvSotrageMaxNum = (TextView)dialog.findViewById(R.id.tvSotrageMaxNum);
        EditText etOutPutNum = (EditText)dialog.findViewById(R.id.etOutPutNum);
        Button btDelete = (Button)dialog.findViewById(R.id.btDelete);
        Button btCancel = (Button)dialog.findViewById(R.id.btCancel);
        Button btSure = (Button)dialog.findViewById(R.id.btSure);
        btSure.setText("添加");


        String name = assets.getName();tvGoodsName.setText(name);
        String code = assets.getCode();tvCode.setText(code);
        String standards = assets.getModel();tvStandards.setText(standards);
        // TODO: 29/11/2017  类别是这个吗?
        String classify = assets.getCateName();tvClassify.setText(classify);
        String storage = assets.getStorageName();tvStorage.setText(storage);
        String unit = assets.getUnit1();tvUnit.setText(unit);


        btDelete.setVisibility(View.GONE);
        // TODO: 库存信息呢?
        String storageGoodsNum = assets.getNumber1();tvSotrageMaxNum.setText(storageGoodsNum);

        String number = outPutUpLoad.getOutDepotNum();etOutPutNum.setText(number);

//        btDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });

        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNumberIsUnRight(context,etOutPutNum,tvSotrageMaxNum)){
                    return;
                }

                outPutUpLoad.setAssetsID(assets.getAssetID());
                outPutUpLoad.setAssetCode(assets.getCode());
                outPutUpLoad.setOutDepotNum(etOutPutNum.getText().toString().trim());
                outPutUpLoad.setAssetStockID(assets.getStorageID());
                outPutUpLoad.setAssetStockName(assets.getStorageName());

                outPutUpLoadList.getAssets().add(outPutUpLoad);
                assetsList.add(assets);
                dialog.dismiss();
                adapter.notifyDataSetChanged();


                assetsIDPositionList.put(assets.getAssetID(),assetsList.size()-1);

            }
        });

        modifyEditTextByCodeType(context,assets,etOutPutNum);


        btSure.clearFocus();
        btCancel.clearFocus();
        btCancel.setSelected(false);
        btSure.requestFocus();

    }




    public static void showDialogDelete(Context context,List<Assets> assetsList, OutPutUpLoadList outPutUpLoadList, int position, OutPutUploadAdapter adapter,HashMap<String,Integer> assetsIDPositionList ){
        Dialog dialog = getDialog(context);

        TextView tvGoodsName = (TextView)dialog.findViewById(R.id.tvGoodsName);
        TextView tvCode = (TextView)dialog.findViewById(R.id.tvCode);
        TextView tvStandards = (TextView)dialog.findViewById(R.id.tvStandards);
        TextView tvClassify = (TextView)dialog.findViewById(R.id.tvClassify);
        TextView tvStorage = (TextView)dialog.findViewById(R.id.tvStorage);
        TextView tvUnit = (TextView)dialog.findViewById(R.id.tvUnit);
        TextView tvSotrageMaxNum = (TextView)dialog.findViewById(R.id.tvSotrageMaxNum);
        EditText etOutPutNum = (EditText)dialog.findViewById(R.id.etOutPutNum);
        Button btDelete = (Button)dialog.findViewById(R.id.btDelete);
        Button btCancel = (Button)dialog.findViewById(R.id.btCancel);
        Button btSure = (Button)dialog.findViewById(R.id.btSure);

        Assets assets = assetsList.get(position);

        String name = assets.getName();tvGoodsName.setText(name);
        String code = assets.getCode();tvCode.setText(code);
        String standards = assets.getModel();tvStandards.setText(standards);
        // TODO: 29/11/2017  类别是这个吗?
        String classify = assets.getCateCode();tvClassify.setText(classify);
        String storage = assets.getStorageName();tvStorage.setText(storage);
        String unit = assets.getUnit1();tvUnit.setText(unit);



        String storageGoodsNum = assets.getNumber1();tvSotrageMaxNum.setText(storageGoodsNum);

        String number = outPutUpLoadList.getAssets().get(position).getOutDepotNum();etOutPutNum.setText(number);


        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetsList.remove(position);
                outPutUpLoadList.getAssets().remove(position);
                adapter.notifyDataSetChanged();
                dialog.dismiss();

                assetsIDPositionList.remove(assets.getAssetID());
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });

        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (NumberCheckUtil.notLessThan1(etOutPutNum.getText().toString().trim())) {
//                    ToastUtil.showToast(context,("请输入数量!"));
//                    return;
//                }

                if(checkNumberIsUnRight(context,etOutPutNum,tvSotrageMaxNum)){
                    return;
                }


                OutPutUpLoad  outPutUpLoad = outPutUpLoadList.getAssets().get(position);
                outPutUpLoad.setOutDepotNum(etOutPutNum.getText().toString().trim());
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });

        modifyEditTextByCodeType(context,assets,etOutPutNum);



        btCancel.clearFocus();
        btSure.clearFocus();

    }


    private static  Dialog getDialog(Context context){
        Dialog dialog = new Dialog(context,R.style.Dialog_Fullscreen);
        dialog.setContentView(R.layout.dialog_output_modify);

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

    // TODO: 01/12/2017  检查下怎么会没反应??
    private static void modifyEditTextByCodeType(Context context,Assets assets,EditText etOutPutNum){

        LogUtil.e(TAG,"assets.getCodeType() = " + assets.getCodeType());

        if(assets.getCodeType().equals(Const.TYPE_CODE_COMMON)){
            etOutPutNum.setText("1");
            etOutPutNum.setSelection(etOutPutNum.getText().toString().length());
        }
        else if(assets.getCodeType().equals(Const.TYPE_CODE_OWN) ||assets.getCodeType().equals(Const.TYPE_CODE_INDEX) ){
            etOutPutNum.setText("1");
            etOutPutNum.setCursorVisible(false);
            etOutPutNum.setClickable(false);
            etOutPutNum.setFocusable(false);
            etOutPutNum.setPressed(false);
            etOutPutNum.setBackgroundColor(ResHelper.getColor(context,R.color.white));
        }

    }



    private static boolean  checkNumberIsUnRight(Context context,EditText etOutPutNum,TextView tvSotrageMaxNum ){
        if (NumberCheckUtil.notLessThan1(etOutPutNum.getText().toString().trim())) {
            ToastUtil.showToast(context,("请输入数量!"));
            return true;
        }


        int max = 0;
        try {
            max = Integer.parseInt(tvSotrageMaxNum.getText().toString().trim());
        }catch (Exception e){
            ExceptionUtil.handleException(e);
        }
        int input = 0;
        try {
            input = Integer.parseInt(etOutPutNum.getText().toString().trim());
        }catch (Exception e){
            ExceptionUtil.handleException(e);
        }
        if(input>max){
            ToastUtil.showToast(context,("输入数量不能大于库存数量!"));
            return true;
        }
        return false;
    }


//
//
//
//
//
//
//    private void showDialogAddNotDelete(Assets assets, OutPutUpLoad outPutUpLoad, OutPutUpLoadList outPutUpLoadList,List<Assets> assetsList){
//        Dialog dialog = getDialog();
//
//        TextView tvGoodsName = (TextView)dialog.findViewById(R.id.tvGoodsName);
//        TextView tvCode = (TextView)dialog.findViewById(R.id.tvCode);
//        TextView tvStandards = (TextView)dialog.findViewById(R.id.tvStandards);
//        TextView tvClassify = (TextView)dialog.findViewById(R.id.tvClassify);
//        TextView tvStorage = (TextView)dialog.findViewById(R.id.tvStorage);
//        TextView tvUnit = (TextView)dialog.findViewById(R.id.tvUnit);
//        TextView tvSotrageMaxNum = (TextView)dialog.findViewById(R.id.tvSotrageMaxNum);
//        EditText etOutPutNum = (EditText)dialog.findViewById(R.id.etOutPutNum);
//        Button btDelete = (Button)findViewById(R.id.btDelete);
//        Button btCancel = (Button)findViewById(R.id.btCancel);
//        Button btSure = (Button)findViewById(R.id.btSure);
//        btSure.setText("添加");
//
//
//        String name = assets.getName();tvGoodsName.setText(name);
//        String code = assets.getCode();tvCode.setText(code);
//        String standards = assets.getModel();tvStandards.setText(standards);
//        // TODO: 29/11/2017  类别是这个吗?
//        String classify = assets.getCateCode();tvClassify.setText(classify);
//        String storage = assets.getStorageName();tvStorage.setText(storage);
//        String unit = assets.getUnit1();tvUnit.setText(unit);
//
//
//        btDelete.setVisibility(View.GONE);
//        // TODO: 库存信息呢?
//        String storageGoodsNum = assets.getMaxBuy();tvSotrageMaxNum.setText(storageGoodsNum);
//
//        String number = outPutUpLoad.getOutDepotNum();etOutPutNum.setText(number);
//
////        btDelete.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//
//        btCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(dialog!=null){
//                    dialog.dismiss();
//                }
//            }
//        });
//
//        btSure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (NumberCheckUtil.notLessThan1(etOutPutNum.getText().toString().trim())) {
//                    showNoticeView("请输入数量!");
//                    return;
//                }
//                outPutUpLoad.setAssetsID(assets.getAssetID());
//                outPutUpLoad.setAssetCode(assets.getCode());
//                outPutUpLoad.setOutDepotNum(etOutPutNum.getText().toString().trim());
//                outPutUpLoad.setAssetStockID(assets.getStorageID());
//                outPutUpLoad.setAssetStockName(assets.getStorageName());
//
//                outPutUpLoadList.getData().add(outPutUpLoad);
//                assetsList.add(assets);
//
//
//            }
//        });
//    }
//
//
//
//
//
//    private void showDialogDelete(List<Assets> assetsList, OutPutUpLoadList outPutUpLoadList, int position, OutPutUploadAdapter adapter){
//        Dialog dialog = getDialog();
//
//        TextView tvGoodsName = (TextView)dialog.findViewById(R.id.tvGoodsName);
//        TextView tvCode = (TextView)dialog.findViewById(R.id.tvCode);
//        TextView tvStandards = (TextView)dialog.findViewById(R.id.tvStandards);
//        TextView tvClassify = (TextView)dialog.findViewById(R.id.tvClassify);
//        TextView tvStorage = (TextView)dialog.findViewById(R.id.tvStorage);
//        TextView tvUnit = (TextView)dialog.findViewById(R.id.tvUnit);
//        TextView tvSotrageMaxNum = (TextView)dialog.findViewById(R.id.tvSotrageMaxNum);
//        EditText etOutPutNum = (EditText)dialog.findViewById(R.id.etOutPutNum);
//        Button btDelete = (Button)findViewById(R.id.btDelete);
//        Button btCancel = (Button)findViewById(R.id.btCancel);
//        Button btSure = (Button)findViewById(R.id.btSure);
//
//        Assets assets = assetsList.get(position);
//
//        String name = assets.getName();tvGoodsName.setText(name);
//        String code = assets.getCode();tvCode.setText(code);
//        String standards = assets.getModel();tvStandards.setText(standards);
//        // TODO: 29/11/2017  类别是这个吗?
//        String classify = assets.getCateCode();tvClassify.setText(classify);
//        String storage = assets.getStorageName();tvStorage.setText(storage);
//        String unit = assets.getUnit1();tvUnit.setText(unit);
//
//
//
//        String storageGoodsNum = assets.getNumber1();tvSotrageMaxNum.setText(storageGoodsNum);
//
//        String number = outPutUpLoadList.getData().get(position).getOutDepotNum();etOutPutNum.setText(number);
//
//
//        btDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                assetsList.remove(position);
//                outPutUpLoadList.getData().remove(position);
//                adapter.notifyDataSetChanged();
//            }
//        });
//
//        btCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(dialog!=null){
//                    dialog.dismiss();
//                }
//            }
//        });
//
//        btSure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (NumberCheckUtil.notLessThan1(etOutPutNum.getText().toString().trim())) {
//                    showNoticeView("请输入数量!");
//                    return;
//                }
//                OutPutUpLoad  outPutUpLoad = outPutUpLoadList.getData().get(position);
//                outPutUpLoad.setOutDepotNum(etOutPutNum.getText().toString().trim());
//                adapter.notifyDataSetChanged();
//
//            }
//        });
//
//    }
//
//
//
//    private Dialog getDialog(){
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_output_modify);
//        dialog.show();
//        Window window = dialog.getWindow();
//        window.setGravity(Gravity.CENTER);
//        window.getDecorView().setPadding(0, 0, 0, 0);// 消除边距
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(lp);
//        dialog.setCanceledOnTouchOutside(false);
//
//        return dialog;
//
//    }





}
