package kld.com.huizhan.adapter.output;

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
import kld.com.huizhan.bean.download.assets.Assets;
import kld.com.huizhan.bean.upload.output.OutPutUpLoad;
import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;

/**
 * Created by LDW10000000 on 29/11/2017.
 */

public class OutPutUploadAdapter extends RecyclerView.Adapter<OutPutUploadAdapter.ViewHolder> {

    private Context mContext;
    private OutPutUpLoadList outPutUpLoadList ;
    List<Assets> assetsList;
    private String TAG = "OutPutUploadAdapter";

//    HashSet<Integer> selectItem;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        View vModify;

        public ViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.content);
            vModify =  view.findViewById(R.id.vModify);
        }
    }

    public OutPutUploadAdapter(OutPutUpLoadList outPutUpLoadList , List<Assets> assetsList, OnContentClickListener l) {
        this.outPutUpLoadList = outPutUpLoadList;
        this.assetsList = assetsList;
        listener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_output_upload, parent, false);

        final ViewHolder holder = new ViewHolder(view);


        holder.vModify.setOnClickListener(new View.OnClickListener() {

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

        Assets assets = assetsList.get(position);

        String name = assets.getName();
        String code = assets.getCode();
        String standards = assets.getModel();
        // TODO: 29/11/2017  类别是这个吗?
//        String classify = assets.getCateCode();
//        String storage = assets.getStorageName();
        String unit = assets.getUnit1();

        String storageGoodsNum = assets.getNumber1();


        OutPutUpLoad outPutUpLoad = outPutUpLoadList.getAssets().get(position);
        String number = outPutUpLoad.getOutDepotNum();


        String str = String.format(ResHelper.getString(mContext,R.string.text_output_upload_goods_item),name,
                code,standards,unit,storageGoodsNum,number);
//        holder.content.setText( str );
        setText(mContext,holder.content,str);

    }

    public static void setText(Context mContext,TextView tv , String  content){
//      String text = String.format("￥%1$s  门市价:￥%2$s", 18.6, 22);//<color name="amaranth">#E72F58</color><color name="paco">#524438</color>
        String text = String.format(content);
        int nameBefore = text.indexOf("物资条码:");
        int standerBefore = text.indexOf("规格型号:");

        int numbeBefore = text.indexOf("出库数量:");


        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext,20)), 0, nameBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#C97330")), 0, nameBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色

        style.setSpan(new ForegroundColorSpan(Color.parseColor("#878787")), nameBefore+5, standerBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色

        style.setSpan(new ForegroundColorSpan(Color.parseColor("#524438")), numbeBefore, numbeBefore+5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#E72F58")), numbeBefore+5, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色


        tv.setText(style);
    }


    @Override
    public int getItemCount() {
        return outPutUpLoadList.getAssets().size();
    }

   OnContentClickListener listener;
    public interface OnContentClickListener{
        void contentClick(int position);
    }




}
