package kld.com.huizhan.adapter.giveback;

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

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.bean.download.giveback.GiveBackBill;

/**
 * Created by LDW10000000 on 27/11/2017.
 */

public class SelectBillNoAdapter extends RecyclerView.Adapter<SelectBillNoAdapter.ViewHolder> {

    private Context mContext;
    private List<GiveBackBill> giveBackBillList = new ArrayList<>();
    private String TAG = "SelectBillNoAdapter";

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        public ViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.tv);
        }
    }

    public SelectBillNoAdapter(List<GiveBackBill> list , OnContentClickListener l) {
        giveBackBillList = list;
        listener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_storage_select2, parent, false);

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
        GiveBackBill gbb = giveBackBillList.get(position);

        String billNo = gbb.getOutDepotNo();
        String person = gbb.getBillPerson();
        String date = gbb.getBillDate();
        String department = gbb.getUseDepartment();


        String str = String.format(ResHelper.getString(mContext,R.string.text_give_back_item),billNo,
                person,date,department);
        setText(mContext,holder.content,str);


    }

    @Override
    public int getItemCount() {
        return giveBackBillList.size();
    }

    OnContentClickListener listener;
    public interface OnContentClickListener{
        void contentClick(int position);
    }

    public static void setText(Context mContext,TextView tv , String  content){
//      String text = String.format("￥%1$s  门市价:￥%2$s", 18.6, 22);//<color name="amaranth">#E72F58</color><color name="paco">#524438</color>
        String text = String.format(content);
        int BillBefore = text.indexOf("出库单号:");
        int storageBefore = text.indexOf("经办人:");


        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext,18)), 0, storageBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#2c2c2c")), 0, storageBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色

        style.setSpan(new ForegroundColorSpan(Color.parseColor("#878787")), storageBefore, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色



        tv.setText(style);
    }

}
