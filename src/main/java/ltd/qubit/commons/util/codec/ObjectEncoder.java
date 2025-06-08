////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * The interface of encoder which convert objects into strings.
 *
 * @param <T>
 *     the type of the objects to be converted.
 */
public interface ObjectEncoder<T> extends Encoder<T, String> {
  //  empty
}