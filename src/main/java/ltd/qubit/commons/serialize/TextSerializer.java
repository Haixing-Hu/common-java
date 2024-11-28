////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.serialize;

import java.io.Writer;
import java.util.List;

import ltd.qubit.commons.io.io.error.SerializationException;

/**
 * The interface for serializing an object into a text stream.
 *
 * @param <E>
 *       the type of the object to be serialized.
 * @author Haixing Hu
 */
public interface TextSerializer<E> extends Serializer<E, Writer> {
  /**
   * Serializes an object into the specified text stream.
   *
   * @param writer
   *     the writer to which the object is to be serialized.
   * @param obj
   *     the object to be serialized.
   * @throws SerializationException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, serialization/deserialization errors.
   */
  @Override
  void serialize(Writer writer, E obj) throws SerializationException;

  /**
   * Serializes a list of objects into the specified text stream.
   *
   * @param writer
   *     the writer to which the objects are to be serialized.
   * @param list
   *     the list of objects to be serialized.
   * @throws SerializationException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, serialization/deserialization errors.
   */
  @Override
  void serializeList(Writer writer, List<E> list) throws SerializationException;
}
