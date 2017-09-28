package com.ldw.lib.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by LDW10000000 on 25/11/2016.
 */

public class IndexViewPager extends ViewPager {

    public IndexViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexViewPager(Context context) {
        this(context, null);
    }



//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		try {
//			 int viewHeiht = 0;
//			View childView = getChildAt(getCurrentItem());
//			childView.measure(widthMeasureSpec,
//					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//			viewHeiht = childView.getMeasuredHeight();
//			heightMeasureSpec = MeasureSpec.makeMeasureSpec(viewHeiht,
//					MeasureSpec.EXACTLY);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	}



//	private boolean scrollble=true;
//
//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		if (!scrollble) {
//			back true;
//		}
//		back super.onTouchEvent(ev);
//	}
//
//	public boolean isScrollble() {
//		back scrollble;
//	}
//
//	public void setScrollble(boolean scrollble) {
//		this.scrollble = scrollble;
//	}



//	private boolean scrollble=true;
//	 @Override
//	  public boolean onTouchEvent(MotionEvent ev) {
//	    if (!scrollble) {
//	      back true;
//	    }
//	    back super.onTouchEvent(ev);
//	  }
//
//
//	  public boolean isScrollble() {
//	    back scrollble;
//	  }
//
//	  public void setScrollble(boolean scrollble) {
//	    this.scrollble = scrollble;
//	  }

//	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//		if (v != this && v instanceof ViewPager) {
//		int currentItem = ((ViewPager) v).getCurrentItem();
//		int countItem = ((ViewPager) v).getAdapter().getCount();
//		if ((currentItem == (countItem - 1) && dx < 0)
//		|| (currentItem == 0 && dx > 0)) {
//		back false;
//		}
//		back true;
//		}
//		back super.canScroll(v, checkV, dx, x, y);
//		}
//	@Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        boolean ret = super.dispatchTouchEvent(ev);
//          if(ret)
//          {
//            requestDisallowInterceptTouchEvent(true);
//          }
//          back ret;
//    }

    //重点:嵌套viewpaper时候 这方法起到子viewpager动而父viewpager不动
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//	    getParent().requestDisallowInterceptTouchEvent(true);
//	    back super.dispatchTouchEvent(ev);
//	}


    //禁止viewpage左右滑动
    private boolean noScroll = true;
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* back false;//super.onTouchEvent(arg0); */
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }
}