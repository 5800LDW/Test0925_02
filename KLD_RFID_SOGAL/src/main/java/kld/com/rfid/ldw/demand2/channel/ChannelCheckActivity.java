package kld.com.rfid.ldw.demand2.channel;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.PreferenceUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.ToastUtil;
import com.ldw.xyz.util.string.StringHelper2;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.R;
import kld.com.rfid.ldw.adapter.MyCostCenterAdapter;
import kld.com.rfid.ldw.adapter.SelectChannelAdapter;
import kld.com.rfid.ldw.bean.channel.ChannelInfo;
import kld.com.rfid.ldw.bean.channel.ChannelInfoListBean;
import kld.com.rfid.ldw.bean.orginfo.DownloadOrganization;
import kld.com.rfid.ldw.bean.orginfo.ORGANIZATION;
import kld.com.rfid.ldw.util.GsonUtil;
import kld.com.rfid.ldw.views.ListViewInPopWindow;
import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by liudongwen on 2018/9/5.
 */

public class ChannelCheckActivity  extends AppCompatActivity implements SelectChannelAdapter.OnContentClickListener {



    static Handler mHandler= new Handler();

    Context mContext;

    View llOrganization;

    private String TAG = "ChannelCheckActivity";

    private PromptDialog dialogOrganization;

    private TextView tvOrganization;

//    List<String> organizationList = new ArrayList<>();

    List<ORGANIZATION> organizationList = new ArrayList<>();

    RecyclerView rv;


