package com.example.hobbymatcher;

import static android.provider.BaseColumns._ID;
import static com.example.hobbymatcher.Constants.TABLE_NAME;
import static com.example.hobbymatcher.Constants.HOBBY_NAME;
import static com.example.hobbymatcher.Constants.CATEGORY;
import static com.example.hobbymatcher.Constants.INTEREST_LEVEL;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "MainActivity";
	private ArrayAdapter<String> adapter;
	private HobbyData hobbies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		View beamButton = findViewById( R.id.beam_button );
		beamButton.setOnClickListener(this);
		
		View addButton = findViewById( R.id.add_button );
		addButton.setOnClickListener( this );
		
		ListView listView = (ListView)findViewById(R.id.hobby_list);
		adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1 );
		listView.setAdapter(adapter);
		
		hobbies = new HobbyData( this );
		
		try{
			Cursor cursor = getHobbies();
			showHobbies( cursor );
			cursor.close();
		} finally {
			hobbies.close();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void addHobby(){
		EditText edit = (EditText)findViewById(R.id.added_hobby_name);
		String hname = edit.getText().toString();
		//adapter.add(hname);
		Log.d(TAG, "add " + hname + "to list" );
		
		// TODO : 
		String category = null;
		Integer interest = 0;
		
		// add to database
		
		SQLiteDatabase db = hobbies.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put( HOBBY_NAME, hname );
		values.put( CATEGORY, category );
		values.put( INTEREST_LEVEL, interest );
		db.insertOrThrow( TABLE_NAME, null, values);
		
		refreshHobbyList();
	}
	
	private void refreshHobbyList(){
		adapter.clear();
		Cursor cursor = getHobbies();
		showHobbies( cursor );
		cursor.close();
	}
	
	private static String[] FROM = { _ID, HOBBY_NAME, CATEGORY, INTEREST_LEVEL };
	private static String ORDER_BY = _ID + " DESC";
	private Cursor getHobbies(){
		SQLiteDatabase db = hobbies.getReadableDatabase();
		Cursor cursor = db.query( TABLE_NAME, FROM, null, null, null, null, ORDER_BY );
		return cursor;
	}
	
	private void showHobbies( Cursor cursor ){
		while( cursor.moveToNext() ){
			StringBuilder builder = new StringBuilder();
			long id = cursor.getLong( 0 );
			String name = cursor.getString( 1 );
			String category = cursor.getString( 2 );
			long interest = cursor.getLong( 3 );
			builder.append( id ).append( ": " );
			builder.append( name ).append( ": ");
			builder.append( category ).append( ": " );
			builder.append( interest );
			adapter.add( builder.toString() );
		}
	}
	
	//private void removeHobby( int i ){}

	@Override
	public void onClick(View v) {
		switch( v.getId() ){
		case R.id.beam_button:
			Intent i = new Intent( this, Beam.class );
			startActivity(i);
			break;
		case R.id.add_button:
			addHobby();
			break;
		default:
			break;
		}
	}
}
