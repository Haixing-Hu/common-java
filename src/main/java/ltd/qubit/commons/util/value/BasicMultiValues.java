////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.BooleanList;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteList;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharList;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleList;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatList;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongList;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortList;
import ltd.qubit.commons.datastructure.list.primitive.impl.BooleanArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.ByteArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.CharArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.DoubleArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.FloatArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.IntArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.LongArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.ShortArrayList;
import ltd.qubit.commons.error.TypeConvertException;
import ltd.qubit.commons.error.TypeMismatchException;
import ltd.qubit.commons.error.UnsupportedDataTypeException;
import ltd.qubit.commons.io.error.InvalidFormatException;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.lang.TypeUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.XmlException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

public class BasicMultiValues implements MultiValues, Serializable {

  @Serial
  private static final long serialVersionUID = 1158197073979656745L;

  static {
    XmlSerialization.register(BasicMultiValues.class,
        BasicMultiValuesXmlSerializer.INSTANCE);
  }

  protected Type type;
  protected int count;
  protected Object valueOrValues;

  public BasicMultiValues() {
    type = DEFAULT_TYPE;
    count = 0;
    valueOrValues = null;
  }

  public BasicMultiValues(final Type type) {
    this.type = requireNonNull("type", type);
    count = 0;
    valueOrValues = null;
  }

  public BasicMultiValues(final boolean value) {
    this();
    setBoolean(value);
  }

  public BasicMultiValues(final char value) {
    this();
    setCharValue(value);
  }

  public BasicMultiValues(final byte value) {
    this();
    setByteValue(value);
  }

  public BasicMultiValues(final short value) {
    this();
    setShortValue(value);
  }

  public BasicMultiValues(final int value) {
    this();
    setIntValue(value);
  }

  public BasicMultiValues(final long value) {
    this();
    setLongValue(value);
  }

  public BasicMultiValues(final float value) {
    this();
    setFloatValue(value);
  }

  public BasicMultiValues(final double value) {
    this();
    setDoubleValue(value);
  }

  public BasicMultiValues(final String value) {
    this();
    setStringValue(value);
  }

  public BasicMultiValues(final LocalDate value) {
    this();
    setDateValue(value);
  }

  public BasicMultiValues(final LocalTime value) {
    this();
    setTimeValue(value);
  }

  public BasicMultiValues(final LocalDateTime value) {
    this();
    setDateTimeValue(value);
  }

  public BasicMultiValues(final BigInteger value) {
    this();
    setBigIntegerValue(value);
  }

  public BasicMultiValues(final BigDecimal value) {
    this();
    setBigDecimalValue(value);
  }

  public BasicMultiValues(final byte[] value) {
    this();
    setByteArrayValue(value);
  }

  public BasicMultiValues(final Class<?> value) {
    this();
    setClassValue(value);
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public void setType(final Type type) {
    if (this.type != type) {
      this.type = requireNonNull("type", type);
      count = 0;
      valueOrValues = null;
    }
  }

  @Override
  public int getCount() {
    return count;
  }

  @Override
  public boolean isEmpty() {
    return (count == 0);
  }

  @Override
  public void clear() {
    count = 0;
    valueOrValues = null;
  }

  @Override
  public void assignValues(final MultiValues other) {
    if (this == other) {
      return;
    }
    final Type otherType = other.getType();
    final int otherCount = other.getCount();
    if (otherCount == 0) {
      this.type = otherType;
      valueOrValues = null;
      this.count = 0;
    } else if (otherCount == 1) {
      assignSingleValue(otherType, other);
    } else {
      assignMultiValues(otherType, other);
    }
  }

  private void assignSingleValue(final Type type, final MultiValues other) {
    switch (type) {
      case BOOL: {
        final boolean value = other.getBoolean();
        this.type = Type.BOOL;
        valueOrValues = value;
        count = 1;
        return;
      }
      case CHAR: {
        final char value = other.getCharValue();
        this.type = Type.CHAR;
        valueOrValues = value;
        count = 1;
        return;
      }
      case BYTE: {
        final byte value = other.getByteValue();
        this.type = Type.BYTE;
        valueOrValues = value;
        count = 1;
        return;
      }
      case SHORT: {
        final short value = other.getShortValue();
        this.type = Type.SHORT;
        valueOrValues = value;
        count = 1;
        return;
      }
      case INT: {
        final int value = other.getIntValue();
        this.type = Type.INT;
        valueOrValues = value;
        count = 1;
        return;
      }
      case LONG: {
        final long value = other.getLongValue();
        this.type = Type.LONG;
        valueOrValues = value;
        count = 1;
        return;
      }
      case FLOAT: {
        final float value = other.getFloatValue();
        this.type = Type.FLOAT;
        valueOrValues = value;
        count = 1;
        return;
      }
      case DOUBLE: {
        final double value = other.getDoubleValue();
        this.type = Type.DOUBLE;
        valueOrValues = value;
        count = 1;
        return;
      }
      case STRING: {
        final String value = other.getStringValue();
        this.type = Type.STRING;
        valueOrValues = value;
        count = 1;
        return;
      }
      case DATE: {
        final LocalDate value = other.getDateValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object of `other`.
        this.type = Type.DATE;
        valueOrValues = value;
        count = 1;
        return;
      }
      case BYTE_ARRAY: {
        final byte[] value = other.getByteArrayValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object of `other`.
        this.type = Type.BYTE_ARRAY;
        valueOrValues = value;
        count = 1;
        return;
      }
      case CLASS: {
        final Class<?> value = other.getClassValue();
        this.type = Type.CLASS;
        valueOrValues = value;
        count = 1;
        return;
      }
      case BIG_INTEGER: {
        final BigInteger value = other.getBigIntegerValue();
        this.type = Type.BIG_INTEGER;
        valueOrValues = value;
        count = 1;
        return;
      }
      case BIG_DECIMAL: {
        final BigDecimal value = other.getBigDecimalValue();
        this.type = Type.BIG_DECIMAL;
        valueOrValues = value;
        count = 1;
        return;
      }
      default:
        throw new UnsupportedDataTypeException(type);
    }
  }

  private void assignMultiValues(final Type type, final MultiValues other) {
    switch (type) {
      case BOOL: {
        final boolean[] values = other.getBooleans();
        addMultipleBooleanValuesImpl(values);
        return;
      }
      case CHAR: {
        final char[] values = other.getCharValues();
        addMultipleCharValuesImpl(values);
        return;
      }
      case BYTE: {
        final byte[] values = other.getByteValues();
        addMultipleByteValuesImpl(values);
        return;
      }
      case SHORT: {
        final short[] values = other.getShortValues();
        addMultipleShortValuesImpl(values);
        return;
      }
      case INT: {
        final int[] values = other.getIntValues();
        addMultipleIntValuesImpl(values);
        return;
      }
      case LONG: {
        final long[] values = other.getLongValues();
        addMultipleLongValuesImpl(values);
        return;
      }
      case FLOAT: {
        final float[] values = other.getFloatValues();
        addMultipleFloatsImpl(values);
        return;
      }
      case DOUBLE: {
        final double[] values = other.getDoubleValues();
        addMultipleDoubleValuesImpl(values);
        return;
      }
      case STRING: {
        final String[] values = other.getStringValues();
        addMultipleStringValuesImpl(values);
        return;
      }
      case DATE: {
        final LocalDate[] values = other.getDateValues();
        // don't need to clone the value, since it is already the cloned
        // copy of the object of `other`.
        addMultipleDateValuesImpl(values);
        return;
      }
      case BYTE_ARRAY: {
        final byte[][] values = other.getByteArrayValues();
        // don't need to clone the value, since it is already the cloned
        // copy of the object of `other`.
        addMultipleByteArrayValuesImpl(false, values);
        return;
      }
      case CLASS: {
        final Class<?>[] values = other.getClassValues();
        addMultipleClassValuesImpl(values);
        return;
      }
      case BIG_INTEGER: {
        final BigInteger[] values = other.getBigIntegerValues();
        addMultipleBigIntegerValuesImpl(values);
        return;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = other.getBigDecimalValues();
        addMultipleBigDecimalValuesImpl(values);
        return;
      }
      default:
        throw new UnsupportedDataTypeException(type);
    }
  }

  @Override
  public void unionValues(final MultiValues other) throws TypeMismatchException {
    if (this == other) {
      return;
    }
    final Type other_type = other.getType();
    if (type != other_type) {
      throw new TypeMismatchException(type, other_type);
    }
    final int other_count = other.getCount();
    if (other_count == 1) {
      unionSingleValueImpl(type, other);
    } else if (other_count > 1) {
      unionMultiValuesImpl(type, other);
    }
  }

  private void unionSingleValueImpl(final Type type, final MultiValues other) {
    switch (type) {
      case BOOL: {
        final boolean other_value = other.getBoolean();
        addSingleBooleanValueImpl(other_value);
        return;
      }
      case CHAR: {
        final char other_value = other.getCharValue();
        addMultipleCharValuesImpl(other_value);
        return;
      }
      case BYTE: {
        final byte other_value = other.getByteValue();
        addSingleByteValueImpl(other_value);
        return;
      }
      case SHORT: {
        final short other_value = other.getShortValue();
        addSingleShortValueImpl(other_value);
        return;
      }
      case INT: {
        final int other_value = other.getIntValue();
        addSingleIntValueImpl(other_value);
        return;
      }
      case LONG: {
        final long other_value = other.getLongValue();
        addSingleLongValueImpl(other_value);
        return;
      }
      case FLOAT: {
        final float other_value = other.getFloatValue();
        addSingleFloatValueImpl(other_value);
        return;
      }
      case DOUBLE: {
        final double other_value = other.getDoubleValue();
        addSingleDoubleValueImpl(other_value);
        return;
      }
      case STRING: {
        final String other_value = other.getStringValue();
        addSingleStringValueImpl(other_value);
        return;
      }
      case DATE: {
        final LocalDate other_value = other.getDateValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object of `other`.
        addSingleDateValueImpl(other_value);
        return;
      }
      case BYTE_ARRAY: {
        final byte[] other_value = other.getByteArrayValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object of `other`.
        addSingleByteArrayValueImpl(false, other_value);
        return;
      }
      case CLASS: {
        final Class<?> other_value = other.getClassValue();
        addSingleClassValueImpl(other_value);
        return;
      }
      case BIG_INTEGER: {
        final BigInteger other_value = other.getBigIntegerValue();
        addSingleBigIntegerValueImpl(other_value);
        return;
      }
      case BIG_DECIMAL: {
        final BigDecimal other_value = other.getBigDecimalValue();
        addSingleBigDecimalValueImpl(other_value);
        return;
      }
      default:
        throw new UnsupportedDataTypeException(type);
    }
  }

  private void unionMultiValuesImpl(final Type type, final MultiValues other) {
    switch (type) {
      case BOOL: {
        final boolean[] other_values = other.getBooleans();
        addMultipleBooleanValuesImpl(other_values);
        return;
      }
      case CHAR: {
        final char[] other_values = other.getCharValues();
        addMultipleCharValuesImpl(other_values);
        return;
      }
      case BYTE: {
        final byte[] other_values = other.getByteValues();
        addMultipleByteValuesImpl(other_values);
        return;
      }
      case SHORT: {
        final short[] other_values = other.getShortValues();
        addMultipleShortValuesImpl(other_values);
        return;
      }
      case INT: {
        final int[] other_values = other.getIntValues();
        addMultipleIntValuesImpl(other_values);
        return;
      }
      case LONG: {
        final long[] other_values = other.getLongValues();
        addMultipleLongValuesImpl(other_values);
        return;
      }
      case FLOAT: {
        final float[] other_values = other.getFloatValues();
        addMultipleFloatsImpl(other_values);
        return;
      }
      case DOUBLE: {
        final double[] other_values = other.getDoubleValues();
        addMultipleDoubleValuesImpl(other_values);
        return;
      }
      case STRING: {
        final String[] other_values = other.getStringValues();
        addMultipleStringValuesImpl(other_values);
        return;
      }
      case DATE: {
        final LocalDate[] other_values = other.getDateValues();
        // don't need to clone the value, since it is already the cloned
        // copy of the object of `other`.
        addMultipleDateValuesImpl(other_values);
        return;
      }
      case BYTE_ARRAY: {
        final byte[][] other_values = other.getByteArrayValues();
        // don't need to clone the value, since it is already the cloned
        // copy of the object of `other`.
        addMultipleByteArrayValuesImpl(false, other_values);
        return;
      }
      case CLASS: {
        final Class<?>[] other_values = other.getClassValues();
        addMultipleClassValuesImpl(other_values);
        return;
      }
      case BIG_INTEGER: {
        final BigInteger[] other_values = other.getBigIntegerValues();
        addMultipleBigIntegerValuesImpl(other_values);
        return;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] other_values = other.getBigDecimalValues();
        addMultipleBigDecimalValuesImpl(other_values);
        return;
      }
      default:
        throw new UnsupportedDataTypeException(type);
    }
  }

