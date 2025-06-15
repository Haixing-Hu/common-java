////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Base64Codec;

/**
 * 用于{@code byte[]}对象的JACKSON键反序列化器，从BASE-64格式编码的字符串反序列化{@code byte[]}。
 *
 * @author 胡海星
 */
@Immutable
public class Base64ByteArrayKeyDeserializer extends DecoderKeyDeserializer<byte[]> {

  @Serial
  private static final long serialVersionUID = -7037446321933808102L;

  /**
   * 单例实例。
   */
  public static final Base64ByteArrayKeyDeserializer INSTANCE =
      new Base64ByteArrayKeyDeserializer();

  /**
   * 构造函数。
   */
  protected Base64ByteArrayKeyDeserializer() {
    super(byte[].class, Base64Codec.INSTANCE);
  }
}
