package com.ldw.xyz.util.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ldw.xyz.util.ExceptionUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LDW10000000 on 07/03/2017.
 */


/**
 * 处理Bitmap
 */
public class BitmapUtil {
    public byte[] Bitmap2Bytes(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /**
         * Write a compressed version of the bitmap to the specified outputstream.
         * If this returns true, the bitmap can be reconstructed by passing a
         * corresponding inputstream to BitmapFactory.decodeStream(). Note: not
         * all Formats support all bitmap configs directly, so it is possible that
         * the returned bitmap from BitmapFactory could be in a different bitdepth,
         * and/or may have lost per-pixel alpha (e.g. JPEG only supports opaque
         * pixels).
         *
         * @param format   The format of the compressed image
         * @param quality  Hint to the compressor, 0-100. 0 meaning compress for
         *                 small size, 100 meaning compress for max quality. Some
         *                 formats, like PNG which is lossless, will ignore the
         *                 quality setting
         * @param stream   The outputstream to write the compressed data.
         * @return true if successfully compressed to the specified stream.
         */
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bitmapByte = baos.toByteArray();

        return bitmapByte;
    }


    public Bitmap Bytes2Bitmap(byte buff[]) {
        Bitmap bmp = BitmapFactory.decodeByteArray(buff, 0, buff.length);
        return bmp;
    }


    /**
     * 将图片保存到本地时进行压缩
     * (即将图片从Bitmap形式变为File形式时进行压缩)
     * 特点是:  File形式的图片确实被压缩了, 但是当你重新读取压缩后的file为 Bitmap是,它占用的内存并没有改变 
     *
     * @param bmp
     * @param file 保存的路径
     */
    public static void compressBmpToFile(Bitmap bmp, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;//个人喜欢从80开始,
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private Bitmap compressBmpFromBmp(Bitmap image) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int options = 100;
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        while (baos.toByteArray().length / 1024 > 100) {
//            baos.reset();
//            options -= 10;
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
//        return bitmap;
//    }


    /***
     * 读取sdk图片,获取Bitmap路径;
     *
     * @param srcPath
     * @return
     */
    private Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//		return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }

    /**
     * Returns the minimum number of bytes that can be used to store this bitmap's pixels.
     *
     * @param bmp
     */
    public static int getByteCount(Bitmap bmp) {
        return bmp.getByteCount();
    }


    /**
     * 保存图片到本地
     *
     * @param bitmap
     * @param fileAbsolutePath
     * @param format           Bitmap.CompressFormat.JPEG 或  Bitmap.CompressFormat.PNG 或 Bitmap.CompressFormat.WEBP
     * @param quality          0-100 (0最差, 100最好)
     */
    public static void saveBitmapFile(Bitmap bitmap, String fileAbsolutePath, Bitmap.CompressFormat format, int quality) {
        File file = new File(fileAbsolutePath);// 将要保存图片的路径
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(format, quality, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            ExceptionUtil.handleException(e);
        }
    }
}



















