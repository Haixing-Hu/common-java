////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.serialize;

import java.io.InputStream;
import java.util.List;

import ltd.qubit.commons.io.io.error.SerializationException;

/**
 * The interface for deserializing an object from a binary stream.
 *
 * @param <E>
 *       the type of the object to be deserialized.
 * @author Haixing Hu
 */
public interface BinaryDeserializer<E> extends Deserializer<E, InputStream> {
  /**
   * Deserializes an object from the specified binary stream.
   *
   * @param input
   *     the input stream from which the object is to be deserialized.
   * @return
   *     the deserialized object.
   * @throws SerializationException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, serialization/deserialization errors.
   */
  @Override
  E deserialize(InputStream input) throws SerializationException;

  /**
   * Deserializes a list of objects from the specified binary stream.
   *
   * @param input
   *     the input stream from which the objects are to be deserialized.
   * @return
   *     the deserialized list of objects.
   * @throws SerializationException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, serialization/deserialization errors.
   */
  @Override
  List<E> deserializeList(InputStream input) throws SerializationException;
}
