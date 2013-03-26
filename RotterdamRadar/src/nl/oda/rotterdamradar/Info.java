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

        
        TabSpec specs1 = tabHost.newTabSpec("InfoApp");
        specs1.setIndicator("infoApp");
        specs1.setContent(R.id.infoApp);
        
        TabSpec specs2 = tabHost.newTabSpec("infoDCMR");
        specs2.setIndicator("infoDCMR");
        specs2.setContent(R.id.infoDCMR);
       
        
        tabHost.addTab(specs1);
        tabHost.addTab(specs2);
      
    }
}