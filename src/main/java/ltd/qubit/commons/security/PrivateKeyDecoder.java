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

import java.security.PrivateKey;

/**
 * The interface of decoding {@link PrivateKey} from byte arrays.
 *
 * @author Haixing Hu
 */
public interface PrivateKeyDecoder extends Decoder<byte[], PrivateKey> {
  //  empty
}
