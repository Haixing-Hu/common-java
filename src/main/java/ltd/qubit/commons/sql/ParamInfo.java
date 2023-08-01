////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.sql.error.UnsupportedSqlTypeException;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.sql.ParameterMetaData;

import static ltd.qubit.commons.lang.Argument.requireInEnum;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * Represents the information about a parameter.
 *
 * @author Haixing Hu
 */
public final class ParamInfo {

  private static final int[] NULLABLE_VALUES = {
      ParameterMetaData.parameterNoNulls,
      ParameterMetaData.parameterNullable,
      ParameterMetaData.parameterNullableUnknown,
  };

  private static final int[] MODE_VALUES = {
      ParameterMetaData.parameterModeUnknown,
      ParameterMetaData.parameterModeIn,
      ParameterMetaData.parameterModeInOut,
      ParameterMetaData.parameterModeOut,
  };

  private int type;
  private String typeName;
  private String typeClassName;
  private int nullable;
  private boolean signed;
  private int precision;
  private int scale;
  private int mode;

  public ParamInfo() {
    type = -1;
    typeName = StringUtils.EMPTY;
    typeClassName = StringUtils.EMPTY;
    nullable = -1;
    signed = false;
    precision = -1;
    scale = -1;
    mode = -1;
  }

  public ParamInfo(final int type) throws UnsupportedSqlTypeException {
    this.type = requireInEnum("type", type, StandardTypeMapping.SQL_TYPES);
    typeName = StandardTypeMapping.getName(type);
    typeClassName = StandardTypeMapping.getJavaType(type).getName();
    nullable = -1;
    signed = false;
    precision = -1;
    scale = -1;
    mode = -1;
  }

  public ParamInfo(final int type, final String typeName,
      final String typeClassName) {
    this.type = requireInEnum("type", type, StandardTypeMapping.SQL_TYPES);
    this.typeName = requireNonNull("typeName", typeName);
    this.typeClassName = requireNonNull("typeClassName", typeClassName);
    nullable = -1;
    signed = false;
    precision = -1;
    scale = -1;
    mode = -1;
  }

  public int getType() {
    return type;
  }

  public void setType(final int type) {
    this.type = requireInEnum("type", type, StandardTypeMapping.SQL_TYPES);
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(final String typeName) {
    this.typeName = requireNonNull("typeName", typeName);
  }

  public String getTypeClassName() {
    return typeClassName;
  }

  public void setTypeClassName(final String typeClassName) {
    this.typeClassName = requireNonNull("typeClassName", typeClassName);
  }

  public int isNullable() {
    return nullable;
  }

  public void setNullable(final int nullable) {
    requireInEnum("nullable", nullable, NULLABLE_VALUES);
    this.nullable = nullable;
  }

  public boolean isSigned() {
    return signed;
  }

  public void setSigned(final boolean signed) {
    this.signed = signed;
  }

  public int getPrecision() {
    return precision;
  }

  public void setPrecision(final int precision) {
    this.precision = precision;
  }

  public int getScale() {
    return scale;
  }

  public void setScale(final int scale) {
    this.scale = scale;
  }

  public int getMode() {
    return mode;
  }

  public void setMode(final int mode) {
    this.mode = requireInEnum("mode", mode, MODE_VALUES);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ParamInfo other = (ParamInfo) o;
    return Equality.equals(type, other.type)
        && Equality.equals(nullable, other.nullable)
        && Equality.equals(signed, other.signed)
        && Equality.equals(precision, other.precision)
        && Equality.equals(scale, other.scale)
        && Equality.equals(mode, other.mode)
        && Equality.equals(typeName, other.typeName)
        && Equality.equals(typeClassName, other.typeClassName);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, typeName);
    result = Hash.combine(result, multiplier, typeClassName);
    result = Hash.combine(result, multiplier, nullable);
    result = Hash.combine(result, multiplier, signed);
    result = Hash.combine(result, multiplier, precision);
    result = Hash.combine(result, multiplier, scale);
    result = Hash.combine(result, multiplier, mode);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("type", type)
        .append("typeName", typeName)
        .append("typeClassName", typeClassName)
        .append("nullable", nullable)
        .append("signed", signed)
        .append("precision", precision)
        .append("scale", scale)
        .append("mode", mode)
        .toString();
  }

}
