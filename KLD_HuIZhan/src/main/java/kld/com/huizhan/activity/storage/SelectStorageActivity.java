package kld.com.huizhan.activity.storage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ldw.xyz.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.adapter.SelectStorageAdapter;
import kld.com.huizhan.bean.download.stock.Stock;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.db.DataFunctionUtil;

/**
 * Created by LDW10000000 on 27/11/2017.
 */

public class SelectStorageActivity extends HuiZhanBaseActivity implements SelectStorageAdapter.OnContentClickListener {

    RecyclerView rv;
    List<Stock> stockList = new ArrayList<>();
    SelectStorageAdapter adapter;
    private String TAG = "SelectStorageActivity";

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_select_storage);

    }

    @Override
    public void findViews() {
        super.findViews();
        rv = (RecyclerView) findViewById(R.id.rv);
        setToolBar("选择仓库");
    }

    @Override
    public void getData() {
        super.getData();
    }

    @Override
    public void showConent() {
        super.showConent();

        stockList = DataFunctionUtil.findAllStock();

        adapter = new SelectStorageAdapter(stockList, this);

        LogUtil.e(TAG,"stockList = " + stockList.toString());

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void contentClick(int position) {

        String name = stockList.get(position).getStorageName();
        String id = stockList.get(position).getStorageID();

        Intent intent = new Intent();
        intent.putExtra(Const.KEY_VALUE_01, name);
        intent.putExtra(Const.KEY_VALUE_02, id);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }
}



















