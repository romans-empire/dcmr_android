package nl.oda.rotterdamradar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MijnKlachten extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> productsList;

	// url to get all klachten list
	private static String url_all_products = "http://dcmr.stefanorie.com/klachten/get_all_myproducts.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_KLACHTEN = "klachten";
	private static final String TAG_KLACHTID = "klachtid";
	private static final String TAG_IMEI = "IMEI";
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
	private static final String TAG_CREATED_AT = "created_at";

	// products JSONArray
	JSONArray klachten = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_products);

		// Hashmap for ListView
		productsList = new ArrayList<HashMap<String, String>>();

		// Loading products in Background Thread
		new LoadAllProducts().execute();

		// Get listview
		ListView lv = getListView();

		// on seleting single product
		// launching Edit Product Screen
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String klachtid = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						EditProductActivity.class);
				// sending pid to next activity
				in.putExtra(TAG_KLACHTID, klachtid);
				
				// starting new activity and expecting some response back
				startActivityForResult(in, 100);
			}
		});

	}

	// Response from Edit Product Activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received 
			// means user edited/deleted product
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}

	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MijnKlachten.this);
			pDialog.setMessage("Loading products. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		

		
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);
				
				TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
				String MyIMEINumber = tm.getDeviceId();
				//String IMEI = TAG_IMEI;
				
				if (success == 1){
					// products found
					// Getting Array of Products
					klachten = json.getJSONArray(TAG_KLACHTEN);

					// looping through All Products
					for (int i = 0; i < klachten.length(); i++) {
						JSONObject c = klachten.getJSONObject(i);

						// Storing each json item in variable
						String klachtid = c.getString(TAG_KLACHTID);
						String imei = c.getString(TAG_IMEI);
						String naam = c.getString(TAG_NAAM);
						String telefoonnummer = c.getString(TAG_TELEFOONNUMMER);
						String mailadres = c.getString(TAG_MAILADRES);
						String postcode = c.getString(TAG_POSTCODE);
						String straatnaam = c.getString(TAG_STRAATNAAM);
						String woonplaats = c.getString(TAG_WOONPLAATS);
						String aardoverlast = c.getString(TAG_AARDOVERLAST);
						String subaard = c.getString(TAG_SUBAARD);
						String subsubaard = c.getString(TAG_SUBSUBAARD);
						String toelichting = c.getString(TAG_TOELICHTING);
						String terugkoppeling = c.getString(TAG_TERUGKOPPELING);
						String klachtstatus = c.getString(TAG_KLACHTSTATUS);
						String created_at = c.getString(TAG_CREATED_AT);
						if (MyIMEINumber.equals(imei)){

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_KLACHTID, klachtid);
						map.put(TAG_IMEI, imei);
						map.put(TAG_NAAM, naam);
						map.put(TAG_TELEFOONNUMMER, telefoonnummer);
						map.put(TAG_MAILADRES, mailadres);
						map.put(TAG_POSTCODE, postcode);
						map.put(TAG_STRAATNAAM, straatnaam);
						map.put(TAG_WOONPLAATS, woonplaats);
						map.put(TAG_AARDOVERLAST, aardoverlast);
						map.put(TAG_SUBAARD, subaard);
						map.put(TAG_SUBSUBAARD, subsubaard);
						map.put(TAG_TOELICHTING, toelichting);
						map.put(TAG_TERUGKOPPELING, terugkoppeling);
						map.put(TAG_KLACHTSTATUS, klachtstatus);
						map.put(TAG_CREATED_AT, created_at);
						
						
						productsList.add(map);
						}
						else
						   {
						  Log.d("string imei ", imei.toString());
						  Log.d("MyImei ", MyIMEINumber.toString());
						   }
					}
					
				} else {
					// no products found
					// Launch Add New product Activity
					Intent i = new Intent(getApplicationContext(),
							NewProductActivity.class);
					// Closing all previous activities
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
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
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							MijnKlachten.this, productsList,
							R.layout.list_item, new String[] { TAG_KLACHTID, TAG_NAAM, TAG_CREATED_AT, TAG_KLACHTSTATUS},
							new int[] { R.id.pid, R.id.name, R.id.created_at, R.id.viewstatus });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}