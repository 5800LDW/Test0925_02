package com.test.honeywell;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.antonioleiva.mvpexample.app.R;

public class HoneWellTestActivity extends Activity
{
	private final int PICK_IMAGE = 457544821; //Random number for intent
	private static boolean SD_Loaded = false;

    public native int DecodeImageSD(Bitmap bitmap);
    public native int LoadSD();
    public native int UnloadSD();
    public native String GetResultSD();


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_honeywelltest);


		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				if (!SD_Loaded)
				{
					System.loadLibrary("nativedecoder");
					if (LoadSD() == 1)
						Toast.makeText(getApplicationContext(), "SwiftDecoder Loaded", Toast.LENGTH_LONG).show();
					else
						Toast.makeText(getApplicationContext(), "SwiftDecoder Not Loaded", Toast.LENGTH_LONG).show();
				}
				SD_Loaded = true;

				findViewById(R.id.buttonDecode).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						//Start Image File Selection
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);

						startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
					}
				});
			}
		},3000);



	}


	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
        if (requestCode == PICK_IMAGE)
        {
        	 if (resultCode == RESULT_OK)
             {
            	//On Image selection done
                Uri selectedImageUri = data.getData();
				try
				{
					Bitmap mBitmap = Media.getBitmap(this.getContentResolver(), selectedImageUri);

					if (DecodeImageSD(mBitmap) == 0)
						Toast.makeText(getApplicationContext(), "Decoding failed", Toast.LENGTH_LONG).show();
					else
					{
						Toast.makeText(getApplicationContext(), "Decoding success", Toast.LENGTH_LONG).show();
						EditText editTextresult = (EditText)findViewById(R.id.editResult);
						editTextresult.setText(new String(GetResultSD()));
						Toast.makeText(getApplicationContext(), editTextresult.getText().toString(), Toast.LENGTH_LONG).show();
					}
				}
				catch (Exception e)
				{
					Toast.makeText(getApplicationContext(), "Can't open Image from Uri " + selectedImageUri, Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
            }
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		UnloadSD();
		super.onDestroy();
	}

}
