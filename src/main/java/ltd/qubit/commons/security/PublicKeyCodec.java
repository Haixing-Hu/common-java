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
 * 用于在 {@link PublicKey} 和字节数组之间进行编解码的编解码器。
 *
 * @author 胡海星
 */
public interface PublicKeyCodec extends PublicKeyEncoder, PublicKeyDecoder {
  //  empty
}