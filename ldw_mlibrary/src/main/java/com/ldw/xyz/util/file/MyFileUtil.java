package com.ldw.xyz.util.file;

/**
 * Created by LDW10000000 on 19/06/2017.
 */

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.ldw.xyz.util.ExceptionUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 说明:
 * 1. 需要在ManiFest添加的权限:
 * 1)android.permission.MOUNT_UNMOUNT_FILESYSTEMS;
 * 2)android.permission.WRITE_EXTERNAL_STORAGE;
 * 3)android.permission.READ_EXTERNAL_STORAGE;
 */
public class MyFileUtil {

    public final static String UTF8_ENCODING = "UTF-8";
    public final static String GBK_ENCODING = "GBK";

    public interface BusinessImpl {
        void dispose(String line);
    }

    /**
     * 因为有的系统的路径的分隔符号是"/" 有的是"\" , 所以统一用 File.separator
     */
    public static String separator = File.separator;


    /**
     * 将字符串写入到文本文件中
     */
    public static void writeTxtToFile(String strcontent, String filePath,
                                      String fileName) {
        // 生成文件夹之后，再生成文件，不然会出错
        makeFile(filePath, fileName);// 生成文件

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("error:", e + "");
        }
    }


    /**
     * 生成文件
     *
     * @param filePath 末尾不要添加"/" 或 "\"
     * @param fileName 文件名字
     * @return
     */
    public static File makeFile(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);// 生成文件夹
        try {
            file = new File(filePath + separator + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 检测文件是否存在
     *
     * @param absolutePath 绝对路径,路径含文件名
     * @return
     */
    public static boolean isFileExists(String absolutePath) {
        try {
            File file = new File(absolutePath);
            if (!file.exists()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
            return false;
        }

    }


    /**
     * 生成文件夹
     */
    public static void makeRootDirectory(String filePath) {
        File file;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
//            throw  e;
        }
    }

    /**
     * 读取sd卡上指定后缀的所有文件,包括此路径下的其他文件夹里面的指定后缀文件
     *
     * @param files    返回的所有文件
     * @param filePath 路径(可传入sd卡路径)
     * @param suffix   后缀名称 比如 .gif
     * @return 使用方法:
     * fileList= new ArrayList<>();
     * //筛选目录下的.txt文件 已经存到fileList里面了
     * MyFileUtil.getSuffixFile(files, FileUtil.FOLDER_PATH, ".txt");
     */
    public final static List<File> getSuffixFile(List<File> files, String filePath, String suffix) {
        File f = new File(filePath);
        if (!f.exists()) {
            return null;
        }
        File[] subFiles = f.listFiles();
        for (File subFile : subFiles) {
            if (subFile.isFile() && subFile.getName().endsWith(suffix)) {
                // ToastUtil.showToast(mContext,subFile.getName());
                files.add(subFile);
            } else if (subFile.isDirectory()) {
                getSuffixFile(files, subFile.getAbsolutePath(), suffix);
            } else {
                //非指定目录文件 不做处理
            }
        }
        return files;
    }

    /**
     * 参考 getSuffixFile
     * 读取sd卡上指定后缀的所有文件,不包括此目录下的子文件夹里面文件
     *
     * @param files    返回的所有文件
     * @param filePath 路径(可传入sd卡路径)
     * @param suffix   后缀名称 比如 .gif
     * @return
     */
    public final static List<File> getSuffixFileExcludeChildFolder(List<File> files, String filePath, String suffix) {
        File f = new File(filePath);
        if (!f.exists()) {
            return null;
        }
        File[] subFiles = f.listFiles();
        for (File subFile : subFiles) {
            if (subFile.isFile() && subFile.getName().endsWith(suffix)) {
                // ToastUtil.showToast(mContext,subFile.getName());
                files.add(subFile);
            } else if (subFile.isDirectory()) {
                //子文件夹不处理
            } else {
                //非指定目录文件 不做处理
            }
        }
        return files;
    }


    /**
     * 获取现在的SD卡的状态是不是可读/可写
     *
     * @return
     */
    public final static boolean isExternalSDCanReadAndWrite() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? true : false;
    }


    /**
     * 例如: /mnt/sdcard
     *
     * @return
     */
    public final static File getMyExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 目录: /0/Android/data/包名/cache     (可以被清理软件清除内容)
     */
    public final static File getMyExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    /**
     * 目录: /0/Android/data/包名/files     (清理软件不能清除内容)
     * <p>
     * 1)如果是根目录就传: ""
     * 2)如果传入一个文件夹名字,例如: wo , 就会自动生成这个文件夹, 路径为:/0/Android/data/包名/files/wo
     * 3)如果传入一个文件夹名字,例如: wo/wo2 , 就会自动生成这个文件夹, 路径为:/0/Android/data/包名/files/wo/wo2
     */
    public final static File getMyExternalFilesDir(Context context, String str) {
        return context.getExternalFilesDir(str);
    }


    /**
     * 逐行读取文件
     *
     * @param from        file.toString() 的路径
     * @param fromCharset gbk 或 utf-8
     * @param impl
     * @throws Exception
     */
    public static void readLine(String from, String fromCharset, BusinessImpl impl) throws Exception {
//        BufferedReader in =
//                new BufferedReader(new InputStreamReader(new FileInputStream(from),fromCharset));
//        PrintWriter out = //接PrintWriter是先转换为Unicode,然后 通过转换流转为其他指定的编码.
//                new PrintWriter(new OutputStreamWriter(new FileOutputStream(to),toCharset));//指定的编码
//        String line;
//        while((line=in.readLine())!=null){
//		    out.println(line);
//            impl.dispose(line);
//        }
//        in.close();
//        out.close();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(from), fromCharset));
            String line;
            while ((line = in.readLine()) != null) {
                impl.dispose(line);
            }
        } catch (IOException e1) {
            throw e1;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }


    /**
     * 把A文件复制到B文件里
     *
     * @param file_Old
     * @param fromEncoding
     * @param file_New
     * @param toEncoding
     * @throws Exception
     */
    public static void copyA2B(String file_Old, String fromEncoding, String file_New, String toEncoding) throws Exception {
        // 来源文件
        InputStreamReader in = null;
        // 目的文件
        OutputStreamWriter out = null;
        try {
            int n;
            char[] buf = new char[8192];
            in = new InputStreamReader(new FileInputStream(file_Old), fromEncoding);

            out = new OutputStreamWriter(new FileOutputStream(file_New), toEncoding);
            //读出的数据往数组里面放
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * 备注: 未经验证
     * <p>
     * 从sdcard上读一个文件
     *
     * @param filePath
     * @return
     */
    public static byte[] readFileFromSdcard
    (String filePath) {
        byte[] data = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            int size = fileInputStream.available();
            data = new byte[size];
            fileInputStream.read(data);
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return data;
    }


    /**
     * 备注: 未经验证
     * <p>
     * 向sdcard写数据
     *
     * @param path
     * @param fileName
     * @param data
     */
    public static void writeToSdcard(String path, String fileName, byte[] data) {
        FileOutputStream fileOutputStream = null;
        try {
            // 判断sdcard的状态
            String sdcardState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                // MOUNTED有sdcard

                // 判断path有没有
                File filePath = new File(path);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }

                // 判断file有没有

                File file = new File(path, fileName);
                if (file.exists()) {
                    file.delete();
                }

                // 写数据
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(data);
                fileOutputStream.flush();
            }
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (Exception e2) {
                ExceptionUtil.handleException(e2);
            }
        }

    }


    /**
     * 把 inputStream 变成 String 字符串
     *
     * @param is
     * @return
     */
    public static String inputStream2String(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    /**
     * 把 String 变成 inputStream  字符串
     *
     * @param str
     * @param encoding
     * @return
     * @throws UnsupportedEncodingException
     */
    public static InputStream string2InputStream(String str, String encoding) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(str.getBytes(encoding));
    }


    public static void deleteDir(String path) {
        File dir = new File(path);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDir(path);
        }
        dir.delete();
    }

    public static void deleteDirContent(String path) {
        File dir = new File(path);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDirContent(file.getPath());
        }
    }

//    public static boolean fileIsExists(String path) {
//        try {
//            File f = new File(path);
//            if (!f.exists()) {
//                return false;
//            }
//        } catch (Exception e) {
//
//            return false;
//        }
//        return true;
//    }



    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("25DA-14E4".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }else if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri,
                                        String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }




}
























