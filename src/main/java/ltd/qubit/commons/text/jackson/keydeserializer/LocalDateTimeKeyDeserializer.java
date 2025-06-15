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
import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link LocalDateTime} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class LocalDateTimeKeyDeserializer extends DecoderKeyDeserializer<LocalDateTime> {

  @Serial
  private static final long serialVersionUID = -912874654699073643L;

  /**
   * 构造一个使用指定解码器的 {@link LocalDateTimeKeyDeserializer}。
   *
   * @param decoder
   *     字符串到 LocalDateTime 的解码器。
   */
  public LocalDateTimeKeyDeserializer(final Decoder<String, LocalDateTime> decoder) {
    super(LocalDateTime.class, decoder);
  }
}