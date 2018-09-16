import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kld.com.rfid.ldw.R;

/**
 * Created by liudongwen on 2018/8/30.
 */

public class ViewUtil {

    public static WindowManager wm;
    public static RelativeLayout alertDialog;
    private static WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();
    public static WindowManager.LayoutParams getMywmParams(){
        return wmParams;
    }

    public interface EventsListener{
        void onLeftClick();
        void onRightClick();
    }

    public static void addFloatAlertView(Context context,String title,String content,final EventsListener listener){

        alertDialog = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.alert_view, null);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);//

        wmParams = getMywmParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG;
        wmParams.flags |= 8;
        wmParams.gravity = Gravity.CENTER;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.format = 1;

        wm.addView(alertDialog, wmParams);

        TextView title_text = (TextView)alertDialog.findViewById(R.id.title_text);//content_text
        TextView content_text = (TextView)alertDialog.findViewById(R.id.content_text);//content_text
        title_text.setText(title);
        content_text.setText(content);
        Button btnClear = (Button)alertDialog.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLeftClick();
            }
        });

        Button confirm_button =(Button)alertDialog.findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRightClick();
            }
        });


    }


    public static void removeFloatAlertView(){
        wm.removeView(alertDialog);
    }


}
