package kld.com.huizhan.activity.repair;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.InputMethodUtil;
import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.ToastUtil;
import com.ldw.xyz.util.string.StringHelper2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kld.com.huizhan.HuIZhanApplication;
import kld.com.huizhan.R;
import kld.com.huizhan.activity.output.look.BaseActivity;
import kld.com.huizhan.adapter.repair.MyCostCenterAdapter;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.upload.repair.ULR;
import kld.com.huizhan.bean.upload.repair.UpLoadRepair;
import kld.com.huizhan.biz.Service;
import kld.com.huizhan.biz.callback.UploadRepairCallBack;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.EdittextEnterUtil;
import kld.com.huizhan.util.GsonUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;
import kld.com.huizhan.util.ui.ProgressUtil;
import kld.com.huizhan.view.CHScrollView;
import kld.com.huizhan.view.ListViewInPopWindow;

/**
 * Created by LDW10000000 on 05/12/2017.
 * <p>
 * 1. 每次进来, 显示数据库数据
 * 2. 每次点击保存, 清空数据库数据, 再全部插入;
 * 2. 点上传, 我1).先把数据清空, 2)全部插入到数据库 ,3)上传;
 */
@Deprecated
public class RepairActivityBackup extends BaseActivity {

    //    TextView tvSave,tvUpLoad;
    EditText etCode;
    private View[] views;
    private String TAG = "RepairActivity";
//    List<Assets> assetsList = new ArrayList<>();
//    HashMap<String,Integer> codePositionList = new HashMap<>();

    List<UpLoadRepair> upLoadRepairList = new ArrayList<>();
    HashSet<String> deleteItemHash = new HashSet<>();
    List<Map<String, String>> mDatas = new ArrayList<>();
    String codeItemID = "data_" + 4;

    HashSet<String> allCodeData = new HashSet<>();

    private boolean requireUserConfirmExit = true;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_repair);
        setToolBar("物资维保");
    }

    @Override
    public void findViews() {
        super.findViews();

        etCode = (EditText) findViewById(R.id.etCode);
        findViewById(R.id.tvSave).setOnClickListener(this);
        findViewById(R.id.tvUpLoad).setOnClickListener(this);
        findViewById(R.id.vAll).setOnClickListener(this);
        findViewById(R.id.vDelete).setOnClickListener(this);

        views = new View[]{etCode};

        EdittextEnterUtil.setListener(etCode, new EdittextEnterUtil.OnEnterListener() {
            @Override
            public void event() {
                LogUtil.e(TAG, "Enter");
                String code = etCode.getText().toString().trim();

                LogUtil.e(TAG, "allCodeData.toString() = " + allCodeData.toString());
                LogUtil.e(TAG, "code = " + code);

                // TODO: 05/12/2017  做兼容,如果入库是小写, 打印是大写, 代码里都转为小写;
                boolean b = allCodeData.contains(code);
                if (b == false) {
                    b = allCodeData.contains(code.toLowerCase());
                }

                if (b) {
                    ToastUtil.showToast(mContext, "条码已经扫过,数据已经在当前界面");
                } else {
                    searchCode(code);
                }


            }
        });
    }


    private void getSavedDataForBegin() {
        List<UpLoadRepair> listForUpLoadRepair = DataFunctionUtil.findAllUpLoadRepair();
        for (int i = 0; i < listForUpLoadRepair.size(); i++) {
            searchCode(listForUpLoadRepair.get(i).getAssetCode());
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tvSave:
                if (mDatas.size() != 0) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("确认保存?")
//                        .setContentText("Won't be able to recover this file!")
                            .setConfirmText("是的")
                            .showContentText(false)
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    SweetAlertDialog dialog = ProgressUtil.show2(mContext, "正在保存到本地...", sDialog, new ProgressUtil.OnFinishListener() {
                                        @Override
                                        public void onFinish() {

                                        }
                                    });
                                    try {
                                        //清空
                                        if (DataFunctionUtil.count(UpLoadRepair.class) != 0) {
                                            DataFunctionUtil.deleteAll(UpLoadRepair.class);
                                        }

                                        //保存
                                        LogUtil.e(TAG, "保存之前 upLoadRepairList.toString() = " + upLoadRepairList.toString());


//                                        List<UpLoadRepair> l = new ArrayList<>();
//                                        for(int i=0;i<upLoadRepairList.size();i++){
//                                            l.add(upLoadRepairList.get(i));
//                                        }
                                        ULR ulr = new ULR();
                                        ulr.setData(upLoadRepairList);
                                        String str = GsonUtil.toJson(ulr);

                                        ULR u = GsonUtil.toULR(str);
                                        LogUtil.e(TAG, "u.toString() = " + u.toString());
                                        DataFunctionUtil.saveAll(u.getData());


                                        //保存
                                        LogUtil.e(TAG, "保存之后 upLoadRepairList.toString() = " + upLoadRepairList.toString());

//                                        upLoadRepairHash.clear();
//                                        deleteItemHash.clear();
//                                        mDatas.clear();
//                                        adapterChange();
                                    } catch (Exception e) {
                                        ProgressUtil.errorDialog(dialog, e.toString());
                                        ExceptionUtil.handleException(e);
                                    }
                                    ProgressUtil.saveSucceed(dialog, mHandler);

                                }
                            }).show();
                } else {
                    //清空数据
                    if (DataFunctionUtil.count(UpLoadRepair.class) != 0) {
                        DataFunctionUtil.deleteAll(UpLoadRepair.class);
                        ToastUtil.showToast(mContext, "数据已经清空!");
                    } else {
                        ToastUtil.showToast(mContext, "当前没有数据!");
                    }

                }


                break;
            case R.id.tvUpLoad:

                if (mDatas.size() != 0 || DataFunctionUtil.count(UpLoadRepair.class) != 0) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("确认上传?")
                            .setConfirmText("是的")
                            .showContentText(false)
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

