package org.kercoin.tekku.carto;

import java.util.Set;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class MeasureService extends Service implements LocationListener, Listener {

	public static enum GPSQuality {
		TOP, MEDIUM, BAD
	}
	public static enum GSMQuality {
		TOP, MEDIUM, BAD
	}
	
	public static interface MeasureServiceListener {
		void updateGPSQuality(GPSQuality q);
		void updateGSMQuality(GSMQuality q);
		void locationChanged(Location location);
	}

	private static final float BEST = 10f;

	private static final float MEDIUM = 20f;
	
	private LocationManager locationManager;
	private NotificationManager notificationManager;
	  
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        MeasureService getService() {
            return MeasureService.this;
        }
    }
    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();
	
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		showNotification();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, this);
//		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 30f, this);
		locationManager.addGpsStatusListener(this);
	}
	
	private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.measure_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(android.R.drawable.ic_menu_mylocation, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Measure.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.measure_service_label),
                       text, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        notificationManager.notify(R.string.measure_service_started, notification);
	}

	@Override
	public void onDestroy() {
		locationManager.removeUpdates(this);
		notificationManager.cancel(R.string.measure_service_started);
        // Tell the user we stopped.
        Toast.makeText(this, R.string.measure_service_stopped, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onGpsStatusChanged(int event) {
		GpsStatus status = locationManager.getGpsStatus(null);
		switch(event) {
		case GpsStatus.GPS_EVENT_FIRST_FIX:
			
			break;
		case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
			
			break;
		case GpsStatus.GPS_EVENT_STARTED:
			
			break;
		case GpsStatus.GPS_EVENT_STOPPED:
			// cancel current tracking
			break;
		}
		
	}

	@Override
	public void onLocationChanged(Location location) {
		GPSQuality q = GPSQuality.BAD;
		if (location.getAccuracy() < BEST) {
			q = GPSQuality.TOP;
		} else if (location.getAccuracy() < MEDIUM){
			q = GPSQuality.MEDIUM;
		}
		fire(location, q);
	}

	private void fire(Location location, GPSQuality q) {
		if (location != null || q != null) {
			for (MeasureServiceListener l : listeners) {
				if (location != null)
					l.locationChanged(location);
				if (q != null)
					l.updateGPSQuality(q);
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}


	// Notification API
	private Set<MeasureServiceListener> listeners = new java.util.HashSet<MeasureServiceListener>();
	public void addMeasureServiceListener(MeasureServiceListener l) { listeners.add(l); }
	public void removeMeasureServiceListener(MeasureServiceListener l) { listeners.remove(l); }

}
