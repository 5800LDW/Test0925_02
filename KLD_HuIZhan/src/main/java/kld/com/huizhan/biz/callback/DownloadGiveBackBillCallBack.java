package kld.com.huizhan.biz.callback;

import android.os.Handler;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.LogUtil;

import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.bean.download.giveback.GiveBackAssets;
import kld.com.huizhan.bean.download.giveback.GiveBackBill;
import kld.com.huizhan.bean.download.giveback.GiveBackBillList;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.base.MyStringCallBack;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 30/11/2017.
 */

public class DownloadGiveBackBillCallBack extends MyStringCallBack {

    HuiZhanBaseActivity activity;
    Handler mHandler;
    private String TAG = "DownloadGiveBackBillCallBack";

    public DownloadGiveBackBillCallBack(String mTips, Handler h, HuiZhanBaseActivity activity) {
        this.mTips = mTips;
        this.Context = activity;
        this.activity = activity;
        mHandler = h;
    }

    @Override
    public void onResponse(String response, int id) {
        super.onResponse(response, id);
        LogUtil.e("TAG","DownloadGiveBackBillCallBack response = "+ response);
        // 取消dialog显示
        BaseBean bean = GsonUtil.toBaseBean(response);
        if (Service.checkType(bean.getType())) {

            GiveBackBillList l = GsonUtil.toGiveBackBillList(response);

            LogUtil.e(TAG, "GiveBackBillList = " + l.getData().toString());
            try {
                if (l.getData().size() != 0) {

                    // TODO: 04/12/2017 这个有点问题,不是很优雅
                    //清空
                    DataFunctionUtil.deleteAll(GiveBackBill.class);
                    DataFunctionUtil.deleteAll(GiveBackAssets.class);


                    for (int i = 0; i < l.getData().size(); i++) {
//                        for(int x=0;x<l.getData().get(i).getAssets().size();x++){
                        DataFunctionUtil.saveAll(l.getData().get(i).getAssets());
//                        }
                    }

                    //保存
                    DataFunctionUtil.saveAll(l.getData());

                }
            } catch (Exception e) {
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
        }
    }

    @Override
    public void inProgress(float progress, long total, int id) {
    }
}