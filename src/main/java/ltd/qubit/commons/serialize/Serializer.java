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
 * The interface for serializing an object into a target.
 *
 * @param <E>
 *       the type of the object to be serialized.
 * @param <TARGET>
 *       the type of the target to which the object is to be serialized.
 * @author Haixing Hu
 */
public interface Serializer<E, TARGET> {
  /**
   * Initializes this serializer.
   *
   * @param target
   *     the target to which the object is to be serialized.
   * @throws IOException
   *     if an error occurs during serialization, including but not limited to
   *     database access errors, I/O errors, serialization errors.
   */
  default void init(final TARGET target) throws IOException {}

  /**
   * Closes this serializer.
   *
   * @param target
   *     the target to which the object is to be serialized.
   * @throws IOException
   *     if an error occurs during serialization, including but not limited to
   *     database access errors, I/O errors, serialization errors.
   */
  default void close(final TARGET target) throws IOException {}

  /**
   * Serializes an object into the specified text stream.
   *
   * @param target
   *     the target to which the object is to be serialized.
   * @param obj
   *     the object to be serialized.
   * @throws IOException
   *     if an error occurs during serialization, including but not limited to
   *     database access errors, I/O errors, serialization errors.
   */
  void serialize(TARGET target, E obj) throws IOException;

  /**
   * Serializes a list of objects into the specified text stream.
   *
   * @param target
   *     the target to which the objects are to be serialized.
   * @param list
   *     the list of objects to be serialized.
   * @throws IOException
   *     if an error occurs during serialization, including but not limited to
   *     database access errors, I/O errors, serialization errors.
   */
  void serializeList(TARGET target, List<E> list) throws IOException;
}
