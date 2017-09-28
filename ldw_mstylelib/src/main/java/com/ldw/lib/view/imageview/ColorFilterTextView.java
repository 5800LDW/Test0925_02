//package com.ldw.lib.view.imageview;
//
//
//import android.content.Context;
//import android.graphics.ColorMatrixColorFilter;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.widget.TextView;
//
///**
// * 颜色滤镜ImageView
// *
// * @ClassName:  ColorFilterImageView
// * @Description:  点击时显示明暗变化(滤镜效果)的ImageView
// * @author LinJ
// * @date 2015-1-6 下午2:13:46
// *
// */
//public class ColorFilterTextView extends TextView implements GestureDetector.OnGestureListener{
//
//        // 自定义的滤镜
//        //变暗(三个-50，值越大则效果越深)
//        public final float[] BT_SELECTED_DARK = new float[] { 1, 0, 0, 0, -50, 0, 1,
//                0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
//        //变亮
//        public final float[] BT_SELECTED_LIGHT = new float[] { 1, 0, 0, 0, 50, 0, 1,
//                0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };
//        //恢复
//        public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,
//                1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };
//
//    /**   监听手势*/
//    private GestureDetector mGestureDetector;
//    public ColorFilterTextView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mGestureDetector=new GestureDetector(context, this);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //在cancel里将滤镜取消，注意不要捕获cacncel事件,mGestureDetector里有对cancel的捕获操作
//        //在滑动GridView时，AbsListView会拦截掉Move和UP事件，直接给子控件返回Cancel
//        if(event.getActionMasked()== MotionEvent.ACTION_CANCEL){
//            removeFilter();
//        }
//        return mGestureDetector.onTouchEvent(event);
//    }
//
//    /**
//     *   设置滤镜
//     */
//    private void setFilter() {
//        //先获取设置的src图片
//        Drawable drawable=getBackground();
//        if(drawable!=null){
//            //设置滤镜
////            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);;
//            drawable.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED_DARK));;
//        }
//    }
//    /**
//     *   清除滤镜
//     */
//    private void removeFilter() {
//        //先获取设置的src图片
//        Drawable
//            drawable=getBackground();
//
//        if(drawable!=null){
//            //清除滤镜
//            drawable.clearColorFilter();
//        }
//    }
//
//    @Override
//    public boolean onDown(MotionEvent e) {
//        setFilter();
//        //这里必须返回true，表示捕获本次touch事件
//        return true;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        removeFilter();
//        performClick();
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//                            float distanceY) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {
//        //长安时，手动触发长安事件
//        performLongClick();
//    }
//
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//                           float velocityY) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//}
////
////import android.content.Context;
////import android.util.AttributeSet;
////import android.widget.ImageView;
////
/////**
//// * 颜色滤镜ImageView  点击会变暗
//// *
//// *
//// * Created by LDW10000000 on 06/01/2017.
//// */
////
////public class ColorFilterImageView extends ImageView {
////
////
////    public ColorFilterImageView(Context context, AttributeSet attrs) {
////        super(context, attrs);
////        // TODO Auto-generated constructor stub
////        this.setOnTouchListener(VIEW_TOUCH_DARK);
////    }
////
////    public static final OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {
////
////        //变暗(三个-50，值越大则效果越深)
////        public final float[] BT_SELECTED_DARK = new float[] { 1, 0, 0, 0, -50, 0, 1,
////                0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
////        /*
////        //变亮
////        public final float[] BT_SELECTED_LIGHT = new float[] { 1, 0, 0, 0, 50, 0, 1,
////                0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };
////
////        //恢复
////        public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,
////                1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };
////         */
////        @Override
////        public boolean onTouch(View v, MotionEvent event) {
////            if (event.getAction() == MotionEvent.ACTION_DOWN) {
////                ImageView iv = (ImageView) v;
////                iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED_DARK));
////            } else if (event.getAction() == MotionEvent.ACTION_UP) {
////                ImageView iv = (ImageView) v;
////                iv.clearColorFilter();
////            }
////            return true;  //如为false，执行ACTION_DOWN后不再往下执行
////        }
////    };
////
////
////
////}
