package kld.com.huizhan.activity.check;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.ToastUtil;
import com.ldw.xyz.util.device.ScreenUtil;
import com.ldw.xyz.util.number.NumberUtil2;
import com.ldw.xyz.util.string.StringHelper2;
import com.ldw.xyz.util.time.TimeUtil;

import java.util.List;

import kld.com.huizhan.HuIZhanApplication;
import kld.com.huizhan.R;
import kld.com.huizhan.activity.output.look.BaseActivity;
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.download.user.User;
import kld.com.huizhan.bean.upload.check.local.UploadCheck;
import kld.com.huizhan.util.Const;
import kld.com.huizhan.util.EdittextEnterUtil;
import kld.com.huizhan.util.db.DataFunctionUtil;

/**
 * Created by LDW10000000 on 08/12/2017.
 */
@Deprecated
public class CheckActivityBackUp extends BaseActivity {
    private String TAG = "CheckActivity";
    TextView tvDate, tvPerson, tvStorage, tvContent, btModify, tvCheckedNum, tvUnCheckedNum, tvUpLoad;
    EditText etCode, etCheckNumber;
    private View[] views;

    @Override
    public void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_check_backup);
        setToolBar("物资盘点");
    }

    @Override
    public void findViews() {
        super.findViews();
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvPerson = (TextView) findViewById(R.id.tvPerson);
        tvStorage = (TextView) findViewById(R.id.tvStorage);
        etCode = (EditText) findViewById(R.id.etCode);
        tvContent = (TextView) findViewById(R.id.tvContent);
        etCheckNumber = (EditText) findViewById(R.id.etCheckNumber);

        btModify = (TextView) findViewById(R.id.btModify);


        tvCheckedNum = (TextView) findViewById(R.id.tvCheckedNum);
        tvUnCheckedNum = (TextView) findViewById(R.id.tvUnCheckedNum);
        tvUpLoad = (TextView) findViewById(R.id.tvUpLoad);

        tvCheckedNum.setOnClickListener(this);
        tvUnCheckedNum.setOnClickListener(this);
        tvUpLoad.setOnClickListener(this);
        btModify.setOnClickListener(this);

        views = new View[]{etCode, etCheckNumber};

    }

    @Override
    public void getData() {
        super.getData();
        EdittextEnterUtil.setListener(etCode, new EdittextEnterUtil.OnEnterListener() {
            @Override
            public void event() {
                LogUtil.e(TAG, "Enter");
                String code = etCode.getText().toString().trim();
                searchCode(code);
            }
        });
    }

    @Override
    public void showConent() {
//        User user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.instance.lName);
        User user = DataFunctionUtil.findFirstUserByName(HuIZhanApplication.getLName(mContext));

//        tvPerson.setText(HuIZhanApplication.instance.lName);
        tvPerson.setText(HuIZhanApplication.getLName(mContext));
        tvDate.setText(TimeUtil.getCurrentDateAccordingToFormat("yyyy-MM-dd"));
        tvStorage.setText(user.getStorageName());


        String str = String.format(ResHelper.getString(mContext, R.string.text_check_item), "",
                "", "", "", " ");

        //更新
        updateItemSurface(str);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.btModify:
                if (NumberUtil2.atLeastOne(etCheckNumber.getText().toString())) {
                    String id = getActivityAssetID();
                    modifyUploadCheckData(etCheckNumber.getText().toString().trim(), id);
                    btModify.setText("");
                } else {
                    ToastUtil.showToast(mContext, "请填入正确数量!");
                }

                break;
            case R.id.tvCheckedNum:
                break;

            case R.id.tvUnCheckedNum:
                break;

            case R.id.tvUpLoad:

                break;

        }
    }

    private String mAssetID = "";

    private String getActivityAssetID() {
        return mAssetID;
    }

    private void setActivityAssetID(String id) {
        mAssetID = id;
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

                setActivityAssetID(assets.getAssetID());

                String name = assets.getName();
                String asstCode = assets.getCode();
                String model = assets.getModel();
                String physicalNum = assets.getNumber1();
                String actualNum = physicalNum;


                //更改数量
                List<UploadCheck> uploadCheckList = DataFunctionUtil.findUploadCheckByAssetID(assets.getAssetID());
                UploadCheck uploadCheck = null;
                if (uploadCheckList.size() != 0) {
                    uploadCheck = uploadCheckList.get(0);
                }
                if (uploadCheck != null) {
                    actualNum = uploadCheck.getNum();
                }

                //显示界面
                String str = String.format(ResHelper.getString(mContext, R.string.text_check_item), name,
                        asstCode, model, physicalNum, actualNum);

                if (assets.getCodeType().equals(Const.TYPE_CODE_COMMON)) {
                    etCheckNumber.setText("1");
                    etCheckNumber.setCursorVisible(true);
                    etCheckNumber.setClickable(true);
                    etCheckNumber.setFocusable(true);
                    etCheckNumber.setPressed(true);
                    btModify.setVisibility(View.VISIBLE);
                    etCheckNumber.setBackground(ResHelper.getDrawable(mContext, R.drawable.shape_round_grey));
                } else if (assets.getCodeType().equals(Const.TYPE_CODE_OWN) || assets.getCodeType().equals(Const.TYPE_CODE_INDEX)) {
                    etCheckNumber.setText("1");
                    etCheckNumber.setCursorVisible(false);
                    etCheckNumber.setClickable(false);
                    etCheckNumber.setFocusable(false);
                    etCheckNumber.setPressed(false);
                    btModify.setVisibility(View.GONE);
                    etCheckNumber.setBackgroundColor(ResHelper.getColor(mContext, R.color.white));
                }


                //数据库没有,就插入数据
                if (uploadCheckList.size() == 0) {
                    insertUploadCheckDataToDB(actualNum, assets.getAssetID());
                }
                //共用条码才能修改数量
                else if(assets.getCodeType().equals(Const.TYPE_CODE_COMMON)){
                    ToastUtil.showToast(mContext,"已经盘点过了, 可以修改实盘数量");
                }

                updateItemSurface(str);
                updateModifyNum(actualNum);
                etCode.setHint(etCode.getText().toString().trim());

            } else {
                ToastUtil.showToast(mContext, "该条码不存在!");
                etCode.requestFocus();
                soundError();
            }
        }
        etCode.setText("");
        hideKeyBoard(views);
    }

    private void insertUploadCheckDataToDB(String num, String AssetID) {
        UploadCheck uc = new UploadCheck();
        uc.setAssetsID(AssetID);
        uc.setNum(num);
        uc.setPerson(tvPerson.getText().toString());
        uc.setDate(tvDate.getText().toString());
        uc.setResult("已盘");
        boolean b = uc.save();
        if (b) {
            ToastUtil.showToast(mContext, "盘点成功!");
        } else {
            ToastUtil.showToast(mContext, "盘点失败!");
        }

    }

    private void modifyUploadCheckData(String num, String AssetID) {
        int updateNum = DataFunctionUtil.updateUploadCheckByAssetID(num, AssetID);
        if (updateNum != 0) {
            ToastUtil.showToast(mContext, "修改成功!");
        } else {
            ToastUtil.showToast(mContext, "修改失败!");
        }
    }


    private void updateItemSurface(String str) {
        //更新内容
        setText(mContext, tvContent, str);
        //更新已盘/未盘
        int totalNum = DataFunctionUtil.count(Assets.class);
        int checkedNum = DataFunctionUtil.count(UploadCheck.class);
        int uncheckedNum = totalNum - checkedNum;

        String checkedStr = String.format(ResHelper.getString(mContext, R.string.text_checked), checkedNum);
        String uncheckedStr = String.format(ResHelper.getString(mContext, R.string.text_unchecked), uncheckedNum);
        setCheckedText(mContext, tvCheckedNum, checkedStr);
        setUnCheckedText(mContext, tvUnCheckedNum, uncheckedStr);
    }

    private void updateModifyNum(String num) {
        etCheckNumber.setText(num);
    }


    private Assets finAssets(String code) {
        return DataFunctionUtil.findFirstAssetsByCode(code);
    }

    public static void setText(Context mContext, TextView tv, String content) {
        String text = String.format(content);
        int nameBefore = text.indexOf("物资条码:");
        int standerBefore = text.indexOf("规格型号:");
        int numbeBefore = text.indexOf("实盘数量:");

        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext, 20)), 0, nameBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#C97330")), 0, nameBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色

        style.setSpan(new ForegroundColorSpan(Color.parseColor("#878787")), nameBefore + 5, standerBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色

        style.setSpan(new ForegroundColorSpan(Color.parseColor("#524438")), numbeBefore, numbeBefore + 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#E72F58")), numbeBefore + 5, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色

        tv.setText(style);
    }

    public static void setCheckedText(Context mContext, TextView tv, String content) {
        String text = String.format(content);
        int checkBefore = text.indexOf("已盘:");
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext, 18)), 0, checkBefore + 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        tv.setText(style);
    }

    public static void setUnCheckedText(Context mContext, TextView tv, String content) {
        String text = String.format(content);
        int checkBefore = text.indexOf("未盘:");
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext, 18)), 0, checkBefore + 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#c10000")), checkBefore + 3, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
        tv.setText(style);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.e(TAG, "keyCode = " + keyCode);
        switch (keyCode) {
            case 261:
            case 0:
//                etCode.setText("");
                etCode.requestFocus();
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
