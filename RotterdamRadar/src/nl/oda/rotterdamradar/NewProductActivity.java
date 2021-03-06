package nl.oda.rotterdamradar;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewProductActivity extends Activity implements OnItemSelectedListener {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText Inaam, Itelefoonnummer, Imailadres, Ipostcode, Istraatnaam, Iwoonplaats, Iaardoverlast, Isubaard, Isubsubaard, 
	Istad, Istraat, Itoelichting, Iterugkoppeling;
	Spinner subject, spinner, spinner1, spinner2;

	// url to create new product
	private static String url_create_product = "http://dcmr.stefanorie.com/klachten/create_product.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_product);
		
		// text fields
		Inaam = (EditText) findViewById(R.id.editTekstNaam);
		Itelefoonnummer = (EditText) findViewById(R.id.editTextMobiel);
		Imailadres = (EditText) findViewById(R.id.editTextMail);
		Ipostcode = (EditText) findViewById(R.id.editPostcode);
		Istraatnaam = (EditText) findViewById(R.id.editStraatnaam);
		Iwoonplaats = (EditText) findViewById(R.id.editWoonplaats);
		Istad = (EditText) findViewById(R.id.locatiestad);
		Istraat = (EditText) findViewById(R.id.locatiestraat);
		Itoelichting = (EditText) findViewById(R.id.toelichting);
		
		//spinners waar je onderwerpen kan selecteren
		subject = (Spinner) findViewById(R.id.spinner);
		String subjects[] = new String[] {"Aard van de overlast", "afval", "bodem", "Diverse", "Lawaai", "Stof", "Stank", "Visuele Hinder", "Water", "Vliegverkeer"  };
		subject.setOnItemSelectedListener(this);
		ArrayAdapter<String> sa = new ArrayAdapter<String>(this,
				R.layout.spinner_item, subjects);
		sa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		subject.setAdapter(sa);
		;
		

		spinner = (Spinner) findViewById(R.id.spinner1);
		String onderwerpen[] = new String[] {"Sub aard", "Algemeen", "Graan / Meel ", "Horeca Evenementen", "Helicoper", "Vliegtuig", "Overig", "Voedingswaren", "Verkeersstank", "Baklucht", 
				"Olie", "Chemisch", "Laag / Hoogfrequent" };
		spinner.setOnItemSelectedListener(this);
		ArrayAdapter<String> subaard = new ArrayAdapter<String>(this,
				R.layout.spinner_item, onderwerpen);
		subaard.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(subaard);
		;
		
		spinner1 = (Spinner) findViewById(R.id.spinner2);
		String subonderwerpen[] = new String[] {"Sub sub aard", "Algemeen", "Mechanische Muziek", "Alarm", "Vliegtuig vloog te laag", "Dreunende bas", "Uitlaatgassen verkeer", 
				"Alcohol", "Heien", "Stookolie", "Roetpluim", "Benzine" };
		spinner1.setOnItemSelectedListener( this);
		ArrayAdapter<String> subsubaard = new ArrayAdapter<String>(this,
				R.layout.spinner_item, subonderwerpen);
		subsubaard.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner1.setAdapter(subsubaard);
		;
		
		spinner2 = (Spinner) findViewById(R.id.spinner3);
		String terugkoppeling[] = new String[] {"ja", "nee", };
		spinner2.setOnItemSelectedListener(this);
		ArrayAdapter<String> terugkoppeling1 = new ArrayAdapter<String>(this,
				R.layout.spinner_item, terugkoppeling);
		terugkoppeling1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner2.setAdapter(terugkoppeling1);
		;
		
		LoadPreferences();

		// Create button
		Button btnCreateProduct = (Button) findViewById(R.id.buttonSend);

		// button click event
		btnCreateProduct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				//checken of velden leeg zijn
				if (Inaam.getText().toString().length() == 0) {
					Inaam.setError("Vul uw naam in");
				} else if (Imailadres.getText().toString().length() == 0) {
					Imailadres.setError("Vul uw email in");
				} else if (Istraatnaam.getText().toString().length() == 0){
					Istraatnaam.setError("Vul straatnaam in");
				} else if (Iwoonplaats.getText().toString().length() == 0){
					Iwoonplaats.setError("Vul woonplaats in");
				} else if (Itelefoonnummer.getText().toString().length() != 10) {
					Itelefoonnummer.setError("Vul een geldig telefoonnummer in");
				} else if (Ipostcode.getText().toString().length() == 0) {
					Ipostcode.setError("Vul een postcode in");
				} else if (Istraat.getText().toString().length() == 0){
					Istraat.setError("Vul een straatnaam in");
				} else if (Istad.getText().toString().length() == 0) {
					Istad.setError("Vul een stad of dorp in");
				}
				else{
				// creating new klacht in background thread
				new CreateNewProduct().execute();
				 SavePreferences("MEM1", Inaam.getText().toString());
				 SavePreferences("MEM2", Imailadres.getText().toString());
				 SavePreferences("MEM3", Itelefoonnummer.getText().toString());
				 SavePreferences("MEM4", Ipostcode.getText().toString());
				 SavePreferences("MEM5", Iwoonplaats.getText().toString());
				 SavePreferences("MEM6", Istraatnaam.getText().toString());
				   LoadPreferences();
				}
			}
		});
	}

	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewProductActivity.this);
			pDialog.setMessage("Klacht opslaan..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String MyIMEINumber = tm.getDeviceId();
			String naam = Inaam.getText().toString();
			String telefoonnummer = Itelefoonnummer.getText().toString();
			String email = Imailadres.getText().toString();
			String postcode = Ipostcode.getText().toString();
			String straatnaam = Istraatnaam.getText().toString();
			String woonplaats = Iwoonplaats.getText().toString();
			String aardoverlast = subject.getSelectedItem().toString();
			String subaard = spinner.getSelectedItem().toString();
			String subsubaard = spinner1.getSelectedItem().toString();
			String locatiestad = Istad.getText().toString();
			String locatiestraat = Istraat.getText().toString();
			String toelichtingg = Itoelichting.getText().toString();
			String terugkoppeling = spinner2.getSelectedItem().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("IMEI", MyIMEINumber));
			params.add(new BasicNameValuePair("naam", naam));
			params.add(new BasicNameValuePair("telefoonnummer", telefoonnummer));
			params.add(new BasicNameValuePair("mailadres", email));
			params.add(new BasicNameValuePair("postcode", postcode));
			params.add(new BasicNameValuePair("straatnaam", straatnaam));
			params.add(new BasicNameValuePair("woonplaats", woonplaats));
			params.add(new BasicNameValuePair("aardoverlast", aardoverlast));
			params.add(new BasicNameValuePair("subaard", subaard));
			params.add(new BasicNameValuePair("subsubaard", subsubaard));
			params.add(new BasicNameValuePair("locatiestad", locatiestad));
			params.add(new BasicNameValuePair("locatiestraat", locatiestraat));
			params.add(new BasicNameValuePair("toelichting", toelichtingg));
			params.add(new BasicNameValuePair("terugkoppeling", terugkoppeling));


			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Intent i = new Intent(getApplicationContext(), MijnKlachten.class);
					startActivity(i);
					
					// closing this screen
					finish();
				} else {
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
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
	//sla de velden op
	private void SavePreferences(String key, String value){
	    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putString(key, value);
	    editor.commit();
	   }
	
	private void LoadPreferences(){
		//gegevens van gebruikers opslaan in lokaal geheugen
	    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    String strSavedMem1 = sharedPreferences.getString("MEM1", "");
	    String strSavedMem2 = sharedPreferences.getString("MEM2", "");
	    String strSavedMem3 = sharedPreferences.getString("MEM3", "");
	    String strSavedMem4 = sharedPreferences.getString("MEM4", "");
	    String strSavedMem5 = sharedPreferences.getString("MEM5", "");
	    String strSavedMem6 = sharedPreferences.getString("MEM6", "");
	    Inaam.setText(strSavedMem1);
	    Imailadres.setText(strSavedMem2);
	    Itelefoonnummer.setText(strSavedMem3);
	    Ipostcode.setText(strSavedMem4);
	    Iwoonplaats.setText(strSavedMem5);
	    Istraatnaam.setText(strSavedMem6);
	   }
}
