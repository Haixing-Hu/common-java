////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.serialize;

import java.io.Reader;
import java.util.List;

import ltd.qubit.commons.io.error.SerializationException;

/**
 * The interface for deserializing an object from a text stream.
 *
 * @param <E>
 *       the type of the object to be deserialized.
 * @author Haixing Hu
 */
public interface TextDeserializer<E> extends Deserializer<E, Reader> {
  /**
   * Deserializes an object from the specified text stream.
   *
   * @param reader
   *     the reader from which the object is to be deserialized.
   * @return
   *     the deserialized object.
   * @throws SerializationException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, serialization/deserialization errors.
   */
  @Override
  E deserialize(Reader reader) throws SerializationException;

  /**
   * Deserializes a list of objects from the specified text stream.
   *
   * @param reader
   *     the reader from which the objects are to be deserialized.
   * @return
   *     the deserialized list of objects.
   * @throws SerializationException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, serialization/deserialization errors.
   */
  @Override
  List<E> deserializeList(Reader reader) throws SerializationException;
}
