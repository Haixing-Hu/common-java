////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.Glob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The base for {@link GlobWhiteListStringFilter} and
 * {@link GlobBlackListStringFilter} classes.
 *
 * @author Haixing Hu
 */
class GlobStringFilter implements StringFilter {

  private static final String ERROR_INVALID_PATTERN =
      "Invalid glob pattern '{}': {}";

  protected List<Matcher> matcherList;
  protected boolean       matchReturn;
  protected boolean       caseInsensitive;

  protected GlobStringFilter(final boolean matchReturn,
      final boolean caseInsensitive) {
    matcherList = null;
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

  public void setGlobList(final List<String> globList) {
    if (matcherList != null) {
      matcherList.clear();
    }
    if ((globList != null) && (globList.size() > 0)) {
      if (matcherList == null) {
        matcherList = new LinkedList<>();
      }
      final int flags = (caseInsensitive ? Pattern.CASE_INSENSITIVE : 0);
      for (final String glob : globList) {
        try {
          final String regex = Glob.toRegex(glob);
          final Pattern pattern = Pattern.compile(regex, flags);
          final Matcher matcher = pattern.matcher(StringUtils.EMPTY);
          matcherList.add(matcher);
        } catch (final PatternSyntaxException e) {
          final Logger logger = LoggerFactory.getLogger(GlobStringFilter.class);
          logger.error(ERROR_INVALID_PATTERN, glob, e.toString());
        }
      }
    }
  }

  public boolean addToGlobList(final String glob) {
    if (matcherList == null) {
      matcherList = new LinkedList<>();
    }
    final int flags = (caseInsensitive ? Pattern.CASE_INSENSITIVE : 0);
    try {
      final String regex = Glob.toRegex(glob);
      final Pattern pattern = Pattern.compile(regex, flags);
      final Matcher matcher = pattern.matcher(StringUtils.EMPTY);
      matcherList.add(matcher);
      return true;
    } catch (final PatternSyntaxException e) {
      final Logger logger = LoggerFactory.getLogger(GlobStringFilter.class);
      logger.error(ERROR_INVALID_PATTERN, glob, e.toString());
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
    return (! matchReturn);
  }
}
