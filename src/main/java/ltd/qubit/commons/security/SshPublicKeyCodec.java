////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.PublicKey;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.EncodingException;

/**
 * The {@link PublicKeyCodec} which encode/decode between {@link PublicKey}s and
 * byte arrays in the Secure Shell (SSH) transport layer protocol compatible format.
 *
 * @author Haixing Hu
 * @see <a href="https://www.ietf.org/rfc/rfc4253.txt">RFC-4235: The Secure Shell (SSH) Transport Layer Protocol</a>
 */
public class SshPublicKeyCodec implements PublicKeyCodec {

  @Override
  public PublicKey decode(final byte[] source) throws DecodingException {
    return null;
  }

  @Override
  public byte[] encode(final PublicKey source) throws EncodingException {
    return new byte[0];
  }
}
