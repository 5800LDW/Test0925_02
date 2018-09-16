package kld.com.huizhan.view;

/**
 * Created by liudongwen on 2018/7/31.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 *  拦截键盘向下的 EditTextView
 */
public class MyTextInputEditText extends android.support.design.widget.TextInputEditText {
    public MyTextInputEditText(Context context) {
        super(context);
    }

    public MyTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            super.onKeyPreIme(keyCode, event);
            onKeyBoardHideListener.onKeyHide(keyCode, event);
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    /**
     *键盘监听接口
     */
    OnKeyBoardHideListener onKeyBoardHideListener;
    public void setOnKeyBoardHideListener(OnKeyBoardHideListener onKeyBoardHideListener) {
        this.onKeyBoardHideListener = onKeyBoardHideListener;
    }

    public interface OnKeyBoardHideListener{
        void onKeyHide(int keyCode, KeyEvent event);
    }
}

