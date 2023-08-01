////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.filter.character.AcceptSpecifiedCharFilter;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.character.InRangeCharFilter;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.KebabCaseStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.LowerCamelCaseStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperCamelCaseStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperSnakeCaseStrategy;

import static java.util.Objects.requireNonNull;

/**
 * Utility class for converting between various ASCII case formats. Behavior is
 * undefined for non-ASCII input.
 *
 * @author Haixing Hu
 */
public enum CaseFormat {

  /**
   * Hyphenated variable naming convention, e.g., "lower-hyphen".
   */
  LOWER_HYPHEN(new AcceptSpecifiedCharFilter('-'), "-") {
    @Override
    String normalizeWord(final String word) {
      return Ascii.toLowerCase(word);
    }

    @Override
    String convert(final CaseFormat format, final String str) {
      if (format == LOWER_UNDERSCORE) {
        return str.replace('-', '_');
      }
      if (format == UPPER_UNDERSCORE) {
        return Ascii.toUpperCase(str.replace('-', '_'));
      }
      return super.convert(format, str);
    }

    @Override
    public PropertyNamingStrategies.NamingBase toPropertyNamingStrategy() {
      return new KebabCaseStrategy();
    }
  },

  /**
   * C++ variable naming convention, e.g., "lower_underscore".
   */
  LOWER_UNDERSCORE(new AcceptSpecifiedCharFilter('_'), "_") {
    @Override
    String normalizeWord(final String word) {
      return Ascii.toLowerCase(word);
    }

    @Override
    String convert(final CaseFormat format, final String str) {
      if (format == LOWER_HYPHEN) {
        return str.replace('_', '-');
      }
      if (format == UPPER_UNDERSCORE) {
        return Ascii.toUpperCase(str);
      }
      return super.convert(format, str);
    }

    @Override
    public PropertyNamingStrategies.NamingBase toPropertyNamingStrategy() {
      return new SnakeCaseStrategy();
    }
  },

  /**
   * Java variable naming convention, e.g., "lowerCamel".
   */
  LOWER_CAMEL(new InRangeCharFilter('A', 'Z'), "") {
    @Override
    String normalizeWord(final String word) {
      return firstCharOnlyToUpper(word);
    }

    @Override
    String normalizeFirstWord(final String word) {
      return Ascii.toLowerCase(word);
    }

    @Override
    public PropertyNamingStrategies.NamingBase toPropertyNamingStrategy() {
      return new LowerCamelCaseStrategy();
    }
  },

  //  LOWER_CAMEL_DOT(ch -> (ch == '.' || (ch >= 'A' && ch <= 'Z')), "") {
  //    @Override
  //    String normalizeWord(final String word) {
  //      return firstCharOnlyToUpper(word);
  //    }
  //
  //    @Override
  //    String normalizeFirstWord(String word) {
  //      return Ascii.toLowerCase(word);
  //    }
  //  },

  /**
   * Java and C++ class naming convention, e.g., "UpperCamel".
   */
  UPPER_CAMEL(new InRangeCharFilter('A', 'Z'), "") {
    @Override
    String normalizeWord(final String word) {
      return firstCharOnlyToUpper(word);
    }

    @Override
    public PropertyNamingStrategies.NamingBase toPropertyNamingStrategy() {
      return new UpperCamelCaseStrategy();
    }
  },

  /**
   * Java and C++ constant naming convention, e.g., "UPPER_UNDERSCORE".
   */
  UPPER_UNDERSCORE(new AcceptSpecifiedCharFilter('_'), "_") {
    @Override
    String normalizeWord(final String word) {
      return Ascii.toUpperCase(word);
    }

    @Override
    String convert(final CaseFormat format, final String str) {
      if (format == LOWER_HYPHEN) {
        return Ascii.toLowerCase(str.replace('_', '-'));
      }
      if (format == LOWER_UNDERSCORE) {
        return Ascii.toLowerCase(str);
      }
      return super.convert(format, str);
    }

    @Override
    public PropertyNamingStrategies.NamingBase toPropertyNamingStrategy() {
      return new UpperSnakeCaseStrategy();
    }
  };

  private final CharFilter wordBoundary;
  private final String wordSeparator;

  CaseFormat(final CharFilter wordBoundary, final String wordSeparator) {
    this.wordBoundary = wordBoundary;
    this.wordSeparator = wordSeparator;
  }

  public final String getWordSeparator() {
    return wordSeparator;
  }

  /**
   * Converts the specified {@code String str} from this format to the specified
   * {@code format}.
   *
   * <p>A "best effort" approach is taken; if {@code str} does not conform to
   * the assumed format, then the behavior of this method is undefined but we
   * make a reasonable effort at converting anyway.
   *
   * @param format
   *     the specified format to be converted to.
   * @param str
   *     the specified string to be converted.
   * @return
   *     the conversion result.
   */
  public final String to(final CaseFormat format, @Nullable final String str) {
    requireNonNull(format);
    if (str == null || str.isEmpty()) {
      return str;
    } else {
      return (format == this) ? str : convert(format, str);
    }
  }

  /**
   * Enum values can override for performance reasons.
   */
  String convert(final CaseFormat format, final String str) {
    // include some extra space for separators
    final Searcher searcher = new Searcher().forCharsSatisfy(wordBoundary);
    StringBuilder out = null;
    int i = 0;
    int j = -1;
    while ((j = searcher.startFrom(++j).findFirstIndexIn(str)) >= 0) {
      final String word = str.substring(i, j);
      if (i == 0) {
        final String normalizedWord = format.normalizeFirstWord(word);
        out = new StringBuilder(str.length() + 4 * format.wordSeparator.length());
        out.append(normalizedWord);
      } else {
        final String normalizedWord = format.normalizeWord(word);
        out.append(normalizedWord);
      }
      out.append(format.wordSeparator);
      i = j + wordSeparator.length();
    }
    if (i == 0) {
      return format.normalizeFirstWord(str);
    } else {
      final String word = str.substring(i);
      final String normalizedWord = format.normalizeWord(word);
      return out.append(normalizedWord).toString();
    }
  }

  abstract String normalizeWord(String word);

  String normalizeFirstWord(final String word) {
    return normalizeWord(word);
  }

  private static String firstCharOnlyToUpper(final String word) {
    if (word.isEmpty()) {
      return word;
    } else {
      return Ascii.toUpperCase(word.charAt(0)) + Ascii.toLowerCase(word.substring(1));
    }
  }

  public abstract PropertyNamingStrategies.NamingBase toPropertyNamingStrategy();
}
