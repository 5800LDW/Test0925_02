package com.ldw.xyz.util.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by LDW10000000 on 01/08/2017.
 */

public class FragmentUtil {

    public static void activityAddFragment( int viewGroupId , Fragment fa,AppCompatActivity activity ,FragmentManager manager){
        if(manager == null ){
            manager = activity.getSupportFragmentManager();//获取fragment管理者
        }
        FragmentTransaction transaction = manager.beginTransaction();//通过管理者开启事务
        transaction.add(viewGroupId, fa);
        transaction.addToBackStack(null); // 添加到栈, LDW我添加的,看业务需求吧
        transaction.commit();//非常关键  这句话的意思提交  没有这句的话  是没有反应的
    }

    public static void activityReplaceFragment( int viewGroupId , Fragment fa ,AppCompatActivity activity ,FragmentManager manager){
        if(manager == null){
            manager = activity.getSupportFragmentManager();//获取fragment管理者
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(viewGroupId, fa);
        transaction.addToBackStack(null); // 添加到栈, LDW我添加的,看业务需求吧
        transaction.commit();
    }


    public static void FragmentAddFragment(int viewGroupId ,Fragment parent,Fragment child ){
        parent.getChildFragmentManager().beginTransaction().add(viewGroupId,child).addToBackStack(null)
                .commit();
    }

    public static void FragmentReplaceFragment(int viewGroupId ,Fragment parent,Fragment child ){
        parent.getFragmentManager().beginTransaction().replace(viewGroupId,child).addToBackStack(null)
                .commit();
    }






}
