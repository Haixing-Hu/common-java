////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.expand;

import javax.annotation.concurrent.Immutable;

/**
 * 占用内存较少的 {@link ExpansionPolicy}。
 *
 * <p>其增长模式为： 0, 4, 8, 16, 25, 35, 46, 58, 72, 88, ...
 *
 * @author 胡海星
 */
@Immutable
public class MemorySavingExpansionPolicy extends ExpansionPolicy {

  public static final MemorySavingExpansionPolicy INSTANCE = new MemorySavingExpansionPolicy();

  //  stop checkstyle: MagicNumberCheck
  @Override
  public int getNextCapacity(final int oldCapacity, final int newLength) {
    // The growth pattern is:  0, 4, 8, 16, 25, 35, 46, 58, 72, 88, ...
    final int newCapacity = (newLength >> 3) + (newLength < 9 ? 3 : 6) + newLength;
    return newCapacity;
  }

  @Override
  public boolean needShrink(final int length, final int capacity) {
    return length < capacity / 3;
  }

  @Override
  public int getShrinkCapacity(final int length, final int capacity) {
    final int cap1 = getNextCapacity(capacity, length);
    final int cap2 = getNextCapacity(capacity, capacity / 2);
    return Math.max(cap1, cap2);
  }
  //  resume checkstyle: MagicNumberCheck
}