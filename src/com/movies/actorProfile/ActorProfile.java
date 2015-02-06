package com.movies.actorProfile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Html.ImageGetter;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bongo1st.ImageLoader;
import com.example.bongo1st.LeadingMarginSpan2;
import com.example.bongo1st.R;
import com.movies.browseAll.Movies;
import com.movies.singleMovie.SingleMoviePage;
import com.movies.startingPage.StartingPage;
import com.tab.TabAndListView;

public class ActorProfile extends Activity implements ImageGetter {
	// Progress dialog
	private ProgressDialog pDialog;
	public ActorProfile actorProfileInstance;
	public static String DEBUG_TAG = ActorProfile.class.getSimpleName();
	// Volley's request queue
	private RequestQueue requestQueue;

	public String actorBio, actorImage;
	public ImageView ivActorImage;
	public TextView tvActorBio, tvCastName, tvCastRole;
	public ImageLoader actorImageLoader;
	public TextView hide, show;

	public ViewGroup.LayoutParams ivLayoutParams;;
	public int screenheight, screenWidth;
	
	String castId, castName, castRole;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actor_profile_page);

		actorProfileInstance = this;

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenheight = displaymetrics.heightPixels;
		screenWidth = displaymetrics.widthPixels;
		
		Intent i = getIntent();
		castId = i.getStringExtra("artistId");
		Log.d(DEBUG_TAG, "castId:" + castId);
		castName = i.getStringExtra("artistName");
		Log.d(DEBUG_TAG, "castName:" + castName);
		castRole = i.getStringExtra("artistRole");
		
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);

		ivActorImage = (ImageView) findViewById(R.id.icon);
		tvActorBio = (TextView) findViewById(R.id.message_view);
		tvCastName = (TextView) findViewById(R.id.tvCastName);
		tvCastRole = (TextView) findViewById(R.id.tvCastRole);

		actorImageLoader = new ImageLoader(this);
		ivLayoutParams = ivActorImage.getLayoutParams();

		ivLayoutParams.width = screenWidth / 2 - 10;
		Log.d(DEBUG_TAG, "vc.width:" + ivLayoutParams.width);

		makeJsonObjectRequest("http://site.bongobd.com/api/artist.php?artist_id="+castId);

		show = (TextView) findViewById(R.id.show);
		show.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// System.out.println("Show button");
				show.setVisibility(View.INVISIBLE);
				hide.setVisibility(View.VISIBLE);
				tvActorBio.setMaxLines(Integer.MAX_VALUE);

			}
		});
		hide = (TextView) findViewById(R.id.hide);
		hide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("Hide button");
				hide.setVisibility(View.INVISIBLE);
				show.setVisibility(View.VISIBLE);
				tvActorBio.setMaxLines(4);

			}
		});
	}

	/**
	 * Method to make json object request where json response starts wtih {
	 * */
	private void makeJsonObjectRequest(String urlJsonObj) {

		showpDialog();
		// Create an array

		requestQueue = Volley.newRequestQueue(StartingPage.startInstance);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d(DEBUG_TAG, response.toString());

						try {
							JSONObject data = response.getJSONObject("data");
							Log.d(DEBUG_TAG, "data: " + data);

							actorBio = data.getString("bio");
							Log.d(DEBUG_TAG, "actorBio:" + actorBio);

							actorImage = "http://site.bongobd.com/wp-content/themes/bongobd/images/artistimage/profile/"
									+ data.getString("profile_image");
							Log.d(DEBUG_TAG, "movieImage:" + actorImage);

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


	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

//		int width = ivLayoutParams.width;
		int width = screenWidth/2;
		int height = ivActorImage.getHeight();

		Log.d(DEBUG_TAG, "width:" + width);
		Log.d(DEBUG_TAG, "height:" + height);

		int leftMargin = width + 10;

		// icon.setImageDrawable(getResources().getDrawable(R.drawable.download));
		float textLineHeight = tvActorBio.getPaint().getTextSize();
		int lines = (int) Math.round(height / textLineHeight);
		SpannableString ss = new SpannableString(actorBio);
		ss.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new LeadingMarginSpan2(lines, leftMargin), 0, ss.length(),
				0);
		tvActorBio.setText(ss);
		tvActorBio.setMaxLines(4);
	}


	private void showpDialog() {
		if (!pDialog.isShowing()) {
			pDialog.show();
		}
	}

	private void hidepDialog() {
		if (pDialog.isShowing()) {
			pDialog.dismiss();

			// tvViews.setText(movieViews);
			actorImageLoader.DisplayImage(actorImage, ivActorImage);
			tvCastName.setText(castName);
			tvCastRole.setText(castRole);
			
		}
	}

	@Override
	public Drawable getDrawable(String source) {
		LevelListDrawable d = new LevelListDrawable();
		Drawable empty = getResources().getDrawable(R.drawable.ic_launcher);
		d.addLevel(0, 0, empty);
		d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
		new LoadImage().execute(source, d);

		return d;
	}

	class LoadImage extends AsyncTask<Object, Void, Bitmap> {

		private LevelListDrawable mDrawable;

		@Override
		protected Bitmap doInBackground(Object... params) {
			String source = (String) params[0];
			mDrawable = (LevelListDrawable) params[1];
			Log.d("", "doInBackground " + source);
			try {
				InputStream is = new URL(source).openStream();
				return BitmapFactory.decodeStream(is);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			Log.d("", "onPostExecute drawable " + mDrawable);
			Log.d("", "onPostExecute bitmap " + bitmap);
			if (bitmap != null) {
				BitmapDrawable d = new BitmapDrawable(bitmap);
				mDrawable.addLevel(1, 1, d);
				mDrawable.setBounds(0, 0, 20, 50);
				mDrawable.setLevel(1);
				// d.setGravity(Gravity.TOP);
				// i don't know yet a better way to refresh TextView
				// mTv.invalidate() doesn't work as expected
				CharSequence t = tvActorBio.getText();
				tvActorBio.setText(t);
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();

		finish();
		startActivity(new Intent(getBaseContext(), TabAndListView.class));
	}
}
