package kld.com.huizhan.activity.main;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldw.xyz.collector.ActivityCollector;
import com.ldw.xyz.control.Controller;
import com.ldw.xyz.util.ActivityUtil;
import com.ldw.xyz.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kld.com.huizhan.HuIZhanApplication;
import kld.com.huizhan.R;
import kld.com.huizhan.activity.base.HuiZhanBaseActivity;
import kld.com.huizhan.activity.check.CheckActivity;
import kld.com.huizhan.activity.giveback.GiveBackActivity;
import kld.com.huizhan.activity.output.OutPutActivity2;
import kld.com.huizhan.activity.output.look.LookOutPutActivity;
import kld.com.huizhan.activity.putin.PutInActivity;
import kld.com.huizhan.activity.putin.look.LookPutInActivity;
import kld.com.huizhan.activity.repair.RepairActivity;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.download.department.Department;
import kld.com.huizhan.bean.download.giveback.GiveBackBill;
import kld.com.huizhan.bean.download.stock.Stock;
import kld.com.huizhan.bean.download.supplier.Supplier;
import kld.com.huizhan.bean.download.user.User;
import kld.com.huizhan.bean.local.AssetsCheck;
import kld.com.huizhan.bean.upload.check.local.UploadCheck;
import kld.com.huizhan.bean.upload.giveback.UploadGiveBack;
import kld.com.huizhan.bean.upload.giveback.UploadGiveBackList;
import kld.com.huizhan.bean.upload.output.OPUL;
import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;
import kld.com.huizhan.bean.upload.putin.Upload;
import kld.com.huizhan.bean.upload.putin.UploadList;
import kld.com.huizhan.bean.upload.repair.UpLoadRepair;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.callback.DownloadAssetsCallBack;
import kld.com.huizhan.biz.callback.DownloadDepartmentCallBack;
import kld.com.huizhan.biz.callback.DownloadGiveBackBillCallBack;
import kld.com.huizhan.biz.callback.DownloadStockCallBack;
import kld.com.huizhan.biz.callback.DownloadSupplierCallBack;
import kld.com.huizhan.biz.callback.UploadGiveBackCallBack;
import kld.com.huizhan.biz.callback.UploadOutPutCallBack;
import kld.com.huizhan.biz.callback.UploadPutInCallBack;
import kld.com.huizhan.biz.callback.listener.OnDownloadCallBackListener;
import kld.com.huizhan.biz.callback.listener.OnUploadCallBackListener;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;
import kld.com.huizhan.view.DragBallView;

public class MainActivity extends HuiZhanBaseActivity implements OnDownloadCallBackListener {
    private GridView gridView;
    private List<Map<String, Object>> data_list;


    private final int DOWNLOAD_ALL = 0;// 信息下载
    private final int MAINTENANCE = 1;//维保
    private final int CHECK = 2;//物资盘点

    private final int PUT_IN = 3;//物资入库 //扫码入库
    private final int LOOKUP_PUTIN = 4;//入库单查看
    private final int UPLOAD_PUTIN = 5;//入库上传

    private final int OUT_PUT = 6;//领用出库 //扫码出库
    private final int LOOKUP_OUTPUT = 7; //出库单查看
    private final int UPLOAD_OUTPUT = 8;//出库上传

    private final int GIVE_BACK_DOWNLOAD = 9;//下载出库单
    private final int GIVE_BACK = 10;//物资归还
    private final int GIVE_BACK_UPLOAD = 11;//归还上传;



//    private final int PUT_IN = 0;//物资入库
//    //private final int UPLOAD_PUTIN = 1;//入库上传
//    //private final int LOOKUP_PUTIN = 2;//入库单查看
//    private final int LOOKUP_PUTIN = 1;//入库单查看
//    private final int UPLOAD_PUTIN = 2;//入库上传


//    private final int OUT_PUT = 3;//领用出库
//    //private final int UPLOAD_OUTPUT = 4; //出库上传
//    //private final int LOOKUP_OUTPUT = 5;//出库单查看
//    private final int LOOKUP_OUTPUT = 4; //出库单查看
//    private final int UPLOAD_OUTPUT = 5;//出库上传


//    private final int GIVE_BACK_DOWNLOAD = 6;//下载出库单
//    private final int GIVE_BACK = 7;//物资归还
//    private final int GIVE_BACK_UPLOAD = 8;//归还上传;

//    private final int DOWNLOAD_ALL = 9;// 信息下载
//    private final int MAINTENANCE = 10;//维保
//    private final int CHECK = 11;//物资盘点


//    private final int PUT_IN = 9;//物资入库
//    //private final int UPLOAD_PUTIN = 1;//入库上传
//    //private final int LOOKUP_PUTIN = 2;//入库单查看
//    private final int LOOKUP_PUTIN = 10;//入库单查看
//    private final int UPLOAD_PUTIN = 11;//入库上传





