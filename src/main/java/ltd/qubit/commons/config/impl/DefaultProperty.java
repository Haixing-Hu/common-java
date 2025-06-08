////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.config.Property;
import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.value.BasicNamedMultiValues;

/**
 * {@link Property} 接口的默认实现。
 *
 * @author 胡海星
 */
public class DefaultProperty extends BasicNamedMultiValues implements Property {

  @Serial
  private static final long serialVersionUID = 3586004753799522564L;

  /**
   * 日志记录器。
   */
  protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultProperty.class);

  /**
   * 注册 {@link DefaultProperty} 的 {@link BinarySerialization} 和 {@link XmlSerialization}。
   */
  static {
    BinarySerialization.register(DefaultProperty.class, DefaultPropertyBinarySerializer.INSTANCE);
    XmlSerialization.register(DefaultProperty.class, DefaultPropertyXmlSerializer.INSTANCE);
  }

  /**
   * 默认的 {@code isFinal} 值。
   */
  public static final boolean DEFAULT_IS_FINAL = false;

  /**
   * 默认的 {@code trim} 值。
   */
  public static final boolean DEFAULT_TRIM = true;

  /**
   * 默认的 {@code preserveSpace} 值。
   */
  public static final boolean DEFAULT_PRESERVE_SPACE = false;

  /**
   * 描述。
   */
  protected String description;

  /**
   * 是否为最终属性。
   */
  protected boolean isFinal;

  /**
   * 构造一个 {@link DefaultProperty}。
   */
  public DefaultProperty() {
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   */
  public DefaultProperty(final String name) {
    super(name);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param type
   *     属性类型。
   */
  public DefaultProperty(final String name, final Type type) {
    super(name, type);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final boolean value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final char value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final byte value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final short value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final int value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final long value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final float value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final double value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final String value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final LocalDate value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final LocalTime value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final LocalDateTime value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final BigInteger value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final BigDecimal value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final byte[] value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * 构造一个 {@link DefaultProperty}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     属性值。
   */
  public DefaultProperty(final String name, final Class<?> value) {
    super(name, value);
    description = null;
    isFinal = DEFAULT_IS_FINAL;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFinal() {
    return isFinal;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFinal(final boolean isFinal) {
    this.isFinal = isFinal;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void assign(final Property that) {
    if (this != that) {
      name = that.getName();
      description = that.getDescription();
      isFinal = that.isFinal();
      assignValues(that);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultProperty cloneEx() {
    final DefaultProperty result = new DefaultProperty(name, type);
    result.assign(this);
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 1121;
    int code = 3;
    code = Hash.combine(code, multiplier, super.hashCode());
    code = Hash.combine(code, multiplier, description);
    code = Hash.combine(code, multiplier, isFinal);
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
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DefaultProperty other = (DefaultProperty) obj;
    return super.equals(other)
        && (isFinal == other.isFinal)
        && Equality.equals(description, other.description);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .appendSuper(super.toString())
        .append("description", description)
        .append("isFinal", isFinal)
        .toString();
  }

}