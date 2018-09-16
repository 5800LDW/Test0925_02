package com.ldw.xyz.collector;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LDW10000000 on 29/12/2016.
 */

public class ActivityCollector {

    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static Activity getTopActivity(){
        if(activityList.isEmpty()){
            return  null;
        }
        else {
            return activityList.get(activityList.size()-1);
        }
    }


    /***仅供测试用*/
    public static  List<Activity> getActivityList(){
        return activityList;
    }


}
