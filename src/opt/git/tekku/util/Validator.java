package opt.git.tekku.util;


/**
 * Helper class for validation
 * @author tym
 * TODO : replace with apache commun library
 */
public class Validator {
  
  public static void notNull(Object obj) {
    if (obj == null) {
      throw new IllegalArgumentException("Parameter cannot be null !");
    }
  }

  public static void notNull(Object obj, String message) {
    if (obj == null) {
      throw new IllegalArgumentException(message);
    }
  }
}
