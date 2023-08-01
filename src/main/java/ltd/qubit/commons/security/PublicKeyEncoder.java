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

import java.security.PublicKey;

/**
 * The interface for encoding {@link PublicKey}s into byte arrays.
 *
 * @author Haixing Hu
 */
public interface PublicKeyEncoder extends Encoder<PublicKey, byte[]> {
  //  empty
}
