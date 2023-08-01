////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import javax.annotation.Nullable;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The {@link PatternMap} maps a {@link String} to an object according to
 * predefined patterns.
 *
 * @author Haixing Hu
 */
public class PatternMap<VALUE> {

  private final class RegexEntry {
    Matcher matcher;
    VALUE value;
  }

  private Map<String, VALUE> caseSensitiveStringMap;
  private Map<String, VALUE> caseInsensitiveStringMap;
  private TrieMap<VALUE> caseSensitivePrefixMap;
  private TrieMap<VALUE> caseInsensitivePrefixMap;
  private TrieMap<VALUE> caseSensitiveSuffixMap;
  private TrieMap<VALUE> caseInsensitiveSuffixMap;
  private List<RegexEntry> regexEntries;
  private Map<Pattern, VALUE> patternMap;
  private int size;

  public PatternMap() {
    caseSensitiveStringMap = new HashMap<>();
    caseInsensitiveStringMap = new HashMap<>();
    caseSensitivePrefixMap = new TrieMap<>(false);
    caseInsensitivePrefixMap = new TrieMap<>(true);
    caseSensitiveSuffixMap = new TrieMap<>(false);
    caseInsensitiveSuffixMap = new TrieMap<>(true);
    regexEntries = new LinkedList<>();
    patternMap = new HashMap<>();
    size = 0;
  }

  public PatternMap(final Map<Pattern, VALUE> patternMap) {
    this();
    for (final Map.Entry<Pattern, VALUE> entry : patternMap.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return (size == 0);
  }

  public void clear() {
    caseSensitiveStringMap.clear();
    caseInsensitiveStringMap.clear();
    caseSensitivePrefixMap.clear();
    caseInsensitivePrefixMap.clear();
    caseSensitiveSuffixMap.clear();
    caseInsensitiveSuffixMap.clear();
    regexEntries.clear();
    patternMap.clear();
    size = 0;
  }

  public void put(final Pattern pattern, final VALUE value) {
    requireNonNull("value", value);
    final PatternType type = pattern.getType();
    final boolean caseInsensitive = pattern.getIgnoreCase();
    final String expression = pattern.getExpression();
    switch (type) {
      case LITERAL:
        if (caseInsensitive) {
          caseInsensitiveStringMap.put(expression.toLowerCase(), value);
        } else {
          caseSensitiveStringMap.put(expression, value);
        }
        break;
      case PREFIX:
        if (caseInsensitive) {
          caseInsensitivePrefixMap.put(expression, value);
        } else {
          caseSensitivePrefixMap.put(expression, value);
        }
        break;
      case SUFFIX: {
        final String reversedExp = StringUtils.reverse(expression);
        if (caseInsensitive) {
          caseInsensitiveSuffixMap.put(reversedExp, value);
        } else {
          caseSensitiveSuffixMap.put(reversedExp, value);
        }
        break;
      }
      case REGEX: {
        int flags = 0;
        if (caseInsensitive) {
          flags = java.util.regex.Pattern.CASE_INSENSITIVE;
        }
        final RegexEntry entry = new RegexEntry();
        entry.matcher = java.util.regex.Pattern.compile(expression, flags)
                                               .matcher(StringUtils.EMPTY);
        entry.value = value;
        regexEntries.add(entry);
        break;
      }
      case GLOB: {
        int flags = 0;
        if (caseInsensitive) {
          flags = java.util.regex.Pattern.CASE_INSENSITIVE;
        }
        final String regex = Glob.toRegex(expression);
        final RegexEntry entry = new RegexEntry();
        entry.matcher = java.util.regex.Pattern.compile(regex, flags)
                                               .matcher(StringUtils.EMPTY);
        entry.value = value;
        regexEntries.add(entry);
        break;
      }
      default:
        throw new IllegalArgumentException("Unsupported pattern type: " + type);
    }
    patternMap.put(pattern, value);
    ++size;
  }

  public VALUE get(@Nullable final String str) {
    if (str == null) {
      return null;
    }
    // check the string patterns
    VALUE result = caseSensitiveStringMap.get(str);
    if (result != null) {
      return result;
    }
    result = caseInsensitiveStringMap.get(str.toLowerCase());
    if (result != null) {
      return result;
    }
    // check the prefix patterns
    result = caseSensitivePrefixMap.getPrefixOf(str);
    if (result != null) {
      return result;
    }
    result = caseInsensitivePrefixMap.getPrefixOf(str);
    if (result != null) {
      return result;
    }
    // check the suffix patterns
    final String reversedStr = StringUtils.reverse(str);
    result = caseSensitiveSuffixMap.getPrefixOf(reversedStr);
    if (result != null) {
      return result;
    }
    result = caseInsensitiveSuffixMap.getPrefixOf(reversedStr);
    if (result != null) {
      return result;
    }
    // now check the regex and glob patterns
    for (final RegexEntry entry : regexEntries) {
      if (entry.matcher.reset(str).matches()) {
        return entry.value;
      }
    }
    return null;
  }

  public Map<Pattern, VALUE> getPatternMap() {
    return patternMap;
  }

  public void setPatternMap(final Map<Pattern, VALUE> patternMap) {
    final PatternMap<VALUE> other = new PatternMap<>();
    for (final Map.Entry<Pattern, VALUE> entry : patternMap.entrySet()) {
      other.put(entry.getKey(), entry.getValue());
    }
    assignWith(other);
  }

  private void assignWith(final PatternMap<VALUE> other) {
    caseSensitiveStringMap = other.caseSensitiveStringMap;
    caseInsensitiveStringMap = other.caseInsensitiveStringMap;
    caseSensitivePrefixMap = other.caseSensitivePrefixMap;
    caseInsensitivePrefixMap = other.caseInsensitivePrefixMap;
    caseSensitiveSuffixMap = other.caseSensitiveSuffixMap;
    caseInsensitiveSuffixMap = other.caseInsensitiveSuffixMap;
    regexEntries = other.regexEntries;
    patternMap = other.patternMap;
    size = other.size;
  }

  @Override
  public int hashCode() {
    return patternMap.hashCode();
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
    final PatternMap<?> other = (PatternMap<?>) obj;
    return patternMap.equals(other.patternMap);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("patternMap", patternMap)
               .toString();
  }

}
