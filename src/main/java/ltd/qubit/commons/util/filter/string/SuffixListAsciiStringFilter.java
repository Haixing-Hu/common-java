////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import java.util.List;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.AsciiTrie;

/**
 * The base for {@link SuffixBlackListAsciiStringFilter} and
 * {@link SuffixWhiteListAsciiStringFilter} classes.
 *
 * @author Haixing Hu
 */
class SuffixListAsciiStringFilter implements StringFilter {

  private AsciiTrie trie;
  private final boolean   matchReturn;
  private final boolean   caseInsensitive;

  protected SuffixListAsciiStringFilter(final boolean matchReturn,
      final boolean caseInsensitive) {
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
        trie = new AsciiTrie(caseInsensitive);
      }
      for (final String str : strList) {
        trie.add(StringUtils.reverse(str));
      }
    }
  }

  public void addToSuffixList(final String str) {
    if (trie == null) {
      trie = new AsciiTrie(caseInsensitive);
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
    return (!matchReturn);
  }
}
