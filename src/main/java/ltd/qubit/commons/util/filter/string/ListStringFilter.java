////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import java.util.Collection;

import ltd.qubit.commons.text.Trie;

/**
 * The base class for {@link BlackListStringFilter} and
 * {@link WhiteListStringFilter} classes.
 *
 * @author Haixing Hu
 */
class ListStringFilter implements StringFilter {

  protected Trie    trie;
  protected boolean matchReturn;
  protected boolean caseInsensitive;

  protected ListStringFilter(final boolean matchReturn,
      final boolean caseInsensitive) {
    trie = null;
    this.matchReturn = matchReturn;
    this.caseInsensitive = caseInsensitive;
  }

  public final boolean isCaseInsensitive() {
    return caseInsensitive;
  }

  public final int size() {
    return (trie == null ? 0 : trie.size());
  }

  public final boolean isEmpty() {
    return ((trie == null) || trie.isEmpty());
  }

  public final void clear() {
    if (trie != null) {
      trie.clear();
    }
  }

  public final void setList(final Collection<String> strList) {
    // note that even if strList is empty,
    // we still need to clear the trie.
    if (trie != null) {
      trie.clear();
    }
    if (! strList.isEmpty()) {
      if (trie == null) {
        trie = new Trie(caseInsensitive);
      }
      for (final String str : strList) {
        trie.add(str);
      }
    }
  }

  public final void setList(final String ... strList) {
    // note that even if strList is empty,
    // we still need to clear the trie.
    if (trie != null) {
      trie.clear();
    }
    if (strList.length > 0) {
      if (trie == null) {
        trie = new Trie(caseInsensitive);
      }
      for (final String str : strList) {
        trie.add(str);
      }
    }
  }

  public final void addToList(final String str) {
    if (trie == null) {
      trie = new Trie(caseInsensitive);
    }
    trie.add(str);
  }

  public final void addToList(final Collection<String> strings) {
    if (strings.isEmpty()) {
      return;
    }
    if (trie == null) {
      trie = new Trie(caseInsensitive);
    }
    for (final String str : strings) {
      trie.add(str);
    }
  }

  public final void addToList(final String ... strings) {
    if (strings.length == 0) {
      return;
    }
    if (trie == null) {
      trie = new Trie(caseInsensitive);
    }
    for (final String str : strings) {
      trie.add(str);
    }
  }

  @Override
  public final boolean accept(final String str) {
    if (trie != null) {
      if (trie.contains(str)) {
        return matchReturn;
      }
    }
    return (! matchReturn);
  }

}
