package kld.com.huizhan.adapter.input;

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
import kld.com.huizhan.bean.upload.putin.Upload;

/**
 * Created by LDW10000000 on 29/11/2017.
 */

public class InPutUnUploadLookAdapter extends RecyclerView.Adapter<InPutUnUploadLookAdapter.ViewHolder> {

    private Context mContext;
    List<Upload> uploadList;
    List<Assets> assetsList;
    private String TAG = "InPutUnUploadLookAdapter";


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        View tvDelete;
//        View vModify;

        public ViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.content);
            tvDelete = view.findViewById(R.id.tvDelete);
//            vModify =  view.findViewById(R.id.vModify);
        }
    }

    public InPutUnUploadLookAdapter(List<Upload> uploadList, List<Assets> assetsList, OnContentClickListener l) {
        this.uploadList = uploadList;
        this.assetsList = assetsList;
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

        holder.tvDelete.setVisibility(View.VISIBLE);

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                listener.deleteClick(position);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Upload upload = uploadList.get(position);

        String billNo = upload.getBillNo();
        String stroageName = upload.getStockName();
        String putInDate = upload.getBillDate();

        Assets assets = assetsList.get(position);

        String goodsName = assets.getName();


        String str = String.format(ResHelper.getString(mContext,R.string.text_input_unupload_goods_item),billNo,
                stroageName,putInDate,goodsName);
        setText(mContext,holder.content,str);

    }

    public static void setText(Context mContext,TextView tv , String  content){
//      String text = String.format("￥%1$s  门市价:￥%2$s", 18.6, 22);//<color name="amaranth">#E72F58</color><color name="paco">#524438</color>
        String text = String.format(content);
        int BillBefore = text.indexOf("单号:");
        int storageBefore = text.indexOf("仓库:");


        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext,18)), 0, storageBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#2c2c2c")), 0, storageBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色

        style.setSpan(new ForegroundColorSpan(Color.parseColor("#878787")), storageBefore, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色



        tv.setText(style);
    }


    @Override
    public int getItemCount() {
        return uploadList.size();
    }

   OnContentClickListener listener;
    public interface OnContentClickListener{
        void contentClick(int position);
        void deleteClick(int position);
    }




}
