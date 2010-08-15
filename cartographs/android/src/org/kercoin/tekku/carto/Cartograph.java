package org.kercoin.tekku.carto;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Cartograph extends Activity implements OnClickListener {
	
	private transient ArrayAdapter<CharSequence> networkAdapter;
	private transient ArrayAdapter<CharSequence> lineAdapter;

	private transient ArrayAdapter<CharSequence> directionAdapter;
	private transient ArrayAdapter<CharSequence> departureAdapter;
	private transient ArrayAdapter<CharSequence> stationAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        load1of2();
    }

	private void load1of2() {
		setContentView(R.layout.main1of2);
		if (networkAdapter == null)
			networkAdapter = ArrayAdapter.createFromResource(
				this, R.array.networks, android.R.layout.simple_spinner_item);
		Spinner network = (Spinner) findViewById(R.id.network);
		network.setAdapter(networkAdapter);

		if (lineAdapter == null)
			lineAdapter = ArrayAdapter.createFromResource(
				this, R.array.lines, android.R.layout.simple_spinner_item);
		Spinner line = (Spinner) findViewById(R.id.line);
		line.setAdapter(lineAdapter);
		
		Button next1of2 = (Button) findViewById(R.id.btn_1of2_next);
		next1of2.setOnClickListener(this);
	}
	
	private void load2of2() {
		setContentView(R.layout.main2of2);
		if (directionAdapter == null)
			directionAdapter = ArrayAdapter.createFromResource(
				this, R.array.directions, android.R.layout.simple_spinner_item);
		Spinner direction = (Spinner) findViewById(R.id.direction);
		direction.setAdapter(directionAdapter);
		
//		if (departureAdapter == null)
//			departureAdapter = ArrayAdapter.createFromResource(
//				this, R.array.stations, android.R.layout.simple_spinner_item);
		Spinner departure = (Spinner) findViewById(R.id.departure);
		departure.setAdapter(stationAdapter);
		
		if (stationAdapter == null)
			stationAdapter = ArrayAdapter.createFromResource(
				this, R.array.stations, android.R.layout.simple_spinner_item);
		Spinner arrival = (Spinner) findViewById(R.id.arrival);
		arrival.setAdapter(stationAdapter);
		
		Button previous2of2 = (Button) findViewById(R.id.btn_2of2_previous);
		previous2of2.setOnClickListener(this);
		Button go = (Button) findViewById(R.id.btn_2of2_go);
		go.setOnClickListener(this);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	boolean superResult = super.onCreateOptionsMenu(menu);
    	MenuItem option = menu.add(R.string.menu_option);
    	option.setIcon(android.R.drawable.ic_menu_preferences);
		return superResult;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_1of2_next:
			load2of2();
			break;
		case R.id.btn_2of2_previous:
			load1of2();
			break;
		case R.id.btn_2of2_go:
			go();
			break;
		}
	}

	private void go() {
		// TODO Auto-generated method stub
		
	}
		
}
