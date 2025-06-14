////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.PrivateKey;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.EncodingException;

/**
 * 用于在{@link PrivateKey}和字节数组之间进行编码/解码的{@link PrivateKeyCodec}实现，
 * 字节数组采用Secure Shell (SSH)传输层协议兼容格式。
 *
 * @author 胡海星
 * @see <a href="https://www.ietf.org/rfc/rfc4253.txt">RFC-4235: The Secure Shell (SSH) Transport Layer Protocol</a>
 */
public class SshPrivateKeyCodec implements PrivateKeyCodec {

  /**
   * {@inheritDoc}
   */
  @Override
  public PrivateKey decode(final byte[] source) throws DecodingException {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] encode(final PrivateKey source) throws EncodingException {
    return new byte[0];
  }
}