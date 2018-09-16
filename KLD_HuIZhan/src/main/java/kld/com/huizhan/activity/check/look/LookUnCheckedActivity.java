package kld.com.huizhan.activity.check.look;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.number.NumberUtil2;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.adapter.check.UnCheckedLookAdapter;
import kld.com.huizhan.bean.local.AssetsCheck;

/**
 * Created by LDW10000000 on 01/12/2017.
 */

public class LookUnCheckedActivity extends HuiZhanBaseActivity implements UnCheckedLookAdapter.OnContentClickListener {

    private String TAG = "LookPutInActivity";
    List<AssetsCheck> list = new ArrayList<>();
    UnCheckedLookAdapter adapter;
    RecyclerView rv;

    LinearLayoutManager linearLayoutManager;
    int lastVisibleItem;
    int page = 0;
    boolean isLoading = false;//用来控制进入getdata()的次数
    int totlePage = 5;//模拟请求的一共的页数
    final int onePageShowItemNum = 10;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_checked_lookup);
        setToolBar("未盘物资");
    }


    @Override
    public void findViews() {
        super.findViews();
        rv = (RecyclerView) findViewById(R.id.rv);
    }


    @Override
    public void getData() {
        super.getData();
//        List<AssetsCheck> l =  DataFunctionUtil.getAssetsUnCheckList();
//        if(l.size()!=0){
//            list = l;
//        }
        int totalNum = DataSupport.where("checkIsSucceed = ?", "未盘").count(AssetsCheck.class);
        LogUtil.e(TAG,"totalNum = " + totalNum);
        totlePage = NumberUtil2.ceil(totalNum / (onePageShowItemNum * 1.0));
        LogUtil.e(TAG,"totlePage = " + totlePage);
        getData2(page);
        if(totalNum==0){
            rv.setVisibility(View.GONE);
        }
    }

    @Override
    public void showConent() {
        super.showConent();
        adapter = new UnCheckedLookAdapter(list, this);
        linearLayoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);

        //给recyclerView添加滑动监听
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                /*
                到达底部了,如果不加!isLoading的话到达底部如果还一滑动的话就会一直进入这个方法
                就一直去做请求网络的操作,这样的用户体验肯定不好.添加一个判断,每次滑倒底只进行一次网络请求去请求数据
                当请求完成后,在把isLoading赋值为false,下次滑倒底又能进入这个方法了
                 */
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && !isLoading) {
                    //到达底部之后如果footView的状态不是正在加载的状态,就将 他切换成正在加载的状态
                    if (page + 1 < totlePage) {
                        LogUtil.e(TAG, "onScrollStateChanged: " + "进来了");
                        isLoading = true;
                        adapter.changeState(1);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                page++;
                                getData2(page);
                            }
                        });
                    } else {
                        adapter.changeState(2);

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //拿到最后一个出现的item的位置
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapter!=null && totlePage==1){
                    adapter.changeState(2);
                }
            }
        },1000);


    }


    @Override
    public void contentClick(int position) {
    }

    private void getData2(int page) {

        int excursionNum = 10 * page;

        if (page > 0) {
            List<AssetsCheck> l = DataSupport.where("checkIsSucceed = ?", "未盘") .limit(onePageShowItemNum).offset(excursionNum).find(AssetsCheck.class);
            for(int i=0;i<l.size();i++){
                l.get(i).setCheckIsSucceed("未盘");
            }
            if (l.size() != 0) {
                list.addAll(l);
            }
            LogUtil.e(TAG,"page = "  + page + "    "+"list = " + list.toString());
        } else {
            List<AssetsCheck> l = DataSupport.where("checkIsSucceed = ?", "未盘") .limit(onePageShowItemNum).find(AssetsCheck.class);
            if (l.size() != 0) {
                for(int i=0;i<l.size();i++){
                    l.get(i).setCheckIsSucceed("未盘");
                }
                list = l;
            }
            LogUtil.e(TAG,"page = "  + page + "    "+"list = " + list.toString());
        }

        isLoading = false;
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }

    }

}


