package com.movies.catagory;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bongo1st.ImageLoader;
import com.example.bongo1st.R;
import com.movies.browseAll.Movies;
import com.movies.startingPage.StartingPage;
import com.tab.ShareData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Catagory extends Activity 
{
	public static String DEBUG_TAG = Catagory.class.getSimpleName();

	// Volley's request queue
	private RequestQueue requestQueue;

	// Progress dialog
	private ProgressDialog pDialog;
	
	public static ImageLoader imageLoader;

	public String[] images;
	int imageCounterSlider=0;
	
	public static Timer timer;
	public static TimerTask timerTask;
	public static int counter=0;

	public int errorCheck=0;

	public static int carasolLoopCounter=0, urlLoopCounter=0;
	static HorizontalScrollView listview;
	public static LinearLayout mLinearLayout, ll1;
	ImageView iv1,iv2,iv3,iv4,iv5;
	public static boolean flag;
	static int widthScreen;
	public static int loop;
	
	public static Catagory catagoryInstance;
//	public static String[] totalImages, movieLength;
//	static int imageCounterCarasol=0;
//	public static ArrayList<String> urls = new ArrayList<String>();
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catagory);
		
		catagoryInstance = this;
		
		if(ShareData.isNetworkAvailable()==true)
		{
			
			carasolLoopCounter = 0;
			urlLoopCounter = 0;
			errorCheck = 0;
		
			flag=false;
			mLinearLayout = (LinearLayout) findViewById(R.id.ll);
			ll1 = (LinearLayout) findViewById(R.id.ll1);
			listview = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);

			final int count = ((LinearLayout) listview.getChildAt(0))
					.getChildCount();
			Log.d("Slider", "count:"+count);
        
			DisplayMetrics metrics = this.getResources().getDisplayMetrics(); 
			widthScreen = metrics.widthPixels;
    	    Log.d("Slider", "widthScreen:"+widthScreen);
			imageLoader = new ImageLoader(this);
			
//			pDialog = new ProgressDialog(this);
//			pDialog.setMessage("Please wait...");
//			pDialog.setCancelable(false);
		
			pDialog = ProgressDialog.show(this, "", "");
			pDialog.setContentView(R.layout.custom_progress);
			pDialog.show();
			pDialog.setCancelable(false);

			// get json data
			makeJsonObjectRequest("http://site.bongobd.com/api/category.php?catID=1");
		
