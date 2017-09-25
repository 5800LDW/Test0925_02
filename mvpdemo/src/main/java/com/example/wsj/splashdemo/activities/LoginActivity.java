package com.example.wsj.splashdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.antonioleiva.mvpexample.app.R;

//import com.example.wsj.splashdemo.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录界面");
    }
}
