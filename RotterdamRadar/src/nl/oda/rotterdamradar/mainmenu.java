package nl.oda.rotterdamradar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class mainmenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.notrans, R.anim.notrans);
		setContentView(R.layout.mainmenu);
		StartAnimations();

		ImageButton button1 = (ImageButton) findViewById(R.id.button1);
		ImageButton button2 = (ImageButton) findViewById(R.id.button2);
		ImageButton button3 = (ImageButton) findViewById(R.id.button3);
		ImageButton button4 = (ImageButton) findViewById(R.id.button4);
		Button button5 =(Button) findViewById(R.id.button5);

		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent x = new Intent(mainmenu.this, MailActivity.class);
				startActivity(x);

			}
		});

		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent x = new Intent(mainmenu.this, Info.class);
				startActivity(x);

			}
		});

		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.exit(0);

			}
		});

		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent x = new Intent(mainmenu.this, MapActivity.class);
				startActivity(x);

			}
		});
		
		button5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent x = new Intent(mainmenu.this, Nieuws.class);
				startActivity(x);

			}
		});
		
	}
	
	private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.TableLayout1);
        l.clearAnimation();
        l.startAnimation(anim);
	}
}
