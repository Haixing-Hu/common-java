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

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.EncodingException;

/**
 * The {@link PrivateKeyCodec} which encode/decode between {@link PrivateKey}s and
 * byte arrays in the Secure Shell (SSH) transport layer protocol compatible format.
 *
 * @author Haixing Hu
 * @see <a href="https://www.ietf.org/rfc/rfc4253.txt">RFC-4235: The Secure Shell (SSH) Transport Layer Protocol</a>
 */
public class SshPrivateKeyCodec implements PrivateKeyCodec {

  @Override
  public PrivateKey decode(final byte[] source) throws DecodingException {
    return null;
  }

  @Override
  public byte[] encode(final PrivateKey source) throws EncodingException {
    return new byte[0];
  }
}
