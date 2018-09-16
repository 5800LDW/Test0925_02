package kld.com.huizhan.util.ui;

import android.content.Context;

import com.feealan.wheelview.widget.TimePickerDialog;

/**
 * Created by LDW10000000 on 28/11/2017.
 */

public class DatePickUtil {

    public static void show(Context context , OnDatePickListener listener){

        TimePickerDialog time_Dialog = new TimePickerDialog(context);
        time_Dialog.setCallback(new TimePickerDialog.OnClickCallback() {
            @Override
            public void onCancel() {
                listener.cancel();
                time_Dialog.dismiss();
            }

            @Override
            public void onSure(int year, int month, int day, int hour, int minter, long time) {
                listener.sure(year,month,day,hour,minter,time);
                time_Dialog.dismiss();
            }
        });
        time_Dialog.show();

    }




    public interface OnDatePickListener{
        void cancel();
        void sure(int year, int month, int day, int hour, int minter, long time);


    }



}
