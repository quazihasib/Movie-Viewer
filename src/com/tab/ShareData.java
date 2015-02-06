package com.tab;

import com.movies.startingPage.StartingPage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

public class ShareData {
	
	public static int getScreenWidth(Activity instance)
	{
		DisplayMetrics displaymetrics = new DisplayMetrics();
		instance.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;
		
		return width;
	}
	
	public static int getScreenHeight(Activity instance)
	{
		DisplayMetrics displaymetrics = new DisplayMetrics();
		instance.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		
		return height;
	}
	

	public static void savePreferences(Activity instance, String key, String value) 
	{
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(instance);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
		Log.d("ShareData", "SavePref:"+value);
	}

	public static String loadSavedPreferences(Activity instance,String key) 
	{
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(instance);
		String name = sharedPreferences.getString(key, "");
		Log.d("ShareData", "LoadPref:"+name);
		return name;
	}

	public static boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) StartingPage.startInstance.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public static void killPage()
	{
		System.runFinalizersOnExit(true);
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
