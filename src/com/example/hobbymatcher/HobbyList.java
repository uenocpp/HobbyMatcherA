package com.example.hobbymatcher;

import static android.provider.BaseColumns._ID;
import static com.example.hobbymatcher.Constants.CATEGORY;
import static com.example.hobbymatcher.Constants.HOBBY_NAME;
import static com.example.hobbymatcher.Constants.INTEREST_LEVEL;
import static com.example.hobbymatcher.Constants.TABLE_NAME;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

public class HobbyList {
	List< Hobby > hobbyList = new ArrayList< Hobby >();
	
	HobbyList( Context ctx ){
		HobbyData hobbyData = new HobbyData( ctx );
		try{
			Cursor cursor = getHobbyData( hobbyData );
			loadHobbyData( cursor );
			cursor.close();
		}
		finally{
			hobbyData.close();
		}
	}
	
	private void loadHobbyData( Cursor cursor ){
		hobbyList = new ArrayList< Hobby >();
		while( cursor.moveToNext() ){
			//long id = cursor.getLong( 0 );
			String name = cursor.getString( 1 );
			String category = cursor.getString( 2 );
			long interest = cursor.getLong( 3 );
			Hobby hobby = new Hobby( name, category, interest );
			hobbyList.add( hobby );
		}
	}
	
	private static String[] FROM = { _ID, HOBBY_NAME, CATEGORY, INTEREST_LEVEL };
	private static String ORDER_BY = _ID + " DESC";
	public static Cursor getHobbyData( HobbyData hobbyData ){
		SQLiteDatabase db = hobbyData.getReadableDatabase();
		Cursor cursor = db.query( TABLE_NAME, FROM, null, null, null, null, ORDER_BY );
		return cursor;
	}
	
	public static HobbyList fromJson( String json ){
		Gson gson = new Gson();
		HobbyList hobbyList = gson.fromJson( json, HobbyList.class );
		return hobbyList;
	}
	
	public static String toJson( HobbyList hobbyList ){
		Gson gson = new Gson();
		String json = gson.toJson( hobbyList );
		return json;
	}
}
