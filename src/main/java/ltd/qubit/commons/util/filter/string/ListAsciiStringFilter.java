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

import ltd.qubit.commons.text.AsciiTrie;

/**
 * The base class for {@link BlackListAsciiStringFilter} and
 * {@link WhiteListAsciiStringFilter} classes.
 *
 * @author Haixing Hu
 */
class ListAsciiStringFilter implements StringFilter {

  protected AsciiTrie trie;
  protected boolean   matchReturn;
  protected boolean   caseInsensitive;

  protected ListAsciiStringFilter(final boolean matchReturn,
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

  public void setList(final List<String> strList) {
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

  public void addToList(final String str) {
    if (trie == null) {
      trie = new AsciiTrie(caseInsensitive);
    }
    trie.add(str);
  }

  @Override
  public boolean accept(final String str) {
    if (trie != null) {
      if (trie.contains(str)) {
        return matchReturn;
      }
    }
    return (! matchReturn);
  }

}
