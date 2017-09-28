package com.ldw.lib.biz.photo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.ldw.xyz.activity.BaseActivity;
import com.ldw.xyz.util.camera.CameraUtil;
import com.ldw.xyz.util.file.MyFileUtil;
import com.ldw.xyz.util.picture.PictureUtil;

import java.io.File;

/**
 * Created by LDW10000000 on 17/08/2017.
 *
 * 使用说明:
 *          1.继承PhotoActivity;
 *          2.改写PHOTO_DIR可以改变图片存放的文件夹;
 *          3.调用
 *          每次都要判断下是否被授予权限了
 *           RuntimePermissionUtil.test(RuntimePermissionUtil.concatAll(RuntimePermissionUtil.getCameraPermissionList(),RuntimePermissionUtil.getStoragePermissionList()
 *                   ), new PermissionListener() {
 *                       @Override
 *                       public void onGranted() {
 *                               // 进行拍照
 *                               openCamera("com.qqwl.fileprovider", "test.jpg");
 *                       }
 *
 *                      @Override
 *                       public void onDenied(List<String> deniedPermissions) {
 *                               ToastUtil.showToast(TestActvity.this, "拍照或写入sdk权限被禁止,请到应用权限进行打开!");
 *                       }
 *                   });
 *
 *
 *
 *
 */

public abstract class PhotoActivity extends BaseActivity implements doWithResultImpl {

    public static final int PHOTO_PICKED_WITH_DATA = 1001;//相册
    public static final int CAMERA_WITH_DATA = 1002;//相机
    public static final int CROP_PHOTO = 101;
    public static File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/CameraTest");
    public File file;

    /**
     * 调用相册进行选择图片
     *
     */
    protected void selectPicutreFromAlbum() {
        PictureUtil.selectPicutreFromAlbum(this, PHOTO_PICKED_WITH_DATA);
    }

    /**
     * 对图片进行裁剪
     *
     * @param originalPhotoFile
     * @param authorities
     * @param fileName
     */
    protected void tailor(File originalPhotoFile, @Nullable String authorities, String fileName) {
        file = PictureUtil.tailor(originalPhotoFile, this, authorities, PHOTO_DIR.toString(), fileName, CROP_PHOTO);
    }


    /**
     * 打开相机进行图片拍照
     *
     * @param authorities
     * @param fileName
     */
    protected void openCamera(@Nullable String authorities, String fileName) {
        file = CameraUtil.takePhotos(this, authorities, PHOTO_DIR.toString(), fileName, CAMERA_WITH_DATA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA://  相册返回
                Uri uri = data.getData();
                String selectedImagePath = MyFileUtil.getPath(this, uri);
                File mCurrentPhotoFile = new File(selectedImagePath);
//                dealAlbumData(Uri.fromFile(mCurrentPhotoFile));
                dealAlbumData( mCurrentPhotoFile);

                break;
            case CAMERA_WITH_DATA:// 相机返回
//                dealCameraData(Uri.fromFile(file));
                dealCameraData(file);
                break;
            case CROP_PHOTO://图片剪裁返回
//                String url = file.getAbsolutePath();
                dealTailorData(file);

                break;
        }
    }
}

interface doWithResultImpl {

    /**
     * @param file 含有图片的地址
     */
    void dealAlbumData(File file);

    /**
     * @param file 含有图片的地址
     */
    void dealCameraData(File file);

    /**
     * 这个file含有图片的地址
     *
     * 如要要在imageview显示图片就调用这个方法:
     *      if(file!=null){
     *           iv_photo.setImageURI(Uri.parse(file.getAbsolutePath()));
     *      }
     *
     * @param file 含有图片的地址
     */
    void dealTailorData(File file);

}