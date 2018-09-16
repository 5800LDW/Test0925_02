package kld.com.huizhan.adapter.giveback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.string.StringHelper2;

import java.util.HashMap;
import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.bean.download.giveback.GiveBackAssets;

/**
 * Created by LDW10000000 on 29/11/2017.
 */

public class GiveBckAdapter extends RecyclerView.Adapter<GiveBckAdapter.ViewHolder> {

    private Context mContext;
    List<GiveBackAssets> giveBackAssetsList;
    HashMap<String,Integer> checkHashMap;
    private String TAG = "GiveBckAdapter";


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvCode;
        TextView tModel;
        CheckBox cbCheck;
        EditText etCheckNumber;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvCode = (TextView) view.findViewById(R.id.tvCode);
            tModel = (TextView) view.findViewById(R.id.tModel);
            cbCheck = (CheckBox) view.findViewById(R.id.cbCheck);
            etCheckNumber = (EditText)view.findViewById(R.id.etCheckNumber);
        }
    }

    public GiveBckAdapter(List<GiveBackAssets> giveBackAssetsList, HashMap<String,Integer> checkHashMap, OnContentClickListener l) {
        this.giveBackAssetsList = giveBackAssetsList;
        this.checkHashMap = checkHashMap;
        listener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_giveback, parent, false);

        final ViewHolder holder = new ViewHolder(view);


        holder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                listener.contentClick(position);
                LogUtil.e(TAG,"giveBackAssetsList.toString() =" +giveBackAssetsList.toString());
                LogUtil.e(TAG,"checkHashMap.toString() =" +checkHashMap.toString());

            }
        });

        holder.etCheckNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                listener.contentClick(position);
                LogUtil.e(TAG,"giveBackAssetsList.toString() =" +giveBackAssetsList.toString());
                LogUtil.e(TAG,"checkHashMap.toString() =" +checkHashMap.toString());
            }
        });

        LogUtil.e(TAG,"onCreateViewHolder giveBackAssetsList = " + giveBackAssetsList.toString());


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        GiveBackAssets gba = giveBackAssetsList.get(position);

        LogUtil.e(TAG,"gba.toString()" + gba.toString());

        String name = gba.getName();
        String code = gba.getAssetCode();
        String model = gba.getModel();

        //todo
        String num = gba.getReturnNum();

        holder.tvName.setText(name);
        holder.tvCode.setText(code);
        holder.tModel.setText(model);

        //todo
        holder.etCheckNumber.setText(num);

        if(!StringHelper2.isEmpty(gba.getReturnCheck())&&gba.getReturnCheck().equals("true")){
            holder.cbCheck.setChecked(true);
        }
        else{
            holder.cbCheck.setChecked(false);
            gba.setReturnCheck("false");
        }




    }

//    public static void setText(Context mContext,TextView tv , String  content){
////      String text = String.format("￥%1$s  门市价:￥%2$s", 18.6, 22);//<color name="amaranth">#E72F58</color><color name="paco">#524438</color>
//        String text = String.format(content);
//        int BillBefore = text.indexOf("单号:");
//        int storageBefore = text.indexOf("仓库:");
//
//        SpannableStringBuilder style = new SpannableStringBuilder(text);
//        style.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(mContext,18)), 0, storageBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
//        style.setSpan(new ForegroundColorSpan(Color.parseColor("#2c2c2c")), 0, storageBefore, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
//
//        style.setSpan(new ForegroundColorSpan(Color.parseColor("#878787")), storageBefore, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
//
//        tv.setText(style);
//    }


    @Override
    public int getItemCount() {
        LogUtil.e(TAG,"giveBackAssetsList.size() = "  +giveBackAssetsList.size());
        return giveBackAssetsList.size();
    }

   OnContentClickListener listener;
    public interface OnContentClickListener{
        void contentClick(int position);
    }




}
