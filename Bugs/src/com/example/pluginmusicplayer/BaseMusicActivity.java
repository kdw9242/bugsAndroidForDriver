package com.example.pluginmusicplayer;

import com.example.pluginmusicplayer.BugsThirdPartyApi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Î≤ÖÏä§ Third-Party App
 * 
 * @author whand76
 * 
 */
public abstract class BaseMusicActivity extends Activity implements BugsThirdPartyApi {

	private static final String TAG = "BaseMusicActivity";
	private MetaChangeReceiver mMetaChangeReceiver = new MetaChangeReceiver();

	public static final int NEXT = 1;
	public static final int PREV = 2;
	public static final int PLAY = 3;
	public static final int PAUSE = 4;
	public static final int OPEN = 5;
	public static final int STOP = 6;
	public static final int REPEATMODE = 7;
	public static final int SHUFFLEMODE = 8;

	private AudioManager mAudioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAudioManager = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
	}

	public void volumeChange(boolean isUp) {
		if (isUp) {
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		} else {
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		registerBr();
	}

	/**
	 * ?¨ÏÉùÏ§ëÏù∏ ?åÏïÖ???ÅÌÉúÎ≥?≤Ω?±ÏùÑ Í∞êÏãú?úÎã§.
	 */
	private void registerBr() {
		// MUSIC
		IntentFilter filter = new IntentFilter();
		filter.addAction(PLAYSTATE_CHANGED);
		filter.addAction(META_CHANGED);
		filter.addAction(PLAYBACK_COMPLETE);
		filter.addAction(QUEUE_CHANGED);
		filter.addAction(ASYNC_OPEN_START);
		filter.addAction(ASYNC_OPEN_COMPLETE);
		filter.addAction(BUFFERING_CHANGED);
		filter.addAction(PLAYSTATE_INFO_NEW);
		filter.addAction(PROGRESS_INFO);
		filter.addAction(REPEATMODE_CHANGED);
		filter.addAction(SHUFFLEMODE_CHANGED);
		filter.addAction(ERROR);
		registerReceiver(mMetaChangeReceiver, new IntentFilter(filter));
	}

	@Override
	public void onStop() {
		super.onStop();
		unregisterReceiver(mMetaChangeReceiver);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public boolean mIsOffLineMode;

	class MetaChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
			if (action.equals(META_CHANGED)) {
				metaChange();
			} else if (action.equals(BUFFERING_CHANGED)) {
				bufferingChange(intent);
			} else if (action.equals(PLAYSTATE_CHANGED)) {
			} else if (action.equals(PLAYBACK_COMPLETE)) {
				playStateChange(intent.getBooleanExtra("isplaying", false));
			} else if (action.equals(QUEUE_CHANGED)) {
				queuChange();
			} else if (action.equals(ASYNC_OPEN_START)) {
				ayncOpenStart();
			} else if (action.equals(ASYNC_OPEN_COMPLETE)) {
				ayncOpenComplete();
			} else if (action.equals(SHUFFLEMODE_CHANGED)) {
//				int shufflemode = intent.getIntExtra("shufflemode", -1);
//				shufflemodeChange(shufflemode);
			} else if (action.equals(REPEATMODE_CHANGED)) {
//				int repeatmode = intent.getIntExtra("repeatmode", -1);
//				repeatmodeChange(repeatmode);
			} else if (action.equals(PROGRESS_INFO)) {
				long position = intent.getLongExtra("position", -1); // "?ÑÏû¨ Í≥°Ïùò ?¨ÏÉù ?ÑÏπò"
				long duration = intent.getLongExtra("duration", -1); // "?ÑÏû¨ Í≥°Ïùò Ï¥??¨ÏÉù Í∏∏Ïù¥"
				progressInfo(position, duration);
			} else if (action.equals(PLAYSTATE_INFO_NEW)) {

				int playpos = intent.getIntExtra("playpos", -1);
				int repeatmode = intent.getIntExtra("repeatmode", -1);
				int shufflemode = intent.getIntExtra("shufflemode", -1);
				boolean isplaying = intent.getBooleanExtra("isplaying", false);
				boolean isPrepare = intent.getBooleanExtra("isPrepare", false);
				String trackTitle = intent.getStringExtra("title");
				String trackArtistNm = intent.getStringExtra("artist");
				String trackAlbumUrl = intent.getStringExtra("url");

				playstateInfo(trackTitle, trackArtistNm, trackAlbumUrl, playpos, repeatmode, shufflemode, isplaying, isPrepare, -1);
			}
		}
	}

	protected abstract void metaChange();

	protected abstract void repeatmodeChange(int type);

	protected abstract void shufflemodeChange(int type);

	protected abstract void bufferingChange(Intent intent);

	protected abstract void playStateChange(boolean isPlaying);

	protected abstract void queuChange();

	protected abstract void ayncOpenStart();

	protected abstract void ayncOpenComplete();

	protected abstract void playstateInfo(String trackTitle, String trackArtistNm, String trackAlbumImageUrl, int playpos, int repeatmode, int shufflemode, boolean isPlaying, boolean isPrepare, int playingType);

	protected abstract void progressInfo(long position, long duration);

	protected Handler mCmdMusicHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case NEXT:
				musicNext();
				break;
			case PREV:
				musicPrev();
				break;
			case PLAY:
				musicPlay();
				break;
			case OPEN:
				musicOpen(msg.arg1);
				break;
			case PAUSE:
				musicPause();
				break;
			case STOP:
				musicStop();
				break;
			case REPEATMODE:
				musicRepeateMode(msg.arg1);
				break;
			case SHUFFLEMODE:
				musicShufflemode(msg.arg1);
				break;
			}
		}
	};

	protected void updateMusicHandler(int what) {
		mCmdMusicHandler.removeCallbacksAndMessages(null);
		mCmdMusicHandler.sendEmptyMessageDelayed(what, 300);
	}

	protected void updateMusicHandler(int what, int args1) {
		mCmdMusicHandler.removeCallbacksAndMessages(null);
		mCmdMusicHandler.sendMessageDelayed(mCmdMusicHandler.obtainMessage(what, args1, 0), 300);
	}

	private void musicPrev() {
		sendBroadcast("previous");
	}

	private void musicNext() {
		sendBroadcast("next");
	}

	private void musicPlay() {
		sendBroadcast("togglepause");
	}

	private void musicOpen(int position) {
		sendBroadcast("open", "playpos", position);
	}

	private void musicPause() {
		sendBroadcast("pause");
	}

	private void musicStop() {
		sendBroadcast("stop");
	}

	// " MUSIC ?ïÎ≥¥Î•??îÏ≤≠?òÎ©¥ BR Î°??∞Ïù¥?∞Í? ?òÏñ¥?®Îã§. "
	public void musicServiceInfo() {
		sendBroadcast("info_new");
	}

	// " ?ÑÎ°úÍ∑∏Îûò???ïÎ≥¥Î•??îÏ≤≠?òÎ©¥ BR Î°??∞Ïù¥?∞Í? ?òÏñ¥?®Îã§. "
	public void musicProgressInfo() {
		sendBroadcast("progress");
	}

	// " BR ?ëÎãµ?ÜÏùå. "
	private void musicRepeateMode(int repeatmode) {
		sendBroadcast("repeatmode", "repeatmode", repeatmode);
	}

	private void musicShufflemode(int shufflemode) {
		sendBroadcast("shufflemode", "shufflemode", shufflemode);
	}

	private void sendBroadcast(String cmd) {
		Intent intent = new Intent(MEDIA_MESSAGE);
		intent.putExtra("command", cmd);
		this.getApplicationContext().sendBroadcast(intent);
	}

	private void sendBroadcast(String cmd, String extraCmd, int args1) {
		Log.d(TAG, extraCmd + ", " + args1);
		Intent intent = new Intent(MEDIA_MESSAGE);
		intent.putExtra("command", cmd);
		intent.putExtra(extraCmd, args1);
		this.getApplicationContext().sendBroadcast(intent);
	}

	/**
	 * ?∏Îûô?ÑÏù¥?îÎ? ?ÑÎã¨?òÎ©¥ ?¥Îãπ Í≥°Îì§???¨ÏÉù?úÎã§. 123456|123010
	 * 
	 * @param trackIds
	 */
	protected void sendBroadcastTracks(String trackIds) {
		Log.d(TAG, "sendBroadcastTracks : "+trackIds);
		Intent intent = new Intent(MEDIA_MESSAGE);
		intent.putExtra("command", "open_track");
		intent.putExtra("track_ids", trackIds);
		sendBroadcast(intent);
	}

	protected void sendBroadcast(int realpos, String channel) {
		Intent intent = new Intent(MEDIA_MESSAGE);
		intent.putExtra("command", "openchanneltrack");
		intent.putExtra("channel", channel); // "tracks/new/total, tracks/genre/7080/1970ost"
		intent.putExtra("realPosition", realpos);
		sendBroadcast(intent);
	}
	
}
