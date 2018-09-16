package kld.com.huizhan.activity;

import android.support.design.widget.TextInputEditText;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.ActivityUtil;
import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.InputMethodUtil;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.MD5Util;
import com.ldw.xyz.util.device.DeviceUtil;
import com.ldw.xyz.util.device.ScreenUtil;
import com.ldw.xyz.util.string.StringHelper2;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kld.com.huizhan.HuIZhanApplication;
import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.activity.main.MainActivity;
import kld.com.huizhan.adapter.acount.MyCostCenterAdapter;
import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.bean.download.user.User;
import kld.com.huizhan.bean.download.user.UserList;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.base.MyStringCallBack;
import kld.com.huizhan.test.AndroidBug5497Workaround;
import kld.com.huizhan.test.KeyboardChangeListener;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.PictureUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;
import kld.com.huizhan.view.ListViewInPopWindow;

/**
 * Created by LDW10000000 on 20/11/2017.
 */

public class LoginActivity extends HuiZhanBaseActivity  implements KeyboardChangeListener.KeyBoardListener{

    TextInputEditText mUserMobile;
    TextInputEditText mUserPsw;
    CheckBox check_visible,checkBox_RemberPWD;

    View root_layout;
    private String TAG = "LoginActivity";

    View iv_Logo;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_login);

        Log.e("TAG","onCreate");
        LogUtil.e("TAG","2----");
//        //todo
//        activityRootView = findViewById(R.id.root_layout);
//
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //获取屏幕高度
//                screenHeight = LoginActivity.this.getWindowManager().getDefaultDisplay().getHeight();
//                //阀值设置为屏幕高度的1/3
//                keyHeight = screenHeight/3;
//            }
//        },1000);

//        AndroidBug5497Workaround.assistActivity(this);
    }

    @Override
    public void findViews() {
        super.findViews();

        mUserMobile = (TextInputEditText) findViewById(R.id.user_mobile);
        mUserPsw = (TextInputEditText) findViewById(R.id.user_password);
        check_visible = (CheckBox) findViewById(R.id.check_visible);
        check_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mUserPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mUserPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mUserPsw.postInvalidate();
                CharSequence text = mUserPsw.getText();
                if (!TextUtils.isEmpty(text)) {
                    Selection.setSelection((Spannable) text, text.length());
                }
            }
        });

        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.check_AccountLists).setOnClickListener(this);//iv_Background

        ImageView iv_Background = (ImageView) findViewById(R.id.iv_Background);
        PictureUtil.load(this, R.drawable.login_background, iv_Background);


        findViewById(R.id.DownLoadUsers).setOnClickListener(this);
        findViewById(R.id.ServerSet).setOnClickListener(this);


        checkBox_RemberPWD = (CheckBox) findViewById(R.id.checkBox_RemberPWD);
        checkBox_RemberPWD.setChecked(true);




        iv_Logo = findViewById(R.id.iv_Logo);
//        mUserMobile.setOnKeyBoardHideListener(new MyTextInputEditText.OnKeyBoardHideListener() {
//            @Override
//            public void onKeyHide(int keyCode, KeyEvent event) {
//                showLogo();
//            }
//        });
//
//        mUserPsw.setOnKeyBoardHideListener(new MyTextInputEditText.OnKeyBoardHideListener() {
//            @Override
//            public void onKeyHide(int keyCode, KeyEvent event) {
//                showLogo();
//            }
//        });
//
//        mUserMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    hideLogo();
//                }
//            }
//        });
//
//        mUserPsw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    hideLogo();
//                }
//            }
//        });


        root_layout = findViewById(R.id.root_layout);
    }

    //todo
    private void hideLogo(){
        showHideTitle(iv_Logo, ScreenUtil.dip2px(mContext,104),300,false);
    }

    //todo
    private void showLogo(){

        showHideTitle(iv_Logo,ScreenUtil.dip2px(mContext,104),300,true);
    }


    //todo
    @Override
    protected void onResume() {
        super.onResume();
        initView();
//        test();
        //添加layout大小发生改变监听器
//        activityRootView.addOnLayoutChangeListener(this);
    }
