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
import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link LocalDateTime} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class LocalDateTimeDeserializer extends DecoderDeserializer<LocalDateTime> {

  @Serial
  private static final long serialVersionUID = 566573999418927054L;

  /**
   * 构造一个 {@link LocalDateTimeDeserializer} 对象。
   *
   * @param decoder
   *     指定的解码器。
   */
  public LocalDateTimeDeserializer(final Decoder<String, LocalDateTime> decoder) {
    super(LocalDateTime.class, decoder);
  }
}
