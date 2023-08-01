////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import javax.crypto.SecretKey;

/**
 * The codec for encoding/decoding between {@link SecretKey} and byte arrays.
 *
 * @author Haixing Hu
 */
public interface SecretKeyCodec extends SecretKeyEncoder, SecretKeyDecoder {
  //  empty
}
