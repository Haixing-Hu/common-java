////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.ParameterMetaData;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.sql.error.UnsupportedSqlTypeException;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireInEnum;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 表示参数的信息。
 *
 * @author 胡海星
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

  /**
   * SQL类型。
   */
  private int type;

  /**
   * 类型名称。
   */
  private String typeName;

  /**
   * 类型的Java类名。
   */
  private String typeClassName;

  /**
   * 是否可为空。
   */
  private int nullable;

  /**
   * 是否有符号。
   */
  private boolean signed;

  /**
   * 精度。
   */
  private int precision;

  /**
   * 小数位数。
   */
  private int scale;

  /**
   * 参数模式。
   */
  private int mode;

  /**
   * 构造一个默认的参数信息对象。
   */
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

  /**
   * 根据指定的SQL类型构造参数信息对象。
   *
   * @param type
   *     SQL类型，必须是{@link StandardTypeMapping#SQL_TYPES}中定义的有效值。
   * @throws UnsupportedSqlTypeException
   *     如果指定的SQL类型不受支持。
   */
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

  /**
   * 根据指定的SQL类型、类型名称和Java类名构造参数信息对象。
   *
   * @param type
   *     SQL类型，必须是{@link StandardTypeMapping#SQL_TYPES}中定义的有效值。
   * @param typeName
   *     类型名称，不能为{@code null}。
   * @param typeClassName
   *     类型的Java类名，不能为{@code null}。
   */
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

  /**
   * 获取SQL类型。
   *
   * @return
   *     SQL类型。
   */
  public int getType() {
    return type;
  }

  /**
   * 设置SQL类型。
   *
   * @param type
   *     SQL类型，必须是{@link StandardTypeMapping#SQL_TYPES}中定义的有效值。
   */
  public void setType(final int type) {
    this.type = requireInEnum("type", type, StandardTypeMapping.SQL_TYPES);
  }

  /**
   * 获取类型名称。
   *
   * @return
   *     类型名称。
   */
  public String getTypeName() {
    return typeName;
  }

  /**
   * 设置类型名称。
   *
   * @param typeName
   *     类型名称，不能为{@code null}。
   */
  public void setTypeName(final String typeName) {
    this.typeName = requireNonNull("typeName", typeName);
  }

  /**
   * 获取类型的Java类名。
   *
   * @return
   *     类型的Java类名。
   */
  public String getTypeClassName() {
    return typeClassName;
  }

  /**
   * 设置类型的Java类名。
   *
   * @param typeClassName
   *     类型的Java类名，不能为{@code null}。
   */
  public void setTypeClassName(final String typeClassName) {
    this.typeClassName = requireNonNull("typeClassName", typeClassName);
  }

  /**
   * 获取参数是否可以为空的指示符。
   *
   * @return
   *     可空指示符，可能的值为：
   *     <ul>
   *       <li>{@link ParameterMetaData#parameterNoNulls} - 参数不能为空</li>
   *       <li>{@link ParameterMetaData#parameterNullable} - 参数可以为空</li>
   *       <li>{@link ParameterMetaData#parameterNullableUnknown} - 未知是否可以为空</li>
   *     </ul>
   */
  public int isNullable() {
    return nullable;
  }

  /**
   * 设置参数是否可以为空的指示符。
   *
   * @param nullable
   *     可空指示符，必须是以下值之一：
   *     <ul>
   *       <li>{@link ParameterMetaData#parameterNoNulls} - 参数不能为空</li>
   *       <li>{@link ParameterMetaData#parameterNullable} - 参数可以为空</li>
   *       <li>{@link ParameterMetaData#parameterNullableUnknown} - 未知是否可以为空</li>
   *     </ul>
   */
  public void setNullable(final int nullable) {
    requireInEnum("nullable", nullable, NULLABLE_VALUES);
    this.nullable = nullable;
  }

  /**
   * 获取参数是否有符号。
   *
   * @return
   *     如果参数有符号则返回{@code true}，否则返回{@code false}。
   */
  public boolean isSigned() {
    return signed;
  }

  /**
   * 设置参数是否有符号。
   *
   * @param signed
   *     如果参数有符号则为{@code true}，否则为{@code false}。
   */
  public void setSigned(final boolean signed) {
    this.signed = signed;
  }

  /**
   * 获取参数的精度。
   *
   * @return
   *     参数的精度。
   */
  public int getPrecision() {
    return precision;
  }

  /**
   * 设置参数的精度。
   *
   * @param precision
   *     参数的精度。
   */
  public void setPrecision(final int precision) {
    this.precision = precision;
  }

  /**
   * 获取参数的小数位数。
   *
   * @return
   *     参数的小数位数。
   */
  public int getScale() {
    return scale;
  }

  /**
   * 设置参数的小数位数。
   *
   * @param scale
   *     参数的小数位数。
   */
  public void setScale(final int scale) {
    this.scale = scale;
  }

  /**
   * 获取参数的模式。
   *
   * @return
   *     参数的模式，可能的值为：
   *     <ul>
   *       <li>{@link ParameterMetaData#parameterModeUnknown} - 未知模式</li>
   *       <li>{@link ParameterMetaData#parameterModeIn} - 输入参数</li>
   *       <li>{@link ParameterMetaData#parameterModeInOut} - 输入输出参数</li>
   *       <li>{@link ParameterMetaData#parameterModeOut} - 输出参数</li>
   *     </ul>
   */
  public int getMode() {
    return mode;
  }

  /**
   * 设置参数的模式。
   *
   * @param mode
   *     参数的模式，必须是以下值之一：
   *     <ul>
   *       <li>{@link ParameterMetaData#parameterModeUnknown} - 未知模式</li>
   *       <li>{@link ParameterMetaData#parameterModeIn} - 输入参数</li>
   *       <li>{@link ParameterMetaData#parameterModeInOut} - 输入输出参数</li>
   *       <li>{@link ParameterMetaData#parameterModeOut} - 输出参数</li>
   *     </ul>
   */
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