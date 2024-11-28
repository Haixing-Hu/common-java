////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.PrivateKey;

/**
 * The {@link PrivateKeyCodec} which encode/decode between {@link PrivateKey}s and
 * byte arrays in the PKCS#8 format for the specified {@link SignatureAlgorithm}.
 *
 * @author Haixing Hu
 */
public class SignaturePrivateKeyCodec extends Pkcs8PrivateKeyCodec {

  public SignaturePrivateKeyCodec(final SignatureAlgorithm signatureAlgorithm) {
    super(signatureAlgorithm.cryptoAlgorithm());
  }

}
