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

/**
 * The codec for encoding/decoding between {@link PublicKey} and byte arrays.
 *
 * @author Haixing Hu
 */
public interface PublicKeyCodec extends PublicKeyEncoder, PublicKeyDecoder {
  //  empty
}
