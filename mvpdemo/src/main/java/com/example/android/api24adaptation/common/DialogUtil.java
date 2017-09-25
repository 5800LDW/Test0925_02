package com.example.android.api24adaptation.common;


import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtil {
	public static ProgressDialog loadingProgressDialog = null;

	public static void showOpenPhoto(Context context, ChoicePhotoDialog.CheckListener checkListener) {

		ChoicePhotoDialog dialog=new ChoicePhotoDialog(context,checkListener);
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
	}


}
