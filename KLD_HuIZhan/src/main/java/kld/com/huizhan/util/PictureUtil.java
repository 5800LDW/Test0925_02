package kld.com.huizhan.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by LDW10000000 on 20/11/2017.
 */

public class PictureUtil {

    public static void  load(Context context,int id , ImageView iv){
        Glide.with(context).load(id).centerCrop().into(iv);
    }





}
