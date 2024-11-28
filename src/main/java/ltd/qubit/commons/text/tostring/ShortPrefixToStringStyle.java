////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import javax.annotation.concurrent.Immutable;

/**
 * The short prefix {@code toString()} style.
 * <p>
 * Using the {@code Person} example from {@link ToStringBuilder}, the
 * output would look like this:
 * </p>
 *
 * <pre>
 * Person[name=John Doe,age=33,smoker=false]
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class ShortPrefixToStringStyle extends ToStringStyle {

  private static final long serialVersionUID = - 3279297606363902427L;

  public static final ShortPrefixToStringStyle INSTANCE = new ShortPrefixToStringStyle();

  public ShortPrefixToStringStyle() {
    this.setUseShortClassName(true);
    this.setUseIdentityHashCode(false);
  }

  @Override
  public String toString() {
    return "ShortPrefixToStringStyle";
  }
}
