package kld.com.huizhan.biz.callback;

import android.os.Handler;

import com.ldw.xyz.util.LogUtil;

import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.bean.upload.repair.UpLoadRepair;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.base.MyStringCallBack;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 30/11/2017.
 */

public class UploadRepairCallBack extends MyStringCallBack {


    private String TAG = "UploadRepairCallBack";
    Handler mHandler;
    HuiZhanBaseActivity activity;

    public interface OnUploadRepairListener{
        void upLoadSucceed();
        void upLoadFailed(String msg);


    }
    OnUploadRepairListener listener;
    public UploadRepairCallBack( String mTips, Handler h, HuiZhanBaseActivity activity,OnUploadRepairListener l) {
        this.mTips = mTips;
        this.Context = activity;
        this.activity = activity;
        this.listener=l;
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
            DataFunctionUtil.deleteAll(UpLoadRepair.class);

            if (pDialog != null) {
                ProgressUtil.upLoadSucceed(pDialog, mHandler);
            } else if (pDialog != null) {
                pDialog.dismiss();
            }
            listener.upLoadSucceed();

        } else {
            if (pDialog != null) {
                pDialog.dismiss();
            }
            LogUtil.e(TAG, "bean.getMsg()  = " + bean.getMsg());
            activity.showNoticeView(bean.getMsg());
            listener.upLoadFailed(bean.getMsg());
        }
    }

    @Override
    public void inProgress(float progress, long total, int id) {
    }
}
