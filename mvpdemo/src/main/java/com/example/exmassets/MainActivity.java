//package com.example.exmassets;
//
//import java.io.IOException;
//
//import com.example.exmassets.R;
//import com.example.exmassets.util.AssetsUtil;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class MainActivity extends Activity implements OnClickListener {
//	private final String TAG = "MainActivity";
//
//	private TextView tvHint;
//	private TextView tvTxt;
//	private ImageView ivImg;
//	private WebView wvHtm;
//	private String fileName;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//
//		tvHint = (TextView) findViewById(R.id.tvHint);
//		tvTxt = (TextView) findViewById(R.id.tvTxt);
//		ivImg = (ImageView) findViewById(R.id.ivImg);
//		wvHtm = (WebView) findViewById(R.id.wvHtm);
//		
//		Button btn_txt = (Button) findViewById(R.id.btn_txt);
//		Button btn_img = (Button) findViewById(R.id.btn_img);
//		Button btn_htm = (Button) findViewById(R.id.btn_htm);
//		btn_txt.setOnClickListener(this);
//		btn_img.setOnClickListener(this);
//		btn_htm.setOnClickListener(this);
//
//		tvHint.setVisibility(View.VISIBLE);
//		try {
//			String[] fileNames = getAssets().list("");
//			if (fileNames != null) {
//				String fileDesc = String.format("assets根目录下找到%d个文件（夹）：", fileNames.length);
//				for (int i=0; i<fileNames.length; i++) {
//					if (i != 0) {
//						fileDesc = fileDesc + "、";
//					}
//					fileDesc = fileDesc + fileNames[i];
//				}
//				fileDesc = fileDesc + "。";
//				tvHint.setText(fileDesc);
//			} else {
//				tvHint.setText("assets根目录下没有找到任何文件（夹）。");
//			}
//			Log.d(TAG, String.format("assets根目录下找到%d个文件（夹）：", fileNames.length));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		if (v.getId() == R.id.btn_txt) {
//			fileName = "txt/libai.txt";
//			tvHint.setText("当前打开的文本文件名是：/assets/"+fileName);
//			tvTxt.setVisibility(View.VISIBLE);
//			ivImg.setVisibility(View.GONE);
//			wvHtm.setVisibility(View.GONE);
//			tvTxt.setText(AssetsUtil.getTxtFromAssets(this, fileName));
//		} else if (v.getId() == R.id.btn_img) {
//			fileName = "images/water.jpg";
//			tvHint.setText("当前打开的图像文件名是：/assets/"+fileName);
//			tvTxt.setVisibility(View.GONE);
//			ivImg.setVisibility(View.VISIBLE);
//			wvHtm.setVisibility(View.GONE);
//			ivImg.setImageBitmap(AssetsUtil.getImgFromAssets(this, fileName));
//		} else if (v.getId() == R.id.btn_htm) {
//			fileName = "file:///android_asset/html/index.html";
//			tvHint.setText("当前打开的网页文件名是："+fileName);
//			tvTxt.setVisibility(View.GONE);
//			ivImg.setVisibility(View.GONE);
//			wvHtm.setVisibility(View.VISIBLE);
//			wvHtm.loadUrl(fileName);
//			wvHtm.setWebViewClient(mWebViewClient);
//		}
//	}
//	private WebViewClient mWebViewClient = new WebViewClient();
//
//}
//
