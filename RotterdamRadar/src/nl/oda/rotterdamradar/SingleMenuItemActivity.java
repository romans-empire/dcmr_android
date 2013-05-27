package nl.oda.rotterdamradar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleMenuItemActivity  extends Activity {
	
	// JSON node keys
	private static final String TAG_INGEVOERDDOOR = "IngevoerdDoor";
	private static final String TAG_DATUMOVERLAST = "DatumOverlast";
	private static final String TAG_AANTAL = "Aantal";
	private static final String TAG_STRAATNAAM = "Straatnaam";
	private static final String TAG_POSTCODE = "Postcode";
	private static final String TAG_PLAATSNAAM = "Plaatsnaam";
	private static final String TAG_AARDOVERLAST = "Aardoverlast";
	private static final String TAG_SUBAARDOVERLAST = "SubaardOverlast";
	private static final String TAG_SUBSUBAARDOVERLAST = "SubsubaardOverlast";
	private static final String TAG_TERUGKOPPELINGGEVRAAGD = "TerugkoppellingGevraagd";
	private static final String TAG_ID = "Id";
	private static final String TAG_GPSDATA = "GPSData";
	private static final String TAG_IMAGE = "Image";
	private static final String TAG_USERID = "UserId";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent    
        String ingevoerddoor = in.getStringExtra(TAG_INGEVOERDDOOR);
		String datumoverlast = in.getStringExtra(TAG_DATUMOVERLAST);
		String aantal = in.getStringExtra(TAG_AANTAL);
		String straatnaam = in.getStringExtra(TAG_STRAATNAAM);
		String postcode = in.getStringExtra(TAG_POSTCODE);
		String plaatsnaam = in.getStringExtra(TAG_PLAATSNAAM);
		String aardoverlast = in.getStringExtra(TAG_AARDOVERLAST);
		String subaardoverlast = in.getStringExtra(TAG_SUBAARDOVERLAST);
		String subsubaardoverlast = in.getStringExtra(TAG_SUBSUBAARDOVERLAST);
		String terugkoppelinggevraagd = in.getStringExtra(TAG_TERUGKOPPELINGGEVRAAGD);
		String id = in.getStringExtra(TAG_ID);
		String gpsdata = in.getStringExtra(TAG_GPSDATA);
		String image = in.getStringExtra(TAG_IMAGE);
		String userid = in.getStringExtra(TAG_USERID);
        
        // Displaying all values on the screen
        TextView lblDatum = (TextView) findViewById(R.id.datum_label);
        TextView lblGebruiker = (TextView) findViewById(R.id.gebruiker_label);
        TextView lblUserid = (TextView) findViewById(R.id.userid_label);
        TextView lblIngevoerddoor = (TextView) findViewById(R.id.ingevoerddoor_label);
        TextView lblTerugkoppeling = (TextView) findViewById(R.id.terugkoppeling_label);
        TextView lblOverlast = (TextView) findViewById(R.id.overlast_label);
        TextView lblAardoverlast = (TextView) findViewById(R.id.aardoverlast_label);
        TextView lblSubaardoverlast = (TextView) findViewById(R.id.subaardoverlast_label);
        TextView lblSubsubaardoverlast = (TextView) findViewById(R.id.subsubaardoverlast_label);
        TextView lblLocatie = (TextView) findViewById(R.id.locatie_label);
        TextView lblPlaatsnaam = (TextView) findViewById(R.id.plaatsnaam_label);
        TextView lblPostcode = (TextView) findViewById(R.id.postcode_label);
        TextView lblStraatnaam = (TextView) findViewById(R.id.straatnaam_label);
        TextView lblGpsdata = (TextView) findViewById(R.id.gpsdata_label);
        TextView lblImage = (TextView) findViewById(R.id.image_label);
        
        lblDatum.setText(datumoverlast);
        lblGebruiker.setText("Gebruiker:");
        lblUserid.setText(userid);
        lblIngevoerddoor.setText(ingevoerddoor);
        lblTerugkoppeling.setText(terugkoppelinggevraagd);
        lblOverlast.setText("\nOverlast:");
        lblAardoverlast.setText(aardoverlast);
        lblSubaardoverlast.setText(subaardoverlast);
        lblSubsubaardoverlast.setText(subsubaardoverlast);
        lblLocatie.setText("\nLocatie:");
        lblPlaatsnaam.setText(plaatsnaam);
        lblPostcode.setText(postcode);
        lblStraatnaam.setText(straatnaam);
        lblGpsdata.setText(gpsdata);
        lblImage.setText(image);
        
        
    }
}
