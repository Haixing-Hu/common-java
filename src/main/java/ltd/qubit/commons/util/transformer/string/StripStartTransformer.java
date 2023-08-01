////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import javax.annotation.concurrent.Immutable;

/**
 * A {@link StripStartTransformer} transform a string by stripping the
 * leading whitespace and non-printable characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class StripStartTransformer extends AbstractStringTransformer {

  public static final StripStartTransformer INSTANCE = new StripStartTransformer();

  public StripStartTransformer() {
    // empty
  }

  @Override
  public String transform(final String str) {
    return new Stripper().fromStart().strip(str);
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
    return 3;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }

  @Override
  public StripStartTransformer clone() {
    return this;
  }
}
