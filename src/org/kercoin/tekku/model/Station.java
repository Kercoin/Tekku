  package org.kercoin.tekku.model;


import org.kercoin.tekku.util.EqualsHelper;
import org.kercoin.tekku.util.Validator;

/**
 * Station class represent a transport characteristic stop like a bus stop.
 * @author tym
*/
public class Station {
  
  /**
   * GPS location for this station.
   */
  private Location location;
  
  /**
   * a free text to describe a Station.
   * TODO : define more the description : underground, access, ... /
   */
  private String description;

  /**
   * Public default constructor
   * @param location GPS location for this station
   */
  public Station(Location location) {
    Validator.notNull(location, "location cannot be null");
    this.location = location;
  }
  
  /**
   * Public default constructor
   * @param location GPS location for this station
   * @param description description of this station
   */
  public Station(Location location, String description) {
    this(location);
    this.description = description;
  }
  
  /**
   * @return the location
   */
  public Location getLocation() {
    return location;
  }

  /**
   * @param location the location to set
   */
  public void setLocation(Location location) {
    Validator.notNull(location, "location cannot be null");
    this.location = location;
  }
  
  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  // ###########################################################################
  // overriden methods
  // ###########################################################################
  
  @Override
  public int hashCode() {
    int hashCode = location.hashCode();
    return hashCode;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Station)) {
      return false;
    }
    Station station = (Station) obj;

    return EqualsHelper.equals(location, station.location)
        && EqualsHelper.equals(description, station.description);
  }
  
  @Override
  public String toString() {
    return "Station : " + location
      + ((description == null) ? "" : ", " + description);
  }
  
}
