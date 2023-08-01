////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
