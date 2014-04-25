package com.example.bugs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

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
			i = new Intent(MainActivity.this, PlayListActivity.class);
			startActivity(i);
			break;
		}
	}
    
}
