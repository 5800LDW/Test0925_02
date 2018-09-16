package kld.com.huizhan.activity.giveback;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ldw.xyz.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.adapter.giveback.SelectBillNoAdapter;
import kld.com.huizhan.bean.download.giveback.GiveBackBill;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.db.DataFunctionUtil;

/**
 * Created by LDW10000000 on 27/11/2017.
 */

public class SelectOutPutBillNoActivity extends HuiZhanBaseActivity implements SelectBillNoAdapter.OnContentClickListener {

    RecyclerView rv;
    List<GiveBackBill> giveBackBillList = new ArrayList<>();
    SelectBillNoAdapter adapter;
    private String TAG = "SelectOutPutBillNoActivity";

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_select_storage);

    }

    @Override
    public void findViews() {
        super.findViews();
        rv = (RecyclerView) findViewById(R.id.rv);
        setToolBar("选择出库单号");
    }

    @Override
    public void getData() {
        super.getData();
    }

    @Override
    public void showConent() {
        super.showConent();

        giveBackBillList = DataFunctionUtil.getAllGiveBackBill();

        adapter = new SelectBillNoAdapter(giveBackBillList, this);

        LogUtil.e(TAG,"giveBackBillList = " + giveBackBillList.toString());

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void contentClick(int position) {

        String billNo = giveBackBillList.get(position).getOutDepotNo();

        Intent intent = new Intent();
        intent.putExtra(Const.KEY_VALUE_01, billNo);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }
}



















