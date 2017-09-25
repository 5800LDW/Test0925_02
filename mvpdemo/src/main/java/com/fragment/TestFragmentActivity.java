package com.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.antonioleiva.mvpexample.app.R;

/**
 * Created by LDW10000000 on 31/07/2017.
 */

public class TestFragmentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1;
    private Button button2;
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private FragmentTransaction transactiona;
    private FragmentTransaction transactionb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        manager = getSupportFragmentManager();//获取fragment管理者
        transaction = manager.beginTransaction();//通过管理者开启事务
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);


        FragmentA fa = new FragmentA();//首先加载第一个界面
        transaction.add(R.id.line, fa);
        transaction.commit();//非常关键  这句话的意思提交  没有这句的话  是没有反应的
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.button1:
                transactiona = manager.beginTransaction();
                FragmentA fa = new FragmentA();
                transactiona.replace(R.id.line, fa);
                transactiona.commit();
                break;
            case R.id.button2:
                transactionb = manager.beginTransaction();
                FragmentB fb = new FragmentB();
                transactionb.replace(R.id.line, fb);
                transactionb.commit();
                break;

            default:
                break;
        }
    }


}