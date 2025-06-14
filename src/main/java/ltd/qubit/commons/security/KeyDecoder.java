////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 用于解码加密或解密的密码学密钥的类。
 *
 * @author 胡海星
 */
public class KeyDecoder {

  private final CryptoAlgorithm algorithm;

  /**
   * 构造一个密钥解码器。
   *
   * @param algorithm
   *     加密算法。
   */
  public KeyDecoder(final CryptoAlgorithm algorithm) {
    this.algorithm = requireNonNull("algorithm", algorithm);
  }

  /**
   * 获取加密算法。
   *
   * @return
   *     加密算法。
   */
  public CryptoAlgorithm getAlgorithm() {
    return algorithm;
  }

}