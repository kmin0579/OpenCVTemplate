package com.kmin.OpenCVTemplate;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import org.opencv.android.*;
import android.util.*;
import android.view.View.*;
import android.content.*;
import android.net.*;
import android.graphics.*;
import java.io.*;

public class MainActivity extends Activity
{
	private final String TAG = "opencv load";
	
	Button btn_si,btn_proc;
	ImageView iv_src,iv_ret;
	TextView tv_msg;
	
	Bitmap src_bmp,ret_bmp;
	
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) 
	{
		public void onManagerConnected(int status) 
		{
			switch (status) 
			{
				case LoaderCallbackInterface.SUCCESS:
				{
					Log.i(TAG, "OpenCV loaded successfully");
				} 
				break;
				default:
				{
					super.onManagerConnected(status);
				} 
				break;
			}
		}
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		initUI();
    }
	//界面初始化
	public void initUI()
	{
		iv_src = (ImageView)findViewById(R.id.iv_src);
		iv_ret = (ImageView)findViewById(R.id.iv_ret);
		tv_msg = (TextView)findViewById(R.id.tv_msg);
		
		btn_si = (Button)findViewById(R.id.btn_si);
		btn_proc = (Button)findViewById(R.id.btn_proc);
		
		btn_si.setOnClickListener(new BtnClickListener());
		btn_proc.setOnClickListener(new BtnClickListener());
	}
	
	/**static{
		OpenCVLoader.initDebug();
		if (OpenCVLoader.initDebug()) {
			//System.loadLibrary("opencv_java");// load other libraries
			Log.d("DEBUG","init debug");
		}
	}*/
	
	//按钮点击
	public class BtnClickListener implements OnClickListener
	{
		@Override
		public void onClick(View p1)
		{
			// TODO: Implement this method
			switch(p1.getId())
			{
				case R.id.btn_si:
				{
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intent,1);
				}
				break;
				case R.id.btn_proc:
				{
					TemplateCVClass tc = new TemplateCVClass();
					ret_bmp = tc.process(src_bmp);
					iv_ret.setImageBitmap(ret_bmp);
				}
				break;
				default: break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO: Implement this method
		if(resultCode == RESULT_OK)
		{
			Uri uri = data.getData();
			ContentResolver cr = this.getContentResolver();
			try{
				src_bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
				iv_src.setImageBitmap(src_bmp);
			}catch(FileNotFoundException e){
				Log.d("get src img error",e.toString());
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void onResume()
    {
        super.onResume();
		OpenCVLoader.initDebug();
        Log.d(TAG, "OpenCV library found inside package. Using it!");
        mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
    }
}
