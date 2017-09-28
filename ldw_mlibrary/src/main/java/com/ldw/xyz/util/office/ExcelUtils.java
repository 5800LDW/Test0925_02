package com.ldw.xyz.util.office;

/**
 * Created by LDW10000000 on 21/06/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ldw.xyz.util.ExceptionUtil;
import com.ldw.xyz.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jxl.CellType;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 使用方法:
 * public void initData() {
 * File file = new File(MyFileUtil.FOLDER_PATH);
 * ExcelUtils.initExcel(file.toString() + 文件名字.xls,title,"条码信息");
 * ExcelUtils.writeObjListToExcel(getScanCodeAllData(), file.toString() + File.separator + 文件名字.xls, this
 * , new ExcelUtils.TipsInterfaceListener() {
 *
 * @Override public void exportSuccess() {}
 * @Override public void exportFailed(Exception e) {});
 * }
 */
@Deprecated
public class ExcelUtils {

    public interface TipsInterfaceListener {
        void exportSuccess();

        void exportFailed(Exception e);

    }


    public static WritableFont arial14font = null;

    public static WritableCellFormat arial14format = null;
    public static WritableFont arial10font = null;
    public static WritableCellFormat arial10format = null;
    public static WritableFont arial12font = null;
    public static WritableCellFormat arial12format = null;

    public final static String UTF8_ENCODING = "UTF-8";
    public final static String GBK_ENCODING = "GBK";

    public final static String DEFAULT_ENCODING = GBK_ENCODING;

    public static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);
            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(jxl.format.Colour.LIGHT_BLUE);
            arial12font = new WritableFont(WritableFont.ARIAL, 12);
            arial12format = new WritableCellFormat(arial12font);
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        } catch (WriteException e) {

            e.printStackTrace();
        }
    }

    //LDW
    private static int isHasCol = 0;
    //

    /**
     * @param fileName  文件的完整路径名字, 例如:/storage/sdcard0/BeiYe/2017-06-22 01-29-00.xls
     * @param colName   可以为空,不为空就是占用第一行显示标题
     * @param SheetName 不能为空,不能为"" 或者 null
     */
    public static void initExcel(String fileName, @Nullable String[] colName, @NonNull String SheetName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet(SheetName, 0);
//            sheet.addCell((WritableCell) new Label(0, 0, fileName, arial14format));
//            for (int col = 0; col < colName.length; col++) {
//                sheet.addCell(new Label(col, 0, colName[col], arial10format));
//            }

            //LDW
            if (colName != null && colName.length != 0) {
                sheet.addCell((WritableCell) new Label(0, 0, fileName, arial14format));
                for (int col = 0; col < colName.length; col++) {
                    sheet.addCell(new Label(col, 0, colName[col], arial10format));
                }
                isHasCol = 1;
            } else {
                isHasCol = 0;
            }
            //
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }


    @SuppressWarnings("unchecked")
    public static <T> void addObjListToExitedExcel(int installAmountInDB, int palletAmountInDB, List<T> objList, String fileName, Context c, TipsInterfaceListener listener) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(DEFAULT_ENCODING);

                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                File excelFile = new File(fileName);


//               Workbook workbook = Workbook.getWorkbook(excelFile);
                writebook = Workbook.createWorkbook(excelFile, workbook);
                WritableSheet sheet = writebook.getSheet(0);








                //excel里面的装柜数
                int installAmountInExcel = 0;
                //excel里面的栈板数
                int palletAmountInExcel = 0;
                String installAmountStr = "";
                String palletAmountStr = "";

                WritableCell cell = sheet.getWritableCell(9, 1);
                if (cell.getType() == CellType.LABEL) {
                    Label l = (Label) cell;
                    installAmountStr = l.getString();
                }

                WritableCell second = sheet.getWritableCell(10, 1);
                if (second.getType() == CellType.LABEL) {
                    Label l = (Label) second;
                    palletAmountStr = l.getString();
                }
                try {
                    if (installAmountStr != null) {
                        installAmountInExcel = Integer.parseInt(installAmountStr);
                    }
                } catch (Exception e) {
                    ExceptionUtil.handleException(e);
                }
                try {
                    if (palletAmountStr != null) {
                        palletAmountInExcel = Integer.parseInt(palletAmountStr);
                    }
                } catch (Exception e) {
                    ExceptionUtil.handleException(e);
                }


                //更新
//            ExcelUtils.setCellContent(fileAbsolutePath,0,9,1,installAmountTotal+"");
//            ExcelUtils.setCellContent(fileAbsolutePath,0,10,1,palletAmountTotal+"");

