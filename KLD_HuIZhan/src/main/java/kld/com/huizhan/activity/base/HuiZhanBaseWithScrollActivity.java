package kld.com.huizhan.activity.base;

import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.view.CHScrollView;

/**
 * Created by LDW10000000 on 02/12/2017.
 */

public class HuiZhanBaseWithScrollActivity extends HuiZhanBaseWithSoundActivity{
    // 方便测试，直接写的public
    public HorizontalScrollView mTouchView;
    //装入所有的HScrollView
    public List<CHScrollView> mHScrollViews =new ArrayList<CHScrollView>();
    public void onScrollChanged(int l, int t, int oldl, int oldt){
        for(CHScrollView scrollView : mHScrollViews) {
            //防止重复滑动
            if(mTouchView != scrollView)
                scrollView.smoothScrollTo(l, t);
        }
    }

}
