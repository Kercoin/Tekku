package opt.git.tekku.model;

import java.util.HashSet;
import java.util.Set;

import opt.git.tekku.util.EqualsHelper;
import opt.git.tekku.util.Validator;


/**
 * Stop class represent a transport stop like a bus stop.
 * @author tym
 */
public class Stop {
  
  /** describe the stop characteristic */
  private Station station;
  
  /** all predecessors of this Stop */
  private Set<Stop> predecessors;
  
  /** all successors of this Stop */
  private Set<Stop> successors;

  /**
   * Public default constructor 
   * @param station station description
   */
  public Stop(Station station) {
    Validator.notNull(station, "Strtion cannot be null");
    this.station = station;
    this.predecessors = new HashSet<Stop>();
    this.successors = new HashSet<Stop>();
  }
  
  /**
   * Public constructor 
   * @param station station description
   * @param predecessors predecessors of this Stop
   * @param successors successors of this Stop
   */
  public Stop(Station station, Set<Stop> predecessors, Set<Stop> successors) {
    this(station);
    Validator.notNull(predecessors, "predecessors cannot be null");
    Validator.notNull(successors, "successors cannot be null");
    // TODO validate predecessors intersction successors is empty !!!
    this.predecessors.addAll(predecessors);
    this.successors.addAll(successors);
  }
  
  /**
   * @return the station
   */
  public Station getStation() {
    return station;
  }
  
  /**
   * @param station the station to set
   */
  public void setStation(Station station) {
    Validator.notNull(station, "Strtion cannot be null");
    this.station = station;
  }
  
  /**
   * @return the predecessors
   */
  public Set<Stop> getPredecessors() {
    return predecessors;
  }

  /**
   * @param predecessors the predecessors to set
   */
  public void setPredecessors(Set<Stop> predecessors) {
    Validator.notNull(predecessors, "predecessors cannot be null");
    this.predecessors = predecessors;
  }

  /**
   * @return the successors
   */
  public Set<Stop> getSuccessors() {
    return successors;
  }
  
  /**
   * @param successors the successors to set
   */
  public void setSuccessors(Set<Stop> successors) {
    Validator.notNull(successors, "successors cannot be null");
    this.successors = successors;
  }
  
  // ###########################################################################
  // helper methods
  // ###########################################################################
  
  public void addPredeccessor(Stop stop) {
    Validator.notNull(stop, "stop cannot be null");
    if (successors.contains(stop)) {
      throw new IllegalArgumentException(stop + " is already a successor !");
    }
    predecessors.add(stop);
  }
  
  public void addSuccessor(Stop stop) {
    Validator.notNull(stop, "stop cannot be null");
    if (predecessors.contains(stop)) {
      throw new IllegalArgumentException(stop + " is already a predecessor !");
    }
    successors.add(stop);
  }
  
  // ###########################################################################
  // overriden methods
  // ###########################################################################
  
  @Override
  public int hashCode() {
    int hashCode = station.hashCode();
    return hashCode;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Stop)) {
      return false;
    }
    Stop stop = (Stop) obj;

    return EqualsHelper.equals(station, stop.station)
        && EqualsHelper.equals(predecessors, stop.predecessors)
        && EqualsHelper.equals(successors, stop.successors);
  }
  
  @Override
  public String toString() {
    return "Stop : " + station
      + ", predessors(" + predecessors.size() + ")"
      +	", successor(" + successors.size() + ")";
  }
  
}
