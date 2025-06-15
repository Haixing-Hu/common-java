////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Base64Codec;

/**
 * {@code byte[]}对象的Jackson反序列化器，将Base64编码格式的字符串反序列化为字节数组。
 *
 * @author 胡海星
 */
@Immutable
public class Base64ByteArrayDeserializer extends DecoderDeserializer<byte[]> {

  @Serial
  private static final long serialVersionUID = 4060958725438976043L;

  /**
   * 单例实例。
   */
  public static final Base64ByteArrayDeserializer INSTANCE =
      new Base64ByteArrayDeserializer();

  /**
   * 构造Base64字节数组反序列化器。
   */
  public Base64ByteArrayDeserializer() {
    super(byte[].class, Base64Codec.INSTANCE);
  }
}