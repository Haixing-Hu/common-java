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

/**
 * 用于指定 {@link SignatureAlgorithm} 的 {@link PrivateKeyCodec}，
 * 在 {@link PrivateKey} 和 PKCS#8 格式的字节数组之间进行编码/解码。
 *
 * @author 胡海星
 */
public class SignaturePrivateKeyCodec extends Pkcs8PrivateKeyCodec {

  /**
   * 构造一个 {@link SignaturePrivateKeyCodec}。
   *
   * @param signatureAlgorithm
   *     签名算法。
   */
  public SignaturePrivateKeyCodec(final SignatureAlgorithm signatureAlgorithm) {
    super(signatureAlgorithm.cryptoAlgorithm());
  }

}