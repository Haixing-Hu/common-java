////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The enumeration of pattern types.
 *
 * @author Haixing Hu
 */
public enum PatternType {
  /**
   * Indicate that the pattern represents a full string literal, therefore a string
   * matches the pattern if and only if the string equals the pattern.
   */
  LITERAL("literal"),

  /**
   * Indicate that the pattern represents a prefix, therefore a string matches
   * the pattern if and only if the string has a prefix equals the pattern.
   */
  PREFIX("prefix"),

  /**
   * Indicate that the pattern represents a suffix, therefore a string matches
   * the pattern if and only if the string has a suffix equals the pattern.
   */
  SUFFIX("suffix"),

  /**
   * Indicate that the pattern represents a substring, therefore a string matches
   * the pattern if and only if the string has a substring equals the pattern.
   */
  SUBSTRING("substring"),

  /**
   * Indicate that the pattern represents a regular expression pattern,
   * therefore a string matches the pattern if and only if the string matches
   * the regular expression represented by the pattern.
   */
  REGEX("regex"),

  /**
   * Indicate that the pattern represents a glob pattern, therefore a string
   * matches the pattern if and only if the string matches the glob represented
   * by the pattern.
   */
  GLOB("glob");

  private static final Map<String, PatternType> NAME_MAP;
  static {
    NAME_MAP = new HashMap<>(5);
    NAME_MAP.put(LITERAL.name, LITERAL);
    NAME_MAP.put(PREFIX.name, PREFIX);
    NAME_MAP.put(SUFFIX.name, SUFFIX);
    NAME_MAP.put(SUBSTRING.name, SUBSTRING);
    NAME_MAP.put(REGEX.name, REGEX);
    NAME_MAP.put(GLOB.name, GLOB);
  }

  public static PatternType forName(final String name) {
    return NAME_MAP.get(name);
  }

  private final String name;

  PatternType(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String toRegex(final String pattern) {
    switch (this) {
      case LITERAL:
        return Pattern.quote(pattern);
      case PREFIX:
        return "^" + Pattern.quote(pattern);
      case SUFFIX:
        return Pattern.quote(pattern) + "$";
      case SUBSTRING:
        return ".*" + Pattern.quote(pattern) + ".*";
      case REGEX:
        return pattern;
      case GLOB:
        return Glob.toRegex(pattern);
      default:
        throw new IllegalStateException("Unknown pattern type.");
    }
  }

  @Override
  public String toString() {
    return name;
  }
}
