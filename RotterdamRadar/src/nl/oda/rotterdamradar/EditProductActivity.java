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

public class EditProductActivity extends Activity implements OnItemSelectedListener {

	EditText Inaam, Itelefoonnummer, Imailadres, Ipostcode, Istraatnaam, Iwoonplaats, Iviewkeus, Isubkeus, Isubsubkeus, Itoelichting, Istatus;
	Button btnSave, btnDelete;
	Spinner Iaardoverlast, Isubaard, Isubsubaard, Iterugkoppeling;

	String klachtid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_product_details = "http://dcmr.stefanorie.com/klachten/get_product_details.php";

	// url to update product
	private static final String url_update_product = "http://dcmr.stefanorie.com/klachten/update_product.php";
	
	// url to delete product
	private static final String url_delete_product = "http://dcmr.stefanorie.com/klachten/delete_product.php";

	// JSON Node names
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
	private static final String TAG_TOELICHTING = "toelichting";
	private static final String TAG_TERUGKOPPELING = "terugkoppeling";
	private static final String TAG_KLACHTSTATUS = "klachtstatus";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_product);
		
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
		
		Iaardoverlast = (Spinner) findViewById(R.id.spinner);
		String subjects[] = new String[] {"Selecteer", "afval", "bodem", "Stankoverlast", "Geluidsoverlast", "Stofoverlast", "Lichtoverlast" };
		Iaardoverlast.setOnItemSelectedListener(this);
		ArrayAdapter<String> sa = new ArrayAdapter<String>(this,
				R.layout.spinner_item, subjects);
		sa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		Iaardoverlast.setAdapter(sa);
		;
		

		Isubaard = (Spinner) findViewById(R.id.spinner1);
		String onderwerpen[] = new String[] {"Selecteer", "riool", "kassen", "rook", "vliegtuig", "fabriek", "muziek" };
		Isubaard.setOnItemSelectedListener(this);
		ArrayAdapter<String> subaard = new ArrayAdapter<String>(this,
				R.layout.spinner_item, onderwerpen);
		subaard.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		Isubaard.setAdapter(subaard);
		;
		
		Isubsubaard = (Spinner) findViewById(R.id.spinner2);
		String subonderwerpen[] = new String[] {"Selecteer", "helicopter", "brandlucht", "DJ", "feest" };
		Isubsubaard.setOnItemSelectedListener(this);
		ArrayAdapter<String> subsubaard = new ArrayAdapter<String>(this,
				R.layout.spinner_item, subonderwerpen);
		subsubaard.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		Isubsubaard.setAdapter(subsubaard);
		;
		
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

		// save button click event
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// starting background task to update product
				new SaveProductDetails().execute();
			}
		});

		// Delete button click event
		btnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// deleting product in background thread
				new DeleteProduct().execute();
			}
		});

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
			pDialog = new ProgressDialog(EditProductActivity.this);
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
							Inaam = (EditText) findViewById(R.id.editTekstNaam);
							Itelefoonnummer = (EditText) findViewById(R.id.editTextMobiel);
							Imailadres = (EditText) findViewById(R.id.editTextMail);
							Ipostcode = (EditText) findViewById(R.id.editPostcode);
							Istraatnaam = (EditText) findViewById(R.id.editStraatnaam);
							Iwoonplaats = (EditText) findViewById(R.id.editWoonplaats);
							Iviewkeus = (EditText) findViewById(R.id.viewkeus);
							Isubkeus = (EditText) findViewById(R.id.viewsub);
							Isubsubkeus = (EditText) findViewById(R.id.viewsubsub);
							Itoelichting = (EditText) findViewById(R.id.toelichting);
							Istatus = (EditText) findViewById(R.id.viewstatus);
						

							// display product data in EditText
							Inaam.setText(klacht.getString(TAG_NAAM));
							Itelefoonnummer.setText(klacht.getString(TAG_TELEFOONNUMMER));
							Imailadres.setText(klacht.getString(TAG_MAILADRES));
							Ipostcode.setText(klacht.getString(TAG_POSTCODE));
							Istraatnaam.setText(klacht.getString(TAG_STRAATNAAM));
							Iwoonplaats.setText(klacht.getString(TAG_WOONPLAATS));
							Iviewkeus.setText(klacht.getString(TAG_AARDOVERLAST));
							Isubkeus.setText(klacht.getString(TAG_SUBAARD));
							Isubsubkeus.setText(klacht.getString(TAG_SUBSUBAARD));
							Itoelichting.setText(klacht.getString(TAG_TOELICHTING));
							Istatus.setText(klacht.getString(TAG_KLACHTSTATUS));

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
	 * Background Async Task to  Save product Details
	 * */
	class SaveProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditProductActivity.this);
			pDialog.setMessage("Saving product ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving product
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String naam = Inaam.getText().toString();
			String nummer = Itelefoonnummer.getText().toString();
			String mailadres = Imailadres.getText().toString();
			String postcode = Ipostcode.getText().toString();
			String straatnaam = Istraatnaam.getText().toString();
			String woonplaats = Iwoonplaats.getText().toString();
			String aardoverlast = Iaardoverlast.getSelectedItem().toString();
			String subaard = Isubaard.getSelectedItem().toString();
			String subsubaard = Isubsubaard.getSelectedItem().toString();
			String toelichting = Itoelichting.getText().toString();
			String terugkoppeling = Iterugkoppeling.getSelectedItem().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_KLACHTID, klachtid));
			params.add(new BasicNameValuePair(TAG_NAAM, naam));
			params.add(new BasicNameValuePair(TAG_TELEFOONNUMMER, nummer));
			params.add(new BasicNameValuePair(TAG_MAILADRES, mailadres));
			params.add(new BasicNameValuePair(TAG_POSTCODE, postcode));
			params.add(new BasicNameValuePair(TAG_STRAATNAAM, straatnaam));
			params.add(new BasicNameValuePair(TAG_WOONPLAATS, woonplaats));
			params.add(new BasicNameValuePair(TAG_AARDOVERLAST, aardoverlast));
			params.add(new BasicNameValuePair(TAG_SUBAARD, subaard));
			params.add(new BasicNameValuePair(TAG_SUBSUBAARD, subsubaard));
			params.add(new BasicNameValuePair(TAG_TOELICHTING, toelichting));
			params.add(new BasicNameValuePair(TAG_TERUGKOPPELING, terugkoppeling));
			

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_product, "POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about product update
					setResult(100, i);
					finish();
				} else {
					// failed to update product
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
			// dismiss the dialog once product uupdated
			pDialog.dismiss();
		}
	}

	/*****************************************************************
	 * Background Async Task to Delete Product
	 * */
	class DeleteProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditProductActivity.this);
			pDialog.setMessage("Deleting Product...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Deleting product
		 * */
		protected String doInBackground(String... args) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("klachtid", klachtid));

				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(
						url_delete_product, "POST", params);

				// check your log for json response
				Log.d("Delete Product", json.toString());
				
				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// product successfully deleted
					// notify previous activity by sending code 100
					Intent i = getIntent();
					// send result code 100 to notify about product deletion
					setResult(100, i);
					finish();
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
			// dismiss the dialog once product deleted
			pDialog.dismiss();

		}

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
