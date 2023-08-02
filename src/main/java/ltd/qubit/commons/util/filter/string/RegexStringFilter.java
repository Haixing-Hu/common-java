////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import ltd.qubit.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The base for {@link RegexWhiteListStringFilter} and
 * {@link RegexBlackListStringFilter} classes.
 *
 * @author Haixing Hu
 */
class RegexStringFilter implements StringFilter {

  private static final String ERROR_INVALID_PATTERN =
      "Invalid regular expression pattern {}: {}";

  protected List<Matcher> matcherList;
  protected boolean matchReturn;
  protected boolean caseInsensitive;

  protected RegexStringFilter(final boolean matchReturn,
      final boolean caseInsensitive) {
    this.matcherList = null;
    this.matchReturn = matchReturn;
    this.caseInsensitive = caseInsensitive;
  }

  public boolean isCaseInsensitive() {
    return caseInsensitive;
  }

  public int size() {
    return (matcherList == null ? 0 : matcherList.size());
  }

  public boolean isEmpty() {
    return ((matcherList == null) || matcherList.isEmpty());
  }

  public void clear() {
    if (matcherList != null) {
      matcherList.clear();
    }
  }

  public void setRegexList(final List<String> regexList) {
    if (matcherList != null) {
      matcherList.clear();
    }
    if ((regexList != null) && (regexList.size() > 0)) {
      if (matcherList == null) {
        matcherList = new LinkedList<>();
      }
      final int flags = (caseInsensitive ? Pattern.CASE_INSENSITIVE : 0);
      for (final String regex : regexList) {
        try {
          final Pattern pattern = Pattern.compile(regex, flags);
          final Matcher matcher = pattern.matcher(StringUtils.EMPTY);
          matcherList.add(matcher);
        } catch (final PatternSyntaxException e) {
          final Logger logger = LoggerFactory.getLogger(GlobStringFilter.class);
          logger.error(ERROR_INVALID_PATTERN, regex, e.toString());
        }
      }
    }
  }

  public boolean addToRegexList(final String regex) {
    if (matcherList == null) {
      matcherList = new LinkedList<>();
    }
    final int flags = (caseInsensitive ? Pattern.CASE_INSENSITIVE : 0);
    try {
      final Pattern pattern = Pattern.compile(regex, flags);
      final Matcher matcher = pattern.matcher(StringUtils.EMPTY);
      matcherList.add(matcher);
      return true;
    } catch (final PatternSyntaxException e) {
      final Logger logger = LoggerFactory.getLogger(GlobStringFilter.class);
      logger.error(ERROR_INVALID_PATTERN, regex, e.toString());
      return false;
    }
  }

  @Override
  public boolean accept(final String str) {
    if (matcherList != null) {
      for (final Matcher matcher : matcherList) {
        if (matcher.reset(str).matches()) {
          return matchReturn;
        }
      }
    }
    return (!matchReturn);
  }

}
