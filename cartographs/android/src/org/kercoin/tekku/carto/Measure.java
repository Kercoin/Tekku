package org.kercoin.tekku.carto;

import org.kercoin.tekku.carto.MeasureService.GPSQuality;
import org.kercoin.tekku.carto.MeasureService.GSMQuality;
import org.kercoin.tekku.carto.MeasureService.MeasureServiceListener;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Measure extends Activity implements MeasureServiceListener, OnClickListener {

	private TextView accuracyTV;
	private TextView locationTV;
	private TextView gpsQuality;
	private Button mark;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		load();
		doBindService();
		
	}
	
	private void load() {
		setContentView(R.layout.go);
		Button done = (Button) findViewById(R.id.btn_measure_done);
		done.setOnClickListener(this);
		locationTV = (TextView) findViewById(R.id.location);
		accuracyTV = (TextView) findViewById(R.id.accuracy);
		gpsQuality = (TextView) findViewById(R.id.gps_quality);
		mark = (Button) findViewById(R.id.btn_measure_mark);
	}
	
	private MeasureService mBoundService;

	private ServiceConnection mConnection = new ServiceConnection() {
	    public void onServiceConnected(ComponentName className, IBinder service) {
	        // This is called when the connection with the service has been
	        // established, giving us the service object we can use to
	        // interact with the service.  Because we have bound to a explicit
	        // service that we know is running in our own process, we can
	        // cast its IBinder to a concrete class and directly access it.
	        mBoundService = ((MeasureService.LocalBinder)service).getService();
	        mBoundService.addMeasureServiceListener(Measure.this);
	        // Tell the user about this for our demo.
	        Toast.makeText(Measure.this, R.string.measure_service_connected,
	                Toast.LENGTH_SHORT).show();
	    }

	    public void onServiceDisconnected(ComponentName className) {
	        // This is called when the connection with the service has been
	        // unexpectedly disconnected -- that is, its process crashed.
	        // Because it is running in our same process, we should never
	        // see this happen.
	        mBoundService = null;
	        Toast.makeText(Measure.this, R.string.measure_service_disconnected,
	                Toast.LENGTH_SHORT).show();
	    }
	};

	private boolean mIsBound;

	void doBindService() {
	    // Establish a connection with the service.  We use an explicit
	    // class name because we want a specific service implementation that
	    // we know will be running in our own process (and thus won't be
	    // supporting component replacement by other applications).
	    bindService(new Intent(Measure.this, 
	            MeasureService.class), mConnection, Context.BIND_AUTO_CREATE);
	    mIsBound = true;
	    
	}

	void doUnbindService() {
	    if (mIsBound) {
	        // Detach our existing connection.
	        unbindService(mConnection);
	        mIsBound = false;
	    }
	}

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    doUnbindService();
	}

	@Override
	public void updateGSMQuality(GSMQuality q) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateGPSQuality(GPSQuality q) {
		this.gpsQuality.setText("GPS: " + q.name());
		this.mark.setEnabled(GPSQuality.TOP.equals(q));
	}
	
	@Override
	public void locationChanged(Location location) {
		locationTV.setText(location.getLatitude() + " " + location.getLongitude());
		accuracyTV.setText(location.getAccuracy() + "m");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_measure_done:
			doUnbindService();
			startActivity(new Intent(this, Cartograph.class));
			break;
		}
	}
	
}
