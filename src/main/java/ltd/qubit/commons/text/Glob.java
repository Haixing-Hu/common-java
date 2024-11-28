////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.Serial;
import java.io.Serializable;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.error.TypeMismatchException;
import ltd.qubit.commons.io.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The class of the filename glob patterns.
 *
 * <p>The glob pattern syntax obey the rule at <a href="http://www.jedit.org/users-guide/globs.html">Globs</a>.
 *
 * <p>The following character sequences have special meaning within a glob pattern:
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
 * @author Haixing Hu
 */
@NotThreadSafe
public class Glob implements Serializable, CloneableEx<Glob>, Assignable<Glob> {

  @Serial
  private static final long   serialVersionUID = -1303433525002426637L;

  public static final int DEFAULT_FLAGS        = Pattern.CASE_INSENSITIVE;
  public static final String  ROOT_NODE        = "glob";
  public static final String  FLAGS_ATTRIBUTE  = "flags";

  private static final Object NEGATIVE         = new Object();
  private static final Object GROUP            = new Object();

  static {
    BinarySerialization.register(Glob.class, GlobBinarySerializer.INSTANCE);
    XmlSerialization.register(Glob.class, GlobXmlSerializer.INSTANCE);
  }

  /**
   * Tests whether the specified string is a glob pattern.
   *
   * @param str
   *     the string to be tested.
   * @return
   *     {@code true} if the specified string is a glob pattern; {@code false}
   *     otherwise.
   */
  public static boolean isGlob(final String str) {
    if (str == null) {
      return false;
    }
    final int len = str.length();
    if (len == 0) {
      return false;
    }
    for (int i = 0; i < len; ++i) {
      final char ch = str.charAt(i);
      switch (ch) {
        case '?':
        case '*':
        case '{':
        case '}':
        case '[':
        case ']':
          return true;
        case '\\':
          if ((i + 1) < len) {
            ++i;
          }
          break;
        default:
          break;
      }
    }
    return false;
  }

