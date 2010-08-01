package opt.git.tekku.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import opt.git.tekku.util.EqualsHelper;
import opt.git.tekku.util.Validator;


/**
 * Transport class represent a kind of transporter. example : bus, train, ...
 * @author tym
 */
public class Transport {
  
  /** primary name of a Transport*/
  private String name;

  /** all possible names of this Transport including primary one */
  private Set<String> names;
  
  /** type of a Transport */
  private TransportType type;
  
  /** all stops of this transport */
  private Set<Stop> stops;
  
  /** all terminus of this transport */
  private Set<Stop> terminus;

  /**
   * Public default constructor
   * @param name transport name
   * @param type transport type
   */
  public Transport(String name, TransportType type) {
    Validator.notNull(name, "name cannot be null");
    Validator.notNull(type, "type cannot be null");
    this.name = name;
    this.type = type;
    this.names = Collections.singleton(name);
    this.stops = new HashSet<Stop>();
    this.terminus = new HashSet<Stop>();
  }
  
  /**
   * Public constructor
   * @param name transport name
   * @param type transport type
   * @param stops transport stops
   * @param terminus transport terminus
   */
  public Transport(String name, TransportType type, Set<Stop> stops,
      Set<Stop> terminus) {
    this(name, type);
    Validator.notNull(stops, "stops cannot be null");
    Validator.notNull(terminus, "terminus cannot be null");
    this.stops.addAll(stops);
    this.terminus.addAll(terminus);
    this.stops.addAll(terminus);
    names = Collections.singleton(name);
  }
  
  /**
   * Public constructor
   * @param name transport name
   * @param names transport names
   * @param type transport type
   * @param stops transport stops
   * @param terminus transport terminus
   */
  public Transport(String name, Set<String> names, TransportType type,
      Set<Stop> stops, Set<Stop> terminus) {
    this(name, type, stops, terminus);
    Validator.notNull(names, "names cannot be null");
    this.names = new HashSet<String>(names);
    names.add(name);
  }
  
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  
  /**
   * @param name the name to set
   */
  public void setName(String name) {
    Validator.notNull(name, "name cannot be null");
    this.name = name;
  }
  
  /**
   * @return the names
   */
  public Set<String> getNames() {
    return names;
  }
  
  /**
   * @param names the names to set
   */
  public void setNames(Set<String> names) {
    Validator.notNull(names, "names cannot be null");
    this.names.addAll(names);
  }

  /**
   * @return the type
   */
  public TransportType getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(TransportType type) {
    Validator.notNull(type, "type cannot be null");
    this.type = type;
  }
  
  /**
   * @return the stops
   */
  public Set<Stop> getStops() {
    return stops;
  }

  /**
   * @param stops the stops to set
   */
  public void setStops(Set<Stop> stops) {
    Validator.notNull(stops, "stops cannot be null");
    this.stops.addAll(stops);
  }

  /**
   * @return the terminus
   */
  public Set<Stop> getTerminus() {
    return terminus;
  }

  /**
   * @param terminus the terminus to set
   */
  public void setTerminus(Set<Stop> terminus) {
    Validator.notNull(terminus, "terminus cannot be null");
    this.terminus.addAll(terminus);
    this.stops.addAll(terminus);
  }
  
  // ###########################################################################
  // helper methods
  // ###########################################################################
  
  /**
   * add a Stop
   * @param stop stop to add
   */
  public void addStop(Stop stop) {
    Validator.notNull(stop, "stop cannot be null");
    stops.add(stop);
  }
  
  /**
   * add a Stop
   * @param stop stop to add
   */
  public void addTerminus(Stop stop) {
    Validator.notNull(stop, "stop cannot be null");
    terminus.add(stop);
    stops.add(stop);
  }
  
  // ###########################################################################
  // overriden methods
  // ###########################################################################
  
  @Override
  public int hashCode() {
    int hashCode = name.hashCode();
    hashCode += 32 * type.hashCode();
    return hashCode;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Transport)) {
      return false;
    }
    Transport t = (Transport) obj;

    return EqualsHelper.equals(name, t.name)
        && EqualsHelper.equals(names, t.names)
        && EqualsHelper.equals(type, t.type)
        && EqualsHelper.equals(stops, t.stops)
        && EqualsHelper.equals(terminus, t.terminus);
  }
  
  @Override
  public String toString() {
    return "Transport : " + name + ", " + type + " (" + names + ")"
      + ", stops(" + stops.size() + ")"
      + ", terminus(" + terminus.size() + ")";
  }
  
}
