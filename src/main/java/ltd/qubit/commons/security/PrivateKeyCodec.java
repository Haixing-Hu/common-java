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

/**
 * The codec for encoding/decoding between {@link PrivateKey} and byte arrays.
 *
 * @author Haixing Hu
 */
public interface PrivateKeyCodec extends PrivateKeyEncoder, PrivateKeyDecoder {
  //  empty
}
