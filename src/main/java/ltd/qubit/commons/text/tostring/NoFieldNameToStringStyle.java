////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import javax.annotation.concurrent.Immutable;

/**
 * The no field names {@code toString()} style.
 * <p>
 * Using the {@code Person} example from {@link ToStringBuilder}, the
 * output would look like this:
 * </p>
 *
 * <pre>
 * Person@182f0db[John Doe,33,false]
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class NoFieldNameToStringStyle extends ToStringStyle {

  private static final long serialVersionUID = 8376261817715975340L;

  public static final NoFieldNameToStringStyle INSTANCE = new NoFieldNameToStringStyle();

  public NoFieldNameToStringStyle() {
    this.setUseFieldNames(false);
  }

  @Override
  public String toString() {
    return "NoFieldNameToStringStyle";
  }
}
