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
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.text.xml.XmlException;

/**
 * A {@link Value} object represents a value of common types.
 *
 * <p>Currently the {@link Value} interface support the following types:
 * <ul>
 * <li>{@code boolean}</li>
 * <li>{@code char}</li>
 * <li>{@code byte}</li>
 * <li>{@code short}</li>
 * <li>{@code int}</li>
 * <li>{@code long}</li>
 * <li>{@code float}</li>
 * <li>{@code double}</li>
 * <li>{@code String}</li>
 * <li>{@code java.util.Date}</li>
 * <li>{@code java.math.BigDecimal}</li>
 * <li>{@code java.math.BigInteger}</li>
 * <li>{@code byte[]}</li>
 * <li>{@code Class}</li>
 * </ul>
 *
 * @author Haixing Hu
 */
public interface Value extends CloneableEx<Value> {

  /**
   * The default type of the {@link Value} objects.
   */
  Type DEFAULT_TYPE = Type.STRING;

  /**
   * Gets the type of this object.
   *
   * @return the type of this object.
   */
  Type getType();

  /**
   * Sets the type of this object.
   *
   * <p>NOTE: If the new type is the same as the old type of this object, nothing
   * is done; otherwise, the type of this object will be set to the new type,
   * and all old value of this object will be cleared.
   *
*
   * @param type
   *          the new type of this object.
   */
  void setType(Type type);

  /**
   * Tests whether this object has any value.
   *
   * @return true if this object has any value; false otherwise.
   */
  boolean isEmpty();

  /**
   * Clears the value of this object.
   */
  void clear();

  /**
   * Unions the value and type of another {@link Value} object to this
   * {@link Value} object .
   *
   * @param other
   *          the other {@link Value} object.
   */
  void assignValue(Value other);

  /**
   * Reads value from an input stream.
   *
   * @param type
   *          the type of value to be read.
   * @param in
   *          the input stream.
   * @throws IOException
   *           If any I/O error occurred.
   */
  void readValue(Type type, InputStream in) throws IOException;

  /**
   * Writes value to an output stream.
   *
   * <p>Note that this function does NOT write the type to the output stream.
   *
   * @param out
   *          the output stream.
   * @throws IOException
   *           If any I/O error occurred.
   */
  void writeValue(OutputStream out) throws IOException;

  /**
   * Deserialize the value from the children of a DOM node.
   *
   * @param type
   *          the type of the value.
   * @param parent
   *          the parent DOM node.
   * @param tagName
   *          the tag name of the value nodes.
   * @param prevSpaceAttr
   *          the attribute name of the preserve space attribute. If it is null,
   *          no preserve space attribute is used.
   * @throws XmlException
   *           If any XML related error occurred.
   */
  void getValueFromXml(Type type, Element parent, String tagName,
          @Nullable String prevSpaceAttr) throws XmlException;

  /**
   * Serializes the value as a child nodes of a DOM node.
   *
   * @param doc
   *          the DOM document.
   * @param parent
   *          the parent DOM node.
   * @param tagName
   *          the tag name of value nodes.
   * @param prevSpaceAttr
   *          the attribute name of the preserve space attribute. If it is null,
   *          no preserve space attribute is used.
   * @throws XmlException
   *           If any XML related error occurred.
   */
  void appendValueToXml(Document doc, Element parent,
          String tagName, @Nullable String prevSpaceAttr) throws XmlException;

  /**
   * Gets the {@code boolean} value of this object.
   *
   * @return the {@code boolean} value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BOOL}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  boolean getBooleanValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@code boolean} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#BOOL}, all previous value of this object is cleared, and the
   * new value is set to this object.
   *
*
   * @param value
   *          the new {@code boolean} value to be set to this object.
   */
  void setBooleanValue(boolean value);

  /**
   * Gets the {@code char} value of this object.
   *
   * @return the {@code char} value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CHAR}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  char getCharValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@code char} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#CHAR}, all previous value of this object is cleared, and the
   * new value is set to this object.
   *
*
   * @param value
   *          the new {@code char} value to be set to this object.
   */
  void setCharValue(char value);

  /**
   * the {@code byte} value of this object.
   *
   * @return the {@code byte} value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  byte getByteValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@code byte} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#BYTE}, all previous value of this object is cleared, and the
   * new value is set to this object.
   *
*
   * @param value
   *          the new {@code byte} value to be set to this object.
   */
  void setByteValue(byte value);

