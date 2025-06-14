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

import ltd.qubit.commons.util.codec.Encoder;

/**
 * 将 {@link SecretKey} 编码为字节数组的接口。
 *
 * @author 胡海星
 */
public interface SecretKeyEncoder extends Encoder<SecretKey, byte[]> {
  //  empty
}