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
import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link LocalTime} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class LocalTimeDeserializer extends DecoderDeserializer<LocalTime> {

  @Serial
  private static final long serialVersionUID = -8851269510603079378L;

  /**
   * 构造一个 {@link LocalTimeDeserializer} 对象。
   *
   * @param decoder
   *     指定的解码器。
   */
  public LocalTimeDeserializer(final Decoder<String, LocalTime> decoder) {
    super(LocalTime.class, decoder);
  }
}
