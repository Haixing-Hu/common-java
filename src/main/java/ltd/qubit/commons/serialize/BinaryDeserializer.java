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

/**
 * The interface for deserializing an object from a binary stream.
 *
 * @param <E>
 *       the type of the object to be deserialized.
 * @author Haixing Hu
 */
public interface BinaryDeserializer<E> extends Deserializer<E, InputStream> {
  //  empty
}
