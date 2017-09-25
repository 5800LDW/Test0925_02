package com.example.android.api24adaptation.common;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.antonioleiva.mvpexample.app.R;

//import com.example.android.api24adaptation.R;


public class MsgDialog extends Dialog {
	View view;
	private SubmitListener submitListener;
	private CanclListener canclListener;
	private String title;
	private String msg;
	public MsgDialog(Context context, String title, String msg, SubmitListener submitListener) {
		super(context, R.style.CustomDialog);
		this.submitListener=submitListener;
		this.title=title;
		this.msg=msg;
	}
	public MsgDialog(Context context, String title, String msg, CanclListener canclListener) {
		super(context, R.style.CustomDialog);
		this.canclListener=canclListener;
		this.title=title;
		this.msg=msg;
	}
	public MsgDialog(Context context, String title, String msg, SubmitListener submitListener, CanclListener canclListener) {
		super(context,R.style.CustomDialog);
		this.submitListener=submitListener;
		this.canclListener=canclListener;
		this.title=title;
		this.msg=msg;
	}

	public MsgDialog(Context context, int theme) {
		super(context,theme);
	}

	public MsgDialog(Context context, View view) {
		super(context,R.style.CustomDialog);
		this.view = view;
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.dialog_msg);
        this.setCanceledOnTouchOutside(false);

		((TextView)findViewById(R.id.tvTitle)).setText(title);
		TextView tvMsg=(TextView)findViewById(R.id.tvMsg);
		tvMsg.setText(msg);

		TextView tvSubmit=(TextView)findViewById(R.id.tvSubmit);
		TextView tvCancel=(TextView)findViewById(R.id.tvCancel);

		 if(submitListener==null){
			 tvSubmit.setVisibility(View.GONE);
		 }else{
			 tvSubmit.setVisibility(View.VISIBLE);
			 tvSubmit.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View v) {
					 dismiss();
					 submitListener.onClick();
				 }
			 });
		 }
		if(canclListener==null){
			tvCancel.setVisibility(View.GONE);
		}else{
			tvCancel.setVisibility(View.VISIBLE);
			tvCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					canclListener.onClick();
					dismiss();
				}
			});
		}
    }  


	public interface SubmitListener{
		void onClick();
	}

	public interface CanclListener{
		void onClick();
	}
}
