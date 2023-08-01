////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A {@link StripEndTransformer} transform a string by stripping the trailing
 * whitespace and non-printable characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class StripEndTransformer extends AbstractStringTransformer {

  public static final StripEndTransformer INSTANCE = new StripEndTransformer();

  public StripEndTransformer() {
    // empty
  }

  @Override
  public String transform(final String str) {
    return new Stripper().fromEnd().strip(str);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    return (o != null) && (getClass() == o.getClass());
  }

  @Override
  public int hashCode() {
    return 2;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }

  @Override
  public StripEndTransformer clone() {
    return this;
  }
}
