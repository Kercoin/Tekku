package hello;

import javax.microedition.location.Location;


/**
 * GPS Listener interface.
 * 
 * @author Yacine Touil
 */
public interface GPSListener {
   
  /**
   * receive a gps location
   * @param location GPS location
   */
  void receive(Location location);
  
}
