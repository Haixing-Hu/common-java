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

/**
 * The interface for serializing an object into a binary stream.
 *
 * @param <E>
 *       the type of the object to be serialized.
 * @author Haixing Hu
 */
public interface BinarySerializer<E> extends Serializer<E, OutputStream> {
  //  empty
}
