//package kld.com.huizhan.activity.main;
//
//import java.util.List;
//
//import kld.com.huizhan.HuIZhanApplication;
//import kld.com.huizhan.R;
//import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
//import kld.com.huizhan.bean.upload.output.OPUL;
//import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;
//import kld.com.huizhan.biz.Service;
//import kld.com.huizhan.biz.callback.DownloadAssetsCallBack;
//import kld.com.huizhan.biz.callback.DownloadDepartmentCallBack;
//import kld.com.huizhan.biz.callback.DownloadStockCallBack;
//import kld.com.huizhan.biz.callback.UploadOutPutCallBack;
//import kld.com.huizhan.util.Const;
//@Deprecated
//public class MainActivity2 extends HuiZhanBaseActivity {
//
//
//    private String TAG = "MainActivity2";
//
//
//    @Override
//    public void setContentView() {
//        super.setContentView();
//        setContentView(R.layout.activity_main2);
//
//        setToolBar(HuIZhanApplication.instance.lName);
//    }
//
//
//    private void getStockData() {
//        Service.stringGet(this, Const.URL_DOWNLOAD_STOCKS, new DownloadStockCallBack("正在获取仓库信息...", mHandler, this), 102);
//    }
//
//    private void getDepartmentData() {
//        Service.stringGet(this, Const.URL_DOWNLOAD_DEPARTMENT, new DownloadDepartmentCallBack("正在获取部门信息...", mHandler, this), 103);
//    }
//
//    private void getAssetsData() {
//        Service.stringGet(this, Const.URL_DOWNLOAD_ASSETS, new DownloadAssetsCallBack("正在获取数据...", mHandler, this, new DownloadAssetsCallBack.OnOtherMsgDownloadListener() {
//            @Override
//            public void download() {
//
//            }
//
//            @Override
//            public void saveOrDeleteFailedCallBack() {
//
//            }
//        }));
//    }
//
//    private void upLoadOutPutData(List<OutPutUpLoadList> list) {
//
//        OPUL opul = new OPUL();
//        opul.setData(list);
//
//        Service.postUploadList(this, Const.URL_UPLOAD_OUTPUT, new UploadOutPutCallBack(mContext, "正在提交数据", mHandler, this, list), opul);
//    }
//
//
//}
