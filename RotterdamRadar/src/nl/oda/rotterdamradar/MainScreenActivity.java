package nl.oda.rotterdamradar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainScreenActivity extends Activity{
	
	Button btnViewProducts, buttonklacht, buttonklacht2, btnNewProduct;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		
		// Buttons
		btnViewProducts = (Button) findViewById(R.id.btnViewProducts);
		btnNewProduct = (Button) findViewById(R.id.btnCreateProduct);
		buttonklacht = (Button) findViewById(R.id.mijnklacht);
		buttonklacht2 = (Button) findViewById(R.id.mijnklacht1);
		
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
