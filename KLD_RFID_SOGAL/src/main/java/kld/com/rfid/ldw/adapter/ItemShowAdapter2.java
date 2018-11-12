package kld.com.rfid.ldw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kld.com.rfid.ldw.Const;
import kld.com.rfid.ldw.R;


/**
 * Created by LDW10000000 on 27/11/2017.
 */

public class ItemShowAdapter2 extends RecyclerView.Adapter<ItemShowAdapter2.ViewHolder> {

    private Context mContext;
    List<Map<String, String>>  list = new ArrayList<>();
    private String TAG = "ItemShowAdapter2";
    String[] Coname = Const.Coname;


    public void setColor(int color) {
        cr=color;
    }
    private int cr;
    int[] colors = {cr, Color.rgb(219, 238, 244) };//RGB颜色

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_readsort;
        TextView textView_readepc;
//        TextView textView_readcnt;

        public ViewHolder(View view) {
            super(view);
            textView_readsort = (TextView) view.findViewById(R.id.textView_readsort);
            textView_readepc = (TextView) view.findViewById(R.id.textView_readepc);
//            textView_readcnt = (TextView) view.findViewById(R.id.textView_readcnt);
        }
    }

    public ItemShowAdapter2(List<Map<String, String>> l) {
        list = l;
        cr=Color.WHITE;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.listitemview_inv_suofeiya, parent, false);

        final ViewHolder holder = new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String textView_readsort = list.get(position).get(Coname[0]);
        String textView_readepc = list.get(position).get(Coname[1]);
//        String textView_readcnt = list.get(position).get(Coname[2]);
        holder.textView_readsort.setText(textView_readsort+"");
        holder.textView_readepc.setText(textView_readepc+"");
//        holder.textView_readcnt.setText(textView_readsort);

//        LogUtil.e(TAG,"------------------------");
//        LogUtil.e(TAG,"position = " + position);
//
//        LogUtil.e(TAG,"------------------------");
//        LogUtil.e(TAG,"list.get(position) = " + list.get(position));
//
//        LogUtil.e(TAG,"------------------------");
//        LogUtil.e(TAG,"textView_readsort = " + textView_readsort);
//        LogUtil.e(TAG,"------------------------");
//        LogUtil.e(TAG,"textView_readepc = " + textView_readepc);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
