package com.tab;

import com.example.bongo1st.R;
import com.movies.browseAll.Movies;
import com.movies.movieTiming.MovieTiming;
import com.movies.startingPage.StartingPage;
import com.navdrawer.SimpleSideDrawer;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class TabAndListView extends TabActivity implements TabContentFactory,OnTabChangeListener
{

	public static TabHost tabHost;
	public static int numberOfTabs;
	public static TabAndListView tabAndListViewInstance;
	
	SimpleSideDrawer slide_me;
	TextView tv1, tv2;
	ImageView left_button;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.android_tab_and_list_view);
        tabAndListViewInstance = this;
        
        //total number of tabs
        numberOfTabs = 5;
        
        TabView.TabHost();
    	tabHost.setOnTabChangedListener(this);
    	
    	slide_me = new SimpleSideDrawer(this);
		slide_me.setLeftBehindContentView(R.layout.left_menu);

		AddMenu am = new AddMenu(this);
    	am.addMenus();
    	
		left_button = (ImageView) findViewById(R.id.ivMenuSettings);
		left_button.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				slide_me.toggleLeftDrawer();
				
			}
		});


		TabView.createTab();
    }

    
    /** {@inheritDoc} */
    public View createTabContent(String tag) {
    	 TextView tv = null;
    	if(!tag.equals(1) || !tag.equals(2) || !tag.equals(3))
    	{
    		tv = new TextView(this);
    		tv.setText("Content for tab with tag " + tag);
    	}
        return tv;
    }

    @Override
	public void onTabChanged(String tabId) 
    {
    	
    	TabView.setTabChanhed(tabId);
	}
    
    
}