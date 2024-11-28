////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.serialize;

import java.io.OutputStream;
import java.util.List;

import ltd.qubit.commons.io.io.error.SerializationException;

/**
 * The interface for serializing an object into a binary stream.
 *
 * @param <E>
 *       the type of the object to be serialized.
 * @author Haixing Hu
 */
public interface BinarySerializer<E> extends Serializer<E, OutputStream> {
  /**
   * Serializes an object into the specified text stream.
   *
   * @param output
   *     the output stream to which the object is to be serialized.
   * @param obj
   *     the object to be serialized.
   * @throws SerializationException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, serialization/deserialization errors.
   */
  @Override
  void serialize(OutputStream output, E obj) throws SerializationException;

  /**
   * Serializes a list of objects into the specified text stream.
   *
   * @param output
   *     the output stream to which the objects are to be serialized.
   * @param list
   *     the list of objects to be serialized.
   * @throws SerializationException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, serialization/deserialization errors.
   */
  @Override
  void serializeList(OutputStream output, List<E> list) throws SerializationException;
}
