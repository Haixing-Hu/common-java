////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.config.AbstractConfig;
import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.config.MergingPolicy;
import ltd.qubit.commons.config.Property;
import ltd.qubit.commons.config.WritableConfig;
import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * The {@link DefaultConfig} class is the default implementation of the {@link
 * Config} interface.
 *
 * @author Haixing Hu
 */
public class DefaultConfig extends AbstractConfig implements WritableConfig {

  @Serial
  private static final long serialVersionUID = 3519879255214071861L;

  protected static final Logger LOGGER = LoggerFactory
      .getLogger(DefaultConfig.class);

  static {
    BinarySerialization.register(DefaultConfig.class, DefaultConfigBinarySerializer.INSTANCE);
    XmlSerialization.register(DefaultConfig.class, DefaultConfigXmlSerializer.INSTANCE);
  }

  protected String description;
  protected Map<String, DefaultProperty> properties;

  /**
   * Constructs an empty {@link DefaultConfig} object.
   */
  public DefaultConfig() {
    description = null;
    properties = new HashMap<>();
  }

  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description
   *     the new description to set, or {@code null} if none.
   */
  @Override
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Override
  public void setDescription(final String name, final String description) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDescription(description);
  }

  @Override
  public boolean isEmpty() {
    return properties.isEmpty();
  }

  @Override
  public int size() {
    return properties.size();
  }

  @Override
  public Collection<DefaultProperty> getProperties() {
    return properties.values();
  }

  @Override
  public Set<String> getNames() {
    return properties.keySet();
  }

  @Override
  public boolean contains(final String name) {
    return properties.containsKey(name);
  }

  @Override
  public DefaultProperty get(final String name) {
    return properties.get(name);
  }

  public DefaultProperty set(final DefaultProperty property) {
    return properties.put(property.getName(), property);
  }

  /**
   * Adds all properties of the specified {@link Config} object to this
   * {@link DefaultConfig} object.
   *
   * @param config
   *     the {@link Config} object whose properties are to be added.
   * @return
   *     this {@link DefaultConfig} object.
   */
  public DefaultConfig addAll(final Config config) {
    Argument.requireNonNull("config", config);
    for (final Property prop : config.getProperties()) {
      add(prop);
    }
    return this;
  }

  /**
   * Adds a collection of {@link Property}s to this {@link DefaultConfig} object.
   *
   * @param properties
   *     the collection of {@link Property}s to be added.
   * @return
   *     this {@link DefaultConfig} object.
   */
  public DefaultConfig addAll(final Collection<? extends Property> properties) {
    Argument.requireNonNull("properties", properties);
    for (final Property prop : properties) {
      add(prop);
    }
    return this;
  }

  /**
   * Adds a new {@link Property} to this {@link DefaultConfig} object.
   *
   * @param prop
   *     the new {@link Property} to be add.
   * @return the previous {@link Property} with the same name in this {@link
   *     DefaultConfig} object, or null if no previous {@link Property} with the
   *     same name.
   */
  public DefaultProperty add(final Property prop) {
    DefaultProperty newProp = null;
    if (prop instanceof DefaultProperty) {
      newProp = (DefaultProperty) prop;
    } else {
      newProp = new DefaultProperty();
      newProp.assign(prop);
    }
    return properties.put(prop.getName(), newProp);
  }

  /**
   * Add a new empty property with the specified name to this {@link
   * DefaultConfig} object.
   *
   * @param name
   *     the name of the new empty property.
   * @return the newly added {@link DefaultProperty} object.
   */
  public DefaultProperty add(final String name) {
    final DefaultProperty prop = new DefaultProperty(name);
    properties.put(name, prop);
    return prop;
  }

  /**
   * Add a new empty property with the specified name and type to this {@link
   * DefaultConfig} object.
   *
   * @param name
   *     the name of the new empty property.
   * @param type
   *     the type of the new empty property.
   * @return the newly added {@link DefaultProperty} object.
   */
  public DefaultProperty add(final String name, final Type type) {
    final DefaultProperty prop = new DefaultProperty(name, type);
    properties.put(name, prop);
    return prop;
  }

  @Override
  public DefaultProperty remove(final String name) {
    return properties.remove(name);
  }

  @Override
  public void setFinal(final String name, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setFinal(isFinal);
  }

  @Override
  public void setType(final String name, final Type type) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setType(type);
  }

  @Override
  public void setBoolean(final String name, final boolean value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBooleanValue(value);
  }

  @Override
  public void setBooleans(final String name, final boolean... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBooleanValues(values);
  }

  @Override
  public void setBooleans(final String name, final BooleanCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBooleanValues(values);
  }

  @Override
  public void addBoolean(final String name, final boolean value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addBooleanValue(value);
  }

  @Override
  public void addBooleans(final String name, final boolean... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addBooleanValues(values);
  }

  @Override
  public void addBooleans(final String name, final BooleanCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addBooleanValues(values);
  }

  @Override
  public void setChar(final String name, final char value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setCharValue(value);
  }

  @Override
  public void setChars(final String name, final char... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setCharValues(values);
  }

  @Override
  public void setChars(final String name, final CharCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setCharValues(values);
  }

  @Override
  public void addChar(final String name, final char value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addCharValue(value);
  }

  @Override
  public void addChars(final String name, final char... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addCharValues(values);
  }

  @Override
  public void addChars(final String name, final CharCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addCharValues(values);
  }

  @Override
  public void setByte(final String name, final byte value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setByteValue(value);
  }

  @Override
  public void setBytes(final String name, final byte... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setByteValues(values);
  }

  @Override
  public void setBytes(final String name, final ByteCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setByteValues(values);
  }

  @Override
  public void addByte(final String name, final byte value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addByteValue(value);
  }

  @Override
  public void addBytes(final String name, final byte... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addByteValues(values);
  }

  @Override
  public void addBytes(final String name, final ByteCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addByteValues(values);
  }

  @Override
  public void setShort(final String name, final short value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setShortValue(value);
  }

  @Override
  public void setShorts(final String name, final short... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setShortValues(values);
  }

  @Override
  public void setShorts(final String name, final ShortCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setShortValues(values);
  }

  @Override
  public void addShort(final String name, final short value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addShortValue(value);
  }

  @Override
  public void addShorts(final String name, final short... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addShortValues(values);
  }

  @Override
  public void addShorts(final String name, final ShortCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addShortValues(values);
  }

  @Override
  public void setInt(final String name, final int value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setIntValue(value);
  }

  @Override
  public void setInts(final String name, final int... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setIntValues(values);
  }

  @Override
  public void setInts(final String name, final IntCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setIntValues(values);
  }

  @Override
  public void addInt(final String name, final int value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addIntValue(value);
  }

  @Override
  public void addInts(final String name, final int... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addIntValues(values);
  }

  @Override
  public void addInts(final String name, final IntCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addIntValues(values);
  }

  @Override
  public void setLong(final String name, final long value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setLongValue(value);
  }

  @Override
  public void setLongs(final String name, final long... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setLongValues(values);
  }

  @Override
  public void setLongs(final String name, final LongCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setLongValues(values);
  }

  @Override
  public void addLong(final String name, final long value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addLongValue(value);
  }

  @Override
  public void addLongs(final String name, final long... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addLongValues(values);
  }

  @Override
  public void addLongs(final String name, final LongCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addLongValues(values);
  }

  @Override
  public void setFloat(final String name, final float value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setFloatValue(value);
  }

  @Override
  public void setFloats(final String name, final float... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setFloatValues(values);
  }

  @Override
  public void setFloats(final String name, final FloatCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setFloatValues(values);
  }

  @Override
  public void addFloat(final String name, final float value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addFloatValue(value);
  }

  @Override
  public void addFloats(final String name, final float... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addFloatValues(values);
  }

  @Override
  public void addFloats(final String name, final FloatCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addFloatValues(values);
  }

  @Override
  public void setDouble(final String name, final double value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDoubleValue(value);
  }

  @Override
  public void setDoubles(final String name, final double... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDoubleValues(values);
  }

  @Override
  public void setDoubles(final String name, final DoubleCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDoubleValues(values);
  }

  @Override
  public void addDouble(final String name, final double value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addDoubleValue(value);
  }

  @Override
  public void addDoubles(final String name, final double... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addDoubleValues(values);
  }

  @Override
  public void addDoubles(final String name, final DoubleCollection values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addDoubleValues(values);
  }

  @Override
  public void setString(final String name, final String value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setStringValue(value);
  }

  @Override
  public void setStrings(final String name, final String... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setStringValues(values);
  }

  @Override
  public void setStrings(final String name, final Collection<String> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setStringValues(values);
  }

  @Override
  public void addString(final String name, final String value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addStringValue(value);
  }

  @Override
  public void addStrings(final String name, final String... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addStringValues(values);
  }

  @Override
  public void addStrings(final String name, final Collection<String> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addStringValues(values);
  }

  @Override
  public void setDate(final String name, final Date value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDateValue(value);
  }

  @Override
  public void setDates(final String name, final Date... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDateValues(values);
  }

  @Override
  public void setDates(final String name, final Collection<Date> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDateValues(values);
  }

  @Override
  public void addDate(final String name, final Date value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addDateValue(value);
  }

  @Override
  public void addDates(final String name, final Date... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addDateValues(values);
  }

  @Override
  public void addDates(final String name, final Collection<Date> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addDateValues(values);
  }

  @Override
  public void setBigInteger(final String name, final BigInteger value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBigIntegerValue(value);
  }

  @Override
  public void setBigIntegers(final String name, final BigInteger... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBigIntegerValues(values);
  }

  @Override
  public void setBigIntegers(final String name,
      final Collection<BigInteger> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBigIntegerValues(values);
  }

  @Override
  public void addBigInteger(final String name, final BigInteger value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addBigIntegerValue(value);
  }

  @Override
  public void addBigIntegers(final String name, final BigInteger... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addBigIntegerValues(values);
  }

  @Override
  public void addBigIntegers(final String name,
      final Collection<BigInteger> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addBigIntegerValues(values);
  }

  @Override
  public void setBigDecimal(final String name, final BigDecimal value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBigDecimalValue(value);
  }

  @Override
  public void setBigDecimals(final String name, final BigDecimal... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBigDecimalValues(values);
  }

  @Override
  public void setBigDecimals(final String name,
      final Collection<BigDecimal> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBigDecimalValues(values);
  }

  @Override
  public void addBigDecimal(final String name, final BigDecimal value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addBigDecimalValue(value);
  }

  @Override
  public void addBigDecimals(final String name, final BigDecimal... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addBigDecimalValues(values);
  }

  @Override
  public void addBigDecimals(final String name,
      final Collection<BigDecimal> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addBigDecimalValues(values);
  }

  @Override
  public void setByteArray(final String name, final byte[] value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setByteArrayValue(value);
  }

  @Override
  public void setByteArrays(final String name, final byte[]... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setByteArrayValues(values);
  }

  @Override
  public void setByteArrays(final String name, final Collection<byte[]> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setByteArrayValues(values);
  }

  @Override
  public void addByteArray(final String name, final byte[] value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addByteArrayValue(value);
  }

  @Override
  public void addByteArrays(final String name, final byte[]... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addByteArrayValues(values);
  }

  @Override
  public void addByteArrays(final String name, final Collection<byte[]> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addByteArrayValues(values);
  }

  @Override
  public void setEnum(final String name, @Nullable final Enum<?> value) {
    setEnum(name, value, false);
  }

  @Override
  public void setEnum(final String name, @Nullable final Enum<?> value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setEnumValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setEnums(final String name, @Nullable final Enum<?>... values) {
    setEnums(name, values, false);
  }

  @Override
  public void setEnums(final String name, @Nullable final Enum<?>[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setEnumValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setEnums(final String name,
      @Nullable final Collection<? extends Enum<?>> values) {
    setEnums(name, values, false);
  }

  @Override
  public void setEnums(final String name,
      @Nullable final Collection<? extends Enum<?>> values,
      final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setEnumValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void addEnum(final String name, final Enum<?> value) {
    Argument.requireNonNull("value", value);
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
      prop.setEnumValue(value);
    } else {
      prop.addEnumValue(value);
    }
  }

  @Override
  public void addEnums(final String name, final Enum<?>... values) {
    Argument.requireNonNull("values", values);
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
      prop.setEnumValues(values);
    } else {
      prop.addEnumValues(values);
    }
  }

  @Override
  public void addEnums(final String name, final Collection<? extends Enum<?>> values) {
    Argument.requireNonNull("values", values);
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
      prop.setEnumValues(values);
    } else {
      prop.addEnumValues(values);
    }
  }

  @Override
  public void setClass(final String name, final Class<?> value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setClassValue(value);
  }

  @Override
  public void setClasses(final String name, final Class<?>... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setClassValues(values);
  }

  @Override
  public void setClasses(final String name, final Collection<Class<?>> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setClassValues(values);
  }

  @Override
  public void addClass(final String name, final Class<?> value) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addClassValue(value);
  }

  @Override
  public void addClasses(final String name, final Class<?>... values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addClassValues(values);
  }

  @Override
  public void addClasses(final String name, final Collection<Class<?>> values) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.addClassValues(values);
  }

  @Override
  public void merge(final Config config, final MergingPolicy policy) {
    merge(config, StringUtils.EMPTY, policy);
  }

  @Override
  public void merge(final Config config, final String prefix,
      final MergingPolicy policy) {
    Argument.requireNonNull("config", config);
    Argument.requireNonNull("prefix", prefix);
    Argument.requireNonNull("policy", policy);
    if (this == config) {
      return;
    }
    LOGGER.trace("Start merging using policy {} ....", policy);
    switch (policy) {
      case SKIP:
        for (final Property thatProp : config.getProperties()) {
          final String name = thatProp.getName();
          if (!name.startsWith(prefix)) {
            continue;
          }
          if (contains(name)) {
            LOGGER.trace("Skip the existing property '{}'.", name);
          } else {
            LOGGER.trace("Adding a new property '{}'.", name);
            final DefaultProperty thisProp = add(name);
            thisProp.assign(thatProp);
          }
        }
        break;
      case UNION:
        for (final Property thatProp : config.getProperties()) {
          final String name = thatProp.getName();
          if (!name.startsWith(prefix)) {
            continue;
          }
          DefaultProperty thisProp = get(name);
          if (thisProp == null) {
            LOGGER.trace("Adding a new property '{}'.", name);
            thisProp = add(name);
            thisProp.assign(thatProp);
          } else if (thisProp.isFinal) {
            LOGGER.trace("Skip the final property '{}'.", name);
          } else {
            if (thisProp.getType() != thatProp.getType()) {
              LOGGER.trace("Overwrite a existing property '{}'.", name);
              thisProp.assign(thatProp);
            } else {
              LOGGER.trace("Union a existing property '{}'.", name);
              thisProp.unionValues(thatProp);
            }
          }
        }
        break;
      case OVERWRITE:
      default:
        for (final Property thatProp : config.getProperties()) {
          final String name = thatProp.getName();
          if (!name.startsWith(prefix)) {
            continue;
          }
          DefaultProperty thisProp = get(name);
          if (thisProp == null) {
            LOGGER.trace("Adding a new property '{}'.", name);
            thisProp = add(name);
            thisProp.assign(thatProp);
          } else if (thisProp.isFinal()) {
            LOGGER.trace("Skip the final property '{}'.", name);
          } else {
            LOGGER.trace("Overwrite a existing property '{}'.", name);
            thisProp.assign(thatProp);
          }
        }
        break;
    }
    LOGGER.trace("Merging finished.");
  }

  @Override
  public void assign(final Config config) {
    LOGGER.trace("Start Assignment with another configuration ...");
    if (this != config) {
      LOGGER.trace("Removing all properties ... ");
      removeAll();
      for (final Property thatProp : config.getProperties()) {
        final String name = thatProp.getName();
        LOGGER.trace("Adding a new property '{}'.", name);
        final DefaultProperty thisProp = add(name);
        thisProp.assign(thatProp);
      }
    }
    LOGGER.trace("Assignment finished.");
  }

  @Override
  public void assign(final Config config, final String prefix) {
    LOGGER.trace("Start Assignment with another configuration ...");
    if (this != config) {
      LOGGER.trace("Removing all properties ... ");
      removeAll();
      for (final Property thatProp : config.getProperties()) {
        final String name = thatProp.getName();
        if (name.startsWith(prefix)) {
          LOGGER.trace("Adding a new property '{}'.", name);
          final DefaultProperty thisProp = add(name);
          thisProp.assign(thatProp);
        }
      }
    } else {
      // this == config, just remove all properties not starting with prefix
      for (final String name : getNames()) {
        if (!name.startsWith(prefix)) {
          LOGGER.trace("Removing the property '{}'.", name);
          remove(name);
        }
      }
    }
    LOGGER.trace("Assignment finished.");
  }

  @Override
  public DefaultProperty clear(final String name) {
    final DefaultProperty prop = get(name);
    if (prop != null) {
      prop.clear();
    }
    return prop;
  }

  public void removeAll() {
    properties.clear();
  }

  @Override
  public int hashCode() {
    return properties.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    // note that we only need the obj to be instance of DefaultConfig,
    // that is, allowing the object of the sub-class of DefaultConfig
    // to be equal to the object of DefaultConfig, as long as they have
    // the same properties.
    if (!(obj instanceof final DefaultConfig other)) {
      return false;
    }
    return properties.equals(other.properties);
  }

  @Override
  public Config cloneEx() {
    final DefaultConfig result = new DefaultConfig();
    for (final DefaultProperty prop : properties.values()) {
      result.properties.put(prop.getName(), prop.cloneEx());
    }
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("properties", properties)
        .toString();
  }

  @Override
  public void setBoolean(final String name, final boolean value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBooleanValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setBooleans(final String name, final boolean[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBooleanValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setChar(final String name, final char value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setCharValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setChars(final String name, final char[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setCharValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setByte(final String name, final byte value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setByteValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setBytes(final String name, final byte[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setByteValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setShort(final String name, final short value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setShortValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setShorts(final String name, final short[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setShortValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setInt(final String name, final int value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setIntValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setInts(final String name, final int[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setIntValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setLong(final String name, final long value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setLongValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setLongs(final String name, final long[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setLongValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setFloat(final String name, final float value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setFloatValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setFloats(final String name, final float[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setFloatValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setDouble(final String name, final double value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDoubleValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setDoubles(final String name, final double[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDoubleValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setString(final String name, @Nullable final String value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setStringValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setStrings(final String name, @Nullable final String[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setStringValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setBigDecimal(final String name, @Nullable final BigDecimal value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBigDecimalValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setBigDecimals(final String name, @Nullable final BigDecimal[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBigDecimalValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setBigInteger(final String name, @Nullable final BigInteger value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBigIntegerValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setBigIntegers(final String name, @Nullable final BigInteger[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setBigIntegerValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setDate(final String name, @Nullable final Date value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDateValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setDates(final String name, @Nullable final Date[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setDateValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setByteArray(final String name, @Nullable final byte[] value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setByteArrayValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setByteArrays(final String name, @Nullable final byte[][] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setByteArrayValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void setClass(final String name, @Nullable final Class<?> value, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setClassValue(value);
    prop.setFinal(isFinal);
  }

  @Override
  public void setClasses(final String name, @Nullable final Class<?>[] values, final boolean isFinal) {
    DefaultProperty prop = get(name);
    if (prop == null) {
      prop = add(name);
    }
    prop.setClassValues(values);
    prop.setFinal(isFinal);
  }

  @Override
  public void clear() {
    removeAll();
  }

}