    String ORGANIZATION_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_check);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        setToolBar("选择通道");

        tvOrganization = (TextView)findViewById(R.id.tvOrganization);

        rv = (RecyclerView)findViewById(R.id.rv);

        llOrganization = findViewById(R.id.llOrganization);
        llOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(organizationList.size()==0){
                    getOrganization();
                    return;
                }

                List<String> list = new ArrayList<>();
                for(int i=0;i<organizationList.size();i++){
                    list.add(organizationList.get(i).getORGANIZATION_NAME());
                }

                showPopupWindow(list, llOrganization, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int positionForRepair, long id) {


                        if(positionForRepair<organizationList.size()){
                            String name = organizationList.get(positionForRepair).getORGANIZATION_NAME();
                            String ID = organizationList.get(positionForRepair).getORGANIZATION_ID();
                            tvOrganization.setText(name+"");

                            ORGANIZATION_ID = ID;
                            if( popupWindow!=null){
                                popupWindow.dismiss();
                            }
//                        //todo 联网获取数据;
//                        mHanlder.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                getChannelInfo();
//                            }
//                        },100);

                            //todo 联网获取数据;
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getChannelInfo();
                                }
                            },50);

                        }

                    }
                });

            }
        });




        mContext = this;

    }


    @Override
    protected void onResume() {
        super.onResume();

        if(organizationList.size()==0){
            llOrganization.performClick();
            return;
        }


    }

    public void setToolBar(String str ){
        //        setToolBar(currentChooseStoreName + "--商品品列表", R.id.toolbar);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView tv= (TextView)toolbar.findViewById(R.id.tv_Text);
        tv.setText(str);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                finishWithAnimation();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void finishWithAnimation(){
        //// TODO: 13/06/2017  设置退出动画
        finish();
//        overridePendingTransition(com.ldw.xyz.R.anim.push_left_in,
//                com.ldw.xyz.R.anim.push_left_out);
//        overridePendingTransition(com.ldw.xyz.R.anim.push_left_out,
//                com.ldw.xyz.R.anim.push_left_in);

    }

    private PopupWindow popupWindow;
    private void showPopupWindow(List list, View v, AdapterView.OnItemClickListener listener) {
        MyCostCenterAdapter adapter = new MyCostCenterAdapter(mContext, list);
        popupWindow = ListViewInPopWindow.showpopwindow(this, v, listener, adapter, popupWindow);
    }




    public void getOrganization() {
        String url = Const.getURL_MATERIALS();


        LogUtil.e(TAG,"***URL*** ------------------");
        LogUtil.e(TAG,"url =" + url);

        OkHttpUtils
                .get()
                .url(url)//
                .build()
                .connTimeOut(15)
                .readTimeOut(15)
                .writeTimeOut(15).execute(new OrganizationCallback());

    }




    public class OrganizationCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            dialogOrganization.viewAnimDuration = 10;
            dialogOrganization = new PromptDialog(ChannelCheckActivity.this);
            dialogOrganization.getDefaultBuilder().touchAble(false).round(3).loadingDuration(300);
            dialogOrganization.showLoading("正在获取数据...");
        }

        @Override
        public void onAfter(int id) {
            //todo 取消显示正在上传
//            isCanRunRead =true;
//            tvTips.setVisibility(View.GONE);
            if(dialogOrganization!=null){
                dialogOrganization.dismiss();
            }
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            if(dialogOrganization!=null){
                dialogOrganization.dismiss();
            }

            LogUtil.e(TAG,"onError " + e.toString());

            if (mContext != null ) {
                final String info;
                if (e instanceof UnknownHostException) {
                    info = "网络未连接!";
                } else if (e instanceof SocketTimeoutException) {
                    info = "连接网络超时!";
                } else if(e instanceof ConnectException){
                    info = "连接地址: "+ PreferenceUtil.get(mContext,Const.KEY_URL_SERVER, ResHelper.getString(mContext,R.string.url_server_address))+" 失败";
                }
                else {
                    info = e.toString();
                }

                Toast.makeText(mContext,
                        ""+info,
                        Toast.LENGTH_LONG).show();

            }

            Log.e(TAG,"Exception = "+e.toString());

        }

        @Override
        public void onResponse(String response, int id) {
            if(dialogOrganization!=null){
                dialogOrganization.dismiss();
            }
            LogUtil.e(TAG, "onResponse：complete-------->");
            LogUtil.e(TAG, "response ------------> " + response);
            LogUtil.e(TAG, "id ------------>" + id);

            DownloadOrganization obj = GsonUtil.toDownloadOrganization(response);
            if(obj!=null){

                if(obj.getSuccess().toUpperCase().equals("TRUE")){
//                    ToastUtil.showToast(mContext,obj.getMsg());
                    organizationList = obj.getData();
                    llOrganization.performClick();
                }
                else {
                    ToastUtil.showToast(mContext,obj.getMsg());
                }
            }
            else{
                ToastUtil.showToast(mContext,"response = "+ response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
        }
    }



    private List<String> channelList = new ArrayList<>();
    SelectChannelAdapter adapter;
    private void setAdapter(){

        if(channelList!=null&&channelList.size()>0){
            adapter = new SelectChannelAdapter(channelList, this);
            rv.setLayoutManager(new LinearLayoutManager(mContext));
            adapter = new SelectChannelAdapter(channelList, this);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


    }

    @Override
    public void contentClick(int position) {

        String name = channelList.get(position);
        String code = ChannelInfoList.get(position).getSTAGE_CODE();


        PreferenceUtil.set(mContext,Const.KEY_STAGE_NMAE,name+"");
        PreferenceUtil.set(mContext,Const.KEY_STAGE_CODE,code+"");

        PreferenceUtil.set(mContext,Const.KEY_ORGANIZATION,ORGANIZATION_ID+"");

        LogUtil.e("ORGANIZATION_ID","ORGANIZATION_ID =" + ORGANIZATION_ID);

//        Intent intent = new Intent();
//        intent.putExtra(Const.KEY_VALUE_01, name);
//        intent.putExtra(Const.KEY_VALUE_02, id);
//        setResult(Activity.RESULT_OK, intent);
        finish();
    }





    private void getChannelInfo(){

        if(StringHelper2.isEmpty(ORGANIZATION_ID)){
            ToastUtil.showToast(mContext,"错误:组织id为空");
            if(dialogOrganization!=null){
                dialogOrganization.dismiss();
            }
            return;
        }
        String url = Const.getURL_ORG_ID(ORGANIZATION_ID);

//todo
        LogUtil.e(TAG,"***URL*** ------------------");
        LogUtil.e(TAG,"url =" + url);
//
        OkHttpUtils
                .get()
                .url(url)//
                .build()
                .connTimeOut(15)
                .readTimeOut(15)
                .writeTimeOut(15).execute(new ChannelCallback());
    }



    PromptDialog  dialogOrganization2;
    List<ChannelInfoListBean> ChannelInfoList = new ArrayList<>();
    public class ChannelCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            dialogOrganization2.viewAnimDuration = 10;
            dialogOrganization2 = new PromptDialog(ChannelCheckActivity.this);
            dialogOrganization2.getDefaultBuilder().touchAble(false).round(3).loadingDuration(500);
            dialogOrganization2.showLoading("正在获取数据...");
        }

        @Override
        public void onAfter(int id) {
            //todo 取消显示正在上传
//            isCanRunRead =true;
//            tvTips.setVisibility(View.GONE);
            if(dialogOrganization2!=null){
                dialogOrganization2.dismiss();
            }
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            if(dialogOrganization2!=null){
                dialogOrganization2.dismiss();
            }

            LogUtil.e(TAG,"onError " + e.toString());

            if (mContext != null ) {
                final String info;
                if (e instanceof UnknownHostException) {
                    info = "网络未连接!";
                } else if (e instanceof SocketTimeoutException) {
                    info = "连接网络超时!";
                } else if(e instanceof ConnectException){
                    info = "连接地址: "+ PreferenceUtil.get(mContext,Const.KEY_URL_SERVER, ResHelper.getString(mContext,R.string.url_server_address))+" 失败";
                }
                else {
                    info = e.toString();
                }

                Toast.makeText(mContext,
                        ""+info,
                        Toast.LENGTH_LONG).show();
            }
            Log.e(TAG,"Exception = "+e.toString());

        }

        @Override
        public void onResponse(String response, int id) {
            if(dialogOrganization2!=null){
                dialogOrganization2.dismiss();
            }
            LogUtil.e(TAG, "onResponse：complete-------->");
            LogUtil.e(TAG, "response ------------> " + response);
            LogUtil.e(TAG, "id ------------>" + id);

            ChannelInfo obj = GsonUtil.toChannelInfo(response);
            if(obj!=null){

                if(obj.getSuccess().toUpperCase().equals("TRUE")){
                    //adapter

                    ChannelInfoList = obj.getData();

                    channelList.clear();

                    for(int i= 0;i<ChannelInfoList.size();i++){
                        channelList.add(ChannelInfoList.get(i).getSTAGE_NMAE());
                    }

                    if(channelList.size()!=0){
                        setAdapter();
                    }
                    else{
                        ChannelInfoList.clear();
                        ToastUtil.showToast(mContext,"查无数据!");
                        if(adapter!=null){
                            adapter.notifyDataSetChanged();
                        }
                    }


//                    ToastUtil.showToast(mContext,obj.getMsg());

                }
                else {
                    ToastUtil.showToast(mContext,obj.getMsg());
                }

                LogUtil.e(TAG, "ChannelInfoList-------->" + ChannelInfoList.toString());
                LogUtil.e(TAG, "channelList-------->" + channelList.toString());
            }
            else{
                ToastUtil.showToast(mContext,"response = "+ response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
        }
    }

    @Override
    protected void onDestroy() {
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
