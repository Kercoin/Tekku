package org.kercoin.tekku.model;
  

/**
 * GPS Location
 * @author tym
 */
public class Location {
  
  /** latitude coordinate */
  private double latitude;
  
  /** longitude coordinate */
  private double longitude;
  
  /** altitude of this GPS position */
  private float altitude;
  
  /**
   * Public default constructor 
   * @param latitude latitude coordinate
   * @param longitude longitude coordinate
   */
  public Location(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Public default constructor 
   * @param latitude latitude coordinate
   * @param longitude longitude coordinate
   * @param altitude altitude of this GPS position
   */
  public Location(double latitude, double longitude, float altitude) {
    this(latitude, longitude);
    this.altitude = altitude;
  }

  /**
   * @return the latitude
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * @param latitude the latitude to set
   */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /**
   * @return the longitude
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * @param longitude the longitude to set
   */
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /**
   * @return the altitude
   */
  public float getAltitude() {
    return altitude;
  }

  /**
   * @param altitude the altitude to set
   */
  public void setAltitude(float altitude) {
    this.altitude = altitude;
  }
  
  // ###########################################################################
  // overriden methods
  // ###########################################################################
  
  @Override
  public int hashCode() {
    int hashCode = (int) (longitude * (32 + altitude + latitude));
    return hashCode;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Location)) {
      return false;
    }
    Location location = (Location) obj;

    return latitude == location.latitude
        && longitude == location.longitude
        && altitude == location.altitude;
  }
  
  @Override
  public String toString() {
    // TODO print a better GPS location text.
    return "Location : " + latitude + ", " + longitude + ", " + altitude;
  }
  
  
}
