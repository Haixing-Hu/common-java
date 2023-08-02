////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.concurrent.Immutable;

/**
 * An {@link UnmodifiableParseOptions} is a {@link ParseOptions} which can't be
 * modified since created.
 *
 * @author Haixing Hu
 */
@Immutable
public final class UnmodifiableParseOptions extends ParseOptions {

  private static final long serialVersionUID = 1029792624986278503L;

  public UnmodifiableParseOptions() {
  }

  public UnmodifiableParseOptions(final int flags) {
    super(flags);
  }

  public UnmodifiableParseOptions(final int flags, final int defaultRadix) {
    super(flags, defaultRadix);
  }

  public UnmodifiableParseOptions(final int flags, final int defaultRadix,
      final int maxDigits) {
    super(flags, defaultRadix, maxDigits);
  }

  public UnmodifiableParseOptions(final ParseOptions options) {
    super(options.flags, options.defaultRadix, options.maxDigits);
  }

  @Override
  public void setFlags(final int flags) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addFlags(final int flags) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addFlags(final int flags, final int mask) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setDefaultRadix(final int defaultRadix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setMaxDigits(final int maxDigits) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void resetMaxDigits() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setBoolAlpha(final boolean value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setGrouping(final boolean value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setKeepBlank(final boolean value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setBinary(final boolean value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setOctal(final boolean value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setDecimal(final boolean value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setHex(final boolean value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clearRadixOptions() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setFixPoint(final boolean value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setScientific(final boolean value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setGeneral(final boolean value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clearRealOptions() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void reset() {
    throw new UnsupportedOperationException();
  }

}
