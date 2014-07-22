package com.example.hobbymatcher;

import static com.example.hobbymatcher.Constants.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Hobbies extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "hobbies.db";
	private static final int DATABASE_VERSION = 1;

	public Hobbies( Context ctx ){
		super( ctx, DATABASE_NAME, null, DATABASE_VERSION  );
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL( "create table" + TABLE_NAME );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
