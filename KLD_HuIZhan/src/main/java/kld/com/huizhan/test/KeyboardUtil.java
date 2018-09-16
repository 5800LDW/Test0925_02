package kld.com.huizhan.test;

/**
 * Created by liudongwen on 2018/7/31.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ZhangQixiang on 2017/7/21.
 */

public class KeyboardUtil {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void listenKeyboard(Activity activity, KeyBoardListener listener) {
        KeyboardHandler kbHandler = new KeyboardHandler(activity);
        kbHandler.setKeyBoardListener(listener);
    }


    public interface KeyBoardListener {
        /**
         * @param isShow         键盘弹起
         * @param keyboardHeight 键盘高度
         */
        void onKeyboardChange(boolean isShow, int keyboardHeight);
    }

    private static class KeyboardHandler implements ViewTreeObserver.OnGlobalLayoutListener {

        private View             mContentView;
        private int              mOriginHeight;
        private int              mPreHeight;
        private KeyBoardListener mKeyBoardListener;

        KeyboardHandler(Activity activity) {
            if (activity == null) return;
            mContentView = activity.findViewById(android.R.id.content);
            if (mContentView == null) return;
            mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);

        }

        void setKeyBoardListener(KeyBoardListener keyBoardListen) {
            this.mKeyBoardListener = keyBoardListen;
        }

        @Override
        public void onGlobalLayout() {
            int currHeight = mContentView.getHeight();
            Log.d("debug", "currHeight: " + currHeight);
            if (currHeight == 0) return;

            boolean hasChange = false;
            if (mPreHeight == 0) {
                mPreHeight = currHeight;
                mOriginHeight = currHeight;
                Log.d("debug", "mPreHeight: "+mPreHeight);
            } else {
                if (mPreHeight != currHeight) {
                    hasChange = true;
                    mPreHeight = currHeight;
                } else {
                    hasChange = false;
                }
            }
            if (hasChange) {
                boolean isShow;
                int keyboardHeight = 0;
                if (mOriginHeight == currHeight) {
                    //hidden
                    isShow = false;
                } else {
                    //show
                    keyboardHeight = mOriginHeight - currHeight;
                    isShow = true;
                }

                if (mKeyBoardListener != null) {
                    mKeyBoardListener.onKeyboardChange(isShow, keyboardHeight);
                }
            }
        }

    }


}