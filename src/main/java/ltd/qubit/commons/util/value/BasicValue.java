////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.error.TypeConvertException;
import ltd.qubit.commons.error.TypeMismatchException;
import ltd.qubit.commons.error.UnsupportedDataTypeException;
import ltd.qubit.commons.io.error.InvalidFormatException;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.lang.TypeUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.XmlException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The basic implementation of the {@link Value} interface.
 *
 * @author Haixing Hu
 */
public class BasicValue implements Value {

  static {
    XmlSerialization.register(BasicValue.class, BasicValueXmlSerializer.INSTANCE);
  }

  protected Type type;
  protected Object value;

  public BasicValue() {
    type = DEFAULT_TYPE;
    value = null;
  }

  public BasicValue(final Type type) {
    this.type = requireNonNull("type", type);
    value = null;
  }

  public BasicValue(final boolean value) {
    this();
    setBooleanValue(value);
  }

  public BasicValue(final char value) {
    this();
    setCharValue(value);
  }

  public BasicValue(final byte value) {
    this();
    setByteValue(value);
  }

  public BasicValue(final short value) {
    this();
    setShortValue(value);
  }

  public BasicValue(final int value) {
    this();
    setIntValue(value);
  }

  public BasicValue(final long value) {
    this();
    setLongValue(value);
  }

  public BasicValue(final float value) {
    this();
    setFloatValue(value);
  }

  public BasicValue(final double value) {
    this();
    setDoubleValue(value);
  }

  public BasicValue(@Nullable final String value) {
    this();
    setStringValue(value);
  }

  public BasicValue(@Nullable final Date value) {
    this();
    setDateValue(value);
  }

  public BasicValue(@Nullable final BigInteger value) {
    this();
    setBigIntegerValue(value);
  }

  public BasicValue(@Nullable final BigDecimal value) {
    this();
    setBigDecimalValue(value);
  }

  public BasicValue(@Nullable final byte[] value) {
    this();
    setByteArrayValue(value);
  }

