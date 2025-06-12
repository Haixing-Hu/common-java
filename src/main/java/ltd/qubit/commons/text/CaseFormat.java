////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.KebabCaseStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.LowerCamelCaseStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperCamelCaseStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperSnakeCaseStrategy;

import ltd.qubit.commons.util.filter.character.AcceptSpecifiedCharFilter;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.character.InRangeCharFilter;

import static java.util.Objects.requireNonNull;

/**
 * 用于在各种ASCII大小写格式之间转换的实用类。对于非ASCII输入，行为是未定义的。
 *
 * @author 胡海星
 */
public enum CaseFormat {

  /**
   * 连字符变量命名约定，例如"lower-hyphen"。
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
   * C++变量命名约定，例如"lower_underscore"。
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
   * Java变量命名约定，例如"lowerCamel"。
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
   * Java和C++类命名约定，例如"UpperCamel"。
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
   * Java和C++常量命名约定，例如"UPPER_UNDERSCORE"。
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

  /**
   * 单词边界过滤器。
   */
  private final CharFilter wordBoundary;

  /**
   * 单词分隔符。
   */
  private final String wordSeparator;

  /**
   * 构造函数。
   *
   * @param wordBoundary 单词边界过滤器
   * @param wordSeparator 单词分隔符
   */
  CaseFormat(final CharFilter wordBoundary, final String wordSeparator) {
    this.wordBoundary = wordBoundary;
    this.wordSeparator = wordSeparator;
  }

  /**
   * 获取单词分隔符。
   *
   * @return 单词分隔符字符串
   */
  public final String getWordSeparator() {
    return wordSeparator;
  }

  /**
   * 将指定的{@code String str}从此格式转换为指定的{@code format}。
   *
   * <p>采用"尽力而为"的方法；如果{@code str}不符合假定的格式，
   * 则此方法的行为是未定义的，但我们会合理地尝试进行转换。
   *
   * @param format
   *     要转换到的指定格式。
   * @param str
   *     要转换的指定字符串。
   * @return
   *     转换结果。
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
   * 枚举值可以为了性能原因重写此方法。
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

  /**
   * 规范化单词的格式。
   *
   * @param word 要规范化的单词
   * @return 规范化后的单词
   */
  abstract String normalizeWord(String word);

  /**
   * 规范化第一个单词的格式。
   *
   * @param word 要规范化的第一个单词
   * @return 规范化后的第一个单词
   */
  String normalizeFirstWord(final String word) {
    return normalizeWord(word);
  }

  /**
   * 将第一个字符转换为大写，其余字符转换为小写。
   *
   * @param word 要转换的单词
   * @return 转换后的单词
   */
  private static String firstCharOnlyToUpper(final String word) {
    if (word.isEmpty()) {
      return word;
    } else {
      return Ascii.toUpperCase(word.charAt(0)) + Ascii.toLowerCase(word.substring(1));
    }
  }

  /**
   * 转换为Jackson属性命名策略。
   *
   * @return 对应的Jackson属性命名策略
   */
  public abstract PropertyNamingStrategies.NamingBase toPropertyNamingStrategy();
}