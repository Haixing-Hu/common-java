////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.mapper;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

public class TestEntity {

  private String field1;
  private String field2;
  private TestEntity child;

  public TestEntity() {
    // empty
  }

  public String getField1() {
    return field1;
  }

  public void setField1(final String field1) {
    this.field1 = field1;
  }

  public String getField2() {
    return field2;
  }

  public void setField2(final String field2) {
    this.field2 = field2;
  }

  public TestEntity getChild() {
    return child;
  }

  public void setChild(final TestEntity child) {
    this.child = child;
  }

  public String getField(final int index) {
    switch (index) {
      case 0:
        return field1;
      case 1:
        return field2;
      default:
        throw new IndexOutOfBoundsException();
    }
  }

  public void setField(final int index, final String value) {
    switch (index) {
      case 0:
        field1 = value;
        return;
      case 1:
        field2 = value;
        return;
      default:
        throw new IndexOutOfBoundsException();
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
    final TestEntity other = (TestEntity) o;
    return Equality.equals(field1, other.field1) && Equality.equals(field2, other.field2) && Equality.equals(child,
        other.child);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, field1);
    result = Hash.combine(result, multiplier, field2);
    result = Hash.combine(result, multiplier, child);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("field1", field1)
        .append("field2", field2)
        .append("child", child)
        .toString();
  }
}