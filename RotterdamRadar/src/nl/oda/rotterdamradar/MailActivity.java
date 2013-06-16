package nl.oda.rotterdamradar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MailActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {
	/** Called when the activity is first created. */
	EditText straatnaamField, woonplaatsField, subtoelichtingField, terugkoppelingField, naamField, mailField, mobielField, toelichtingField, postcodeField;
	Spinner subject, spinner, spinner1, spinner2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);

		naamField = (EditText) findViewById(R.id.editTekstNaam);
		mailField = (EditText) findViewById(R.id.editTextMail);
		mobielField = (EditText) findViewById(R.id.editTextMobiel);
		toelichtingField = (EditText) findViewById(R.id.toelichting);
		postcodeField = (EditText) findViewById(R.id.editPostcode);
		straatnaamField = (EditText) findViewById(R.id.editStraatnaam);
		woonplaatsField = (EditText) findViewById(R.id.editWoonplaats);
		//subtoelichtingField = (EditText) findViewById(R.id.subtoelichting);
		//terugkoppelingField = (EditText) findViewById(R.id.terugkoppeling);

		subject = (Spinner) findViewById(R.id.spinner);
		String subjects[] = new String[] { "Hoofd Overlast", "afval", "bodem", "Stankoverlast", "Geluidsoverlast", "Stofoverlast", "Lichtoverlast" };
		subject.setOnItemSelectedListener(this);
		ArrayAdapter<String> sa = new ArrayAdapter<String>(this,
				R.layout.spinner_item, subjects);
		sa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		subject.setAdapter(sa);
		;
		

		spinner = (Spinner) findViewById(R.id.spinner1);
		String onderwerpen[] = new String[] {"Extra Overlast", "riool", "kassen", "rook", "vliegtuig", "fabriek", "muziek" };
		spinner.setOnItemSelectedListener(this);
		ArrayAdapter<String> subaard = new ArrayAdapter<String>(this,
				R.layout.spinner_item, onderwerpen);
		subaard.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(subaard);
		;
		
		spinner1 = (Spinner) findViewById(R.id.spinner2);
		String subonderwerpen[] = new String[] {"Extra extra Overlast", "helicopter", "brandlucht", "DJ", "feest" };
		spinner1.setOnItemSelectedListener(this);
		ArrayAdapter<String> subsubaard = new ArrayAdapter<String>(this,
				R.layout.spinner_item, subonderwerpen);
		subsubaard.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner1.setAdapter(subsubaard);
		;
		
		spinner2 = (Spinner) findViewById(R.id.spinner3);
		String terugkoppeling[] = new String[] {"kies", "ja", "nee", };
		spinner2.setOnItemSelectedListener(this);
		ArrayAdapter<String> terugkoppeling1 = new ArrayAdapter<String>(this,
				R.layout.spinner_item, terugkoppeling);
		terugkoppeling1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner2.setAdapter(terugkoppeling1);
		;

		final Button buttonSend = (Button) findViewById(R.id.buttonSend);
		buttonSend.setOnClickListener(this);
		
		LoadPreferences();
	}

	public void onClick(View v) {
		if (naamField.getText().toString().length() == 0) {
			naamField.setError("Vul uw naam in");
		} else if (mailField.getText().toString().length() == 0) {
			mailField.setError("Vul uw email in");
		} else if (straatnaamField.getText().toString().length() == 0){
			straatnaamField.setError("Vul straatnaam in");
		} else if (woonplaatsField.getText().toString().length() == 0){
			woonplaatsField.setError("Vul woonplaats in");
		} else if (mobielField.getText().toString().length() != 10) {
			mobielField.setError("Vul een geldig telefoonnummer in");
		} else if (postcodeField.getText().toString().length() == 0) {
			postcodeField.setError("Vul een postcode in");
		} else if (subject.getSelectedItemPosition() == 0) {
			Toast.makeText(MailActivity.this, "Vul het onderwerp in",
					Toast.LENGTH_SHORT).show();
		} else if (spinner2.getSelectedItemPosition() == 0) {
			Toast.makeText(MailActivity.this, "Vul terugkoppeling in",
					Toast.LENGTH_SHORT).show();
		
		} else {
			String body = "Name : " + naamField.getText().toString()
					+ "<br>Mobile :" + mobielField.getText().toString()
					+ "<br>Email :" + mailField.getText().toString()
					+ "<br>Eventuele opmerkingen :" + toelichtingField.getText().toString()
					+ "<br>Postcode :" + postcodeField.getText().toString()
					+ "<br>Straatnaam :" + straatnaamField.getText().toString()
					+ "<br>Woonplaats :" +  woonplaatsField.getText().toString()
					+ "<br>Terugkoppeling :" + spinner2.getSelectedItem().toString()
					+ "<br>onderwerp :" + subject.getSelectedItem().toString()
					+ "<br>Extra onderwerp  :" + spinner.getSelectedItem().toString()
					+ "<br>Nog meer extra's :" + spinner1.getSelectedItem().toString();

			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[] { "richard-lindhout@hotmail.com" });
			email.putExtra(Intent.EXTRA_SUBJECT, new String[] { "klacht" });
			email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
			email.setType("message/rfc822");
			startActivityForResult(Intent.createChooser(email, "Richard"), 1);
			 SavePreferences("MEM1", naamField.getText().toString());
			 SavePreferences("MEM2", mailField.getText().toString());
			 SavePreferences("MEM3", mobielField.getText().toString());
			 SavePreferences("MEM4", postcodeField.getText().toString());
			 SavePreferences("MEM5", woonplaatsField.getText().toString());
			 SavePreferences("MEM6", straatnaamField.getText().toString());
			   LoadPreferences();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		new AlertDialog.Builder(MailActivity.this)
				.setMessage("Uw bericht is verstuurd!\nDank u.")
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	

	
	private void SavePreferences(String key, String value){
	    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putString(key, value);
	    editor.commit();
	   }
	
	private void LoadPreferences(){
	    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    String strSavedMem1 = sharedPreferences.getString("MEM1", "");
	    String strSavedMem2 = sharedPreferences.getString("MEM2", "");
	    String strSavedMem3 = sharedPreferences.getString("MEM3", "");
	    String strSavedMem4 = sharedPreferences.getString("MEM4", "");
	    String strSavedMem5 = sharedPreferences.getString("MEM5", "");
	    String strSavedMem6 = sharedPreferences.getString("MEM6", "");
	    naamField.setText(strSavedMem1);
	    mailField.setText(strSavedMem2);
	    mobielField.setText(strSavedMem3);
	    postcodeField.setText(strSavedMem4);
	    woonplaatsField.setText(strSavedMem5);
	    straatnaamField.setText(strSavedMem6);
	   }
}