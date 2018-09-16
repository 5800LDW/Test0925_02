package kld.com.huizhan.activity.putin.look;

import android.content.Intent;
import android.widget.TextView;

import com.ldw.xyz.util.LogUtil;

import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.upload.putin.Upload;
import kld.com.huizhan.util.Const;

/**
 * Created by LDW10000000 on 01/12/2017.
 */

public class LookPutInDetailsActivity extends HuiZhanBaseActivity {

    private String TAG = "LookPutInDetailsActivity";
    Upload upload = new Upload();
    Assets assets = new Assets();

    TextView tvBillNo, tvStorage, tvName, tvPutInTime, tvGoodsName, tvStandards, tvClassify, tvUnit, tvValidity ,tvPutInNum,tvCode,tvSupplier;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_putin_lookup_details);
        setToolBar("单据详情");
    }


    @Override
    public void findViews() {
        super.findViews();
        tvBillNo = (TextView)findViewById(R.id.tvBillNo);
        tvStorage = (TextView)findViewById(R.id.tvStorage);
        tvName = (TextView)findViewById(R.id.tvName);
        tvPutInTime = (TextView)findViewById(R.id.tvPutInTime);
        tvGoodsName = (TextView)findViewById(R.id.tvGoodsName);
        tvStandards = (TextView)findViewById(R.id.tvStandards);
        tvClassify = (TextView)findViewById(R.id.tvClassify);
        tvUnit = (TextView)findViewById(R.id.tvUnit);
        tvValidity = (TextView)findViewById(R.id.tvValidity);
        tvPutInNum = (TextView)findViewById(R.id.tvPutInNum);
        tvCode = (TextView)findViewById(R.id.tvCode);
        tvSupplier = (TextView)findViewById(R.id.tvSupplier);

    }


    @Override
    public void getData() {
        super.getData();
        Intent intent = getIntent();
        upload = (Upload) intent.getSerializableExtra(Const.KEY_VALUE_01);
        assets = (Assets) intent.getSerializableExtra(Const.KEY_VALUE_02);

        LogUtil.e(TAG,"upload = " +upload.toString());
        LogUtil.e(TAG,"assets = " +assets.toString());

    }

    @Override
    public void showConent() {
        super.showConent();
        tvBillNo.setText(upload.getBillNo());
        tvStorage.setText(upload.getStockName());
        tvName.setText(upload.getBillPerson());
        tvPutInTime.setText(upload.getBillDate());
        tvGoodsName.setText(assets.getName());
        tvStandards.setText(assets.getModel());
        tvClassify.setText(assets.getCateName());
        tvUnit.setText(assets.getUnit1());
        tvValidity.setText(upload.getInDate());
        tvPutInNum.setText(upload.getInstockNum());
        tvCode.setText(assets.getCode());
        tvSupplier.setText(upload.getSupplierName());
    }


}


































