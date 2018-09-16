package kld.com.huizhan.biz;

import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.PreferenceUtil;
import com.ldw.xyz.util.ResHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import kld.com.huizhan.HuIZhanApplication;
import kld.com.huizhan.R;
import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;
import kld.com.huizhan.biz.base.BaseService;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.GsonUtil;
import okhttp3.MediaType;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class Service extends BaseService{

    private static  String TAG = "Service";

    private static String jointUrl(String url){

        String url2 =  PreferenceUtil.get(HuIZhanApplication.instance, Const.KEY_URL_SERVER,
                ResHelper.getString(HuIZhanApplication.instance, R.string.url_server_address)
                ) + "/" + url;
        LogUtil.e(TAG," url = " + url2);

        return  url2;

    }


    public static void stringGet(Object TAG , String url ,StringCallback callBack){
        url = jointUrl(url);
        OkHttpUtils.get().url(url).id(101).tag(TAG).build().execute(callBack);
    }


    public static void stringGet(Object TAG , String url ,StringCallback callBack,int id){
        url = jointUrl(url);
        LogUtil.e("TAG","获取assets  url = " + url);
        OkHttpUtils.get().url(url).id(id).tag(TAG).build().execute(callBack);
    }


    public static void postUploadList(Object T , String url , StringCallback callBack , UpLoadBaseBean l){
        url = jointUrl(url);

        String str = GsonUtil.toJson(l);

        LogUtil.e(TAG,"GsonUtil.toJson(widget) = " + str);

        OkHttpUtils.postString().url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(str)
                .tag(T)
                .build()
                .execute(callBack);
    }

//    public static void postUploadList(Object T , String url , StringCallback callBack , UploadList l){
//        url = jointUrl(url);
//
//        String str = GsonUtil.toJson(l);
//
//        LogUtil.e(TAG,"GsonUtil.toJson(widget) = " + str);
//
//        OkHttpUtils.postString().url(url)
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .content(str)
//                .tag(T)
//                .build()
//                .execute(callBack);
//    }


    /**
     * 检测返回值
     *
     * @param s
     * @return
     */
    public static boolean checkType(String s) {
        if (s.toUpperCase().equals("S")) {
            return true;
        }

        return false;
    }





}
