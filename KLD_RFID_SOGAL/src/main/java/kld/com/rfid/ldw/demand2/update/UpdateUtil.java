package kld.com.rfid.ldw.demand2.update;

import android.content.Context;
import android.support.annotation.Nullable;

import com.allenliu.versionchecklib.callback.OnCancelListener;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.ldw.xyz.util.LogUtil;

import kld.com.rfid.ldw.Const;

/**
 * Created by liudongwen on 2018/9/12.
 */

public class UpdateUtil {


    private static final String TAG = "UpdateUtil";
    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private static UIData crateUIData(String title,String content) {
        UIData uiData = UIData.create();
        uiData.setTitle(title);
        uiData.setDownloadUrl(Const.getURL_DownloadAPK());
        uiData.setContent(content);
        return uiData;
    }


    public static void check(final Context context, HttpRequestMethod requestMethod){
//        test(  context);
         builder = AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl(Const.getURL_Version())
                .setRequestMethod(requestMethod)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        LogUtil.e(TAG,"****** result ****** -------------------");
                        LogUtil.e(TAG,result);

                        String title = "立刻更新?";
                        String content = "发现新版本:"+"test";


                        return crateUIData(title,content);
                    }
                    @Override
                    public void onRequestVersionFailure(String message) {
                        LogUtil.e(TAG,"****** 下载失败  ****** -------------------");
                        LogUtil.e(TAG,"下载失败!"+"\n"+message);
//                        Toast.makeText(context, "下载失败!"+"\n"+message, Toast.LENGTH_SHORT).show();

                    }
                });

        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel() {
                //Toast.makeText(context,"Cancel Hanlde",Toast.LENGTH_SHORT).show();
            }
        });

        builder.excuteMission(context);
    }




    static private DownloadBuilder builder;
    static void test( Context context){
        builder = AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl("https://www.baidu.com").setRequestMethod(HttpRequestMethod.GET)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {

                        return crateUIData();
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {


                    }
                });

        builder.setOnCancelListener(new OnCancelListener() {
        @Override
        public void onCancel() {
//            Toast.makeText(,"cancel",Toast.LENGTH_SHORT).show();
        }
    });

        builder.excuteMission(context);
    }

    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private static UIData crateUIData() {
        UIData uiData = UIData.create();
        uiData.setTitle("title");
        uiData.setDownloadUrl("http://test-1251233192.coscd.myqcloud.com/1_1.apk");
        uiData.setContent("content");
        return uiData;
    }

}
