////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.StringUtils;

/**
 * The simple {@code toString()} style.
 *
 * <p>Using the {@code Person} example from {@link ToStringBuilder}, the
 * output would look like this:
 * <pre>
 * John Doe,33,false
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class SimpleToStringStyle extends ToStringStyle {

  private static final long serialVersionUID = -542352958755202081L;

  public static final SimpleToStringStyle INSTANCE = new SimpleToStringStyle();

  public SimpleToStringStyle() {
    setUseClassName(false);
    setUseIdentityHashCode(false);
    setUseFieldNames(false);
    setContentStart(StringUtils.EMPTY);
    setContentEnd(StringUtils.EMPTY);
  }

  @Override
  public String toString() {
    return "SimpleToStringStyle";
  }
}