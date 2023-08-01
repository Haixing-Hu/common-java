////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.expand;

import javax.annotation.concurrent.Immutable;

/**
 * The {@link ExpansionPolicy} which just fit the new length.
 *
 * @author Haixing Hu
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
