package nl.oda.rotterdamradar;

import android.app.Activity;

import android.os.Bundle;

import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Info extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);       
        TabHost tabHost = (TabHost) findViewById (R.id.tabhost);
        tabHost.setup(); 

        //tabbladen in de activity
        TabSpec specs2 = tabHost.newTabSpec("infoApp");
        specs2.setIndicator("infoApp");
        specs2.setContent(R.id.infoApp);
        
        TabSpec specs1 = tabHost.newTabSpec("infoDCMR");
        specs1.setIndicator("infoDCMR");
        specs1.setContent(R.id.infoDCMR);
       
        TabSpec specs3 = tabHost.newTabSpec("Gebruik app");
        specs3.setIndicator("Gebruik app");
        specs3.setContent(R.id.appgebruik);
        
        tabHost.addTab(specs1);
        tabHost.addTab(specs2);
        tabHost.addTab(specs3);
      
    }
}