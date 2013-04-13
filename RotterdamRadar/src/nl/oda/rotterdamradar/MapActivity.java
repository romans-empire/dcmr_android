package nl.oda.rotterdamradar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MapActivity extends FragmentActivity implements LocationListener {

	Context context = this;
	GoogleMap gmap;
	private ImageView imageview;
	private String markerIcon = "default";
	private AlertDialog alertf;
	Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initMap();

		CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(51.923,
				4.478));
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

		gmap.moveCamera(center);
		gmap.animateCamera(zoom);

		gmap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker mark) {
				LayoutInflater li = LayoutInflater.from(context);
				final View v = li.inflate(R.layout.infoviewer, null);

				String io = mark.getTitle();
				String im = mark.getSnippet();

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(v);
				builder.setCancelable(true);

				TextView onderwerp = (TextView) v.findViewById(R.id.iOnderwerp);
				TextView melding = (TextView) v.findViewById(R.id.iMelding);
				// ImageView foto = (ImageView) v.findViewById(R.id.iFoto);

				onderwerp.setText(io);
				melding.setText(im);
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

		gmap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng point) {
				// TODO Auto-generated method stub
				mark(point);
			}

		});

	}

	public void mark(final LatLng latlng) {
		LayoutInflater li = LayoutInflater.from(context);
		final View v = li.inflate(R.layout.markermenu, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setView(v);
		builder.setCancelable(false);

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(final DialogInterface dialog, int which) {

				EditText snippet = (EditText) v.findViewById(R.id.meldingedit);

				String markerTitel = spinner.getSelectedItem().toString();

				if (markerTitel.equals("Stof")) {
					gmap.addMarker(new MarkerOptions()
							.title(markerTitel+" overlast")
							.snippet(snippet.getText().toString())
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
							.position(latlng));
				}
				if (markerTitel.equals("Light")) {
					gmap.addMarker(new MarkerOptions()
							.title(markerTitel+" overlast")
							.snippet(snippet.getText().toString())
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
							.position(latlng));

				}
				if (markerTitel.equals("Stank")) {
					gmap.addMarker(new MarkerOptions()
							.title(markerTitel+" overlast")
							.snippet(snippet.getText().toString())
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
							.position(latlng));

				}
				if (markerTitel.equals("Geluid")) {
					gmap.addMarker(new MarkerOptions()
							.title(markerTitel+" overlast")
							.snippet(snippet.getText().toString())
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_RED))
							.position(latlng));

				}
			}
		});

		builder.setNegativeButton("Nope",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();

					}
				});

		AlertDialog alert = builder.create();

		alertf = alert;

		alert.show();

		spinner = (Spinner) alert.findViewById(R.id.spinmark);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinnerlijst,
				android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(adapter);

		Button buttonImgLoader = (Button) alert.findViewById(R.id.ibutton);
		Button fotoImgLoader = (Button) alert.findViewById(R.id.mmfoto);

		buttonImgLoader.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent choosePictureIntent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(choosePictureIntent, 0);
			}
		});

		fotoImgLoader.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent takePicture = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(takePicture, 1);
			}

		});

	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
		switch (requestCode) {
		case 0:
			if (resultCode == RESULT_OK) {
				Uri selectedImage = imageReturnedIntent.getData();
				imageview = (ImageView) alertf.findViewById(R.id.imgMM);
				imageview.setImageURI(selectedImage);
			}

			break;
		case 1:
			if (resultCode == RESULT_OK) {
				Uri selectedImage = imageReturnedIntent.getData();
				imageview = (ImageView) alertf.findViewById(R.id.imgMM);
				imageview.setImageURI(selectedImage);
			}
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
		AlertDialog.Builder alertbuild = new AlertDialog.Builder(this);
		alertbuild.setTitle("GPS status : UIT");
		alertbuild.setCancelable(false);
		alertbuild.setPositiveButton("Schakel GPS aan",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Intent startGPS = new Intent(
								android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(startGPS);
						// throw new
						// UnsupportedOperationException("Functie niet beschikbaar");
					}
				});

		alertbuild.setNegativeButton("GPS niet inschakelen",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		AlertDialog alert = alertbuild.create();
		alert.show();
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	private void initMap() {
		SupportMapFragment mapfrag = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		gmap = mapfrag.getMap();
		gmap.setMyLocationEnabled(true);
		gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}

}
