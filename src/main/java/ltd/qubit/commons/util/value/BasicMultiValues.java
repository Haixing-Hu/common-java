////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

public class BasicMultiValues implements MultiValues, Serializable {

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
    setBooleanValue(value);
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

  public BasicMultiValues(@Nullable final String value) {
    this();
    setStringValue(value);
  }

  public BasicMultiValues(@Nullable final Date value) {
    this();
    setDateValue(value);
  }

  public BasicMultiValues(@Nullable final BigInteger value) {
    this();
    setBigIntegerValue(value);
  }

  public BasicMultiValues(@Nullable final BigDecimal value) {
    this();
    setBigDecimalValue(value);
  }

  public BasicMultiValues(@Nullable final byte[] value) {
    this();
    setByteArrayValue(value);
  }

  public BasicMultiValues(@Nullable final Class<?> value) {
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
    final Type type = other.getType();
    final int count = other.getCount();
    if (count == 0) {
      this.type = type;
      valueOrValues = null;
      this.count = 0;
    } else if (count == 1) {
      assignSingleValue(type, other);
    } else {
      assignMultiValues(type, other);
    }
  }

  private void assignSingleValue(final Type type, final MultiValues other) {
    switch (type) {
      case BOOL: {
        final boolean value = other.getBooleanValue();
        this.type = Type.BOOL;
        valueOrValues = Boolean.valueOf(value);
        count = 1;
        return;
      }
      case CHAR: {
        final char value = other.getCharValue();
        this.type = Type.CHAR;
        valueOrValues = Character.valueOf(value);
        count = 1;
        return;
      }
      case BYTE: {
        final byte value = other.getByteValue();
        this.type = Type.BYTE;
        valueOrValues = Byte.valueOf(value);
        count = 1;
        return;
      }
      case SHORT: {
        final short value = other.getShortValue();
        this.type = Type.SHORT;
        valueOrValues = Short.valueOf(value);
        count = 1;
        return;
      }
      case INT: {
        final int value = other.getIntValue();
        this.type = Type.INT;
        valueOrValues = Integer.valueOf(value);
        count = 1;
        return;
      }
      case LONG: {
        final long value = other.getLongValue();
        this.type = Type.LONG;
        valueOrValues = Long.valueOf(value);
        count = 1;
        return;
      }
      case FLOAT: {
        final float value = other.getFloatValue();
        this.type = Type.FLOAT;
        valueOrValues = Float.valueOf(value);
        count = 1;
        return;
      }
      case DOUBLE: {
        final double value = other.getDoubleValue();
        this.type = Type.DOUBLE;
        valueOrValues = Double.valueOf(value);
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
        final Date value = other.getDateValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        this.type = Type.DATE;
        valueOrValues = value;
        count = 1;
        return;
      }
      case BYTE_ARRAY: {
        final byte[] value = other.getByteArrayValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
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
        final boolean[] values = other.getBooleanValues();
        doAddBooleanValues(values);
        return;
      }
      case CHAR: {
        final char[] values = other.getCharValues();
        doAddCharValues(values);
        return;
      }
      case BYTE: {
        final byte[] values = other.getByteValues();
        doAddByteValues(values);
        return;
      }
      case SHORT: {
        final short[] values = other.getShortValues();
        doAddShortValues(values);
        return;
      }
      case INT: {
        final int[] values = other.getIntValues();
        doAddIntValues(values);
        return;
      }
      case LONG: {
        final long[] values = other.getLongValues();
        doAddLongValues(values);
        return;
      }
      case FLOAT: {
        final float[] values = other.getFloatValues();
        doAddFloatValues(values);
        return;
      }
      case DOUBLE: {
        final double[] values = other.getDoubleValues();
        doAddDoubleValues(values);
        return;
      }
      case STRING: {
        final String[] values = other.getStringValues();
        doAddStringValues(values);
        return;
      }
      case DATE: {
        final Date[] values = other.getDateValues();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        doAddDateValues(false, values);
        return;
      }
      case BYTE_ARRAY: {
        final byte[][] values = other.getByteArrayValues();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        doAddByteArrayValues(false, values);
        return;
      }
      case CLASS: {
        final Class<?>[] values = other.getClassValues();
        doAddClassValues(values);
        return;
      }
      case BIG_INTEGER: {
        final BigInteger[] values = other.getBigIntegerValues();
        doAddBigIntegerValues(values);
        return;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] values = other.getBigDecimalValues();
        doAddBigDecimalValues(values);
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
      doUnionSingleValue(type, other);
    } else if (other_count > 1) {
      doUnionMultiValues(type, other);
    }
  }

