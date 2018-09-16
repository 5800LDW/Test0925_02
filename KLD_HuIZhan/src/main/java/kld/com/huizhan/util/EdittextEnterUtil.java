package kld.com.huizhan.util;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by LDW10000000 on 29/11/2017.
 */

public class EdittextEnterUtil {

    private static String TAG = "EdittextEnterUtil";

    public interface OnEnterListener{
        void event();
    }

    public static void setListener(EditText et,OnEnterListener listener){
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE
                        /** 这里做些特殊处理, 让光标往下走也要 执行下面的代码 */
                        || actionId == EditorInfo.IME_ACTION_NEXT
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                        && KeyEvent.ACTION_DOWN == event.getAction())) {
                    // 处理事件
                    listener.event();

//                    LogUtil.e(TAG, "Enter");
//                    String code = etCode.getText().toString().trim();
//                    searchCode(code);
                }
                return false;
            }
        });

    }



}
