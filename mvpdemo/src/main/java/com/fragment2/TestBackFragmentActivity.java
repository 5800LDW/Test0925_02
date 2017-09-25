package com.fragment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.antonioleiva.mvpexample.app.R;

/**
 * Created by LDW10000000 on 01/08/2017.
 */

public class TestBackFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_back_fragment);
    }

    public void fragmentDemo(View view) {
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);

    }

    public void viewPageDemo(View view) {
        Intent intent = new Intent(this, ViewPagerActivity.class);
        startActivity(intent);
    }
}
