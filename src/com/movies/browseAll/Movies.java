package com.movies.browseAll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bongo1st.R;
import com.movies.startingPage.StartingPage;
import com.tab.ShareData;


public class Movies extends Activity {
	// Declare Variables
	JSONObject jsonobject;
	JSONArray jsonarray;
	public static ListView listview;
	ListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	ArrayList<HashMap<String, String>> arraylist;
	public static String MOVIE_NAME = "movieName";
	public static String MOVIE_DIRECTOR = "movieDirector";
	public static String MOVIE_VIEWS = "movieViews";
	public static String MOVIE_IMAGE = "movieImage";
	public static String MOVIE_CONTENT_LENGTH = "contentLength";
	public static String MOVIE_SHORT_SUMMARY= "movieShortSummary";
	public static String MOVIE_SUMMARY= "movieSummary";
	public static String MOVIE_ID= "movieId";
	public static Movies moviesInstance;
	public static String DEBUG_TAG = Movies.class.getSimpleName();
	
	// Volley's request queue
    private RequestQueue requestQueue;
    
    // Progress dialog
 	private ProgressDialog pDialog;
 	public int counter=0, loopCounter=0, seeMoreCounter=0;
	public int mCurrentX, mCurrentY;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from listview_main.xml
		setContentView(R.layout.listview_movie);
		
