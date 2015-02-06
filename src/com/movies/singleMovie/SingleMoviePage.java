package com.movies.singleMovie;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bongo1st.R;
import com.movies.browseAll.Movies;
import com.movies.movieSummary.MovieSummary;
import com.tab.TabAndListView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class SingleMoviePage extends TabActivity {
	// Declare Variables
	String movieName;
	String movieDirector;
	String movieViews;
	String movieImage;
	String movieSummary;
	public static String movieShortSummary;
	public static String movieCategory;
	public static String moviePostedOn;
	public static String movieAvgRating;
	String movieReleaseDate;
	String movieDuration;
	String movieGenre;
	String movieContentDetails;
	String movieUrl;
	String position;
	String formattedYear, formattedMonth, formattedDay;
	
	public String detailsId, roleName , roleId, artistProfileImage, 
		   contentId, artistId, artistName;
	
	public static float movieRating;
	
	public WebView webView;
	//public RatingBar ratingBar;
	public TextView tvRatingValue, tvMovieName, 
			        tvDirector, tvViews, tvCategory,
			        tvMovieCategory, tvPostedOn, tvShortSummary;
	
	public Button btnRate, btnFullScreen;
	public int height, width, count;
	public ViewGroup.LayoutParams vc;
	public int finalHeight, finalWidth;
	
	// Volley's request queue
    private RequestQueue requestQueue;
    
    // Progress dialog
 	private ProgressDialog pDialog;
 	public SingleMoviePage singleMovieInstance;
	
	public static String DEBUG_TAG = SingleMoviePage.class.getSimpleName();
	
	public String id;
	
	LinearLayout directorLayout, infoLayout;
	public JSONArray cast;
	public JSONObject data, additionalData;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// Get the view from singleitemview.xml
		setContentView(R.layout.single_movie_page);
		
		singleMovieInstance = this;
		
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		
		tvDirector = (TextView) findViewById(R.id.tvDirectorName);
		tvViews = (TextView) findViewById(R.id.tvViews);
//		tvShortSummary = (TextView) findViewById(R.id.tvShortSummary);
//		tvCategory = (TextView) findViewById(R.id.tvCategory);
//		tvPostedOn = (TextView) findViewById(R.id.tvPostedOn);
		tvMovieName = (TextView) findViewById(R.id.tvMovieName);
		tvMovieName.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		tvMovieName.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v)
			{
			    finish();
			    Intent i = new Intent(getBaseContext(), MovieSummary.class);
			    i.putExtra("movieName", movieName);
			    i.putExtra("movieImage", movieImage);
			    i.putExtra("movieSummary", movieSummary);
			    i.putExtra("movieDuration", movieDuration);
			    i.putExtra("movieReleaseDate", movieReleaseDate);
			    i.putExtra("movieGenre", movieGenre);

			    i.putExtra("json", data.toString());
//			    i.putExtra("roleName", roleName);
//			    i.putExtra("artistName", artistName);
			    
			    startActivity(i);
			}
		});
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;
		
		webView = (WebView)findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		vc=webView.getLayoutParams();
		vc.height=height/2;
		Log.d("Screen Height:", "vc.height:"+vc.height);
		webView.setLayoutParams(vc);
		
		ViewTreeObserver vto = webView.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
		    public boolean onPreDraw() {
		    	webView.getViewTreeObserver().removeOnPreDrawListener(this);
		        finalHeight = webView.getMeasuredHeight();
		        finalWidth = webView.getMeasuredWidth();
		        //tv.setText("Height: " + finalHeight + " Width: " + finalWidth);
		        Log.d("", "Height: " + finalHeight + " Width: " + finalWidth);
		        return true;
		    }
		});
		 
		//webView.setInitialScale(120);
		webView.setHorizontalScrollBarEnabled(true);
//		webView.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				
//				finish();
//				startActivity(new Intent(getBaseContext(), PlayMovie.class));
//				System.runFinalizersOnExit(true);
//				System.exit(0);
//				android.os.Process.killProcess(android.os.Process.myPid());
//				return false;
//			}
//		});
	
		//id = ShareData.loadSavedPreferences(singleMovieInstance, "id");
		
        //addListenerOnRatingBar();
        //addFullScreenButton();
        
		Intent i = getIntent();
		id = i.getStringExtra("movieId");

		//get json data
		makeJsonObjectRequest("http://site.bongobd.com/api/content.php?id="+id);
		
		infoLayout = (LinearLayout)findViewById(R.id.infoLayout);
		directorLayout = (LinearLayout)infoLayout.findViewById(R.id.directorLayout);
		
	}
	
	
