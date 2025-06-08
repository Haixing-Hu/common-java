////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.file;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.Glob;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A {@link GlobFileFilter} filters files using the supplied glob patterns.
 *
 * <p>This filter selects files and directories based on one or more glob
 * patterns. Testing is case-sensitive by default, but this can be configured.
 *
 * <p>The following character sequences have special meaning within a glob
 * pattern:
 *
 * <ul>
 * <li>? matches any one character</li>
 * <li>* matches any number of characters</li>
 * <li>{!glob} Matches anything that does not match glob</li>
 * <li>{a,b,c} matches any one of a, b or c</li>
 * <li>[abc] matches any character in the set a, b or c</li>
 * <li>[^abc] matches any character not in the set a, b or c</li>
 * <li>[a-z] matches any character in the range a to z, inclusive. A leading or
 * trailing dash will be interpreted literally</li>
 * </ul>
 *
 * <p>For example:
 *
 * <pre>
 * File dir = new File(&quot;.&quot;);
 * FileFilter fileFilter = new GlobFileFilter(&quot;*test*.java&tilde;*&tilde;&quot;);
 * File[] files = dir.listFiles(fileFilter);
 * for (int i = 0; i &lt; files.length; i++) {
 *   System.out.println(files[i]);
 * }
 * </pre>
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class GlobFileFilter implements FileFilter {

  public static final boolean DEFAULT_CASE_SENSITIVE = false;

  List<Glob> globs;

  public GlobFileFilter() {
    globs = null;
  }

  private int getGlobFlags(final boolean caseSensitive) {
    int options = 0;
    if (! caseSensitive) {
      options = Pattern.CASE_INSENSITIVE;
    }
    return options;
  }

  public void addPattern(final boolean caseSensitive, final String pattern) {
    final int options = getGlobFlags(caseSensitive);
    if (globs == null) {
      globs = new LinkedList<>();
    }
    final Glob glob = new Glob(pattern, options);
    globs.add(glob);
  }

  public void addPatterns(final boolean caseSensitive, final String ... patterns) {
    final int options = getGlobFlags(caseSensitive);
    if (globs == null) {
      globs = new LinkedList<>();
    }
    for (final String pattern : patterns) {
      final Glob glob = new Glob(pattern, options);
      globs.add(glob);
    }
  }

  public void addPatterns(final boolean caseSensitive, final Iterable<String> patterns) {
    final int options = getGlobFlags(caseSensitive);
    if (globs == null) {
      globs = new LinkedList<>();
    }
    for (final String pattern : patterns) {
      final Glob glob = new Glob(pattern, options);
      globs.add(glob);
    }
  }

  public boolean isEmpty() {
    return (globs == null) || globs.isEmpty();
  }

  public int size() {
    return (globs == null ? 0 : globs.size());
  }

  public void clear() {
    if (globs != null) {
      globs.clear();
    }
  }

  @Override
  public boolean accept(final File file) {
    if (globs != null) {
      final String name = file.getName();
      for (final Glob glob : globs) {
        if (glob.matches(name)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean accept(final File dir, final String name) {
    if (globs != null) {
      for (final Glob glob : globs) {
        if (glob.matches(name)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final GlobFileFilter other = (GlobFileFilter) o;
    return Equality.equals(globs, other.globs);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, globs);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("globs", globs)
        .toString();
  }
}