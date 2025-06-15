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
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * {@link LocalDate}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
public class LocalDateSerializer extends EncoderSerializer<LocalDate> {

  @Serial
  private static final long serialVersionUID = -3069712202687578743L;

  /**
   * 构造一个使用指定编码器的LocalDateSerializer实例。
   *
   * @param encoder
   *     LocalDate编码器。
   */
  public LocalDateSerializer(final Encoder<LocalDate, String> encoder) {
    super(LocalDate.class, encoder, JsonGenerator::writeString);
  }
}