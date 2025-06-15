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
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * {@link LocalDateTime}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
public class LocalDateTimeSerializer extends EncoderSerializer<LocalDateTime> {

  @Serial
  private static final long serialVersionUID = -3899350055750189523L;

  /**
   * 构造一个使用指定编码器的LocalDateTimeSerializer实例。
   *
   * @param encoder
   *     LocalDateTime编码器。
   */
  public LocalDateTimeSerializer(final Encoder<LocalDateTime, String> encoder) {
    super(LocalDateTime.class, encoder, JsonGenerator::writeString);
  }
}