  public BasicValue(@Nullable final Class<?> value) {
    this();
    setClassValue(value);
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public void setType(final Type type) {
    this.type = requireNonNull("type", type);
  }

  @Override
  public boolean isEmpty() {
    return value == null;
  }

  @Override
  public void clear() {
    value = null;
  }

  @Override
  public void assignValue(final Value other) {
    if (this == other) {
      return;
    }
    final Type otherType = other.getType();
    if (other.isEmpty()) {
      value = null;
      this.type = otherType;
      return;
    }
    //  note that the other Value may NOT store its value as an Object;
    //  for example, the other Value implementation choose to store all value
    //  in its string representation.
    switch (otherType) {
      case BOOL: {
        final boolean otherValue = other.getBooleanValue();
        this.type = Type.BOOL;
        this.value = Boolean.valueOf(otherValue);
        return;
      }
      case CHAR: {
        final char otherValue = other.getCharValue();
        this.type = Type.CHAR;
        this.value = Character.valueOf(otherValue);
        return;
      }
      case BYTE: {
        final byte otherValue = other.getByteValue();
        this.type = Type.BYTE;
        this.value = Byte.valueOf(otherValue);
        return;
      }
      case SHORT: {
        final short otherValue = other.getShortValue();
        this.type = Type.SHORT;
        this.value = Short.valueOf(otherValue);
        return;
      }
      case INT: {
        final int otherValue = other.getIntValue();
        this.type = Type.INT;
        this.value = Integer.valueOf(otherValue);
        return;
      }
      case LONG: {
        final long otherValue = other.getLongValue();
        this.type = Type.LONG;
        this.value = Long.valueOf(otherValue);
        return;
      }
      case FLOAT: {
        final float otherValue = other.getFloatValue();
        this.type = Type.FLOAT;
        this.value = Float.valueOf(otherValue);
        return;
      }
      case DOUBLE: {
        final double otherValue = other.getDoubleValue();
        this.type = Type.DOUBLE;
        this.value = Double.valueOf(otherValue);
        return;
      }
      case STRING: {
        final String otherValue = other.getStringValue();
        this.type = Type.STRING;
        this.value = otherValue;
        return;
      }
      case DATE: {
        final Date otherValue = other.getDateValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        this.type = Type.DATE;
        this.value = otherValue;
        return;
      }
      case BYTE_ARRAY: {
        final byte[] otherValue = other.getByteArrayValue();
        // don't need to clone the value, since it is already the cloned
        // copy of the object in other.
        this.type = Type.BYTE_ARRAY;
        this.value = otherValue;
        return;
      }
      case CLASS: {
        final Class<?> otherValue = other.getClassValue();
        this.type = Type.CLASS;
        this.value = otherValue;
        return;
      }
      case BIG_INTEGER: {
        final BigInteger otherValue = other.getBigIntegerValue();
        this.type = Type.BIG_INTEGER;
        this.value = otherValue;
        return;
      }
      case BIG_DECIMAL: {
        final BigDecimal otherValue = other.getBigDecimalValue();
        this.type = Type.BIG_DECIMAL;
        this.value = otherValue;
        return;
      }
      default:
        throw new UnsupportedDataTypeException(otherType);
    }
  }

  @Override
  public void readValue(final Type type, final InputStream in)
      throws IOException {
    try {
      final Object otherValue = TypeUtils.readObject(type, in);
      this.type = type;
      this.value = otherValue;
    } catch (final UnsupportedDataTypeException | ClassNotFoundException e) {
      throw new InvalidFormatException(e);
    }
  }

  @Override
  public void writeValue(final OutputStream out) throws IOException {
    TypeUtils.writeObject(type, value, out);
  }

  @Override
  public void getValueFromXml(final Type type, final Element parent,
      final String tagName, final String prevSpaceAttr) throws XmlException {
    final Element child = DomUtils.getOptChild(parent, tagName);
    if (child == null) {
      this.type = type;
      value = null;
    } else {
      final Object otherValue = TypeUtils.fromXmlNode(type, child, prevSpaceAttr);
      this.type = type;
      this.value = otherValue;
    }
  }

  @Override
  public void appendValueToXml(final Document doc, final Element parent,
      final String tagName, @Nullable final String prevSpaceAttr) {
    if (value != null) {
      final Element node = TypeUtils.toXmlNode(type, value, doc, tagName,
          prevSpaceAttr);
      parent.appendChild(node);
    }
  }

  @Override
  public boolean getBooleanValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BOOL) {
      throw new TypeMismatchException(Type.BOOL.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Boolean) value;
  }

  @Override
  public void setBooleanValue(final boolean value) {
    type = Type.BOOL;
    this.value = Boolean.valueOf(value);
  }

