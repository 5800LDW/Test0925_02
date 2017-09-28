package com.ldw.xyz.util.picture;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import com.ldw.xyz.util.file.MyFileUtil;

import java.io.File;

/**
 * Created by LDW10000000 on 17/08/2017.
 */

public class PictureUtil {


    /**
     * 裁剪
     *
     * @param originalPhotoFile  原图的绝对路径的file
     * @param activity
     * @param authorities fileProvider 的 authorities; 多个authorities的方法: 自己继承FileProvider , 然后在清单配置文件里 写自己的authorities
     * @param filePath  文件的路径,不含文件名,
     * @param fileName  文件名,要包含后缀;
     * @param CROP_PHOTO  拍照后的数据返回标记, 要在startActivityForResult根据标记处理数据
     * @return 返回的裁剪过的图片的地址就在这个file里面,然后,你在imageview里调用 iv.setImageURI(Uri.parse(file.getAbsolutePath())), 就可以加载图片了;
     */
    public static File  tailor(File originalPhotoFile,
                               Activity activity,
                               @Nullable String authorities,
                               String filePath,
                               String fileName,
                               int CROP_PHOTO){

        Uri uri = Uri.fromFile(originalPhotoFile);

        File PHOTO_DIR = new File(filePath);
        if (!PHOTO_DIR.exists()) {
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
        }
        File mCacheFile = new File(PHOTO_DIR, fileName);
        Uri outputUri = Uri.fromFile(new File(mCacheFile.getPath()));
        String url = MyFileUtil.getPath(activity, uri);

        Intent intent = new Intent("com.android.camera.action.CROP");
        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri imageUri = FileProvider.getUriForFile(activity, authorities, new File(url));//通过FileProvider创建一个content类型的Uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra("noFaceDetection", true);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.setDataAndType(imageUri, "image/*");
            //19=<sdk<24
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            //sdk<19
        } else {
            intent.setDataAndType(uri, "image/*");
        }
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 19);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 20);// x:y=1:2
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 500);
        intent.putExtra("output", outputUri);
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        activity.startActivityForResult(intent, CROP_PHOTO);

        return  mCacheFile;
    }


    /**
     * 打开相册选择图片
     *  在activityOnResult 里面作如下处理
     *    Uri uri = data.getData();
     *    String selectedImagePath = FileUtils.getPath(activity, uri);
     *    这个selectedImagePath 就是选择的图片的绝对路径
     *
     * @param activity
     * @param PHOTO_PICKED_WITH_DATA  在activityOnResult里面要处理的常量标志
     */
    public static void selectPicutreFromAlbum(Activity activity,int PHOTO_PICKED_WITH_DATA){
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        activity.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
    }







}
