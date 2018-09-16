package kld.com.huizhan.view;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.device.ScreenUtil;

import kld.com.huizhan.R;


public class ListViewInPopWindow {

    public static PopupWindow showpopwindowForLogin(Activity activity, View view, OnItemClickListener onitemclick,
//											int height,
                                            ListAdapter adapter, PopupWindow popupWindow) {
        ListView lv_list = new ListView(activity);
//		lv_list.setDivider(new ColorDrawable(0x23443234));
        lv_list.setDivider(activity.getResources().getDrawable(R.drawable.group_divider));
        lv_list.setSelector(activity.getResources().getDrawable(R.drawable.grouplist_item_bg));
        lv_list.setDividerHeight(2);
        lv_list.setOnItemClickListener(onitemclick);

        // 绑定数据
        lv_list.setAdapter(adapter);

//        if (popupWindow == null) {
        popupWindow = new PopupWindow(lv_list, view.getWidth() , ViewGroup.LayoutParams.WRAP_CONTENT);
//        } else {
//			popupWindow.setHeight(height);
//        }

        // 设置点击外部可以被关闭.
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.group_bg));
        // popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 获取焦点
        popupWindow.setFocusable(true);

        popupWindow.showAsDropDown(view, 2, 1);


        return popupWindow;
    }

    public static PopupWindow showpopwindow(Activity activity, View view, OnItemClickListener onitemclick,
//											int height,
                                            ListAdapter adapter, PopupWindow popupWindow) {
        ListView lv_list = new ListView(activity);
//		lv_list.setDivider(new ColorDrawable(0x23443234));
        lv_list.setDivider(activity.getResources().getDrawable(R.drawable.group_divider));
        lv_list.setSelector(activity.getResources().getDrawable(R.drawable.grouplist_item_bg));
        lv_list.setDividerHeight(2);
        lv_list.setOnItemClickListener(onitemclick);

        // 绑定数据
        lv_list.setAdapter(adapter);

//        if (popupWindow == null) {
            popupWindow = new PopupWindow(lv_list, view.getWidth()+50 , ViewGroup.LayoutParams.WRAP_CONTENT);
//        } else {
//			popupWindow.setHeight(height);
//        }

        // 设置点击外部可以被关闭.
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.group_bg));
        // popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 获取焦点
        popupWindow.setFocusable(true);

        popupWindow.showAsDropDown(view, 2, 1);


        return popupWindow;
    }

    public static PopupWindow showpopwindow3(Activity activity, View view, OnItemClickListener onitemclick,
//											int height,
                                            ListAdapter adapter, PopupWindow popupWindow) {


//        LinearLayout ll = new LinearLayout(activity);
        ListView lv_list = new ListView(activity);
//		lv_list.setDivider(new ColorDrawable(0x23443234));
        lv_list.setDivider(activity.getResources().getDrawable(R.drawable.group_divider));
        lv_list.setSelector(activity.getResources().getDrawable(R.drawable.grouplist_item_bg));
        lv_list.setDividerHeight(2);
        lv_list.setOnItemClickListener(onitemclick);

        // 绑定数据
        lv_list.setAdapter(adapter);

//        ll.addView(lv_list);

        popupWindow = new PopupWindow(lv_list, view.getWidth() + 50, ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置点击外部可以被关闭.
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.group_bg2));
        // popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 获取焦点
        popupWindow.setFocusable(true);

//        int windowPos[] = calculatePopWindowPos(activity,view, lv_list);
//        int xOff = 20;// 可以自己调整偏移
//        windowPos[0] -= xOff;


//        int bottomButtonHight = ScreenUtil.dip2px(activity,40);

        LogUtil.e("TAG",">>>> view.getHeight() = "+view.getHeight());
        LogUtil.e("TAG",">>>> view.getMeasuredHeight() = "+view.getMeasuredHeight());
//        LogUtil.e("TAG",">>>> bottomButtonHight = "+bottomButtonHight);
        LogUtil.e("TAG",">>>> popupWindow.getHeight() = "+popupWindow.getHeight());
        LogUtil.e("TAG",">>>> ScreenUtil.getScreenHeight(activity) = "+ScreenUtil.getScreenHeight(activity));
//        popupWindow.showAsDropDown(view, 0, -(view.getHeight()));
        popupWindow.showAsDropDown(view, 0, -( view.getMeasuredHeight()*3));

//        popupWindow.showAtLocation(view, Gravity.TOP , windowPos[0], windowPos[1]);
        // windowContentViewRoot是根布局View




        return popupWindow;
    }









    public static PopupWindow showpopwindow2(Context context, View view, OnItemClickListener onitemclick, int height,
                                             ListAdapter adapter, PopupWindow popupWindow) {
        ListView lv_list = new ListView(context);
//		lv_list.setDivider(new ColorDrawable(0x23443234));
        lv_list.setDivider(context.getResources().getDrawable(R.drawable.group_divider));
        lv_list.setSelector(context.getResources().getDrawable(R.drawable.grouplist_item_bg));
        lv_list.setDividerHeight(2);
        lv_list.setOnItemClickListener(onitemclick);

        // 绑定数据
        lv_list.setAdapter(adapter);

        if (popupWindow == null) {
            popupWindow = new PopupWindow(lv_list, view.getWidth() - 4, height);
        } else {
            popupWindow.setHeight(height);
        }

        // 设置点击外部可以被关闭.
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.group_bg2));
        // popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 获取焦点
        popupWindow.setFocusable(true);

        // 显示在输入框的左下角位置
        popupWindow.showAsDropDown(view, 2, 1);

//
////        View windowContentViewRoot = 我们要设置给PopupWindow进行显示的View
//        int windowPos[] = calculatePopWindowPos(view, windowContentViewRoot);
//        int xOff = 20;// 可以自己调整偏移
//        windowPos[0] -= xOff;
//        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
//// windowContentViewRoot是根布局View





        return popupWindow;
    }


    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     * @param anchorView  呼出window的view
     * @param contentView   window的内容布局
     * @return window显示的左上角的xOff,yOff坐标
     */
    private static int[] calculatePopWindowPos(Activity activity,final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        final int screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

}
