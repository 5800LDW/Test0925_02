//package cn.pedant.SweetAlert.ldw;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.widget.EditText;
//
//import cn.pedant.SweetAlert.SweetAlertDialog;
//import kld.com.rfid.ldw.R;
//
///**
// * Created by LDW10000000 on 19/01/2017.
// */
//
//public class LDWSweetAlertDialog extends SweetAlertDialog {
//
//    public static final int Edittext_TYPE = 6;
//
//    public LDWSweetAlertDialog(Context context) {
//        super(context);
//    }
//
//    public LDWSweetAlertDialog(Context context, int alertType) {
//        super(context, alertType);
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.alert_dialog2);
//
//        EditText content_edittext  = (EditText)findViewById(R.id.content_edittext);
//
//
//        changeAlertType(mAlertType, true);
//    }
//
//    @Override
//    protected void changeAlertType(int alertType, boolean fromCreate) {
//        super.changeAlertType(alertType, fromCreate);
//
//        if (mDialogView != null && mAlertType==Edittext_TYPE) {
//            if (!fromCreate) {
//                // restore all of views state before switching alert type
//                restore();
//            }
//            switch (mAlertType) {
//
//            }
//            if (!fromCreate) {
//                playAnimation();
//            }
//        }
//
//
//
//    }
//}
