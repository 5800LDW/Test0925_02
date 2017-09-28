package com.ldw.xyz.util.camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by LDW10000000 on 17/08/2017.
 */

public class CameraUtil {


    /**
     * 拍照
     *
     * @param activity
     * @param authorities fileProvider 的 authorities; 多个authorities的方法: 自己继承FileProvider , 然后在清单配置文件里 写自己的authorities
     * @param filePath   文件的路径,不含文件名,
     * @param fileName   文件名,要包含后缀;
     * @param CAMERA_WITH_DATA   拍照后的数据返回标记, 要在startActivityForResult根据标记处理数据
     * @throws NoSDCardException  如果没有sd卡,或sd卡不可读写,会返回这个自定义的错误;

     * @return 返回的拍照的图片的地址就在这个file里面;
     */
    public static File takePhotos(Activity activity,
                                  @Nullable String authorities,
                                  String filePath,
                                  String fileName,
                                  int CAMERA_WITH_DATA) throws NoSDCardException {

        File mCurrentPhotoFile;
        File PHOTO_DIR = new File(filePath);
        String status = Environment.getExternalStorageState();
        //当前Android的外部存储器可读可写
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            if (!PHOTO_DIR.exists()) {
                PHOTO_DIR.mkdirs();// 创建照片的存储目录
            }
            mCurrentPhotoFile = new File(PHOTO_DIR, fileName);
            Intent intentC = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri imageUri = FileProvider.getUriForFile(activity, authorities, mCurrentPhotoFile);
                intentC.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intentC.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            } else {
                intentC.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
            }
            activity.startActivityForResult(intentC, CAMERA_WITH_DATA);
        } else {
            throw new NoSDCardException(
                    "没有存在SD卡!");
        }

        return mCurrentPhotoFile;

    }


    static class NoSDCardException extends RuntimeException {
        public NoSDCardException(String msg) {
            super(msg);
        }
    }

}
