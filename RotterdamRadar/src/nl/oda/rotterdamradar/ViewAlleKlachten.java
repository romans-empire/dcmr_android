package nl.oda.rotterdamradar;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewAlleKlachten extends Activity implements OnItemSelectedListener {

	EditText Inaam, Itelefoonnummer, Imailadres, Ipostcode, Istraatnaam, Iwoonplaats, Iviewkeus, Isubkeus, Isubsubkeus, Ilocatiestad, Ilocatiestraat, Itoelichting, Istatus;
	TextView naam, telefoonnummer, mailadres, postcode, straatnaam, woonplaats, viewkeus, subkeus, subsubkeus, locatiestad, locatiestraat, toelichting, status;
	Button btnSave, btnDelete;
	Spinner Iterugkoppeling;

	String klachtid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_product_details = "http://dcmr.stefanorie.com/klachten/get_all_product_details.php";


	private static final String TAG_SUCCESS = "success";
	private static final String TAG_KLACHT = "klachten";
	private static final String TAG_KLACHTID = "klachtid";
	private static final String TAG_NAAM = "naam";
	private static final String TAG_TELEFOONNUMMER = "telefoonnummer";
	private static final String TAG_MAILADRES = "mailadres";
	private static final String TAG_POSTCODE = "postcode";
	private static final String TAG_STRAATNAAM = "straatnaam";
	private static final String TAG_WOONPLAATS = "woonplaats";
	private static final String TAG_AARDOVERLAST = "aardoverlast";
	private static final String TAG_SUBAARD = "subaard";
	private static final String TAG_SUBSUBAARD = "subsubaard";
	private static final String TAG_LOCATIESTAD = "locatiestad";
	private static final String TAG_LOCATIESTRAAT = "locatiestraat";
	private static final String TAG_TOELICHTING = "toelichting";
	private static final String TAG_TERUGKOPPELING = "terugkoppeling";
	private static final String TAG_KLACHTSTATUS = "klachtstatus";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewklachten);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// save button
		btnSave = (Button) findViewById(R.id.edit);
		btnDelete = (Button) findViewById(R.id.delete);

		// getting product details from intent
		Intent i = getIntent();
		
		// getting product id (pid) from intent
		klachtid = i.getStringExtra(TAG_KLACHTID);
		
		Iterugkoppeling = (Spinner) findViewById(R.id.spinner3);
		String terugkoppeling[] = new String[] {"ja", "nee", };
		Iterugkoppeling.setOnItemSelectedListener(this);
		ArrayAdapter<String> terugkoppeling1 = new ArrayAdapter<String>(this,
				R.layout.spinner_item, terugkoppeling);
		terugkoppeling1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		Iterugkoppeling.setAdapter(terugkoppeling1);
		;

		// Getting complete product details in background thread
		new GetProductDetails().execute();

	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewAlleKlachten.this);
			pDialog.setMessage("Loading product details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("klachtid", klachtid));

						// getting product details by making HTTP request
						// Note that product details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_product_details, "GET", params);

						// check your log for json response
						Log.d("Single Product Details", json.toString());
						
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received product details
							JSONArray productObj = json.getJSONArray(TAG_KLACHT); // JSON Array
							
							// get first product object from JSON Array
							JSONObject klacht = productObj.getJSONObject(0);

							// product with this pid found
							// Edit Text
							naam = (TextView) findViewById(R.id.editTekstNaam);
							telefoonnummer = (EditText) findViewById(R.id.editTextMobiel);
							mailadres = (EditText) findViewById(R.id.editTextMail);
							postcode = (EditText) findViewById(R.id.editPostcode);
							straatnaam = (EditText) findViewById(R.id.editStraatnaam);
							woonplaats = (EditText) findViewById(R.id.editWoonplaats);
							viewkeus = (TextView) findViewById(R.id.viewkeus);
							subkeus = (TextView) findViewById(R.id.viewsub);
							subsubkeus = (TextView) findViewById(R.id.viewsubsub);
							locatiestad = (TextView) findViewById(R.id.kaas);
							locatiestraat = (TextView) findViewById(R.id.kaas2);
							toelichting = (TextView) findViewById(R.id.toelichting);
							status = (TextView) findViewById(R.id.viewstatus);
							
							
							// display product data in EditText
							naam.setText(klacht.getString(TAG_NAAM));
							viewkeus.setText(klacht.getString(TAG_AARDOVERLAST));
							subkeus.setText(klacht.getString(TAG_SUBAARD));
							subsubkeus.setText(klacht.getString(TAG_SUBSUBAARD));
							locatiestad.setText(klacht.getString(TAG_LOCATIESTAD));
							locatiestraat.setText(klacht.getString(TAG_LOCATIESTRAAT));
							toelichting.setText(klacht.getString(TAG_TOELICHTING));
							status.setText(klacht.getString(TAG_KLACHTSTATUS));

						}else{
							// product with pid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
		}
	}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();

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
}
