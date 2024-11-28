////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * This class wraps the class object and could be used as the key of a hash map.
 *
 * <p>There is a lot of problem if we use the {@link Class} object as the key of
 * a hash map. For example, if your code is running in a web container and you
 * are in the habit of doing hot deployment of webapps, a retained reference to
 * a single class object can cause a significant permgen memory leak.
 *
 * <p>In a nutshell, the problem is that each class contains a reference to its
 * classloader, and each classloader contains references to every class that it
 * has loaded. So if one class is reachable, all of them are.</p>
 *
 * <p>If one of the classes that you are using as a key is reloaded then:</p>
 * <ul>
 * <li>The old and new versions of the class will not be equal.</li>
 * <li>Looking up the new class will initially give a "miss".</li>
 * <li>After you have added the new class to the map, you will now have two
 * different map entries for the different versions of the class.</li>
 * <li>This applies even if there is no code difference between the two versions
 * of the class. They will be different simply because they were loaded by
 * different classloaders.</li>
 * </ul>
 *
 * @author Haixing Hu
 */
public class ClassKey implements Comparable<ClassKey> {

  private final String className;

  public ClassKey(final Class<?> cls) {
    this.className = cls.getName();
  }

  public String getClassName() {
    return className;
  }

  public Class<?> getActualClass() {
    try {
      return Class.forName(className);
    } catch (final ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ClassKey other = (ClassKey) o;
    return Equality.equals(className, other.className);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, className);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("className", className)
        .toString();
  }

  @Override
  public int compareTo(final ClassKey other) {
    return className.compareTo(other.className);
  }
}
