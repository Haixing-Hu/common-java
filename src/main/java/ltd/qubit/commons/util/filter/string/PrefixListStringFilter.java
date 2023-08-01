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

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.text.Trie;

/**
 * The base class for {@link PrefixBlackListStringFilter} and
 * {@link PrefixWhiteListStringFilter} classes.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
class PrefixListStringFilter implements StringFilter {

  protected Trie    trie;
  protected boolean matchReturn;
  protected boolean caseInsensitive;

  protected PrefixListStringFilter(final boolean matchReturn,
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
        trie = new Trie(caseInsensitive);
      }
      for (final String str : strList) {
        trie.add(str);
      }
    }
  }

  public void addToPrefixList(final String str) {
    if (trie == null) {
      trie = new Trie(caseInsensitive);
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
    return (! matchReturn);
  }
}
