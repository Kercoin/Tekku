package hello;

import com.sun.lwuit.util.Resources;

/**
 * Resource listener is used for load resource in background and notify the
 * listener when this operation is done.
 * <p>
 * @author Yacine Touil
 */
public interface ResourceListener {
  
  /**
   * notify that the resource is loaded.
   * @param r resources.
   */
  public void resourceLoaded(Resources r);
}
