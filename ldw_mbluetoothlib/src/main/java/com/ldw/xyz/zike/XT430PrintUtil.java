package com.ldw.xyz.zike;

import com.ldw.xyz.util.LogUtil;
import com.ldw.xyz.util.character.CountCharacter;

import zpSDK.zpSDK.zpBluetoothPrinter;

/**
 * 芝科XT430打印
 *
 */
public class XT430PrintUtil {

    /**
     * 宽度最大的点阵数量
     */
    public static final int MOST_WIDTH = 568;
    /**
     * 线条绘制的点阵数量
     */
    public static final int LINE_HEIGHT = 2;
    /**
     * 距离顶部的点阵数量
     */
//	public static final int MARGIN_TOP = 50;
    /**
     * 线条距离顶部的点阵数量
     */
    public static final int LINE_MARGIN_TOP = 18;
    /**
     * 文字距离顶部的点阵数量
     */
    public static final int TEXT_MARGIN_TOP = 18;


    public static final int FONTSIZE_1 = 1;
    public static final int FONTSIZE_2 = 2;
    public static final int FONTSIZE_3 = 3;
    public static final int FONTSIZE_4 = 4;
    public static final int FONTSIZE_5 = 5;
    public static final int FONTSIZE_6 = 6;
    public static final int FONTSIZE_7 = 7;

    public static int total_height;
    public static int MARGIN_LEFT;
    public static int MARGIN_RIGHT;

    public static void pageSetup(zpBluetoothPrinter zpSDK,int pageWidth, int pageHeight){
        zpSDK.pageSetup(pageWidth,pageHeight);
        //初始化默认的值
        drawInit();
    }


    private static void drawInit(){
        total_height = 0;
        MARGIN_LEFT = 30;
        MARGIN_RIGHT = 30;
    }

    public static void drawLine(zpBluetoothPrinter zpSDK){
//		total_height +=LINE_MARGIN_TOP;
//		zpSDK.drawLine(LINE_HEIGHT, MARGIN_LEFT, total_height, MOST_WIDTH, total_height, false);
        drawLineNotMoveDown(zpSDK);
        total_height +=LINE_HEIGHT;
    }

    public static void drawLineNotMoveDown(zpBluetoothPrinter zpSDK){
        total_height +=LINE_MARGIN_TOP;
        zpSDK.drawLine(LINE_HEIGHT, MARGIN_LEFT, total_height, MOST_WIDTH, total_height, false);
//		total_height +=LINE_HEIGHT;
    }


    /**
     *
     *
     * @param zpSDK
     * @param text
     * @param fontSize 字体大小 1：16点阵；2：24点阵；3：32点阵；4：24点阵放大一倍；5：32点阵放大一倍
     *            6：24点阵放大两倍；7：32点阵放大两倍；其他：24点阵
     */
    public static void drawMiddleText(zpBluetoothPrinter zpSDK,String text,int fontSize){
        int textHeight ;
        int margin_left ;

        CountCharacter countCharacter = new CountCharacter();
        countCharacter.count(text);
        int EandN = countCharacter.getEnCharacter() + countCharacter.getNumberCharacter();
        int Other = text.length() - EandN;

        if(FONTSIZE_1==fontSize){
            textHeight=16;
            margin_left = (MOST_WIDTH - EandN*8-Other*16)/2;
        }
        else if(FONTSIZE_2==fontSize){
            textHeight=24;
            margin_left = (MOST_WIDTH - EandN*12 - Other*24)/2;
        }
        else if(FONTSIZE_3==fontSize){
            textHeight=32;
            margin_left = (MOST_WIDTH - EandN*16 - Other*32)/2;
        }
        else if(FONTSIZE_4==fontSize){
            textHeight=48;
            margin_left = (MOST_WIDTH - EandN*24 - Other*48)/2;
        }
        else if(FONTSIZE_5==fontSize){
            textHeight=64;
            margin_left = (MOST_WIDTH - EandN*32 - Other*64)/2;
        }
        else if(FONTSIZE_6==fontSize){
            textHeight=96;
            margin_left =(MOST_WIDTH - EandN*48 - Other*96)/2;
        }
        else if(FONTSIZE_7==fontSize){
            textHeight=128;
            margin_left = (MOST_WIDTH - EandN*64 - Other*128)/2;
        }
        else{
            textHeight=24;
            margin_left = (MOST_WIDTH - EandN*32 - Other*64)/2;
        }

        if(margin_left<0){
            margin_left = 0;
        }

        total_height +=TEXT_MARGIN_TOP;
        zpSDK.drawText(margin_left, total_height, text, fontSize, 0, 0, false, false);
        total_height +=textHeight;
    }

    public static void drawNormalText(zpBluetoothPrinter zpSDK,String text,int fontSize){
        int textHeight = getTextHeight(fontSize);
        drawNormalTextNotMoveDown(zpSDK,text,fontSize);
        total_height +=textHeight;
    }

    public static void drawNormalTextNotMoveDown(zpBluetoothPrinter zpSDK,String text,int fontSize){
//		int textHeight = getTextHeight(fontSize);
        total_height +=TEXT_MARGIN_TOP;
        zpSDK.drawText(MARGIN_LEFT, total_height, text, fontSize, 0, 0, false, false);
//		total_height +=textHeight;
    }


    public static void drawTextInSameLine(zpBluetoothPrinter zpSDK, String text,int fontSize,boolean lineDrawFinish ,int... marginlist){
		int textHeight = getTextHeight(fontSize);
//		total_height +=TEXT_MARGIN_TOP;
        int marginLe ;
        if(marginlist.length == 0){
            marginLe = MARGIN_LEFT;
        }
        else{
            marginLe = marginlist[0]+MARGIN_LEFT;
        }
        LogUtil.e("TAG","marginLe ---------->"+marginLe);

        zpSDK.drawText(marginLe, total_height, text, fontSize, 0, 0, false, false);
        if(lineDrawFinish){
            total_height +=textHeight;
        }

    }


    private static int getTextHeight(int fontSize){
        int textHeight ;
        if(FONTSIZE_1==fontSize){
            textHeight=16;
        }
        else if(FONTSIZE_2==fontSize){
            textHeight=24;
        }
        else if(FONTSIZE_3==fontSize){
            textHeight=32;
        }
        else if(FONTSIZE_4==fontSize){
            textHeight=48;
        }
        else if(FONTSIZE_5==fontSize){
            textHeight=64;
        }
        else if(FONTSIZE_6==fontSize){
            textHeight=96;
        }
        else if(FONTSIZE_7==fontSize){
            textHeight=128;
        }
        else{
            textHeight=24;
        }

        return textHeight;
    }






}







