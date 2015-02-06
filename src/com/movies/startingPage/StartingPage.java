package com.movies.startingPage;

import com.example.bongo1st.R;
import com.example.bongo1st.R.id;
import com.example.bongo1st.R.layout;
import com.movies.catagory.Catagory;
import com.movies.movieTiming.MovieTiming;
import com.tab.TabAndListView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class StartingPage extends Activity
{

	public ImageView ivFullMovies, ivClips, ivMovieTiming;
	public static int value;
	public static StartingPage startInstance;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.starting_page);
		
		startInstance = this;
		
		ivFullMovies = (ImageView)findViewById(R.id.ivFullMovies);
		ivFullMovies.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				value=1;
				finish();
				startActivity(new Intent(getBaseContext(), TabAndListView.class));
			}
		});
		
		ivClips = (ImageView)findViewById(R.id.ivClips);
		ivClips.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				value=4;
				finish();
				startActivity(new Intent(getBaseContext(), TabAndListView.class));
			}
		});
		
		ivMovieTiming = (ImageView)findViewById(R.id.ivMovieTiming);
		ivMovieTiming.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				value=3;
				finish();
				startActivity(new Intent(getBaseContext(), TabAndListView.class));
			}
		});
	}
	
}