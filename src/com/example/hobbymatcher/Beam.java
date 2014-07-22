package com.example.hobbymatcher;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.widget.Toast;


public class Beam extends Activity implements CreateNdefMessageCallback {

	NfcAdapter mNfcAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.beam );
		
		// NFC
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if( mNfcAdapter == null ){
			Toast.makeText( this , "‚±‚Ì’[––‚Å‚ÍNFC‚ª—˜—p‚Å‚«‚Ü‚¹‚ñB", Toast.LENGTH_LONG ).show();
			finish();
			return;
		}
		mNfcAdapter.setNdefPushMessageCallback(this, this);
	}
	
	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}
