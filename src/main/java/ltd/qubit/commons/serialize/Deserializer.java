////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.serialize;

import java.util.List;

import ltd.qubit.commons.io.error.SerializationException;

/**
 * The interface for deserializing an object from a source.
 *
 * @param <E>
 *       the type of the object to be deserialized.
 * @param <SOURCE>
 *       the type of the source to which the object is to be serialized.
 * @author Haixing Hu
 */
public interface Deserializer<E, SOURCE> {
  /**
   * Deserializes an object from the specified text stream.
   *
   * @param source
   *     the source from which the object is to be deserialized.
   * @return
   *     the deserialized object.
   * @throws SerializationException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, serialization/deserialization errors.
   */
  E deserialize(SOURCE source) throws SerializationException;

  /**
   * Deserializes a list of objects from the specified text stream.
   *
   * @param source
   *     the source from which the objects are to be deserialized.
   * @return
   *     the deserialized list of objects.
   * @throws SerializationException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, serialization/deserialization errors.
   */
  List<E> deserializeList(final SOURCE source) throws SerializationException;
}
