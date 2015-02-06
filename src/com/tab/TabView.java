package com.tab;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;

import com.movies.browseAll.Movies;
import com.movies.catagory.Catagory;
import com.movies.movieTiming.MovieTiming;
import com.movies.startingPage.StartingPage;

public class TabView
{
	
	public static TextView[] tabs;
	
	public static void createTab() 
    {
    	//set the Tab title & color
		tabs = new TextView[TabAndListView.numberOfTabs];
		for(int j=1; j<= TabAndListView.numberOfTabs; j++)
		{
			if(TabAndListView.tabHost.getTabWidget().getChildAt(j)!=null)
			{
				if(j==1)
				{
					setTab(1, "Movies");
				}
				else if(j==2)
				{
					setTab(2, "Tv");
				}
				else if(j==3)
				{
					setTab(3, "Movie Timing");
				}
				else if(j==4)
				{
					setTab(4, "Catagory");
				}
				else
				{
					setTab(j, "Others");
				}
			}
		}
		tabs[TabAndListView.numberOfTabs-1] = (TextView)TabAndListView.tabHost.getTabWidget().getChildAt(TabAndListView.numberOfTabs-1).findViewById(android.R.id.title);
		tabs[TabAndListView.numberOfTabs-1].setText("Others");
		tabs[TabAndListView.numberOfTabs-1].setTextColor(Color.WHITE);
		tabs[TabAndListView.numberOfTabs-1].setBackgroundColor(Color.RED);
		
		//tab selector color red
		TabAndListView.tabHost.getTabWidget().setCurrentTab(0);
		for(int l=0; l<TabAndListView.numberOfTabs; l++)
		{
			TabAndListView.tabHost.getTabWidget().getChildAt(l).setBackgroundColor(Color.RED);
		}
		
		//check starting page value
		if(StartingPage.value==1)
		{
			TabAndListView.tabHost.setCurrentTab(0);
		}
		else if(StartingPage.value==2)
		{
			TabAndListView.tabHost.setCurrentTab(1);
		}
		else if(StartingPage.value==3)
		{
			TabAndListView.tabHost.setCurrentTab(2);
		}		
		else if(StartingPage.value==4)
		{
			TabAndListView.tabHost.setCurrentTab(3);
		}		
	}

	@SuppressWarnings("deprecation")
	public static void TabHost()
    {
		TabAndListView.tabHost = TabAndListView.tabAndListViewInstance.getTabHost();
        //tab backgroumd color red
		TabAndListView.tabHost.getTabWidget().setBackgroundColor(Color.RED);
        
        for (int i=1; i <= TabAndListView.numberOfTabs; i++)
        {
        	if(i==1)
        	{
        		   String name = "";
        		   Intent moviesIntent = new Intent(TabAndListView.tabAndListViewInstance, Movies.class);
        		   TabAndListView.tabHost.addTab(TabAndListView.tabHost.newTabSpec(name)
                           .setIndicator(name, null)
                           .setContent(moviesIntent));
        	}
//        	else if(i==2)
//        	{
//        		   String name = "";
//        		   Intent inboxIntent = new Intent(this, InboxActivity.class);
//                   tabHost.addTab(tabHost.newTabSpec(name)
//                           .setIndicator(name, null)
//                           .setContent(inboxIntent));
//        	}
        	else if(i==3)
        	{
        		   String name = "";
        		   Intent movieTimingIntent = new Intent(TabAndListView.tabAndListViewInstance, MovieTiming.class);
        		   TabAndListView.tabHost.addTab(TabAndListView.tabHost.newTabSpec(name)
                           .setIndicator(name, null)
                           .setContent(movieTimingIntent));
        	}
        	else if(i==4)
        	{
        		   String name = "";
        		   Intent catagoryIntent = new Intent(TabAndListView.tabAndListViewInstance, Catagory.class);
        		   TabAndListView.tabHost.addTab(TabAndListView.tabHost.newTabSpec(name)
                           .setIndicator(name, null)
                           .setContent(catagoryIntent));
        	}
        	else
        	{
        		String name = "Tab " + i;
//        		String name = "";
        		TabAndListView.tabHost.addTab(TabAndListView.tabHost.newTabSpec(name)
        				.setIndicator(name)
        				.setContent(TabAndListView.tabAndListViewInstance));
        	}
        	
        }
    	
    }
	
	
	
	 public static void setTab(int i, String text)
	 {
	     tabs[i] = (TextView)TabAndListView.tabAndListViewInstance.tabHost.getTabWidget().getChildAt(i-1).findViewById(android.R.id.title);
    	 tabs[i].setText(text);
    	 tabs[i].setTextColor(Color.WHITE);
	     tabs[i].setBackgroundColor(Color.RED);
	 }
	 
	public static void setTabChanhed(String id)
	{
		// Check current selected tab and change according images
		for (int i = 0; i < TabAndListView.tabHost.getTabWidget().getChildCount(); i++) 
		{
			if (i == 0)
				TabAndListView.tabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.RED);
			else if (i == 1)
				TabAndListView.tabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.RED);
			else if (i == 2)
				TabAndListView.tabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.RED);
			else 
				TabAndListView.tabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.RED);
		}

		Log.i("tabs", "CurrentTab: " + TabAndListView.tabHost.getCurrentTab());

		if(TabAndListView.tabHost.getCurrentTab() == 0)
			TabAndListView.tabHost.getTabWidget().getChildAt(TabAndListView.tabHost.getCurrentTab())
				.setBackgroundColor(Color.RED);
		else if(TabAndListView.tabHost.getCurrentTab() == 1)
			TabAndListView.tabHost.getTabWidget().getChildAt(TabAndListView.tabHost.getCurrentTab())
				.setBackgroundColor(Color.RED);
		else if(TabAndListView.tabHost.getCurrentTab() == 2)
			TabAndListView.tabHost.getTabWidget().getChildAt(TabAndListView.tabHost.getCurrentTab())
				.setBackgroundColor(Color.RED);
		else 
			TabAndListView.tabHost.getTabWidget().getChildAt(TabAndListView.tabHost.getCurrentTab())
				.setBackgroundColor(Color.RED);

	}
}
