////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.serialize;

import java.io.IOException;
import java.util.List;

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
   * Initializes this deserializer.
   *
   * @param source
   *     the source from which the object is to be deserialized.
   * @throws IOException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, deserialization errors.
   */
  default void init(final SOURCE source) throws IOException {}

  /**
   * Closes this deserializer.
   *
   * @param source
   *     the source from which the object is to be deserialized.
   * @throws IOException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, deserialization errors.
   */
  default void close(final SOURCE source) throws IOException {}

  /**
   * Deserializes an object from the specified text stream.
   *
   * @param source
   *     the source from which the object is to be deserialized.
   * @return
   *     the deserialized object.
   * @throws IOException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, deserialization errors.
   */
  E deserialize(SOURCE source) throws IOException;

  /**
   * Deserializes a list of objects from the specified text stream.
   *
   * @param source
   *     the source from which the objects are to be deserialized.
   * @return
   *     the deserialized list of objects.
   * @throws IOException
   *     if an error occurs during deserialization, including but not limited to
   *     database access errors, I/O errors, deserialization errors.
   */
  List<E> deserializeList(final SOURCE source) throws IOException;
}
