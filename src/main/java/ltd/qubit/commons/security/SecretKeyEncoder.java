////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import ltd.qubit.commons.util.codec.Encoder;

import javax.crypto.SecretKey;

/**
 * The interface for encoding {@link SecretKey}s into byte arrays.
 *
 * @author Haixing Hu
 */
public interface SecretKeyEncoder extends Encoder<SecretKey, byte[]> {
  //  empty
}
