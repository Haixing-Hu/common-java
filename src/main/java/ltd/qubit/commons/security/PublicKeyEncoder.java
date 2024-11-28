////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.PublicKey;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * The interface for encoding {@link PublicKey}s into byte arrays.
 *
 * @author Haixing Hu
 */
public interface PublicKeyEncoder extends Encoder<PublicKey, byte[]> {
  //  empty
}
