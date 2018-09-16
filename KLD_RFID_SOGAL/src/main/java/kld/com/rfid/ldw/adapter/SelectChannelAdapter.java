package kld.com.rfid.ldw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldw.xyz.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import kld.com.rfid.ldw.R;


/**
 * Created by LDW10000000 on 27/11/2017.
 */

public class SelectChannelAdapter extends RecyclerView.Adapter<SelectChannelAdapter.ViewHolder> {

    private Context mContext;
    private List<String> channelList = new ArrayList<>();
    private String TAG = "SelectChannelAdapter";

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

    public SelectChannelAdapter(List<String> list , OnContentClickListener l) {
        channelList = list;
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

                holder.name.setBackgroundColor(Color.YELLOW);
                int position = holder.getAdapterPosition();
                listener.contentClick(position);

            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = channelList.get(position);
        holder.name.setText(name);
//
        LogUtil.e(TAG, "stock = " + name);


    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    OnContentClickListener listener;
    public interface OnContentClickListener{
        void contentClick(int position);
    }



}
