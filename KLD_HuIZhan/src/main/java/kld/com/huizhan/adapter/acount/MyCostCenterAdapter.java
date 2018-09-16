package kld.com.huizhan.adapter.acount;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ldw.xyz.util.ResHelper;

import java.util.List;

import kld.com.huizhan.R;


/**
 * Created by LDW10000000 on 01/12/2016.
 */

public class MyCostCenterAdapter extends BaseAdapter {

    private List<String> mCostList;
    private Context mContext;

    public MyCostCenterAdapter(Context mContext, List<String> lv) {
        super();
        this.mCostList = lv;
        this.mContext=mContext;
    }
    public MyCostCenterAdapter(Context mContext, List<String> lv, OnTextStyleShowListener listener) {
        super();
        this.mCostList = lv;
        this.mContext=mContext;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mCostList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if(convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_cost_center, null);
            mHolder = new ViewHolder();
            mHolder.tvNumber = (TextView) convertView.findViewById(R.id.tv_number_item);
            convertView.setTag(mHolder);
        } else {
            // 取出holder类
            mHolder = (ViewHolder) convertView.getTag();
        }

        // 给mHolder类中的对象赋值.
        if(mListener!=null){
            mListener.textStyleShow(position);
        }
        else{
            mHolder.tvNumber.setTextColor(ResHelper.getColor(mContext, R.color.white));
            mHolder.tvNumber.setText(mCostList.get(position));
        }



        return convertView;
    }

    class ViewHolder {
        public TextView tvNumber;
    }


    private OnTextStyleShowListener mListener = null;
    public  interface OnTextStyleShowListener{
         void textStyleShow(int position);
    }


}

///**
// * Created by LDW10000000 on 01/12/2016.
// */
//
//public class MyCostCenterAdapter extends BaseAdapter {
//
//    private List<String> mCostList;
//    private Context mContext;
//
//    public MyCostCenterAdapter(Context mContext,List<String> lv) {
//        super();
//        this.mCostList = lv;
//        this.mContext=mContext;
//    }
//
//    @Override
//    public int getCount() {
//        back mCostList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        // TODO Auto-generated method stub
//        back null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        // TODO Auto-generated method stub
//        back 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder mHolder = null;
//        if(convertView == null) {
//            convertView = View.inflate(mContext, R.layout.item_cost_center, null);
//            mHolder = new ViewHolder();
//            mHolder.tv_Code = (TextView) convertView.findViewById(R.id.tv_Code);
//            mHolder.tv_Remove = (TextView) convertView.findViewById(R.id.tv_Remove);
//            convertView.setTag(mHolder);
//        } else {
//            // 取出holder类
//            mHolder = (ViewHolder) convertView.getTag();
//        }
//
//        if(position<mCostList.size()){
//            mHolder.tv_Code.setText(mCostList.get(position));
//        }
//
//        back convertView;
//    }
//
//    class ViewHolder {
//        public TextView tv_Code;
//        public TextView tv_Remove;
//    }
//
//
//}