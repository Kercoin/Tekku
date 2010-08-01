package opt.git.tekku.util;

import java.util.Set;


/**
 * Helper class for equals
 * @author tym
 * TODO : replace with apache commun library
 */
public class EqualsHelper {
  
  public static boolean equals(Object obj1, Object obj2) {
    if (obj1 == null && obj2 == null) {
      return true;
    }
    if (obj1 == null || obj2 == null) {
      return false;
    }
    return obj1.equals(obj2);
  }

  public static boolean equals(Set<Object> set1, Set<Object> set2) {
    if (set1 == null && set2 == null) {
      return true;
    }
    if (set1 == null || set2 == null) {
      return false;
    }
    return set1.equals(set2);
  }

}