//    //Activity最外层的Layout视图
//    private View activityRootView;
//    //屏幕高度
//    private int screenHeight = 0;
//    //软件盘弹起后所占高度阀值
//    private int keyHeight = 0;
//    @Override
//    public void onLayoutChange(View v, int left, int top, int right,
//                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//
//        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
//
////      System.out.println(oldLeft + " " + oldTop +" " + oldRight + " " + oldBottom);
////      System.out.println(left + " " + top +" " + right + " " + bottom);
//
//
//        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
//        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){
//
//            Toast.makeText(this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
//
//        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){
//            showHideTitle(iv_Logo, 0,300,true);
//            Toast.makeText(this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }
//
//
//    private boolean isSoftShowing() {
//        //获取当前屏幕内容的高度
//        int screenHeight = getWindow().getDecorView().getHeight();
//        //获取View可见区域的bottom
//        Rect rect = new Rect();
//        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//
//        return screenHeight - rect.bottom != 0;
//    }
//    //todo






        @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        switch (id) {

            case R.id.DownLoadUsers:
                LogUtil.e(TAG, "DownLoadUsers ");
                int count = DataFunctionUtil.count(User.class);
                if(count==0){
                    getUserData();
                }
                else{

                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("确定下载?")
                            .setContentText("本地已经存在数据")
                            .setConfirmText("是的")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    if(sDialog!=null){
                                        sDialog.dismiss();
                                    }
                                    getUserData();
                                }
                            }).show();

                }

                break;

            case R.id.ServerSet:
                ActivityUtil.startActivity(mContext, SettingActivity.class, null);
                break;

            case R.id.login_btn:



                if (StringHelper2.isEmpty(mUserMobile.getText().toString().trim())) {

//                    ToastUtil.showToast(mContext, "请输入用户名!");
                    showNoticeView("请输入用户名!");

                    return;
                }
                if (StringHelper2.isEmpty(mUserPsw.getText().toString().trim())) {

                    showNoticeView("请输入密码!");
//                    ToastUtil.showToast(mContext, "请输入密码!");

                    return;
                }


                int number = DataFunctionUtil.count(User.class);
                LogUtil.e(TAG,"number = "+number);
                if(number<=0){
                    showNoticeView("请先下载用户资料!");
//                    ToastUtil.showToast(mContext,"请先下载用户资料!");
                    return;
                }

                String name = mUserMobile.getText().toString().trim();
                String pwd ;

                //如果标志位是1，就是自动填充，直接是MD5了，不需要变成MD5
                User user = DataFunctionUtil.findUser(name);
                if(user!=null && user.getFill()!=null&& user.getFill().equals("1")){
                    pwd = user.getPassword();
                }
                else {
                    pwd = MD5Util.getMD5ForLowercase(mUserPsw.getText().toString().trim());
                }


                if(DataFunctionUtil.findUser(name,pwd) >=1){
                    ((HuIZhanApplication)getApplicationContext()).lName = mUserMobile.getText().toString().trim();
                    ActivityUtil.startActivity(mContext, MainActivity.class, null);
                    mUserPsw.setText("");
                    //todo
                    //登录成功，如果勾选了记住密码，把这个账号的标志位变为1；

                    LogUtil.e("TAG","checkBox_RemberPWD is check = " + checkBox_RemberPWD.isChecked());
                    if(checkBox_RemberPWD.isChecked()){
                        DataFunctionUtil.updateUserFill(name,"1");
                    }
                    else{
                        //没勾选记住密码,把这个账号的标志位变为0;
                        DataFunctionUtil.updateUserFill(name,"0");
                    }

                }
                else{
                    showNoticeView("用户名或密码错误!");
//                    ToastUtil.showToast(mContext,"用户名或密码错误!");
                }

                break;



            case R.id.check_AccountLists:
                final List<String> lists = new ArrayList<>();
                List<User> accounts =  DataFunctionUtil.findUserFillIsTrue();
                for(int i =0 ;i<accounts.size();i++){
                    lists.add(accounts.get(i).getlName());
                    LogUtil.e("TAG","lists.toString()>>>>>>"+lists.toString());
                }

