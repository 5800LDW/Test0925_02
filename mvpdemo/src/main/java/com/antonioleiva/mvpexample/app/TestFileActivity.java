package com.antonioleiva.mvpexample.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ldw.xyz.util.file.MyFileUtil;

import java.io.File;

/**
 * Created by LDW10000000 on 01/07/2017.
 */

public class TestFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);
        WebView webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.baidu.com");


        TextView tv_Content = (TextView)findViewById(R.id.tv_Content);
        String str = "getMyExternalCacheDir==>"+ MyFileUtil.getMyExternalCacheDir(this).toString() + "\n"
                     + "getMyExternalFilesDir===>"+ MyFileUtil.getMyExternalFilesDir(this,"wo"+ File.separator+"wo2").toString() + "\n"
                     + "getMyExternalStorageDirectory===>"+ MyFileUtil.getMyExternalStorageDirectory().toString();
        tv_Content.setText(str);


    }
}
