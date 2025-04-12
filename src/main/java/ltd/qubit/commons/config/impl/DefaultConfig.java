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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

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
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The {@link DefaultConfig} class is the default implementation of the {@link
 * Config} interface.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class DefaultConfig extends AbstractConfig implements WritableConfig {

  @Serial
  private static final long serialVersionUID = 3519879255214071861L;

  static {
    BinarySerialization.register(DefaultConfig.class, DefaultConfigBinarySerializer.INSTANCE);
    XmlSerialization.register(DefaultConfig.class, DefaultConfigXmlSerializer.INSTANCE);
  }

  protected volatile String description;
  protected ConcurrentHashMap<String, DefaultProperty> properties;

  /**
   * Constructs an empty {@link DefaultConfig} object.
   */
  public DefaultConfig() {
    description = null;
    properties = new ConcurrentHashMap<>();
  }

  @Override
  public synchronized String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description
   *     the new description to set, or {@code null} if none.
   */
  @Override
  public synchronized DefaultConfig setDescription(@Nullable final String description) {
    this.description = description;
    return this;
  }

  @Override
  public DefaultConfig setDescription(final String name,
      final String description) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDescription(description);
      return v;
    });
    return this;
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
    requireNonNull("name", name);
    return properties.get(name);
  }

  public DefaultProperty set(final DefaultProperty property) {
    requireNonNull("property", property);
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
    requireNonNull("config", config);
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
    requireNonNull("properties", properties);
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
    requireNonNull("prop", prop);
    final DefaultProperty newProp;
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
  public Property remove(final String name) {
    return properties.remove(name);
  }

  @Override
  public DefaultConfig setFinal(final String name, final boolean isFinal) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setType(final String name, final Type type) {
    requireNonNull("name", name);
    requireNonNull("type", type);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setType(type);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBoolean(final String name, final boolean value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBoolean(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBoolean(final String name, final boolean value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBoolean(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBooleans(final String name, @Nullable final boolean... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBooleans(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBooleans(final String name, @Nullable final boolean[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBooleans(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBooleans(final String name, @Nullable final BooleanCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBooleans(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBoolean(final String name, final boolean value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setBoolean(value);
      } else {
        v.addBooleanValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBooleans(final String name, @Nullable final boolean... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setBooleans(values);
      } else {
        v.addBooleanValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBooleans(final String name, @Nullable final BooleanCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setBooleans(values);
      } else {
        v.addBooleanValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setChar(final String name, final char value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setCharValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setChar(final String name, final char value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setCharValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setChars(final String name, @Nullable final char... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setCharValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setChars(final String name, @Nullable final char[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setCharValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setChars(final String name, @Nullable final CharCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setCharValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addChar(final String name, final char value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setCharValue(value);
      } else {
        v.addCharValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addChars(final String name, @Nullable final char... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setCharValues(values);
      } else {
        v.addCharValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addChars(final String name, @Nullable final CharCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setCharValues(values);
      } else {
        v.addCharValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setByte(final String name, final byte value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setByte(final String name, final byte value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBytes(final String name, @Nullable final byte... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBytes(final String name, @Nullable final byte[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBytes(final String name, @Nullable final ByteCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addByte(final String name, final byte value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setByteValue(value);
      } else {
        v.addByteValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBytes(final String name, final byte... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setByteValues(values);
      } else {
        v.addByteValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBytes(final String name, final ByteCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setByteValues(values);
      } else {
        v.addByteValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setShort(final String name, final short value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setShortValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setShort(final String name, final short value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setShortValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setShorts(final String name, final short... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setShortValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setShorts(final String name, final short[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setShortValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setShorts(final String name, final ShortCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setShortValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addShort(final String name, final short value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setShortValue(value);
      } else {
        v.addShortValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addShorts(final String name, final short... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setShortValues(values);
      } else {
        v.addShortValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addShorts(final String name, final ShortCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setShortValues(values);
      } else {
        v.addShortValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setInt(final String name, final int value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setIntValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setInt(final String name, final int value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setIntValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setInts(final String name, final int... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setIntValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setInts(final String name, final int[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setIntValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setInts(final String name, final IntCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setIntValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addInt(final String name, final int value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setIntValue(value);
      } else {
        v.addIntValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addInts(final String name, final int... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setIntValues(values);
      } else {
        v.addIntValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addInts(final String name, final IntCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setIntValues(values);
      } else {
        v.addIntValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setLong(final String name, final long value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setLongValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setLong(final String name, final long value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setLongValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setLongs(final String name, final long... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setLongValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setLongs(final String name, final long[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setLongValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setLongs(final String name, final LongCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setLongValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addLong(final String name, final long value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setLongValue(value);
      } else {
        v.addLongValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addLongs(final String name, final long... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setLongValues(values);
      } else {
        v.addLongValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addLongs(final String name, final LongCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setLongValues(values);
      } else {
        v.addLongValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setFloat(final String name, final float value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setFloatValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setFloat(final String name, final float value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setFloatValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setFloats(final String name, final float... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setFloatValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setFloats(final String name, final float[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setFloatValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setFloats(final String name, final FloatCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setFloatValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addFloat(final String name, final float value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setFloatValue(value);
      } else {
        v.addFloatValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addFloats(final String name, final float... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setFloatValues(values);
      } else {
        v.addFloatValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addFloats(final String name, final FloatCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setFloatValues(values);
      } else {
        v.addFloatValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDouble(final String name, final double value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDoubleValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDouble(final String name, final double value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDoubleValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDoubles(final String name, final double... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDoubleValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDoubles(final String name, final double[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDoubleValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDoubles(final String name, final DoubleCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDoubleValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addDouble(final String name, final double value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setDoubleValue(value);
      } else {
        v.addDoubleValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addDoubles(final String name, final double... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setDoubleValues(values);
      } else {
        v.addDoubleValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addDoubles(final String name, final DoubleCollection values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setDoubleValues(values);
      } else {
        v.addDoubleValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setString(final String name, @Nullable final String value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setStringValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setString(final String name, @Nullable final String value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setStringValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setStrings(final String name, @Nullable final String... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setStringValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setStrings(final String name, @Nullable final String[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setStringValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setStrings(final String name, final Collection<String> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setStringValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addString(final String name, final String value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setStringValue(value);
      } else {
        v.addStringValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addStrings(final String name, @Nullable final String... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setStringValues(values);
      } else {
        v.addStringValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addStrings(final String name, final Collection<String> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setStringValues(values);
      } else {
        v.addStringValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDate(final String name, @Nullable final LocalDate value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDate(final String name, @Nullable final LocalDate value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDates(final String name, @Nullable final LocalDate... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDates(final String name, @Nullable final LocalDate[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDates(final String name, final Collection<LocalDate> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addDate(final String name, final LocalDate value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addDates(final String name, final LocalDate... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addDates(final String name, final Collection<LocalDate> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setTime(final String name, @Nullable final LocalTime value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setTimeValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setTime(final String name, @Nullable final LocalTime value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setTimeValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setTimes(final String name, @Nullable final LocalTime... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setTimeValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setTimes(final String name, @Nullable final LocalTime[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setTimeValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setTimes(final String name, final Collection<LocalTime> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setTimeValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addTime(final String name, final LocalTime value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addTimeValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addTimes(final String name, final LocalTime... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addTimeValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addTimes(final String name, final Collection<LocalTime> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addTimeValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDateTime(final String name, @Nullable final LocalDateTime value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateTimeValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDateTime(final String name, @Nullable final LocalDateTime value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateTimeValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDateTimes(final String name, @Nullable final LocalDateTime... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateTimeValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDateTimes(final String name, @Nullable final LocalDateTime[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateTimeValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setDateTimes(final String name, final Collection<LocalDateTime> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateTimeValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addDateTime(final String name, final LocalDateTime value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateTimeValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addDateTimes(final String name, final LocalDateTime... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateTimeValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addDateTimes(final String name, final Collection<LocalDateTime> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateTimeValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBigInteger(final String name, @Nullable final BigInteger value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigIntegerValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBigInteger(final String name, @Nullable final BigInteger value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigIntegerValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBigIntegers(final String name, @Nullable final BigInteger... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigIntegerValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBigIntegers(final String name, @Nullable final BigInteger[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigIntegerValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBigIntegers(final String name, final Collection<BigInteger> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigIntegerValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBigInteger(final String name, final BigInteger value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setBigIntegerValue(value);
      } else {
        v.addBigIntegerValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBigIntegers(final String name, final BigInteger... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setBigIntegerValues(values);
      } else {
        v.addBigIntegerValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBigIntegers(final String name, final Collection<BigInteger> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setBigIntegerValues(values);
      } else {
        v.addBigIntegerValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBigDecimal(final String name, @Nullable final BigDecimal value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigDecimalValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBigDecimal(final String name, @Nullable final BigDecimal value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigDecimalValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBigDecimals(final String name, @Nullable final BigDecimal... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigDecimalValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBigDecimals(final String name, @Nullable final BigDecimal[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigDecimalValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setBigDecimals(final String name, final Collection<BigDecimal> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigDecimalValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBigDecimal(final String name, final BigDecimal value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setBigDecimalValue(value);
      } else {
        v.addBigDecimalValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBigDecimals(final String name, final BigDecimal... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setBigDecimalValues(values);
      } else {
        v.addBigDecimalValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addBigDecimals(final String name, final Collection<BigDecimal> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setBigDecimalValues(values);
      } else {
        v.addBigDecimalValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setByteArray(final String name, @Nullable final byte[] value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteArrayValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setByteArray(final String name, @Nullable final byte[] value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteArrayValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setByteArrays(final String name, final Collection<byte[]> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteArrayValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setByteArrays(final String name, @Nullable final byte[]... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteArrayValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setByteArrays(final String name, @Nullable final byte[][] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteArrayValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addByteArrays(final String name, final byte[]... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setByteArrayValues(values);
      } else {
        v.addByteArrayValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addByteArrays(final String name, final Collection<byte[]> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setByteArrayValues(values);
      } else {
        v.addByteArrayValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addByteArray(final String name, final byte[] value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setByteArrayValue(value);
      } else {
        v.addByteArrayValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setEnum(final String name, @Nullable final Enum<?> value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setEnumValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setEnum(final String name, @Nullable final Enum<?> value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setEnumValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setEnums(final String name, @Nullable final Enum<?>... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setEnumValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setEnums(final String name, @Nullable final Enum<?>[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setEnumValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setEnums(final String name, @Nullable final Collection<? extends Enum<?>> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setEnumValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setEnums(final String name, @Nullable final Collection<? extends Enum<?>> values,
      final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setEnumValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addEnum(final String name, final Enum<?> value) {
    requireNonNull("value", value);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setEnumValue(value);
      } else {
        v.addEnumValue(value);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addEnums(final String name, final Enum<?>... values) {
    requireNonNull("values", values);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setEnumValues(values);
      } else {
        v.addEnumValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addEnums(final String name, final Collection<? extends Enum<?>> values) {
    requireNonNull("values", values);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
        v.setEnumValues(values);
      } else {
        v.addEnumValues(values);
      }
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setClass(final String name, @Nullable final Class<?> value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setClassValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setClass(final String name, @Nullable final Class<?> value, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setClassValue(value);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setClasses(final String name, @Nullable final Class<?>... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setClassValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setClasses(final String name, @Nullable final Class<?>[] values, final boolean isFinal) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setClassValues(values);
      v.setFinal(isFinal);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig setClasses(final String name, final Collection<Class<?>> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setClassValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addClass(final String name, final Class<?> value) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addClassValue(value);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addClasses(final String name, final Class<?>... values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addClassValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig addClasses(final String name, final Collection<Class<?>> values) {
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addClassValues(values);
      return v;
    });
    return this;
  }

  @Override
  public DefaultConfig merge(final Config config, final MergingPolicy policy) {
    return merge(config, StringUtils.EMPTY, policy);
  }

  @Override
  public synchronized DefaultConfig merge(final Config config, final String prefix,
      final MergingPolicy policy) {
    requireNonNull("config", config);
    requireNonNull("prefix", prefix);
    requireNonNull("policy", policy);
    if (this == config) {
      return this;
    }
    logger.trace("Start merging using policy {} ....", policy);
    switch (policy) {
      case SKIP:
        for (final Property thatProp : config.getProperties()) {
          final String name = thatProp.getName();
          if (!name.startsWith(prefix)) {
            continue;
          }
          if (contains(name)) {
            logger.trace("Skip the existing property '{}'.", name);
          } else {
            logger.trace("Adding a new property '{}'.", name);
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
            logger.trace("Adding a new property '{}'.", name);
            thisProp = add(name);
            thisProp.assign(thatProp);
          } else if (thisProp.isFinal) {
            logger.trace("Skip the final property '{}'.", name);
          } else {
            if (thisProp.getType() != thatProp.getType()) {
              logger.trace("Overwrite a existing property '{}'.", name);
              thisProp.assign(thatProp);
            } else {
              logger.trace("Union a existing property '{}'.", name);
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
            logger.trace("Adding a new property '{}'.", name);
            thisProp = add(name);
            thisProp.assign(thatProp);
          } else if (thisProp.isFinal()) {
            logger.trace("Skip the final property '{}'.", name);
          } else {
            logger.trace("Overwrite a existing property '{}'.", name);
            thisProp.assign(thatProp);
          }
        }
        break;
    }
    logger.trace("Merging finished.");
    return this;
  }

  @Override
  public synchronized DefaultConfig assign(final Config config) {
    logger.trace("Start Assignment with another configuration ...");
    if (this != config) {
      logger.trace("Removing all properties ... ");
      removeAll();
      for (final Property thatProp : config.getProperties()) {
        final String name = thatProp.getName();
        logger.trace("Adding a new property '{}'.", name);
        final DefaultProperty thisProp = add(name);
        thisProp.assign(thatProp);
      }
    }
    logger.trace("Assignment finished.");
    return this;
  }

  @Override
  public synchronized DefaultConfig assign(final Config config, final String prefix) {
    logger.trace("Start Assignment with another configuration ...");
    if (this != config) {
      logger.trace("Removing all properties ... ");
      removeAll();
      for (final Property thatProp : config.getProperties()) {
        final String name = thatProp.getName();
        if (name.startsWith(prefix)) {
          logger.trace("Adding a new property '{}'.", name);
          final DefaultProperty thisProp = add(name);
          thisProp.assign(thatProp);
        }
      }
    } else {
      // this == config, just remove all properties not starting with prefix
      for (final String name : getNames()) {
        if (!name.startsWith(prefix)) {
          logger.trace("Removing the property '{}'.", name);
          remove(name);
        }
      }
    }
    logger.trace("Assignment finished.");
    return this;
  }

  @Override
  public Property clear(final String name) {
    return properties.computeIfPresent(name, (k, v) -> {
      v.clear();
      return v;
    });
  }

  @Override
  public DefaultConfig clear() {
    return removeAll();
  }

  public DefaultConfig removeAll() {
    properties.clear();
    return this;
  }

  @Override
  public synchronized Config cloneEx() {
    final DefaultConfig result = new DefaultConfig();
    for (final DefaultProperty prop : properties.values()) {
      result.properties.put(prop.getName(), prop.cloneEx());
    }
    return result;
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
  public String toString() {
    return new ToStringBuilder(this)
        .append("properties", properties)
        .toString();
  }
}