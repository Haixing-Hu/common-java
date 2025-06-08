////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.Replacer;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link RegexTransformer}使用预定义的正则表达式模式转换字符串。
 * <p>
 * {@link #transform(String)}函数将尝试将输入字符串中所有匹配预定义输入模式的子串替换掉；
 * 对于每个匹配的子串,该函数将尝试将输出模式中的"${0}","${1}","${2}"等替换为
 * 组0(整个匹配字符串),组1(第一个捕获的组),组2(第二个捕获的组)等。
 * <p>
 * 请注意,此类的实现不是线程安全的。
 *
 * @author 胡海星
 */
public class RegexTransformer extends AbstractStringTransformer {

  private static final Logger LOGGER = LoggerFactory.getLogger(RegexTransformer.class);

  public static final int MAX_CAPTURE_GROUPS = 100;

  private static final String[] CAPTURE_SUBSTITUTION_PATTERNS =
      new String[MAX_CAPTURE_GROUPS + 1];

  static {
    for (int i = 0; i <= MAX_CAPTURE_GROUPS; ++i) {
      CAPTURE_SUBSTITUTION_PATTERNS[i] = "${" + i + "}";
    }
  }

  private String inputPattern;

  private String outputPattern;

  private transient Matcher inputMatcher;

  /**
   * 构造一个{@link RegexTransformer}。
   */
  public RegexTransformer() {
    inputPattern = StringUtils.EMPTY;
    outputPattern = StringUtils.EMPTY;
    inputMatcher = null;
  }

  /**
   * 构造一个{@link RegexTransformer}。
   *
   * @param inputPattern
   *     用于匹配的正则表达式。
   * @param outputPattern
   *     用于替换的正则表达式。
   */
  public RegexTransformer(final String inputPattern, final String outputPattern) {
    this.inputPattern = requireNonNull("inputPattern", inputPattern);
    this.outputPattern = requireNonNull("outputPattern", outputPattern);
    inputMatcher = null;
  }

  /**
   * 获取用于匹配的正则表达式。
   *
   * @return
   *     用于匹配的正则表达式。
   */
  public String getInputPattern() {
    return inputPattern;
  }

  /**
   * 设置用于匹配的正则表达式。
   *
   * @param inputPattern
   *     新的用于匹配的正则表达式。
   */
  public void setInputPattern(final String inputPattern) {
    this.inputPattern = requireNonNull("inputPattern", inputPattern);
    inputMatcher = null;
  }

  /**
   * 获取用于替换的正则表达式。
   *
   * @return
   *     用于替换的正则表达式。
   */
  public String getOutputPattern() {
    return outputPattern;
  }

  /**
   * 设置用于替换的正则表达式。
   *
   * @param outputPattern
   *     新的用于替换的正则表达式。
   */
  public void setOutputPattern(final String outputPattern) {
    this.outputPattern = requireNonNull("outputPattern", outputPattern);
  }

  /**
   * 使用此转换器转换指定的字符串。
   *
   * @param str
   *     要转换的字符串，可以为{@code null}。
   * @return
   *     转换后的字符串。
   */
  @Override
  public String transform(@Nullable final String str) {
    if ((str == null) || (str.length() == 0)) {
      return str;
    }
    if (inputPattern.length() == 0) {
      LOGGER.warn("The input pattern is not set; "
          + "string will not be changed: {}", str);
      return str;
    }
    if (inputMatcher == null) {
      inputMatcher = Pattern.compile(inputPattern).matcher(str);
    } else {
      inputMatcher.reset(str);
    }
    final Replacer replacer = new Replacer();
    final StringBuilder builder = new StringBuilder();
    final int len = str.length();
    int i = 0;
    while (i < len) {
      if (!inputMatcher.find(i)) {
        builder.append(str, i, len);
        break;
      }
      // now found a match
      final int j = inputMatcher.start();
      final int k = inputMatcher.end();
      // append the substring between the current current and the starting
      // of the matching
      if (i < j) {
        builder.append(str, i, j);
      }
      // now do the substitution
      int n = inputMatcher.groupCount();
      if (n > MAX_CAPTURE_GROUPS) {
        LOGGER.warn("Too many ({}) captured groups, only {} was supported: {}",
            n, MAX_CAPTURE_GROUPS, inputPattern);
        n = MAX_CAPTURE_GROUPS;
      }
      String substituted = outputPattern;
      for (int c = 1; c <= n; ++c) {
        final String pattern = CAPTURE_SUBSTITUTION_PATTERNS[c];
        final String replacement = inputMatcher.group(c);
        substituted = replacer
            .searchForSubstring(pattern)
            .replaceWithString(replacement)
            .ignoreCase(false)
            .applyTo(substituted);
      }
      // now append the substituted string
      builder.append(substituted);
      // find the next match
      i = k;
    }
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int multiplier = 17;
    int code = 11;
    code = Hash.combine(code, multiplier, inputPattern);
    code = Hash.combine(code, multiplier, outputPattern);
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
    final RegexTransformer other = (RegexTransformer) obj;
    return Equality.equals(inputPattern, other.inputPattern)
        && Equality.equals(outputPattern, other.outputPattern);
  }

  @Override
  public RegexTransformer cloneEx() {
    return new RegexTransformer(inputPattern, outputPattern);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("inputPattern", inputPattern)
        .append("outputPattern", outputPattern)
        .toString();
  }
}