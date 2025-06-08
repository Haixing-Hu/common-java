////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.PublicKey;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The interface of decoding {@link PublicKey} from byte arrays.
 *
 * @author Haixing Hu
 */
public interface PublicKeyDecoder extends Decoder<byte[], PublicKey> {
  //  empty
}