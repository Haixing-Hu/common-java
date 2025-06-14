////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * MAC（消息认证码）算法的密钥编解码器。
 *
 * @author 胡海星
 */
public class MacSecretKeyCodec implements SecretKeyCodec {

  /**
   * MAC 算法。
   */
  private final MacAlgorithm algorithm;

  /**
   * 构造一个 MAC 密钥编解码器。
   *
   * @param algorithm
   *     MAC 算法。
   */
  public MacSecretKeyCodec(final MacAlgorithm algorithm) {
    this.algorithm = requireNonNull("algorithm", algorithm);
  }

  /**
   * 获取 MAC 算法。
   *
   * @return
   *     MAC 算法。
   */
  public MacAlgorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SecretKey decode(final byte[] source) {
    return new SecretKeySpec(source, algorithm.code());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] encode(final SecretKey source) {
    return source.getEncoded();
  }
}