////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.io.IOException;

/**
 * The reference to the method used to write a string.
 *
 * @param <T>
 *     the type of the object on which the method is invoked.
 */
@FunctionalInterface
public interface WriteMethodReference<T> {

  void invoke(T obj, String value) throws IOException;
}
