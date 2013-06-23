package nl.oda.rotterdamradar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;


public class MainScreenActivity extends Activity{
	
	ImageButton btnViewProducts, buttonklacht, buttonklacht2, btnNewProduct;
	public static Vibrator myVib;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		
		myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		
		// Buttons
		btnViewProducts = (ImageButton) findViewById(R.id.btnViewProducts);
		btnNewProduct = (ImageButton) findViewById(R.id.btnCreateProduct);
		buttonklacht = (ImageButton) findViewById(R.id.mijnklacht);
		buttonklacht2 = (ImageButton) findViewById(R.id.mijnklacht1);
		
		// view products click event
		btnViewProducts.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching All klachten Activity
				Intent i = new Intent(getApplicationContext(), ViewAlleKlachten.class);
				startActivity(i);
				
			}
		});
		
		// view products click event
		btnNewProduct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching create new klacht activity
				Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
				startActivity(i);
				
			}
		});
		buttonklacht.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching imei checker
				Intent i = new Intent(getApplicationContext(), MijnKlacht.class);
				startActivity(i);
				
			}
		});
		buttonklacht2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching mijn klachten
				Intent i = new Intent(getApplicationContext(), MijnKlachten.class);
				startActivity(i);
				
			}
		});
	}
}
