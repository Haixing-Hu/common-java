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
 * 恰好适应新长度的 {@link ExpansionPolicy}。
 *
 * @author 胡海星
 */
@Immutable
public class JustFitExpansionPolicy extends ExpansionPolicy {

  public static final JustFitExpansionPolicy INSTANCE = new JustFitExpansionPolicy();

  @Override
  public int getNextCapacity(final int oldCapacity, final int newLength) {
    return newLength;
  }

  @Override
  public boolean needShrink(final int length, final int capacity) {
    return length < capacity;
  }

  @Override
  public int getShrinkCapacity(final int length, final int capacity) {
    return length;
  }

}