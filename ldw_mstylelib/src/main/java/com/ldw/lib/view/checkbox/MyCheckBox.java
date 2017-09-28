package com.ldw.lib.view.checkbox;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by LDW10000000 on 22/02/2017.
 */

public class MyCheckBox extends CheckBox {
    public MyCheckBox(Context context) {
        super(context);
    }

    public MyCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static  boolean isIgnore = false;

    public interface onCheckBoxEventListener{
        void doEvent(CompoundButton buttonView, boolean isChecked);
    }

    /**
     * 解决多个checkbox 进行 setCheck(boolean arg0) 会触发SetOnCheckChangeListener的问题;
     *
     * @param lisenter
     */
    public void mySetOnCheckChangeListener(final onCheckBoxEventListener lisenter){
        this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isIgnore){
                    isIgnore = true;

                    lisenter.doEvent( buttonView, isChecked);

                    isIgnore = false;
                }

            }
        });
    }

}