package kld.com.huizhan.biz.callback;

import android.os.Handler;

import com.ldw.xyz.util.LogUtil;

import java.util.List;

import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.bean.upload.output.OutPutUpLoad;
import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.base.MyStringCallBack;
import kld.com.huizhan.biz.callback.listener.OnUploadCallBackListener;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;

/**
 * Created by LDW10000000 on 30/11/2017.
 */

public class UploadOutPutCallBack extends MyStringCallBack {


    private String TAG = "UploadOutPutCallBack";
    Handler mHandler;
    HuiZhanBaseActivity activity;
    List<OutPutUpLoadList> list;

    OnUploadCallBackListener listener;

    public UploadOutPutCallBack(android.content.Context mContext, String mTips,Handler h,HuiZhanBaseActivity activity,List<OutPutUpLoadList> list,OnUploadCallBackListener listener) {
        this.mTips = mTips;
        this.Context = mContext;
        this.activity = activity;
        this.list = list;
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
            DataFunctionUtil.deleteAll(OutPutUpLoad.class);
            DataFunctionUtil.deleteAll(OutPutUpLoadList.class);

            //暂时不用修改为"已出库"
            //修改数据
//            Assets assets = new Assets();
//            for(int y=0;y<list.size();y++){
//                List<OutPutUpLoad> outPutUpLoadlist = list.get(y).getAssets();
//                for(int i =0;i<outPutUpLoadlist.size();i++){
//                    OutPutUpLoad upLoad = outPutUpLoadlist.get(i);
//                    String assetId = upLoad.getAssetsID();
//                    assets.setState(Const.STATE_OUTPUTED);
//                    assets.updateAll("assetID = ? ", assetId);
//                    LogUtil.i(TAG,"修改的assetID = " + assetId );
//                }
//            }


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
