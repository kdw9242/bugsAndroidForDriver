package com.example.pluginmusicplayer;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	MusicController musicController;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		musicController = MusicController.getMusicController(this);
		ArrayList<TrackInfo> TrackList = musicController.getContents(MusicController.CONTENT_URI_PLAYLIST);
		//ArrayList<TrackInfo> TrackList = musicController.searchMusic("track", "이승환");
		TextView textView = (TextView)findViewById(R.id.text);
		String text = new String();
		text+=TrackList.size()+"tracks added\n";
		for(int i=0; i<TrackList.size(); i++)
		{
			text+=(TrackList.get(i).getTrackTitle()+"\n");
		}
		textView.setText(text);
	}
	@Override
	public void onStart() {
		super.onStart();
		musicController.startService();
	}

	@Override
	public void onStop() {
		super.onStop();
		musicController.stopService();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	// "플레이어 EVENT"
	public void onPlayer(View v) {
		int id = v.getId();
		if (id == R.id.btn_play) {
			musicController.updateMusicHandler(MusicController.PLAY);
		} else if (id == R.id.btn_next) {
			musicController.updateMusicHandler(MusicController.NEXT);
		} else if (id == R.id.btn_pre) {
			musicController.updateMusicHandler(MusicController.PREV);
		} else if(id == R.id.btn_repeat){
			musicController.updateMusicHandler(MusicController.REPEATMODE);
		}else if(id == R.id.btn_shuffle){
			
			int currentShuffleMode = 1;
			musicController.updateMusicHandler(MusicController.SHUFFLEMODE, MusicController.getNextShuffleMode(currentShuffleMode));
		}
		else if(id==R.id.btn_add){
			
		}
	}

}
