package com.example.UHFDemo_install;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.uhf.api.cls.Reader;
import com.uhf.api.cls.Reader.TAGINFO;
import com.uhf.uhf.UHF1.UHF1Application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kld.com.rfid.ldw.R;

public class SubSystemActivity extends Activity {
	
	private Spinner spinner_threadmode;
	private ArrayAdapter<String> arradp_thmode;

	String[] str_thmode={"MainActivity","Alone"}; 
	 
	Button button_save,button_default,button_thread,
		   button_path,button_out;
	static EditText et_path;
	UHF1Application myapp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.systemlayout);
		Application app=getApplication();
		myapp=(UHF1Application) app;
		button_save=(Button) findViewById(R.id.button_saveparam);
		button_default=(Button) findViewById(R.id.button_paramdefault);
		button_thread=(Button) findViewById(R.id.button_threadset);
		button_path=(Button) findViewById(R.id.button_locate);
		button_out=(Button) findViewById(R.id.button_outfile);
		
		spinner_threadmode = (Spinner) findViewById(R.id.spinner_thread); 
		arradp_thmode = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,str_thmode); 
		arradp_thmode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_threadmode.setAdapter(arradp_thmode);
		
		et_path=(EditText) findViewById(R.id.editText_path);
		
		
		button_save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try{
				myapp.spf.SaveReaderParams(myapp.Rparams);
				}catch(Exception ex)
				{
					Toast.makeText(SubSystemActivity.this, "Save failed",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(SubSystemActivity.this, "executed!",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		button_default.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myapp.Rparams.opant=1;
				
		    	myapp.Rparams.invpro=new ArrayList<String>();
		    	myapp.Rparams.invpro.add("GEN2");
		    	
		    	myapp.Rparams.opro="GEN2";
		    	
		    	myapp.Rparams.uants=new int[]{1};

				myapp.Rparams.readtime=200;
				myapp.Rparams.sleep=0; 
				myapp.Rparams.checkant=1;
				 
				myapp.Rparams.rpow=new int[]{2000,2000,2000,2000};
				myapp.Rparams.wpow=new int[]{2000,2000,2000,2000};
				 
				myapp.Rparams.region=1;
				myapp.Rparams.frelen=0; 
				 
				myapp.Rparams.session=0; 
				myapp.Rparams.qv=-1; 
				myapp.Rparams.wmode=0; 
				myapp.Rparams.blf=0; 
				myapp.Rparams.maxlen=0; 
				myapp.Rparams.target=0; 
				myapp.Rparams.gen2code=2; 
				myapp.Rparams.gen2tari=0; 
		 
				myapp.Rparams.fildata="";
				myapp.Rparams.filadr=32;
				myapp.Rparams.filbank=1;
				myapp.Rparams.filisinver=0;
				myapp.Rparams.filenable=0; 
				
				myapp.Rparams.emdadr=0;
				myapp.Rparams.emdbytec=0; 
				myapp.Rparams.emdbank=1;
				myapp.Rparams.emdenable=0; 
				 
				myapp.Rparams.adataq=0; 
				myapp.Rparams.rhssi=1; 
				myapp.Rparams.invw=0; 
				myapp.Rparams.iso6bdeep=0; 
				myapp.Rparams.iso6bdel=0; 
				myapp.Rparams.iso6bblf=0; 
				myapp.spf.SaveReaderParams(myapp.Rparams);
				Toast.makeText(SubSystemActivity.this, "Reconnect the effect!",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		button_thread.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			myapp.ThreadMODE=spinner_threadmode.getSelectedItemPosition();
			if(myapp.ThreadMODE==1)
			{
				EditText et=(EditText) findViewById(R.id.editText_refreshtime);
				myapp.refreshtime=Integer.valueOf(et.getText().toString());
			}
		}
		
	});
		
		button_path.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SubSystemActivity.this, SubPathActivity.class);
				startActivityForResult(intent, 0); 
			}
			
		});
		button_out.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!et_path.getText().toString().contains("txt")&&
						!et_path.getText().toString().contains("csv"))
				{
					Toast.makeText(SubSystemActivity.this, "input txt or csv",
							Toast.LENGTH_SHORT).show();
				}
				File file=new File(et_path.getText().toString());
	           	
	           	try {
	           		FileOutputStream fs=new FileOutputStream(file);
	           		String wstr="EPC,Count,\r\n";
	           		fs.write(wstr.getBytes());
	           		 
	           		List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
	       		    Iterator<Entry<String, TAGINFO>> iesb;
	       		    iesb=myapp.Devaddrs.entrySet().iterator();
	                int j=1;
	             
	                   while(iesb.hasNext())
	                  { 
	                    TAGINFO bd=iesb.next().getValue();
	                    String linestr= Reader.bytes_Hexstr(bd.EpcId)+",";
	                    linestr+=String.valueOf(bd.ReadCnt)+",";
	                    linestr+="\r\n";
	                    fs.write(linestr.getBytes());
	                  }
	                   Toast.makeText(SubSystemActivity.this, " Save successful",
								Toast.LENGTH_SHORT).show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		});
	}

}
