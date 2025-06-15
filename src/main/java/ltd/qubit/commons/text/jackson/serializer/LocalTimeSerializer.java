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
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * {@link LocalTime}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
public class LocalTimeSerializer extends EncoderSerializer<LocalTime> {

  @Serial
  private static final long serialVersionUID = -8533742385136266762L;

  /**
   * 构造一个使用指定编码器的LocalTimeSerializer实例。
   *
   * @param encoder
   *     LocalTime编码器。
   */
  public LocalTimeSerializer(final Encoder<LocalTime, String> encoder) {
    super(LocalTime.class, encoder, JsonGenerator::writeString);
  }
}