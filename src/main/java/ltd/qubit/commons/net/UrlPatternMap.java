////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.lang.reflect.Array;

import ltd.qubit.commons.text.Pattern;
import ltd.qubit.commons.text.PatternMap;

/**
 * The {@link PatternMap} maps a {@link Url} to an object according to
 * predefined patterns.
 *
 * @author Haixing Hu
 */
public class UrlPatternMap<VALUE> {

  private final PatternMap<VALUE>[] maps;
  private int size;

  @SuppressWarnings("unchecked")
  public UrlPatternMap() {
    final UrlPart[] parts = UrlPart.values();
    final int n = parts.length;
    maps = (PatternMap<VALUE>[]) Array.newInstance(PatternMap.class, n);
    size = 0;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return (size == 0);
  }

  public void clear() {
    for (int i = 0; i < maps.length; ++i) {
      if (maps[i] != null) {
        maps[i].clear();
      }
    }
    size = 0;
  }

  public void put(final UrlPattern urlPattern, final VALUE value) {
    final UrlPart part = urlPattern.getPart();
    final int index = part.ordinal();
    if (maps[index] == null) {
      maps[index] = new PatternMap<>();
    }
    final Pattern pattern = urlPattern.getPattern();
    maps[index].put(pattern, value);
  }

  public VALUE get(final Url url) {
    final UrlPart[] parts = UrlPart.values();
    assert (parts.length == maps.length);
    for (int i = 0; i < parts.length; ++i) {
      if (maps[i] != null) {
        final String str = url.get(parts[i]);
        final VALUE result = maps[i].get(str);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }
}
