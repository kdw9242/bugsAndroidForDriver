package com.example.pluginmusicplayer;

import java.util.ArrayList;


import android.database.Cursor;

public class TrackInfo {
	private static boolean indexinfo = false;
	private static int mIdxID;
	private static int mIdxTrackTitle;
	private static int mIdxArtist;
	private static int mIdxAlbum;

	private static int mIdxImageUrl;
	private static int mIdxSmallImageUrl;
	private static int mIdxDuration;

	private static int mIdxIsAdult;
	private static int mIdxState;
	private static int mIdxData;
	private static int mIdxIsListenable;
	private static int mIdxRestrict;

	private int mPlayMode;

	private String mTrackTitle;
	private String mArtistNm;
	private String mAlbumTitle;

	
	int restrictType;

	public TrackInfo()
	{
	}
	public String getTrackTitle()
	{
		return mTrackTitle;
	}
	public String getArtistNm()
	{
		return mArtistNm;
	}
	public String getmAlbumTitle()
	{
		return mAlbumTitle;
	}
	protected static void getColumnIndex(Cursor c)
	{
		mIdxID = c.getColumnIndex("_id");
		mIdxTrackTitle = c.getColumnIndex("track_title");
		mIdxArtist = c.getColumnIndex("artist_nm");
		mIdxAlbum = c.getColumnIndex("album_title");

		mIdxImageUrl = c.getColumnIndex("album_image_url");
		mIdxSmallImageUrl = c.getColumnIndex("album_small_image_url");
		mIdxDuration = c.getColumnIndex("duration");

		mIdxIsAdult = c.getColumnIndex("adult");
		mIdxState = c.getColumnIndex("state");
		mIdxData = c.getColumnIndex("local");
		mIdxIsListenable = c.getColumnIndex("islistenable");
		mIdxRestrict = c.getColumnIndex("restrict");
	}
	public static ArrayList<TrackInfo> getTrackInfoList(Cursor cursor)
	{
		if(indexinfo==false)
		{
			getColumnIndex(cursor);
		}
		ArrayList<TrackInfo> Res = new ArrayList<TrackInfo>();
		if(cursor != null)
		{
			while(cursor.moveToNext())
			{
				TrackInfo trackInfo = new TrackInfo();
				trackInfo.mTrackTitle = (cursor.getString(mIdxTrackTitle));
				trackInfo.mArtistNm = (cursor.getString(mIdxArtist));
				trackInfo.mAlbumTitle = (cursor.getString(mIdxAlbum));
	
				
				if (mIdxRestrict > 0){
					trackInfo.restrictType = parseInt(cursor.getString(mIdxRestrict));
				}
				else{
					trackInfo.restrictType = -1;
				}
	
				Res.add(trackInfo);
			}
		}
		return Res;
	}
	private static int parseInt(String index) {
		try {
			return Integer.parseInt(index);
		} catch (Exception e) {
		}
		return 0;
	}

}
