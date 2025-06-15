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
import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link LocalDate} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class LocalDateDeserializer extends DecoderDeserializer<LocalDate> {

  @Serial
  private static final long serialVersionUID = 2892249089469487448L;

  /**
   * 构造一个 {@link LocalDateDeserializer} 对象。
   *
   * @param decoder
   *     指定的解码器。
   */
  public LocalDateDeserializer(final Decoder<String, LocalDate> decoder) {
    super(LocalDate.class, decoder);
  }
}