//                setCell(installAmountTotal, sheet);
//                setCell(palletAmountTotal, sheet);

                if(true){
                    int installAmountTotal = installAmountInExcel + installAmountInDB;
                    WritableCell setCellinstallAmountTotal = sheet.getWritableCell(9, 1);
                    if (setCellinstallAmountTotal.getType() == CellType.LABEL) {
                        Label l = (Label) setCellinstallAmountTotal;
                        l.setString(installAmountTotal + "");
                    } else {
                        Label l = new Label(9, 1, installAmountTotal + "");
                        try {
                            sheet.addCell(l);
                        } catch (Exception e) {
                            ExceptionUtil.handleException(e);
                        }
                    }
                    LogUtil.e("TAG", "ExcelUtils里面的"+"installAmountTotal="+installAmountTotal );
                }

                if(true){
                    int palletAmountTotal = palletAmountInExcel + palletAmountInDB;
                    WritableCell setCellpalletAmountTotal = sheet.getWritableCell(10, 1);
                    if (setCellpalletAmountTotal.getType() == CellType.LABEL) {
                        Label l = (Label) setCellpalletAmountTotal;
                        l.setString(palletAmountTotal + "");
                    } else {
                        Label l = new Label(10, 1, palletAmountTotal + "");
                        try {
                            sheet.addCell(l);
                        } catch (Exception e) {
                            ExceptionUtil.handleException(e);
                        }
                    }
                    LogUtil.e("TAG", "ExcelUtils里面的"+""+ "palletAmountTotal="+palletAmountTotal);
                }














                //LDW
                int row = sheet.getRows();
                LogUtil.e("TAG", "row=" + row);
                row -= 1;


                //LDW 下面的很关键, 其实, 静态变量怎么会在程序被杀后, 变为null(就算重启程序之后),我是很好奇
                if(arial12format==null||arial12font==null){
                    try{
                        arial12font = new WritableFont(WritableFont.ARIAL, 12);
                        arial12format = new WritableCellFormat(arial12font);
                        arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
                    }catch (Exception e){
                        ExceptionUtil.handleException(e);
                    }

                }

                //END



                for (int j = 0; j < objList.size(); j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + isHasCol + row, list.get(i), arial12format));
                    }
                }
                writebook.write();

//                Toast.makeText(c, "保存成功", Toast.LENGTH_SHORT).show();

                //LDW
                listener.exportSuccess();
                //
            } catch (Exception e) {
                //LDW
                ExceptionUtil.handleException(e);
                listener.exportFailed(e);
                //
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


    private static void setCell(int installAmountTotal, WritableSheet sheet) {
        WritableCell setCell = sheet.getWritableCell(9, 1);
        if (setCell.getType() == CellType.LABEL) {
            Label l = (Label) setCell;
            l.setString(installAmountTotal + "");
        } else {
            Label l = new Label(9, 1, installAmountTotal + "");
            try {
                sheet.addCell(l);
            } catch (Exception e) {
                ExceptionUtil.handleException(e);
            }

        }
    }


    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context c, TipsInterfaceListener listener) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(DEFAULT_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);
                for (int j = 0; j < objList.size(); j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + isHasCol, list.get(i), arial12format));
                    }
                }
                writebook.write();
//                Toast.makeText(c, "保存成功", Toast.LENGTH_SHORT).show();

                //LDW
                listener.exportSuccess();
                //

            } catch (Exception e) {
                //LDW
                ExceptionUtil.handleException(e);
                listener.exportFailed(e);
                //
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public static Object getValueByRef(Class cls, String fieldName) {
        Object value = null;
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
        String getMethodName = "get" + fieldName;
        try {
            Method method = cls.getMethod(getMethodName);
            value = method.invoke(cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 获取Exce的某张表里面有多少行
     *
     * @param fileName    文件的绝对路径(含文件名,文件后缀)
     * @param sheetNumber 这个Excel文件里面的第几张表
     * @return
     */
    public static int getRowTotalNumber(String fileName, int sheetNumber) {
        try {
            File excelFile = new File(fileName);
            Workbook workbook = Workbook.getWorkbook(excelFile);
            WritableWorkbook writebook = Workbook.createWorkbook(excelFile, workbook);
            WritableSheet sheet = writebook.getSheet(sheetNumber);
            int row = sheet.getRows();
            return row;
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
            return 0;
        }

    }


    /**
     * 获取第几列 第几行的 单元格内容
     *
     * @param fileName    文件的绝对路径(含文件名,文件后缀)
     * @param sheetNumber 这个Excel文件里面的第几张表
     * @param row         第几列(从0开始)
     * @param line        第几行(从0开始)
     * @return
     */
    public static String getCellContent(String fileName, int sheetNumber, int row, int line) {
        InputStream in = null;
        WritableWorkbook writebook = null;
        String content = null;
        try {
//            File excelFile = new File(fileName);
//            Workbook workbook = Workbook.getWorkbook(excelFile);

            in = new FileInputStream(new File(fileName));
            Workbook workbook = Workbook.getWorkbook(in);
            File excelFile = new File(fileName);


            writebook = Workbook.createWorkbook(excelFile, workbook);
            WritableSheet sheet = writebook.getSheet(sheetNumber);
            WritableCell cell = sheet.getWritableCell(row, line);
            if (cell.getType() == CellType.LABEL) {
                Label l = (Label) cell;
                content = l.getString();
            }
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        } finally {
            if (writebook != null) {
                try {
                    writebook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }


    /**
     * 设置第几列 第几行的 单元格内容
     *
     * @param fileName    文件的绝对路径(含文件名,文件后缀)
     * @param sheetNumber 这个Excel文件里面的第几张表
     * @param row         第几列(从0开始)
     * @param line        第几行(从0开始)
     * @return
     */
    public static boolean setCellContent(String fileName, int sheetNumber, int row, int line, String content) {
        InputStream in = null;
        WritableWorkbook writebook = null;
        try {
//            File excelFile = new File(fileName);
//            Workbook workbook = Workbook.getWorkbook(excelFile);

            in = new FileInputStream(new File(fileName));
            Workbook workbook = Workbook.getWorkbook(in);
            File excelFile = new File(fileName);


            writebook = Workbook.createWorkbook(excelFile, workbook);
            WritableSheet sheet = writebook.getSheet(sheetNumber);
            WritableCell cell = sheet.getWritableCell(row, line);

            if (cell.getType() == CellType.LABEL) {
                Label l = (Label) cell;
                l.setString(content);
            } else {
                Label l = new Label(row, line, content);
                sheet.addCell(l);
            }
            return true;
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
            return false;
        } finally {
            if (writebook != null) {
                try {
                    writebook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}