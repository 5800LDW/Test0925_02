package kld.com.huizhan.biz.callback;

import android.os.Handler;

import com.ldw.xyz.util.LogUtil;

import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.bean.upload.putin.Upload;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.base.MyStringCallBack;
import kld.com.huizhan.biz.callback.listener.OnUploadCallBackListener;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 30/11/2017.
 */

public class UploadPutInCallBack extends MyStringCallBack {
    private String TAG = "UploadPutInCallBack";
    Handler mHandler;
    HuiZhanBaseActivity activity;


    OnUploadCallBackListener listener;
    public UploadPutInCallBack( String mTips, Handler h, HuiZhanBaseActivity activity,OnUploadCallBackListener listener) {
        this.mTips = mTips;
        this.Context = activity;
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
            //清空数据
            DataFunctionUtil.deleteAll(Upload.class);

            if (pDialog != null) {
                ProgressUtil.upLoadSucceed(pDialog, mHandler);
            } else if (pDialog != null) {
                pDialog.dismiss();
            }

            listener.succeed();

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