    private String[] iconName = {
            "信息下载", "物资维保", "物资盘点",
            //Begin 20180729 物资入库 改为 扫码入库
            "扫码入库", "入库单查看", "入库上传",
            //Begin 20180729 领用出库 改为 扫码出库
            "扫码出库", "出库单查看", "出库上传",
            "下载出库单", "物资归还", "归还上传"
            };

    private int[] icon = {

            R.drawable.icon_download_all,
            R.drawable.icon_maintenance,
            R.drawable.icon_check,


            R.drawable.icon_put_in,
            R.drawable.icon_putin_lookup,
            R.drawable.icon_upload_putin,

            R.drawable.icon_output,
            R.drawable.icon_output_lookup,
            R.drawable.icon_output_upload2,


            R.drawable.icon_give_back_dl,
            R.drawable.icon_give_back,
            R.drawable.icon_give_back_ul


            };


    private String TAG = "MainActivity";
    MySimpleAdapter sim_adapter;


    @Override
    protected void onResume() {
        super.onResume();
        adapterChange();
    }


    private void adapterChange(){
        if(sim_adapter!=null){
            sim_adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_main);

//
////        setToolBar(HuIZhanApplication.instance.lName);
        TextView tvUser = (TextView)findViewById(R.id.tvUser);
//        tvUser.setText(HuIZhanApplication.instance.lName);
        tvUser.setText(HuIZhanApplication.getLName(mContext));
//        User user = DataFunctionUtil.findUser(HuIZhanApplication.instance.lName);
        User user = DataFunctionUtil.findUser(HuIZhanApplication.getLName(mContext));
        if(user!=null){
            TextView tvStorage = (TextView)findViewById(R.id.tvStorage);
            tvStorage.setText(user.getStorageName());
        }

//        ImageView ivBackground = (ImageView)findViewById(R.id.ivBackground);
//        PictureUtil.load(this, R.drawable.logo_main, ivBackground);





        gridView = (GridView) findViewById(R.id.gridView);
        data_list = new ArrayList<>();
        getListData();
        // 新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
         sim_adapter = new MySimpleAdapter(this, data_list, R.layout.item_main_gv, from, to);
        // 配置适配器
        gridView.setAdapter(sim_adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case DOWNLOAD_ALL:
                        LogUtil.e(TAG, "DownLoadAssets ");
                        int count = DataFunctionUtil.count(Assets.class);
                        if (count == 0) {
                            getAssetsData();
                        }
                        else if(DataFunctionUtil.count(UploadCheck.class)!=0){

                            ProgressUtil.reminderDialog3(mContext,"\"物资盘点\"存在未上传信息,请先上传!");

                        }

                        else {
                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("确定下载?")
                                    .setContentText("本地已经存在数据")
                                    .setConfirmText("是的")
                                    .setCancelText("取消")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            if (sDialog != null) {
                                                sDialog.dismiss();
                                            }
                                            getAssetsData(); //下载完成后, 才会自动下载department 和 sock

                                        }
                                    }).show();
                        }

                        break;

                    case PUT_IN:

                        if (DataFunctionUtil.count(Assets.class) == 0) {
                            showNoticeView("请先进行信息下载!");
//                            ToastUtil.showToast(MainActivity.this, "请先进行信息下载!");
                        } else {
                            ActivityUtil.startActivity(mContext, PutInActivity.class, null);
                        }

                        break;

                    case OUT_PUT:
                        if (DataFunctionUtil.count(Assets.class) == 0) {
                            showNoticeView("请先进行信息下载!");
//                            ToastUtil.showToast(MainActivity.this, "请先进行信息下载!");
                        } else {
                            ActivityUtil.startActivity(mContext, OutPutActivity2.class, null);
                        }


                        break;

                    case GIVE_BACK_DOWNLOAD:
                        LogUtil.e(TAG, "GIVE_BACK_DOWNLOAD ");
                        count = DataFunctionUtil.count(GiveBackBill.class);
                        if (count == 0) {
                            getGiveBackBillList();
                        } else {
                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("确定下载?")
                                    .setContentText("本地已经存在数据")
                                    .setConfirmText("是的")
                                    .setCancelText("取消")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {

                                            getGiveBackBillList();

                                            if (sDialog != null) {
                                                sDialog.dismiss();
                                            }

                                        }
                                    }).show();

                        }
                        break;

                    case GIVE_BACK:

                        if (DataFunctionUtil.count(Assets.class) == 0) {
                            showNoticeView("请先进行信息下载!");
//                            ToastUtil.showToast(MainActivity.this, "请先进行信息下载!");

                        } else if (DataFunctionUtil.count(GiveBackBill.class) == 0) {
                            showNoticeView("当前没有需要归还的物资"+"\n"+"或需要更新信息下载");
//                            ToastUtil.showToast(MainActivity.this, "请下载出库单!");
                        } else {
                            ActivityUtil.startActivity(mContext, GiveBackActivity.class, null);
                        }


                        break;

                    case GIVE_BACK_UPLOAD:

                        int uploadGiveBackNum = DataFunctionUtil.count(UploadGiveBack.class);

                        if (uploadGiveBackNum != 0) {

                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("确定上传?")
                                    .showContentText(false)
                                    .setConfirmText("是的")
                                    .setCancelText("取消")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {


                                            List<UploadGiveBack> list = DataFunctionUtil.findAllUploadGiveBack();
                                            UploadGiveBackList uploadGiveBackList = new UploadGiveBackList();

                                            uploadGiveBackList.setData(list);


                                            LogUtil.e("TAG", "要提交的数据: " + uploadGiveBackList.toString());

                                            Service.postUploadList(this, Const.URL_UPLOAD_GIVBACK,
                                                    new UploadGiveBackCallBack(mContext, "正在提交数据", mHandler, MainActivity.this),
                                                    uploadGiveBackList);


                                            if (sDialog != null) {
                                                sDialog.dismiss();
                                            }

                                        }
                                    }).show();

                        } else {
                            showNoticeView("没有需要提交的数据!");
                        }
                        break;


                    case CHECK:

                        if (DataFunctionUtil.count(Assets.class) == 0) {
                            showNoticeView("请先进行信息下载!");
//                            ToastUtil.showToast(MainActivity.this, "请先进行信息下载!");
                        } else {
                            ActivityUtil.startActivity(mContext, CheckActivity.class, null);
                        }


                        break;


                    case UPLOAD_PUTIN:

                        int uploadNum = DataFunctionUtil.count(Upload.class);

                        if (uploadNum != 0) {

                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("确定上传?")
                                    .showContentText(false)
                                    .setConfirmText("是的")
                                    .setCancelText("取消")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            List<Upload> list = DataFunctionUtil.findAllUpload();
                                            UploadList uploadList = new UploadList();
                                            uploadList.setData(list);

                                            LogUtil.e(TAG, "要提交的数据: " + uploadList.toString());

                                            Service.postUploadList(this, Const.URL_UPLOAD_PUTIN,
                                                    new UploadPutInCallBack("正在提交数据", mHandler, MainActivity.this, new OnUploadCallBackListener() {
                                                        @Override
                                                        public void succeed() {
                                                            adapterChange();
                                                        }

                                                        @Override
                                                        public void failedCallBack() {

                                                        }
                                                    }),
                                                    uploadList);

                                            if (sDialog != null) {
                                                sDialog.dismiss();
                                            }

//                                            adapterChange();

                                        }
                                    }).show();

                        } else {
                            showNoticeView("没有需要提交的数据!");
                        }




                        break;


                    case UPLOAD_OUTPUT:
                        int upLoadNum = DataFunctionUtil.count(OutPutUpLoadList.class);
                        if (upLoadNum != 0) {
                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("确定上传?")
                                    .showContentText(false)
                                    .setConfirmText("是的")
                                    .setCancelText("取消")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {

                                            List<OutPutUpLoadList> outPutLoadList = DataFunctionUtil.findAllOutPutLoadList();
                                            LogUtil.e(TAG, "outPutLoadList 要提交的数据: " + outPutLoadList.toString());
                                            upLoadOutPutData(outPutLoadList);

                                            if (sDialog != null) {
                                                sDialog.dismiss();
                                            }

//                                            adapterChange();

                                        }
                                    }).show();

                        } else {
                            showNoticeView("没有需要提交的数据!");
                        }

                        break;

                    //入库单查看
                    case LOOKUP_PUTIN:

                        int totalNum = DataFunctionUtil.count(Upload.class);
                        if (totalNum != 0) {
                            ActivityUtil.startActivity(mContext, LookPutInActivity.class, null);
                        } else {
                            showNoticeView("没有未提交的入库单!");
                        }

                        break;

                    //出库单查看
                    case LOOKUP_OUTPUT:

                        int outPutUnUploadNum = DataFunctionUtil.count(OutPutUpLoadList.class);
                        if (outPutUnUploadNum != 0) {
                            ActivityUtil.startActivity(mContext, LookOutPutActivity.class, null);
                        } else {
                            showNoticeView("没有未提交的出库单!");
                        }

                        break;

                    case MAINTENANCE:


                        if (DataFunctionUtil.count(Assets.class) == 0) {
                            showNoticeView("请先进行信息下载!");
//                            ToastUtil.showToast(MainActivity.this, "请先进行信息下载!");
                        } else {
                            ActivityUtil.startActivity(mContext, RepairActivity.class, null);
                        }
