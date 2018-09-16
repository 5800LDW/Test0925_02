package kld.com.huizhan.biz.base;

import android.app.Activity;
import android.content.Context;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.string.StringHelper2;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kld.com.huizhan.bean.base.BaseBean;
import kld.com.huizhan.biz.callback.listener.OnDownloadCallBackListener;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.ui.ProgressUtil;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class MyStringCallBack extends StringCallback implements ProgressUtil.OnFinishListener{

    public OnDownloadCallBackListener onDownloadCallBackListener;

    private String TAG = "MyStringCallBack";

    public MyStringCallBack(){}

    public Context Context;

    public String mTips;

    public BaseBean mBean;
    protected SweetAlertDialog pDialog;


    /**
     * @param mContext
     * @param mTips    可以为null
     */
    public MyStringCallBack(Context mContext, String mTips) {
        this.mTips = mTips;
        this.Context = mContext;
    }


    @Override
    public void onError(Call call, Exception e, int id) {

        LogUtil.e(TAG,"onError Complete");
        LogUtil.e(TAG,"onError " + e.toString());

        if (Context != null && Context instanceof Activity) {
            final String info;
            if (e instanceof UnknownHostException) {
                info = "网络未连接!";
            } else if (e instanceof SocketTimeoutException) {
                info = "连接网络超时!";
            }
            else {
                info = e.toString();
            }

            ((Activity) Context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(Context!=null && pDialog!=null){
                        ProgressUtil.errorDialog(pDialog,info);
                    }
                }
            });
        }

        ExceptionUtil.handleException(e);


    }

    @Override
    public void onResponse(String response, int id) {
        LogUtil.e(TAG, ">>>>>>>>>> response >>>>>>>>> " + response);
        try {
            if (!StringHelper2.isEmpty(response)) {
                if(response.startsWith("{")){
                    mBean = GsonUtil.toBaseBean(response);
                }
                else{
                    mBean = null;
                }

            }

            doJudgemBean(response);

        } catch (Exception e) {

            doJudgemBean(response);
            ExceptionUtil.handleException(e);
        }

//        if (pDialog != null) {
//            pDialog.dismiss();
//        }

        LogUtil.e(TAG,"onResponse Complete");

    }


    private void doJudgemBean(String response) {
        if (mBean == null) {
            mBean = new BaseBean();
            /***解析不成功就代表有误了*/
            mBean.setMsg("Json格式有误!" + "\n" + "返回内容:"+response);
        }

    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        pDialog = ProgressUtil.show(Context, mTips, null, this);
        LogUtil.e(TAG,"onBefore Complete");
    }


    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        LogUtil.e(TAG,"onAfter Complete");
    }


    @Override
    public void onFinish() {

    }
}
