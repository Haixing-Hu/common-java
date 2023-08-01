////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.util.concurrent.TimeUnit;

/**
 * A DateRange object is used to represents a range of date.
 *
 * @author Haixing Hu
 */
public class DateRange {

  public enum Type {
    ABSOLUTE,
    RELATIVE,
  }

  private Type type;
  private long start;
  private long end;
  private TimeUnit unit;

  public DateRange() {
    type = Type.ABSOLUTE;
    start = -1;
    end = -1;
    unit = TimeUnit.MILLISECONDS;
  }

  public Type getType() {
    return type;
  }

  public void setType(final Type type) {
    this.type = type;
  }

  public long getStart() {
    return start;
  }

  public void setStart(final long start) {
    this.start = start;
  }

  public long getEnd() {
    return end;
  }

  public void setEnd(final long end) {
    this.end = end;
  }

  public TimeUnit getUnit() {
    return unit;
  }

  public void setUnit(final TimeUnit unit) {
    this.unit = unit;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final DateRange other = (DateRange) o;
    return Equality.equals(start, other.start)
            && Equality.equals(end, other.end)
            && Equality.equals(type, other.type)
            && Equality.equals(unit, other.unit);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, start);
    result = Hash.combine(result, multiplier, end);
    result = Hash.combine(result, multiplier, unit);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("type", type)
            .append("start", start)
            .append("end", end)
            .append("unit", unit)
            .toString();
  }
}