//	  public void addRateItButton() 
//	  {
//		//ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//		btnRate = (Button) findViewById(R.id.btnRate);
//	 
//		//if click on me, then display the current rating value.
//		btnRate.setOnClickListener(new OnClickListener() {
//	 
//			@Override
//			public void onClick(View v) {
//	 
//				Toast.makeText(SingleMoviePage.this,
//					String.valueOf(ratingBar.getRating()+" "+height),
//						Toast.LENGTH_SHORT).show();
//	 
//			}
//	 
//		});
//	 
//	  }
	  
//	  public void addFullScreenButton()
//	  {
//		  btnFullScreen = (Button) findViewById(R.id.btnFullScreen);
//		  btnFullScreen.setOnClickListener(new View.OnClickListener() 
//		  {
//			
//			@Override
//			public void onClick(View v)
//			{
//				// TODO Auto-generated method stub
//				finish();
//				startActivity(new Intent(getBaseContext(), PlayMovie.class));
//			}
//		});
//	  }
	  
	  @Override
	  public void onBackPressed()
	  {
		    super.onBackPressed();

			finish();
			startActivity(new Intent(getBaseContext(), TabAndListView.class));
			System.runFinalizersOnExit(true);
			System.exit(0);
			android.os.Process.killProcess(android.os.Process.myPid());
	  }
	  
	  
	  
		/**
		 * Method to make json object request where json response starts wtih {
		 * */
		private void makeJsonObjectRequest(String urlJsonObj) {

			showpDialog();
			// Create an array
						
			requestQueue = Volley.newRequestQueue(this);
			JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
					urlJsonObj, null, new Response.Listener<JSONObject>() 
					{
						@Override
						public void onResponse(JSONObject response) 
						{
							Log.d(DEBUG_TAG, response.toString());

							try {
								data = response.getJSONObject("data");
								//Log.d(DEBUG_TAG, "data: "+data); 
								additionalData = response.getJSONObject("additionalData");
								Log.d(DEBUG_TAG, " additionalData:"+ additionalData );
								movieUrl = additionalData.getString("iframe2");
								Log.d(DEBUG_TAG, " movieUrl:"+ movieUrl );
								
								
								movieName = data.getString("content_title");
					            Log.d(DEBUG_TAG, "movieName:"+movieName );
					            
					            movieImage = "http://site.bongobd.com/wp-content/themes/bongobd/images/posterimage/thumb/"+data.getString("content_thumb");
					            Log.d(DEBUG_TAG, "movieImage:"+movieImage );
					            
					        	movieDirector = data.getString("by");
								Log.d(DEBUG_TAG, "movieDirector: "+movieDirector); 
								//if there is no director then delete it
								if(movieDirector.length()==0)
								{
									infoLayout.removeView(directorLayout);
								}
								else
								{
									tvDirector.setText(movieDirector);
								}
								
								movieViews = data.getString("total_view");
								Log.d(DEBUG_TAG, "movieViews: "+movieViews); 
								
								movieCategory = data.getString("category_name");
								Log.d(DEBUG_TAG, "movieCategory: "+movieCategory); 
								
								moviePostedOn = data.getString("entry_time");
								Log.d(DEBUG_TAG, "moviePostedOn: "+moviePostedOn); 

//								moviePostedOn = DateFormatter(moviePostedOn);
//								Log.d(DEBUG_TAG, "DateFormatter: "+moviePostedOn); 
								
								movieShortSummary = data.getString("content_short_summary");
								Log.d(DEBUG_TAG, "movieShortSummary: "+movieShortSummary);

								movieSummary = data.getString("content_summary");
								Log.d(DEBUG_TAG, "movieSummary: "+movieSummary);

								movieReleaseDate = data.getString("release_date");
								Log.d(DEBUG_TAG, "movieReleaseDate: "+movieReleaseDate);
								//movieReleaseDate = DateFormatter(movieReleaseDate);
								//Log.d(DEBUG_TAG, "movieReleaseDate: "+movieReleaseDate);
								
								movieAvgRating = data.getString("avg_rating");
								//Log.d(DEBUG_TAG, "movieAvgRating:"+movieAvgRating.length());
								if(movieAvgRating.length()>4)
								{
									//Log.d(DEBUG_TAG, "movieAvgRatingNot : not null");
									Log.d(DEBUG_TAG, "movieAvgRatingNot : "+movieAvgRating);
									movieRating = Float.parseFloat(movieAvgRating);
								}
								else  
								{
									Log.d(DEBUG_TAG, "movieAvgRatingNull: null");
									
								}
								//Log.d(DEBUG_TAG, "movieAvgRating:"+movieAvgRating);
								
								if(data.has("content_details"))
								{
									movieContentDetails = data.getString("content_details");
									Log.d(DEBUG_TAG, "movieContentDetails: "+movieContentDetails);
								
									//Parse Movie Casts
									ArrayList<String> allNames = new ArrayList<String>();
									cast = data.getJSONArray("content_details");
									for(int i=0; i<cast.length(); i++) 
									{
										JSONObject actor = cast.getJSONObject(i);
										detailsId = actor.getString("details_id");
										contentId = actor.getString("content_id");
										artistId = actor.getString("artist_id");
										artistName = actor.getString("artist_name");
										artistProfileImage = actor.getString("artist_profile_image");
										roleName = actor.getString("role_name");
										roleId = actor.getString("role_id");
								    
										Log.d(DEBUG_TAG, "detailsId: "+detailsId);
										Log.d(DEBUG_TAG, "contentId: "+contentId);
										Log.d(DEBUG_TAG, "artistId: "+artistId);
										Log.d(DEBUG_TAG, "artistName: "+artistName);
										Log.d(DEBUG_TAG, "artistProfileImage: "+artistProfileImage);
										Log.d(DEBUG_TAG, "roleName: "+roleName);
										Log.d(DEBUG_TAG, "roleId: "+roleId);
								    
										//allNames.add(roleName);
									}
								}
								
							}
							catch (JSONException e) 
							{
								e.printStackTrace();
								Toast.makeText(getApplicationContext(),
										"Error: " + e.getMessage(),
										Toast.LENGTH_LONG).show();
							}
							hidepDialog();
						}
					}, new Response.ErrorListener() { 

						@Override
						public void onErrorResponse(VolleyError error) 
						{
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

		//useage: movieSummary activity page & this activity page
//		public static String DateFormatter(String Dates)
//		{
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy"); 
//			SimpleDateFormat monthFormat = new SimpleDateFormat("MM"); 
//			SimpleDateFormat dayFormat = new SimpleDateFormat("dd"); 
//		 
//			String formattedYear1 = null, formattedMonth1 = null, formattedDay1 = null;
//			
//			try {
//		 
//				Date dates = formatter.parse(Dates);
//				formattedYear1 = yearFormat.format(dates.getTime());  
//				Log.d(DEBUG_TAG, "formattedYear1: "+formattedYear1); 
//				
//				formattedMonth1 =  monthFormat.format(dates.getTime());  
//				formattedMonth1 = getMonth(Integer.parseInt(formattedMonth1));
//				formattedMonth1 = formattedMonth1.substring(0, 3);
//				Log.d(DEBUG_TAG, "formattedMonth1: "+formattedMonth1);
//				
//				formattedDay1 = dayFormat.format(dates.getTime());
//				Log.d(DEBUG_TAG, "formattedDay1: "+formattedDay1);
//				
//		 
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			
//			return formattedMonth1+" "+formattedDay1+", "+formattedYear1;
//		}
//		public static String getMonth(int month) 
//		{
//		    return new DateFormatSymbols().getMonths()[month-1];
//		}
		
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
				
				tvViews.setText(movieViews);
//				tvShortSummary.setText(movieShortSummary);
//				tvCategory.setText(movieCategory);
				tvMovieName.setText(movieName);
//				tvPostedOn.setText(moviePostedOn);
//				ratingBar.setRating(movieRating);
				addTabView();
				webView.loadUrl(movieUrl);
			
			}
		}

		public static View createTabView(Context context, String tabText)
		{
		    View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null, false);
		    TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
		    tv.setTextSize(15);
	        tv.setText(tabText);
	        
		    return view;
		}
		 
		public void addTabView()
		{
			TabHost tabHost = getTabHost();  // The activity TabHost
		    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
            Intent intent;  // Reusable Intent for each tab
	        // Create an Intent to launch an Activity for the tab (to be reused)
		    intent = new Intent().setClass(this, TestActivity.class);
		    tabHost.getTabWidget().setStripEnabled(false);
	        tabHost.getTabWidget().setDividerDrawable(null);
		        
		    // Create our custom view. 
		    View tabView = createTabView(this, "Details");
	        // Initialize a TabSpec for each tab and add it to the TabHost
		    spec = tabHost.newTabSpec("tab1").setIndicator(tabView)
		              .setContent(intent);
	        tabHost.addTab(spec);
		      
		        
	        // Do the same for the other tabs
		    tabView = createTabView(this, "Description");
	        intent = new Intent().setClass(this, TestActivity.class);
		    spec = tabHost.newTabSpec("tab2").setIndicator(tabView)
		             .setContent(intent);
		    tabHost.addTab(spec);
		        
		         
		    // Do the same for the other tabs
		    tabView = createTabView(this, "Share");
		    intent = new Intent().setClass(this, TestActivity.class);
		    spec = tabHost.newTabSpec("tab3").setIndicator(tabView)
		              .setContent(intent);
		    tabHost.addTab(spec);
		}
		 
}