//              InputMethodUtil.forceHideKeyBoard(mContext,mUserMobile);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showPopupWindow(lists, mUserMobile, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String name = lists.get(position);
                                mUserMobile.setText(name);
                                popupWindow.dismiss();

                                //查找数据库有没有这个账号；
                                User user = DataFunctionUtil.findUserAndFillIsTrue(name);
                                //这个账号的标志位是不是1，是1就自动填充；
                                if(user!=null){
                                    String pwd = user.getPassword()+"";
                                    //自动填充
                                    mUserPsw.setText(pwd);

//                                  InputMethodUtil.forceHideKeyBoard(mContext,mUserMobile);
                                }


                            }
                        });
                    }
                },100);

                break;





            default:
                break;
        }
    }


    public class DownloadUsersCallBack extends MyStringCallBack {

        public DownloadUsersCallBack(android.content.Context mContext, String mTips) {
            this.mTips = mTips;
            this.Context = mContext;
        }

        @Override
        public void onResponse(String response, int id) {
            super.onResponse(response,id);
            // 取消dialog显示
            BaseBean bean = GsonUtil.toBaseBean(response);
            if (Service.checkType(bean.getType())) {

                UserList l = GsonUtil.toUserList(response);
                LogUtil.e(TAG, "UserList = " + l.getData().toString());

                try{
                    if(l.getData().size()!=0){
                        //清空
                        DataFunctionUtil.deleteAll(User.class);
                        //保存
                        DataFunctionUtil.saveAll(l.getData());
                    }
                }catch (Exception e){
                    ProgressUtil.updateFailed(pDialog);
                    ExceptionUtil.handleException(e);
                    return;
                }

                if(l.getData().size() != 0 && pDialog!=null){
                    ProgressUtil.updateSucceed(pDialog,mHandler);
                }
                else if(pDialog!=null){
                    pDialog.dismiss();
                }

            } else {
                if(pDialog!=null){
                    pDialog.dismiss();
                }
                LogUtil.e(TAG, "bean.getMsg()  = " + bean.getMsg());
                showNoticeView( bean.getMsg());
//                ToastUtil.showToast(mContext, bean.getMsg());
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
        }
    }

    private void getUserData(){
        Service.stringGet(this, Const.URL_DOWNLOAD_USERS, new DownloadUsersCallBack(this, "正在获取数据..."));
    }




    private void test(){
        if(Controller.isRelease==false){
            mUserPsw.setText("fa02");
            mUserMobile.setText("fa02");
        }
    }


    private PopupWindow popupWindow;
    private void showPopupWindow(List list, TextView tv, AdapterView.OnItemClickListener listener) {

        MyCostCenterAdapter adapter = new MyCostCenterAdapter(mContext, list);
        popupWindow = ListViewInPopWindow.showpopwindowForLogin(LoginActivity.this, tv, listener, adapter, popupWindow);

    }






    private void showHideTitle(final View view, final int maxHeight, int duration, boolean isShow) {
        ValueAnimator animator;
        if (isShow) {
            animator = ValueAnimator.ofFloat(0f, 1f);
        } else {
            animator = ValueAnimator.ofFloat(1f, 0f);
        }

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) (currentValue * maxHeight);
                view.setLayoutParams(params);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                root_layout.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                root_layout.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                root_layout.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });





        animator.setDuration(duration).start();
    }




    private KeyboardChangeListener mKeyboardChangeListener;

    private void initView(){
        mKeyboardChangeListener = new KeyboardChangeListener(this);
        mKeyboardChangeListener.setKeyBoardListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mKeyboardChangeListener.destroy();
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        LogUtil.e("GlobalLayoutActivity==", "onKeyboardChange() called with: " + "isShow = [" + isShow + "], keyboardHeight = [" + keyboardHeight + "]");
//        Toast.makeText(this,"软键盘=" + isShow + ",高度差=keyboardHeight==" + keyboardHeight,Toast.LENGTH_LONG).show();


            LogUtil.e("TAG","getDeviceManufacturer() = " + DeviceUtil.getDeviceManufacturer());
            //华为手机都不适配...
            if("HUAWEI".equals(DeviceUtil.getDeviceManufacturer()+"")){
               return;
            }
            if(isShow){
                hideLogo();
            }
            else {
                showLogo();
            }

     }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if(mKeyboardChangeListener!=null){
            mKeyboardChangeListener.destroy();
        }
    }
}
