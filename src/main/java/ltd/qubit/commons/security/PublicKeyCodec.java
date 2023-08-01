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

/**
 * The codec for encoding/decoding between {@link PublicKey} and byte arrays.
 *
 * @author Haixing Hu
 */
public interface PublicKeyCodec extends PublicKeyEncoder, PublicKeyDecoder {
  //  empty
}
