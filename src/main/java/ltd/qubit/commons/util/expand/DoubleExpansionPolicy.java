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
 * 将容量加倍以容纳新长度的 {@link ExpansionPolicy}。
 *
 * @author 胡海星
 */
@Immutable
public class DoubleExpansionPolicy extends ExpansionPolicy {

  public static final DoubleExpansionPolicy INSTANCE = new DoubleExpansionPolicy();

  @Override
  public int getNextCapacity(final int oldCapacity, final int newLength) {
    int newCapacity = (oldCapacity <= 0 ? 1 : oldCapacity);
    while (newCapacity < newLength) {
      newCapacity *= 2;
    }
    return newCapacity;
  }

  @Override
  public boolean needShrink(final int length, final int capacity) {
    return length < capacity / 3;
  }

  @Override
  public int getShrinkCapacity(final int length, final int capacity) {
    return Math.max(length, capacity / 2);
  }

}