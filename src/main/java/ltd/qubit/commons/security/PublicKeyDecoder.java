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

import ltd.qubit.commons.util.codec.Decoder;

/**
 * 从字节数组解码 {@link PublicKey} 的接口。
 *
 * @author 胡海星
 */
public interface PublicKeyDecoder extends Decoder<byte[], PublicKey> {
  //  empty
}