  /**
   * the {@code short} value of this object.
   *
   * @return the {@code short} value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#SHORT}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  short getShortValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@code short} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#SHORT}, all previous value of this object is cleared, and the
   * new value is set to this object.
   *
*
   * @param value
   *          the new {@code short} value to be set to this object.
   */
  void setShortValue(short value);

  /**
   * the {@code int} value of this object.
   *
   * @return the {@code int} value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#INT}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  int getIntValue() throws TypeMismatchException, NoSuchElementException;

  /**
   * Sets a {@code int} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#INT}, all previous value of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *          the new {@code int} value to be set to this object.
   */
  void setIntValue(int value);

  /**
   * the {@code long} value of this object.
   *
   * @return the {@code long} value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#LONG}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  long getLongValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@code long} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#LONG}, all previous value of this object is cleared, and the
   * new value is set to this object.
*
   * @param value
   *          the new {@code long} value to be set to this object.
   */
  void setLongValue(long value);

  /**
   * the {@code float} value of this object.
   *
   * @return the {@code float} value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#FLOAT}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  float getFloatValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@code float} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#FLOAT}, all previous value of this object is cleared, and the
   * new value is set to this object.
*
   * @param value
   *          the new {@code float} value to be set to this object.
   */
  void setFloatValue(float value);

  /**
   * The {@code double} value of this object.
   *
   * @return the {@code double} value of this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DOUBLE}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  double getDoubleValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@code double} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#DOUBLE}, all previous value of this object is cleared, and the
   * new value is set to this object.
*
   * @param value
   *          the new {@code double} value to be set to this object.
   */
  void setDoubleValue(double value);

  /**
   * the {@code String} value of this object.
   *
   * @return the {@code String} value of this object, which may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#STRING}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  String getStringValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@link String} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#STRING}, all previous value of this object is cleared, and the
   * new value is set to this object.
   *
   * @param value
   *          the new {@link String} value to be set to this object, which may
   *          be null.
   */
  void setStringValue(@Nullable String value);

  /**
   * the {@link Date} value of this object.
   *
   * @return the {@link Date} value of this object, which may be null. Note that
   *         the returned object is the cloned copy of the first {@link Date}
   *         object stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#DATE}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  Date getDateValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@link Date} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#DATE}, all previous value of this object is cleared, and the
   * new value is set to this object.
*
   * @param value
   *          the new {@link Date} value to be set to this object, which may be
   *          null. Note that the cloned copy of this object will be stored in
   *          this object.
   */
  void setDateValue(@Nullable Date value);

  /**
   * Gets the {@link BigInteger} value of this object.
   *
   * @return the {@link BigInteger} value of this object, which may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_INTEGER}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  BigInteger getBigIntegerValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@link BigInteger} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#BIG_INTEGER}, all previous value of this object is cleared, and
   * the new value is set to this object.
   *
   * @param value
   *          the new {@link BigInteger} value to be set to this object, which
   *          may be null.
   */
  void setBigIntegerValue(@Nullable BigInteger value);

  /**
   * Gets the {@link BigDecimal} value of this object.
   *
   * @return the {@link BigDecimal} value of this object, which may be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BIG_DECIMAL}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  BigDecimal getBigDecimalValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@link BigDecimal} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#BIG_DECIMAL}, all previous value of this object is cleared, and
   * the new value is set to this object.
   *
   * @param value
   *          the new {@link BigDecimal} value to be set to this object, which
   *          may be null.
   */
  void setBigDecimalValue(@Nullable BigDecimal value);

  /**
   * Gets the {@code byte[]} value of this object.
   *
   * @return the {@code byte[]} value of this object, which may be null.
   *         Note that the returned object is the cloned copy of the
   *         {@code byte[]} object stored in this object.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#BYTE_ARRAY}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  byte[] getByteArrayValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a {@code byte[]} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#BYTE_ARRAY}, all previous value of this object is cleared, and
   * the new value is set to this object.
   *
   * @param value
   *          the new {@code byte[]} value to be set to this object, which
   *          may be null. Note that the cloned copy of this object will be
   *          stored in this object.
   */
  void setByteArrayValue(@Nullable byte[] value);

