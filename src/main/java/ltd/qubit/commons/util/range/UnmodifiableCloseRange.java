////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

/**
 * The class represents close ranges.
 *
 * @param <T>
 *     the type of elements in the range.
 */
public final class UnmodifiableCloseRange<T> extends CloseRange<T> {

  public UnmodifiableCloseRange() {
  }

  public UnmodifiableCloseRange(final T min, final T max) {
    super(min, max);
  }

  public void setMin(final T min) {
    throw new UnsupportedOperationException("Cannot modify unmodifiable range.");
  }

  public void setMax(final T max) {
    throw new UnsupportedOperationException("Cannot modify unmodifiable range.");
  }
}