  /**
   * Converts a Unix-style filename glob to a regular expression.
   *
   * <p>Since we use java.util.regex patterns to implement globs, this means that
   * in addition to the above, a number of “character class metacharacters” may
   * be used. Keep in mind, their usefulness is limited since the regex
   * quantifier metacharacters (asterisk, questionmark, and curly brackets) are
   * redefined to mean something else in filename glob language, and the regex
   * quantifiers are not available in glob language.
   *
   * <ul>
   * <li>\w matches any alphanumeric character or underscore</li>
   * <li>\s matches a space or horizontal tab</li>
   * <li>\S matches a printable non-whitespace.</li>
   * <li>\d matches a decimal digit</li>
   * </ul>
   *
   *
   * <p>Here are some examples of glob patterns:
   *
   * <ul>
   * <li>"*" - all files.</li>
   * <li>"*.java" - all files whose names end with ".java".</li>
   * <li>"*.[ch]" - all files whose names end with either ".c" or ".h".</li>
   * <li>"*.{c,cpp,h,hpp,cxx,hxx}" - all C or C++ files.</li>
   * <li>"[^#]*" - all files whose names do not start with "#".</li>
   * </ul>
   *
   *
   * <p>This function makes the following conversion:
   *
   * <ul>
   * <li>"?" becomes "."</li>
   * <li>"*" becomes ".*"</li>
   * <li>"{aa,bb}" becomes "(aa|bb)"</li>
   * <li>all other special meta-characters in regular expressions are escaped.</li>
   * </ul>
   *
   * @param glob
   *          the Unix-style filename glob pattern
   * @return the string representation of the regular expression converted from
   *        this glob.
   */
  public static String toRegex(final String glob) {
    final Stack<Object> state = new Stack<>();
    boolean backSlash = false;
    final int globLen = glob.length();
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < globLen; ++i) {
      final char ch = glob.charAt(i);
      if (backSlash) {
        builder.append('\\').append(ch);
        backSlash = false;
        continue;
      }
      switch (ch) {
        case '\\':
          backSlash = true;
          break;
        case '?':
          builder.append('.');
          break;
        case '.':
        case '+':
        case '(':
        case ')':
          builder.append('\\').append(ch);
          break;
        case '*':
          builder.append('.').append('*');
          break;
        case '|':
          if (backSlash) {
            builder.append('\\').append('|');
          } else {
            builder.append('|');
          }
          break;
        case '{': {
          builder.append('(');
          final int next = i + 1;
          if ((next != glob.length()) && (glob.charAt(next) == '!')) {
            builder.append('?');
            state.push(NEGATIVE);
          } else {
            state.push(GROUP);
          }
          break;
        }
        case ',':
          if (! state.isEmpty() && (state.peek() == GROUP)) {
            builder.append('|');
          } else {
            builder.append(',');
          }
          break;
        case '}':
          if (! state.isEmpty()) {
            builder.append(")");
            if (state.pop() == NEGATIVE) {
              builder.append('.').append('*');
            }
          } else {
            builder.append('}');
          }
          break;
        default:
          builder.append(ch);
      }
    }
    return builder.toString();
  }

  protected String pattern;
  protected int flags;
  protected transient Matcher matcher;

  public Glob() {
    pattern = StringUtils.EMPTY;
    flags = DEFAULT_FLAGS;
    matcher = null;
  }

  /**
   * Construct a case-insensitive glob pattern object.
   *
   * @param pattern
   *          the string of glob pattern.
   */
  public Glob(final String pattern) {
    this.pattern = requireNonNull("pattern", pattern);
    flags = DEFAULT_FLAGS;
    matcher = null;
  }

  /**
   * Construct a glob pattern object.
   *
   * @param pattern
   *          the string of glob pattern.
   * @param flags
   *          the match flags, a bit mask that may include
   *          Pattern.CASE_INSENSITIVE, Pattern.MULTILINE, Pattern.DOTALL,
   *          Pattern.UNICODE_CASE, Pattern.CANON_EQ, Pattern.UNIX_LINES,
   *          Pattern.LITERAL and Pattern.COMMENTS. Where Pattern is the
   *          java.util.regex.Pattern class.
   */
  public Glob(final String pattern, final int flags) {
    this.pattern = requireNonNull("pattern", pattern);
    this.flags = flags;
    matcher = null;
  }

  public int getFlags() {
    return flags;
  }

  public void setFlags(final int flags) {
    this.flags = flags;
    matcher = null;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(final String pattern) {
    this.pattern = requireNonNull("pattern", pattern);
    matcher = null;
  }

  public void set(final String pattern, final int flags) {
    this.pattern = requireNonNull("pattern", pattern);
    this.flags = flags;
    matcher = null;
  }

  public boolean matches(final String str) {
    if (str == null) {
      return false;
    } else if (pattern.length() == 0) {
      return false;
    }
    if (matcher == null) {
      final String regex = toRegex(pattern);
      matcher = Pattern.compile(regex, flags)
                       .matcher(StringUtils.EMPTY);
    }
    return matcher.reset(str).matches();
  }

  @Override
  public void assign(final Glob that) {
    if (this != that) {
      final Class<?> thisClass = getClass();
      final Class<?> thatClass = that.getClass();
      if (thisClass != thatClass) {
        throw new TypeMismatchException(thisClass, thatClass);
      }
      flags = that.flags;
      pattern = that.pattern;
      if (that.matcher == null) {
        matcher = null;
      } else {
        matcher = that.matcher.pattern().matcher(StringUtils.EMPTY);
      }
    }
  }

  @Override
  public Glob cloneEx() {
    final Glob result = new Glob();
    result.flags = flags;
    result.pattern = pattern;
    result.matcher = null;
    return result;
  }

  @Override
  public int hashCode() {
    int code = 77;
    final int multiplier = 131;
    code = Hash.combine(code, multiplier, flags);
    code = Hash.combine(code, multiplier, pattern);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Glob other = (Glob) obj;
    return (flags == other.flags)
        && Equality.equals(pattern, other.pattern);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("flags", flags)
               .append("pattern", pattern)
               .toString();
  }
}
