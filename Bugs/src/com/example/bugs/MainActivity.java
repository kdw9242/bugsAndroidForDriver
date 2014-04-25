package com.example.bugs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener, AnimationListener {
	
	private RelativeLayout playListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ((ImageButton)findViewById(R.id.button1)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.button2)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.button3)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.button4)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.button5)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.button6)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.button7)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.button8)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.button9)).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View arg0) {
		Intent i ;
		switch(arg0.getId()) {
		case R.id.button1 :
		case R.id.button2 :
			Toast.makeText(this, "UP", Toast.LENGTH_LONG);
			Log.i("MAINACTIVITY", "UP");
			break;
		case R.id.button3 :
			i = new Intent(MainActivity.this, Search.class);
			startActivity(i);
			break;
		case R.id.button4 :
		case R.id.button5 :
		case R.id.button6 :
		case R.id.button7 :
		case R.id.button8 :
		case R.id.button9 :
//			i = new Intent(MainActivity.this, PlayListActivity.class);
//			startActivity(i);
//			break;
            Context context = this;
            Animation anim;
            anim = AnimationUtils.loadAnimation(context, R.anim.push_left_out);
//            if (!menuOut) {
//                menu.setVisibility(View.VISIBLE);
//                ViewUtils.printView("menu", menu);
//                anim = AnimationUtils.loadAnimation(context, R.anim.push_right_in);
//            } else {
//                anim = AnimationUtils.loadAnimation(context, R.anim.push_left_out);
//            }
            anim.setAnimationListener(this);
            playListLayout = (RelativeLayout)findViewById(R.id.playlistlayout);
            View playList = playListLayout.findViewById(R.id.playlistlayout);
            playList.startAnimation(anim);
		}
	}


	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}
    
}
