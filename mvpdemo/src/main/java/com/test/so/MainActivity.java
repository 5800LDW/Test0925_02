//package com.test.so;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import com.zqprintersdk.PrinterConst;
//import com.zqprintersdk.ZQLabelSDK;
//
//
//import android.os.Bundle;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.Log;
//import android.view.Menu;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//public class MainActivity extends Activity {
//	private Button butConnect;
//	private Button butDisConnect;
//	private Button butTest;
//	private Button butQRCode;
//	private Button butLogo;
//	private Button butStatus;
//	private Button butExit;
//	private EditText etAddr;
//	private ZQLabelSDK prn = null;
//
//	private View.OnClickListener MyClickListener = new View.OnClickListener() {
//		public void onClick(View v) {
//			int nRet = 0;//ZQ_SUCCESS;
//			switch (v.getId()) {
//			case R.id.ButConnect: {
//				nRet = prn.prn_Connect(etAddr.getText().toString());
//				if (nRet == PrinterConst.ErrorCode.SUCCESS)
//				{
//					butDisConnect.setEnabled(true);
//					butTest.setEnabled(true);
//					butQRCode.setEnabled(true);
//					butLogo.setEnabled(true);
//					butStatus.setEnabled(true);
//				}
//				else
//				{
//					DisplayMsg(String.format("Connect Error:%d", nRet));
//				}
//				//
//				break;
//			}
//			case R.id.ButDisConnect: {
//				prn.prn_DisConnect();
//				butDisConnect.setEnabled(false);
//				butTest.setEnabled(false);
//				butQRCode.setEnabled(false);
//				butLogo.setEnabled(false);
//				butStatus.setEnabled(false);
//				break;
//			}
//			case R.id.ButTest: {
//				prn.prn_PageSetup(576,700);
//				prn.prn_PageClear();
//				int line_x1=5;
//				int line_x2=636;
//				int line_y1=1;
//				int line_y2=101-5;
//				int line_y3=199-5-5;
//				int line_y4=252-5-5;
//				int line_y5=305-5-5;
//				int line_y6=400+10+5;
//				int lineWidth=4;//�������
//				int split_x=113;
//				prn.prn_DrawLine(lineWidth,line_x1,line_y1,line_x2,line_y1);
//				prn.prn_DrawLine(lineWidth,line_x1,line_y2,line_x2,line_y2);
//				prn.prn_DrawLine(lineWidth,line_x1,line_y3,line_x1+split_x*4,line_y3);
//				prn.prn_DrawLine(lineWidth,line_x1,line_y4,line_x2,line_y4);
//				prn.prn_DrawLine(lineWidth,line_x1,line_y5,line_x2,line_y5);
//
//				prn.prn_DrawLine(lineWidth,line_x1,line_y1,line_x1,line_y5);//�����������
//				prn.prn_DrawLine(lineWidth,line_x2,line_y1,line_x2,line_y5);//�����������
//
//				prn.prn_DrawLine(lineWidth,line_x1+split_x  ,line_y3+lineWidth,line_x1+split_x  ,line_y5);
//				prn.prn_DrawLine(lineWidth,line_x1+split_x*2,line_y3+lineWidth,line_x1+split_x*2,line_y5);
//				prn.prn_DrawLine(lineWidth,line_x1+split_x*3,line_y3+lineWidth,line_x1+split_x*3,line_y5);
//				prn.prn_DrawLine(lineWidth,line_x1+split_x*4,line_y1          ,line_x1+split_x*4,line_y5);
//				//////
//				prn.prn_DrawText(3, 20, "���͡�", "32", 0x00, 0, 1, 0, 0 );
//				prn.prn_DrawText(85, 15, "�Ϻ���Ŧ����-������", "32", 0x00, 0, 1, 0, 0 );
//
//				prn.prn_DrawText(472, 10, "0001/1", "24", 0x00, 0, 1, 0, 0 );
//				prn.prn_DrawText(472, 55, "1ֽ", "24", 0x00, 0, 0, 0, 0 );
//				//
//				prn.prn_DrawText(8, 118, "�Ϻ�", "24", 0x00, 0, 1, 0, 0 );
//				prn.prn_DrawText(20, 147, "��", "24", 0x00, 0, 1, 0, 0 );
//
//				prn.prn_DrawText(77, 125, "-�Ϻ���������", "24", 0x11, 0, 1, 0, 0);
//				//
//				prn.prn_DrawText(18, 192, "D02", "24", 0x11, 0, 0, 0, 0 );
//				prn.prn_DrawText(18, 245, "234", "24", 0x11, 0, 0, 0, 0 );
//				//
//				prn.prn_DrawText(15,305,"�й�����","24",0x00,0,0,1,0);
//				prn.prn_DrawText(35, 340, "154205", "24", 0x00, 0, 0, 0, 0 );
//				prn.prn_DrawText(20, 365, "2014-11-25", "24", 0x00, 0, 0, 0, 0 );
//				//
//				prn.prn_DrawText(488, 120, "20101", "24", 0x11, 0, 1, 0, 0 );
//				prn.prn_DrawText(488, 190, "0106", "24", 0x11, 0, 1, 0, 0 );
//				//
//				prn.prn_DrawText(462, 242, "��׼����(��;)", "24", 0x00, 0, 0, 0, 1);
//				//
//				prn.prn_DrawText(575, 315, "A", "24", 0x01, 0, 1, 0, 0 );
//				//
//				prn.prn_DrawBarcode(185, 300, "954478956700015001", 128, 0, 2, 85);
//				prn.prn_DrawText(232, 385, "954478956700015001", "24", 0x00, 0, 0, 0, 0 );
//				//////
//				nRet = prn.prn_PagePrint(1);
//				if (nRet != PrinterConst.ErrorCode.SUCCESS)
//				{
//					DisplayMsg(String.format("Print Error:%d", nRet));
//				}
//				break;
//			}
//			case R.id.ButQRCode: {
//				prn.prn_PageSetup(576,576);
//				prn.prn_DrawBarcode(5, 5, "QRCode 1234567890", 19, 0, 1, 3);
//				nRet = prn.prn_PagePrint(1);
//				if (nRet != PrinterConst.ErrorCode.SUCCESS)
//				{
//					DisplayMsg(String.format("Print Error:%d", nRet));
//				}
//				break;
//			}
//			case R.id.ButLogo: {
//				prn.prn_PageSetup(300,300);
//				/////
//				InputStream is;
//				Bitmap bmp = null;
//				try {
//					is = getAssets().open("logo.png");
//					int size = is.available();
//					//Read the entire asset into a local byte buffer.
//					byte[] buffer = new byte[size];
//					is.read(buffer);
//					is.close();
//					bmp = BitmapFactory.decodeByteArray(buffer, 0, size);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					break;
//				}
//				prn.prn_PrintBitmap(0, 0, bmp);
//				nRet = prn.prn_PagePrint(0);
//				if (nRet != PrinterConst.ErrorCode.SUCCESS)
//				{
//					DisplayMsg(String.format("Print Error:%d", nRet));
//				}
//				break;
//			}
//			case R.id.ButStatus: {
//				nRet = prn.prn_PrinterStatus();
//				if (nRet == PrinterConst.ErrorCode.SUCCESS)
//				{
//					DisplayMsg("Printer is ok");
//				}
//				else
//				{
//					String strStatus = "";
//					if ((nRet & 0x01) == 0x01)
//						strStatus = "Conver open";
//					else if ((nRet & 0x02) == 0x02)
//						strStatus = "No Paper";
//					else if ((nRet & 0x04) == 0x04)
//						strStatus = "Hot";
//					DisplayMsg(strStatus);
//				}
//				break;
//			}
//			case R.id.ButExit: {
//				finish();
//				break;
//			}
//			}
//		}
//	};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		butConnect = (Button) findViewById(R.id.ButConnect);
//		butConnect.setOnClickListener(MyClickListener);
//		butDisConnect = (Button) findViewById(R.id.ButDisConnect);
//		butDisConnect.setOnClickListener(MyClickListener);
//		butTest = (Button) findViewById(R.id.ButTest);
//		butTest.setOnClickListener(MyClickListener);
//		butQRCode = (Button) findViewById(R.id.ButQRCode);
//		butQRCode.setOnClickListener(MyClickListener);
//		butLogo = (Button) findViewById(R.id.ButLogo);
//		butLogo.setOnClickListener(MyClickListener);
//		butStatus = (Button) findViewById(R.id.ButStatus);
//		butStatus.setOnClickListener(MyClickListener);
//		butExit = (Button) findViewById(R.id.ButExit);
//		butExit.setOnClickListener(MyClickListener);
//		etAddr = (EditText) findViewById(R.id.EdAddress);
//		butDisConnect.setEnabled(false);
//		butTest.setEnabled(false);
//		butQRCode.setEnabled(false);
//		butLogo.setEnabled(false);
//		butStatus.setEnabled(false);
//		prn = new ZQLabelSDK();
//		prn.EnableBLE(false);
//	}
//
//	private void DisplayMsg(String strMsg) {
//		AlertDialog.Builder b = new AlertDialog.Builder(this);
//		b.setTitle("Information");
//
//		b.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
//	         public void onClick(DialogInterface dialog, int which) {}
//        });
//		b.setMessage(strMsg);
//		b.show();
//	}
//
//}
