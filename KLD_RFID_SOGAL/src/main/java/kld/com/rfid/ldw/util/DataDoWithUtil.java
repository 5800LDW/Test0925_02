package kld.com.rfid.ldw.util;

import com.ldw.xyz.util.LogUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by liudongwen on 2018/8/29.
 */

public class DataDoWithUtil {

    private final static String TAG = "DataDoWithUtil";

    public static String dataConversion(String content) {

        //todo
//        String PA = content.substring(0,4);
//        PA = bytesToAscii(hexStringToBytes(PA),0,PA.length()/2);
//        String normal = content.substring(4,content.length());
//        return PA+normal;

//        //截取;
//        int FIndex = content.substring(4, content.length()).indexOf("F") + 4;
//        if(FIndex!=-1){
//            content = content.substring(0, FIndex);
//        }
//        //转换;
//        String PA = content.substring(0, 4);
//        PA = bytesToAscii(hexStringToBytes(PA), 0, PA.length() / 2);
//        String normal = content.substring(4, content.length());
//        return PA + normal;

        LogUtil.e(TAG,"----------------------------");
        LogUtil.e(TAG,"处理前 content = "+ content);


        content = content.toUpperCase();

        StringBuffer sb = new StringBuffer();

        //转换;
        String PA = content.substring(0, 4);
        PA = bytesToAscii(hexStringToBytes(PA), 0, PA.length() / 2);

        content = content.substring(4,content.length()).replace("F","");

        LogUtil.e(TAG,"----------------------------");
        LogUtil.e(TAG,"处理后  = "+ PA+content);

        sb.append(PA);
        sb.append(content);

        return sb.toString();


//        return bytesToAscii(hexStringToBytes(content),0,content.length()/2);
//        return  content;
    }

//    public static void main(String[] args) {
//
//        System.out.println(printHexString(asciiStringToBytes("PA1808100175310")));
////		byte[] bs = new byte[] {50,41,31,38,30,38,31,30,30,31,37,35,33 31 30};
//        String str = "504131383038313030313735333130";
//        System.out.println(bytesToAscii(hexStringToBytes("504131383038313030313735333130"),0,str.length()/2));
//
//    }


    //50 41 31 38 30 38 31 30 30 31 37 35 33 31 30

    /**
     * @param b
     * @return String
     * @function 字节的转换成16进制
     */
    public static String printHexString(byte[] b) {
        String tagString = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF) + " ";
            if (hex.length() == 2) {
                hex = '0' + hex;
            }
            tagString += hex.toUpperCase();
        }
        return tagString;
    }


    /**
     * Resolve the bytes String data to ASCII.String like this "2f3Eab"
     *
     * @return String the return value.
     * @parahexStringm the needed parse data.
     */
    private static int charToInt(char c) {
        int num = -1;
        num = "0123456789ABCDEF".indexOf(String.valueOf(c));
        if (num < 0)
            num = "0123456789abcdef".indexOf(String.valueOf(c));
        return num;
    }

    /**
     * Convert list of byte to Ascii
     *
     * @param bytes
     * @param offset
     * @param datalength
     * @return
     */
    public static String bytesToAscii(byte[] bytes, int offset, int datalength) {
        if ((bytes == null) || (bytes.length == 0) || (offset < 0) || (datalength <= 0)) {
            return null;
        }
        if ((offset >= bytes.length) || (bytes.length - offset < datalength)) {
            return null;
        }
        String asciiString = null;
        byte[] newbytes = new byte[datalength];
        System.arraycopy(bytes, offset, newbytes, 0, datalength);
        try {
            asciiString = new String(newbytes, "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return asciiString;
    }

    /**
     * Convert hexString to byte array.
     *
     * @param hexString to bytes
     * @return The converted byte array.
     */
    public static byte[] hexStringToBytes(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        for (int i = 0; i < hexString.length(); i = i + 2) {
            char high = hexString.charAt(i);
            char low = hexString.charAt(i + 1);

            bytes[i / 2] = (byte) (charToInt(high) * 16 + charToInt(low));
        }
        return bytes;
    }

    /**
     * Convert Ascii string to byte array.
     *
     * @param string the needed converter data.
     * @return byte[] the converted value.
     */
    public static byte[] asciiStringToBytes(String string) {
        byte[] result = new byte[string.length()];
        for (int i = 0; i < string.length(); i++) {
            result[i] = (byte) string.charAt(i);
        }
        return result;
    }


}
