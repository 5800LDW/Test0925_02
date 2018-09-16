package kld.com.huizhan.util;

import android.widget.CheckBox;

/**
 * Created by LDW10000000 on 29/11/2017.
 */

public class CBCheckUtil {

    public static void singleCBisCheck(CheckBox cb,CheckBox[] cbViews){
        for(int i =0;i<cbViews.length;i++){
            cbViews[i].setChecked(false);
        }
        cb.setChecked(true);
    }


}
