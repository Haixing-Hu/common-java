////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import java.util.List;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.Trie;

/**
 * The base for {@link SuffixBlackListStringFilter} and
 * {@link SuffixWhiteListStringFilter} classes.
 *
 * @author Haixing Hu
 */
class SuffixListStringFilter implements StringFilter {

  private Trie    trie;
  private final boolean matchReturn;
  private final boolean caseInsensitive;

  protected SuffixListStringFilter(final boolean matchReturn, final boolean caseInsensitive) {
    this.trie = null;
    this.matchReturn = matchReturn;
    this.caseInsensitive = caseInsensitive;
  }

  public boolean isCaseInsensitive() {
    return caseInsensitive;
  }

  public int size() {
    return (trie == null ? 0 : trie.size());
  }

  public boolean isEmpty() {
    return ((trie == null) || trie.isEmpty());
  }

  public void clear() {
    if (trie != null) {
      trie.clear();
    }
  }

  public void setSuffixList(final List<String> strList) {
    if (trie != null) {
      trie.clear();
    }
    if ((strList != null) && (strList.size() > 0)) {
      if (trie == null) {
        trie = new Trie(caseInsensitive);
      }
      for (final String str : strList) {
        trie.add(StringUtils.reverse(str));
      }
    }
  }

  public void addToSuffixList(final String str) {
    if (trie == null) {
      trie = new Trie(caseInsensitive);
    }
    trie.add(StringUtils.reverse(str));
  }

  @Override
  public boolean accept(final String str) {
    if (trie != null) {
      if (trie.containsPrefixOf(StringUtils.reverse(str))) {
        return matchReturn;
      }
    }
    return (! matchReturn);
  }
}
