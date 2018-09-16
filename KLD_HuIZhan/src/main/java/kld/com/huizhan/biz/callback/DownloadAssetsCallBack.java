package kld.com.huizhan.biz.callback;

import android.os.Handler;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.LogUtil;

import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.download.assets.AssetsList;
import kld.com.huizhan.bean.local.AssetsCheck;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.base.MyStringCallBack;
import kld.com.huizhan.biz.callback.listener.OnDownloadCallBackListener;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 30/11/2017.
 */

public class DownloadAssetsCallBack extends MyStringCallBack {

    HuiZhanBaseActivity activity;
    Handler mHandler;
    private String TAG = "DownloadAssetsCallBack";
    public DownloadAssetsCallBack(String mTips, Handler h, HuiZhanBaseActivity activity,OnDownloadCallBackListener listener) {
        this.mTips = mTips;
        this.Context = activity;
        this.activity = activity;
        onDownloadCallBackListener=listener;
        mHandler = h;
    }

    @Override
    public void onResponse(String response, int id) {
        super.onResponse(response, id);


        LogUtil.e(TAG,"id = " + id);
        LogUtil.e(TAG,"response = " + response);


        // 取消dialog显示
        BaseBean bean = GsonUtil.toBaseBean(response);
        if (Service.checkType(bean.getType())) {

            AssetsList l = GsonUtil.toAssetsList(response);


            LogUtil.e(TAG, "AssetsList = " + l.getData().toString());

            try {
                if (l.getData().size() != 0) {

                    //清空
                    DataFunctionUtil.deleteAll(Assets.class);
                    //保存
                    DataFunctionUtil.saveAll(l.getData());


//                    // TODO: 11/12/2017  备用表的存储
//                    AssetsCheckList ll = GsonUtil.toAssetsCheckList(response);
//                    LogUtil.e(TAG, "AssetsCheckList = " + ll.getData().toString());
                    //清空
                    DataFunctionUtil.deleteAll(AssetsCheck.class);
//                    //保存
//                    DataFunctionUtil.saveAll(ll.getData());


                    onDownloadCallBackListener.continueDownload();
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