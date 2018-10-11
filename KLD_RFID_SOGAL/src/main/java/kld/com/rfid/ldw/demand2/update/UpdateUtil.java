package kld.com.rfid.ldw.demand2.update;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.allenliu.versionchecklib.callback.OnCancelListener;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.ldw.OtherBizUtil;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.PreferenceUtil;
import com.ldw.xyz.util.ToastUtil;
import com.ldw.xyz.util.service.AccessibilityServiceUtil;
import com.ldw.xyz.util.string.StringHelper2;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.RFIDApplication;
import kld.com.rfid.ldw.bean.update.UpdateBean;
import kld.com.rfid.ldw.demand2.way3.MyAccessibilityService;
import kld.com.rfid.ldw.util.GsonUtil;

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
    private static UIData crateUIData(String title, String content, String url) {
        UIData uiData = UIData.create();
        uiData.setTitle(title);
        uiData.setDownloadUrl(url);
        uiData.setContent(content);
        return uiData;
    }

    public interface  UpdateCheckListener{
        void checkSuccess();
        void checkFail();
    }



    public static void check(final Context context, HttpRequestMethod requestMethod, final UpdateCheckListener listener) {

        RFIDApplication.setIsCanInstallFALSE();

//        test(  context);

        if (!StringHelper2.judeIsLegalURL(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_UPDATE))) {
            LogUtil.i("TAG", "软件更新地址 = " + PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_UPDATE));

            ToastUtil.showToast(RFIDApplication.instance, "检测更新失败,请正确填写软件更新地址");
            return;
        }



        builder = AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl(PreferenceUtil.get(RFIDApplication.instance, Const.KEY_URL_UPDATE))
//                .setRequestUrl(Const.getURL_Version())
                .setRequestMethod(requestMethod)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        LogUtil.e(TAG, "****** result ****** -------------------");
                        LogUtil.e(TAG, result);

                        String title = "发现新版本,立刻更新?";
                        String content;


                        String url;
                        UpdateBean bean;

                        //测试数据
                        if (Const.IsCanTest == true) {
                            bean = new UpdateBean();
                            bean.setUpdateUrl(Const.getURL_DownloadAPK());
                            bean.setApkSize("5m");
                            bean.setCanUpdate("YES");
                            bean.setConstraintUpdate("false");
                            bean.setVersionInfo("1.测试更新" + "\n" + "2.测试更新" + "\n" + "3.测试更新");
                        } else {
                            bean = GsonUtil.toUpdateBean(result);
                        }


                        if (bean != null && bean.getCanUpdate().toUpperCase().equals("YES")) {


                            //辅助功能开启了,才需要设置开启允许自动安装;
                            if (isAccessibilitySettingsOn(context)){
                                OtherBizUtil.setMyOtherBizUtilInterface(new OtherBizUtil.myOtherBizUtilInter() {
                                    @Override
                                    public void biz1() {
                                        RFIDApplication.setIsCanInstallTRUE();
                                    }
                                });

                            }


                            url = bean.getUpdateUrl();

                            //版本内容;
                            StringBuffer sb = new StringBuffer();
                            sb.append(bean.getVersionInfo());
                            sb.append("\n");
                            content = sb.toString();


                            //是否强制更新;后台下载,然后直接安装;
                            if (!bean.getConstraintUpdate().toUpperCase().equals("FALSE")) {
                                Toast.makeText(context, "新版本需要强制更新,请稍后...", Toast.LENGTH_LONG).show();
                                if (context != null && context instanceof Activity) {
                                    ((Activity) context).finish();
                                }

//                                //下面是静默下载和直接安装
                                if (true) {
                                    builder.setDirectDownload(true);
                                    builder.setShowNotification(true);
                                    builder.setShowDownloadingDialog(false);
                                    builder.setShowDownloadFailDialog(false);
                                }
                            }


//                            else{
//                                //注释下面的代码,下载框就能取消(就是能取消下载);
//                                builder.setForceUpdateListener(new ForceUpdateListener() {
//                                    @Override
//                                    public void onShouldForceUpdate() {
//                                        if(context!=null && context instanceof Activity){
//                                            ((Activity)context).finish();
//                                        }
//                                    }
//                                });
//                            }


                            //强制重新下载
                            builder.setForceRedownload(true);

                            builder.setShowDownloadFailDialog(false);

                            //不能取消
                            builder.setIsDownloadingDialogCancelable(false);


                            if(listener!=null){
                                listener.checkSuccess();
                            }


                            return crateUIData(title, content, url);
                        } else {
                            //不进行下载;
                            RFIDApplication.setIsCanInstallFALSE();
                            ToastUtil.showToast(context,"检查更新失败!");

                            if(listener!=null){
                                listener.checkFail();
                            }

                            return null;
                        }


                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
                        LogUtil.e(TAG, "****** 下载失败  ****** -------------------");
                        LogUtil.e(TAG, "下载失败!" + "\n" + message);
//                        Toast.makeText(context, "下载失败!"+"\n"+message, Toast.LENGTH_SHORT).show();
                        RFIDApplication.setIsCanInstallFALSE();
                    }
                });

        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel() {
                //Toast.makeText(context,"Cancel Hanlde",Toast.LENGTH_SHORT).show();
                RFIDApplication.setIsCanInstallFALSE();
            }
        });

        builder.excuteMission(context);
    }


    static private DownloadBuilder builder;

    static void test(Context context) {
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


    private static boolean isAccessibilitySettingsOn(Context mContext) {
        return AccessibilityServiceUtil.isAccessibilitySettingsOn(mContext, MyAccessibilityService.class);

    }
}
