////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.html;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;

import ltd.qubit.commons.lang.CloneableEx;

/**
 * {@link HyperLink} 对象的集合。
 * <p>
 * 此类用于简化超链接集合的序列化。
 * </p>
 *
 * @author 胡海星
 */
public final class HyperLinkSet extends HashSet<HyperLink> implements
        CloneableEx<HyperLinkSet> {

  @Serial
  private static final long serialVersionUID = 4345600463485787291L;

  /**
   * 构造一个空的超链接集合。
   */
  public HyperLinkSet() {
  }

  /**
   * 构造一个包含指定集合中所有元素的超链接集合。
   *
   * @param c
   *     要包含在此集合中的集合。
   */
  public HyperLinkSet(final Collection<HyperLink> c) {
    super(c);
  }

  /**
   * 构造一个具有指定初始容量和加载因子的空超链接集合。
   *
   * @param initialCapacity
   *     初始容量。
   * @param loadFactor
   *     加载因子。
   */
  public HyperLinkSet(final int initialCapacity, final float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  /**
   * 构造一个具有指定初始容量的空超链接集合。
   *
   * @param initialCapacity
   *     初始容量。
   */
  public HyperLinkSet(final int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HyperLinkSet cloneEx() {
    return new HyperLinkSet(this);
  }
}