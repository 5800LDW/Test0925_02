package kld.com.huizhan.test;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

import com.ldw.xyz.util.LogUtil;

/**
 * simple and powerful Keyboard show/hidden listener,view {@android.R.id.content} and {@ViewTreeObserver.OnGlobalLayoutListener}
 * Created by yes.cpu@gmail.com 2016/7/13.
 */
public class KeyboardChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "ListenerHandler";
    private View mContentView;
    private int mOriginHeight;
    private int mPreHeight;
    private KeyBoardListener mKeyBoardListen;

    public interface KeyBoardListener {
        /**
         * call back
         * @param isShow true is show else hidden
         * @param keyboardHeight keyboard height
         */
        void onKeyboardChange(boolean isShow, int keyboardHeight);
    }

    public void setKeyBoardListener(KeyBoardListener keyBoardListen) {
        this.mKeyBoardListen = keyBoardListen;
    }

    public KeyboardChangeListener(Activity contextObj) {
        if (contextObj == null) {
            LogUtil.e("TAG", "contextObj is null");
            return;
        }
        mContentView = findContentView(contextObj);
        if (mContentView != null) {
            addContentTreeObserver();
        }
    }

    private View findContentView(Activity contextObj) {
        return contextObj.findViewById(android.R.id.content);
    }

    private void addContentTreeObserver() {
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        LogUtil.e("TAG","==============================");
        //todo
        Rect r = new Rect();
        mContentView.getWindowVisibleDisplayFrame(r);
//        int screenHeight = mContentView.getHeight();
        LogUtil.e("TAG","r.bottom =" +r.bottom);
        LogUtil.e("TAG","r.top = " + r.top);



        int currHeight = r.bottom - r.top;
        //todo

//        int currHeight = mContentView.getHeight();//初始高度

        LogUtil.e("TAG","currHeight = " + currHeight);

        //主要是测量为零的时候， 就是测量高度失败了，这里不用改；
        if (currHeight == 0) {
            LogUtil.e("TAG","currHeight is 0");
            return;
        }
        if(mOriginHeight==0){
            mOriginHeight = mContentView.getHeight()-r.top;
        }

        boolean hasChange = false;
        if (mPreHeight == 0) {
            mPreHeight = currHeight;
//            mOriginHeight = currHeight;
            LogUtil.e("TAG","mPreHeight == 0 ------>>>>>>" + mPreHeight);
            LogUtil.e("TAG","mOriginHeight ------>>>>>>" + mOriginHeight);

        } else {
            LogUtil.e("TAG","mPreHeight ------>>>>>>" + mPreHeight);
            LogUtil.e("TAG","currHeight ------>>>>>>" + currHeight);
            if (mPreHeight != currHeight) {//说明布局变化了，
                hasChange = true;
                mPreHeight = currHeight;
            } else {//又变化了，这里默认回到了初始状态
                hasChange = false;
            }
        }


        LogUtil.e("TAG","hasChange =" + hasChange);
        if (hasChange) {//弹出状态

            boolean isShow;
            int keyboardHeight = 0;
            if (mOriginHeight == currHeight) {//mOriginHeight，初始高度
                //hidden
                isShow = false;
            } else {
                //show
                keyboardHeight = mOriginHeight - currHeight;//键盘高度
                isShow = true;
            }
            LogUtil.e("TAG","我》》》》》》》。" );


            if (mKeyBoardListen != null) {
                LogUtil.e("TAG","mKeyBoardListen 被调用了" );
                mKeyBoardListen.onKeyboardChange(isShow, keyboardHeight);
            }

        }
    }

    /**
     * 注销
     */
    public void destroy() {
        if (mContentView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        }
    }
}