//                                  DataFunctionUtil.saveAll(upLoadRepairHash);
//                                  upLoadRepairHash.clear();
//                                  deleteItemHash.clear();
//                                  mDatas.clear();

                                    // TODO: 07/12/2017 上传
//                                    List<UpLoadRepair> listForUpLoadRepair = DataFunctionUtil.findAllUpLoadRepair();

                                    if (sDialog != null) {
                                        sDialog.dismiss();
                                    }

                                    ULR ulr = new ULR();
                                    ulr.setData(upLoadRepairList);
                                    uploadRepair(ulr);


                                }
                            }).show();
                } else {
                    ToastUtil.showToast(mContext, "当前没有任何数据!");
                }

                break;

            case R.id.vAll:

                if (deleteItemHash.size() == mDatas.size()) {
                    deleteItemHash.clear();
                    adapterChange();
                } else if (deleteItemHash.size() < mDatas.size()) {
                    for (int i = 0; i < mDatas.size(); i++) {
                        deleteItemHash.add(mDatas.get(i).get(codeItemID));
                    }
                    adapterChange();
                }

                break;

            case R.id.vDelete:


                if (true) {
                    Iterator<Map<String, String>> it = mDatas.iterator();
                    while (it.hasNext()) {
                        Map<String, String> map = it.next();
                        String code = map.get(codeItemID);
                        if (deleteItemHash.contains(code)) {
                            it.remove();
                        }
                    }
                    LogUtil.e(TAG, "mDatas.toString() = " + mDatas.toString());
                }

                if (true) {
                    Iterator<UpLoadRepair> it = upLoadRepairList.iterator();
                    while (it.hasNext()) {
                        UpLoadRepair upLoadRepair = it.next();
                        String code = upLoadRepair.getAssetCode();
                        if (deleteItemHash.contains(code)) {
                            it.remove();
                        }
                    }

                    LogUtil.e(TAG, "upLoadRepairList.toString() = " + upLoadRepairList.toString());
                }

                if (true) {
                    allCodeData.clear();
                    for (int i = 0; i < upLoadRepairList.size(); i++) {
                        String code = upLoadRepairList.get(i).getAssetCode();
                        allCodeData.add(code);
                    }
                }

                deleteItemHash.clear();
                adapterChange();

                break;
        }
    }

    private void adapterChange() {
        mSimpleAdapter.notifyDataSetChanged();
    }