  private void doUnionSingleValue(final Type type, final MultiValues other) {
    switch (type) {
      case BOOL: {
        final boolean other_value = other.getBooleanValue();
        doAddBooleanValue(other_value);
        return;
      }
      case CHAR: {
        final char other_value = other.getCharValue();
        doAddCharValue(other_value);
        return;
      }
      case BYTE: {
        final byte other_value = other.getByteValue();
        doAddByteValue(other_value);
        return;
      }
      case SHORT: {
        final short other_value = other.getShortValue();
        doAddShortValue(other_value);
        return;
      }
      case INT: {
        final int other_value = other.getIntValue();
        doAddIntValue(other_value);
        return;
      }
      case LONG: {
        final long other_value = other.getLongValue();
        doAddLongValue(other_value);
        return;
      }
      case FLOAT: {
        final float other_value = other.getFloatValue();
        doAddFloatValue(other_value);
        return;
      }
      case DOUBLE: {
        final double other_value = other.getDoubleValue();
        doAddDoubleValue(other_value);
        return;
      }
      case STRING: {
        final String other_value = other.getStringValue();
        doAddStringValue(other_value);
        return;
      }
      case DATE: {
        final Date other_value = other.getDateValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        doAddDateValue(false, other_value);
        return;
      }
      case BYTE_ARRAY: {
        final byte[] other_value = other.getByteArrayValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        doAddByteArrayValue(false, other_value);
        return;
      }
      case CLASS: {
        final Class<?> other_value = other.getClassValue();
        doAddClassValue(other_value);
        return;
      }
      case BIG_INTEGER: {
        final BigInteger other_value = other.getBigIntegerValue();
        doAddBigIntegerValue(other_value);
        return;
      }
      case BIG_DECIMAL: {
        final BigDecimal other_value = other.getBigDecimalValue();
        doAddBigDecimalValue(other_value);
        return;
      }
      default:
        throw new UnsupportedDataTypeException(type);
    }
  }

