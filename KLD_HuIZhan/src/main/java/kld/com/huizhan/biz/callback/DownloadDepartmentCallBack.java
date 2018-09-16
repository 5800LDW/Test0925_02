package kld.com.huizhan.biz.callback;

import android.os.Handler;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.LogUtil;

import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.bean.download.department.Department;
import kld.com.huizhan.bean.download.department.DepartmentList;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.base.MyStringCallBack;
import kld.com.huizhan.biz.callback.listener.OnDownloadCallBackListener;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 30/11/2017.
 */

public class DownloadDepartmentCallBack extends MyStringCallBack {
    private String TAG = "DownloadDepartmentCallBack";
    Handler mHandler;
    HuiZhanBaseActivity activity;
    public DownloadDepartmentCallBack(String mTips, Handler h, HuiZhanBaseActivity activity,OnDownloadCallBackListener listener) {
        this.mTips = mTips;
        this.Context = activity;
        mHandler = h;
        onDownloadCallBackListener=listener;
        this.activity = activity;
    }

    @Override
    public void onResponse(String response, int id) {
        super.onResponse(response, id);
        // 取消dialog显示
        BaseBean bean = GsonUtil.toBaseBean(response);
        if (Service.checkType(bean.getType())) {

            DepartmentList l = GsonUtil.toDepartmentList(response);

            LogUtil.e(TAG, "DepartmentList = " + l.getData().toString());

            try {
                if (l.getData().size() != 0) {

                    //清空
                    DataFunctionUtil.deleteAll(Department.class);
                    //保存
                    DataFunctionUtil.saveAll(l.getData());

                }
            } catch (Exception e) {
                onDownloadCallBackListener.failedCallBack();
                ProgressUtil.updateFailed(pDialog);
                ExceptionUtil.handleException(e);
                return;
            }

            if (l.getData().size() != 0 && pDialog != null) {
                ProgressUtil.updateSucceed(pDialog, mHandler);
            } else if (pDialog != null) {
                pDialog.dismiss();
            }

        } else {
            if (pDialog != null) {
                pDialog.dismiss();
            }
            LogUtil.e(TAG, "bean.getMsg()  = " + bean.getMsg());
            activity.showNoticeView(bean.getMsg());
            onDownloadCallBackListener.failedCallBack();
        }
    }

    @Override
    public void inProgress(float progress, long total, int id) {
    }
}
