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

/**
 * The interface for deserializing an object from a text stream.
 *
 * @param <E>
 *       the type of the object to be deserialized.
 * @author Haixing Hu
 */
public interface TextDeserializer<E> extends Deserializer<E, Reader> {
  //  empty
}
