package com.ldw.lib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyToolBar extends RelativeLayout {

    /**
     * 标题栏的三个控件
     */
    public TextView leftBtn, rightBtn;
    public TextView title;

    /**
     * 左边按钮的属性
     */
    private int leftTextColor;
    private Drawable leftBackground;
    private String leftText;

    /**
     * 右边按钮的属性
     */
    private int rightTextColor;
    private Drawable rightBackground;
    private String rightText;

    /**
     * 中间TextView的属性
     */
    public int titleTextColor;
    public String titleText;
    public float titleTextSize;

    /**
     * 三个控件的布局参数
     */
    public LayoutParams leftParams, rightParams, titleParams;
    private View view;

    public MyToolBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @SuppressLint("NewApi")
    public MyToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyToolBar);

        leftTextColor = ta.getColor(R.styleable.MyToolBar_leftTextColor, 0);
        leftBackground = ta.getDrawable(R.styleable.MyToolBar_leftBackground);
        leftText = ta.getString(R.styleable.MyToolBar_leftText);

        rightTextColor = ta.getColor(R.styleable.MyToolBar_rightTextColor, 0);
        rightBackground = ta.getDrawable(R.styleable.MyToolBar_rightBackground);
        rightText = ta.getString(R.styleable.MyToolBar_rightText);

        titleText = ta.getString(R.styleable.MyToolBar_title);
        titleTextColor = ta.getColor(R.styleable.MyToolBar_titleTextColor, 0);
        titleTextSize = ta.getDimension(R.styleable.MyToolBar_titleTextSize, 0);

        // 对ta进行回收
        ta.recycle();

        leftBtn = new TextView(context);
        rightBtn = new TextView(context);
        title = new TextView(context);

        /**
         * 设置属性
         */
		leftBtn.setText(leftText);
		leftBtn.setTextColor(leftTextColor);
        leftBtn.setBackground(leftBackground);
//		leftBtn.setBackgroundResource(R.drawable.icon_point);back.png

//		leftBtn.setScaleType(ScaleType.FIT_END);
        leftBtn.setPadding(20, 20, 0, 5);
        //begin
        leftBtn.setVisibility(View.VISIBLE);
        //end


		rightBtn.setText(rightText);
		rightBtn.setTextColor(rightTextColor);
        rightBtn.setBackground(rightBackground);
        //begin
        rightBtn.setVisibility(View.VISIBLE);
        //end


        title.setText(titleText);
        title.setTextColor(titleTextColor);
        title.setTextSize(titleTextSize);
        title.setGravity(Gravity.CENTER);

        // 设置整体背景颜色
//        setBackgroundColor(0x0000055);

        leftParams = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        // 添加控件
        addView(leftBtn, leftParams);

        rightParams = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightBtn, rightParams);

        titleParams = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(title, titleParams);

        leftBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });
        rightBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });


        //begin
        view = new View(context);
//		view.setBackgroundColor(context.getResources().getColor(R.color.home_background));
        LayoutParams lineParams = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
                2);
        lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, TRUE);

        addView(view, lineParams);
        //end
    }

    private OnToolBarClickListener listener;

    public interface OnToolBarClickListener {
        /**
         * 左边按钮点击事件
         */
        public void leftClick();

        /**
         * 右边按钮点击事件
         */
        public void rightClick();
    }

    public void setOnToolBarClickListener(OnToolBarClickListener listener) {
        this.listener = listener;
    }

    public void setToolBarBackgroundColor(Context context, int color) {
        view.setBackgroundColor(context.getResources().getColor(color));
    }

    public void setTitle(String str) {
        title.setText(str);
    }

    public void setLeftBtnBackground(Context context, int img) {
        Resources res = context.getResources();
        Drawable myImage = res.getDrawable(R.drawable.back);
        leftBtn.setCompoundDrawablesWithIntrinsicBounds(myImage, null, null, null);
//		leftBtn.setBackgroundResource(img);
    }

    public void setLeftBtnText(String leftBtnText) {
        leftBtn.setText(leftBtnText);
    }

    public void setRightBtnBackground(int img) {
        rightBtn.setBackgroundResource(img);
    }

    public void setRightBtnText(String rightBtnText) {
        rightBtn.setText(rightBtnText);
    }


}
