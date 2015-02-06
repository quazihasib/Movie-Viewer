package com.movies.singleMovie;

import com.example.bongo1st.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class TestActivity extends Activity {

	String txt, txt1; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        SingleMoviePage myActivity = (SingleMoviePage) this.getParent();
        String currentTab = myActivity.getTabHost().getCurrentTabTag();
        
        if(currentTab.equals("tab3"))
        {
        	setContentView(R.layout.single_page_share_tab);
        	//txt = SingleMoviePage.movieShortSummary;
        	//((TextView)findViewById(R.id.tvShre)).setText("Share Page");

        }
        //Description tab
        else if(currentTab.equals("tab2"))
        {
        	setContentView(R.layout.single_page_description_tab);
        	txt = SingleMoviePage.movieShortSummary;
        	Log.d("", "movieShortSummary:"+txt);
        	((TextView)findViewById(R.id.tvMovieDescription)).setText(txt);

        }
        //Details tab
        else if(currentTab.equals("tab1"))
        {
        	setContentView(R.layout.single_page_detailes_tab);
        	
        	txt = SingleMoviePage.movieCategory;
        	txt1 = SingleMoviePage.moviePostedOn;
        	
        	Log.d("", "cata:"+txt);
        	Log.d("", "post:"+txt1);
        	
        	((TextView)findViewById(R.id.tvCatagoryNameDetails)).setText(" "+txt);
        	((TextView)findViewById(R.id.tvPostedOnNameDetails)).setText(" "+txt1);
//        	RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//        	ratingBar.setRating(txt2);
//        	LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
//        	stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        	
        	addListenerOnRatingBar();
        }
    }
    
    
    public void addListenerOnRatingBar() 
	{
    	RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    	final TextView tvRatingValue = (TextView) findViewById(R.id.tvRatingValue);
    	
    	float txt2 = SingleMoviePage.movieRating;
    	Log.d("", "Rating:"+txt2);
    	ratingBar.setRating(txt2);
    	tvRatingValue.setText(" "+txt2+"/10");
    	
		LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
		stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
		//stars.getDrawable(1).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
		stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
		
		//if rating value is changed,
		//display the current rating value in the result (textview) automatically
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
		{
			public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
	 
				tvRatingValue.setText(String.valueOf(rating)+"/10");
	 
			}
		});
		
	  }
}