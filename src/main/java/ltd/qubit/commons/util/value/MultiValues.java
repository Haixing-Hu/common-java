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
import java.util.Collection;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.error.TypeConvertException;
import ltd.qubit.commons.error.TypeMismatchException;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.text.xml.XmlException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A {@link MultiValues} object represents one or more values of values of
 * common types.
 *
 * <p>Currently the {@link MultiValues} interface support the following types:
 * <ul>
 * <li>{@code boolean}</li>
 * <li>{@code char}</li>
 * <li>{@code byte}</li>
 * <li>{@code short}</li>
 * <li>{@code int}</li>
 * <li>{@code long}</li>
 * <li>{@code float}</li>
 * <li>{@code double}</li>
 * <li>{@link String}</li>
 * <li>{@code java.util.Date}</li>
 * <li>{@code java.math.BigDecimal}</li>
 * <li>{@code java.math.BigInteger}</li>
 * <li>{@code byte[]}</li>
 * <li>{@code Class}</li>
 * </ul>
 *
 * @author Haixing Hu
 */
public interface MultiValues extends CloneableEx<MultiValues> {

  /**
   * The default type of the {@link MultiValues} objects.
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
   * <p><b>NOTE:</b> If the new type is the same as the old type of this
   * object, nothing is done; otherwise, the type of this object will be set to
   * the new type, and all old values of this object will be cleared.
   *
   * @param type
   *     the new type of this object.
   */
  void setType(Type type);

  /**
   * Gets the number of values of this object.
   *
   * @return the number of values of this object.
   */
  int getCount();

  /**
   * Tests whether this object has any value.
   *
   * @return true if this object has any value; false otherwise.
   */
  boolean isEmpty();

  /**
   * Clears all the values of this object.
   */
  void clear();

  /**
   * Unions the values and type of another {@link MultiValues} object to this
   * {@link MultiValues} object .
   *
   * @param other
   *     the other {@link MultiValues} object.
   */
  void assignValues(MultiValues other);

  /**
   * Unions the values of two variants with the same type.
   *
   * @param other
   *     the other {@link MultiValues} object, which must have the same type as
   *     this {@link MultiValues} object.
   * @throws TypeMismatchException
   *     if the type of this {@link MultiValues} object is not the same as the
   *     type of the other {@link MultiValues object}.
   */
  void unionValues(MultiValues other) throws TypeMismatchException;

  /**
   * Reads values from an input stream.
   *
   * @param type
   *     the type of values to be read.
   * @param n
   *     the number of values to be read.
   * @param in
   *     the input stream.
   * @throws IOException
   *     If any I/O error occurred.
   */
  void readValues(Type type, int n, InputStream in) throws IOException;

  /**
   * Writes values to an output stream.
   *
   * <p>Note that this function does NOT write the type nor the value count to
   * the output stream.
   *
   * @param out
   *     the output stream.
   * @throws IOException
   *     If any I/O error occurred.
   */
  void writeValues(OutputStream out) throws IOException;

  /**
   * Deserialize the values from the children of a DOM node.
   *
   * @param type
   *     the type of the values.
   * @param parent
   *     the parent DOM node.
   * @param tagName
   *     the tag name of the value nodes.
   * @param prevSpaceAttr
   *     the attribute name of the preserve space attribute. If it is null, no
   *     preserve space attribute is used.
   * @throws XmlException
   *     If any XML related error occurred.
   */
  void getValuesFromXml(Type type, Element parent, String tagName,
      @Nullable String prevSpaceAttr) throws XmlException;

  /**
   * Serializes the values as children nodes of a DOM node.
   *
   * @param doc
   *     the DOM document.
   * @param parent
   *     the parent DOM node.
   * @param containerName
   *     the tag name of the container of the value nodes, which could be null.
   *     If it is null, no container is used.
   * @param tagName
   *     the tag name of value nodes.
   * @param prevSpaceAttr
   *     the attribute name of the preserve space attribute. If it is null, no
   *     preserve space attribute is used.
   * @throws XmlException
   *     If any XML related error occurred.
   */
  void appendValuesToXml(Document doc, Element parent,
      @Nullable String containerName, String tagName,
      @Nullable String prevSpaceAttr) throws XmlException;

  /**
   * Gets the first value of this object as a {@code boolean} value.
   *
   * @return the first value of this object as a {@code boolean} value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BOOL}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  boolean getBooleanValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@code boolean} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BOOL}, all previous values of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *     the new {@code boolean} value to be set to this object.
   */
  void setBooleanValue(boolean value);

  /**
   * Gets the values of this object as a {@code boolean} value.
   *
   * @return the values of this object as a {@code boolean} array; or an empty
   *     array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BOOL}.
   */
  boolean[] getBooleanValues() throws TypeMismatchException;

  /**
   * Sets {@code boolean} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BOOL}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@code boolean} values to be set to this object.
   */
  void setBooleanValues(boolean... values);

  /**
   * Sets {@code boolean} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BOOL}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@code boolean} values to be set to this object.
   */
  void setBooleanValues(BooleanCollection values);

  /**
   * Adds a {@code boolean} value to this object.
   *
   * <p>If the type of this object is not {@link Type#BOOL}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * boolean} value will be add to this object.
   *
   * @param value
   *     the {@code boolean} value to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BOOL}.
   */
  void addBooleanValue(boolean value) throws TypeMismatchException;