		moviesInstance = this;
		if(ShareData.isNetworkAvailable()==true)
		{
			seeMoreCounter=0;
//			pDialog = new ProgressDialog(this);
//			//pDialog.setMessage("");
//			pDialog.setContentView(R.layout.custom_progress);
//			pDialog.setCancelable(false);
			
			pDialog = ProgressDialog.show(this, "", "");
			pDialog.setContentView(R.layout.custom_progress);
			pDialog.show();
			pDialog.setCancelable(false);
		    
			arraylist = new ArrayList<HashMap<String, String>>();
			//get json data
			makeJsonObjectRequest("http://site.bongobd.com/api/category.php?catID=1");
		}
		else
		{
			Toast.makeText(getBaseContext(), "No Internet", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * Method to make json object request where json response starts wtih {
	 * */
	private void makeJsonObjectRequest(String urlJsonObj) {

		showpDialog();
		// Create an array
		counter = 0;
		loopCounter = 0;
		requestQueue = Volley.newRequestQueue(Movies.moviesInstance);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(DEBUG_TAG, response.toString());

						try {
							JSONObject js = response.getJSONObject("data");
							//Log.d(DEBUG_TAG, "js: "+js); 
							
						    Iterator<String> iter = js.keys();
						    while(iter.hasNext()) 
						    {
						    	counter++;
						    	//Log.d(DEBUG_TAG, "number of items: "+counter); 
						        String key = iter.next();
//						        if(a<10)
						        try 
						        {
						            Object value = js.get(key);
						            //Log.d(DEBUG_TAG, "value:"+value ); 
						            
						            JSONObject eachObject = js.getJSONObject(""+ key);
						            
//						            String loop = eachObject.getString("loop");
						            loopCounter = Integer.parseInt("12");
						            //Log.d(DEBUG_TAG, "loopCounter: "+loopCounter); 
						            
						            String id = eachObject.getString("id");
						            //Log.d(DEBUG_TAG, "id: "+id); 
						            
						            String avg_rating = eachObject.getString("avg_rating");
									//Log.d(DEBUG_TAG, "avg_rating: "+avg_rating); 
									
									String content_title = eachObject.getString("content_title");
									//Log.d(DEBUG_TAG, "content_title: "+content_title);
									
									String entry_time = eachObject.getString("entry_time");
								    //Log.d(DEBUG_TAG, "entry_time: "+entry_time); 
										
									String content_thumb = eachObject.getString("content_thumb");
									content_thumb= "http://site.bongobd.com/wp-content/themes/bongobd/" +
											"images/posterimage/thumb/"+content_thumb;
									//Log.d(DEBUG_TAG, "content_thumb: "+content_thumb);
									
									String by = eachObject.getString("by");
									//Log.d(DEBUG_TAG, "by: "+by); 
									
									String total_view = eachObject.getString("total_view");
									//Log.d(DEBUG_TAG, "total_view: "+total_view); 
									
									String content_length = eachObject.getString("content_length");
									//Log.d(DEBUG_TAG, "content_length: "+content_length); 
									
									String content_short_summary = eachObject.getString("content_short_summary");
									//Log.d(DEBUG_TAG, "content_short_summary: "+content_short_summary); 
									
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("movieId", id);
									map.put("movieName", content_title);
									map.put("movieViews", total_view);
									map.put("movieDirector", by);
									map.put("movieImage", content_thumb);
									map.put("contentLength", content_length);
									map.put("movieShortSummary", content_short_summary);
									
									arraylist.add(map);
						        } 
						        catch (JSONException e)
						        {
						            // Something went wrong!
						        }
						    }
						    
						

						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
									"Error: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
						hidepDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(DEBUG_TAG, "Error: " + error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog
						hidepDialog();
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}
	
	
	private void showpDialog()
	{
		if(!pDialog.isShowing())
		{
			pDialog.show();
		}
	}

	private void hidepDialog() 
	{
		if(pDialog.isShowing())
		{
			
			pDialog.dismiss();
			
			createListView();
		}
	}
	public void createListView() 
	{
		// Locate the listview in listview_main.xml
		listview = (ListView) findViewById(R.id.listviewMovies);
		// Pass the results into ListViewAdapter.java
		adapter = new ListViewAdapter(Movies.this, arraylist);
		// Set the adapter to the ListView
		listview.setAdapter(adapter);
		
//		listview.scrollTo(mCurrentX, mCurrentY);
//		Log.d(DEBUG_TAG, "mmx:"+mCurrentX);
//		Log.d(DEBUG_TAG, "mmy:"+mCurrentY);
		
		if(seeMoreCounter<loopCounter){
		
		final LinearLayout ll = new LinearLayout(this);
		final Button btnLoadMore = new Button(this);
		btnLoadMore.setBackgroundColor(Color.RED);
		btnLoadMore.setText("See More");
		btnLoadMore.setTextSize(14);
		btnLoadMore.setPadding(25, 0, 25, 0);
		btnLoadMore.setTextColor(Color.WHITE);
		ListView.LayoutParams params = new ListView.LayoutParams
				(ListView.LayoutParams.WRAP_CONTENT,ListView.LayoutParams.WRAP_CONTENT);
		btnLoadMore.setLayoutParams(params);
		
		ll.setGravity(Gravity.CENTER);
		ll.addView(btnLoadMore);
		

		// Adding button to listview at footer
		listview.addFooterView(ll);
		btnLoadMore.setOnClickListener(new View.OnClickListener() {
			  
	            @Override
	            public void onClick(View arg0) {
	                // Starting a new async task
	            	listview.removeFooterView(ll);
	            	makeJsonObjectRequest("http://site.bongobd.com/api/category.php?catID=1");
	            	
	            	seeMoreCounter++;
	            	Log.d(DEBUG_TAG, "seeMoreCounter:"+seeMoreCounter);
	            }
	        });
		}

		
		listview.setOnScrollListener(new OnScrollListener()
		{
	        private int mLastFirstVisibleItem;

	        @Override
	        public void onScrollStateChanged(AbsListView view, int scrollState) 
	        {

	        }

	        @Override
	        public void onScroll(AbsListView view, int firstVisibleItem,
	                int visibleItemCount, int totalItemCount) 
	        {
	        	if(ListViewAdapter.popupWindowMenuDropDown!=null && 
	        			ListViewAdapter.popupWindowMenuDropDown.isShowing()==true)
            	{
	        		ListViewAdapter.popupWindowMenuDropDown.dismiss();
            	}
	        	
	            if(mLastFirstVisibleItem<firstVisibleItem)
	            {
	                Log.i("SCROLLING DOWN","TRUE");
	            }
	            if(mLastFirstVisibleItem>firstVisibleItem)
	            {
	                Log.i("SCROLLING UP","TRUE");
	            }
	            mLastFirstVisibleItem=firstVisibleItem;
	            
//	            mCurrentX = view.getScrollX();
//	            mCurrentY = view.getScrollY();
//	            Log.d(DEBUG_TAG, "mmx:"+mCurrentX);
//				Log.d(DEBUG_TAG, "mmy:"+mCurrentY);
	        }
	    });		
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
//		startActivity(new Intent(getBaseContext(), StartingPage.class));
//		ShareData.killPage();
	}
}