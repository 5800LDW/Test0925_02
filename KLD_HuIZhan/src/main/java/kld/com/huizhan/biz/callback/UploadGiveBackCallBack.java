package kld.com.huizhan.biz.callback;

import android.os.Handler;

import com.ldw.xyz.util.LogUtil;

import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.bean.download.giveback.GiveBackAssets;
import kld.com.huizhan.bean.download.giveback.GiveBackBill;
import kld.com.huizhan.bean.upload.giveback.UploadGiveBack;
import kld.com.huizhan.bean.upload.giveback.UploadGiveBackAssets;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.base.MyStringCallBack;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 30/11/2017.
 */

public class UploadGiveBackCallBack extends MyStringCallBack {


    private String TAG = "UploadGiveBackCallBack";
    Handler mHandler;
    HuiZhanBaseActivity activity;
    public UploadGiveBackCallBack(android.content.Context mContext, String mTips, Handler h, HuiZhanBaseActivity activity) {
        this.mTips = mTips;
        this.Context = mContext;
        this.activity = activity;
        mHandler = h;
    }

    @Override
    public void onResponse(String response, int id) {
        super.onResponse(response, id);

        // 取消dialog显示
        BaseBean bean = GsonUtil.toBaseBean(response);
        if (Service.checkType(bean.getType())) {

            LogUtil.e(TAG, "提交成功!" + bean.toString());
            LogUtil.e(TAG, "response = " + response);
            //清空数据
            DataFunctionUtil.deleteAll(UploadGiveBack.class);
            DataFunctionUtil.deleteAll(UploadGiveBackAssets.class);
            DataFunctionUtil.deleteAll(GiveBackBill.class);
            DataFunctionUtil.deleteAll(GiveBackAssets.class);



            if (pDialog != null) {
                ProgressUtil.upLoadSucceed(pDialog, mHandler);
            } else if (pDialog != null) {
                pDialog.dismiss();
            }

        } else {
            if (pDialog != null) {
                pDialog.dismiss();
            }
            LogUtil.e(TAG, "bean.getMsg()  = " + bean.getMsg());
            activity.showNoticeView(bean.getMsg());
        }
    }

    @Override
    public void inProgress(float progress, long total, int id) {
    }
}
