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

import ltd.qubit.commons.lang.SystemUtils;

/**
 * The multi-line {@code toString()} style.
 *
 * <p>Using the {@code Person} example from {@link ToStringBuilder}, the
 * output would look like this:
 *
 * <pre>
 * Person@182f0db[
 *   name=John Doe
 *   age=33
 *   smoker=false
 * ]
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class MultiLineToStringStyle extends ToStringStyle {

  private static final long serialVersionUID = 4592558914484643637L;

  public static final MultiLineToStringStyle INSTANCE = new MultiLineToStringStyle();

  public MultiLineToStringStyle() {
    setContentStart("[");
    setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
    setFieldSeparatorAtStart(true);
    setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
  }

  @Override
  public String toString() {
    return "MultiLineToStringStyle";
  }
}
