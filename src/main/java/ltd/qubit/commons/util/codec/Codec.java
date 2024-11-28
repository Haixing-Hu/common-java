////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * The interface of codecs.
 *
 * @author Haixing Hu
 */
public interface Codec<FROM, TO> extends Encoder<FROM, TO>, Decoder<TO, FROM> {
  // empty
}
