package com.test.so;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.zqprintersdk.PrinterConst;
import com.zqprintersdk.ZQLabelSDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZQLabelJSONSDK {
	static final String TAG = "ZQLabel";
	static final String VERSION = "1.0";
	static ZQLabelSDK prn = null;
	public ZQLabelJSONSDK()
	{
		System.setProperty("file.encoding", "gb2312");
		prn = new ZQLabelSDK();
		prn.prn_Connect("ttyS0:115200");
		//prn.prn_Connect("s3c2410_serial3:115200");
	}

	public String SDK_Version()
	{
		return VERSION;
	}

	public String PrinterStatus() throws JSONException
	{
		int nRet = prn.prn_PrinterStatus();
		JSONObject objRet = new JSONObject();
		if (nRet == PrinterConst.ErrorCode.SUCCESS)
		{
			objRet.put("Code", 0);
			objRet.put("Message", "成功");
		}
		else
		{
			if ((nRet & 0x01) == 0x01)
			{
				objRet.put("Code", -1);
				objRet.put("Message", "开盖");
			}
			else if ((nRet & 0x02) == 0x02)
			{
				objRet.put("Code", -2);
				objRet.put("Message", "缺纸");
			}
			else if ((nRet & 0x08) == 0x08)
			{
				objRet.put("Code", -3);
				objRet.put("Message", "忙");
			}
			else if ((nRet & 0x04) == 0x04)
			{
				objRet.put("Code", -4);
				objRet.put("Message", "温度异常");
			}
			else if ((nRet & 0x10) == 0x10)
			{
				objRet.put("Code", -5);
				objRet.put("Message", "电量低");
			}
			else if ((nRet & 0x20) == 0x20)
			{
				objRet.put("Code", -6);
				objRet.put("Message", "正在打印");
			}
		}
		return objRet.toString();
	}

	public String PrinterStop() throws JSONException
	{
		int nRet = prn.prn_PrinterStop();
		JSONObject objRet = new JSONObject();
		if (nRet == 0)
		{
			objRet.put("Code", 0);
			objRet.put("Message", "成功");
		}
		else
		{
			objRet.put("Code", -1);
			objRet.put("Message", "失败");
		}
		return objRet.toString();
	}

	public String Print(String strJson) throws JSONException
	{
		JSONObject objRet = new JSONObject();
		int nRet = prn.prn_PrinterStatus();
		if (nRet != PrinterConst.ErrorCode.SUCCESS)
		{
			if ((nRet & 0x01) == 0x01)
			{
				objRet.put("Code", -1);
				objRet.put("Message", "开盖");
			}
			else if ((nRet & 0x02) == 0x02)
			{
				objRet.put("Code", -2);
				objRet.put("Message", "缺纸");
			}
			else if ((nRet & 0x08) == 0x08)
			{
				objRet.put("Code", -3);
				objRet.put("Message", "忙");
			}
			return objRet.toString();
		}
		JSONArray jsonPrint = new JSONArray(strJson);
		int nPageNum = jsonPrint.length();
		if (nPageNum == 0)
		{
			objRet.put("Code", -5);
			objRet.put("Message", "没有可打印页面");
			return objRet.toString();
		}
		for (int i = 0; i < nPageNum; i++)
		{
			JSONArray jsonPage = jsonPrint.getJSONArray(i);
			int nList = jsonPage.length();
			int nPageWidth = 0;
			int nPageHeight = 0;
			int nPageRotate = 0;
			for (int j = 0; j < nList; j++)
			{
				JSONObject jsonData = jsonPage.getJSONObject(j);
				switch (jsonData.getInt("Code"))
				{
					case 0://page
					{
						nPageWidth = jsonData.getInt("PageWidth");
						nPageHeight = jsonData.getInt("PageHeight");
						nPageRotate = jsonData.getInt("Rotate");
						prn.prn_PageSetup(nPageHeight, nPageWidth);
					}
					break;
					case 1://line
					{//DrawLine(int lineWidth,int x0,int y0,int x1,int y1)
						int nStartX = jsonData.getInt("StartX");
						int nStartY = jsonData.getInt("StartY");
						int nEndX = jsonData.getInt("EndX");
						int nEndY = jsonData.getInt("EndY");
						int nLineWidth = jsonData.getInt("LineWidth");
						Log.v(TAG, String.format("%d,%d,%d,%d,%d", nLineWidth, nStartX, nStartY, nEndX, nEndY));
						prn.prn_DrawLine(nLineWidth, nStartX, nStartY, nEndX, nEndY);
					}
					break;
					case 2://Text
					{//DrawText(int x,int y,String text,String fontName,int fontsize,int rotate,int bold,int underline,int reverse)
						int nStartX = jsonData.getInt("StartX");
						int nStartY = jsonData.getInt("StartY");
						String strText = jsonData.getString("Text");
						String strFontName = jsonData.getString("FontName");
						int nFontSize = jsonData.getInt("FontSize");
						int nRotate = jsonData.getInt("Rotate");
						int nBold = jsonData.getInt("Bold");
						int nUnderline = jsonData.getInt("Underline");
						int nReverse = jsonData.getInt("Reverse");
						prn.prn_DrawText(nStartX, nStartY, strText, strFontName, nFontSize, nRotate, nBold, nUnderline, nReverse);
					}
					break;
					case 3://Barcode
					{//DrawBarcode(int x,int y, String text,int barcodetype,int rotate,int linewidth,int height)
						int nStartX = jsonData.getInt("StartX");
						int nStartY = jsonData.getInt("StartY");
						String strText = jsonData.getString("Text");
						int nBarcodeType = jsonData.getInt("BarcodeType");
						int nRotate = jsonData.getInt("Rotate");
						int nWidth = jsonData.getInt("Width");
						int nHeight = jsonData.getInt("Height");
						prn.prn_DrawBarcode(nStartX, nStartY, strText, nBarcodeType, nRotate, nWidth, nHeight);
					}
					break;
					case 4://QRCode
					{
						int nStartX = jsonData.getInt("StartX");
						int nStartY = jsonData.getInt("StartY");
						String strText = jsonData.getString("Text");
						int nBarcodeType = 19;
						int nRotate = jsonData.getInt("Rotate");
						int nWidth = jsonData.getInt("Model");
						int nHeight = jsonData.getInt("Ratio");
						prn.prn_DrawBarcode(nStartX, nStartY, strText, nBarcodeType, nRotate, nWidth, nHeight);
					}
					break;
					case 5://Bitmap
					{//PrintBitmap(int startX, int startY, Bitmap bmp)
						int nStartX = jsonData.getInt("StartX");
						int nStartY = jsonData.getInt("StartY");
						Bitmap bmp = convertStringToBitmap(jsonData.getString("Bitmap"));
						prn.prn_PrintBitmap(nStartX, nStartY, bmp);
					}
					break;
				}
			}
			//
			nRet = prn.prn_PagePrint(nPageRotate);
			if (nRet == PrinterConst.ErrorCode.SUCCESS)
			{
				objRet.put("Code", 0);
				objRet.put("Message", "成功");
			}
			else
			{
				objRet.put("Code", -4);
				objRet.put("Message", "失败");
			}
		}
		return objRet.toString();
	}

	public String GetDeviceID() throws JSONException
	{
		JSONObject objRet = new JSONObject();
		String strRet = prn.prn_GetDeviceID();
		objRet.put("DeviceID", strRet);
		return objRet.toString();
	}

	private static Bitmap convertStringToBitmap(String st)
	{ // OutputStream out; 
		Bitmap bitmap = null;
		try
		{
			// out = new FileOutputStream("/sdcard/aa.jpg");
			byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
			// bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			return bitmap;
		} catch (Exception e)
		{
			return null;
		}
	}
}
