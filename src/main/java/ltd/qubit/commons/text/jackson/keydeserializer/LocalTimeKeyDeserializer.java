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
import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link LocalTime} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class LocalTimeKeyDeserializer extends DecoderKeyDeserializer<LocalTime> {

  @Serial
  private static final long serialVersionUID = -450559835040646903L;

  /**
   * 构造一个使用指定解码器的 {@link LocalTimeKeyDeserializer}。
   *
   * @param decoder
   *     字符串到 LocalTime 的解码器。
   */
  public LocalTimeKeyDeserializer(final Decoder<String, LocalTime> decoder) {
    super(LocalTime.class, decoder);
  }
}