  /**
   * Gets the value of this object as a {@code Class} value.
   *
   * @return the value of this object as a {@code Class} value, which may
   *         be null.
   * @throws TypeMismatchException
   *           if the type of this object is not {@link Type#CLASS}.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  Class<?> getClassValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@code Class} value to this object.
   *
   * <p>After calling this function, the type of this object is set to
   * {@link Type#CLASS}, all previous value of this object is cleared, and the
   * new value is set to this object.
   *
   * @param value
   *          the new {@code Class} value to be set to this object, which
   *          may be null.
   */
  void setClassValue(@Nullable Class<?> value);

  /**
   * Gets the value of this object as a {@code boolean} value.
   *
   * <p>If the type of this object is not {@link Type#BOOL}, the value will be
   * converted into a {@code boolean} value.
   *
   * @return the value of this object as a {@code boolean} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code boolean} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  boolean getValueAsBoolean() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@code char} value.
   *
   * <p>If the type of this object is not {@link Type#CHAR}, the value will be
   * converted into a {@code char} value.
   *
   * @return the value of this object as a {@code char} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code char} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  char getValueAsChar() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@code byte} value.
   *
   * <p>If the type of this object is not {@link Type#BYTE}, the value will be
   * converted into a {@code byte} value.
   *
   * @return the value of this object as a {@code boolean} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code byte} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  byte getValueAsByte() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@code short} value.
   *
   * <p>If the type of this object is not {@link Type#SHORT}, the value will be
   * converted into a {@code short} value.
   *
   * @return the value of this object as a {@code boolean} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code short} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  short getValueAsShort() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@code int} value.
   *
   * <p>If the type of this object is not {@link Type#INT}, the value will be
   * converted into a {@code int} value.
   *
   * @return the value of this object as a {@code int} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code int} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  int getValueAsInt() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@code long} value.
   *
   * <p>If the type of this object is not {@link Type#LONG}, the value will be
   * converted into a {@code long} value.
   *
   * @return the value of this object as a {@code long} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code long} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  long getValueAsLong() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@code float} value.
   *
   * <p>If the type of this object is not {@link Type#FLOAT}, the value will be
   * converted into a {@code float} value.
   *
   * @return the value of this object as a {@code float} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code float} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  float getValueAsFloat() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@code double} value.
   *
   * <p>If the type of this object is not {@link Type#DOUBLE}, the value will be
   * converted into a {@code boolean} value.
   *
   * @return the value of this object as a {@code double} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code double} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  double getValueAsDouble() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@code String} value.
   *
   * <p>If the type of this object is not {@link Type#STRING}, the value will be
   * converted into a {@code String} value.
   *
   * @return the value of this object as a {@code String} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code String} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  String getValueAsString() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@link Date} value.
   *
   * <p>If the type of this object is not {@link Type#DATE}, the value will be
   * converted into a {@link Date} value.
   *
   * @return the value of this object as a {@link Date} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@link Date} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  Date getValueAsDate() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@code byte[]} value.
   *
   * <p>If the type of this object is not {@link Type#BYTE_ARRAY}, the value will
   * be converted into a {@code byte[]} value.
   *
   * @return the value of this object as a {@code boolean} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code byte[]} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  byte[] getValueAsByteArray() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@code Class} value.
   *
   * <p>If the type of this object is not {@link Type#CLASS}, the value will be
   * converted into a {@code boolean} value.
   *
   * @return the value of this object as a {@code Class} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@code Class} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  Class<?> getValueAsClass() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@link BigInteger} value.
   *
   * <p>If the type of this object is not {@link Type#BIG_INTEGER}, the value will
   * be converted into a {@code boolean} value.
   *
   * @return the value of this object as a {@link BigInteger} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@link BigInteger} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  BigInteger getValueAsBigInteger() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the value of this object as a {@link BigDecimal} value.
   *
   * <p>If the type of this object is not {@link Type#BIG_DECIMAL}, the value will
   * be converted into a {@code boolean} value.
   *
   * @return the value of this object as a {@link BigDecimal} value.
   * @throws TypeConvertException
   *           if the value of this object can not be converted into a
   *           {@link BigDecimal} value.
   * @throws NoSuchElementException
   *           if this object has no value.
   */
  BigDecimal getValueAsBigDecimal() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Clones this {@link Value} object.
   *
   * @return the cloned copy of this {@link Value} object.
   */
  @Override
  Value clone();
}
