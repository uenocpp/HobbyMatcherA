
package com.example.hobbymatcher;

import static android.provider.BaseColumns._ID;
import static com.example.hobbymatcher.Constants.TABLE_NAME;
import static com.example.hobbymatcher.Constants.HOBBY_NAME;
import static com.example.hobbymatcher.Constants.CATEGORY;
import static com.example.hobbymatcher.Constants.INTEREST_LEVEL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HobbyData extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "hobbies.db";
	private static final int DATABASE_VERSION = 1;

	public HobbyData( Context ctx ){
		super( ctx, DATABASE_NAME, null, DATABASE_VERSION  );
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL( "create table " + TABLE_NAME + "(" + _ID + " integer primary key autoincrement, "
				+ HOBBY_NAME + " text not null, "
				+ CATEGORY + " text, "
				+ INTEREST_LEVEL + " integer default 0 );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL( "drop table if exists " + TABLE_NAME );
		onCreate( db );
	}

}
