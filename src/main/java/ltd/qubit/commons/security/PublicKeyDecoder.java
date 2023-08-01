////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
