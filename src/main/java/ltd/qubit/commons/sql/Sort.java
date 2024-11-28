////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.io.Serial;
import java.io.Serializable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * Stores the sorting conditions.
 *
 * @author Haixing Hu
 */
public class Sort implements Serializable {

  @Serial
  private static final long serialVersionUID = -1666296704696820914L;

  private String field;
  private SortOrder order;

  public final String getField() {
    return field;
  }

  public final void setField(final String field) {
    this.field = field;
  }

  public final SortOrder getOrder() {
    return order;
  }

  public final void setOrder(final SortOrder order) {
    this.order = order;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Sort other = (Sort) o;
    return Equality.equals(field, other.field)
            && Equality.equals(order, other.order);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, field);
    result = Hash.combine(result, multiplier, order);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("field", field)
            .append("order", order)
            .toString();
  }
}
