////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.PrivateKey;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * The interface for encoding {@link PrivateKey}s into byte arrays.
 *
 * @author Haixing Hu
 */
public interface PrivateKeyEncoder extends Encoder<PrivateKey, byte[]> {
  //  empty
}
