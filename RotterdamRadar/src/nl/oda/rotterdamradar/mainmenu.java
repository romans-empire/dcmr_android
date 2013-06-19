package nl.oda.rotterdamradar;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class mainmenu extends Activity {
	
	public static Vibrator myVib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.notrans, R.anim.notrans);
		setContentView(R.layout.mainmenu);
		StartAnimations();
		
		myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

		ImageButton button1 = (ImageButton) findViewById(R.id.button1);
		ImageButton button2 = (ImageButton) findViewById(R.id.button2);
		ImageButton button3 = (ImageButton) findViewById(R.id.button3);
		ImageButton button4 = (ImageButton) findViewById(R.id.button4);
		ImageButton button5 = (ImageButton) findViewById(R.id.button5);
		ImageButton button6 = (ImageButton) findViewById(R.id.button6);		
		
		//mooie knoppen
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Vibrate();
				Intent x = new Intent(mainmenu.this, MailActivity.class);
				startActivity(x);

			}
		});

		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Vibrate();
				Intent x = new Intent(mainmenu.this, Info.class);
				startActivity(x);

			}
		});
		
		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Vibrate();
				Intent x = new Intent(mainmenu.this, MapActivity.class);
				startActivity(x);

			}
		});
		
		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Vibrate();
				Intent x = new Intent(mainmenu.this, MainScreenActivity.class);
				startActivity(x);

			}
		});
		
		button5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Vibrate();
				Intent x = new Intent(mainmenu.this, Nieuws.class);
				startActivity(x);

			}
		});
		
		//de share knop
		button6.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Vibrate();
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent
						.putExtra(
								Intent.EXTRA_TEXT,
								"Gebruik net als mij de DCMR app om meldingen te doen bij de DCMR als u ook last heeft van bedrijven in de buurt!");
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, "The title");
				startActivity(Intent.createChooser(shareIntent, "Share..."));

			}
		});
	}
	
	public static void Vibrate()
	{
		myVib.vibrate(50);
	}
	
	//animatie
	private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.TableLayout1);
        l.clearAnimation();
        l.startAnimation(anim);
	}
}
