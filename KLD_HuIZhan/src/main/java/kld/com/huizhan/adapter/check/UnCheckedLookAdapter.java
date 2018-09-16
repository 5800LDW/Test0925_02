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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ldw.xyz.util.ResHelper;
import com.ldw.xyz.util.device.ScreenUtil;

import java.util.List;

import kld.com.huizhan.R;
import kld.com.huizhan.bean.local.AssetsCheck;

/**
 * Created by LDW10000000 on 29/11/2017.
 */

public class UnCheckedLookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //普通布局的type
    static final int TYPE_ITEM = 0;
    //脚布局
    static final int TYPE_FOOTER = 1;
    //正在加载更多
    static final int LOADING_MORE = 1;
    //没有更多
    static final int NO_MORE = 2;
    //脚布局当前的状态,默认为没有更多
    int footer_state = 1;


    private Context mContext;
    List<AssetsCheck> list;
    private String TAG = "CheckedLookAdapter";

    /**
     * 正常布局的ViewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }


    /**
     * 脚布局的ViewHolder
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;
        private TextView tv_state;
        private TextView tv_line1;
        private TextView tv_line2;


        public FootViewHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            tv_state = (TextView) itemView.findViewById(R.id.foot_view_item_tv);
            tv_line1 = (TextView) itemView.findViewById(R.id.tv_line1);
            tv_line2 = (TextView) itemView.findViewById(R.id.tv_line2);

        }
    }

    public UnCheckedLookAdapter(List<AssetsCheck> list, OnContentClickListener l) {
        this.list = list;
        listener = l;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }

        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_putin_lookup, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    listener.contentClick(position);
                }
            });

            return holder;
        } else if (viewType == TYPE_FOOTER) {
            //脚布局
            View view = View.inflate(mContext, R.layout.item_recycleview_foot, null);
            FootViewHolder footViewHolder = new FootViewHolder(view);
            return footViewHolder;
        }
        return null;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            // TODO: 13/12/2017  正常item的业务逻辑

            AssetsCheck assets = list.get(position);
            String name = assets.getName();
            String asstCode = assets.getCode();
            String model = assets.getModel();
            String physicalNum = assets.getNumber1();
//            String actualNum = assets.getActualNum();
            String actualNum = "";


            //显示界面
            String str = String.format(ResHelper.getString(mContext, R.string.text_check_item), name,
                    asstCode, model, physicalNum, actualNum);

            setText(mContext, ((MyViewHolder) holder).content, str);

        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            if (position == 0) {//如果第一个就是脚布局,,那就让他隐藏
                footViewHolder.mProgressBar.setVisibility(View.GONE);
                footViewHolder.tv_line1.setVisibility(View.GONE);
                footViewHolder.tv_line2.setVisibility(View.GONE);
                footViewHolder.tv_state.setText("");
            }
            switch (footer_state) {//根据状态来让脚布局发生改变
//                case PULL_LOAD_MORE://上拉加载
//                    footViewHolder.mProgressBar.setVisibility(View.GONE);
//                    footViewHolder.tv_state.setText("上拉加载更多");
//                    break;
                case LOADING_MORE:
                    footViewHolder.mProgressBar.setVisibility(View.VISIBLE);
                    footViewHolder.tv_line1.setVisibility(View.GONE);
                    footViewHolder.tv_line2.setVisibility(View.GONE);
                    footViewHolder.tv_state.setText("正在加载...");
                    break;
                case NO_MORE:
                    footViewHolder.mProgressBar.setVisibility(View.GONE);
                    footViewHolder.tv_line1.setVisibility(View.VISIBLE);
                    footViewHolder.tv_line2.setVisibility(View.VISIBLE);
                    footViewHolder.tv_state.setText("所有数据加载完成");
                    footViewHolder.tv_state.setTextColor(Color.parseColor("#528DCC"));
                    break;
            }
        }


    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
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

    @Override
    public int getItemViewType(int position) {
        //如果position加1正好等于所有item的总和,说明是最后一个item,将它设置为脚布局
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * 改变脚布局的状态的方法,在activity根据请求数据的状态来改变这个状态
     *
     * @param state
     */
    public void changeState(int state) {
        this.footer_state = state;
        notifyDataSetChanged();
    }


}