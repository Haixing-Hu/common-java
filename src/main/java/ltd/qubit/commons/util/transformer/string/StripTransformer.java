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
 * A {@link StripTransformer} transform a string by stripping the
 * leading and trailing whitespace and non-printable characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class StripTransformer extends AbstractStringTransformer {

  public static final StripTransformer INSTANCE = new StripTransformer();

  public StripTransformer() {
    // empty
  }

  @Override
  public String transform(final String str) {
    return new Stripper().strip(str);
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
    return 7;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }

  @Override
  public StripTransformer clone() {
    return this;
  }
}
