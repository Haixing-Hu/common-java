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
 * The {@link PublicKeyCodec} which encode/decode between {@link PublicKey}s and
 * byte arrays in the X.509 format for the specified {@link SignatureAlgorithm}.
 *
 * @author Haixing Hu
 */
public class SignaturePublicKeyCodec extends X509PublicKeyCodec {

  public SignaturePublicKeyCodec(final SignatureAlgorithm signatureAlgorithm) {
    super(signatureAlgorithm.cryptoAlgorithm());
  }

}
