package com.movies.catagory;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddLayout
{
	
	public static void addLayouts(int a) 
	{
		TextView title = new TextView(Catagory.catagoryInstance);
		title.setText("" + Catagory.carasolLoopCounter);

		HorizontalScrollView scrollView = new HorizontalScrollView(Catagory.catagoryInstance);
		LinearLayout topLinearLayout = new LinearLayout(Catagory.catagoryInstance);
		LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
			
			
		RelativeLayout.LayoutParams params = 
			    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
			        RelativeLayout.LayoutParams.WRAP_CONTENT);
			
		LinearLayout topLinearLayout1 = new LinearLayout(Catagory.catagoryInstance);
		LinearLayout.LayoutParams tParams1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		topLinearLayout1.setOrientation(LinearLayout.VERTICAL);
			
		for(int i = 1; i < 31; i++)
		{
			final ImageView im = new ImageView(Catagory.catagoryInstance);
			im.setScaleType(ScaleType.FIT_XY);
			Catagory.imageLoader.DisplayImage(Carasol.images[i], im);
			RelativeLayout.LayoutParams imParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			imParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			imParams.height = 150;
			imParams.width = 200;
			im.setTag(i);
				
			final TextView tv = new TextView(Catagory.catagoryInstance);
			tv.setText(""+Carasol.movieLength[i]);
			tv.setTextColor(Color.WHITE);
			tv.setBackgroundColor(Color.BLACK);
			RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			tvParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
			tvParams.setMargins(10, 90, 0, 0); //substitute parameters for left, top, right, bottom
					
			View v = new View(Catagory.catagoryInstance);
			v.setBackgroundColor(Color.WHITE);
			RelativeLayout.LayoutParams vL= new RelativeLayout.LayoutParams(5,10); 
			vL.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
					 
			final RelativeLayout rl = new RelativeLayout(Catagory.catagoryInstance);
			rl.addView(im, imParams);
			rl.addView(tv, tvParams);
					
			topLinearLayout.addView(rl,params);
			topLinearLayout.addView(v, vL);
			im.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					// Log.e("Tag"," "+imageView[ii].getTag());
						Toast.makeText(Catagory.catagoryInstance.getBaseContext(),"row:"+Catagory.carasolLoopCounter+ " "+im.getTag(), Toast.LENGTH_SHORT).show();
					}
			});
		}

		scrollView.addView(topLinearLayout, tParams);
			
		topLinearLayout1.addView(title);
		topLinearLayout1.addView(scrollView);
		
		Catagory.ll1.addView(topLinearLayout1, tParams1);
		
		if(Catagory.carasolLoopCounter<a)
		{
			Catagory.urlLoopCounter = Catagory.urlLoopCounter+5;
			Log.d("AddLayout", "urlLoopCounter:"+Catagory.urlLoopCounter);
			Carasol.makeJsonObjectRequest("http://site.bongobd.com/api/category.php?catID=1",a);
		}
	}
}
