package com.movies.singleMovie;

import com.example.bongo1st.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

public class PlayMovie extends Activity
{
	public WebView webView1;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
   
        
		setContentView(R.layout.play_movie);
		
		webView1 = (WebView) findViewById(R.id.webView2);
		webView1.getSettings().setJavaScriptEnabled(true);
		webView1.getSettings().setUseWideViewPort(true);
		webView1.getSettings().setLoadWithOverviewMode(true);
		
        webView1.loadUrl("http://kaltura.bongobd.com/html5/html5lib/v2.21/mwEmbedFrame.php/p/108/uiconf_id/23448332/entry_id/0_5nkyga3r?wid=_108&iframeembed=true&playerId=kaltura_player_1419318603&entry_id=0_5nkyga3r");
		webView1.setHorizontalScrollBarEnabled(true);
        
        
	}
	
	@Override
	public void onBackPressed()
	{
	    super.onBackPressed();

		finish();
		//startActivity(new Intent(getBaseContext(), SingleMoviePage.class));
		System.runFinalizersOnExit(true);
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
