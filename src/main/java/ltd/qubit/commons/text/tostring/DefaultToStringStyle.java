////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import javax.annotation.concurrent.Immutable;

/**
 * The default {@code toString()} style.
 * <p>
 * Using the {@code Person} example from {@link ToStringBuilder}, the
 * output would look like this:
 * </p>
 *
 * <pre>
 * Person@182f0db[name=John Doe,age=33,smoker=false]
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class DefaultToStringStyle extends ToStringStyle {

  private static final long serialVersionUID = - 2169892717080992996L;

  public static final DefaultToStringStyle INSTANCE = new DefaultToStringStyle();

  public DefaultToStringStyle() {
  }

  @Override
  public String toString() {
    return "DefaultToStringStyle";
  }
}
