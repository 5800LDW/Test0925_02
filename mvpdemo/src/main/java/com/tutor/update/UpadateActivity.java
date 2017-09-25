package com.tutor.update;

import android.app.Activity;
import android.os.Bundle;

import com.antonioleiva.mvpexample.app.R;

/**
 * Created by LDW10000000 on 08/08/2017.
 */

public class UpadateActivity extends Activity {


    private UpdateManager mUpdateManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //这里来检测版本是否需要更新
        mUpdateManager = new UpdateManager(this);
        mUpdateManager.checkUpdateInfo();


    }
}