  private void doUnionMultiValues(final Type type, final MultiValues other) {
    switch (type) {
      case BOOL: {
        final boolean[] other_values = other.getBooleanValues();
        doAddBooleanValues(other_values);
        return;
      }
      case CHAR: {
        final char[] other_values = other.getCharValues();
        doAddCharValues(other_values);
        return;
      }
      case BYTE: {
        final byte[] other_values = other.getByteValues();
        doAddByteValues(other_values);
        return;
      }
      case SHORT: {
        final short[] other_values = other.getShortValues();
        doAddShortValues(other_values);
        return;
      }
      case INT: {
        final int[] other_values = other.getIntValues();
        doAddIntValues(other_values);
        return;
      }
      case LONG: {
        final long[] other_values = other.getLongValues();
        doAddLongValues(other_values);
        return;
      }
      case FLOAT: {
        final float[] other_values = other.getFloatValues();
        doAddFloatValues(other_values);
        return;
      }
      case DOUBLE: {
        final double[] other_values = other.getDoubleValues();
        doAddDoubleValues(other_values);
        return;
      }
      case STRING: {
        final String[] other_values = other.getStringValues();
        doAddStringValues(other_values);
        return;
      }
      case DATE: {
        final Date[] other_values = other.getDateValues();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        doAddDateValues(false, other_values);
        return;
      }
      case BYTE_ARRAY: {
        final byte[][] other_values = other.getByteArrayValues();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        doAddByteArrayValues(false, other_values);
        return;
      }
      case CLASS: {
        final Class<?>[] other_values = other.getClassValues();
        doAddClassValues(other_values);
        return;
      }
      case BIG_INTEGER: {
        final BigInteger[] other_values = other.getBigIntegerValues();
        doAddBigIntegerValues(other_values);
        return;
      }
      case BIG_DECIMAL: {
        final BigDecimal[] other_values = other.getBigDecimalValues();
        doAddBigDecimalValues(other_values);
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
    } catch (final UnsupportedDataTypeException e) {
      throw new InvalidFormatException(e);
    } catch (final ClassNotFoundException e) {
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
    if (count == 0) {
      return;
    } else if (count == 1) {
      final Element node = TypeUtils.toXmlNode(type, valueOrValues, doc,
          tagName, prevSpaceAttr);
      parent.appendChild(node);
    } else {
      final List<Element> nodes = TypeUtils.toXmlNodes(type, valueOrValues,
          doc, parent, containerName, tagName, prevSpaceAttr);
      for (final Element node : nodes) {
        parent.appendChild(node);
      }
    }
  }

  @Override
  public boolean getBooleanValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BOOL) {
      throw new TypeMismatchException(Type.BOOL.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      final Boolean value = (Boolean) valueOrValues;
      return value;
    } else {
      final BooleanList values = (BooleanList) valueOrValues;
      final boolean value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setBooleanValue(final boolean value) {
    type = Type.BOOL;
    valueOrValues = Boolean.valueOf(value);
    count = 1;
  }

  @Override
  public boolean[] getBooleanValues() throws TypeMismatchException {
    if (type != Type.BOOL) {
      throw new TypeMismatchException(Type.BOOL.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    } else if (count == 1) {
      final Boolean value = (Boolean) valueOrValues;
      final boolean[] result = { value };
      return result;
    } else { // count > 1
      final BooleanList values = (BooleanList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setBooleanValues(final boolean... values) {
    if (values.length == 0) {
      type = Type.BOOL;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final boolean value = values[0];
      type = Type.BOOL;
      valueOrValues = Boolean.valueOf(value);
      count = 1;
    } else {
      final BooleanList list = new BooleanArrayList(values);
      type = Type.BOOL;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setBooleanValues(final BooleanCollection values) {
    final int n = values.size();
    if (n == 0) {
      type = Type.BOOL;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final boolean value = values.iterator().next();
      type = Type.BOOL;
      valueOrValues = Boolean.valueOf(value);
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
    doAddBooleanValue(value);
  }

  private void doAddBooleanValue(final boolean value) {
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
  public void addBooleanValues(final boolean... values)
      throws TypeMismatchException {
    if ((type != Type.BOOL) && (count > 0)) {
      throw new TypeMismatchException(Type.BOOL.name(), type.name());
    }
    if (values.length == 1) {
      final boolean value = values[0];
      doAddBooleanValue(value);
    } else if (values.length > 1) {
      doAddBooleanValues(values);
    }
  }

  @Override
  public void addBooleanValues(final BooleanCollection values)
      throws TypeMismatchException {
    if ((type != Type.BOOL) && (count > 0)) {
      throw new TypeMismatchException(Type.BOOL.name(), type.name());
    }
    if (count == 1) {
      final boolean value = values.iterator().next();
      doAddBooleanValue(value);
    } else if (count > 1) {
      doAddBooleanValues(values);
    }
  }

  private void doAddBooleanValues(final boolean... values) {
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

  private void doAddBooleanValues(final BooleanCollection values) {
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
      final Character value = (Character) valueOrValues;
      return value;
    } else {
      final CharList values = (CharList) valueOrValues;
      final char value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setCharValue(final char value) {
    type = Type.CHAR;
    valueOrValues = Character.valueOf(value);
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
      final char[] result = { value };
      return result;
    } else { // count > 1
      final CharList values = (CharList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setCharValues(final char... values) {
    if (values.length == 0) {
      type = Type.CHAR;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final char value = values[0];
      type = Type.CHAR;
      valueOrValues = Character.valueOf(value);
      count = 1;
    } else {
      final CharList list = new CharArrayList(values);
      type = Type.CHAR;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setCharValues(final CharCollection values) {
    final int n = values.size();
    if (n == 0) {
      type = Type.CHAR;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final char value = values.iterator().next();
      type = Type.CHAR;
      valueOrValues = Character.valueOf(value);
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
    doAddCharValue(value);
  }

  private void doAddCharValue(final char value) {
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
  public void addCharValues(final char... values) throws TypeMismatchException {
    if ((type != Type.CHAR) && (count > 0)) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    if (values.length == 1) {
      final char value = values[0];
      doAddCharValue(value);
    } else if (values.length > 1) {
      doAddCharValues(values);
    }
  }

  @Override
  public void addCharValues(final CharCollection values)
      throws TypeMismatchException {
    if ((type != Type.CHAR) && (count > 0)) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    if (count == 1) {
      final char value = values.iterator().next();
      doAddCharValue(value);
    } else if (count > 1) {
      doAddCharValues(values);
    }
  }

  private void doAddCharValues(final char... values) {
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

  private void doAddCharValues(final CharCollection values) {
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
      final Byte value = (Byte) valueOrValues;
      return value;
    } else {  // count > 1
      final ByteList values = (ByteList) valueOrValues;
      final byte value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setByteValue(final byte value) {
    type = Type.BYTE;
    valueOrValues = Byte.valueOf(value);
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
      final byte[] result = { value };
      return result;
    } else { // count > 1
      final ByteList values = (ByteList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setByteValues(final byte... values) {
    if (values.length == 0) {
      type = Type.BYTE;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final byte value = values[0];
      type = Type.BYTE;
      valueOrValues = Byte.valueOf(value);
      count = 1;
    } else {
      final ByteList list = new ByteArrayList(values);
      type = Type.BYTE;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setByteValues(final ByteCollection values) {
    final int n = values.size();
    if (n == 0) {
      type = Type.BYTE;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final byte value = values.iterator().next();
      type = Type.BYTE;
      valueOrValues = Byte.valueOf(value);
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
    doAddByteValue(value);
  }

  private void doAddByteValue(final byte value) {
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
  public void addByteValues(final byte... values) throws TypeMismatchException {
    if ((type != Type.BYTE) && (count > 0)) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    if (values.length == 1) {
      final byte value = values[0];
      doAddByteValue(value);
    } else if (values.length > 1) {
      doAddByteValues(values);
    }
  }

  @Override
  public void addByteValues(final ByteCollection values)
      throws TypeMismatchException {
    if ((type != Type.BYTE) && (count > 0)) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final byte value = values.iterator().next();
      doAddByteValue(value);
    } else if (n > 1) {
      doAddByteValues(values);
    }
  }

  private void doAddByteValues(final byte... values) {
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

  private void doAddByteValues(final ByteCollection values) {
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
      final Short value = (Short) valueOrValues;
      return value;
    } else {
      final ShortList values = (ShortList) valueOrValues;
      final short value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setShortValue(final short value) {
    type = Type.SHORT;
    valueOrValues = Short.valueOf(value);
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
      final short[] result = { value };
      return result;
    } else { // count > 1
      final ShortList values = (ShortList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setShortValues(final short... values) {
    if (values.length == 0) {
      type = Type.SHORT;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final short value = values[0];
      type = Type.SHORT;
      valueOrValues = Short.valueOf(value);
      count = 1;
    } else {
      final ShortList list = new ShortArrayList(values);
      type = Type.SHORT;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setShortValues(final ShortCollection values) {
    final int n = values.size();
    if (n == 0) {
      type = Type.SHORT;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final short value = values.iterator().next();
      type = Type.SHORT;
      valueOrValues = Short.valueOf(value);
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
    doAddShortValue(value);
  }

  private void doAddShortValue(final short value) {
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
  public void addShortValues(final short... values)
      throws TypeMismatchException {
    if ((type != Type.SHORT) && (count > 0)) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    if (values.length == 1) {
      final short value = values[0];
      doAddShortValue(value);
    } else if (values.length > 1) {
      doAddShortValues(values);
    }
  }

  @Override
  public void addShortValues(final ShortCollection values)
      throws TypeMismatchException {
    if ((type != Type.SHORT) && (count > 0)) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final short value = values.iterator().next();
      doAddShortValue(value);
    } else if (n > 1) {
      doAddShortValues(values);
    }
  }

  private void doAddShortValues(final short... values) {
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

  private void doAddShortValues(final ShortCollection values) {
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
      final Integer value = (Integer) valueOrValues;
      return value;
    } else {  //  count > 1
      final IntList values = (IntList) valueOrValues;
      final int value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setIntValue(final int value) {
    type = Type.INT;
    valueOrValues = Integer.valueOf(value);
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
      final int[] result = { value };
      return result;
    } else { // count > 1
      final IntList values = (IntList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setIntValues(final int... values) {
    if (values.length == 0) {
      type = Type.INT;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final int value = values[0];
      type = Type.INT;
      valueOrValues = Integer.valueOf(value);
      count = 1;
    } else {
      final IntList list = new IntArrayList(values);
      type = Type.INT;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setIntValues(final IntCollection values) {
    final int n = values.size();
    if (n == 0) {
      type = Type.INT;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final int value = values.iterator().next();
      type = Type.INT;
      valueOrValues = Integer.valueOf(value);
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
    doAddIntValue(value);
  }

  private void doAddIntValue(final int value) {
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
  public void addIntValues(final int... values) throws TypeMismatchException {
    if ((type != Type.INT) && (count > 0)) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    if (values.length == 1) {
      final int value = values[0];
      doAddIntValue(value);
    } else if (values.length > 1) {
      doAddIntValues(values);
    }
  }

  @Override
  public void addIntValues(final IntCollection values)
      throws TypeMismatchException {
    if ((type != Type.INT) && (count > 0)) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final int value = values.iterator().next();
      doAddIntValue(value);
    } else if (n > 1) {
      doAddIntValues(values);
    }
  }

  private void doAddIntValues(final int... values) {
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

  private void doAddIntValues(final IntCollection values) {
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
      final Long value = (Long) valueOrValues;
      return value;
    } else {
      final LongList values = (LongList) valueOrValues;
      final long value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setLongValue(final long value) {
    type = Type.LONG;
    valueOrValues = Long.valueOf(value);
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
      final long[] result = { value };
      return result;
    } else { // count > 1
      final LongList values = (LongList) valueOrValues;
      return values.toArray();
    }
  }

  @Override
  public void setLongValues(final long... values) {
    if (values.length == 0) {
      type = Type.LONG;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final long value = values[0];
      type = Type.LONG;
      valueOrValues = Long.valueOf(value);
      count = 1;
    } else {
      final LongList list = new LongArrayList(values);
      type = Type.LONG;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setLongValues(final LongCollection values) {
    final int n = values.size();
    if (n == 0) {
      type = Type.LONG;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final long value = values.iterator().next();
      type = Type.LONG;
      valueOrValues = Long.valueOf(value);
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
    doAddLongValue(value);
  }

  private void doAddLongValue(final long value) {
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
  public void addLongValues(final long... values) throws TypeMismatchException {
    if ((type != Type.LONG) && (count > 0)) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    if (values.length == 1) {
      final long value = values[0];
      doAddLongValue(value);
    } else if (values.length > 1) {
      doAddLongValues(values);
    }
  }

  @Override
  public void addLongValues(final LongCollection values)
      throws TypeMismatchException {
    if ((type != Type.LONG) && (count > 0)) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final long value = values.iterator().next();
      doAddLongValue(value);
    } else if (n > 1) {
      doAddLongValues(values);
    }
  }

  private void doAddLongValues(final long... values) {
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

  private void doAddLongValues(final LongCollection values) {
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
  public void setFloatValues(final float... values) {
    if (values.length == 0) {
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
  public void setFloatValues(final FloatCollection values) {
    final int n = values.size();
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
    doAddFloatValue(value);
  }

  private void doAddFloatValue(final float value) {
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
  public void addFloatValues(final float... values)
      throws TypeMismatchException {
    if ((type != Type.FLOAT) && (count > 0)) {
      throw new TypeMismatchException(Type.FLOAT.name(), type.name());
    }
    if (values.length == 1) {
      final float value = values[0];
      doAddFloatValue(value);
    } else if (values.length > 1) {
      doAddFloatValues(values);
    }
  }

  @Override
  public void addFloatValues(final FloatCollection values)
      throws TypeMismatchException {
    if ((type != Type.FLOAT) && (count > 0)) {
      throw new TypeMismatchException(Type.FLOAT.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final float value = values.iterator().next();
      doAddFloatValue(value);
    } else if (n > 1) {
      doAddFloatValues(values);
    }
  }

  private void doAddFloatValues(final float... values) {
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

  private void doAddFloatValues(final FloatCollection values) {
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
  public void setDoubleValues(final double... values) {
    if (values.length == 0) {
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
  public void setDoubleValues(final DoubleCollection values) {
    final int n = values.size();
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
    doAddDoubleValue(value);
  }

  private void doAddDoubleValue(final double value) {
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
  public void addDoubleValues(final double... values)
      throws TypeMismatchException {
    if ((type != Type.DOUBLE) && (count > 0)) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    if (values.length == 1) {
      final double value = values[0];
      doAddDoubleValue(value);
    } else if (values.length > 1) {
      doAddDoubleValues(values);
    }
  }

  @Override
  public void addDoubleValues(final DoubleCollection values)
      throws TypeMismatchException {
    if ((type != Type.DOUBLE) && (count > 0)) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final double value = values.iterator().next();
      doAddDoubleValue(value);
    } else if (n > 1) {
      doAddDoubleValues(values);
    }
  }

  private void doAddDoubleValues(final double... values) {
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

  private void doAddDoubleValues(final DoubleCollection values) {
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

  @SuppressWarnings("unchecked")
  @Override
  public String getStringValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.STRING) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      final String value = (String) valueOrValues;
      return value;
    } else {
      final List<String> values = (List<String>) valueOrValues;
      final String value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setStringValue(final String value) {
    type = Type.STRING;
    valueOrValues = value;
    count = 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  public String[] getStringValues() throws TypeMismatchException {
    if (type != Type.STRING) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else if (count == 1) {
      final String value = (String) valueOrValues;
      final String[] result = { value };
      return result;
    } else { // count > 1
      final List<String> values = (List<String>) valueOrValues;
      return values.toArray(new String[values.size()]);
    }
  }

  @Override
  public void setStringValues(final String... values) {
    if (values.length == 0) {
      type = Type.STRING;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.STRING;
      valueOrValues = values[0];
      count = 1;
    } else {
      final List<String> list = new ArrayList<>(values.length);
      for (final String value : values) {
        list.add(value);
      }
      type = Type.STRING;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setStringValues(final Collection<String> values) {
    final int n = values.size();
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
      for (final String value : values) {
        list.add(value);
      }
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
    doAddStringValue(value);
  }

  @SuppressWarnings("unchecked")
  private void doAddStringValue(final String value) {
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
      final List<String> list = (List<String>) valueOrValues;
      list.add(value);
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addStringValues(final String... values)
      throws TypeMismatchException {
    if ((type != Type.STRING) && (count > 0)) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    if (values.length == 1) {
      final String value = values[0];
      doAddStringValue(value);
    } else if (values.length > 1) {
      doAddStringValues(values);
    }
  }

  @Override
  public void addStringValues(final Collection<String> values)
      throws TypeMismatchException {
    if ((type != Type.STRING) && (count > 0)) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final String value = values.iterator().next();
      doAddStringValue(value);
    } else if (n > 1) {
      doAddStringValues(values);
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddStringValues(final String... values) {
    if (count == 0) {
      final List<String> list = new ArrayList<>(values.length);
      for (final String value : values) {
        list.add(value);
      }
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final String oldValue = (String) valueOrValues;
      final List<String> list = new ArrayList<>();
      list.add(oldValue);
      for (final String value : values) {
        list.add(value);
      }
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final List<String> list = (List<String>) valueOrValues;
      for (final String value : values) {
        list.add(value);
      }
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddStringValues(final Collection<String> values) {
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
      final List<String> list = (List<String>) valueOrValues;
      list.addAll(values);
      type = Type.STRING;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Date getDateValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DATE) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      final Date value = (Date) valueOrValues;
      return value;
    } else {
      final List<Date> values = (List<Date>) valueOrValues;
      final Date value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setDateValue(final Date value) {
    type = Type.DATE;
    valueOrValues = Assignment.clone(value);
    count = 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Date[] getDateValues() throws TypeMismatchException {
    if (type != Type.DATE) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    } else if (count == 1) {
      final Date value = (Date) valueOrValues;
      final Date[] result = { Assignment.clone(value) };
      return result;
    } else { // count > 1
      final List<Date> values = (List<Date>) valueOrValues;
      final Date[] result = new Date[values.size()];
      int i = 0;
      for (final Date value : values) {
        result[i++] = Assignment.clone(value);
      }
      return result;
    }
  }

  @Override
  public void setDateValues(final Date... values) {
    if (values.length == 0) {
      type = Type.DATE;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      final Date value = values[0];
      type = Type.DATE;
      valueOrValues = Assignment.clone(value);
      count = 1;
    } else {
      final List<Date> list = new ArrayList<>(values.length);
      for (final Date value : values) {
        list.add(Assignment.clone(value));
      }
      type = Type.DATE;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setDateValues(final Collection<Date> values) {
    final int n = values.size();
    if (n == 0) {
      type = Type.DATE;
      valueOrValues = null;
      count = 0;
    } else if (n == 1) {
      final Date value = values.iterator().next();
      type = Type.DATE;
      valueOrValues = Assignment.clone(value);
      count = 1;
    } else {
      final List<Date> list = new ArrayList<>(n);
      for (final Date value : values) {
        list.add(Assignment.clone(value));
      }
      type = Type.DATE;
      valueOrValues = list;
      count = n;
    }
  }

  @Override
  public void addDateValue(final Date value) throws TypeMismatchException {
    if ((type != Type.DATE) && (count > 0)) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    doAddDateValue(true, value);
  }

  @SuppressWarnings("unchecked")
  private void doAddDateValue(final boolean cloneValue, final Date value) {
    if (count == 0) {
      type = Type.DATE;
      count = 1;
      if (cloneValue) {
        valueOrValues = Assignment.clone(value);
      } else {
        valueOrValues = value;
      }
    } else if (count == 1) {
      final Date oldValue = (Date) valueOrValues;
      final List<Date> list = new ArrayList<>(2);
      list.add(oldValue);
      if (cloneValue) {
        list.add(Assignment.clone(value));
      } else {
        list.add(value);
      }
      type = Type.DATE;
      valueOrValues = list;
      count = 2;
    } else { // count > 1
      final List<Date> list = (List<Date>) valueOrValues;
      if (cloneValue) {
        list.add(Assignment.clone(value));
      } else {
        list.add(value);
      }
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addDateValues(final Date... values) throws TypeMismatchException {
    if ((type != Type.DATE) && (count > 0)) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    if (values.length == 1) {
      final Date value = values[0];
      doAddDateValue(true, value);
    } else if (values.length > 1) {
      doAddDateValues(true, values);
    }
  }

  @Override
  public void addDateValues(final Collection<Date> values)
      throws TypeMismatchException {
    if ((type != Type.DATE) && (count > 0)) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final Date value = values.iterator().next();
      doAddDateValue(true, value);
    } else if (n > 1) {
      doAddDateValues(true, values);
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddDateValues(final boolean cloneValue, final Date... values) {
    if (count == 0) {
      final List<Date> list = new ArrayList<>(values.length);
      if (cloneValue) {
        for (final Date value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        for (final Date value : values) {
          list.add(value);
        }
      }
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final Date oldValue = (Date) valueOrValues;
      final List<Date> list = new ArrayList<>();
      list.add(oldValue);
      if (cloneValue) {
        for (final Date value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        for (final Date value : values) {
          list.add(value);
        }
      }
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final List<Date> list = (List<Date>) valueOrValues;
      if (cloneValue) {
        for (final Date value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        for (final Date value : values) {
          list.add(value);
        }
      }
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddDateValues(final boolean cloneValue,
      final Collection<Date> values) {
    if (count == 0) {
      final List<Date> list = new ArrayList<>(values.size());
      if (cloneValue) {
        for (final Date value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        for (final Date value : values) {
          list.add(value);
        }
      }
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final Date oldValue = (Date) valueOrValues;
      final List<Date> list = new ArrayList<>();
      list.add(oldValue);
      if (cloneValue) {
        for (final Date value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        for (final Date value : values) {
          list.add(value);
        }
      }
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final List<Date> list = (List<Date>) valueOrValues;
      if (cloneValue) {
        for (final Date value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        for (final Date value : values) {
          list.add(value);
        }
      }
      type = Type.DATE;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public BigInteger getBigIntegerValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BIG_INTEGER) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      final BigInteger value = (BigInteger) valueOrValues;
      return value;
    } else {
      final List<BigInteger> values = (List<BigInteger>) valueOrValues;
      final BigInteger value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setBigIntegerValue(final BigInteger value) {
    type = Type.BIG_INTEGER;
    valueOrValues = value;
    count = 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  public BigInteger[] getBigIntegerValues() throws TypeMismatchException {
    if (type != Type.BIG_INTEGER) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
    } else if (count == 1) {
      final BigInteger value = (BigInteger) valueOrValues;
      final BigInteger[] result = { value };
      return result;
    } else { // count > 1
      final List<BigInteger> values = (List<BigInteger>) valueOrValues;
      return values.toArray(new BigInteger[values.size()]);
    }
  }

  @Override
  public void setBigIntegerValues(final BigInteger... values) {
    if (values.length == 0) {
      type = Type.BIG_INTEGER;
      valueOrValues = null;
      count = 1;
    } else if (values.length == 1) {
      type = Type.BIG_INTEGER;
      valueOrValues = values[0];
      count = 1;
    } else {
      final List<BigInteger> list = new ArrayList<>(values.length);
      for (final BigInteger value : values) {
        list.add(value);
      }
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setBigIntegerValues(final Collection<BigInteger> values) {
    final int n = values.size();
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
      for (final BigInteger value : values) {
        list.add(value);
      }
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
    doAddBigIntegerValue(value);
  }

  @SuppressWarnings("unchecked")
  private void doAddBigIntegerValue(final BigInteger value) {
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
      final List<BigInteger> list = (List<BigInteger>) valueOrValues;
      list.add(value);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addBigIntegerValues(final BigInteger... values)
      throws TypeMismatchException {
    if ((type != Type.BIG_INTEGER) && (count > 0)) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    if (values.length == 1) {
      final BigInteger value = values[0];
      doAddBigIntegerValue(value);
    } else if (values.length > 1) {
      doAddBigIntegerValues(values);
    }
  }

  @Override
  public void addBigIntegerValues(final Collection<BigInteger> values)
      throws TypeMismatchException {
    if ((type != Type.BIG_INTEGER) && (count > 0)) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      doAddBigIntegerValue(values.iterator().next());
    } else if (n > 1) {
      doAddBigIntegerValues(values);
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddBigIntegerValues(final BigInteger... values) {
    if (count == 0) {
      final List<BigInteger> list = new ArrayList<>(values.length);
      for (final BigInteger value : values) {
        list.add(value);
      }
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final BigInteger oldValue = (BigInteger) valueOrValues;
      final List<BigInteger> list = new ArrayList<>();
      list.add(oldValue);
      for (final BigInteger value : values) {
        list.add(value);
      }
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final List<BigInteger> list = (List<BigInteger>) valueOrValues;
      for (final BigInteger value : values) {
        list.add(value);
      }
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddBigIntegerValues(final Collection<BigInteger> values) {
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
      final List<BigInteger> list = (List<BigInteger>) valueOrValues;
      list.addAll(values);
      type = Type.BIG_INTEGER;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public BigDecimal getBigDecimalValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BIG_DECIMAL) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      final BigDecimal value = (BigDecimal) valueOrValues;
      return value;
    } else {
      final List<BigDecimal> values = (List<BigDecimal>) valueOrValues;
      final BigDecimal value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setBigDecimalValue(final BigDecimal value) {
    type = Type.BIG_DECIMAL;
    valueOrValues = value;
    count = 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  public BigDecimal[] getBigDecimalValues() throws TypeMismatchException {
    if (type != Type.BIG_DECIMAL) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
    } else if (count == 1) {
      final BigDecimal value = (BigDecimal) valueOrValues;
      final BigDecimal[] result = { value };
      return result;
    } else { // count > 1
      final List<BigDecimal> values = (List<BigDecimal>) valueOrValues;
      return values.toArray(new BigDecimal[values.size()]);
    }
  }

  @Override
  public void setBigDecimalValues(final BigDecimal... values) {
    if (values.length == 0) {
      type = Type.BIG_DECIMAL;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.BIG_DECIMAL;
      valueOrValues = values[0];
      count = 1;
    } else {
      final List<BigDecimal> list = new ArrayList<>(values.length);
      for (final BigDecimal value : values) {
        list.add(value);
      }
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setBigDecimalValues(final Collection<BigDecimal> values) {
    final int n = values.size();
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
      for (final BigDecimal value : values) {
        list.add(value);
      }
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
    doAddBigDecimalValue(value);
  }

  @SuppressWarnings("unchecked")
  private void doAddBigDecimalValue(final BigDecimal value) {
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
      final List<BigDecimal> list = (List<BigDecimal>) valueOrValues;
      list.add(value);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addBigDecimalValues(final BigDecimal... values)
      throws TypeMismatchException {
    if ((type != Type.BIG_DECIMAL) && (count > 0)) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    if (values.length == 1) {
      final BigDecimal value = values[0];
      doAddBigDecimalValue(value);
    } else if (values.length > 1) {
      doAddBigDecimalValues(values);
    }
  }

  @Override
  public void addBigDecimalValues(final Collection<BigDecimal> values)
      throws TypeMismatchException {
    if ((type != Type.BIG_DECIMAL) && (count > 0)) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final BigDecimal value = values.iterator().next();
      doAddBigDecimalValue(value);
    } else if (n > 1) {
      doAddBigDecimalValues(values);
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddBigDecimalValues(final BigDecimal... values) {
    if (count == 0) {
      final List<BigDecimal> list = new ArrayList<>(values.length);
      for (final BigDecimal value : values) {
        list.add(value);
      }
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final BigDecimal oldValue = (BigDecimal) valueOrValues;
      final List<BigDecimal> list = new ArrayList<>();
      list.add(oldValue);
      for (final BigDecimal value : values) {
        list.add(value);
      }
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final List<BigDecimal> list = (List<BigDecimal>) valueOrValues;
      for (final BigDecimal value : values) {
        list.add(value);
      }
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddBigDecimalValues(final Collection<BigDecimal> values) {
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
      final List<BigDecimal> list = (List<BigDecimal>) valueOrValues;
      list.addAll(values);
      type = Type.BIG_DECIMAL;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public byte[] getByteArrayValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BYTE_ARRAY) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      final byte[] value = (byte[]) valueOrValues;
      return value;
    } else {
      final List<byte[]> values = (List<byte[]>) valueOrValues;
      final byte[] value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setByteArrayValue(final byte[] value) {
    type = Type.BYTE_ARRAY;
    valueOrValues = Assignment.clone(value);
    count = 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  public byte[][] getByteArrayValues() throws TypeMismatchException {
    if (type != Type.BYTE_ARRAY) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else if (count == 1) {
      final byte[] value = (byte[]) valueOrValues;
      final byte[][] result = { Assignment.clone(value) };
      return result;
    } else { // count > 1
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
  public void setByteArrayValues(final byte[]... values) {
    if (values.length == 0) {
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
  public void setByteArrayValues(final Collection<byte[]> values) {
    final int n = values.size();
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
    doAddByteArrayValue(true, value);
  }

  @SuppressWarnings("unchecked")
  private void doAddByteArrayValue(final boolean cloneValue, final byte[] value) {
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
  public void addByteArrayValues(final byte[]... values)
      throws TypeMismatchException {
    if ((type != Type.BYTE_ARRAY) && (count > 0)) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    if (values.length == 1) {
      final byte[] value = values[0];
      doAddByteArrayValue(true, value);
    } else if (values.length > 1) {
      doAddByteArrayValues(true, values);
    }
  }

  @Override
  public void addByteArrayValues(final Collection<byte[]> values)
      throws TypeMismatchException {
    if ((type != Type.BYTE_ARRAY) && (count > 0)) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final byte[] value = values.iterator().next();
      doAddByteArrayValue(true, value);
    } else if (n > 1) {
      doAddByteArrayValues(true, values);
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddByteArrayValues(final boolean cloneValue,
      final byte[]... values) {
    if (count == 0) {
      final List<byte[]> list = new ArrayList<>(values.length);
      if (cloneValue) {
        for (final byte[] value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        for (final byte[] value : values) {
          list.add(value);
        }
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
        for (final byte[] value : values) {
          list.add(value);
        }
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final List<byte[]> list = (List<byte[]>) valueOrValues;
      if (cloneValue) {
        for (final byte[] value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        for (final byte[] value : values) {
          list.add(value);
        }
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddByteArrayValues(final boolean cloneValue,
      final Collection<byte[]> values) {
    if (count == 0) {
      final List<byte[]> list = new ArrayList<>(values.size());
      if (cloneValue) {
        for (final byte[] value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        for (final byte[] value : values) {
          list.add(value);
        }
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
        for (final byte[] value : values) {
          list.add(value);
        }
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final List<byte[]> list = (List<byte[]>) valueOrValues;
      if (cloneValue) {
        for (final byte[] value : values) {
          list.add(Assignment.clone(value));
        }
      } else {
        for (final byte[] value : values) {
          list.add(value);
        }
      }
      type = Type.BYTE_ARRAY;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<?> getClassValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.CLASS) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      final Class<?> value = (Class<?>) valueOrValues;
      return value;
    } else {
      final List<Class<?>> values = (List<Class<?>>) valueOrValues;
      final Class<?> value = values.iterator().next();
      return value;
    }
  }

  @Override
  public void setClassValue(final Class<?> value) {
    type = Type.CLASS;
    valueOrValues = value;
    count = 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<?>[] getClassValues() throws TypeMismatchException {
    if (type != Type.CLASS) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    } else if (count == 1) {
      final Class<?> value = (Class<?>) valueOrValues;
      final Class<?>[] result = { value };
      return result;
    } else { // count > 1
      final List<Class<?>> values = (List<Class<?>>) valueOrValues;
      return values.toArray(new Class<?>[values.size()]);
    }
  }

  @Override
  public void setClassValues(final Class<?>... values) {
    if (values.length == 0) {
      type = Type.CLASS;
      valueOrValues = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.CLASS;
      valueOrValues = values[0];
      count = 1;
    } else {
      final List<Class<?>> list = new ArrayList<>(values.length);
      for (final Class<?> value : values) {
        list.add(value);
      }
      type = Type.CLASS;
      valueOrValues = list;
      count = values.length;
    }
  }

  @Override
  public void setClassValues(final Collection<Class<?>> values) {
    final int n = values.size();
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
      for (final Class<?> value : values) {
        list.add(value);
      }
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
    doAddClassValue(value);
  }

  @SuppressWarnings("unchecked")
  private void doAddClassValue(final Class<?> value) {
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
      final List<Class<?>> list = (List<Class<?>>) valueOrValues;
      list.add(value);
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    }
  }

  @Override
  public void addClassValues(final Class<?>... values)
      throws TypeMismatchException {
    if ((type != Type.CLASS) && (count > 0)) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    if (values.length == 1) {
      doAddClassValue(values[0]);
    } else if (values.length > 1) {
      doAddClassValues(values);
    }
  }

  @Override
  public void addClassValues(final Collection<Class<?>> values)
      throws TypeMismatchException {
    if ((type != Type.CLASS) && (count > 0)) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    final int n = values.size();
    if (n == 1) {
      final Class<?> value = values.iterator().next();
      doAddClassValue(value);
    } else if (n > 1) {
      doAddClassValues(values);
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddClassValues(final Class<?>... values) {
    if (count == 0) {
      final List<Class<?>> list = new ArrayList<>(values.length);
      for (final Class<?> value : values) {
        list.add(value);
      }
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    } else if (count == 1) {
      final Class<?> oldValue = (Class<?>) valueOrValues;
      final List<Class<?>> list = new ArrayList<>();
      list.add(oldValue);
      for (final Class<?> value : values) {
        list.add(value);
      }
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    } else { // count > 1
      final List<Class<?>> list = (List<Class<?>>) valueOrValues;
      for (final Class<?> value : values) {
        list.add(value);
      }
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    }
  }

  @SuppressWarnings("unchecked")
  private void doAddClassValues(final Collection<Class<?>> values) {
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
      final List<Class<?>> list = (List<Class<?>>) valueOrValues;
      list.addAll(values);
      type = Type.CLASS;
      valueOrValues = list;
      count = list.size();
    }
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
      final boolean[] result = { value };
      return result;
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
      final char[] result = { value };
      return result;
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
      final byte[] result = { value };
      return result;
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
      final short[] result = { value };
      return result;
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
      final int[] result = { value };
      return result;
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
      final long[] result = { value };
      return result;
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
      final float[] result = { value };
      return result;
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
      final double[] result = { value };
      return result;
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
      final String[] result = { value };
      return result;
    } else { // count > 1
      final Object list = valueOrValues;
      return TypeUtils.collectionAsStrings(type, list);
    }
  }

  @Override
  public Date getValueAsDate() throws TypeConvertException,
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
  public Date[] getValuesAsDate() throws TypeConvertException {
    if (count == 0) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    } else if (count == 1) {
      final Date value = TypeUtils.objectAsDate(type, valueOrValues);
      final Date[] result = { value };
      return result;
    } else { // count > 1
      return TypeUtils.collectionAsDates(type, valueOrValues);
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
      final byte[][] result = { value };
      return result;
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
      final Class<?>[] result = { value };
      return result;
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
      final BigInteger[] result = { value };
      return result;
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
      final BigDecimal[] result = { value };
      return result;
    } else { // count > 1
      return TypeUtils.collectionAsBigDecimals(type, valueOrValues);
    }
  }

  @Override
  public BasicMultiValues clone() {
    try {
      final BasicMultiValues result = (BasicMultiValues) super.clone();
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
    if (! (obj instanceof BasicMultiValues)) {
      return false;
    }
    final BasicMultiValues other = (BasicMultiValues) obj;
    if ((type != other.type) || (count != other.count)) {
      return false;
    }
    if (count == 1) {
      return TypeUtils.equalsObject(type, valueOrValues, other.valueOrValues);
    } else if (count > 1) {
      return TypeUtils.equalsCollections(type, valueOrValues,
          other.valueOrValues);
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
