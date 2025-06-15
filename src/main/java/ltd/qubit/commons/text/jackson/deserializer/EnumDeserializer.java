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

import ltd.qubit.commons.util.codec.Decoder;

/**
 * 枚举类的Jackson反序列化器。
 *
 * @param <T>
 *     枚举类型。
 * @author 胡海星
 */
public class EnumDeserializer<T extends Enum<T>> extends DecoderDeserializer<T> {

  @Serial
  private static final long serialVersionUID = 8423949645823757703L;

  /**
   * 构造枚举反序列化器。
   *
   * @param cls
   *     枚举类。
   * @param decoder
   *     用于解码的解码器。
   */
  public EnumDeserializer(final Class<T> cls, final Decoder<String, T> decoder) {
    super(cls, decoder);
  }
}