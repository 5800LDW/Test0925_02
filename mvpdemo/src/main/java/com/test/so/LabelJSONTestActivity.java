package com.test.so;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.antonioleiva.mvpexample.app.R;
//import com.zqprintersdk.ZQLabelJSONSDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LabelJSONTestActivity extends Activity {
	private String TAG = "labeltest";
	private Button butPrint;
	private Button butPrintStop;
	private Button butQRCode;
	private Button butLogo;
	private Button butExit;
	private Button butStatus;
	private Button butDeviceID;
	private ZQLabelJSONSDK prn = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_label_jsontest);

		butPrint = (Button) findViewById(R.id.ButPrint);
		butPrint.setOnClickListener(MyClickListener);
		butPrintStop = (Button) findViewById(R.id.ButPrintStop);
		butPrintStop.setOnClickListener(MyClickListener);
		butStatus = (Button) findViewById(R.id.ButStatus);
		butStatus.setOnClickListener(MyClickListener);
		butQRCode = (Button) findViewById(R.id.ButQRCode);
		butQRCode.setOnClickListener(MyClickListener);
		butLogo = (Button) findViewById(R.id.ButLogo);
		butLogo.setOnClickListener(MyClickListener);
		butStatus = (Button) findViewById(R.id.ButStatus);
		butStatus.setOnClickListener(MyClickListener);
		butDeviceID = (Button) findViewById(R.id.ButGetDeviceID);
		butDeviceID.setOnClickListener(MyClickListener);
		butExit = (Button) findViewById(R.id.ButExit);
		butExit.setOnClickListener(MyClickListener);
		//
		prn = new ZQLabelJSONSDK();
	}

	private OnClickListener MyClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ButPrint: {
					JSONArray jsonPrint = new JSONArray();
					JSONArray jsonPage = new JSONArray();
					//
					try {
						JSONObject jsonData = new JSONObject();
						jsonData.put("Code", 0);
						jsonData.put("PageWidth", 650);
						jsonData.put("PageHeight", 576);
						jsonData.put("Rotate", 1);
						jsonPage.put(jsonData);
						//
						int line_x1=5;
						int line_x2=636;
						int line_y1=1;
						int line_y2=101-5;
						int line_y3=199-5-5;
						int line_y4=252-5-5;
						int line_y5=305-5-5;
						int line_y6=400+10+5;
						int lineWidth=4;//线条宽度
						int split_x=113;
						//
						JSONObject jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x1);jsonLine.put("StartY", line_y1);jsonLine.put("EndX", line_x2);jsonLine.put("EndY", line_y1);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x1);jsonLine.put("StartY", line_y2);jsonLine.put("EndX", line_x2);jsonLine.put("EndY", line_y2);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x1);jsonLine.put("StartY", line_y3);jsonLine.put("EndX", line_x1+split_x*4);jsonLine.put("EndY", line_y3);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x1);jsonLine.put("StartY", line_y4);jsonLine.put("EndX", line_x2);jsonLine.put("EndY", line_y4);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x1);jsonLine.put("StartY", line_y5);jsonLine.put("EndX", line_x2);jsonLine.put("EndY", line_y5);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x1);jsonLine.put("StartY", line_y1);jsonLine.put("EndX", line_x1);jsonLine.put("EndY", line_y5);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x2);jsonLine.put("StartY", line_y1);jsonLine.put("EndX", line_x2);jsonLine.put("EndY", line_y5);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x1+split_x);jsonLine.put("StartY", line_y3+lineWidth);jsonLine.put("EndX", line_x1+split_x);jsonLine.put("EndY", line_y5);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x1+split_x*2);jsonLine.put("StartY", line_y3+lineWidth);jsonLine.put("EndX", line_x1+split_x*2);jsonLine.put("EndY", line_y5);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x1+split_x*3);jsonLine.put("StartY", line_y3+lineWidth);jsonLine.put("EndX", line_x1+split_x*3);jsonLine.put("EndY", line_y5);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						jsonLine = new JSONObject();
						jsonLine.put("Code", 1);
						jsonLine.put("StartX", line_x1+split_x*4);jsonLine.put("StartY", line_y1);jsonLine.put("EndX", line_x1+split_x*4);jsonLine.put("EndY", line_y5);jsonLine.put("LineWidth", lineWidth);jsonPage.put(jsonLine);
						//////
						JSONObject jsonText = new JSONObject();
						jsonText.put("Code", 2);jsonText.put("StartX", 3);
						jsonText.put("StartY", 30);
						jsonText.put("Text", "【送】");
						jsonText.put("FontName", "32");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 1);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 85);
						jsonText.put("StartY", 18);
						jsonText.put("Text", "上海枢纽中心-青浦区");
						jsonText.put("FontName", "32");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 1);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 472);
						jsonText.put("StartY", 10);
						jsonText.put("Text", "0001/1");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 1);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 472);
						jsonText.put("StartY", 55);
						jsonText.put("Text", "1纸");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 0);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 8);
						jsonText.put("StartY", 118);
						jsonText.put("Text", "上海");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 1);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 20);
						jsonText.put("StartY", 147);
						jsonText.put("Text", "市");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 1);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 77);
						jsonText.put("StartY", 125);
						jsonText.put("Text", "-上海派送中心");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 1);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 18);
						jsonText.put("StartY", 192);
						jsonText.put("Text", "D02");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x11);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 1);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 18);
						jsonText.put("StartY", 245);
						jsonText.put("Text", "234");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x11);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 0);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 15);
						jsonText.put("StartY", 305);
						jsonText.put("Text", "中国物流");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 0);
						jsonText.put("Underline", 1);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 35);
						jsonText.put("StartY", 340);
						jsonText.put("Text", "154205");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 0);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 20);
						jsonText.put("StartY", 365);
						jsonText.put("Text", "2014-11-25");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 0);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 488);
						jsonText.put("StartY", 120);
						jsonText.put("Text", "20101");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x11);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 1);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 488);
						jsonText.put("StartY", 190);
						jsonText.put("Text", "0106");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x11);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 1);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 462);
						jsonText.put("StartY", 242);
						jsonText.put("Text", "精准汽运(短途)");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 0);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 1);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 575);
						jsonText.put("StartY", 315);
						jsonText.put("Text", "A");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x01);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 1);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						jsonText = new JSONObject();
						jsonText.put("Code", 2);
						jsonText.put("StartX", 232);
						jsonText.put("StartY", 385);
						jsonText.put("Text", "954478956700015001");
						jsonText.put("FontName", "24");
						jsonText.put("FontSize", 0x00);
						jsonText.put("Rotate", 0);
						jsonText.put("Bold", 0);
						jsonText.put("Underline", 0);
						jsonText.put("Reverse", 0);
						jsonPage.put(jsonText);

						JSONObject jsonBarcode = new JSONObject();
						jsonBarcode.put("Code", 3);
						jsonBarcode.put("StartX", 185);
						jsonBarcode.put("StartY", 300);
						jsonBarcode.put("Text", "954478956700015001");
						jsonBarcode.put("BarcodeType", 128);
						jsonBarcode.put("Rotate", 0);
						jsonBarcode.put("Width", 1);
						jsonBarcode.put("Height", 85);
						jsonPage.put(jsonBarcode);
						//////////////////////
						jsonPrint.put(jsonPage);
						String strRet = prn.Print(jsonPrint.toString());
						JSONObject obj = new JSONObject(strRet);
						String strCode = obj.get("Code").toString();
						String strMessage = obj.get("Message").toString();
						DisplayMsg(String.format("%s:%s", strCode, strMessage));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case R.id.ButQRCode: {
					JSONArray jsonPrint = new JSONArray();
					JSONArray jsonPage = new JSONArray();
					//
					try {
						JSONObject jsonData = new JSONObject();
						jsonData.put("Code", 0);
						jsonData.put("PageWidth", 576);
						jsonData.put("PageHeight", 576);
						jsonData.put("Rotate", 0);
						jsonPage.put(jsonData);
						//
						JSONObject jsonQRCode = new JSONObject();
						jsonQRCode.put("Code", 4);
						jsonQRCode.put("StartX", 5);
						jsonQRCode.put("StartY", 5);
						jsonQRCode.put("Text", "二维码测试");
						jsonQRCode.put("Rotate", 0);
						jsonQRCode.put("Model", 1);
						jsonQRCode.put("Ratio", 3);
						jsonPage.put(jsonQRCode);
						/////
						jsonPrint.put(jsonPage);
						String strRet = prn.Print(jsonPrint.toString());
						JSONObject obj = new JSONObject(strRet);
						String strCode = obj.get("Code").toString();
						String strMessage = obj.get("Message").toString();
						DisplayMsg(String.format("%s:%s", strCode, strMessage));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
				case R.id.ButLogo: {
					JSONArray jsonPrint = new JSONArray();
					JSONArray jsonPage = new JSONArray();
					//
					InputStream is;
					Bitmap bmp = null;
					try {
						is = getAssets().open("logo.png");
						int size = is.available();
						//Read the entire asset into a local byte buffer.
						byte[] buffer = new byte[size];
						is.read(buffer);
						is.close();
						bmp = BitmapFactory.decodeByteArray(buffer, 0, size);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					//
					try {
						JSONObject jsonData = new JSONObject();
						jsonData.put("Code", 0);
						jsonData.put("PageWidth", 576);
						jsonData.put("PageHeight", 576);
						jsonData.put("Rotate", 0);
						jsonPage.put(jsonData);
						//
						JSONObject jsonBitmap = new JSONObject();
						jsonBitmap.put("Code", 5);
						jsonBitmap.put("StartX", 5);
						jsonBitmap.put("StartY", 5);
						jsonBitmap.put("Bitmap", convertIconToString(bmp));
						jsonPage.put(jsonBitmap);
						/////
						jsonPrint.put(jsonPage);
						String strRet = prn.Print(jsonPrint.toString());
						JSONObject obj = new JSONObject(strRet);
						String strCode = obj.get("Code").toString();
						String strMessage = obj.get("Message").toString();
						DisplayMsg(String.format("%s:%s", strCode, strMessage));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
				case R.id.ButPrintStop: {
					String strRet;
					try {
						strRet = prn.PrinterStop();
						JSONObject obj = new JSONObject(strRet);
						String strCode = obj.get("Code").toString();
						String strMessage = obj.get("Message").toString();
						DisplayMsg(String.format("%s:%s", strCode, strMessage));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case R.id.ButStatus: {
					String strRet;
					try {
						strRet = prn.PrinterStatus();
						JSONObject obj = new JSONObject(strRet);
						String strCode = obj.get("Code").toString();
						String strMessage = obj.get("Message").toString();
						DisplayMsg(String.format("%s:%s", strCode, strMessage));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case R.id.ButGetDeviceID: {
					String strRet;
					try {
						strRet = prn.GetDeviceID();
						Log.v(TAG, strRet);
						JSONObject obj = new JSONObject(strRet);
						String strDeviceID = obj.get("DeviceID").toString();
						DisplayMsg(String.format("DeviceID:%s", strDeviceID));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case R.id.ButExit: {
					finish();
					break;
				}
			}
		}
	};

	public static String convertIconToString(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();//outputstream

		bitmap.compress(CompressFormat.PNG, 100, baos);
		byte[] appicon = baos.toByteArray();// 转为byte数组
		return Base64.encodeToString(appicon, Base64.DEFAULT);
	}

	private void DisplayMsg(String strMsg) {
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Information");

		b.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		});
		b.setMessage(strMsg);
		b.show();
	}
}