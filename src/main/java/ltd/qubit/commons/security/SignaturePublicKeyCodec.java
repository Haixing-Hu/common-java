////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.PublicKey;

/**
 * 用于指定 {@link SignatureAlgorithm} 的 {@link PublicKeyCodec}，
 * 在 {@link PublicKey} 和 X.509 格式的字节数组之间进行编码/解码。
 *
 * @author 胡海星
 */
public class SignaturePublicKeyCodec extends X509PublicKeyCodec {

  /**
   * 构造一个 {@link SignaturePublicKeyCodec}。
   *
   * @param signatureAlgorithm
   *     签名算法。
   */
  public SignaturePublicKeyCodec(final SignatureAlgorithm signatureAlgorithm) {
    super(signatureAlgorithm.cryptoAlgorithm());
  }

}