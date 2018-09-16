package kld.com.huizhan.activity;

import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.PreferenceUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.ToastUtil;
import com.ldw.xyz.util.string.StringHelper2;

import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.util.Const;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class SettingActivity extends HuiZhanBaseActivity {

    TextInputEditText mUserMobile;

    private String TAG = "SettingActivity";

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void findViews() {
        super.findViews();
        mUserMobile = (TextInputEditText) findViewById(R.id.user_mobile);
        findViewById(R.id.login_btn).setOnClickListener(this);
    }

    @Override
    public void getData() {
        super.getData();
    }

    @Override
    public void showConent() {
        super.showConent();
        setToolBar("配置地址");
        String address = PreferenceUtil.get(mContext, Const.KEY_URL_SERVER,
                ResHelper.getString(mContext, R.string.url_server_address)
        );
        mUserMobile.setText(address);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_btn:
                String address = mUserMobile.getText().toString().trim();


                if(StringHelper2.judeIsLegalURL(address)){
                    PreferenceUtil.set(mContext, Const.KEY_URL_SERVER, address);

                    LogUtil.i(TAG, "SettingActivity = " + PreferenceUtil.get(mContext, Const.KEY_URL_SERVER, ResHelper.getString(mContext, R.string.url_server_address)));

                    ToastUtil.showToast(mContext, "修改成功!");
                }
                else{
                    ToastUtil.showToast(mContext,"修改失败!地址格式不正确!");
                }




                break;


        }


    }
}
