package nl.oda.rotterdamradar;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidJSONParsingActivity extends ListActivity {

	// url to make request
	private static String url = "http://api-dcmr.azurewebsites.net/api/Klacht/?testid=7";

	// JSON Node names
	private static final String TAG_INGEVOERDDOOR = "IngevoerdDoor";
	private static final String TAG_DATUMOVERLAST = "DatumOverlast";
	private static final String TAG_AANTAL = "Aantal";
	private static final String TAG_STRAATNAAM = "Straatnaam";
	private static final String TAG_POSTCODE = "Postcode";
	private static final String TAG_PLAATSNAAM = "Plaatsnaam";
	private static final String TAG_AARDOVERLAST = "Aardoverlast";
	private static final String TAG_SUBAARDOVERLAST = "SubaardOverlast";
	private static final String TAG_SUBSUBAARDOVERLAST = "SubsubaardOverlast";
	private static final String TAG_TERUGKOPPELINGGEVRAAGD = "TerugkoppelingGevraagd";
	private static final String TAG_KLACHTID = "Id";
	private static final String TAG_GPSDATA = "GPSData";
	private static final String TAG_IMAGE = "Image";
	private static final String TAG_USERID = "UserId";

	// contacts JSONArray
	// JSONArray user = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);

		try {
			
			// looping through All Contacts
				
				// Storing each json item in variable
				String ingevoerddoor = json.getString(TAG_INGEVOERDDOOR);
				String datumoverlast = json.getString(TAG_DATUMOVERLAST);
				String aantal = json.getString(TAG_AANTAL);
				String straatnaam = json.getString(TAG_STRAATNAAM);
				String postcode = json.getString(TAG_POSTCODE);
				String plaatsnaam = json.getString(TAG_PLAATSNAAM);
				String aardoverlast = json.getString(TAG_AARDOVERLAST);
				String subaardoverlast = json.getString(TAG_SUBAARDOVERLAST);
				String subsubaardoverlast = json.getString(TAG_SUBSUBAARDOVERLAST);
				String terugkoppelinggevraagd = json.getString(TAG_TERUGKOPPELINGGEVRAAGD);
				String klachtid = json.getString(TAG_KLACHTID);
				String gpsdata = json.getString(TAG_GPSDATA);
				String image = json.getString(TAG_IMAGE);
				String userid = json.getString(TAG_USERID);
				
				
//				// Phone number is agin JSON Object
//				JSONObject phone = c.getJSONObject(TAG_PHONE);
//				String mobile = phone.getString(TAG_PHONE_MOBILE);
//				String home = phone.getString(TAG_PHONE_HOME);
//				String office = phone.getString(TAG_PHONE_OFFICE);
				
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				
				// adding each child node to HashMap key => value
				map.put(TAG_INGEVOERDDOOR, ingevoerddoor);
				map.put(TAG_DATUMOVERLAST, datumoverlast);
				map.put(TAG_AANTAL, aantal);
				map.put(TAG_STRAATNAAM, straatnaam);
				map.put(TAG_POSTCODE, postcode);
				map.put(TAG_PLAATSNAAM, plaatsnaam);
				map.put(TAG_AARDOVERLAST, aardoverlast);
				map.put(TAG_SUBAARDOVERLAST, subaardoverlast);
				map.put(TAG_SUBSUBAARDOVERLAST, subsubaardoverlast);
				map.put(TAG_TERUGKOPPELINGGEVRAAGD, terugkoppelinggevraagd);
				map.put(TAG_KLACHTID, klachtid);
				map.put(TAG_GPSDATA, gpsdata);
				map.put(TAG_IMAGE, image);
				map.put(TAG_USERID, userid);

				// adding HashList to ArrayList
				contactList.add(map);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("JSONException", "Error: " + e.toString());
			Log.e("JSONException", "Dit is de json: " + json);
		}
		
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, contactList,
				R.layout.list_item,
				new String[] { TAG_DATUMOVERLAST, TAG_STRAATNAAM, TAG_AARDOVERLAST, TAG_INGEVOERDDOOR, TAG_AANTAL, TAG_POSTCODE
				, TAG_PLAATSNAAM, TAG_SUBAARDOVERLAST, TAG_SUBSUBAARDOVERLAST, TAG_TERUGKOPPELINGGEVRAAGD, TAG_KLACHTID
				, TAG_GPSDATA, TAG_IMAGE, TAG_USERID}, new int[] {
						R.id.datum, R.id.straatnaam, R.id.aardoverlast, R.id.ingevoerddoor, R.id.aantal, R.id.postcode,
						R.id.plaatsnaam, R.id.subaardoverlast, R.id.subsubaardoverlast, R.id.terugkoppelinggevraagd,
						R.id.klachtid, R.id.gpsdata, R.id.image, R.id.userid});

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				mainmenu.myVib.vibrate(50);
				String datumoverlast = ((TextView) view.findViewById(R.id.datum)).getText().toString();
				String straatnaam = ((TextView) view.findViewById(R.id.straatnaam)).getText().toString();
				String aardoverlast = ((TextView) view.findViewById(R.id.aardoverlast)).getText().toString();
				String ingevoerddoor = ((TextView) view.findViewById(R.id.ingevoerddoor)).getText().toString();
				String aantal = ((TextView) view.findViewById(R.id.aantal)).getText().toString();
				String postcode = ((TextView) view.findViewById(R.id.postcode)).getText().toString();
				String plaatsnaam = ((TextView) view.findViewById(R.id.plaatsnaam)).getText().toString();
				String subaardoverlast = ((TextView) view.findViewById(R.id.subaardoverlast)).getText().toString();
				String subsubaardoverlast = ((TextView) view.findViewById(R.id.subsubaardoverlast)).getText().toString();
				String terugkoppelinggevraagd = ((TextView) view.findViewById(R.id.terugkoppelinggevraagd)).getText().toString();
				String klachtid = ((TextView) view.findViewById(R.id.klachtid)).getText().toString();
				String gpsdata = ((TextView) view.findViewById(R.id.gpsdata)).getText().toString();
				String image = ((TextView) view.findViewById(R.id.image)).getText().toString();
				String userid = ((TextView) view.findViewById(R.id.userid)).getText().toString();
				
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(TAG_INGEVOERDDOOR, ingevoerddoor);
				in.putExtra(TAG_DATUMOVERLAST, datumoverlast);
				in.putExtra(TAG_AANTAL, aantal);
				in.putExtra(TAG_STRAATNAAM, straatnaam);
				in.putExtra(TAG_POSTCODE, postcode);
				in.putExtra(TAG_PLAATSNAAM, plaatsnaam);
				in.putExtra(TAG_AARDOVERLAST, aardoverlast);
				in.putExtra(TAG_SUBAARDOVERLAST, subaardoverlast);
				in.putExtra(TAG_SUBSUBAARDOVERLAST, subsubaardoverlast);
				in.putExtra(TAG_TERUGKOPPELINGGEVRAAGD, terugkoppelinggevraagd);
				in.putExtra(TAG_KLACHTID, klachtid);
				in.putExtra(TAG_GPSDATA, gpsdata);
				in.putExtra(TAG_IMAGE, image);
				in.putExtra(TAG_USERID, userid);
				startActivity(in);

			}
		});
		
		

	}
}