//			startSlider();
//			listViewOnClickListener();
		}
		else
		{
			Toast.makeText(getBaseContext(), "No Internet", Toast.LENGTH_SHORT).show();
		}
	}

	public static void listViewOnClickListener()
	{
		listview.setOnTouchListener(new View.OnTouchListener() 
		{ 
			//outer scroll listener         
	        private float mx, my, curX, curY, downX, moveX;
	        private boolean started = false;
	        
	        @Override
	        public boolean onTouch(View v, MotionEvent event)
	        {
	            curX = event.getX();
	            curY = event.getY();
	            
	            int dx = (int) (mx - curX);
	            int dy = (int) (my - curY);
	            
	            switch (event.getAction()) {
	                case MotionEvent.ACTION_MOVE:
	                    if (started) {
	                    	moveX = event.getX();
//	                    	listview.scrollBy(dx, 0);
	                    } else {
	                        started = true;
	                    }
	                    mx = curX;
	                    my = curY;
	                    
	                    flag=true;
	                    
	                    break;
	                case MotionEvent.ACTION_DOWN: 
	                	downX = event.getX();
	                    started = false;
	                    flag=true;
	                    break;
	                case MotionEvent.ACTION_UP: 
	                    started = false;
	                    
	                    if(counter<0)
	                    {
	                    	counter = 0;
	                    }
	                    
	                    Log.d("Slider", "counters:"+counter);
	                    if(downX<moveX)
	                    {
//	    	            	Log.d("Slider", "ACTION_RIGHT:");
	                    	counter =counter- widthScreen;
	    	            	listview.smoothScrollTo(counter, 0); 
	                    }
	    	            else 
	    	            {
//	    	 	            Log.d("Slider", "ACTION_LEFT:");
	    	            	counter =counter+ widthScreen;
    	            		listview.smoothScrollTo(counter, 0); 
    	            		
	    	            	if(counter>=widthScreen*29)
	    	            	{
	    	            		counter = widthScreen*28;
	    	            	}
	    	            }
	                    
	                    final Handler handler = new Handler();
	                    handler.postDelayed(new Runnable() {
	                        @Override
	                        public void run() {
	                            // Do something after 5s = 5000ms
	                        	flag=false;
	                        }
	                    }, 1500);
	                    break;
	            }
	            
	          
	            return true;
	        }
	    });
		
	}

	public static void startSlider() 
	{
		try {
			timer = new Timer();
			timerTask = new TimerTask() {
				@Override
				public void run() {
					if(flag==false)
					{
						counter=counter+widthScreen;
						//Log.d("Slider", "counter:"+counter);
					
						if(counter>(widthScreen*28))
						{
							counter=0;
						}
						listview.smoothScrollTo(counter, 0); 
					}
				}
			};
			timer.schedule(timerTask, 1000, 4000);
		} 
		catch (IllegalStateException e) 
		{
			android.util.Log.i("Damn", "resume error");
		}
	}

	private void makeJsonObjectRequest(String urlJsonObj)
	{
		showpDialog();

		requestQueue = Volley.newRequestQueue(this);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>()
				{

					@Override
					public void onResponse(JSONObject response) 
					{
						Log.d(DEBUG_TAG, response.toString());
						try 
						{
							JSONObject js = response.getJSONObject("data");
							//Log.d(DEBUG_TAG, "js: " + js);

							images = new String[js.length()+1];
							
							
							Iterator<String> iter = js.keys();
							while (iter.hasNext()) 
							{
								String key = iter.next();
								try 
								{
									Object value = js.get(key);
									//Log.d(DEBUG_TAG, "value:" + value);
									imageCounterSlider++;
									JSONObject eachObject = js.getJSONObject(""+ key);

									String content_title = eachObject
											.getString("content_title");
									//Log.d(DEBUG_TAG, "content_title: "+ content_title);

									String content_thumb = eachObject
											.getString("content_thumb");
									content_thumb = "http://site.bongobd.com/wp-content/themes/bongobd/"
											+ "images/posterimage/thumb/"
											+ content_thumb;
//									Log.d(DEBUG_TAG, "content_thumb: "
//											+ content_thumb);

									images[imageCounterSlider] = content_thumb;
									
									Log.d(DEBUG_TAG, "content_thumb: "
											+ images[imageCounterSlider]);
								} 
								catch (JSONException e) 
								{
									// Something went wrong!
								}
							}

						} catch (JSONException e) 
						{
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
							"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
							errorCheck = 1;
						}
						hidepDialog();
					}
				}, 
				new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error) 
					{
						VolleyLog.d(DEBUG_TAG, "Error: " + error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						errorCheck = 1;
						// hide the progress dialog
						hidepDialog();
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}

	private void showpDialog() 
	{
		if (!pDialog.isShowing()) 
		{
			pDialog.show();
		}
	}

	@SuppressLint("NewApi")
	private void hidepDialog()
	{
		if(pDialog.isShowing())
		{
		
			pDialog.dismiss();
			
			if(errorCheck==0)
			{
				for (int i = 1; i < 31; i++) 
				{
					ImageView imageView = new ImageView(this);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widthScreen, 300);
					imageView.setLayoutParams(layoutParams);
					imageView.setScaleType(ScaleType.FIT_XY);
					imageLoader.DisplayImage(images[i], imageView);
					mLinearLayout.addView(imageView);
				}
			    loop = 12;
				Carasol.makeJsonObjectRequest("http://site.bongobd.com/api/category.php?catID=1", loop);
			}
		}
	}

	
	
	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
		finish();
		startActivity(new Intent(getBaseContext(), StartingPage.class));
		//ShareData.killPage();
	}
	
}
