package com.example.pluginmusicplayer;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MusicController implements BugsThirdPartyApi {

	private static final String TAG = "MusicController";
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
	private Context mContext;
	private static MusicController musicController;
	public static MusicController getMusicController(Context context)
	{
		if(musicController==null)
		{
			musicController = new MusicController(context);
		}
		return musicController;
	}
	private MusicController(Context context)
	{
		mContext = context;
		mAudioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
	}

	public void volumeChange(boolean isUp) {
		if (isUp) {
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		} else {
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		}
	}

	public void startService() {
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
		mContext.registerReceiver(mMetaChangeReceiver, new IntentFilter(filter));
	}

	public void stopService() {
		mContext.unregisterReceiver(mMetaChangeReceiver);
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
	protected void metaChange() {
		// TODO Auto-generated method stub
		Log.d(TAG, "metaChange ");
		musicServiceInfo();
	}

	protected void repeatmodeChange(int type) {
		// TODO Auto-generated method stub

	}

	protected void shufflemodeChange(int type) {
		// TODO Auto-generated method stub

	}

	protected void bufferingChange(Intent intent) {
		// TODO Auto-generated method stub

	}

	protected void playStateChange(boolean isPlaying) {
		// TODO Auto-generated method stub

	}

	protected void queuChange() {
		// TODO Auto-generated method stub
		//provider¿ÃøÎ«ÿ playlist∫Ø∞Ê
	}

	protected void ayncOpenStart() {
		// TODO Auto-generated method stub

	}

	protected void ayncOpenComplete() {
		// TODO Auto-generated method stub
		Log.d(TAG, "ayncOpenComplete ");
	}

	protected void playstateInfo(String trackTitle, String trackArtistNm, String trackAlbumImageUrl, int playpos, int repeatmode, int shufflemode, boolean isPlaying, boolean isPrepare, int playingType) {
		// TODO Auto-generated method stub
		Log.d(TAG, "playstateInfo " + trackTitle);
//	    TextView mTextTitle = (TextView) findViewById(R.id.txt_title);
//		mTextTitle.setText(trackTitle);
	}

	protected void progressInfo(long position, long duration) {
		// TODO Auto-generated method stub
		Log.d(TAG, "position " + position);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d(TAG, "playpos " + position + " / " + id);
		
//		sendBroadcast(position, null); // "Ω«Ω√∞£ ¬˜∆Æ ∏ÆΩ∫∆Æ∏¶ ∆˜«‘«œø© ¿Áª˝."
//		sendBroadcast(position, "tracks/genre/7080/1980ost"); // "1980ost OST ∏ÆΩ∫∆Æ∏¶ ∆˜«‘«œø© ¿Áª˝."
//		updateMusicHandler(OPEN, position); // "«√∑π¿Ã∏ÆΩ∫∆Æ¿« POS «ÿ¥Á«œ¥¬ ∞Ó¿ª ¿Áª˝."
		
		sendBroadcastTracks(String.valueOf(id)+"|"+3174962); // "tracks id ø° «ÿ¥Á«œ¥¬ ∞Ó¿ª ¿Áª˝ / ∞Àªˆ»ƒ id ∏¶ ∫∏≥ªº≠ ¿Áª˝«œ∏Èµ»¥Ÿ."
	}
	public void addNewTrack(long id)
	{
		sendBroadcastTracks(String.valueOf(id)+"|"+3174962); // "tracks id ø° «ÿ¥Á«œ¥¬ ∞Ó¿ª ¿Áª˝ / ∞Àªˆ»ƒ id ∏¶ ∫∏≥ªº≠ ¿Áª˝«œ∏Èµ»¥Ÿ."
	}
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

	public static int getNextShuffleMode(int currentShuffle)
	{
		return (currentShuffle+1)/3;
	}
	public static int getNextRepeatMode(int currentRep)
	{
		return (currentRep+1)/3;
	}
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
		mContext.getApplicationContext().sendBroadcast(intent);
	}

	private void sendBroadcast(String cmd, String extraCmd, int args1) {
		Log.d(TAG, extraCmd + ", " + args1);
		Intent intent = new Intent(MEDIA_MESSAGE);
		intent.putExtra("command", cmd);
		intent.putExtra(extraCmd, args1);
		mContext.getApplicationContext().sendBroadcast(intent);
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
		mContext.sendBroadcast(intent);
	}

	protected void sendBroadcast(int realpos, String channel) {
		Intent intent = new Intent(MEDIA_MESSAGE);
		intent.putExtra("command", "openchanneltrack");
		intent.putExtra("channel", channel); // "tracks/new/total, tracks/genre/7080/1970ost"
		intent.putExtra("realPosition", realpos);
		mContext.sendBroadcast(intent);
	}

	protected Cursor mCursor;
	protected ListView mListView;
	protected SampleTrackAdapter mTrackListAdapter;
	

	public static final String SEARCH_TYPE_TRACK = "track";
	public static final String SEARCH_TYPE_ALBUM = "album";
	public static final String SEARCH_TYPE_MV = "mv";
	public static final String SEARCH_TYPE_ESALBUM = "esalbum";
	public static final String SEARCH_TYPE_ARTIST = "artist";
	
	public ArrayList<TrackInfo> searchMusic(String SearchType, String keyword)
	{
		Uri contentUri = Uri.withAppendedPath(CONTENT_URI_SEARCH, "/track/lenka");
		//Uri contentUri = Uri.withAppendedPath(CONTENT_URI_SEARCH, "/"+SearchType+"/"+keyword+"/");
		return getContents(contentUri);
	}
	protected ArrayList<TrackInfo> getContents(Uri uri) 
	{
		uri = Uri.withAppendedPath(CONTENT_URI_SEARCH, "/track/lenka");
		return TrackInfo.getTrackInfoList(resolveContents(uri));
	}
	protected Cursor resolveContents(Uri... params)
	{
		ContentResolver resolver = mContext.getContentResolver();
		Cursor cursor = resolver.query(params[0], new String[] {}, null, null, null);
		return cursor;
	}
//	protected void getContents(Uri uri) {
//
//		final TrackListTask task = new TrackListTask(mContext.getApplicationContext());
//		task.setOnPostExecuteListener(new OnPostExecuteListener<Cursor>() {
//			@Override
//			public void onPostExecute(Cursor cursor) {
//				// "HIDE LOADING"
//				if (cursor != null) {
//					Log.d(TAG, "Content Count : " + cursor.getCount());
//					if (mCursor != null)
//						mCursor.close();
//
//					int code = getErrorCode(cursor);
//					if (code != SUCCESS) {
//						Log.d(TAG, "ERROR");
//						// " TODO DISP ERROR VIEW "
//						return;
//					}
//
//					mCursor = cursor;
//					setList(cursor);
//				} else {
//					Log.d(TAG, "Content is null");
//				}
//			}
//		});
//
//		task.setTimeout(new Handler() {
//			public void handleMessage(Message msg) {
//				super.handleMessage(msg);
//				Toast.makeText(mContext.getApplicationContext(), "µ•¿Ã≈Õ∏¶ ∞°¡Æø¿¡ˆ ∏¯«ﬂΩ¿¥œ¥Ÿ", Toast.LENGTH_SHORT).show();
//			}
//		}, 0);
//
//		// "TODO DISP LOADING"
//		task.execute(uri);
//	}

//	protected void setList(Cursor cursor) {
//
//		if (mTrackListAdapter == null) {
//			mTrackListAdapter = new SampleTrackAdapter(mContext.getApplicationContext(), cursor);
//			mListView.setAdapter(mTrackListAdapter);
//		} else {
//			mTrackListAdapter.changeCursor(cursor);
//			mTrackListAdapter.notifyDataSetChanged();
//		}
//	}
//
//	public static final int SUCCESS = 0;
//	public static final int ERROR_CODE = -1;
//
//	protected int getErrorCode(Cursor cursor) {
//		cursor.moveToFirst();
//		int ret = parseInt(cursor.getString(0));
//		int error_code = parseInt(cursor.getString(2));
//		if (ret == ERROR_CODE) {
//			return error_code;
//		}
//		return SUCCESS;
//	}
//	protected int parseInt(String src) {
//		try {
//			return Integer.parseInt(src);
//		} catch (Exception e) {
//		}
//		return 0;
//	}
}
