package kld.com.huizhan.adapter.check;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.device.ScreenUtil;

import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.bean.local.AssetsCheck;

/**
 * Created by LDW10000000 on 29/11/2017.
 */
@Deprecated
public class UnCheckedLookAdapterBackup extends RecyclerView.Adapter<UnCheckedLookAdapterBackup.ViewHolder> {

    private Context mContext;
    List<AssetsCheck> list;
    private String TAG = "UnCheckedLookAdapter";

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
//        View vModify;

        public ViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.content);
        }
    }

    public UnCheckedLookAdapterBackup(List<AssetsCheck> list, OnContentClickListener l) {
        this.list = list;
        listener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_putin_lookup, parent, false);

        final ViewHolder holder = new ViewHolder(view);


        holder.content.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                listener.contentClick(position);

            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AssetsCheck assets = list.get(position);
        String name = assets.getName();
        String asstCode = assets.getCode();
        String model = assets.getModel();
        String physicalNum = assets.getNumber1();
//        String actualNum = physicalNum;
        String actualNum = "";


        //显示界面
        String str = String.format(ResHelper.getString(mContext, R.string.text_check_item), name,
                asstCode, model, physicalNum, "");

        setText(mContext, holder.content, str);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    OnContentClickListener listener;

    public interface OnContentClickListener {
        void contentClick(int position);
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

//    public void setText(Context mContext, TextView tv, String content) {
//        String text = String.format(content);
//        int nameBefore = text.indexOf("物资条码:");
//        int standerBefore = text.indexOf("规格型号:");
//        int numbeBefore = text.indexOf("实盘数量:");
//
//        SpannableStringBuilder style = new SpannableStringBuilder(text);
//        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext, 20)), 0, nameBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
//        style.setSpan(new ForegroundColorSpan(Color.parseColor("#C97330")), 0, nameBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
//
//        style.setSpan(new ForegroundColorSpan(Color.parseColor("#878787")), nameBefore + 5, standerBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
//
//        style.setSpan(new ForegroundColorSpan(Color.parseColor("#524438")), numbeBefore, numbeBefore + 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
//        style.setSpan(new ForegroundColorSpan(Color.parseColor("#E72F58")), numbeBefore + 5, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
//
//        tv.setText(style);
//    }


}
