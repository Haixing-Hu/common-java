////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The basic implementation of the {@link NamedMultiValues} interface.
 *
 * @author Haixing Hu
 */
public class BasicNamedMultiValues extends BasicMultiValues
    implements NamedMultiValues {

  private static final long serialVersionUID = 5507620034233294778L;

  protected String name;

  public BasicNamedMultiValues() {
    name = StringUtils.EMPTY;
  }

  public BasicNamedMultiValues(final String name) {
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final Type type) {
    super(type);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final boolean value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final char value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final byte value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final short value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final int value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final long value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final float value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final double value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final String value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final Date value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final BigInteger value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final BigDecimal value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final byte[] value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  public BasicNamedMultiValues(final String name, final Class<?> value) {
    super(value);
    this.name = requireNonNull("name", name);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(final String name) {
    this.name = requireNonNull("name", name);
  }

  @Override
  public BasicNamedMultiValues clone() {
    final BasicNamedMultiValues result = (BasicNamedMultiValues) super.clone();
    result.name = name;
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 17;
    int code = super.hashCode();
    code = Hash.combine(code, multiplier, name);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (! (obj instanceof BasicNamedMultiValues)) {
      return false;
    }
    final BasicNamedMultiValues other = (BasicNamedMultiValues) obj;
    return (super.equals(other))
          && Equality.equals(name, other.name);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("name", name)
               .appendSuper(super.toString())
               .toString();
  }
}
