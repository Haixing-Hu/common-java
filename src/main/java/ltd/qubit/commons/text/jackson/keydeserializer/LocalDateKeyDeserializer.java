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
import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link LocalDate} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class LocalDateKeyDeserializer extends DecoderKeyDeserializer<LocalDate> {

  @Serial
  private static final long serialVersionUID = 5679718569124837426L;

  /**
   * 构造一个使用指定解码器的 {@link LocalDateKeyDeserializer}。
   *
   * @param decoder
   *     字符串到 LocalDate 的解码器。
   */
  public LocalDateKeyDeserializer(final Decoder<String, LocalDate> decoder) {
    super(LocalDate.class, decoder);
  }
}