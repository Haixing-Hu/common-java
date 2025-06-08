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
 * {@link DefaultConfig} 类是 {@link Config} 接口的默认实现。
 *
 * @author 胡海星
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
   * 构造一个空的 {@link DefaultConfig} 对象。
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
   * 设置描述。
   *
   * @param description
   *     要设置的新描述，如果无则为 {@code null}。
   * @return
   *     此对象。
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

  /**
   * 设置指定的属性。
   *
   * @param property
   *     要设置的属性。
   * @return
   *     此对象。
   */
  public DefaultProperty set(final DefaultProperty property) {
    requireNonNull("property", property);
    return properties.put(property.getName(), property);
  }

  /**
   * 将指定{@link Config}对象的所有属性添加到此{@link DefaultConfig}对象中。
   *
   * @param config
   *     要添加其属性的{@link Config}对象。
   * @return
   *     此{@link DefaultConfig}对象。
   */
  public DefaultConfig addAll(final Config config) {
    requireNonNull("config", config);
    for (final Property prop : config.getProperties()) {
      add(prop);
    }
    return this;
  }

  /**
   * 将一个{@link Property}的集合添加到此{@link DefaultConfig}对象中。
   *
   * @param properties
   *     要添加的{@link Property}的集合。
   * @return
   *     此{@link DefaultConfig}对象。
   */
  public DefaultConfig addAll(final Collection<? extends Property> properties) {
    requireNonNull("properties", properties);
    for (final Property prop : properties) {
      add(prop);
    }
    return this;
  }

  /**
   * 将一个新的{@link Property}添加到此{@link DefaultConfig}对象中。
   *
   * @param prop
   *     要添加的新{@link Property}。
   * @return
   *     此{@link DefaultConfig}对象中具有相同名称的先前{@link Property}，
   *     如果没有具有相同名称的先前{@link Property}，则为null。
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
   * 向此{@link DefaultConfig}对象添加一个具有指定名称的新空属性。
   *
   * @param name
   *     新空属性的名称。
   * @return
   *     新添加的{@link DefaultProperty}对象。
   */
  public DefaultProperty add(final String name) {
    final DefaultProperty prop = new DefaultProperty(name);
    properties.put(name, prop);
    return prop;
  }

  /**
   * 向此{@link DefaultConfig}对象添加一个具有指定名称和类型的新空属性。
   *
   * @param name
   *     新空属性的名称。
   * @param type
   *     新空属性的类型。
   * @return
   *     新添加的{@link DefaultProperty}对象。
   */
  public DefaultProperty add(final String name, final Type type) {
    final DefaultProperty prop = new DefaultProperty(name, type);
    properties.put(name, prop);
    return prop;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Property remove(final String name) {
    return properties.remove(name);
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBoolean(final String name, final boolean value) {
    return setBoolean(name, value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBoolean(final String name, final boolean value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBooleans(final String name, @Nullable final boolean... values) {
    return setBooleans(name, values, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBooleans(final String name, @Nullable final boolean[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBooleans(final String name, @Nullable final BooleanCollection values) {
    return setBooleans(name, (values == null ? null : values.toArray()), false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBoolean(final String name, final boolean value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBooleans(final String name, @Nullable final boolean... values) {
    if (values != null) {
      requireNonNull("name", name);
      properties.compute(name, (k, v) -> {
        if (v == null) {
          v = new DefaultProperty(name);
          v.setBooleans(values);
        } else {
          v.addBooleanValues(values);
        }
        return v;
      });
    }
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBooleans(final String name, @Nullable final BooleanCollection values) {
    if (values != null) {
      requireNonNull("name", name);
      properties.compute(name, (k, v) -> {
        if (v == null) {
          v = new DefaultProperty(name);
          v.setBooleans(values);
        } else {
          v.addBooleanValues(values);
        }
        return v;
      });
    }
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setChar(final String name, final char value) {
    return setChar(name, value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setChar(final String name, final char value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setChars(final String name, @Nullable final char... values) {
    return setChars(name, values, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setChars(final String name, @Nullable final char[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setChars(final String name, @Nullable final CharCollection values) {
    return setChars(name, (values == null ? null : values.toArray()), false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addChar(final String name, final char value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addChars(final String name, @Nullable final char... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addChars(final String name, @Nullable final CharCollection values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setByte(final String name, final byte value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setByte(final String name, final byte value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBytes(final String name, @Nullable final byte... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBytes(final String name, @Nullable final byte[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBytes(final String name, @Nullable final ByteCollection values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addByte(final String name, final byte value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBytes(final String name, final byte... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBytes(final String name, final ByteCollection values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setShort(final String name, final short value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setShortValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setShort(final String name, final short value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setShorts(final String name, final short... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setShortValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setShorts(final String name, final short[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setShorts(final String name, final ShortCollection values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setShortValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addShort(final String name, final short value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addShorts(final String name, final short... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addShorts(final String name, final ShortCollection values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setInt(final String name, final int value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setIntValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setInt(final String name, final int value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setInts(final String name, final int... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setIntValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setInts(final String name, final int[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setInts(final String name, final IntCollection values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setIntValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addInt(final String name, final int value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addInts(final String name, final int... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addInts(final String name, final IntCollection values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setLong(final String name, final long value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setLongValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setLong(final String name, final long value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setLongs(final String name, final long... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setLongValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setLongs(final String name, final long[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setLongs(final String name, final LongCollection values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setLongValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addLong(final String name, final long value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addLongs(final String name, final long... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addLongs(final String name, final LongCollection values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setFloat(final String name, final float value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setFloatValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setFloat(final String name, final float value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setFloats(final String name, final float... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setFloatValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setFloats(final String name, final float[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setFloats(final String name, final FloatCollection values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setFloatValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addFloat(final String name, final float value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addFloats(final String name, final float... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addFloats(final String name, final FloatCollection values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDouble(final String name, final double value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDoubleValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDouble(final String name, final double value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDoubles(final String name, final double... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDoubleValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDoubles(final String name, final double[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDoubles(final String name, final DoubleCollection values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDoubleValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addDouble(final String name, final double value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addDoubles(final String name, final double... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addDoubles(final String name, final DoubleCollection values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setString(final String name, @Nullable final String value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setStringValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setString(final String name, @Nullable final String value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setStrings(final String name, @Nullable final String... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setStringValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setStrings(final String name, @Nullable final String[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setStrings(final String name, final Collection<String> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setStringValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addString(final String name, final String value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addStrings(final String name, @Nullable final String... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addStrings(final String name, final Collection<String> values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDate(final String name, @Nullable final LocalDate value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDate(final String name, @Nullable final LocalDate value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDates(final String name, @Nullable final LocalDate... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDates(final String name, @Nullable final LocalDate[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDates(final String name, final Collection<LocalDate> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addDate(final String name, final LocalDate value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addDates(final String name, final LocalDate... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addDates(final String name, final Collection<LocalDate> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setTime(final String name, @Nullable final LocalTime value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setTimeValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setTime(final String name, @Nullable final LocalTime value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setTimes(final String name, @Nullable final LocalTime... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setTimeValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setTimes(final String name, @Nullable final LocalTime[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setTimes(final String name, final Collection<LocalTime> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setTimeValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addTime(final String name, final LocalTime value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addTimeValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addTimes(final String name, final LocalTime... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addTimeValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addTimes(final String name, final Collection<LocalTime> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addTimeValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDateTime(final String name, @Nullable final LocalDateTime value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateTimeValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDateTime(final String name, @Nullable final LocalDateTime value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDateTimes(final String name, @Nullable final LocalDateTime... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateTimeValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDateTimes(final String name, @Nullable final LocalDateTime[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setDateTimes(final String name, final Collection<LocalDateTime> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setDateTimeValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addDateTime(final String name, final LocalDateTime value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateTimeValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addDateTimes(final String name, final LocalDateTime... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateTimeValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addDateTimes(final String name, final Collection<LocalDateTime> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addDateTimeValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBigInteger(final String name, @Nullable final BigInteger value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigIntegerValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBigInteger(final String name, @Nullable final BigInteger value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBigIntegers(final String name, @Nullable final BigInteger... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigIntegerValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBigIntegers(final String name, @Nullable final BigInteger[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBigIntegers(final String name, final Collection<BigInteger> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigIntegerValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBigInteger(final String name, final BigInteger value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBigIntegers(final String name, final BigInteger... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBigIntegers(final String name, final Collection<BigInteger> values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBigDecimal(final String name, @Nullable final BigDecimal value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigDecimalValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBigDecimal(final String name, @Nullable final BigDecimal value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBigDecimals(final String name, @Nullable final BigDecimal... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigDecimalValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBigDecimals(final String name, @Nullable final BigDecimal[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setBigDecimals(final String name, final Collection<BigDecimal> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setBigDecimalValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBigDecimal(final String name, final BigDecimal value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBigDecimals(final String name, final BigDecimal... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addBigDecimals(final String name, final Collection<BigDecimal> values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setByteArray(final String name, @Nullable final byte[] value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteArrayValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setByteArray(final String name, @Nullable final byte[] value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setByteArrays(final String name, final Collection<byte[]> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteArrayValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setByteArrays(final String name, @Nullable final byte[]... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setByteArrayValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setByteArrays(final String name, @Nullable final byte[][] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addByteArrays(final String name, final byte[]... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addByteArrays(final String name, final Collection<byte[]> values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addByteArray(final String name, final byte[] value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setEnum(final String name, @Nullable final Enum<?> value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setEnumValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setEnum(final String name, @Nullable final Enum<?> value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setEnums(final String name, @Nullable final Enum<?>... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setEnumValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setEnums(final String name, @Nullable final Enum<?>[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setEnums(final String name, @Nullable final Collection<? extends Enum<?>> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setEnumValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setEnums(final String name, @Nullable final Collection<? extends Enum<?>> values,
      final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addEnum(final String name, final Enum<?> value) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addEnums(final String name, final Enum<?>... values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addEnums(final String name, final Collection<? extends Enum<?>> values) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setClass(final String name, @Nullable final Class<?> value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setClassValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setClass(final String name, @Nullable final Class<?> value, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setClasses(final String name, @Nullable final Class<?>... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setClassValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setClasses(final String name, @Nullable final Class<?>[] values, final boolean isFinal) {
    requireNonNull("name", name);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig setClasses(final String name, final Collection<Class<?>> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.setClassValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addClass(final String name, final Class<?> value) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addClassValue(value);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addClasses(final String name, final Class<?>... values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addClassValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig addClasses(final String name, final Collection<Class<?>> values) {
    requireNonNull("name", name);
    properties.compute(name, (k, v) -> {
      if (v == null) {
        v = new DefaultProperty(name);
      }
      v.addClassValues(values);
      return v;
    });
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig merge(final Config config, final MergingPolicy policy) {
    return merge(config, StringUtils.EMPTY, policy);
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized DefaultConfig assign(final Config config) {
    requireNonNull("config", config);
    logger.trace("Start Assignment with another configuration ...");
    if (this != config) {
      logger.trace("Removing all properties ... ");
      clear();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized DefaultConfig assign(final Config config, final String prefix) {
    requireNonNull("config", config);
    requireNonNull("prefix", prefix);
    logger.trace("Start Assignment with another configuration ...");
    if (this != config) {
      logger.trace("Removing all properties ... ");
      clear();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public Property clear(final String name) {
    requireNonNull("name", name);
    return properties.computeIfPresent(name, (k, v) -> {
      v.clear();
      return v;
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig clear() {
    properties.clear();
    return this;
  }

  /**
   * {@inheritDoc}
   */
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
    if (!(obj instanceof DefaultConfig)) {
      return false;
    }
    final DefaultConfig other = (DefaultConfig) obj;
    return properties.equals(other.properties);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("properties", properties)
        .toString();
  }
}
