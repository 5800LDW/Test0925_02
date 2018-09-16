package kld.com.huizhan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldw.xyz.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.bean.download.stock.Stock;

/**
 * Created by LDW10000000 on 27/11/2017.
 */

public class SelectStorageAdapter extends RecyclerView.Adapter<SelectStorageAdapter.ViewHolder> {

    private Context mContext;
    private List<Stock> mStockList = new ArrayList<>();
    private String TAG = "SelectStorageAdapter";

//    HashSet<Integer> selectItem;

    static class ViewHolder extends RecyclerView.ViewHolder {
        //        LinearLayout ll_Background;
        TextView name;
//        View v_Line;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv);
        }
    }

    public SelectStorageAdapter(List<Stock> list , OnContentClickListener l) {
        mStockList = list;
        listener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_storage_select, parent, false);

        final ViewHolder holder = new ViewHolder(view);


        holder.name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                listener.contentClick(position);

//                selectItem.clear();
//                selectItem.add(position);


            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stock stock = mStockList.get(position);
        holder.name.setText(stock.getStorageName());

        LogUtil.e(TAG, "stock = " + stock.toString());

//        if (selectItem != null && selectItem.contains(position)) {
//            holder.ll_Background.setSelected(true);
//        } else {
//            holder.ll_Background.setSelected(false);
//        }

//        if(position == 0){
//            holder.v_Line.setVisibility(View.GONE);
//        }
//        if(selectItem.contains(position)){
//            holder.v_Line.setVisibility(View.GONE);
//        }


    }

    @Override
    public int getItemCount() {
        return mStockList.size();
    }

    OnContentClickListener listener;
    public interface OnContentClickListener{
        void contentClick(int position);
    }



}
