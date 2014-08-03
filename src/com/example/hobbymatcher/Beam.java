package com.example.hobbymatcher;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Beam extends Activity implements CreateNdefMessageCallback {

	private static final String TAG = "BEAM";
	private HobbyList myHobbyList;
	private NfcAdapter mNfcAdapter;
	private ArrayAdapter<String> arrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.beam );
		
		// NFC
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if( mNfcAdapter == null ){
			Toast.makeText( this , "この端末ではNFCが利用できません。", Toast.LENGTH_LONG ).show();
			finish();
			return;
		}
		mNfcAdapter.setNdefPushMessageCallback(this, this);
		
		// HobbyData
		myHobbyList = new HobbyList( this );
		
		// ListView
		ListView listView = (ListView) findViewById( R.id.matched_list );
		arrayAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1 );
		listView.setAdapter( arrayAdapter );
	}
	
	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		String text = HobbyList.toJson( myHobbyList );
		NdefMessage msg = new NdefMessage(
				new NdefRecord[]{ NdefRecord.createMime(
						"application/com.example.hobbymatcher", text.getBytes())});
		Log.d(TAG, "creating ndef message: " + text );
		return msg;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if( NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()) ){
			processIntent( getIntent() );
		}
	}
	
	@Override
	public void onNewIntent( Intent intent ){
		setIntent( intent );
	}
	
	void processIntent( Intent intent ){
		TextView textView = (TextView) findViewById( R.id.beam_message );
		Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
				NfcAdapter.EXTRA_NDEF_MESSAGES);
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		String str = new String( msg.getRecords()[0].getPayload() );
		HobbyList hisHobbyList = HobbyList.fromJson( str );
		textView.setText( "マッチング成功" );
		arrayAdapter.clear();
		List< Hobby > matched = HobbyList.matchHobbies( myHobbyList.getHobbyList(), hisHobbyList.getHobbyList() );
		for( final Hobby h : matched ){
			arrayAdapter.add( h.getHobbyName() );
		}
		Log.d( TAG, "get message: " + str );
	}

}
