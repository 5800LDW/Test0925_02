package kld.com.rfid.ldw.test.jiewen;

import com.ldw.xyz.util.LogUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import kld.com.rfid.ldw.Const;

/**
 * Created by liudongwen on 2018/10/16.
 */

public class DataSort {


    public static final String TAG = "sortList";
    public static void  sortList( List<Map<String, String>> list){

        LogUtil.e(TAG,"-----------------------------");
        LogUtil.e(TAG,"排序前 list = " + list.toString());

//        System.out.println("按年龄升序：");
        Collections.sort(list, new SortByAge());
//        for (Student student : studentList) {
//            System.out.println(student.getName() + " / " + student.getAge());
//        }


        LogUtil.e(TAG,"-----------------------------");
        LogUtil.e(TAG,"排序后 list = " + list.toString());


    }



    static class  SortByAge implements Comparator {
        public int compare(Object o1, Object o2) {
            String s1 = ((Map<String, String>)o1).get(Const.Coname[1]);
            String s2 = ((Map<String, String>)o2).get(Const.Coname[1]);

            if(s1.equals(Const.Coname[1])){
                return -1;
            }
            if(s2.equals(Const.Coname[1])){
                return -1;
            }




            if(s1.startsWith("PA") && s2.startsWith("PA")){
                s1 = s1.substring(9,s1.length());
                s1 = 1+s1;

                s2 = s2.substring(9,s2.length());
                s2 = 1+s2;

                int i1 = Integer.parseInt(s1);
                int i2 = Integer.parseInt(s2);
                if(i1>i2){
                    return 1;
                }
                else{
                    return -1;
                }

            }
            else if(s1.startsWith("PA")){
                return  1;
            }
            else if(s2.startsWith("PA")){
                return  -1;
            }

            return -1;
        }
    }


}
