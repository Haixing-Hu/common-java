////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
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
