package hello;

import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationProvider;


/**
 * GPS Retriever Tread
 * <p>
 * @author Yacine Touil
 */
public class GPSRetriever extends Thread {

  private GPSListener listener;

  /**
   * Constructor
   * @param listener The GPS listener
   */
  public GPSRetriever(GPSListener listener) {
    this.listener = listener;
  }

  public void run() {
    try {
      checkLocation();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void checkLocation() throws Exception {
    Criteria cr = new Criteria();
    cr.setHorizontalAccuracy(50);
    cr.setVerticalAccuracy(50);
    // Get an instance of the provider
    LocationProvider lp = LocationProvider.getInstance(cr);
    // Request the location, setting a ten seconds timeout
    Location location = lp.getLocation(10);
    listener.receive(location);
  }
}
