package com.movies.catagory;

import java.util.ArrayList;
import java.util.Iterator;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Carasol  
{

	// Volley's request queue
	private static RequestQueue requestQueue;

	// Progress dialog
	private static ProgressDialog pDialog;
	public static ImageLoader imageLoader;
	public static String[] images = new String[500];
	public static String[] movieLength = new String[500];
	static int imageCounter=0;
	public static ArrayList<String> urls = new ArrayList<String>();
//	public static int carasolCounter=0;
	public static String DEBUG_TAG = Carasol.class.getSimpleName();
	
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) 
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		
//		mainInstance = this;
//		
//		imageLoader = new ImageLoader(this);
//		pDialog = new ProgressDialog(this);
//		pDialog.setMessage("Please wait...");
//		pDialog.setCancelable(false);
//		
//		urls.add("http://site.bongobd.com/api/category.php?catID=1");
//
//		// get json data
//		makeJsonObjectRequest("http://site.bongobd.com/api/category.php?catID=1", 12);
//		
//		//addLayouts(4);
//	}
	
	public static void makeJsonObjectRequest(String urlJsonObj, final int b)
	{
		
//		pDialog = new ProgressDialog(Catagory.catagoryInstance);
//		pDialog.setMessage("Please wait...");
//		pDialog.setCancelable(false);
		
//		pDialog = ProgressDialog.show(Catagory.catagoryInstance, "", "");
//		pDialog.setContentView(R.layout.custom_progress);
//		pDialog.show();
//		pDialog.setCancelable(false);
		
	//	showpDialog1();
//		images = null;
//		movieLength = null;

		Catagory.carasolLoopCounter++;
		Log.d("Carasol", "carasolLoopCounter:"+Catagory.carasolLoopCounter);
		
		requestQueue = Volley.newRequestQueue(Catagory.catagoryInstance);
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
							//Log.d("DEBUG_TAG", "js: " + js);

							imageCounter=0;
							
							Iterator<String> iter = js.keys();
							while (iter.hasNext()) 
							{ 
								String key = iter.next();
								try 
								{
									Object value = js.get(key);
									//Log.d("DEBUG_TAG", "value:" + value);
									imageCounter++;
									JSONObject eachObject = js.getJSONObject(""+ key);

									String content_length = eachObject
											.getString("content_length");
									//Log.d("DEBUG_TAG", "content_length: "+ content_length);

									String content_thumb = eachObject
											.getString("content_thumb");
									content_thumb = "http://site.bongobd.com/wp-content/themes/bongobd/"
											+ "images/posterimage/thumb/"
											+ content_thumb;
//									Log.d("DEBUG_TAG", "content_thumb: "
//											+ content_thumb);

									images[imageCounter] = content_thumb;
									movieLength[imageCounter] = content_length;
									
//									Log.d("DEBUG_TAG", "content_thumb: "
//											+ images[imageCounter]);
								} 
								catch (JSONException e) 
								{
									// Something went wrong!
								}
							}

						} 
						catch (JSONException e) 
						{
							e.printStackTrace();
							Toast.makeText(Catagory.catagoryInstance.getApplicationContext(),
							"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
						}
						hidepDialog1(b);
					}
				}, 
				new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error) 
					{
						VolleyLog.d("DEBUG_TAG", "Error: " + error.getMessage());
						Toast.makeText(Catagory.catagoryInstance.getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog
						hidepDialog1(b);
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}

	public static void showpDialog1() 
	{
		if(!pDialog.isShowing()) 
		{
			pDialog.show();
		}
	}

	public static void hidepDialog1(int b)
	{
//		if(pDialog.isShowing())
//		{
			
//			for (int i = 0; i < 30; i++)
//			{
//				ImageView imageView = new ImageView(this);
//				imageLoader.DisplayImage(images[i], imageView);
//				
//				//mViewFlipper.addView(imageView);
//			}
			//pDialog.dismiss();
//			AddLayout.addLayouts(b);
//		}
		AddLayout.addLayouts(b);
		
		if(Catagory.carasolLoopCounter == Catagory.loop)
		{
			Log.d(DEBUG_TAG, "Yes, add now");
			Catagory.startSlider();
			Catagory.listViewOnClickListener();
		}
	}
}
