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

/**
 * The interface for serializing an object into a text stream.
 *
 * @param <E>
 *       the type of the object to be serialized.
 * @author Haixing Hu
 */
public interface TextSerializer<E> extends Serializer<E, Writer> {
  //  empty
}
