package com.example.bugs;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class PlayListActivity extends Activity implements View.OnClickListener {
	
	private PlayListActivity thisActivity = this;
	private String[] values = new String[] { "Hot Summer - f(x)", "chu~ - f(x)", "dangerous - f(x)", "ºù±×¸£ - f(x)"};
	
	private ArrayAdapter<String> adapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_list);
		
		((Button)findViewById(R.id.playlisttomainbtn)).setOnClickListener(this);
		
		adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, values);
		
		((ListView)findViewById(R.id.playlistitems)).setAdapter(adapter);
		((ListView)findViewById(R.id.playlistitems)).setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(thisActivity, "on playlist click", Toast.LENGTH_LONG);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_list, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.playlisttomainbtn :
			finish();
			break;
		}
	}

}
