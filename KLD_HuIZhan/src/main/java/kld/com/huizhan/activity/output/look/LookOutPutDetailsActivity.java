package kld.com.huizhan.activity.output.look;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kld.com.huizhan.R;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.view.CHScrollView;

/**
 * Created by LDW10000000 on 01/12/2017.
 */

public class LookOutPutDetailsActivity extends BaseActivity {

    private String TAG = "LookOutPutDetailsActivity";
    TextView tvBillNo, tvApplyDate, tvDepartment, tvValidity, tvUserName;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_output_lookup_details);
        setToolBar("单据详情");
    }

    @Override
    public void findViews() {
        super.findViews();
        tvBillNo = (TextView) findViewById(R.id.tvBillNo);
        tvApplyDate = (TextView) findViewById(R.id.tvApplyDate);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvValidity = (TextView) findViewById(R.id.tvValidity);
        tvUserName = (TextView) findViewById(R.id.tvUserName);


    }

    OutPutUpLoadList list;
    List<kld.com.huizhan.bean.download.assets.Assets> assetsList = new ArrayList<>();

    @Override
    public void getData() {
        super.getData();

        Intent intent = getIntent();
        list = (OutPutUpLoadList) intent.getSerializableExtra(Const.KEY_VALUE_01);

//      Assets = list.getAssets();

        for (int i = 0; i < list.getAssets().size(); i++) {
            String id = list.getAssets().get(i).getAssetsID();
            Assets assets = DataFunctionUtil.findAssetsByAssetID(id);
            if (assets != null) {
                assetsList.add(assets);
            } else {
                assetsList.add(new Assets());
            }
        }


        tvBillNo.setText(list.getBillNo());
        tvApplyDate.setText(list.getBillDate());
        tvDepartment.setText(list.getUserDepartment());
        tvValidity.setText(list.getUseful());
        tvUserName.setText(list.getUserPerson());


    }

    @Override
    public void showConent() {
        super.showConent();
        initViews();
    }


    private ListView mListView;
    // 方便测试，直接写的public
    public HorizontalScrollView mTouchView;
    // 装入所有的HScrollView
    protected List<CHScrollView> mHScrollViews = new ArrayList<CHScrollView>();
    List<Map<String, String>> mDatas = new ArrayList<Map<String, String>>();
    SimpleAdapter mSimpleAdapter;

    private void initViews() {
        Map<String, String> data ;
        CHScrollView headerScroll = (CHScrollView) findViewById(R.id.item_scroll_title);
        // 添加头滑动事件
        mHScrollViews.add(headerScroll);
        mListView = (ListView) findViewById(R.id.scroll_list);
//         for(int i = 0; i < 1; i++) {
//         data = new HashMap<String, String>();
//        // data.put("title", "Title_" + i);
//         data.put("title", ""+i);
//         data.put("data_" + 1, "Date_" + 1 + "_" +i );
//         data.put("data_" + 2, "Date_" + 2 + "_" +i );
//         data.put("data_" + 3, "Date_" + 3 + "_" +i );
//         data.put("data_" + 4, "Date_" + 4 + "_" +i );
//        // data.put("data_" + 5, "Date_" + 5 + "_" +i );
//        // data.put("data_" + 6, "Date_" + 6 + "_" +i );
//         mDatas.add(data);
//         }
        for (int i = 0; i < assetsList.size(); i++) {
            Assets assets = assetsList.get(i);
            data = new HashMap<String, String>();
            data.put("title", "" + assets.getName());
            data.put("data_" + 1, assets.getCode());
            data.put("data_" + 2, assets.getModel());
            data.put("data_" + 3, assets.getUnit1());
            data.put("data_" + 4, assets.getNumber1());
            data.put("data_" + 5, list.getAssets().get(i).getOutDepotNum());
            // data.put("data_" + 5, "Date_" + 5 + "_" +i );
            // data.put("data_" + 6, "Date_" + 6 + "_" +i );
            mDatas.add(data);
        }
        mSimpleAdapter = new ScrollAdapter(this, mDatas, R.layout.item,
                new String[]{"title", "data_1", "data_2", "data_3", "data_4", "data_5", "data_6",},
                new int[]{R.id.item_title, R.id.item_data1, R.id.item_data2, R.id.item_data3, R.id.item_data4
                        , R.id.item_data5,
                        R.id.llBackground
                        // , R.id.item_data6
                });
        mListView.setAdapter(mSimpleAdapter);
    }

    public void addHViews(final CHScrollView hScrollView) {
        if (!mHScrollViews.isEmpty()) {
            int size = mHScrollViews.size();
            CHScrollView scrollView = mHScrollViews.get(size - 1);
            final int scrollX = scrollView.getScrollX();
            //第一次满屏后，向下滑动，有一条数据在开始时未加入
            if (scrollX != 0) {
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        //当listView刷新完成之后，把该条移动到最终位置
                        hScrollView.scrollTo(scrollX, 0);
                    }
                });
            }
        }
        mHScrollViews.add(hScrollView);
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        for (CHScrollView scrollView : mHScrollViews) {
            //防止重复滑动
            if (mTouchView != scrollView)
                scrollView.smoothScrollTo(l, t);
        }
    }


    class ScrollAdapter extends SimpleAdapter {

        private List<? extends Map<String, ?>> datas;
        private int res;
        private String[] from;
        private int[] to;
        private Context context;

        public ScrollAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from,
                             int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            this.datas = data;
            this.res = resource;
            this.from = from;
            this.to = to;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

//            ViewHolder mHolder;


            View v = convertView;
            if (v == null) {
                v = LayoutInflater.from(context).inflate(res, null);
                // 第一次初始化的时候装进来
                addHViews((CHScrollView) v.findViewById(R.id.item_scroll));
                View[] views = new View[to.length];
                for (int i = 0; i < to.length; i++) {
                    View tv = v.findViewById(to[i]);
                    // tv.setOnClickListener(clickListener);
                    views[i] = tv;
                }
                v.setTag(views);


//                mHolder = new ViewHolder();
//                mHolder.bg = convertView.findViewById(R.id.llBackground);


            }
            View[] holders = (View[]) v.getTag();
            int len = holders.length;
            for (int i = 0; i < len-1; i++) {

                String key = from[i];

                Map<String, ?> map = new HashMap<String, String>();
                if (this.datas.size() > position) {
                    map = this.datas.get(position);
                }
                String s = "";
                if (map.size() > 0) {
                    s = map.get(key).toString() + "";
                }

                ((TextView) holders[i]).setText(s);


//                if(position ==0) {
//                    holders[i].setBackgroundColor(ResHelper.getColor(mContext, R.color.white));
//                } else if(position %2==0) {
//                    holders[i].setBackgroundColor(ResHelper.getColor(mContext, R.color.red));
//                } else {
//                    holders[i].setBackgroundColor(ResHelper.getColor(mContext, R.color.white));
//                }

                // }
//                return v;
            }

//            if(position ==0) {
//                holders[holders.length-1].setBackgroundColor(ResHelper.getColor(mContext, R.color.white));
//            } else if(position %2==0) {
//                holders[holders.length-1].setBackgroundColor(ResHelper.getColor(mContext, R.color.red));
//            } else {
//                holders[holders.length-1].setBackgroundColor(ResHelper.getColor(mContext, R.color.white));
//            }
            return v;

    }

        class ViewHolder {
            public View bg;
        }

}
}





























