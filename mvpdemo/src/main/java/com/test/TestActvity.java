package com.test;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.antonioleiva.mvpexample.app.R;
import com.ldw.lib.biz.photo.PhotoActivity;
import com.ldw.xyz.listener.PermissionListener;
import com.ldw.xyz.util.RuntimePermissionUtil;
import com.ldw.xyz.util.ToastUtil;

import java.io.File;
import java.util.List;

/**
 * Created by LDW10000000 on 17/08/2017.
 */

public class TestActvity extends PhotoActivity implements View.OnClickListener {
    ImageView iv_photo;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test);


    }

    @Override
    public void findViews() {
        findViewById(R.id.bt_takePhoto).setOnClickListener(this);
        findViewById(R.id.bt_openAlbum).setOnClickListener(this);

        iv_photo = (ImageView) findViewById(R.id.iv_photo);
    }

    @Override
    public void getData() {
    }

    @Override
    public void showConent() {
    }


    @Override
    public void dealAlbumData(File file) {
        if(file!=null){
//            iv_photo.setImageURI(Uri.parse(file.getAbsolutePath()));
            tailor(file,"com.qqwl.fileprovider","album.jpg");
        }

    }

    @Override
    public void dealCameraData(File file) {
        if(file!=null){
//            iv_photo.setImageURI(Uri.parse(file.getAbsolutePath()));
            tailor(file,"com.qqwl.fileprovider","camera.jpg");
        }

    }

    @Override
    public void dealTailorData(File file) {
        if(file!=null){
            iv_photo.setImageURI(Uri.parse(file.getAbsolutePath()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_takePhoto:

                RuntimePermissionUtil.test(
                        RuntimePermissionUtil.concatAll(
                                RuntimePermissionUtil.getCameraPermissionList(),
                                RuntimePermissionUtil.getStoragePermissionList()
                        ), new PermissionListener() {
                            @Override
                            public void onGranted() {
                                openCamera("com.qqwl.fileprovider", "test.jpg");
                            }

                            @Override
                            public void onDenied(List<String> deniedPermissions) {
                                ToastUtil.showToast(TestActvity.this, "拍照或写入sdk权限被禁止,请到应用权限进行打开!");
                            }
                        });


                break;

            case R.id.bt_openAlbum:
                RuntimePermissionUtil.test(
                        RuntimePermissionUtil.concatAll(
                                RuntimePermissionUtil.getCameraPermissionList(),
                                RuntimePermissionUtil.getStoragePermissionList()
                        ), new PermissionListener() {
                            @Override
                            public void onGranted() {
                                selectPicutreFromAlbum();
                            }

                            @Override
                            public void onDenied(List<String> deniedPermissions) {
                                ToastUtil.showToast(TestActvity.this, "拍照或写入sdk权限被禁止,请到应用权限进行打开!");
                            }
                        });

                break;


        }

    }
}
