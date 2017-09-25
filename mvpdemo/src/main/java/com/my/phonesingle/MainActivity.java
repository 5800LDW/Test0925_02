//package com.my.phonesingle;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//public class MainActivity extends Activity {
//	IntentFilter wifiIntentFilter; // wifi监听器
//	ImageView wifi_image;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		wifiIntentFilter = new IntentFilter();
//		wifiIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//		wifi_image = (ImageView) findViewById(R.id.wifi_image);
//		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
//		WifiInfo info = wifiManager.getConnectionInfo();
//		int level = WifiManager.calculateSignalLevel(info.getRssi(), 5);
//		Intent intent = new Intent();
//		// intent.putExtra("wifi_state", wifiManager.getWifiState());
//		intent.putExtra("wifi_level", level);
//		sendBroadcast(intent);
//
//	}
//
//	// 声明wifi消息处理过程
//	private BroadcastReceiver wifiIntentReceiver = new BroadcastReceiver() {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			int wifi_state;
//			int level = intent.getIntExtra("wifi_level", 0);
//			if (level >= 0 && level <= 50)
//				wifi_state = WifiManager.WIFI_STATE_ENABLED;
//			else if (level > 50 && level <= 70)
//				wifi_state = WifiManager.WIFI_STATE_ENABLING;
//			else if (level > 70 && level <= 100)
//				wifi_state = WifiManager.WIFI_STATE_DISABLED;
//			else if (level > 100 && level <= 200)
//				wifi_state = WifiManager.WIFI_STATE_DISABLING;
//			else
//				wifi_state = WifiManager.WIFI_STATE_UNKNOWN;
//			Log.i("Leve", "1111:" + level);
//			switch (wifi_state) {
//				case WifiManager.WIFI_STATE_DISABLING:
//					Log.i("Global.TAG", "1111:" + WifiManager.WIFI_STATE_DISABLING);
//					wifi_image.setImageResource(R.drawable.single1);
//					wifi_image.setImageLevel(level);
//					Toast.makeText(MainActivity.this, "信号强度：" + level,
//							Toast.LENGTH_SHORT).show();
//					break;
//				case WifiManager.WIFI_STATE_DISABLED:
//					Log.i("Global.TAG", "2222:" + WifiManager.WIFI_STATE_DISABLED);
//					wifi_image.setImageResource(R.drawable.single2);
//					wifi_image.setImageLevel(level);
//					Toast.makeText(MainActivity.this, "信号强度：" + level,
//							Toast.LENGTH_SHORT).show();
//					break;
//				case WifiManager.WIFI_STATE_ENABLING:
//					wifi_image.setImageResource(R.drawable.single3);
//					wifi_image.setImageLevel(level);
//					Log.i("Global.TAG", "33333:" + WifiManager.WIFI_STATE_ENABLING);
//					Toast.makeText(MainActivity.this, "信号强度：" + level,
//							Toast.LENGTH_SHORT).show();
//					break;
//				case WifiManager.WIFI_STATE_ENABLED:
//					Log.i("Global.TAG", "4444:" + WifiManager.WIFI_STATE_ENABLED);
//					wifi_image.setImageResource(R.drawable.single4);
//					wifi_image.setImageLevel(level);
//					Toast.makeText(MainActivity.this, "信号强度：" + level,
//							Toast.LENGTH_SHORT).show();
//					break;
//				case WifiManager.WIFI_STATE_UNKNOWN:
//					Log.i("Global.TAG", "5555:" + WifiManager.WIFI_STATE_UNKNOWN);
//					wifi_image.setImageResource(R.drawable.single0);
//					wifi_image.setImageLevel(level);
//					Toast.makeText(MainActivity.this, "信号强度：" + level,
//							Toast.LENGTH_SHORT).show();
//					break;
//			}
//		}
//	};
//
//	// 在onResume方法中注册，在onPause方法中销毁：
//	@Override
//	protected void onResume() {
//		super.onResume();
//		// 注册wifi消息处理器
//		registerReceiver(wifiIntentReceiver, wifiIntentFilter);
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		unregisterReceiver(wifiIntentReceiver);
//	}
//
//}
