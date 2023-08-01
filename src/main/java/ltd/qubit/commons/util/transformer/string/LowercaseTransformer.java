////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import java.util.Locale;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A {@link LowercaseTransformer} transform a string to its lowercase form.
 *
 * @author Haixing Hu
 */
public final class LowercaseTransformer extends AbstractStringTransformer {

  private Locale locale;

  public LowercaseTransformer() {
    locale = null;
  }

  public LowercaseTransformer(final Locale locale) {
    this.locale = locale;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

  @Override
  public String transform(final String str) {
    if (locale == null) {
      return str.toLowerCase();
    } else {
      return str.toLowerCase(locale);
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final LowercaseTransformer other = (LowercaseTransformer) o;
    return Equality.equals(locale, other.locale);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, locale);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("locale", locale)
            .toString();
  }

  @Override
  public LowercaseTransformer clone() {
    return new LowercaseTransformer(locale);
  }
}