  @Override
  public char getCharValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.CHAR) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Character) value;
  }

  @Override
  public void setCharValue(final char value) {
    type = Type.CHAR;
    this.value = Character.valueOf(value);
  }

  @Override
  public byte getByteValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BYTE) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Byte) value;
  }

  @Override
  public void setByteValue(final byte value) {
    type = Type.BYTE;
    this.value = Byte.valueOf(value);
  }

  @Override
  public short getShortValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.SHORT) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Short) value;
  }

  @Override
  public void setShortValue(final short value) {
    type = Type.SHORT;
    this.value = Short.valueOf(value);
  }

  @Override
  public int getIntValue() throws TypeMismatchException, NoSuchElementException {
    if (type != Type.INT) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Integer) value;
  }

  @Override
  public void setIntValue(final int value) {
    type = Type.INT;
    this.value = Integer.valueOf(value);
  }

  @Override
  public long getLongValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.LONG) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Long) value;
  }

  @Override
  public void setLongValue(final long value) {
    type = Type.LONG;
    this.value = Long.valueOf(value);
  }

  @Override
  public float getFloatValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.FLOAT) {
      throw new TypeMismatchException(Type.FLOAT.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Float) value;
  }

  @Override
  public void setFloatValue(final float value) {
    type = Type.FLOAT;
    this.value = Float.valueOf(value);
  }

  @Override
  public double getDoubleValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DOUBLE) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Double) value;
  }

  @Override
  public void setDoubleValue(final double value) {
    type = Type.DOUBLE;
    this.value = Double.valueOf(value);
  }

  @Override
  public String getStringValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.STRING) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (String) value;
  }

  @Override
  public void setStringValue(final String value) {
    type = Type.STRING;
    this.value = value;
  }

  @Override
  public Date getDateValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DATE) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return Assignment.clone((Date) value);
  }

  @Override
  public void setDateValue(final Date value) {
    type = Type.DATE;
    this.value = Assignment.clone(value);
  }

  @Override
  public BigInteger getBigIntegerValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BIG_INTEGER) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (BigInteger) value;
  }

  @Override
  public void setBigIntegerValue(final BigInteger value) {
    type = Type.BIG_INTEGER;
    this.value = value;
  }

  @Override
  public BigDecimal getBigDecimalValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BIG_DECIMAL) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (BigDecimal) value;
  }

  @Override
  public void setBigDecimalValue(final BigDecimal value) {
    type = Type.BIG_DECIMAL;
    this.value = value;
  }

  @Override
  public byte[] getByteArrayValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BYTE_ARRAY) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return Assignment.clone((byte[]) value);
  }

  @Override
  public void setByteArrayValue(final byte[] value) {
    type = Type.BYTE_ARRAY;
    this.value = Assignment.clone(value);
  }

  @Override
  public Class<?> getClassValue() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.CLASS) {
      throw new TypeMismatchException(Type.CLASS.name(), type.name());
    }
    if (value == null) {
      throw new NoSuchElementException();
    }
    return (Class<?>) value;
  }

  @Override
  public void setClassValue(final Class<?> value) {
    type = Type.CLASS;
    this.value = value;
  }

  @Override
  public boolean getValueAsBoolean() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsBoolean(type, value);
  }

  @Override
  public char getValueAsChar() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsChar(type, value);
  }

  @Override
  public byte getValueAsByte() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsByte(type, value);
  }

  @Override
  public short getValueAsShort() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsShort(type, value);
  }

  @Override
  public int getValueAsInt() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsInt(type, value);
  }

  @Override
  public long getValueAsLong() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsLong(type, value);
  }

  @Override
  public float getValueAsFloat() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsFloat(type, value);
  }

  @Override
  public double getValueAsDouble() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsDouble(type, value);
  }

  @Override
  public String getValueAsString() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    final Type theType = getType();
    return TypeUtils.objectAsString(theType, value);
  }

  @Override
  public Date getValueAsDate() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    final Type theType = getType();
    return TypeUtils.objectAsDate(theType, value);
  }

  @Override
  public byte[] getValueAsByteArray() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    final Type theType = getType();
    return TypeUtils.objectAsByteArray(theType, value);
  }

  @Override
  public Class<?> getValueAsClass() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsClass(type, value);
  }

  @Override
  public BigInteger getValueAsBigInteger() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    return TypeUtils.objectAsBigInteger(type, value);
  }

  @Override
  public BigDecimal getValueAsBigDecimal() throws TypeConvertException,
      NoSuchElementException {
    if (value == null) {
      throw new NoSuchElementException();
    }
    final Type theType = getType();
    return TypeUtils.objectAsBigDecimal(theType, value);
  }

  @Override
  public BasicValue clone() {
    try {
      final BasicValue result = (BasicValue) super.clone();
      result.type = type;
      result.value = TypeUtils.cloneObject(type, type);
      return result;
    } catch (final CloneNotSupportedException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  @Override
  public int hashCode() {
    final int multiplier = 13;
    int code = 2;
    code = Hash.combine(code, multiplier, type);
    code = Hash.combine(code, multiplier, TypeUtils.hashCodeOfObject(type, value));
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    if (! (obj instanceof BasicValue)) {
      return false;
    }
    final BasicValue other = (BasicValue) obj;
    return (type == other.type)
        && TypeUtils.equalsObject(type, value, other.value);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("type", type)
               .append("value", value)
               .toString();
  }
}
