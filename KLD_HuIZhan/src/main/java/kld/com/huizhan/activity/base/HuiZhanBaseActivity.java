package kld.com.huizhan.activity.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.ldw.xyz.activity.BaseActivity;
import com.ldw.xyz.util.ActivityNameUtil;
import com.ldw.xyz.util.InputMethodUtil;
import com.ldw.xyz.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import kld.com.huizhan.R;

/**
 * Created by LDW10000000 on 20/11/2017.
 */

public class HuiZhanBaseActivity extends BaseActivity implements  AdapterView.OnItemClickListener, View.OnClickListener{



    protected Object cancelTag = this;
    public String TAG;
    public Context mContext;
    public Handler mHandler = new Handler();
    @Override
    public void setContentView() {
        mContext = this;
        TAG = setTAG();
    }
    protected String setTAG() {
        return ActivityNameUtil.getRunningActivityName(this);
    }

    @Override
    public void findViews() {

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
    }

    @Override
    public void getData() {

    }

    @Override
    public void showConent() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    protected void finishWithAnimation(){
        //// TODO: 13/06/2017  设置退出动画
        finish();
//        overridePendingTransition(com.ldw.xyz.R.anim.push_left_in,
//                com.ldw.xyz.R.anim.push_left_out);
        overridePendingTransition(com.ldw.xyz.R.anim.push_left_out,
                com.ldw.xyz.R.anim.push_left_in);

    }

    public void setToolBar(String str ,int id){
        Toolbar toolbar = (Toolbar)findViewById(id);
//        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(str);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
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

    public void setToolBar(String str ,String  rightButtonName){
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

        TextView btnRight= (TextView)toolbar.findViewById(R.id.btnRight);
        btnRight.setText(rightButtonName);
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

    public void hideKeyBoard(final View... views){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodUtil.forceHideKeyBoard(mContext,views);
            }
        },150);
    }

    public void showKeyBoard(final View view){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodUtil.forceShowKeyBoard(mContext,view);
            }
        },150);
    }

    protected void setCursorRight(EditText editText){
        editText.setSelection(editText.getText().toString().length());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(cancelTag);

        //避免内存泄露
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }

    }


    public void showNoticeView(String tips){
        //解决内存泄露 2018
        ToastUtil.showToast(mContext, tips);
    }


}




































