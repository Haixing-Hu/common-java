////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.Replacer;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A {@link RegexTransformer} transforms strings using a predefined regular
 * expression pattern.
 *
 * <p>The {@link #transform(String)} function will firstly try to match the entire
 * string with the predefined input pattern; if it failed, the function will
 * return the string without changes; if it success, the function will try to
 * replace the "${0}", "${1}", "${2}", ..., etc, in the output pattern with the
 * group 0 (entire matching string), group 1 (the first captured group), group 2
 * (the second captured group), etc.
 *
 * <p>Note that the implementation of this class is not thread safe.
 *
 * @author Haixing Hu
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

  public RegexTransformer() {
    inputPattern = StringUtils.EMPTY;
    outputPattern = StringUtils.EMPTY;
    inputMatcher = null;
  }

  public RegexTransformer(final String inputPattern, final String outputPattern) {
    this.inputPattern = requireNonNull("inputPattern", inputPattern);
    this.outputPattern = requireNonNull("outputPattern", outputPattern);
    inputMatcher = null;
  }

  public String getInputPattern() {
    return inputPattern;
  }

  public void setInputPattern(final String inputPattern) {
    this.inputPattern = requireNonNull("inputPattern", inputPattern);
    inputMatcher = null;
  }

  public String getOutputPattern() {
    return outputPattern;
  }

  public void setOutputPattern(final String outputPattern) {
    this.outputPattern = requireNonNull("outputPattern", outputPattern);
  }

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
            .forSubstring(pattern)
            .withString(replacement)
            .ignoreCase(false)
            .replace(substituted);
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
  public RegexTransformer clone() {
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