//                        int  maintenanceNum = DataFunctionUtil.count(UpLoadRepair.class);
//                        if (maintenanceNum != 0) {
//                            ActivityUtil.startActivity(mContext, RepairActivity.class,null);
//                        }
//                        else{
//                            showNoticeView("没有未提交的维保数据!");
//                        }


                        break;

                    default:

                        break;

                }
            }
        });

        if(Controller.isRelease==false){
            findViewById(R.id.ivBackground).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.e(TAG, "ActivityCollector.getActivityList().size() = "+ActivityCollector.getActivityList().size());
                    for (Activity aa : ActivityCollector.getActivityList()) {
                        LogUtil.e(TAG, " == "+aa.getLocalClassName());
                    }
                }
            });
        }


//        getWindow().getDecorView().post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });

    }







    public List<Map<String, Object>> getListData() {
        // cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }


    private void getStockData() {
        Service.stringGet(this, Const.URL_DOWNLOAD_STOCKS, new DownloadStockCallBack("正在获取仓库信息...", mHandler, this,this), 102);
    }

    private void getDepartmentData() {
        Service.stringGet(this, Const.URL_DOWNLOAD_DEPARTMENT, new DownloadDepartmentCallBack("正在获取部门信息...", mHandler, this,this), 103);
    }

    private void getAssetsData() {
        Service.stringGet(this, Const.URL_DOWNLOAD_ASSETS, new DownloadAssetsCallBack("正在获取资产数据...", mHandler, this, this), 105);
    }

    private void getSupplierData() {
        Service.stringGet(this, Const.URL_DOWNLOAD_SUPPLIER, new DownloadSupplierCallBack("正在获取供应商信息...", mHandler, this,this), 106);
    }

    private void upLoadOutPutData(List<OutPutUpLoadList> list) {
        OPUL opul = new OPUL();
        opul.setData(list);
        Service.postUploadList(this, Const.URL_UPLOAD_OUTPUT, new UploadOutPutCallBack(mContext, "正在提交数据...", mHandler, this, list, new OnUploadCallBackListener() {
            @Override
            public void succeed() {
                adapterChange();
            }

            @Override
            public void failedCallBack() {

            }
        }), opul);
    }

    private void getGiveBackBillList() {
//        User user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.instance.lName);
        User user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.getLName(mContext));
        Service.stringGet(this, Const.URL_DOWNLOAD_GIVBACK + user.getStorageID(), new DownloadGiveBackBillCallBack("正在下载出库单...", mHandler, this), 104);

    }


    //先下载Assets表的数据, 然后下载department , stock, supplier的数据
    @Override
    public void continueDownload() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getStockData();
            }
        }, 10);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDepartmentData();
            }
        }, 10);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupplierData();
            }
        }, 10);

        //todo 20180730 下载出库单合并到信息下载那里
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getGiveBackBillList();
            }
        },20);

    }

    //如果正常返回, 但是,返回结果是错误的,或者返回结果解析不成功,就四张表清空,还有作为盘点的备份表也清空,
    //如果是http错误,会走onerror方法(已经封装到myStringcallback了),不会情况表!
    @Override
    public void failedCallBack() {
        DataFunctionUtil.deleteAll(Assets.class);
        DataFunctionUtil.deleteAll(Stock.class);
        DataFunctionUtil.deleteAll(Department.class);
        DataFunctionUtil.deleteAll(Supplier.class);
        DataFunctionUtil.deleteAll(AssetsCheck.class);
    }


    private  void setBallView(int count){


    }

}



     class MySimpleAdapter extends BaseAdapter implements Filterable {
        private final LayoutInflater mInflater;

        private int[] mTo;
        private String[] mFrom;
        private android.widget.SimpleAdapter.ViewBinder mViewBinder;

        private List<? extends Map<String, ?>> mData;

        private int mResource;
        private int mDropDownResource;

        /** Layout inflater used for {@link #getDropDownView(int, View, ViewGroup)}. */
        private LayoutInflater mDropDownInflater;

        private SimpleFilter mFilter;
        private ArrayList<Map<String, ?>> mUnfilteredData;

        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data,
                               @LayoutRes int resource, String[] from, @IdRes int[] to) {
            mData = data;
            mResource = mDropDownResource = resource;
            mFrom = from;
            mTo = to;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * @see android.widget.Adapter#getCount()
         */
        public int getCount() {
            return mData.size();
        }

        /**
         * @see android.widget.Adapter#getItem(int)
         */
        public Object getItem(int position) {
            return mData.get(position);
        }

        /**
         * @see android.widget.Adapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * @see android.widget.Adapter#getView(int, View, ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {

            return createViewFromResource(mInflater, position, convertView, parent, mResource);
        }

        private View createViewFromResource(LayoutInflater inflater, int position, View convertView,
                                            ViewGroup parent, int resource) {
            View v;
            if (convertView == null) {
                v = inflater.inflate(resource, parent, false);
            } else {
                v = convertView;
            }


            DragBallView ballView =(DragBallView)(v.findViewById(R.id.drag_ball_view));
            // TODO: 03/01/2018


            if(position == 1){
                int count = DataFunctionUtil.count(UpLoadRepair.class);
                LogUtil.e("TAG","UpLoadRepair number = " + count);
                if(count !=0){
                    ballView.setMsgCount(count);
                    ballView.setVisibility(View.VISIBLE);
                } else {
                    ballView.setVisibility(View.GONE);

                }
            }
            else if(position==4){
                int count = DataFunctionUtil.count(Upload.class);
                if (count != 0) {
                    ballView.setMsgCount(count);
                    ballView.setVisibility(View.VISIBLE);
                } else {
                    ballView.setVisibility(View.GONE);
                }
//                if(Controller.isRelease==false){
//                    ballView.setMsgCount(99);
//                    ballView.setVisibility(View.VISIBLE);
//                }



            }
            else if(position == 7){
                int count = DataFunctionUtil.count(OutPutUpLoadList.class);
                if (count != 0) {
                    ballView.setVisibility(View.VISIBLE);
                    ballView.setMsgCount(count);
                } else {
                    ballView.setVisibility(View.GONE);
                }
//                if(Controller.isRelease==false){
//                    ballView.setMsgCount(999);
//                    ballView.setVisibility(View.VISIBLE);
//                }
            }
            else{
                ballView.setVisibility(View.GONE);

            }


//            //20180730 隐藏下载出库单
            if(position==9){
                v.setVisibility(View.GONE);
            }


            bindView(position, v);

            return v;
        }

        public void setDropDownViewResource(int resource) {
            mDropDownResource = resource;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            final LayoutInflater inflater = mDropDownInflater == null ? mInflater : mDropDownInflater;
            return createViewFromResource(inflater, position, convertView, parent, mDropDownResource);
        }

        private void bindView(int position, View view) {
            final Map dataSet = mData.get(position);
            if (dataSet == null) {
                return;
            }

            final android.widget.SimpleAdapter.ViewBinder binder = mViewBinder;
            final String[] from = mFrom;
            final int[] to = mTo;
            final int count = to.length;

            for (int i = 0; i < count; i++) {
                final View v = view.findViewById(to[i]);
                if (v != null) {
                    final Object data = dataSet.get(from[i]);
                    String text = data == null ? "" : data.toString();
                    if (text == null) {
                        text = "";
                    }

                    boolean bound = false;
                    if (binder != null) {
                        bound = binder.setViewValue(v, data, text);
                    }

                    if (!bound) {
                        if (v instanceof Checkable) {
                            if (data instanceof Boolean) {
                                ((Checkable) v).setChecked((Boolean) data);
                            } else if (v instanceof TextView) {
                                // Note: keep the instanceof TextView check at the bottom of these
                                // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                                setViewText((TextView) v, text);
                            } else {
                                throw new IllegalStateException(v.getClass().getName() +
                                        " should be bound to a Boolean, not a " +
                                        (data == null ? "<unknown type>" : data.getClass()));
                            }
                        } else if (v instanceof TextView) {
                            // Note: keep the instanceof TextView check at the bottom of these
                            // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                            setViewText((TextView) v, text);
                        } else if (v instanceof ImageView) {
                            if (data instanceof Integer) {
                                setViewImage((ImageView) v, (Integer) data);
                            } else {
                                setViewImage((ImageView) v, text);
                            }
                        } else {
                            throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                    " view that can be bounds by this SimpleAdapter");
                        }
                    }
                }
            }
        }

        public android.widget.SimpleAdapter.ViewBinder getViewBinder() {
            return mViewBinder;
        }

        public void setViewBinder(android.widget.SimpleAdapter.ViewBinder viewBinder) {
            mViewBinder = viewBinder;
        }

        public void setViewImage(ImageView v, int value) {
            v.setImageResource(value);
        }

        public void setViewImage(ImageView v, String value) {
            try {
                v.setImageResource(Integer.parseInt(value));
            } catch (NumberFormatException nfe) {
                v.setImageURI(Uri.parse(value));
            }
        }

        public void setViewText(TextView v, String text) {
            v.setText(text);
        }

        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new SimpleFilter();
            }
            return mFilter;
        }


        public static interface ViewBinder {

            boolean setViewValue(View view, Object data, String textRepresentation);
        }

        private class SimpleFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                FilterResults results = new FilterResults();

                if (mUnfilteredData == null) {
                    mUnfilteredData = new ArrayList<Map<String, ?>>(mData);
                }

                if (prefix == null || prefix.length() == 0) {
                    ArrayList<Map<String, ?>> list = mUnfilteredData;
                    results.values = list;
                    results.count = list.size();
                } else {
                    String prefixString = prefix.toString().toLowerCase();

                    ArrayList<Map<String, ?>> unfilteredValues = mUnfilteredData;
                    int count = unfilteredValues.size();

                    ArrayList<Map<String, ?>> newValues = new ArrayList<Map<String, ?>>(count);

                    for (int i = 0; i < count; i++) {
                        Map<String, ?> h = unfilteredValues.get(i);
                        if (h != null) {

                            int len = mTo.length;

                            for (int j=0; j<len; j++) {
                                String str =  (String)h.get(mFrom[j]);

                                String[] words = str.split(" ");
                                int wordCount = words.length;

                                for (int k = 0; k < wordCount; k++) {
                                    String word = words[k];

                                    if (word.toLowerCase().startsWith(prefixString)) {
                                        newValues.add(h);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    results.values = newValues;
                    results.count = newValues.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //noinspection unchecked
                mData = (List<Map<String, ?>>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        }
    }


