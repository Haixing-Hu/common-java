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
 * A factory interface for creating decoders based on target types.
 * <p>
 * This factory provides methods to obtain appropriate entity decoders for source
 * object into single target objects or lists of target objects.
 *
 * @param <S>
 *    the type of source objects.
 * @author Haixing Hu
 */
public interface DecoderFactory<S> {
  /**
   * Gets a decoder for converting a source object into a single target object
   * of the specified type.
   *
   * @param <T>
   *     the type of the target object to be converted to.
   * @param targetClass
   *     the class of the target object.
   * @return
   *     a decoder for converting the source object into a target object.
   */
  <T> Decoder<S, T> getDecoder(Class<T> targetClass);

  /**
   * Gets a list decoder for converting a source object into a list of
   * target objects of the specified type.
   *
   * @param <T>
   *     the type of the target object to be converted to.
   * @param targetClass
   *     the class of the target object.
   * @return
   *     a list decoder for converting the source object into a list of
   *     target objects.
   */
  <T> ListDecoder<S, T> getListDecoder(Class<T> targetClass);
}