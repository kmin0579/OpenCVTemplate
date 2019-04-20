package com.kmin.OpenCVTemplate;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import org.opencv.core.*;
import org.opencv.android.*;
import org.opencv.imgproc.*;
import android.util.*;

public class TemplateCVClass
{
	public Bitmap process(Bitmap src_bmp)
	{
		Bitmap ret_bmp = Bitmap.createBitmap(src_bmp.getWidth(),src_bmp.getHeight(),Config.RGB_565);
		
		Mat src_rgb_mat = new Mat();
		Mat ret_gray_mat = new Mat();
		
		Utils.bitmapToMat(src_bmp,src_rgb_mat);
		Imgproc.cvtColor(src_rgb_mat,ret_gray_mat,Imgproc.COLOR_RGB2GRAY);
		Utils.matToBitmap(ret_gray_mat,ret_bmp);
		
		return ret_bmp;
	}
}
