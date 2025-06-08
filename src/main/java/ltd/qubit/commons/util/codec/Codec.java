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
 * The interface of codecs which can convert objects of one type to another and
 * vice versa.
 *
 * @param <FROM>
 *     the type of the source objects.
 * @param <TO>
 *     the type of the target objects.
 * @author Haixing Hu
 */
public interface Codec<FROM, TO> extends Encoder<FROM, TO>, Decoder<TO, FROM> {
  // empty
}