  @Override
  public void readValues(final Type type, final int n, final InputStream in)
      throws IOException {
    requireNonNull("type", type);
    try {
      if (n == 0) {
        this.type = type;
        count = 1;
      } else if (n == 1) {
        final Object value = TypeUtils.readObject(type, in);
        this.type = type;
        valueOrValues = value;
        count = 1;
      } else if (n > 1) {
        final Object values = TypeUtils.readList(type, n, in);
        this.type = type;
        valueOrValues = values;
        count = n;
      }
    } catch (final UnsupportedDataTypeException | ClassNotFoundException e) {
      throw new InvalidFormatException(e);
    }
  }

  @Override
  public void writeValues(final OutputStream out) throws IOException {
    if (count == 1) {
      TypeUtils.writeObject(type, valueOrValues, out);
    } else if (count > 1) {
      TypeUtils.writeCollection(type, valueOrValues, out);
    }
  }

  @Override
  public void getValuesFromXml(final Type type, final Element parent,
      final String tagName, @Nullable final String prevSpaceAttr)
      throws XmlException {
    requireNonNull("type", type);
    final List<Element> children = DomUtils.getChildren(parent, tagName, null);
    if ((children == null) || children.isEmpty()) {
      this.type = type;
      valueOrValues = null;
      count = 0;
    } else if (children.size() == 1) {
      final Element node = children.iterator().next();
      final Object value = TypeUtils.fromXmlNode(type, node, prevSpaceAttr);
      this.type = type;
      valueOrValues = value;
      count = 1;
    } else { // children.size() > 1
      final Object values = TypeUtils.fromXmlNodes(type, children,
          prevSpaceAttr);
      this.type = type;
      valueOrValues = values;
      count = children.size();
    }
  }

  @Override
  public void appendValuesToXml(final Document doc, final Element parent,
      final String containerName, final String tagName,
      @Nullable final String prevSpaceAttr) {
    if (count == 1) {
      final Element node = TypeUtils.toXmlNode(type, valueOrValues, doc,
          tagName, prevSpaceAttr);
      parent.appendChild(node);
    } else if (count > 1) {
      final List<Element> nodes = TypeUtils.toXmlNodes(type, valueOrValues,
          doc, parent, containerName, tagName, prevSpaceAttr);
      for (final Element node : nodes) {
        parent.appendChild(node);
      }
    }
  }

