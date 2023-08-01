////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import ltd.qubit.commons.util.codec.Decoder;

import javax.crypto.SecretKey;

/**
 * The interface for decoding {@link SecretKey}s from byte arrays.
 *
 * @author Haixing Hu
 */
public interface SecretKeyDecoder extends Decoder<byte[], SecretKey> {
  //  empty
}
