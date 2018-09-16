package kld.com.huizhan.activity.putin.look;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ldw.xyz.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.adapter.input.InPutUnUploadLookAdapter;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.upload.putin.Upload;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 01/12/2017.
 */

public class LookPutInActivity extends HuiZhanBaseActivity implements InPutUnUploadLookAdapter.OnContentClickListener {

    private String TAG = "LookPutInActivity";
    List<Upload> uploadList = new ArrayList<>();
    List<Assets> assetsList = new ArrayList<>();
    InPutUnUploadLookAdapter adapter;

    RecyclerView rv;
    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_putin_lookup);
        setToolBar("未上传入库单列表");
    }


    @Override
    public void findViews() {
        super.findViews();
        rv = (RecyclerView) findViewById(R.id.rv);
    }


    @Override
    public void getData() {
        super.getData();
        uploadList = DataFunctionUtil.findAllUpload();

        assetsList.clear();
        for(int i=0;i<uploadList.size();i++){
            Assets assets = DataFunctionUtil.findAssetsByAssetID(uploadList.get(i).getAssetsID());
            if(assets!=null){
                assetsList.add(assets);
            }
            else{
                assetsList.add(new Assets());
            }
        }

        adapter = new InPutUnUploadLookAdapter(uploadList,assetsList,this);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
    }

    @Override
    public void showConent() {
        super.showConent();
    }


    @Override
    public void contentClick(int position) {

        Upload upload = uploadList.get(position);
        Assets assets = assetsList.get(position);
        Intent intent = new Intent(this,LookPutInDetailsActivity.class);
        intent.putExtra(Const.KEY_VALUE_01,upload);
        intent.putExtra(Const.KEY_VALUE_02,assets);
        startActivity(intent);

        LogUtil.e(TAG,"upload = " +upload.toString());
        LogUtil.e(TAG,"assets = " +assets.toString());
    }

    @Override
    public void deleteClick(int position) {


//        rv.setClickable(false);
            ProgressUtil.confirmDialog(mContext, new ProgressUtil.OnConfirmListener() {
                @Override
                public void confirm(Dialog dialog) {

                    if(position<uploadList.size()){
                    DataFunctionUtil.deleteUploadByBillNo(uploadList.get(position).getBillNo()+"");

                    uploadList.remove(position);
                    assetsList.remove(position);

                    adapter.notifyDataSetChanged();
                    showNoticeView("删除成功!");
//                    ToastUtil.showToast(mContext,"删除成功!");
                    }
                }
            },2);

//            rv.setClickable(true);

    }
}


