  @Override
  public boolean getBoolean() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BOOL) {
      throw new TypeMismatchException(Type.BOOL.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Boolean) valueOrValues;
    } else {
      final BooleanList values = (BooleanList) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setBoolean(final boolean value) {
    type = Type.BOOL;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public boolean[] getBooleans() throws TypeMismatchException {
    if (type != Type.BOOL) {
      throw new TypeMismatchException(Type.BOOL.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    } else if (count == 1) {
      final Boolean value = (Boolean) valueOrValues;
      return new boolean[]{ value };
    } else { // count > 1
      final BooleanList values = (BooleanList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setBooleans(@Nullable final boolean... values) {
    if (values == null || values.length == 0) {
      type = Type.BOOL;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final boolean value = values[0];
      type = Type.BOOL;
      valueOrValues = value;
      count = 1;
    } else {
      final BooleanList list = new BooleanArrayList(values);
      type = Type.BOOL;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setBooleans(@Nullable final BooleanCollection values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.BOOL;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final boolean value = values.iterator().next();
      type = Type.BOOL;
      valueOrValues = value;
      count = 1;
    } else {
      final BooleanList list = new BooleanArrayList(values);
      type = Type.BOOL;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addBooleanValue(final boolean value) throws TypeMismatchException {
    if ((type != Type.BOOL) && (count > 0)) {
      throw new TypeMismatchException(Type.BOOL.name(), type.name());
    }
    addSingleBooleanValueImpl(value);
  }

  private void addSingleBooleanValueImpl(final boolean value) {
    if (count == 0) {
      type = Type.BOOL;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final boolean oldValue = (Boolean) valueOrValues;
      final BooleanList list = new BooleanArrayList(2);
      list.add(oldValue);
      list.add(value);
      type = Type.BOOL;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      final BooleanList list = (BooleanList) valueOrValues;
      list.add(value);
      type = Type.BOOL;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addBooleanValues(@Nullable final boolean... values)
      throws TypeMismatchException {
    if ((type != Type.BOOL) && (count > 0)) {
      throw new TypeMismatchException(Type.BOOL.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final boolean value = values[0];
        addSingleBooleanValueImpl(value);
      } else if (values.length > 1) {
        addMultipleBooleanValuesImpl(values);
      }
    }
  }

  @Override
  public void addBooleanValues(@Nullable final BooleanCollection values)
      throws TypeMismatchException {
    if ((type != Type.BOOL) && (count > 0)) {
      throw new TypeMismatchException(Type.BOOL.name(), type.name());
    }
    if (values != null) {
      if (count == 1) {
        final boolean value = values.iterator().next();
        addSingleBooleanValueImpl(value);
      } else if (count > 1) {
        addMultipleBooleanValuesImpl(values);
      }
    }
  }

  private void addMultipleBooleanValuesImpl(final boolean... values) {
    if (count == 0) {
      final BooleanList list = new BooleanArrayList(values);
      type = Type.BOOL;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final boolean oldValue = (Boolean) valueOrValues;
      final BooleanList list = new BooleanArrayList();
      list.add(oldValue);
      for (final boolean value : values) {
        list.add(value);
      }
      type = Type.BOOL;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final BooleanList list = (BooleanList) valueOrValues;
      for (final boolean value : values) {
        list.add(value);
      }
      type = Type.BOOL;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleBooleanValuesImpl(final BooleanCollection values) {
    if (count == 0) {
      final BooleanList list = new BooleanArrayList(values);
      type = Type.BOOL;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final boolean oldValue = (Boolean) valueOrValues;
      final BooleanList list = new BooleanArrayList();
      list.add(oldValue);
      list.addAll(values);
      type = Type.BOOL;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final BooleanList list = (BooleanList) valueOrValues;
      list.addAll(values);
      type = Type.BOOL;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public char getCharValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.CHAR) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Character) valueOrValues;
    } else {
      final CharList values = (CharList) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setCharValue(final char value) {
    type = Type.CHAR;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public char[] getCharValues() throws TypeMismatchException {
    if (type != Type.CHAR) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    } else if (count == 1) {
      final Character value = (Character) valueOrValues;
      return new char[]{ value };
    } else { // count > 1
      final CharList values = (CharList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setCharValues(@Nullable final char... values) {
    if (values == null || values.length == 0) {
      type = Type.CHAR;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final char value = values[0];
      type = Type.CHAR;
      valueOrValues = value;
      count = 1;
    } else {
      final CharList list = new CharArrayList(values);
      type = Type.CHAR;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setCharValues(@Nullable final CharCollection values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.CHAR;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final char value = values.iterator().next();
      type = Type.CHAR;
      valueOrValues = value;
      count = 1;
    } else {
      final CharList list = new CharArrayList(values);
      type = Type.CHAR;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addCharValue(final char value) throws TypeMismatchException {
    if ((type != Type.CHAR) && (count > 0)) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    addSingleCharValueImpl(value);
  }

  private void addSingleCharValueImpl(final char value) {
    if (count == 0) {
      type = Type.CHAR;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final char oldValue = (Character) valueOrValues;
      final CharList list = new CharArrayList(2);
      list.add(oldValue);
      list.add(value);
      type = Type.CHAR;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      final CharList list = (CharList) valueOrValues;
      list.add(value);
      type = Type.CHAR;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addCharValues(@Nullable final char... values) throws TypeMismatchException {
    if ((type != Type.CHAR) && (count > 0)) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final char value = values[0];
        addSingleCharValueImpl(value);
      } else if (values.length > 1) {
        addMultipleCharValuesImpl(values);
      }
    }
  }

  @Override
  public void addCharValues(@Nullable final CharCollection values)
      throws TypeMismatchException {
    if ((type != Type.CHAR) && (count > 0)) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    if (values != null) {
      if (count == 1) {
        final char value = values.iterator().next();
        addSingleCharValueImpl(value);
      } else if (count > 1) {
        addMultipleCharValuesImpl(values);
      }
    }
  }

  private void addMultipleCharValuesImpl(final char... values) {
    if (count == 0) {
      final CharList list = new CharArrayList(values);
      type = Type.CHAR;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final char oldValue = (Character) valueOrValues;
      final CharList list = new CharArrayList();
      list.add(oldValue);
      for (final char value : values) {
        list.add(value);
      }
      type = Type.CHAR;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final CharList list = (CharList) valueOrValues;
      for (final char value : values) {
        list.add(value);
      }
      type = Type.CHAR;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleCharValuesImpl(final CharCollection values) {
    if (count == 0) {
      final CharList list = new CharArrayList(values);
      type = Type.CHAR;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final char oldValue = (Character) valueOrValues;
      final CharList list = new CharArrayList();
      list.add(oldValue);
      list.addAll(values);
      type = Type.CHAR;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final CharList list = (CharList) valueOrValues;
      list.addAll(values);
      type = Type.CHAR;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public byte getByteValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BYTE) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Byte) valueOrValues;
    } else {  // count > 1
      final ByteList values = (ByteList) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setByteValue(final byte value) {
    type = Type.BYTE;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public byte[] getByteValues() throws TypeMismatchException {
    if (type != Type.BYTE) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else if (count == 1) {
      final Byte value = (Byte) valueOrValues;
      return new byte[]{ value };
    } else { // count > 1
      final ByteList values = (ByteList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setByteValues(@Nullable final byte... values) {
    if (values == null || values.length == 0) {
      type = Type.BYTE;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final byte value = values[0];
      type = Type.BYTE;
      valueOrValues = value;
      count = 1;
    } else {
      final ByteList list = new ByteArrayList(values);
      type = Type.BYTE;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setByteValues(@Nullable final ByteCollection values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.BYTE;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final byte value = values.iterator().next();
      type = Type.BYTE;
      valueOrValues = value;
      count = 1;
    } else {
      final ByteList list = new ByteArrayList(values);
      type = Type.BYTE;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addByteValue(final byte value) throws TypeMismatchException {
    if ((type != Type.BYTE) && (count > 0)) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    addSingleByteValueImpl(value);
  }

  private void addSingleByteValueImpl(final byte value) {
    if (count == 0) {
      type = Type.BYTE;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final byte oldValue = (Byte) valueOrValues;
      final ByteList list = new ByteArrayList(2);
      list.add(oldValue);
      list.add(value);
      type = Type.BYTE;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      final ByteList list = (ByteList) valueOrValues;
      list.add(value);
      type = Type.BYTE;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addByteValues(@Nullable final byte... values) throws TypeMismatchException {
    if ((type != Type.BYTE) && (count > 0)) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final byte value = values[0];
        addSingleByteValueImpl(value);
      } else if (values.length > 1) {
        addMultipleByteValuesImpl(values);
      }
    }
  }

  @Override
  public void addByteValues(@Nullable final ByteCollection values)
      throws TypeMismatchException {
    if ((type != Type.BYTE) && (count > 0)) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final byte value = values.iterator().next();
      addSingleByteValueImpl(value);
    } else if (n > 1) {
      addMultipleByteValuesImpl(values);
    }
  }

  private void addMultipleByteValuesImpl(final byte... values) {
    if (count == 0) {
      final ByteList list = new ByteArrayList(values);
      type = Type.BYTE;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final byte oldValue = (Byte) valueOrValues;
      final ByteList list = new ByteArrayList();
      list.add(oldValue);
      for (final byte value : values) {
        list.add(value);
      }
      type = Type.BYTE;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final ByteList list = (ByteList) valueOrValues;
      for (final byte value : values) {
        list.add(value);
      }
      type = Type.BYTE;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleByteValuesImpl(final ByteCollection values) {
    if (count == 0) {
      final ByteList list = new ByteArrayList(values);
      type = Type.BYTE;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final byte oldValue = (Byte) valueOrValues;
      final ByteList list = new ByteArrayList();
      list.add(oldValue);
      list.addAll(values);
      type = Type.BYTE;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final ByteList list = (ByteList) valueOrValues;
      list.addAll(values);
      type = Type.BYTE;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public short getShortValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.SHORT) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Short) valueOrValues;
    } else {
      final ShortList values = (ShortList) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setShortValue(final short value) {
    type = Type.SHORT;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public short[] getShortValues() throws TypeMismatchException {
    if (type != Type.SHORT) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    } else if (count == 1) {
      final Short value = (Short) valueOrValues;
      return new short[]{ value };
    } else { // count > 1
      final ShortList values = (ShortList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setShortValues(@Nullable final short... values) {
    if (values == null || values.length == 0) {
      type = Type.SHORT;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final short value = values[0];
      type = Type.SHORT;
      valueOrValues = value;
      count = 1;
    } else {
      final ShortList list = new ShortArrayList(values);
      type = Type.SHORT;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setShortValues(@Nullable final ShortCollection values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.SHORT;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final short value = values.iterator().next();
      type = Type.SHORT;
      valueOrValues = value;
      count = 1;
    } else {
      final ShortList list = new ShortArrayList(values);
      type = Type.SHORT;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addShortValue(final short value) throws TypeMismatchException {
    if ((type != Type.SHORT) && (count > 0)) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    addSingleShortValueImpl(value);
  }

  private void addSingleShortValueImpl(final short value) {
    if (count == 0) {
      type = Type.SHORT;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final short oldValue = (Short) valueOrValues;
      final ShortList list = new ShortArrayList(2);
      list.add(oldValue);
      list.add(value);
      type = Type.SHORT;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      final ShortList list = (ShortList) valueOrValues;
      list.add(value);
      type = Type.SHORT;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addShortValues(@Nullable final short... values)
      throws TypeMismatchException {
    if ((type != Type.SHORT) && (count > 0)) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    if (values != null) {
    if (values.length == 1) {
      final short value = values[0];
      addSingleShortValueImpl(value);
    } else if (values.length > 1) {
      addMultipleShortValuesImpl(values);
    }
    }
  }

  @Override
  public void addShortValues(@Nullable final ShortCollection values)
      throws TypeMismatchException {
    if ((type != Type.SHORT) && (count > 0)) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final short value = values.iterator().next();
      addSingleShortValueImpl(value);
    } else if (n > 1) {
      addMultipleShortValuesImpl(values);
    }
  }

  private void addMultipleShortValuesImpl(final short... values) {
    if (count == 0) {
      final ShortList list = new ShortArrayList(values);
      type = Type.SHORT;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final short oldValue = (Short) valueOrValues;
      final ShortList list = new ShortArrayList();
      list.add(oldValue);
      for (final short value : values) {
        list.add(value);
      }
      type = Type.SHORT;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final ShortList list = (ShortList) valueOrValues;
      for (final short value : values) {
        list.add(value);
      }
      type = Type.SHORT;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleShortValuesImpl(final ShortCollection values) {
    if (count == 0) {
      final ShortList list = new ShortArrayList(values);
      type = Type.SHORT;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final short oldValue = (Short) valueOrValues;
      final ShortList list = new ShortArrayList();
      list.add(oldValue);
      list.addAll(values);
      type = Type.SHORT;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final ShortList list = (ShortList) valueOrValues;
      list.addAll(values);
      type = Type.SHORT;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public int getIntValue() throws TypeMismatchException, NoSuchElementException {
    if (type != Type.INT) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Integer) valueOrValues;
    } else {  //  count > 1
      final IntList values = (IntList) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setIntValue(final int value) {
    type = Type.INT;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public int[] getIntValues() throws TypeMismatchException {
    if (type != Type.INT) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else if (count == 1) {
      final Integer value = (Integer) valueOrValues;
      return new int[]{ value };
    } else { // count > 1
      final IntList values = (IntList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setIntValues(@Nullable final int... values) {
    if (values == null || values.length == 0) {
      type = Type.INT;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final int value = values[0];
      type = Type.INT;
      valueOrValues = value;
      count = 1;
    } else {
      final IntList list = new IntArrayList(values);
      type = Type.INT;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setIntValues(@Nullable final IntCollection values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.INT;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final int value = values.iterator().next();
      type = Type.INT;
      valueOrValues = value;
      count = 1;
    } else {
      final IntList list = new IntArrayList(values);
      type = Type.INT;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addIntValue(final int value) throws TypeMismatchException {
    if ((type != Type.INT) && (count > 0)) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    addSingleIntValueImpl(value);
  }

  private void addSingleIntValueImpl(final int value) {
    if (count == 0) {
      type = Type.INT;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final int oldValue = (Integer) valueOrValues;
      final IntList list = new IntArrayList(2);
      list.add(oldValue);
      list.add(value);
      type = Type.INT;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      final IntList list = (IntList) valueOrValues;
      list.add(value);
      type = Type.INT;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addIntValues(@Nullable final int... values) throws TypeMismatchException {
    if ((type != Type.INT) && (count > 0)) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final int value = values[0];
        addSingleIntValueImpl(value);
      } else if (values.length > 1) {
        addMultipleIntValuesImpl(values);
      }
    }
  }

  @Override
  public void addIntValues(@Nullable final IntCollection values)
      throws TypeMismatchException {
    if ((type != Type.INT) && (count > 0)) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final int value = values.iterator().next();
      addSingleIntValueImpl(value);
    } else if (n > 1) {
      addMultipleIntValuesImpl(values);
    }
  }

  private void addMultipleIntValuesImpl(final int... values) {
    if (count == 0) {
      final IntList list = new IntArrayList(values);
      type = Type.INT;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final int oldValue = (Integer) valueOrValues;
      final IntList list = new IntArrayList();
      list.add(oldValue);
      for (final int value : values) {
        list.add(value);
      }
      type = Type.INT;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final IntList list = (IntList) valueOrValues;
      for (final int value : values) {
        list.add(value);
      }
      type = Type.INT;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleIntValuesImpl(final IntCollection values) {
    if (count == 0) {
      final IntList list = new IntArrayList(values);
      type = Type.INT;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final int oldValue = (Integer) valueOrValues;
      final IntList list = new IntArrayList();
      list.add(oldValue);
      list.addAll(values);
      type = Type.INT;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final IntList list = (IntList) valueOrValues;
      list.addAll(values);
      type = Type.INT;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public long getLongValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.LONG) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Long) valueOrValues;
    } else {
      final LongList values = (LongList) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setLongValue(final long value) {
    type = Type.LONG;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public long[] getLongValues() throws TypeMismatchException {
    if (type != Type.LONG) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    } else if (count == 1) {
      final Long value = (Long) valueOrValues;
      return new long[]{ value };
    } else { // count > 1
      final LongList values = (LongList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setLongValues(@Nullable final long... values) {
    if (values == null || values.length == 0) {
      type = Type.LONG;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final long value = values[0];
      type = Type.LONG;
      valueOrValues = value;
      count = 1;
    } else {
      final LongList list = new LongArrayList(values);
      type = Type.LONG;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setLongValues(@Nullable final LongCollection values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.LONG;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final long value = values.iterator().next();
      type = Type.LONG;
      valueOrValues = value;
      count = 1;
    } else {
      final LongList list = new LongArrayList(values);
      type = Type.LONG;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addLongValue(final long value) throws TypeMismatchException {
    if ((type != Type.LONG) && (count > 0)) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    addSingleLongValueImpl(value);
  }

  private void addSingleLongValueImpl(final long value) {
    if (count == 0) {
      type = Type.LONG;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final long oldValue = (Long) valueOrValues;
      final LongList list = new LongArrayList(2);
      list.add(oldValue);
      list.add(value);
      type = Type.LONG;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      final LongList list = (LongList) valueOrValues;
      list.add(value);
      type = Type.LONG;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addLongValues(@Nullable final long... values) throws TypeMismatchException {
    if ((type != Type.LONG) && (count > 0)) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final long value = values[0];
        addSingleLongValueImpl(value);
      } else if (values.length > 1) {
        addMultipleLongValuesImpl(values);
      }
    }
  }

  @Override
  public void addLongValues(@Nullable final LongCollection values)
      throws TypeMismatchException {
    if ((type != Type.LONG) && (count > 0)) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final long value = values.iterator().next();
      addSingleLongValueImpl(value);
    } else if (n > 1) {
      addMultipleLongValuesImpl(values);
    }
  }

  private void addMultipleLongValuesImpl(final long... values) {
    if (count == 0) {
      final LongList list = new LongArrayList(values);
      type = Type.LONG;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final long oldValue = (Long) valueOrValues;
      final LongList list = new LongArrayList();
      list.add(oldValue);
      for (final long value : values) {
        list.add(value);
      }
      type = Type.LONG;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final LongList list = (LongList) valueOrValues;
      for (final long value : values) {
        list.add(value);
      }
      type = Type.LONG;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleLongValuesImpl(final LongCollection values) {
    if (count == 0) {
      final LongList list = new LongArrayList(values);
      type = Type.LONG;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final long oldValue = (Long) valueOrValues;
      final LongList list = new LongArrayList();
      list.add(oldValue);
      list.addAll(values);
      type = Type.LONG;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final LongList list = (LongList) valueOrValues;
      list.addAll(values);
      type = Type.LONG;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public float getFloatValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.FLOAT) {
      throw new TypeMismatchException(Type.FLOAT.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Float) valueOrValues;
    } else {
      final FloatList values = (FloatList) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setFloatValue(final float value) {
    type = Type.FLOAT;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public float[] getFloatValues() throws TypeMismatchException {
    if (type != Type.FLOAT) {
      throw new TypeMismatchException(Type.FLOAT.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    } else if (count == 1) {
      final Float value = (Float) valueOrValues;
      return new float[]{ value };
    } else { // count > 1
      final FloatList values = (FloatList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setFloatValues(@Nullable final float... values) {
    if (values == null || values.length == 0) {
      type = Type.FLOAT;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final float value = values[0];
      type = Type.FLOAT;
      valueOrValues = value;
      count = 1;
    } else {
      final FloatList list = new FloatArrayList(values);
      type = Type.FLOAT;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setFloatValues(@Nullable final FloatCollection values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.FLOAT;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final float value = values.iterator().next();
      type = Type.FLOAT;
      valueOrValues = value;
      count = 1;
    } else {
      final FloatList list = new FloatArrayList(values);
      type = Type.FLOAT;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addFloatValue(final float value) throws TypeMismatchException {
    if ((type != Type.FLOAT) && (count > 0)) {
      throw new TypeMismatchException(Type.FLOAT.name(), type.name());
    }
    addSingleFloatValueImpl(value);
  }

  private void addSingleFloatValueImpl(final float value) {
    if (count == 0) {
      type = Type.FLOAT;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final float oldValue = (Float) valueOrValues;
      final FloatList list = new FloatArrayList(2);
      list.add(oldValue);
      list.add(value);
      type = Type.FLOAT;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      final FloatList list = (FloatList) valueOrValues;
      list.add(value);
      type = Type.FLOAT;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addFloatValues(@Nullable final float... values)
      throws TypeMismatchException {
    if ((type != Type.FLOAT) && (count > 0)) {
      throw new TypeMismatchException(Type.FLOAT.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final float value = values[0];
        addSingleFloatValueImpl(value);
      } else if (values.length > 1) {
        addMultipleFloatsImpl(values);
      }
    }
  }

  @Override
  public void addFloatValues(@Nullable final FloatCollection values)
      throws TypeMismatchException {
    if ((type != Type.FLOAT) && (count > 0)) {
      throw new TypeMismatchException(Type.FLOAT.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final float value = values.iterator().next();
      addSingleFloatValueImpl(value);
    } else if (n > 1) {
      addMultipleFloatsImpl(values);
    }
  }

  private void addMultipleFloatsImpl(final float... values) {
    if (count == 0) {
      final FloatList list = new FloatArrayList(values);
      type = Type.FLOAT;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final float oldValue = (Float) valueOrValues;
      final FloatList list = new FloatArrayList();
      list.add(oldValue);
      for (final float value : values) {
        list.add(value);
      }
      type = Type.FLOAT;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final FloatList list = (FloatList) valueOrValues;
      for (final float value : values) {
        list.add(value);
      }
      type = Type.FLOAT;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleFloatsImpl(final FloatCollection values) {
    if (count == 0) {
      final FloatList list = new FloatArrayList(values);
      type = Type.FLOAT;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final float oldValue = (Float) valueOrValues;
      final FloatList list = new FloatArrayList();
      list.add(oldValue);
      list.addAll(values);
      type = Type.FLOAT;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final FloatList list = (FloatList) valueOrValues;
      list.addAll(values);
      type = Type.FLOAT;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public double getDoubleValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DOUBLE) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Double) valueOrValues;
    } else {
      final DoubleList values = (DoubleList) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setDoubleValue(final double value) {
    type = Type.DOUBLE;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public double[] getDoubleValues() throws TypeMismatchException {
    if (type != Type.DOUBLE) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    } else if (count == 1) {
      final Double value = (Double) valueOrValues;
      return new double[]{ value };
    } else { // count > 1
      final DoubleList values = (DoubleList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setDoubleValues(@Nullable final double... values) {
    if (values == null || values.length == 0) {
      type = Type.DOUBLE;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final double value = values[0];
      type = Type.DOUBLE;
      valueOrValues = value;
      count = 1;
    } else {
      final DoubleList list = new DoubleArrayList(values);
      type = Type.DOUBLE;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setDoubleValues(@Nullable final DoubleCollection values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.DOUBLE;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final double value = values.iterator().next();
      type = Type.DOUBLE;
      valueOrValues = value;
      count = 1;
    } else {
      final DoubleList list = new DoubleArrayList(values);
      type = Type.DOUBLE;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addDoubleValue(final double value) throws TypeMismatchException {
    if ((type != Type.DOUBLE) && (count > 0)) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    addSingleDoubleValueImpl(value);
  }

  private void addSingleDoubleValueImpl(final double value) {
    if (count == 0) {
      type = Type.DOUBLE;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final double oldValue = (Double) valueOrValues;
      final DoubleList list = new DoubleArrayList(2);
      list.add(oldValue);
      list.add(value);
      type = Type.DOUBLE;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      final DoubleList list = (DoubleList) valueOrValues;
      list.add(value);
      type = Type.DOUBLE;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addDoubleValues(@Nullable final double... values)
      throws TypeMismatchException {
    if ((type != Type.DOUBLE) && (count > 0)) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final double value = values[0];
        addSingleDoubleValueImpl(value);
      } else if (values.length > 1) {
        addMultipleDoubleValuesImpl(values);
      }
    }
  }

  @Override
  public void addDoubleValues(@Nullable final DoubleCollection values)
      throws TypeMismatchException {
    if ((type != Type.DOUBLE) && (count > 0)) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final double value = values.iterator().next();
      addSingleDoubleValueImpl(value);
    } else if (n > 1) {
      addMultipleDoubleValuesImpl(values);
    }
  }

  private void addMultipleDoubleValuesImpl(final double... values) {
    if (count == 0) {
      final DoubleList list = new DoubleArrayList(values);
      type = Type.DOUBLE;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final double oldValue = (Double) valueOrValues;
      final DoubleList list = new DoubleArrayList();
      list.add(oldValue);
      for (final double value : values) {
        list.add(value);
      }
      type = Type.DOUBLE;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final DoubleList list = (DoubleList) valueOrValues;
      for (final double value : values) {
        list.add(value);
      }
      type = Type.DOUBLE;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleDoubleValuesImpl(final DoubleCollection values) {
    if (count == 0) {
      final DoubleList list = new DoubleArrayList(values);
      type = Type.DOUBLE;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final double oldValue = (Double) valueOrValues;
      final DoubleList list = new DoubleArrayList();
      list.add(oldValue);
      list.addAll(values);
      type = Type.DOUBLE;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final DoubleList list = (DoubleList) valueOrValues;
      list.addAll(values);
      type = Type.DOUBLE;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public String getStringValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.STRING) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (String) valueOrValues;
    } else {
      @SuppressWarnings("unchecked")
      final List<String> values = (List<String>) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setStringValue(@Nullable final String value) {
    type = Type.STRING;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public String[] getStringValues() throws TypeMismatchException {
    if (type != Type.STRING) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else if (count == 1) {
      final String value = (String) valueOrValues;
      return new String[]{ value };
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<String> values = (List<String>) valueOrValues;
      return values.toArray(new String[0]);
    }
  }

  @Override
  public void setStringValues(@Nullable final String... values) {
    if (values == null || values.length == 0) {
      type = Type.STRING;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.STRING;
      valueOrValues = values[0];
      count = 1;
    } else {
      final List<String> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.STRING;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setStringValues(@Nullable final Collection<String> values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.STRING;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      type = Type.STRING;
      valueOrValues = values.iterator().next();
      count = 1;
    } else {
      final List<String> list = new ArrayList<>(n);
      list.addAll(values);
      type = Type.STRING;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addStringValue(final String value) throws TypeMismatchException {
    if ((type != Type.STRING) && (count > 0)) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    addSingleStringValueImpl(value);
  }

  private void addSingleStringValueImpl(final String value) {
    if (count == 0) {
      type = Type.STRING;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final String oldValue = (String) valueOrValues;
      final List<String> list = new ArrayList<>(2);
      list.add(oldValue);
      list.add(value);
      type = Type.STRING;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<String> list = (List<String>) valueOrValues;
      list.add(value);
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addStringValues(@Nullable final String... values)
      throws TypeMismatchException {
    if ((type != Type.STRING) && (count > 0)) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final String value = values[0];
        addSingleStringValueImpl(value);
      } else if (values.length > 1) {
        addMultipleStringValuesImpl(values);
      }
    }
  }

  @Override
  public void addStringValues(@Nullable final Collection<String> values)
      throws TypeMismatchException {
    if ((type != Type.STRING) && (count > 0)) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final String value = values.iterator().next();
      addSingleStringValueImpl(value);
    } else if (n > 1) {
      addMultipleStringValuesImpl(values);
    }
  }

  private void addMultipleStringValuesImpl(final String... values) {
    if (count == 0) {
      final List<String> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final String oldValue = (String) valueOrValues;
      final List<String> list = new ArrayList<>();
      list.add(oldValue);
      Collections.addAll(list, values);
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<String> list = (List<String>) valueOrValues;
      Collections.addAll(list, values);
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleStringValuesImpl(final Collection<String> values) {
    if (count == 0) {
      final List<String> list = new ArrayList<>(values);
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final String oldValue = (String) valueOrValues;
      final List<String> list = new ArrayList<>();
      list.add(oldValue);
      list.addAll(values);
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<String> list = (List<String>) valueOrValues;
      list.addAll(values);
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public LocalDate getDateValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DATE) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (LocalDate) valueOrValues;
    } else {
      @SuppressWarnings("unchecked")
      final List<LocalDate> values = (List<LocalDate>) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setDateValue(final LocalDate value) {
    type = Type.DATE;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public LocalDate[] getDateValues() throws TypeMismatchException {
    if (type != Type.DATE) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_LOCAL_DATE_ARRAY;
    } else if (count == 1) {
      final LocalDate value = (LocalDate) valueOrValues;
      return new LocalDate[]{ value };
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalDate> values = (List<LocalDate>) valueOrValues;
      return values.toArray(new LocalDate[0]);
    }
  }

  @Override
  public void setDateValues(@Nullable final LocalDate... values) {
    if (values == null || values.length == 0) {
      type = Type.DATE;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final LocalDate value = values[0];
      type = Type.DATE;
      valueOrValues = value;
      count = 1;
    } else {
      final List<LocalDate> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.DATE;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setDateValues(@Nullable final Collection<LocalDate> values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.DATE;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final LocalDate value = values.iterator().next();
      type = Type.DATE;
      valueOrValues = value;
      count = 1;
    } else {
      final List<LocalDate> list = new ArrayList<>(n);
      list.addAll(values);
      type = Type.DATE;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addDateValue(final LocalDate value) throws TypeMismatchException {
    if ((type != Type.DATE) && (count > 0)) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    addSingleDateValueImpl(value);
  }

  private void addSingleDateValueImpl(final LocalDate value) {
    if (count == 0) {
      type = Type.DATE;
      count = 1;
      valueOrValues = value;
    } else if (count == 1) {
      final LocalDate oldValue = (LocalDate) valueOrValues;
      final List<LocalDate> list = new ArrayList<>(2);
      list.add(oldValue);
      list.add(value);
      type = Type.DATE;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalDate> list = (List<LocalDate>) valueOrValues;
      list.add(value);
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addDateValues(@Nullable final LocalDate... values) throws TypeMismatchException {
    if ((type != Type.DATE) && (count > 0)) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final LocalDate value = values[0];
        addSingleDateValueImpl(value);
      } else if (values.length > 1) {
        addMultipleDateValuesImpl(values);
      }
    }
  }

  @Override
  public void addDateValues(@Nullable final Collection<LocalDate> values)
      throws TypeMismatchException {
    if ((type != Type.DATE) && (count > 0)) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final LocalDate value = values.iterator().next();
      addSingleDateValueImpl(value);
    } else if (n > 1) {
      addMultipleDateValuesImpl(values);
    }
  }

  private void addMultipleDateValuesImpl(final LocalDate... values) {
    if (count == 0) {
      final List<LocalDate> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final LocalDate oldValue = (LocalDate) valueOrValues;
      final List<LocalDate> list = new ArrayList<>();
      list.add(oldValue);
      Collections.addAll(list, values);
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalDate> list = (List<LocalDate>) valueOrValues;
      Collections.addAll(list, values);
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleDateValuesImpl(final Collection<LocalDate> values) {
    if (count == 0) {
      final List<LocalDate> list = new ArrayList<>(values.size());
      list.addAll(values);
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final LocalDate oldValue = (LocalDate) valueOrValues;
      final List<LocalDate> list = new ArrayList<>();
      list.add(oldValue);
      list.addAll(values);
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalDate> list = (List<LocalDate>) valueOrValues;
      list.addAll(values);
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    }
  }


  @Override
  public LocalTime getTimeValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.TIME) {
      throw new TypeMismatchException(Type.TIME.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (LocalTime) valueOrValues;
    } else {
      @SuppressWarnings("unchecked")
      final List<LocalTime> values = (List<LocalTime>) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setTimeValue(final LocalTime value) {
    type = Type.TIME;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public LocalTime[] getTimeValues() throws TypeMismatchException {
    if (type != Type.TIME) {
      throw new TypeMismatchException(Type.TIME.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_LOCAL_TIME_ARRAY;
    } else if (count == 1) {
      final LocalTime value = (LocalTime) valueOrValues;
      return new LocalTime[]{ value };
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalTime> values = (List<LocalTime>) valueOrValues;
      return values.toArray(new LocalTime[0]);
    }
  }

  @Override
  public void setTimeValues(@Nullable final LocalTime... values) {
    if (values == null || values.length == 0) {
      type = Type.TIME;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final LocalTime value = values[0];
      type = Type.TIME;
      valueOrValues = value;
      count = 1;
    } else {
      final List<LocalTime> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.TIME;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setTimeValues(@Nullable final Collection<LocalTime> values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.TIME;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final LocalTime value = values.iterator().next();
      type = Type.TIME;
      valueOrValues = value;
      count = 1;
    } else {
      final List<LocalTime> list = new ArrayList<>(n);
      list.addAll(values);
      type = Type.TIME;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addTimeValue(final LocalTime value) throws TypeMismatchException {
    if ((type != Type.TIME) && (count > 0)) {
      throw new TypeMismatchException(Type.TIME.name(), type.name());
    }
    addSingleTimeValueImpl(value);
  }

  private void addSingleTimeValueImpl(final LocalTime value) {
    if (count == 0) {
      type = Type.TIME;
      count = 1;
      valueOrValues = value;
    } else if (count == 1) {
      final LocalTime oldValue = (LocalTime) valueOrValues;
      final List<LocalTime> list = new ArrayList<>(2);
      list.add(oldValue);
      list.add(value);
      type = Type.TIME;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalTime> list = (List<LocalTime>) valueOrValues;
      list.add(value);
      type = Type.TIME;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addTimeValues(@Nullable final LocalTime... values) throws TypeMismatchException {
    if ((type != Type.TIME) && (count > 0)) {
      throw new TypeMismatchException(Type.TIME.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final LocalTime value = values[0];
        addSingleTimeValueImpl(value);
      } else if (values.length > 1) {
        addMultipleTimeValuesImpl(values);
      }
    }
  }

  @Override
  public void addTimeValues(@Nullable final Collection<LocalTime> values)
      throws TypeMismatchException {
    if ((type != Type.TIME) && (count > 0)) {
      throw new TypeMismatchException(Type.TIME.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final LocalTime value = values.iterator().next();
      addSingleTimeValueImpl(value);
    } else if (n > 1) {
      addMultipleTimeValuesImpl(values);
    }
  }

  private void addMultipleTimeValuesImpl(final LocalTime... values) {
    if (count == 0) {
      final List<LocalTime> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.TIME;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final LocalTime oldValue = (LocalTime) valueOrValues;
      final List<LocalTime> list = new ArrayList<>();
      list.add(oldValue);
      Collections.addAll(list, values);
      type = Type.TIME;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalTime> list = (List<LocalTime>) valueOrValues;
      Collections.addAll(list, values);
      type = Type.TIME;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleTimeValuesImpl(final Collection<LocalTime> values) {
    if (count == 0) {
      final List<LocalTime> list = new ArrayList<>(values.size());
      list.addAll(values);
      type = Type.TIME;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final LocalTime oldValue = (LocalTime) valueOrValues;
      final List<LocalTime> list = new ArrayList<>();
      list.add(oldValue);
      list.addAll(values);
      type = Type.TIME;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalTime> list = (List<LocalTime>) valueOrValues;
      list.addAll(values);
      type = Type.TIME;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public LocalDateTime getDateTimeValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DATETIME) {
      throw new TypeMismatchException(Type.DATETIME.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (LocalDateTime) valueOrValues;
    } else {
      @SuppressWarnings("unchecked")
      final List<LocalDateTime> values = (List<LocalDateTime>) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setDateTimeValue(final LocalDateTime value) {
    type = Type.DATETIME;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public LocalDateTime[] getDateTimeValues() throws TypeMismatchException {
    if (type != Type.DATETIME) {
      throw new TypeMismatchException(Type.DATETIME.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_LOCAL_DATETIME_ARRAY;
    } else if (count == 1) {
      final LocalDateTime value = (LocalDateTime) valueOrValues;
      return new LocalDateTime[]{ value };
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalDateTime> values = (List<LocalDateTime>) valueOrValues;
      return values.toArray(new LocalDateTime[0]);
    }
  }

  @Override
  public void setDateTimeValues(@Nullable final LocalDateTime... values) {
    if (values == null || values.length == 0) {
      type = Type.DATETIME;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final LocalDateTime value = values[0];
      type = Type.DATETIME;
      valueOrValues = value;
      count = 1;
    } else {
      final List<LocalDateTime> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.DATETIME;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setDateTimeValues(@Nullable final Collection<LocalDateTime> values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.DATETIME;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final LocalDateTime value = values.iterator().next();
      type = Type.DATETIME;
      valueOrValues = value;
      count = 1;
    } else {
      final List<LocalDateTime> list = new ArrayList<>(n);
      list.addAll(values);
      type = Type.DATETIME;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addDateTimeValue(final LocalDateTime value) throws TypeMismatchException {
    if ((type != Type.DATETIME) && (count > 0)) {
      throw new TypeMismatchException(Type.DATETIME.name(), type.name());
    }
    addSingleDateTimeValueImpl(value);
  }

  private void addSingleDateTimeValueImpl(final LocalDateTime value) {
    if (count == 0) {
      type = Type.DATETIME;
      count = 1;
      valueOrValues = value;
    } else if (count == 1) {
      final LocalDateTime oldValue = (LocalDateTime) valueOrValues;
      final List<LocalDateTime> list = new ArrayList<>(2);
      list.add(oldValue);
      list.add(value);
      type = Type.DATETIME;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalDateTime> list = (List<LocalDateTime>) valueOrValues;
      list.add(value);
      type = Type.DATETIME;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addDateTimeValues(@Nullable final LocalDateTime... values) throws TypeMismatchException {
    if ((type != Type.DATETIME) && (count > 0)) {
      throw new TypeMismatchException(Type.DATETIME.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final LocalDateTime value = values[0];
        addSingleDateTimeValueImpl(value);
      } else if (values.length > 1) {
        addMultipleDateTimeValuesImpl(values);
      }
    }
  }

  @Override
  public void addDateTimeValues(@Nullable final Collection<LocalDateTime> values)
      throws TypeMismatchException {
    if ((type != Type.DATETIME) && (count > 0)) {
      throw new TypeMismatchException(Type.DATETIME.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final LocalDateTime value = values.iterator().next();
      addSingleDateTimeValueImpl(value);
    } else if (n > 1) {
      addMultipleDateTimeValuesImpl(values);
    }
  }

  private void addMultipleDateTimeValuesImpl(final LocalDateTime... values) {
    if (count == 0) {
      final List<LocalDateTime> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.DATETIME;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final LocalDateTime oldValue = (LocalDateTime) valueOrValues;
      final List<LocalDateTime> list = new ArrayList<>();
      list.add(oldValue);
      Collections.addAll(list, values);
      type = Type.DATETIME;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalDateTime> list = (List<LocalDateTime>) valueOrValues;
      Collections.addAll(list, values);
      type = Type.DATETIME;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleDateTimeValuesImpl(final Collection<LocalDateTime> values) {
    if (count == 0) {
      final List<LocalDateTime> list = new ArrayList<>(values.size());
      list.addAll(values);
      type = Type.DATETIME;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final LocalDateTime oldValue = (LocalDateTime) valueOrValues;
      final List<LocalDateTime> list = new ArrayList<>();
      list.add(oldValue);
      list.addAll(values);
      type = Type.DATETIME;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<LocalDateTime> list = (List<LocalDateTime>) valueOrValues;
      list.addAll(values);
      type = Type.DATETIME;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public BigInteger getBigIntegerValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BIG_INTEGER) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (BigInteger) valueOrValues;
    } else {
      @SuppressWarnings("unchecked")
      final List<BigInteger> values = (List<BigInteger>) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setBigIntegerValue(final BigInteger value) {
    type = Type.BIG_INTEGER;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public BigInteger[] getBigIntegerValues() throws TypeMismatchException {
    if (type != Type.BIG_INTEGER) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
    } else if (count == 1) {
      final BigInteger value = (BigInteger) valueOrValues;
      return new BigInteger[]{ value };
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<BigInteger> values = (List<BigInteger>) valueOrValues;
      return values.toArray(new BigInteger[0]);
    }
  }

  @Override
  public void setBigIntegerValues(@Nullable final BigInteger... values) {
    if (values == null || values.length == 0) {
      type = Type.BIG_INTEGER;
      valueOrValues = null;
      count = 1;
    } else if (values.length == 1) {
      type = Type.BIG_INTEGER;
      valueOrValues = values[0];
      count = 1;
    } else {
      final List<BigInteger> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setBigIntegerValues(@Nullable final Collection<BigInteger> values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.BIG_INTEGER;
      valueOrValues = null;
      count = 1;
    } else if (n == 1) {
      type = Type.BIG_INTEGER;
      valueOrValues = values.iterator().next();
      count = 1;
    } else {
      final List<BigInteger> list = new ArrayList<>(n);
      list.addAll(values);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addBigIntegerValue(final BigInteger value)
      throws TypeMismatchException {
    if ((type != Type.BIG_INTEGER) && (count > 0)) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    addSingleBigIntegerValueImpl(value);
  }

  private void addSingleBigIntegerValueImpl(final BigInteger value) {
    if (count == 0) {
      type = Type.BIG_INTEGER;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final BigInteger oldValue = (BigInteger) valueOrValues;
      final List<BigInteger> list = new ArrayList<>(2);
      list.add(oldValue);
      list.add(value);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<BigInteger> list = (List<BigInteger>) valueOrValues;
      list.add(value);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addBigIntegerValues(@Nullable final BigInteger... values)
      throws TypeMismatchException {
    if ((type != Type.BIG_INTEGER) && (count > 0)) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final BigInteger value = values[0];
        addSingleBigIntegerValueImpl(value);
      } else if (values.length > 1) {
        addMultipleBigIntegerValuesImpl(values);
      }
    }
  }

  @Override
  public void addBigIntegerValues(@Nullable final Collection<BigInteger> values)
      throws TypeMismatchException {
    if ((type != Type.BIG_INTEGER) && (count > 0)) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      addSingleBigIntegerValueImpl(values.iterator().next());
    } else if (n > 1) {
      addMultipleBigIntegerValuesImpl(values);
    }
  }

  private void addMultipleBigIntegerValuesImpl(final BigInteger... values) {
    if (count == 0) {
      final List<BigInteger> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final BigInteger oldValue = (BigInteger) valueOrValues;
      final List<BigInteger> list = new ArrayList<>();
      list.add(oldValue);
      Collections.addAll(list, values);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<BigInteger> list = (List<BigInteger>) valueOrValues;
      Collections.addAll(list, values);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleBigIntegerValuesImpl(final Collection<BigInteger> values) {
    if (count == 0) {
      final List<BigInteger> list = new ArrayList<>(values);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final BigInteger oldValue = (BigInteger) valueOrValues;
      final List<BigInteger> list = new ArrayList<>();
      list.add(oldValue);
      list.addAll(values);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<BigInteger> list = (List<BigInteger>) valueOrValues;
      list.addAll(values);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public BigDecimal getBigDecimalValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BIG_DECIMAL) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (BigDecimal) valueOrValues;
    } else {
      @SuppressWarnings("unchecked")
      final List<BigDecimal> values = (List<BigDecimal>) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setBigDecimalValue(final BigDecimal value) {
    type = Type.BIG_DECIMAL;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public BigDecimal[] getBigDecimalValues() throws TypeMismatchException {
    if (type != Type.BIG_DECIMAL) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
    } else if (count == 1) {
      final BigDecimal value = (BigDecimal) valueOrValues;
      return new BigDecimal[]{ value };
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<BigDecimal> values = (List<BigDecimal>) valueOrValues;
      return values.toArray(new BigDecimal[0]);
    }
  }

  @Override
  public void setBigDecimalValues(@Nullable final BigDecimal... values) {
    if (values == null || values.length == 0) {
      type = Type.BIG_DECIMAL;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.BIG_DECIMAL;
      valueOrValues = values[0];
      count = 1;
    } else {
      final List<BigDecimal> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setBigDecimalValues(@Nullable final Collection<BigDecimal> values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.BIG_DECIMAL;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      type = Type.BIG_DECIMAL;
      valueOrValues = values.iterator().next();
      count = 1;
    } else {
      final List<BigDecimal> list = new ArrayList<>(n);
      list.addAll(values);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addBigDecimalValue(final BigDecimal value)
      throws TypeMismatchException {
    if ((type != Type.BIG_DECIMAL) && (count > 0)) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    addSingleBigDecimalValueImpl(value);
  }

  private void addSingleBigDecimalValueImpl(final BigDecimal value) {
    if (count == 0) {
      type = Type.BIG_DECIMAL;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final BigDecimal oldValue = (BigDecimal) valueOrValues;
      final List<BigDecimal> list = new ArrayList<>(2);
      list.add(oldValue);
      list.add(value);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<BigDecimal> list = (List<BigDecimal>) valueOrValues;
      list.add(value);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addBigDecimalValues(@Nullable final BigDecimal... values)
      throws TypeMismatchException {
    if ((type != Type.BIG_DECIMAL) && (count > 0)) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final BigDecimal value = values[0];
        addSingleBigDecimalValueImpl(value);
      } else if (values.length > 1) {
        addMultipleBigDecimalValuesImpl(values);
      }
    }
  }

  @Override
  public void addBigDecimalValues(@Nullable final Collection<BigDecimal> values)
      throws TypeMismatchException {
    if ((type != Type.BIG_DECIMAL) && (count > 0)) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final BigDecimal value = values.iterator().next();
      addSingleBigDecimalValueImpl(value);
    } else if (n > 1) {
      addMultipleBigDecimalValuesImpl(values);
    }
  }

  private void addMultipleBigDecimalValuesImpl(final BigDecimal... values) {
    if (count == 0) {
      final List<BigDecimal> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final BigDecimal oldValue = (BigDecimal) valueOrValues;
      final List<BigDecimal> list = new ArrayList<>();
      list.add(oldValue);
      Collections.addAll(list, values);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<BigDecimal> list = (List<BigDecimal>) valueOrValues;
      Collections.addAll(list, values);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleBigDecimalValuesImpl(final Collection<BigDecimal> values) {
    if (count == 0) {
      final List<BigDecimal> list = new ArrayList<>(values);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final BigDecimal oldValue = (BigDecimal) valueOrValues;
      final List<BigDecimal> list = new ArrayList<>();
      list.add(oldValue);
      list.addAll(values);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<BigDecimal> list = (List<BigDecimal>) valueOrValues;
      list.addAll(values);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public byte[] getByteArrayValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BYTE_ARRAY) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (byte[]) valueOrValues;
    } else {
      @SuppressWarnings("unchecked")
      final List<byte[]> values = (List<byte[]>) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setByteArrayValue(final byte[] value) {
    type = Type.BYTE_ARRAY;
    valueOrValues = Assignment.clone(value);
    count = 1;
  }

  @Override
  public byte[][] getByteArrayValues() throws TypeMismatchException {
    if (type != Type.BYTE_ARRAY) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else if (count == 1) {
      final byte[] value = (byte[]) valueOrValues;
      return new byte[][]{ Assignment.clone(value) };
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<byte[]> values = (List<byte[]>) valueOrValues;
      final byte[][] result = new byte[values.size()][];
      int i = 0;
      for (final byte[] value : values) {
        result[i++] = Assignment.clone(value);
      }
      return result;
    }
  }

  @Override
  public void setByteArrayValues(@Nullable final byte[]... values) {
    if (values == null || values.length == 0) {
      type = Type.BYTE_ARRAY;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final byte[] value = values[0];
      type = Type.BYTE_ARRAY;
      valueOrValues = Assignment.clone(value);
      count = 1;
    } else {
      final List<byte[]> list = new ArrayList<>(values.length);
      for (final byte[] value : values) {
        list.add(Assignment.clone(value));
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setByteArrayValues(@Nullable final Collection<byte[]> values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.BYTE_ARRAY;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final byte[] value = values.iterator().next();
      type = Type.BYTE_ARRAY;
      valueOrValues = Assignment.clone(value);
      count = 1;
    } else {
      final List<byte[]> list = new ArrayList<>(n);
      for (final byte[] value : values) {
        list.add(Assignment.clone(value));
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addByteArrayValue(final byte[] value)
      throws TypeMismatchException {
    if ((type != Type.BYTE_ARRAY) && (count > 0)) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    addSingleByteArrayValueImpl(true, value);
  }

  private void addSingleByteArrayValueImpl(final boolean cloneValue, final byte[] value) {
    if (count == 0) {
      type = Type.BYTE_ARRAY;
      count = 1;
      if (cloneValue) {
        valueOrValues = Assignment.clone(value);
      } else {
        valueOrValues = value;
      }
    } else if (count == 1) {
      final byte[] oldValue = (byte[]) valueOrValues;
      final List<byte[]> list = new ArrayList<>(2);
      list.add(oldValue);
      if (cloneValue) {
        list.add(Assignment.clone(value));
      } else {
        list.add(value);
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<byte[]> list = (List<byte[]>) valueOrValues;
      if (cloneValue) {
        list.add(Assignment.clone(value));
      } else {
        list.add(value);
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addByteArrayValues(@Nullable final byte[]... values)
      throws TypeMismatchException {
    if ((type != Type.BYTE_ARRAY) && (count > 0)) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        final byte[] value = values[0];
        addSingleByteArrayValueImpl(true, value);
      } else if (values.length > 1) {
        addMultipleByteArrayValuesImpl(true, values);
      }
    }
  }

  @Override
  public void addByteArrayValues(@Nullable final Collection<byte[]> values)
      throws TypeMismatchException {
    if ((type != Type.BYTE_ARRAY) && (count > 0)) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final byte[] value = values.iterator().next();
      addSingleByteArrayValueImpl(true, value);
    } else if (n > 1) {
      addMultipleByteArrayValuesImpl(true, values);
    }
  }

  private void addMultipleByteArrayValuesImpl(final boolean cloneValue,
      final byte[]... values) {
    if (count == 0) {
      final List<byte[]> list = new ArrayList<>(values.length);
      if (cloneValue) {
        for (final byte[] value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        Collections.addAll(list, values);
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final byte[] oldValue = (byte[]) valueOrValues;
      final List<byte[]> list = new ArrayList<>();
      list.add(oldValue);
      if (cloneValue) {
        for (final byte[] value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        Collections.addAll(list, values);
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<byte[]> list = (List<byte[]>) valueOrValues;
      if (cloneValue) {
        for (final byte[] value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        Collections.addAll(list, values);
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleByteArrayValuesImpl(final boolean cloneValue,
      final Collection<byte[]> values) {
    if (count == 0) {
      final List<byte[]> list = new ArrayList<>(values.size());
      if (cloneValue) {
        for (final byte[] value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        list.addAll(values);
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final byte[] oldValue = (byte[]) valueOrValues;
      final List<byte[]> list = new ArrayList<>();
      list.add(oldValue);
      if (cloneValue) {
        for (final byte[] value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        list.addAll(values);
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<byte[]> list = (List<byte[]>) valueOrValues;
      if (cloneValue) {
        for (final byte[] value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        list.addAll(values);
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public Class<?> getClassValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.CLASS) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Class<?>) valueOrValues;
    } else {
      @SuppressWarnings("unchecked")
      final List<Class<?>> values = (List<Class<?>>) valueOrValues;
      return values.iterator().next();
    }
  }

  @Override
  public void setClassValue(final Class<?> value) {
    type = Type.CLASS;
    valueOrValues = value;
    count = 1;
  }

  @Override
  public Class<?>[] getClassValues() throws TypeMismatchException {
    if (type != Type.CLASS) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    } else if (count == 1) {
      final Class<?> value = (Class<?>) valueOrValues;
      return new Class<?>[]{ value };
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<Class<?>> values = (List<Class<?>>) valueOrValues;
      return values.toArray(new Class<?>[0]);
    }
  }

  @Override
  public void setClassValues(@Nullable final Class<?>... values) {
    if (values == null || values.length == 0) {
      type = Type.CLASS;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.CLASS;
      valueOrValues = values[0];
      count = 1;
    } else {
      final List<Class<?>> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.CLASS;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setClassValues(@Nullable final Collection<Class<?>> values) {
    final int n = (values == null ? 0 : values.size());
    if (n == 0) {
      type = Type.CLASS;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      type = Type.CLASS;
      valueOrValues = values.iterator().next();
      count = 1;
    } else {
      final List<Class<?>> list = new ArrayList<>(n);
      list.addAll(values);
      type = Type.CLASS;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addClassValue(final Class<?> value) throws TypeMismatchException {
    if ((type != Type.CLASS) && (count > 0)) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    addSingleClassValueImpl(value);
  }

  private void addSingleClassValueImpl(final Class<?> value) {
    if (count == 0) {
      type = Type.CLASS;
      valueOrValues = value;
      count = 1;
    } else if (count == 1) {
      final Class<?> oldValue = (Class<?>) valueOrValues;
      final List<Class<?>> list = new ArrayList<>(2);
      list.add(oldValue);
      list.add(value);
      type = Type.CLASS;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<Class<?>> list = (List<Class<?>>) valueOrValues;
      list.add(value);
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addClassValues(@Nullable final Class<?>... values)
      throws TypeMismatchException {
    if ((type != Type.CLASS) && (count > 0)) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    if (values != null) {
      if (values.length == 1) {
        addSingleClassValueImpl(values[0]);
      } else if (values.length > 1) {
        addMultipleClassValuesImpl(values);
      }
    }
  }

  @Override
  public void addClassValues(@Nullable final Collection<Class<?>> values)
      throws TypeMismatchException {
    if ((type != Type.CLASS) && (count > 0)) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    final int n = (values == null ? 0 : values.size());
    if (n == 1) {
      final Class<?> value = values.iterator().next();
      addSingleClassValueImpl(value);
    } else if (n > 1) {
      addMultipleClassValuesImpl(values);
    }
  }

  private void addMultipleClassValuesImpl(final Class<?>... values) {
    if (count == 0) {
      final List<Class<?>> list = new ArrayList<>(values.length);
      Collections.addAll(list, values);
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final Class<?> oldValue = (Class<?>) valueOrValues;
      final List<Class<?>> list = new ArrayList<>();
      list.add(oldValue);
      Collections.addAll(list, values);
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<Class<?>> list = (List<Class<?>>) valueOrValues;
      Collections.addAll(list, values);
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    }
  }

  private void addMultipleClassValuesImpl(final Collection<Class<?>> values) {
    if (count == 0) {
      final List<Class<?>> list = new ArrayList<>(values);
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final Class<?> oldValue = (Class<?>) valueOrValues;
      final List<Class<?>> list = new ArrayList<>();
      list.add(oldValue);
      list.addAll(values);
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      @SuppressWarnings("unchecked")
      final List<Class<?>> list = (List<Class<?>>) valueOrValues;
      list.addAll(values);
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public <E extends Enum<E>> E getEnumValue(final Class<E> enumClass)
      throws TypeMismatchException, NoSuchElementException, IllegalArgumentException {
    final String name = getStringValue();
    return Enum.valueOf(enumClass, name);
  }

  @Override
  public void setEnumValue(final @Nullable Enum<?> value) {
    if (value == null) {
      setStringValue(null);
    } else {
      setStringValue(value.name());
    }
  }

  @Override
  public <E extends Enum<E>> E[] getEnumValues(final Class<E> enumClass)
      throws TypeMismatchException, IllegalArgumentException {
    final String[] names = getStringValues();
    @SuppressWarnings("unchecked")
    final E[] result = (E[]) java.lang.reflect.Array.newInstance(enumClass, names.length);
    for (int i = 0; i < names.length; ++i) {
      result[i] = Enum.valueOf(enumClass, names[i]);
    }
    return result;
  }

  @Override
  public void setEnumValues(@Nullable final Enum<?>... values) {
    if (values == null || values.length == 0) {
      type = Type.STRING;
      count = 0;
      valueOrValues = null;
    } else {
      final String[] names = new String[values.length];
      for (int i = 0; i < values.length; ++i) {
        final Enum<?> value = values[i];
        names[i] = (value == null ? null : value.name());
      }
      setStringValues(names);
    }
  }

  @Override
  public void setEnumValues(@Nullable final Collection<? extends Enum<?>> values) {
    if (values == null || values.isEmpty()) {
      type = Type.STRING;
      count = 0;
      valueOrValues = null;
    } else {
      final String[] names = new String[values.size()];
      int i = 0;
      for (final Enum<?> value : values) {
        names[i++] = (value == null ? null : value.name());
      }
      setStringValues(names);
    }
  }

  @Override
  public void addEnumValue(@Nullable final Enum<?> value)
      throws TypeMismatchException {
    if (value == null) {
      addStringValue(null);
    } else {
      addStringValue(value.name());
    }
  }

  @Override
  public void addEnumValues(@Nullable final Enum<?>... values)
      throws TypeMismatchException {
    if (values == null || values.length == 0) {
      return;
    }
    final String[] names = new String[values.length];
    for (int i = 0; i < values.length; ++i) {
      final Enum<?> value = values[i];
      names[i] = (value == null ? null : value.name());
    }
    addStringValues(names);
  }

  @Override
  public void addEnumValues(@Nullable final Collection<? extends Enum<?>> values)
      throws TypeMismatchException {
    if (values == null || values.isEmpty()) {
      return;
    }
    final String[] names = new String[values.size()];
    int i = 0;
    for (final Enum<?> value : values) {
      names[i++] = (value == null ? null : value.name());
    }
    addStringValues(names);
  }

  @Override
  public boolean getValueAsBoolean() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsBoolean(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsBoolean(type, valueOrValues);
    }
  }

  @Override
  public boolean[] getValuesAsBoolean() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    } else if (count == 1) {
      final boolean value = TypeUtils.objectAsBoolean(type, valueOrValues);
      return new boolean[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsBooleans(type, valueOrValues);
    }
  }

  @Override
  public char getValueAsChar() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsChar(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsChar(type, valueOrValues);
    }
  }

  @Override
  public char[] getValuesAsChar() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    } else if (count == 1) {
      final char value = TypeUtils.objectAsChar(type, valueOrValues);
      return new char[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsChars(type, valueOrValues);
    }
  }

  @Override
  public byte getValueAsByte() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsByte(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsByte(type, valueOrValues);
    }
  }

  @Override
  public byte[] getValuesAsByte() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else if (count == 1) {
      final byte value = TypeUtils.objectAsByte(type, valueOrValues);
      return new byte[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsBytes(type, valueOrValues);
    }
  }

  @Override
  public short getValueAsShort() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsShort(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsShort(type, valueOrValues);
    }
  }

  @Override
  public short[] getValuesAsShort() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    } else if (count == 1) {
      final short value = TypeUtils.objectAsShort(type, valueOrValues);
      return new short[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsShorts(type, valueOrValues);
    }
  }

  @Override
  public int getValueAsInt() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsInt(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsInt(type, valueOrValues);
    }
  }

  @Override
  public int[] getValuesAsInt() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else if (count == 1) {
      final int value = TypeUtils.objectAsInt(type, valueOrValues);
      return new int[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsInts(type, valueOrValues);
    }
  }

  @Override
  public long getValueAsLong() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsLong(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsLong(type, valueOrValues);
    }
  }

  @Override
  public long[] getValuesAsLong() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    } else if (count == 1) {
      final long value = TypeUtils.objectAsLong(type, valueOrValues);
      return new long[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsLongs(type, valueOrValues);
    }
  }

  @Override
  public float getValueAsFloat() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsFloat(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsFloat(type, valueOrValues);
    }
  }

  @Override
  public float[] getValuesAsFloat() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    } else if (count == 1) {
      final float value = TypeUtils.objectAsFloat(type, valueOrValues);
      return new float[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsFloats(type, valueOrValues);
    }
  }

  @Override
  public double getValueAsDouble() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsDouble(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsDouble(type, valueOrValues);
    }
  }

  @Override
  public double[] getValuesAsDouble() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    } else if (count == 1) {
      final double value = TypeUtils.objectAsDouble(type, valueOrValues);
      return new double[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsDoubles(type, valueOrValues);
    }
  }

  @Override
  public String getValueAsString() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsString(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsString(type, valueOrValues);
    }
  }

  @Override
  public String[] getValuesAsString() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else if (count == 1) {
      final String value = TypeUtils.objectAsString(type, valueOrValues);
      return new String[]{ value };
    } else { // count > 1
      final Object list = valueOrValues;
      return TypeUtils.collectionAsStrings(type, list);
    }
  }

  @Override
  public LocalDate getValueAsDate() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsDate(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsDate(type, valueOrValues);
    }
  }

  @Override
  public LocalDate[] getValuesAsDate() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_LOCAL_DATE_ARRAY;
    } else if (count == 1) {
      final LocalDate value = TypeUtils.objectAsDate(type, valueOrValues);
      return new LocalDate[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsDates(type, valueOrValues);
    }
  }

  @Override
  public LocalTime getValueAsTime() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsTime(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsTime(type, valueOrValues);
    }
  }

  @Override
  public LocalTime[] getValuesAsTime() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_LOCAL_TIME_ARRAY;
    } else if (count == 1) {
      final LocalTime value = TypeUtils.objectAsTime(type, valueOrValues);
      return new LocalTime[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsTimes(type, valueOrValues);
    }
  }

  @Override
  public LocalDateTime getValueAsDateTime() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsDateTime(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsDateTime(type, valueOrValues);
    }
  }

  @Override
  public LocalDateTime[] getValuesAsDateTime() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_LOCAL_DATETIME_ARRAY;
    } else if (count == 1) {
      final LocalDateTime value = TypeUtils.objectAsDateTime(type, valueOrValues);
      return new LocalDateTime[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsDateTimes(type, valueOrValues);
    }
  }

  @Override
  public byte[] getValueAsByteArray() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsByteArray(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsByteArray(type, valueOrValues);
    }
  }

  @Override
  public byte[][] getValuesAsByteArray() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else if (count == 1) {
      final byte[] value = TypeUtils.objectAsByteArray(type, valueOrValues);
      return new byte[][]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsByteArrays(type, valueOrValues);
    }
  }

  @Override
  public Class<?> getValueAsClass() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsClass(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsClass(type, valueOrValues);
    }
  }

  @Override
  public Class<?>[] getValuesAsClass() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    } else if (count == 1) {
      final Class<?> value = TypeUtils.objectAsClass(type, valueOrValues);
      return new Class<?>[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsClasses(type, valueOrValues);
    }
  }

  @Override
  public BigInteger getValueAsBigInteger() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsBigInteger(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsBigInteger(type, valueOrValues);
    }
  }

  @Override
  public BigInteger[] getValuesAsBigInteger() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
    } else if (count == 1) {
      final BigInteger value = TypeUtils.objectAsBigInteger(type, valueOrValues);
      return new BigInteger[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsBigIntegers(type, valueOrValues);
    }
  }

  @Override
  public BigDecimal getValueAsBigDecimal() throws TypeConvertException,
      NoSuchElementException {
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return TypeUtils.objectAsBigDecimal(type, valueOrValues);
    } else {
      return TypeUtils.firstInCollectionAsBigDecimal(type, valueOrValues);
    }
  }

  @Override
  public BigDecimal[] getValuesAsBigDecimal() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
    } else if (count == 1) {
      final BigDecimal value = TypeUtils.objectAsBigDecimal(type, valueOrValues);
      return new BigDecimal[]{ value };
    } else { // count > 1
      return TypeUtils.collectionAsBigDecimals(type, valueOrValues);
    }
  }

  @Override
  public BasicMultiValues cloneEx() {
    try {
      final BasicMultiValues result = (BasicMultiValues) clone();
      result.assignValues(this);
      return result;
    } catch (final CloneNotSupportedException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  @Override
  public int hashCode() {
    final int multiplier = 71;
    int code = 131;
    code = Hash.combine(code, multiplier, type);
    code = Hash.combine(code, multiplier, count);
    if (count == 0) {
      code = Hash.combine(code, multiplier, 0);
    } else if (count == 1) {
      final int h = TypeUtils.hashCodeOfObject(type, valueOrValues);
      code = Hash.combine(code, multiplier, h);
    } else { // count > 1
      final int h = TypeUtils.hashCodeOfCollection(type, valueOrValues);
      code = Hash.combine(code, multiplier, h);
    }
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
    if (! (obj instanceof final BasicMultiValues other)) {
      return false;
    }
    if ((type != other.type) || (count != other.count)) {
      return false;
    }
    if (count == 1) {
      return TypeUtils.equalsObject(type, valueOrValues, other.valueOrValues);
    } else if (count > 1) {
      return TypeUtils.equalsCollections(type, valueOrValues, other.valueOrValues);
    } else {
      return true;
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("type", type)
               .append("count", count)
               .append("valueOrValues", valueOrValues)
               .toString();
  }
}