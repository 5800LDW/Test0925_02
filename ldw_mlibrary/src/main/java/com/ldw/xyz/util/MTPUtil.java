package com.ldw.xyz.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

/**
 * Created by LDW10000000 on 28/07/2017.
 */

public class MTPUtil {


    /**
     * 强制刷新MTP的单个文件
     *
     * @param context
     * @param path 具体文件的绝对路径,含文件名;
     */
    public static void updateMTPOneFile(Context context, String path){
        MediaScannerConnection.scanFile(context,new String[]{path},null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String p, Uri uri) {
//                ToastUtil.showToast(mContext,p+"  \n"+uri);
            }
        });
    }


}