//    private void saveAndClear(){
//        DataFunctionUtil.saveAll(upLoadRepairHash);
//        upLoadRepairHash.clear();
//        deleteItemHash.clear();
//        mDatas.clear();
//    }


    @Override
    public void showConent() {
        super.showConent();
        initViews();
        getSavedDataForBegin();
    }

    private Assets finAssets(String code) {
        return DataFunctionUtil.findFirstAssetsByCode(code);
    }

    private void searchCode(String code) {
        if (!StringHelper2.isEmpty(code)) {

            Assets assets;
            assets = finAssets(code);

            // TODO: 05/12/2017  做兼容,如果入库是小写, 打印是大写, 代码里都转为小写;
            if (assets == null) {
                assets = finAssets(code.toLowerCase());
            }

            if (assets != null) {
                addListViewData(assets);
                addUploadRepairListData(assets);
                adapterChange();
                mListView.smoothScrollToPosition(upLoadRepairList.size() - 1);
            } else {
                ToastUtil.showToast(mContext, "该条码不存在!");
                soundError();
            }
        }
        etCode.setText("");
        etCode.requestFocus();

        hideKeyBoard(views);
    }


    private void addListViewData(Assets assets) {
        Map<String, String> data = new HashMap<>();
        data.put("title", "" + assets.getName());
        data.put("data_" + 1, assets.getModel());
        data.put("data_" + 2, ResHelper.getString(mContext,R.string.text_repair_default_result));
        data.put("data_" + 3, "");
        data.put("data_" + 4, assets.getCode());
        data.put("data_" + 5, "");
        mDatas.add(data);
        allCodeData.add(assets.getCode());
    }

    private void addUploadRepairListData(Assets assets) {
        UpLoadRepair ulr = new UpLoadRepair();
        ulr.setAssetCode(assets.getCode());
        ulr.setAssetID(assets.getAssetID());
        ulr.setReulst(ResHelper.getString(mContext,R.string.text_repair_default_result));
        ulr.setDesc("");
//        ulr.setBillPerson(HuIZhanApplication.instance.lName);
        ulr.setBillPerson(HuIZhanApplication.getLName(mContext));
        upLoadRepairList.add(ulr);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        LogUtil.e(TAG,"keyCode = " + keyCode);
        switch (keyCode) {
            case Const.KEY_SCAN_CODE_2:
            case Const.KEY_SCAN_CODE_3:
            case Const.KEY_SCAN_CODE_4:
                etCode.setText("");
                etCode.requestFocus();
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    private ListView mListView;
    // 方便测试，直接写的public
    public HorizontalScrollView mTouchView;
    // 装入所有的HScrollView
    protected List<CHScrollView> mHScrollViews = new ArrayList<CHScrollView>();

    SimpleAdapter mSimpleAdapter;

    private void initViews() {
        Map<String, String> data = null;
        CHScrollView headerScroll = (CHScrollView) findViewById(R.id.item_scroll_title);
        // 添加头滑动事件
        mHScrollViews.add(headerScroll);
        mListView = (ListView) findViewById(R.id.scroll_list);

//        for (int i = 0; i < assetsList.size(); i++) {
//            Assets assets = assetsList.get(i);
//            data = new HashMap<>();
//            data.put("title", "" + assets.getName());
//            data.put("data_" + 1, assets.getModel());
//            data.put("data_" + 2,"");
//            data.put("data_" + 3, "");
//            data.put("data_" + 4, assets.getCode());
//            
//            mDatas.add(data);
//        }

        mSimpleAdapter = new ScrollAdapter(this, mDatas, R.layout.item_repair,
                new String[]{"title", "data_1", "data_2", "data_3", "data_4", "data_5", "data_6",},
                new int[]{R.id.item_title, R.id.item_data1, R.id.item_data2, R.id.item_data3, R.id.item_data4
                        , R.id.item_data5
//                        ,R.id.llBackground
                        // , R.id.item_data6
                });
        mListView.setAdapter(mSimpleAdapter);
    }

    public void addHViews(final CHScrollView hScrollView) {
        if (!mHScrollViews.isEmpty()) {
            int size = mHScrollViews.size();
            CHScrollView scrollView = mHScrollViews.get(size - 1);
            final int scrollX = scrollView.getScrollX();
            //第一次满屏后，向下滑动，有一条数据在开始时未加入
            if (scrollX != 0) {
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        //当listView刷新完成之后，把该条移动到最终位置
                        hScrollView.scrollTo(scrollX, 0);
                    }
                });
            }
        }
        mHScrollViews.add(hScrollView);
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        for (CHScrollView scrollView : mHScrollViews) {
            //防止重复滑动
            if (mTouchView != scrollView)
                scrollView.smoothScrollTo(l, t);
        }
    }


    class ScrollAdapter extends SimpleAdapter {

        private List<? extends Map<String, ?>> datas;
        private int res;
        private String[] from;
        private int[] to;
        private Context context;

        public ScrollAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from,
                             int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            this.datas = data;
            this.res = resource;
            this.from = from;
            this.to = to;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                v = LayoutInflater.from(context).inflate(res, null);
                // 第一次初始化的时候装进来
                addHViews((CHScrollView) v.findViewById(R.id.item_scroll));
                View[] views = new View[to.length];
                for (int i = 0; i < to.length; i++) {
                    View tv = v.findViewById(to[i]);
                    // tv.setOnClickListener(clickListener);
                    views[i] = tv;
                }
                v.setTag(views);


            }
            View[] holders = (View[]) v.getTag();
            int len = holders.length;
            for (int i = 0; i < len; i++) {

                String key = from[i];

                Map<String, ?> map = new HashMap<String, String>();
                if (this.datas.size() > position) {
                    map = this.datas.get(position);
                }
                String s = "";
                if (map.size() > 0) {
                    s = map.get(key).toString() + "";
                }

                ((TextView) holders[i]).setText(s);
//                LogUtil.e(TAG,"i = "+ i);
//                LogUtil.e(TAG,"s = "+ s);
//                LogUtil.e(TAG,"-----------分---隔---符------");


                if (i == 2) {
                    //todo 维保结果
                    TextView tv = (TextView) holders[i];

                    if (s.equals("完好")) {
                        tv.setTextColor(ResHelper.getColor(mContext, R.color.green));
                    } else {
                        tv.setTextColor(ResHelper.getColor(mContext, R.color.red));
                    }

                    holders[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final List<String> lists = new ArrayList<>();
                            List<String> listForRepair = new ArrayList<>();

                            listForRepair.add("完好");
                            listForRepair.add("需维修");

                            LogUtil.e(TAG, "listForRepair.toString()>>>>>>" + listForRepair.toString());

                            InputMethodUtil.forceHideKeyBoard(mContext, tv);

                            showPopupWindow(listForRepair, tv, new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int positionForRepair, long id) {
                                    String name = listForRepair.get(positionForRepair);
                                    tv.setText(name);
                                    if (positionForRepair == 0) {
                                        tv.setTextColor(ResHelper.getColor(mContext, R.color.green));
                                    } else if (positionForRepair == 1) {
                                        tv.setTextColor(ResHelper.getColor(mContext, R.color.red));
                                    }
                                    popupWindow.dismiss();

                                    upLoadRepairList.get(position).setReulst(tv.getText().toString());
                                    //修改
                                    mDatas.get(position).put("data_" + 2, tv.getText().toString());

                                }
                            });

                        }
                    });
                }

                if (i == 3) {
                    final EditText et = ((EditText) holders[i]);
                    et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
//                                LogUtil.e(TAG, "获得焦点>>>>>>>>>>>>>>>");
                                Const.isCanMove = true;
                            } else {
                                Const.isCanMove = false;
//                                LogUtil.e(TAG, "失去焦点>>>>>>>>>>>>>>>");
                                String str = et.getText().toString();
                                upLoadRepairList.get(position).setDesc(str);
                                //修改
                                mDatas.get(position).put("data_" + 3, et.getText().toString().trim());
                            }
                        }
                    });
                }


                if (i == 5) {
//                    LogUtil.e(TAG,"i = " + i);
//                    LogUtil.e(TAG,"holders[i] instanceof CheckBox  = " + (holders[i] instanceof CheckBox) );

                    if (holders[i] instanceof CheckBox) {
//                        LogUtil.e(TAG,"holders[i] instanceof CheckBox " );
//                        CheckBox cb = (CheckBox)holders[i];
                        holders[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {





                                boolean bb = deleteItemHash.contains(mDatas.get(position).get(codeItemID));
                                LogUtil.e(TAG, "bb  = " + bb);
                                if (deleteItemHash.contains(mDatas.get(position).get(codeItemID))) {
                                    deleteItemHash.remove(mDatas.get(position).get(codeItemID));

                                    LogUtil.e(TAG, "deleteItemHash.remove " + mDatas.get(position).get(codeItemID));
                                    LogUtil.e(TAG, "position  = " + position);
                                    LogUtil.e(TAG, "deleteItemHash.toString() " + deleteItemHash.toString());

                                    adapterChange();
                                } else if (deleteItemHash.contains(mDatas.get(position).get(codeItemID)) == false) {
                                    deleteItemHash.add(mDatas.get(position).get(codeItemID));

                                    LogUtil.e(TAG, "deleteItemHash.toString() " + deleteItemHash.toString());
                                    adapterChange();
                                }





                            }
                        });

                        boolean b = deleteItemHash.contains(mDatas.get(position).get(codeItemID));
//                        LogUtil.e(TAG,"deleteItemHash.toString() "  +deleteItemHash.toString());
//                        LogUtil.e(TAG,"position = " + position );
//                        LogUtil.e(TAG,"b = " + b );
//                        LogUtil.e(TAG,"----------分----------割----------线----------");
//                        cb.setChecked(b);
                        ((CheckBox) holders[i]).setChecked(b);

                    }
                }
            }


            return v;
        }
    }

    private PopupWindow popupWindow;

    private void showPopupWindow(List list, TextView tv, AdapterView.OnItemClickListener listener) {
        MyCostCenterAdapter adapter = new MyCostCenterAdapter(mContext, list);
        popupWindow = ListViewInPopWindow.showpopwindow(this, tv, listener, adapter, popupWindow);
    }

    private void uploadRepair(ULR ulr) {
        Service.postUploadList(this, Const.URL_UPLOAD_REPAIR, new UploadRepairCallBack("正在上传...", mHandler, this,
                new UploadRepairCallBack.OnUploadRepairListener() {
                    @Override
                    public void upLoadSucceed() {
                        mDatas.clear();
                        upLoadRepairList.clear();
                        deleteItemHash.clear();
                        allCodeData.clear();
                        adapterChange();
                        DataFunctionUtil.deleteAll(UpLoadRepair.class);

                    }

                    @Override
                    public void upLoadFailed(String msg) {

                    }
                }), ulr);

    }

    @Override
    public void onBackPressed() {
        if (upLoadRepairList.size() != 0) {
            finishWithAnimation();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void finishWithAnimation() {
        if (upLoadRepairList.size() != 0) {
            ProgressUtil.reminderDialog2(this,  "确定退出?","你有数据没有上传,退出前请确认已经保存数据", new ProgressUtil.OnReminderDialogListenter() {
                @Override
                public void cancel(Dialog dialog) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void confirm(Dialog dialog) {
                    upLoadRepairList.clear();
                    RepairActivityBackup.super.finishWithAnimation();
                }
            });
        } else {
            super.finishWithAnimation();
        }
    }

}
