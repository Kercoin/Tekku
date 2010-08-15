package org.kercoin.tekku.carto;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Cartograph extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Spinner network = (Spinner) findViewById(R.id.network);
        ArrayAdapter<CharSequence> networkAdapter = ArrayAdapter.createFromResource(
        	    this, R.array.networks, android.R.layout.simple_spinner_item);
		network.setAdapter(networkAdapter);
		Spinner line = (Spinner) findViewById(R.id.line);
		ArrayAdapter<CharSequence> lineAdapter = ArrayAdapter.createFromResource(
				this, R.array.lines, android.R.layout.simple_spinner_item);
		line.setAdapter(lineAdapter);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	boolean superResult = super.onCreateOptionsMenu(menu);
    	MenuItem option = menu.add(R.string.menu_option);
    	option.setIcon(R.drawable.icon);
		return superResult;
    }
}