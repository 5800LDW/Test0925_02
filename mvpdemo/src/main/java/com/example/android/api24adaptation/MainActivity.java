//package com.example.android.api24adaptation;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.anthonycr.grant.PermissionsManager;
//import com.anthonycr.grant.PermissionsResultAction;
//import com.antonioleiva.mvpexample.app.R;
//import com.example.android.api24adaptation.checkupdate.UpdateChecker;
//import com.example.android.api24adaptation.common.ChoicePhotoDialog;
//import com.example.android.api24adaptation.common.DialogUtil;
//import com.example.android.api24adaptation.common.FileUtils;
//import com.example.android.api24adaptation.common.NoScrollGridView;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.UUID;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//
////    @BindView(R.id.btn_update)
//    Button btnUpdate;
////    @BindView(R.id.gv_pic)
//    NoScrollGridView gvPic;
//
//    private PicAdapter madapter;
//    private ArrayList<Pictrue> mpicList;
//    private static final int PHOTO_PICKED_WITH_DATA = 1001;//相册
//    private static final int CAMERA_WITH_DATA = 1002;//相机
//    private static final int CROP_PHOTO = 101;
//    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/CameraCache");
//    private File mCurrentPhotoFile;// 照相机拍照得到的图片
//    private File mCacheFile;
//    private static String fileName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_camera3);
////        ButterKnife.bind(this);
//
//        gvPic = (NoScrollGridView)findViewById(R.id.gv_pic);
//
//        findViewById(R.id.btn_update).setOnClickListener(this);
//
//        initView();
//    }
//
//    private void initView() {
//        mpicList = new ArrayList<>();//用于显示 车辆图片的数据集
//        mpicList.add(new Pictrue("", "default", false));
//        madapter = new PicAdapter(mpicList);
//        gvPic.setAdapter(madapter);
//    }
//
////    @OnClick({R.id.btn_update})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_update:
//
//                //检查更新
//                UpdateChecker.checkForDialog(MainActivity.this);
//
//                break;
//
//        }
//    }
//
//
//    private void showPicChooseView() {
//        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(MainActivity.this, new String[]{
//
//                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {
//            @Override
//            public void onGranted() {
//                DialogUtil.showOpenPhoto(MainActivity.this, new ChoicePhotoDialog.CheckListener() {
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onClickAlbum() {
//                        Intent intent = new Intent();
//                        intent.addCategory(Intent.CATEGORY_OPENABLE);
//                        intent.setType("image/*");
//                        if (Build.VERSION.SDK_INT < 19) {
//                            intent.setAction(Intent.ACTION_GET_CONTENT);
//                        } else {
//                            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//                        }
//                        startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
//                    }
//
//                    @Override
//                    public void onClickCamera() {
//                        String status = Environment.getExternalStorageState();
//                        if (status.equals(Environment.MEDIA_MOUNTED)) {
//                            if (!PHOTO_DIR.exists()) {
//                                PHOTO_DIR.mkdirs();// 创建照片的存储目录
//                            }
//                            fileName = System.currentTimeMillis() + ".jpg";
//                            mCurrentPhotoFile = new File(PHOTO_DIR, fileName);
//                            Intent intentC = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                Uri imageUri = FileProvider.getUriForFile(MainActivity.this, "com.qqwl.fileprovider", mCurrentPhotoFile);
//                                intentC.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                intentC.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//
//                            } else {
//                                intentC.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
//                            }
//                            startActivityForResult(intentC, CAMERA_WITH_DATA);
//                        } else {
//                            Toast.makeText(MainActivity.this, "没有SD卡", Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onDenied(String permission) {
//                Toast.makeText(MainActivity.this, "获取权限失败，请点击重试", Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }
//
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_CANCELED) {
//            return;
//        }
//        switch (requestCode) {
//            case PHOTO_PICKED_WITH_DATA://相机返回
//                Uri uri = data.getData();
//                String selectedImagePath = FileUtils.getPath(MainActivity.this, uri);
//                mCurrentPhotoFile = new File(selectedImagePath);
//                startPhotoZoom(Uri.fromFile(mCurrentPhotoFile));
//                break;
//            case CAMERA_WITH_DATA://相册返回
//                if (mCurrentPhotoFile == null || !mCurrentPhotoFile.exists()) {
//                    mCurrentPhotoFile = new File(PHOTO_DIR, fileName);
//                }
//
//                startPhotoZoom(Uri.fromFile(mCurrentPhotoFile));
//                break;
//            case CROP_PHOTO://图片剪裁返回
//                String url = mCacheFile.getAbsolutePath();
//                addPictoList(url);
//
//                break;
//        }
//    }
//
//    private void addPictoList(String url) {
//        //需要上传的新添加的图片
//        for (int i = 0; i < mpicList.size(); i++) {
//            if (mpicList.get(i).getUrl().equals("default")) {
//                mpicList.set(i, new Pictrue(UUID.randomUUID().toString(), url, true));
//                mpicList.add(new Pictrue("", "default", false));
//                break;
//            }
//
//        }
//
//        madapter.notifyDataSetChanged();
//    }
//
//    /**
//     * 裁剪图片
//     *
//     * @param uri
//     */
//    public void startPhotoZoom(Uri uri) {
//
//        String fileName = System.currentTimeMillis() + ".jpg";
//        if (!PHOTO_DIR.exists()) {
//            PHOTO_DIR.mkdirs();// 创建照片的存储目录
//        }
//        mCacheFile = new File(PHOTO_DIR, fileName);
//        Uri outputUri = Uri.fromFile(new File(mCacheFile.getPath()));
//        String url = FileUtils.getPath(MainActivity.this, uri);
//
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        //sdk>=24
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//            Uri imageUri = FileProvider.getUriForFile(MainActivity.this, "com.qqwl.fileprovider", new File(url));//通过FileProvider创建一个content类型的Uri
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.putExtra("noFaceDetection", true);//去除默认的人脸识别，否则和剪裁匡重叠
//            intent.setDataAndType(imageUri, "image/*");
//
//            //19=<sdk<24
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
//
//            //sdk<19
//        } else {
//            intent.setDataAndType(uri, "image/*");
//        }
//        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
//        intent.putExtra("aspectX", 19);// 这两项为裁剪框的比例.
//        intent.putExtra("aspectY", 20);// x:y=1:2
//        intent.putExtra("outputX", 600);
//        intent.putExtra("outputY", 500);
//        intent.putExtra("output", outputUri);
//        intent.putExtra("outputFormat", "JPEG");// 返回格式
//        startActivityForResult(intent, CROP_PHOTO);
//    }
//
//
//    /**
//     * 选择图片展示
//     */
//    private class PicAdapter extends BaseAdapter {
//        ArrayList<Pictrue> picList;
//
//        public PicAdapter(ArrayList<Pictrue> picList) {
//            this.picList = picList;
//
//        }
//
//        @Override
//        public int getCount() {
//            return picList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return picList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            PicViewHolder holder;
//            if (convertView == null) {
//                holder = new PicViewHolder();
//                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.adapter_mainpic, null);
//                holder.imgContent = (ImageView) convertView.findViewById(R.id.iv_content);
//                holder.imgDel = (ImageView) convertView.findViewById(R.id.iv_del);
//                convertView.setTag(holder);
//            } else {
//                holder = (PicViewHolder) convertView.getTag();
//            }
//
//
//            if (picList.get(position).getUrl().equals("default")) {
//                holder.imgContent.setImageResource(R.mipmap.bg_img_up);
//                holder.imgDel.setVisibility(View.GONE);
//                holder.imgDel.setClickable(false);
//            } else {
//                holder.imgContent.setImageURI(Uri.parse(picList.get(position).getUrl()));
//                holder.imgDel.setVisibility(View.VISIBLE);
//                holder.imgDel.setClickable(true);
//            }
//
//
//            holder.imgContent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mpicList.size() < 10) {
//                        if (picList.get(position).getUrl().equals("default")) {//选择图片
//                            showPicChooseView();
//                        }
//                    } else {
//                        if (mpicList.size() == 10) {
//                            if (mpicList.get(9).getUrl().equals("default")) {
//                                showPicChooseView();
//                            } else {
//                                Toast.makeText(MainActivity.this, "最多只能上传10张图片", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//
//                }
//            });
//
//            holder.imgDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Pictrue p = mpicList.get(position);
//                    if (mpicList.size()==1){
//                        mpicList.remove(p);
//                        mpicList.add(new Pictrue("", "default", false));
//                    }else{
//
//                        mpicList.remove(p);
//                    }
//
//                    madapter.notifyDataSetChanged();
//
//                }
//            });
//
//
//            return convertView;
//        }
//
//        private class PicViewHolder {
//            private ImageView imgContent;
//            private ImageView imgDel;
//
//        }
//
//    }
//}
