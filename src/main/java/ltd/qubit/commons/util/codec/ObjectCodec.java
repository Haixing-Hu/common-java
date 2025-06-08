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
 * The interface of codecs which convert objects to strings and vice versa.
 *
 * @param <T>
 *     the type of the objects to be converted.
 */
public interface ObjectCodec<T> extends ObjectDecoder<T>, ObjectEncoder<T> {
  //  empty
}