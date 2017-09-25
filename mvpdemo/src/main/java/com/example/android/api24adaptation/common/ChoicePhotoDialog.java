package com.example.android.api24adaptation.common;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.antonioleiva.mvpexample.app.R;

//import com.example.android.api24adaptation.R;


public class ChoicePhotoDialog extends ProgressDialog {
	View view;
	private CheckListener checkListener;
	public ChoicePhotoDialog(Context context, CheckListener checkListener) {
		super(context);
		this.checkListener=checkListener;
	}

	public ChoicePhotoDialog(Context context, int theme) {
		super(context,theme);
	}

	public ChoicePhotoDialog(Context context, View view) {
		super(context);
		this.view = view;
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.dialog_choice_img);
        this.setCanceledOnTouchOutside(false);

		findViewById(R.id.tvAlbum).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkListener != null) {
					checkListener.onClickAlbum();
				}
				dismiss();
			}
		});

		findViewById(R.id.tvCamera).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkListener!=null){
					checkListener.onClickCamera();
				}
				dismiss();
			}
		});
		findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkListener!=null){
					checkListener.onCancel();
				}
				dismiss();
			}
		});

    }  


	public interface CheckListener{
		void onClickAlbum();
		void onClickCamera();
		void onCancel();
	}


}
