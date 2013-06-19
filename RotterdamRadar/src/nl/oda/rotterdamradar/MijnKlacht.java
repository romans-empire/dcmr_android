package nl.oda.rotterdamradar;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MijnKlacht extends Activity
{
	Button button;
	EditText telefoonnummer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mijnklacht);
		
		
		
		button = (Button) findViewById(R.id.buttonnummer);
		
		// save button click event
				button.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						
						//haal het imei nummer op
						TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
						try{
						//laat het imei nummer zien in de textbox
						telefoonnummer = (EditText) findViewById(R.id.mijntelefoonnummer);
						String MyPhoneNumber = tm.getDeviceId();
						telefoonnummer.setText("IMEI: " + MyPhoneNumber);
						}
						catch(NullPointerException ex)
						{	}
					}
				});
	}

	

}
