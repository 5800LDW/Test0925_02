package kld.com.huizhan.activity.output.look;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ldw.xyz.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.adapter.output.look.OutPutUnUploadLookAdapter;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.upload.output.OutPutUpLoad;
import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 01/12/2017.
 */

public class LookOutPutActivity extends HuiZhanBaseActivity implements OutPutUnUploadLookAdapter.OnContentClickListener {

    private String TAG = "LookOutPutActivity";

    OutPutUnUploadLookAdapter adapter;

    RecyclerView rv;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_putin_lookup);
        setToolBar("未上传出库单列表");
    }


    @Override
    public void findViews() {
        super.findViews();
        rv = (RecyclerView) findViewById(R.id.rv);
    }

    List<String> nameList = new ArrayList<>();
    List<OutPutUpLoadList> outPutLoadList = new ArrayList<>();
    @Override
    public void getData() {
        super.getData();
         outPutLoadList = DataFunctionUtil.findAllOutPutLoadList();
        LogUtil.e(TAG, "outPutLoadList  = " + outPutLoadList.toString());


        for (int i = 0; i < outPutLoadList.size(); i++) {
            OutPutUpLoadList list = outPutLoadList.get(i);
            List<OutPutUpLoad> l = list.getAssets();
            String goodsName = "";
            if (l.size() != 0) {
                OutPutUpLoad outPutUpLoad = l.get(0);
                Assets a = DataFunctionUtil.findAssetsByAssetID(outPutUpLoad.getAssetsID());
                goodsName = a.getName() + "等" + l.size() + "件商品";
            }
            nameList.add(goodsName);
        }


        adapter = new OutPutUnUploadLookAdapter(outPutLoadList,nameList, this);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
    }

    @Override
    public void showConent() {
        super.showConent();
    }


    @Override
    public void contentClick(int position) {

        OutPutUpLoadList outPutUpLoadList = outPutLoadList.get(position);

        Intent intent = new Intent(this,LookOutPutDetailsActivity.class);
        intent.putExtra(Const.KEY_VALUE_01,outPutUpLoadList);
        startActivity(intent);

        LogUtil.e(TAG,"outPutUpLoadList = " +outPutUpLoadList.toString());
    }

    @Override
    public void deleteClick(int position) {


            ProgressUtil.confirmDialog(mContext, new ProgressUtil.OnConfirmListener() {
                @Override
                public void confirm(Dialog dialog) {

                    if(position<outPutLoadList.size()){
                    OutPutUpLoadList opull  = outPutLoadList.get(position);

                    int num = DataFunctionUtil.deleteOutPutLoadListByCode(opull.getBillNo());
                    LogUtil.e(TAG,"删除的数量:"+num);
                    LogUtil.e(TAG,"删除的:OutPutUpLoadList = "+opull.toString());


                    DataFunctionUtil.deleteOutPutLoadByBillNo(opull.getBillNo());

                    outPutLoadList.remove(position);
                    adapter.notifyDataSetChanged();
                    showNoticeView("删除成功!");
//                    ToastUtil.showToast(mContext,"删除成功!");

                    }
                }
            },2);







    }
}


































