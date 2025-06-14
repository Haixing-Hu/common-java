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

import ltd.qubit.commons.util.codec.Encoder;

/**
 * 将 {@link PrivateKey} 编码为字节数组的接口。
 *
 * @author 胡海星
 */
public interface PrivateKeyEncoder extends Encoder<PrivateKey, byte[]> {
  //  empty
}