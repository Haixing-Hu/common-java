////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.text.AsciiTrie;

/**
 * The base class for {@link PrefixBlackListAsciiStringFilter} and
 * {@link PrefixWhiteListAsciiStringFilter} classes.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
class PrefixListAsciiStringFilter implements StringFilter {

  protected AsciiTrie trie;
  protected boolean   matchReturn;
  protected boolean   caseInsensitive;

  protected PrefixListAsciiStringFilter(final boolean matchReturn,
      final boolean caseInsensitive) {
    trie = null;
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

  public void setPrefixList(final List<String> strList) {
    if (trie != null) {
      trie.clear();
    }
    if ((strList != null) && (strList.size() > 0)) {
      if (trie == null) {
        trie = new AsciiTrie(caseInsensitive);
      }
      for (final String str : strList) {
        trie.add(str);
      }
    }
  }

  public void addToPrefixList(final String str) {
    if (trie == null) {
      trie = new AsciiTrie(caseInsensitive);
    }
    trie.add(str);
  }

  @Override
  public boolean accept(final String str) {
    if (trie != null) {
      if (trie.containsPrefixOf(str)) {
        return matchReturn;
      }
    }
    return (!matchReturn);
  }
}