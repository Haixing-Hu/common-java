////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import javax.crypto.SecretKey;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The interface for decoding {@link SecretKey}s from byte arrays.
 *
 * @author Haixing Hu
 */
public interface SecretKeyDecoder extends Decoder<byte[], SecretKey> {
  //  empty
}
