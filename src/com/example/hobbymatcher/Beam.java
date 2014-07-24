package com.example.hobbymatcher;

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
import android.widget.TextView;
import android.widget.Toast;


public class Beam extends Activity implements CreateNdefMessageCallback {

	private static final String TAG = "BEAM";
	NfcAdapter mNfcAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.beam );
		
		// NFC
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if( mNfcAdapter == null ){
			Toast.makeText( this , "Ç±ÇÃí[ññÇ≈ÇÕNFCÇ™óòópÇ≈Ç´Ç‹ÇπÇÒÅB", Toast.LENGTH_LONG ).show();
			finish();
			return;
		}
		mNfcAdapter.setNdefPushMessageCallback(this, this);
	}
	
	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		String text = "beam beam beam";
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
		TextView textView = (TextView) findViewById(R.id.beam_message);
		Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
				NfcAdapter.EXTRA_NDEF_MESSAGES);
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		String str = new String( msg.getRecords()[0].getPayload() );
		textView.setText( str );
		Log.d( TAG, "get message: " + str );
	}

}
