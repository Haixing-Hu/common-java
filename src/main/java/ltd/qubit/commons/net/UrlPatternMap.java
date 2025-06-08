////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * {@link PatternMap} 根据预定义的模式将 {@link Url} 映射到一个对象。
 *
 * @author 胡海星
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

  /**
   * 获取此映射中的模式数。
   *
   * @return 此映射中的模式数。
   */
  public int size() {
    return size;
  }

  /**
   * 测试此映射是否为空。
   *
   * @return 如果此映射为空，则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * 从此映射中删除所有模式。
   */
  public void clear() {
    for (int i = 0; i < maps.length; ++i) {
      if (maps[i] != null) {
        maps[i].clear();
      }
    }
    size = 0;
  }

  /**
   * 在此映射中放置一个 URL 模式。
   *
   * @param urlPattern
   *     要放置的 URL 模式。
   * @param value
   *     与 URL 模式关联的值。
   */
  public void put(final UrlPattern urlPattern, final VALUE value) {
    final UrlPart part = urlPattern.getPart();
    final int index = part.ordinal();
    if (maps[index] == null) {
      maps[index] = new PatternMap<>();
    }
    final Pattern pattern = urlPattern.getPattern();
    maps[index].put(pattern, value);
    size++;
  }

  /**
   * 获取与指定 URL 匹配的模式所关联的值。
   *
   * @param url
   *     指定的 URL。
   * @return
   *     与指定 URL 匹配的模式所关联的值；如果没有模式匹配，则返回 {@code null}。
   */
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