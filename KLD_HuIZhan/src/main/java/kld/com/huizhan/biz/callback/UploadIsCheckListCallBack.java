package kld.com.huizhan.biz.callback;

import android.os.Handler;

import com.ldw.xyz.util.LogUtil;

import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.base.MyStringCallBack;
import kld.com.huizhan.biz.callback.listener.OnUploadCallBackListener;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 30/11/2017.
 */

public class UploadIsCheckListCallBack extends MyStringCallBack {


    private String TAG = "UploadIsCheckListCallBack";
    Handler mHandler;
    HuiZhanBaseActivity activity;
    OnUploadCallBackListener listener;
    public UploadIsCheckListCallBack(android.content.Context mContext, String mTips, Handler h, HuiZhanBaseActivity activity,OnUploadCallBackListener listener) {
        this.mTips = mTips;
        this.Context = mContext;
        this.activity = activity;
        mHandler = h;
        this.listener = listener;
    }

    @Override
    public void onResponse(String response, int id) {
        super.onResponse(response, id);

        // 取消dialog显示
        BaseBean bean = GsonUtil.toBaseBean(response);
        if (Service.checkType(bean.getType())) {

            LogUtil.e(TAG, "提交成功!" + bean.toString());
            LogUtil.e(TAG, "response = " + response);
//            //清空数据
//            DataFunctionUtil.deleteAll(UploadCheck.class);
//            //TODO 把AssetsCheck的已盘 不用改变, 把未提交更改为已提交

            listener.succeed();

            if (pDialog != null) {
                ProgressUtil.upLoadSucceed(pDialog, mHandler);
            } else if (pDialog != null) {
                pDialog.dismiss();
            }

        } else {
            if (pDialog != null) {
                pDialog.dismiss();
            }
            listener.failedCallBack();
            LogUtil.e(TAG, "bean.getMsg()  = " + bean.getMsg());
            activity.showNoticeView(bean.getMsg());
        }
    }

    @Override
    public void inProgress(float progress, long total, int id) {
    }
}
