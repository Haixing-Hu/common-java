////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.Serial;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * 枚举类的Jackson序列化器。
 *
 * @param <T>
 *     枚举类型。
 * @author 胡海星
 */
public class EnumSerializer<T extends Enum<T>> extends EncoderSerializer<T> {

  @Serial
  private static final long serialVersionUID = -6894346294188257598L;

  /**
   * 构造一个EnumSerializer实例。
   *
   * @param cls
   *     枚举类的Class对象。
   * @param encoder
   *     枚举值的编码器。
   */
  public EnumSerializer(final Class<T> cls, final Encoder<T, String> encoder) {
    super(cls, encoder, JsonGenerator::writeString);
  }
}