  /**
   * Adds {@code boolean} values to this object.
   *
   * <p>If the type of this object is not {@link Type#BOOL}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * boolean} values will be add to this object.
   *
   * @param values
   *     the array of {@code boolean} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BOOL}.
   */
  void addBooleanValues(boolean... values) throws TypeMismatchException;

  /**
   * Adds {@code boolean} values to this object.
   *
   * <p>If the type of this object is not {@link Type#BOOL}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * boolean} values will be add to this object.
   *
   * @param values
   *     the colleciton of {@code boolean} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BOOL}.
   */
  void addBooleanValues(BooleanCollection values) throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@code char} value.
   *
   * @return the first value of this object as a {@code char} value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#CHAR}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  char getCharValue() throws TypeMismatchException, NoSuchElementException;

  /**
   * Sets a single {@code char} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#CHAR}, all previous values of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *     the new {@code char} value to be set to this object.
   */
  void setCharValue(char value);

  /**
   * Gets the values of this object as a {@code char} value.
   *
   * @return the values of this object as a {@code char} array; or an empty
   *     array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#CHAR}.
   */
  char[] getCharValues() throws TypeMismatchException;

  /**
   * Sets {@code char} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#CHAR}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@code char} values to be set to this object.
   */
  void setCharValues(char... values);

  /**
   * Sets {@code char} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#CHAR}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@code char} values to be set to this object.
   */
  void setCharValues(CharCollection values);

  /**
   * Adds a {@code char} value to this object.
   *
   * <p>If the type of this object is not {@link Type#CHAR}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * char} value will be add to this object.
   *
   * @param value
   *     the {@code char} value to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#CHAR} and there is old
   *     value in this object.
   */
  void addCharValue(char value) throws TypeMismatchException;

  /**
   * Adds {@code char} values to this object.
   *
   * <p>If the type of this object is not {@link Type#CHAR}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * char} values will be add to this object.
   *
   * @param values
   *     the array of {@code char} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#CHAR} and there is old
   *     value in this object.
   */
  void addCharValues(char... values) throws TypeMismatchException;

  /**
   * Adds {@code char} values to this object.
   *
   * <p>If the type of this object is not {@link Type#CHAR}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * char} values will be add to this object.
   *
   * @param values
   *     the collection of {@code char} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#CHAR} and there is old
   *     value in this object.
   */
  void addCharValues(CharCollection values) throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@code byte} value.
   *
   * @return the first value of this object as a {@code byte} value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BYTE}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  byte getByteValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@code byte} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BYTE}, all previous values of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *     the new {@code byte} value to be set to this object.
   */
  void setByteValue(byte value);

  /**
   * Gets the values of this object as a {@code byte} value.
   *
   * @return the values of this object as a {@code byte} array; or an empty
   *     array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BYTE}.
   */
  byte[] getByteValues() throws TypeMismatchException;

  /**
   * Sets {@code byte} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BYTE}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@code byte} values to be set to this object.
   */
  void setByteValues(byte... values);

  /**
   * Sets {@code byte} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BYTE}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@code byte} values to be set to this object.
   */
  void setByteValues(ByteCollection values);

  /**
   * Adds a {@code byte} value to this object.
   *
   * <p>If the type of this object is not {@link Type#BYTE}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * byte} value will be add to this object.
   *
   * @param value
   *     the {@code byte} value to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BYTE} and there is old
   *     value in this object.
   */
  void addByteValue(byte value) throws TypeMismatchException;

  /**
   * Adds {@code byte} values to this object.
   *
   * <p>If the type of this object is not {@link Type#BYTE}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * byte} values will be add to this object.
   *
   * @param values
   *     the array of {@code byte} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BYTE} and there is old
   *     value in this object.
   */
  void addByteValues(byte... values) throws TypeMismatchException;

  /**
   * Adds {@code byte} values to this object.
   *
   * <p>If the type of this object is not {@link Type#BYTE}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * byte} values will be add to this object.
   *
   * @param values
   *     the collection of {@code byte} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BYTE} and there is old
   *     value in this object.
   */
  void addByteValues(ByteCollection values) throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@code short} value.
   *
   * @return the first value of this object as a {@code short} value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#SHORT}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  short getShortValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@code short} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#SHORT}, all previous values of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *     the new {@code short} value to be set to this object.
   */
  void setShortValue(short value);

  /**
   * Gets the values of this object as a {@code short} value.
   *
   * @return the values of this object as a {@code short} array; or an empty
   *     array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#SHORT}.
   */
  short[] getShortValues() throws TypeMismatchException;

  /**
   * Sets {@code short} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#SHORT}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@code short} values to be set to this object.
   */
  void setShortValues(short... values);

  /**
   * Sets {@code short} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#SHORT}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@code short} values to be set to this object.
   */
  void setShortValues(ShortCollection values);

  /**
   * Adds a {@code short} value to this object.
   *
   * <p>If the type of this object is not {@link Type#SHORT}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * short} value will be add to this object.
   *
   * @param value
   *     the {@code short} value to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#SHORT} and there is old
   *     value in this object.
   */
  void addShortValue(short value) throws TypeMismatchException;

  /**
   * Adds {@code short} values to this object.
   *
   * <p>If the type of this object is not {@link Type#SHORT}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * short} values will be add to this object.
   *
   * @param values
   *     the array of {@code short} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#SHORT} and there is old
   *     value in this object.
   */
  void addShortValues(short... values) throws TypeMismatchException;

  /**
   * Adds {@code short} values to this object.
   *
   * <p>If the type of this object is not {@link Type#SHORT}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * short} values will be add to this object.
   *
   * @param values
   *     the collection of {@code short} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#SHORT} and there is old
   *     value in this object.
   */
  void addShortValues(ShortCollection values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@code int} value.
   *
   * @return the first value of this object as a {@code int} value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#INT}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  int getIntValue() throws TypeMismatchException, NoSuchElementException;

  /**
   * Sets a single {@code int} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#INT}, all previous values of this object is cleared, and the new value
   * is set to this object.
   *
   * @param value
   *     the new {@code int} value to be set to this object.
   */
  void setIntValue(int value);

  /**
   * Gets the values of this object as a {@code int} value.
   *
   * @return the values of this object as a {@code int} array; or an empty array
   *     if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#INT}.
   */
  int[] getIntValues() throws TypeMismatchException;

  /**
   * Sets {@code int} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#INT}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@code int} values to be set to this object.
   */
  void setIntValues(int... values);

  /**
   * Sets {@code int} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#INT}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@code int} values to be set to this object.
   */
  void setIntValues(IntCollection values);

  /**
   * Adds a {@code int} value to this object.
   *
   * <p>If the type of this object is not {@link Type#INT}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code int}
   * value will be add to this object.
   *
   * @param value
   *     the {@code int} value to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#INT} and there is old
   *     value in this object.
   */
  void addIntValue(int value) throws TypeMismatchException;

  /**
   * Adds {@code int} values to this object.
   *
   * <p>If the type of this object is not {@link Type#INT}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code int}
   * values will be add to this object.
   *
   * @param values
   *     the array of {@code int} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#INT} and there is old
   *     value in this object.
   */
  void addIntValues(int... values) throws TypeMismatchException;

  /**
   * Adds {@code int} values to this object.
   *
   * <p>If the type of this object is not {@link Type#INT}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code int}
   * values will be add to this object.
   *
   * @param values
   *     the collection of {@code int} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#INT} and there is old
   *     value in this object.
   */
  void addIntValues(IntCollection values) throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@code long} value.
   *
   * @return the first value of this object as a {@code long} value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#LONG}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  long getLongValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@code long} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#LONG}, all previous values of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *     the new {@code long} value to be set to this object.
   */
  void setLongValue(long value);

  /**
   * Gets the values of this object as a {@code long} value.
   *
   * @return the values of this object as a {@code long} array; or an empty
   *     array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#LONG}.
   */
  long[] getLongValues() throws TypeMismatchException;

  /**
   * Sets {@code long} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#LONG}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@code long} values to be set to this object.
   */
  void setLongValues(long... values);

  /**
   * Sets {@code long} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#LONG}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@code long} values to be set to this object.
   */
  void setLongValues(LongCollection values);

  /**
   * Adds a {@code long} value to this object.
   *
   * <p>If the type of this object is not {@link Type#LONG}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * long} value will be add to this object.
   *
   * @param value
   *     the {@code long} value to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#LONG} and there is old
   *     value in this object.
   */
  void addLongValue(long value) throws TypeMismatchException;

  /**
   * Adds {@code long} values to this object.
   *
   * <p>If the type of this object is not {@link Type#LONG}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * long} values will be add to this object.
   *
   * @param values
   *     the array of {@code long} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#LONG} and there is old
   *     value in this object.
   */
  void addLongValues(long... values) throws TypeMismatchException;

  /**
   * Adds {@code long} values to this object.
   *
   * <p>If the type of this object is not {@link Type#LONG}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * long} values will be add to this object.
   *
   * @param values
   *     the collection of {@code long} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#LONG} and there is old
   *     value in this object.
   */
  void addLongValues(LongCollection values) throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@code float} value.
   *
   * @return the first value of this object as a {@code float} value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#FLOAT}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  float getFloatValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@code float} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#FLOAT}, all previous values of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *     the new {@code float} value to be set to this object.
   */
  void setFloatValue(float value);

  /**
   * Gets the values of this object as a {@code float} value.
   *
   * @return the values of this object as a {@code float} array; or an empty
   *     array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#FLOAT}.
   */
  float[] getFloatValues() throws TypeMismatchException;

  /**
   * Sets {@code float} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#FLOAT}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@code float} values to be set to this object.
   */
  void setFloatValues(float... values);

  /**
   * Sets {@code float} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#FLOAT}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@code float} values to be set to this object.
   */
  void setFloatValues(FloatCollection values);

  /**
   * Adds a {@code float} value to this object.
   *
   * <p>If the type of this object is not {@link Type#FLOAT}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * float} value will be add to this object.
   *
   * @param value
   *     the {@code float} value to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#FLOAT} and there is old
   *     value in this object.
   */
  void addFloatValue(float value) throws TypeMismatchException;

  /**
   * Adds {@code float} values to this object.
   *
   * <p>If the type of this object is not {@link Type#FLOAT}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * float} values will be add to this object.
   *
   * @param values
   *     the array of {@code float} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#FLOAT} and there is old
   *     value in this object.
   */
  void addFloatValues(float... values) throws TypeMismatchException;

  /**
   * Adds {@code float} values to this object.
   *
   * <p>If the type of this object is not {@link Type#FLOAT}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * float} values will be add to this object.
   *
   * @param values
   *     the collection of {@code float} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#FLOAT} and there is old
   *     value in this object.
   */
  void addFloatValues(FloatCollection values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@code double} value.
   *
   * @return the first value of this object as a {@code double} value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#DOUBLE}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  double getDoubleValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@code double} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#DOUBLE}, all previous values of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *     the new {@code double} value to be set to this object.
   */
  void setDoubleValue(double value);

  /**
   * Gets the values of this object as a {@code double} value.
   *
   * @return the values of this object as a {@code double} array; or an empty
   *     array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#DOUBLE}.
   */
  double[] getDoubleValues() throws TypeMismatchException;

  /**
   * Sets {@code double} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#DOUBLE}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@code double} values to be set to this object.
   */
  void setDoubleValues(double... values);

  /**
   * Sets {@code double} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#DOUBLE}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@code double} values to be set to this object.
   */
  void setDoubleValues(DoubleCollection values);

  /**
   * Adds a {@code double} value to this object.
   *
   * <p>If the type of this object is not {@link Type#DOUBLE}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * double} value will be add to this object.
   *
   * @param value
   *     the {@code double} value to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#DOUBLE} and there is old
   *     value in this object.
   */
  void addDoubleValue(double value) throws TypeMismatchException;

  /**
   * Adds {@code double} values to this object.
   *
   * <p>If the type of this object is not {@link Type#DOUBLE}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * double} values will be add to this object.
   *
   * @param values
   *     the array of {@code double} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#DOUBLE} and there is old
   *     value in this object.
   */
  void addDoubleValues(double... values) throws TypeMismatchException;

  /**
   * Adds {@code double} values to this object.
   *
   * <p>If the type of this object is not {@link Type#DOUBLE}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * double} values will be add to this object.
   *
   * @param values
   *     the collection of {@code double} values to be add to this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#DOUBLE} and there is old
   *     value in this object.
   */
  void addDoubleValues(DoubleCollection values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@link String} value.
   *
   * @return the first value of this object as a {@link String} value, which may
   *     be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#STRING}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  String getStringValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@link String} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#STRING}, all previous values of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *     the new {@link String} value to be set to this object, which may be
   *     null.
   */
  void setStringValue(@Nullable String value);

  /**
   * Gets the values of this object as a {@link String} value.
   *
   * @return the values of this object as a {@link String} array, whose elements
   *     may be null; or an empty array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#STRING}.
   */
  String[] getStringValues() throws TypeMismatchException;

  /**
   * Sets {@link String} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#STRING}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@link String} values to be set to this object, whose
   *     elements may be null.
   */
  void setStringValues(String... values);

  /**
   * Sets {@link String} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#STRING}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@link String} values to be set to this object,
   *     whose elements may be null.
   */
  void setStringValues(Collection<String> values);

  /**
   * Adds a {@link String} value to this object.
   *
   * <p>If the type of this object is not {@link Type#STRING}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * String} value will be add to this object.
   *
   * @param value
   *     the {@link String} value to be add to this object, which may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#STRING} and there is old
   *     value in this object.
   */
  void addStringValue(@Nullable String value)
      throws TypeMismatchException;

  /**
   * Adds {@link String} values to this object.
   *
   * <p>If the type of this object is not {@link Type#STRING}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * String} values will be add to this object.
   *
   * @param values
   *     the array of {@link String} values to be add to this object, whose
   *     elements may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#STRING} and there is old
   *     value in this object.
   */
  void addStringValues(String... values) throws TypeMismatchException;

  /**
   * Adds {@link String} values to this object.
   *
   * <p>If the type of this object is not {@link Type#STRING}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * String} values will be add to this object.
   *
   * @param values
   *     the collection of {@link String} values to be add to this object, whose
   *     elements may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#STRING} and there is old
   *     value in this object.
   */
  void addStringValues(Collection<String> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@link Date} value.
   *
   * @return the first value of this object as a {@link Date} value, which may
   *     be null. Note that the returned object is the cloned copy of the first
   *     {@link Date} object stored in this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#DATE}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  Date getDateValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@link Date} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#DATE}, all previous values of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *     the new {@link Date} value to be set to this object, which may be null.
   *     Note that the cloned copy of this object will be stored in this
   *     object.
   */
  void setDateValue(@Nullable Date value);

  /**
   * Gets the values of this object as a {@link Date} value.
   *
   * @return the values of this object as a {@link Date} array, whose elements
   *     may be null; or an empty array if this object has no value. Note that
   *     the objects in returned array is the cloned copies of the {@link Date}
   *     objects stored in this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#DATE}.
   */
  Date[] getDateValues() throws TypeMismatchException;

  /**
   * Sets {@link Date} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#DATE}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@link Date} values to be set to this object, whose
   *     elements may be null. Note that the cloned copy of this array will be
   *     stored in this object.
   */
  void setDateValues(Date... values);

  /**
   * Sets {@link Date} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#DATE}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@link Date} values to be set to this object,
   *     whose elements may be null. Note that the cloned copy of this
   *     collection will be stored in this object.
   */
  void setDateValues(Collection<Date> values);

  /**
   * Adds a {@link Date} value to this object.
   *
   * <p>If the type of this object is not {@link Type#DATE}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * Date} value will be add to this object.
   *
   * @param value
   *     the {@link Date} value to be add to this object, which may be null.
   *     Note that the cloned copy of this object will be stored in this
   *     object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#DATE} and there is old
   *     value in this object.
   */
  void addDateValue(@Nullable Date value) throws TypeMismatchException;

  /**
   * Adds {@link Date} values to this object.
   *
   * <p>If the type of this object is not {@link Type#DATE}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * Date} values will be add to this object.
   *
   * @param values
   *     the array of {@link Date} values to be add to this object, whose
   *     elements may be null. Note that the cloned copy of this array will be
   *     stored in this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#DATE} and there is old
   *     value in this object.
   */
  void addDateValues(Date... values) throws TypeMismatchException;

  /**
   * Adds {@link Date} values to this object.
   *
   * <p>If the type of this object is not {@link Type#DATE}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * Date} values will be add to this object.
   *
   * @param values
   *     the collection of {@link Date} values to be add to this object, whose
   *     elements may be null. Note that the cloned copy of this collection will
   *     be stored in this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#DATE} and there is old
   *     value in this object.
   */
  void addDateValues(Collection<Date> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@link BigInteger} value.
   *
   * @return the first value of this object as a {@link BigInteger} value, which
   *     may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BIG_INTEGER}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  BigInteger getBigIntegerValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@link BigInteger} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BIG_INTEGER}, all previous values of this object is cleared, and the
   * new value is set to this object.
   *
   * @param value
   *     the new {@link BigInteger} value to be set to this object, which may be
   *     null.
   */
  void setBigIntegerValue(@Nullable BigInteger value);

  /**
   * Gets the values of this object as a {@link BigInteger} value.
   *
   * @return the values of this object as a {@link BigInteger} array, whose
   *     elements may be null; or an empty array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BIG_INTEGER}.
   */
  BigInteger[] getBigIntegerValues() throws TypeMismatchException;

  /**
   * Sets {@link BigInteger} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BIG_INTEGER}, all previous values of this object is cleared, and the
   * new values is set to this object.
   *
   * @param values
   *     the array of new {@link BigInteger} values to be set to this object,
   *     whose elements may be null.
   */
  void setBigIntegerValues(BigInteger... values);

  /**
   * Sets {@link BigInteger} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BIG_INTEGER}, all previous values of this object is cleared, and the
   * new values is set to this object.
   *
   * @param values
   *     the collection of new {@link BigInteger} values to be set to this
   *     object, whose elements may be null.
   */
  void setBigIntegerValues(Collection<BigInteger> values);

  /**
   * Adds a {@link BigInteger} value to this object.
   *
   * <p>If the type of this object is not {@link Type#BIG_INTEGER}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * BigInteger} value will be add to this object.
   *
   * @param value
   *     the {@link BigInteger} value to be add to this object, which may be
   *     null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BIG_INTEGER} and there is
   *     old value in this object.
   */
  void addBigIntegerValue(@Nullable BigInteger value)
      throws TypeMismatchException;

  /**
   * Adds {@link BigInteger} values to this object.
   *
   * <p>If the type of this object is not {@link Type#BIG_INTEGER}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * BigInteger} values will be add to this object.
   *
   * @param values
   *     the array of {@link BigInteger} values to be add to this object, whose
   *     elements may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BIG_INTEGER} and there is
   *     old value in this object.
   */
  void addBigIntegerValues(BigInteger... values)
      throws TypeMismatchException;

  /**
   * Adds {@link BigInteger} values to this object.
   *
   * <p>If the type of this object is not {@link Type#BIG_INTEGER}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * BigInteger} values will be add to this object.
   *
   * @param values
   *     the collection of {@link BigInteger} values to be add to this object,
   *     whose elements may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BIG_INTEGER} and there is
   *     old value in this object.
   */
  void addBigIntegerValues(Collection<BigInteger> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@link BigDecimal} value.
   *
   * @return the first value of this object as a {@link BigDecimal} value, which
   *     may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BIG_DECIMAL}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  BigDecimal getBigDecimalValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@link BigDecimal} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BIG_DECIMAL}, all previous values of this object is cleared, and the
   * new value is set to this object.
   *
   * @param value
   *     the new {@link BigDecimal} value to be set to this object, which may be
   *     null.
   */
  void setBigDecimalValue(@Nullable BigDecimal value);

  /**
   * Gets the values of this object as a {@link BigDecimal} value.
   *
   * @return the values of this object as a {@link BigDecimal} array, whose
   *     elements may be null; or an empty array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BIG_DECIMAL}.
   */
  BigDecimal[] getBigDecimalValues() throws TypeMismatchException;

  /**
   * Sets {@link BigDecimal} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BIG_DECIMAL}, all previous values of this object is cleared, and the
   * new values is set to this object.
   *
   * @param values
   *     the array of new {@link BigDecimal} values to be set to this object,
   *     whose elements may be null.
   */
  void setBigDecimalValues(BigDecimal... values);

  /**
   * Sets {@link BigDecimal} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BIG_DECIMAL}, all previous values of this object is cleared, and the
   * new values is set to this object.
   *
   * @param values
   *     the collection of new {@link BigDecimal} values to be set to this
   *     object, whose elements may be null.
   */
  void setBigDecimalValues(Collection<BigDecimal> values);

  /**
   * Adds a {@link BigDecimal} value to this object.
   *
   * <p>If the type of this object is not {@link Type#BIG_DECIMAL}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * BigDecimal} value will be add to this object.
   *
   * @param value
   *     the {@link BigDecimal} value to be add to this object, which may be
   *     null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BIG_DECIMAL} and there is
   *     old value in this object.
   */
  void addBigDecimalValue(@Nullable BigDecimal value)
      throws TypeMismatchException;

  /**
   * Adds {@link BigDecimal} values to this object.
   *
   * <p>If the type of this object is not {@link Type#BIG_DECIMAL}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * BigDecimal} values will be add to this object.
   *
   * @param values
   *     the array of {@link BigDecimal} values to be add to this object, whose
   *     elements may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BIG_DECIMAL} and there is
   *     old value in this object.
   */
  void addBigDecimalValues(BigDecimal... values)
      throws TypeMismatchException;

  /**
   * Adds {@link BigDecimal} values to this object.
   *
   * <p>If the type of this object is not {@link Type#BIG_DECIMAL}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@link
   * BigDecimal} values will be add to this object.
   *
   * @param values
   *     the collection of {@link BigDecimal} values to be add to this object,
   *     whose elements may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BIG_DECIMAL} and there is
   *     old value in this object.
   */
  void addBigDecimalValues(Collection<BigDecimal> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@code byte[]} value.
   *
   * @return the first value of this object as a {@code byte[]} value, which may
   *     be null. Note that the returned object is the cloned copy of the first
   *     {@code byte[]} object stored in this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BYTE_ARRAY}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  byte[] getByteArrayValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@code byte[]} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BYTE_ARRAY}, all previous values of this object is cleared, and the
   * new value is set to this object.
   *
   * @param value
   *     the new {@code byte[]} value to be set to this object, which may be
   *     null. Note that the cloned copy of this object will be stored in this
   *     object.
   */
  void setByteArrayValue(@Nullable byte[] value);

  /**
   * Gets the values of this object as a {@code byte[]} value.
   *
   * @return the values of this object as a {@code byte[]} array, whose elements
   *     may be null; or an empty array if this object has no value. Note that
   *     the objects in returned array is the cloned copies of the {@code
   *     byte[]} objects stored in this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BYTE_ARRAY}.
   */
  byte[][] getByteArrayValues() throws TypeMismatchException;

  /**
   * Sets {@code byte[]} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BYTE_ARRAY}, all previous values of this object is cleared, and the
   * new values is set to this object.
   *
   * @param values
   *     the array of new {@code byte[]} values to be set to this object, whose
   *     elements may be null. Note that the cloned copy of this array will be
   *     stored in this object.
   */
  void setByteArrayValues(byte[]... values);

  /**
   * Sets {@code byte[]} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#BYTE_ARRAY}, all previous values of this object is cleared, and the
   * new values is set to this object.
   *
   * @param values
   *     the collection of new {@code byte[]} values to be set to this object,
   *     whose elements may be null. Note that the cloned copy of this
   *     collection will be stored in this object.
   */
  void setByteArrayValues(Collection<byte[]> values);

  /**
   * Adds a {@code byte[]} value to this object.
   *
   * <p>If the type of this object is not {@link Type#BYTE_ARRAY}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * byte[]} value will be add to this object.
   *
   * @param value
   *     the {@code byte[]} value to be add to this object, which may be null.
   *     Note that the cloned copy of this object will be stored in this
   *     object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BYTE_ARRAY} and there is
   *     old value in this object.
   */
  void addByteArrayValue(@Nullable byte[] value)
      throws TypeMismatchException;

  /**
   * Adds {@code byte[]} values to this object.
   *
   * <p>If the type of this object is not {@link Type#BYTE_ARRAY}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * byte[]} values will be add to this object.
   *
   * @param values
   *     the array of {@code byte[]} values to be add to this object, whose
   *     elements may be null. Note that the cloned copy of this array will be
   *     stored in this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BYTE_ARRAY} and there is
   *     old value in this object.
   */
  void addByteArrayValues(byte[]... values) throws TypeMismatchException;

  /**
   * Adds {@code byte[]} values to this object.
   *
   * <p>If the type of this object is not {@link Type#BYTE_ARRAY}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * byte[]} values will be add to this object.
   *
   * @param values
   *     the collection of {@code byte[]} values to be add to this object, whose
   *     elements may be null. Note that the cloned copy of this collection will
   *     be stored in this object.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#BYTE_ARRAY} and there is
   *     old value in this object.
   */
  void addByteArrayValues(Collection<byte[]> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@code Class} value.
   *
   * @return the first value of this object as a {@code Class} value, which may
   *     be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#CLASS}.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  Class<?> getClassValue() throws TypeMismatchException,
      NoSuchElementException;

  /**
   * Sets a single {@code Class} value to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#CLASS}, all previous values of this object is cleared, and the new
   * value is set to this object.
   *
   * @param value
   *     the new {@code Class} value to be set to this object, which may be
   *     null.
   */
  void setClassValue(@Nullable Class<?> value);

  /**
   * Gets the values of this object as a {@code Class} value.
   *
   * @return the values of this object as a {@code Class} array, whose elements
   *     may be null; or an empty array if this object has no value.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#CLASS}.
   */
  Class<?>[] getClassValues() throws TypeMismatchException;

  /**
   * Sets {@code Class} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#CLASS}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the array of new {@code Class} values to be set to this object, whose
   *     elements may be null.
   */
  void setClassValues(Class<?>... values);

  /**
   * Sets {@code Class} values to this object.
   *
   * <p>After calling this function, the type of this object is set to {@link
   * Type#CLASS}, all previous values of this object is cleared, and the new
   * values is set to this object.
   *
   * @param values
   *     the collection of new {@code Class} values to be set to this object,
   *     whose elements may be null.
   */
  void setClassValues(Collection<Class<?>> values);

  /**
   * Adds a {@code Class} value to this object.
   *
   * <p>If the type of this object is not {@link Type#CLASS}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * Class} value will be add to this object.
   *
   * @param value
   *     the {@code Class} value to be add to this object, which may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#CLASS} and there is old
   *     value in this object.
   */
  void addClassValue(@Nullable Class<?> value)
      throws TypeMismatchException;

  /**
   * Adds {@code Class} values to this object.
   *
   * <p>If the type of this object is not {@link Type#CLASS}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * Class} values will be add to this object.
   *
   * @param values
   *     the array of {@code Class} values to be add to this object, whose
   *     elements may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#CLASS} and there is old
   *     value in this object.
   */
  void addClassValues(Class<?>... values) throws TypeMismatchException;

  /**
   * Adds {@code Class} values to this object.
   *
   * <p>If the type of this object is not {@link Type#CLASS}, an {@link
   * TypeMismatchException} will be thrown; otherwise, the specified {@code
   * Class} values will be add to this object.
   *
   * @param values
   *     the collection of {@code Class} values to be add to this object, whose
   *     elements may be null.
   * @throws TypeMismatchException
   *     if the type of this object is not {@link Type#CLASS} and there is old
   *     value in this object.
   */
  void addClassValues(Collection<Class<?>> values)
      throws TypeMismatchException;

  /**
   * Gets the first value of this object as a {@code boolean} value.
   *
   * <p>If the type of this object is not {@link Type#BOOL}, the value will be
   * converted into a {@code boolean} value.
   *
   * @return the first value of this object as a {@code boolean} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@code
   *     boolean} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  boolean getValueAsBoolean() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@code boolean} values.
   *
   * <p>If the type of this object is not {@link Type#BOOL}, the values will be
   * converted into {@code boolean} values.
   *
   * @return the values of this object as a {@code boolean} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@code
   *     boolean} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  boolean[] getValuesAsBoolean() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@code char} value.
   *
   * <p>If the type of this object is not {@link Type#CHAR}, the value will be
   * converted into a {@code char} value.
   *
   * @return the first value of this object as a {@code char} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@code
   *     char} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  char getValueAsChar() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@code char} values.
   *
   * <p>If the type of this object is not {@link Type#CHAR}, the values will be
   * converted into {@code char} values.
   *
   * @return the values of this object as a {@code char} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@code char}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  char[] getValuesAsChar() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@code byte} value.
   *
   * <p>If the type of this object is not {@link Type#BYTE}, the value will be
   * converted into a {@code byte} value.
   *
   * @return the first value of this object as a {@code boolean} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@code
   *     byte} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  byte getValueAsByte() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@code byte} values.
   *
   * <p>If the type of this object is not {@link Type#BYTE}, the values will be
   * converted into {@code byte} values.
   *
   * @return the values of this object as a {@code byte} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@code byte}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  byte[] getValuesAsByte() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@code short} value.
   *
   * <p>If the type of this object is not {@link Type#SHORT}, the value will be
   * converted into a {@code short} value.
   *
   * @return the first value of this object as a {@code boolean} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@code
   *     short} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  short getValueAsShort() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@code short} values.
   *
   * <p>If the type of this object is not {@link Type#SHORT}, the values will
   * be
   * converted into {@code short} values.
   *
   * @return the values of this object as a {@code short} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@code short}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  short[] getValuesAsShort() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@code int} value.
   *
   * <p>If the type of this object is not {@link Type#INT}, the value will be
   * converted into a {@code int} value.
   *
   * @return the first value of this object as a {@code int} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@code
   *     int} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  int getValueAsInt() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@code int} values.
   *
   * <p>If the type of this object is not {@link Type#INT}, the values will be
   * converted into {@code int} values.
   *
   * @return the values of this object as a {@code int} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@code int}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  int[] getValuesAsInt() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@code long} value.
   *
   * <p>If the type of this object is not {@link Type#LONG}, the value will be
   * converted into a {@code long} value.
   *
   * @return the first value of this object as a {@code long} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@code
   *     long} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  long getValueAsLong() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@code long} values.
   *
   * <p>If the type of this object is not {@link Type#LONG}, the values will be
   * converted into {@code char} values.
   *
   * @return the values of this object as a {@code long} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@code long}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  long[] getValuesAsLong() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@code float} value.
   *
   * <p>If the type of this object is not {@link Type#FLOAT}, the value will be
   * converted into a {@code float} value.
   *
   * @return the first value of this object as a {@code float} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@code
   *     float} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  float getValueAsFloat() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@code float} values.
   *
   * <p>If the type of this object is not {@link Type#FLOAT}, the values will
   * be
   * converted into {@code float} values.
   *
   * @return the values of this object as a {@code float} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@code float}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  float[] getValuesAsFloat() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@code double} value.
   *
   * <p>If the type of this object is not {@link Type#DOUBLE}, the value will
   * be
   * converted into a {@code boolean} value.
   *
   * @return the first value of this object as a {@code double} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@code
   *     double} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  double getValueAsDouble() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@code double} values.
   *
   * <p>If the type of this object is not {@link Type#DOUBLE}, the values will
   * be
   * converted into {@code double} values.
   *
   * @return the values of this object as a {@code double} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@code double}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  double[] getValuesAsDouble() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@link String} value.
   *
   * <p>If the type of this object is not {@link Type#STRING}, the value will
   * be
   * converted into a {@link String} value.
   *
   * @return the first value of this object as a {@link String} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@link
   *     String} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  String getValueAsString() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@link String} values.
   *
   * <p>If the type of this object is not {@link Type#STRING}, the values will
   * be
   * converted into {@link String} values.
   *
   * @return the values of this object as a {@link String} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@link String}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  String[] getValuesAsString() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@link Date} value.
   *
   * <p>If the type of this object is not {@link Type#DATE}, the value will be
   * converted into a {@link Date} value.
   *
   * @return the first value of this object as a {@link Date} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@link
   *     Date} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  Date getValueAsDate() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@link Date} values.
   *
   * <p>If the type of this object is not {@link Type#DATE}, the values will be
   * converted into {@link Date} values.
   *
   * @return the values of this object as a {@link Date} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@link Date}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  Date[] getValuesAsDate() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@code byte[]} value.
   *
   * <p>If the type of this object is not {@link Type#BYTE_ARRAY}, the value
   * will
   * be converted into a {@code byte[]} value.
   *
   * @return the first value of this object as a {@code boolean} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@code
   *     byte[]} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  byte[] getValueAsByteArray() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@code byte[]} values.
   *
   * <p>If the type of this object is not {@link Type#BYTE_ARRAY}, the values
   * will
   * be converted into {@code byte[]} values.
   *
   * @return the values of this object as a {@code byte[]} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@code byte[]}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  byte[][] getValuesAsByteArray() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@code Class} value.
   *
   * <p>If the type of this object is not {@link Type#CLASS}, the value will be
   * converted into a {@code boolean} value.
   *
   * @return the first value of this object as a {@code Class} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@code
   *     Class} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  Class<?> getValueAsClass() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@code Class} values.
   *
   * <p>If the type of this object is not {@link Type#CLASS}, the values will
   * be
   * converted into {@code Class} values.
   *
   * @return the values of this object as a {@code Class} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@code Class}
   *     value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  Class<?>[] getValuesAsClass() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@link BigInteger} value.
   *
   * <p>If the type of this object is not {@link Type#BIG_INTEGER}, the value
   * will
   * be converted into a {@code boolean} value.
   *
   * @return the first value of this object as a {@link BigInteger} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@link
   *     BigInteger} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  BigInteger getValueAsBigInteger() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@link BigInteger} values.
   *
   * <p>If the type of this object is not {@link Type#BIG_INTEGER}, the values
   * will
   * be converted into {@link BigInteger} values.
   *
   * @return the values of this object as a {@code char} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@link
   *     BigInteger} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  BigInteger[] getValuesAsBigInteger() throws TypeConvertException;

  /**
   * Gets the first value of this object as a {@link BigDecimal} value.
   *
   * <p>If the type of this object is not {@link Type#BIG_DECIMAL}, the value
   * will
   * be converted into a {@code boolean} value.
   *
   * @return the first value of this object as a {@link BigDecimal} value.
   * @throws TypeConvertException
   *     if the first value of this object can not be converted into a {@link
   *     BigDecimal} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  BigDecimal getValueAsBigDecimal() throws TypeConvertException,
      NoSuchElementException;

  /**
   * Gets the values of this object as a {@link BigDecimal} values.
   *
   * <p>If the type of this object is not {@link Type#BIG_DECIMAL}, the values
   * will
   * be converted into {@link BigDecimal} values.
   *
   * @return the values of this object as a {@link BigDecimal} values.
   * @throws TypeConvertException
   *     if the values of this object can not be converted into a {@link
   *     BigDecimal} value.
   * @throws NoSuchElementException
   *     if this object has no value.
   */
  BigDecimal[] getValuesAsBigDecimal() throws TypeConvertException;

  /**
   * Clones this {@link MultiValues} object.
   *
   * @return the cloned copy of this {@link MultiValues} object.
   */
  @Override
  MultiValues clone();
}
