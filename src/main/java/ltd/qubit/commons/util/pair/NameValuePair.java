////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;

import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Comparison;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A name-value pair.
 *
 * @author Haixing Hu
 */
public class NameValuePair implements Serializable, CloneableEx<NameValuePair>,
    Comparable<NameValuePair> {

  private static final long serialVersionUID = 7295136864605612700L;

  public static List<NameValuePair> createList(final Map<String, String> params) {
    final List<NameValuePair> result = new ArrayList<>();
    params.forEach((key, value) -> {
      result.add(new NameValuePair(key, value));
    });
    return result;
  }

  private final String name;

  private final String value;

  public NameValuePair(final String name, final String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final NameValuePair other = (NameValuePair) o;
    return Equality.equals(name, other.name)
        && Equality.equals(value, other.value);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, value);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("value", value)
        .toString();
  }

  @Override
  public NameValuePair cloneEx() {
    return new NameValuePair(name, value);
  }

  @Override
  public int compareTo(@NotNull final NameValuePair other) {
    return Comparison.compare(name, other.name);
  }
}