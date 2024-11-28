////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.text.ParsePosition;

import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A simple structure used to pass the argument between parsing functions.
 *
 * <p>This class extends the {@link ParsePosition}, adding the error code to
 * indicate the error during the parsing.
 *
 * @author Haixing Hu
 */
public final class ParsingPosition extends ParsePosition {

  private int errorCode;

  public ParsingPosition() {
    super(0);
    errorCode = ErrorCode.NONE;
  }

  public ParsingPosition(final int index) {
    super(index);
    errorCode = ErrorCode.NONE;
  }

  public void increase() {
    setIndex(getIndex() + 1);
  }

  public void increase(final int amount) {
    setIndex(getIndex() + amount);
  }

  public void decrease() {
    setIndex(getIndex() - 1);
  }

  public void decrease(final int amount) {
    setIndex(getIndex() - amount);
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(final int errorCode) {
    this.errorCode = errorCode;
  }

  public boolean success() {
    return errorCode == ErrorCode.NONE;
  }

  public boolean fail() {
    return errorCode != ErrorCode.NONE;
  }

  public void clearError() {
    errorCode = ErrorCode.NONE;
    setErrorIndex(-1);
  }

  public void reset() {
    errorCode = ErrorCode.NONE;
    setIndex(0);
    setErrorIndex(-1);
  }

  public void reset(final int index) {
    errorCode = ErrorCode.NONE;
    setIndex(index);
    setErrorIndex(-1);
  }

  @Override
  public int hashCode() {
    final int multiplier = 71;
    int code = 133;
    code = Hash.combine(code, multiplier, super.hashCode());
    code = Hash.combine(code, multiplier, errorCode);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    final ParsingPosition other = (ParsingPosition) obj;
    return (errorCode == other.errorCode);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("current", getIndex())
        .append("errorCode", errorCode)
        .append("errorIndex", getErrorIndex())
        .append("errorMessage", ErrorCode.getMessage(errorCode))
        .toString();
  }
}
