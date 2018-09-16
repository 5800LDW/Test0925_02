package kld.com.huizhan.adapter.output.look;

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
import kld.com.huizhan.bean.upload.output.OutPutUpLoad;
import kld.com.huizhan.bean.upload.output.OutPutUpLoadList;

/**
 * Created by LDW10000000 on 29/11/2017.
 */

public class OutPutUnUploadLookAdapter extends RecyclerView.Adapter<OutPutUnUploadLookAdapter.ViewHolder> {

    private Context mContext;
    List<OutPutUpLoadList> outPutLoadList;
    List<String> nameList;
    private String TAG = "OutPutUnUploadLookAdapter";


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        View tvDelete;

        public ViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.content);
            tvDelete = view.findViewById(R.id.tvDelete);
        }
    }

    public OutPutUnUploadLookAdapter(List<OutPutUpLoadList> outPutLoadList,List<String> nameList, OnContentClickListener l) {
        this.outPutLoadList = outPutLoadList;
        this.nameList = nameList;
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

        OutPutUpLoadList list = outPutLoadList.get(position);
        List<OutPutUpLoad> assets = list.getAssets();

        String billNo = list.getBillNo();
        String userDepartment = list.getUserDepartment();
        String outPutDate = list.getBillDate();

//        String goodsName = "";
//        if(assets.size()!=0){
//            OutPutUpLoad outPutUpLoad  = assets.get(0);
//            Assets a = DataFunctionUtil.findAssetsByAssetID(outPutUpLoad.getAssetsID());
//            goodsName = a.getName() + "等"+assets.size()+"商品";
//        }

        String goodsName = nameList.get(position);

        String str = String.format(ResHelper.getString(mContext,R.string.text_output_unupload_goods_item),billNo,
                userDepartment,outPutDate,goodsName);
        setText(mContext,holder.content,str);

    }

    public static void setText(Context mContext,TextView tv , String  content){
//      String text = String.format("￥%1$s  门市价:￥%2$s", 18.6, 22);//<color name="amaranth">#E72F58</color><color name="paco">#524438</color>
        String text = String.format(content);
        int BillBefore = text.indexOf("单号:");
        int storageBefore = text.indexOf("使用部门:");


        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext,18)), 0, storageBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#2c2c2c")), 0, storageBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色

        style.setSpan(new ForegroundColorSpan(Color.parseColor("#878787")), storageBefore, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色



        tv.setText(style);
    }


    @Override
    public int getItemCount() {
        return outPutLoadList.size();
    }

   OnContentClickListener listener;
    public interface OnContentClickListener{
        void contentClick(int position);
        void deleteClick(int position);
    }




}
