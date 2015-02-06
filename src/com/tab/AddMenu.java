package com.tab;

import com.example.bongo1st.R;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class AddMenu 
{
	
	public Activity context;
	
	public AddMenu(TabAndListView con)
	{
		context = con;
	}
	int height, h1;
	public void addMenus()
	{
		ScrollView sv = (ScrollView)context.findViewById(R.id.scrollView);
		sv.setLayoutParams(new LayoutParams(ShareData.getScreenWidth(context)- ShareData.getScreenWidth(context)/4,
                LayoutParams.FILL_PARENT));
		
		LinearLayout mainLayout = (LinearLayout)context.findViewById(R.id.mainLayout);
		for(int a=0; a<4; a++)
		{
			final LinearLayout ll1 = new LinearLayout(context);
			LinearLayout.LayoutParams llM1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			ll1.setBackgroundColor(Color.parseColor("#D8D8D8"));
			ll1.setLayoutParams(llM1);
			ll1.setId(a);
			ll1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context.getBaseContext(), "a:"+v.getId(), Toast.LENGTH_SHORT).show();
				}
			});
		
			ImageView iv = new ImageView(context);
			iv.setBackgroundColor(Color.RED);
			iv.setImageResource(R.drawable.ic_launcher);
			LinearLayout.LayoutParams iv1 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,2);
			iv1.setMargins(10, 10, 10, 10);
			iv.setLayoutParams(iv1);
		
			TextView tv = new TextView(context);
			if(a==0)
			{
				tv.setText("Home");
			}
			else if(a==1)
			{
				tv.setText("My Favourite");
			}
			else if(a==2)
			{
				tv.setText("Queue");
			}
			else if(a==3)
			{
				tv.setText("My History");
			}
			tv.setGravity(Gravity.FILL_VERTICAL);
			LinearLayout.LayoutParams tv1 = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,8);
			tv.setLayoutParams(tv1);
			
	
			View v = new View(context);
			v.setBackgroundColor(Color.parseColor("#A4A4A4"));
			LinearLayout.LayoutParams v1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 3);
		
			ll1.addView(iv);
			ll1.addView(tv);
		
			mainLayout.addView(ll1);
			mainLayout.addView(v,v1);

			mainLayout.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					height= ll1.getHeight();
					Log.d("AddMenu", "height:"+height);
				}
			});

			
		}
		
		h1=height;
		
		for(int t=0; t<5; t++)
		{
		
		LinearLayout ll2 = new LinearLayout(context);
		LinearLayout.LayoutParams llM2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 56);
		ll2.setBackgroundColor(Color.parseColor("#D8D8D8"));
		
		ll2.setLayoutParams(llM2);
//		ll2.setId(a);
//		LayoutParams params = ll2.getLayoutParams();
		ll2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context.getBaseContext(), "a:"+v.getId(), Toast.LENGTH_SHORT).show();
			}
		});
		
		TextView tv1 = new TextView(context);
		tv1.setGravity(Gravity.FILL_VERTICAL);
		if(t==0)
		{
			tv1.setText("Catagories");
			tv1.setTextColor(Color.RED);
		}
		else if(t==1)
		{
			tv1.setText("Movies");
		}
		else if(t==2)
		{
			tv1.setText("TV");
		}
		else if(t==3)
		{
			tv1.setText("OTHERS");
		}
		else if(t==4)
		{
			tv1.setText("CELEBS");
		}
		
		tv1.setPadding(10, 0, 0, 0);
		LinearLayout.LayoutParams tv2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,8);
		tv1.setLayoutParams(tv2);
		
		View v1 = new View(context);
		v1.setBackgroundColor(Color.parseColor("#A4A4A4"));
		LinearLayout.LayoutParams v2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 2);

		
//		ll2.addView(v1, v2);
		ll2.addView(tv1);
		
		mainLayout.addView(ll2);
		}
	}
}
