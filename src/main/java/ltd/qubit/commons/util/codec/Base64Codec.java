////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.util.Base64;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.text.Stripper;

/**
 * 对字节数组进行 BASE-64 编码解码的编码解码器。
 *
 * @author 胡海星
 */
@Immutable
public class Base64Codec implements Codec<byte[], String> {

  public static final Base64Codec INSTANCE = new Base64Codec();

  private final Base64.Encoder encoder = Base64.getEncoder();
  private final Base64.Decoder decoder = Base64.getDecoder();

  @Override
  public String encode(final byte[] source) {
    if (source == null) {
      return null;
    }
    return encoder.encodeToString(source);
  }

  @Override
  public byte[] decode(final String source) throws DecodingException {
    final String text = new Stripper().strip(source);
    if (text == null) {
      return null;
    }
    if (text.isEmpty()) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    try {
      return decoder.decode(text);
    } catch (final IllegalArgumentException e) {
      throw new DecodingException(e);
    }
  }
}
