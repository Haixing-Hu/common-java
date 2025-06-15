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
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * {@link Date}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
public class DateSerializer extends EncoderSerializer<Date> {

  @Serial
  private static final long serialVersionUID = -6691972410224992988L;

  /**
   * 构造一个使用指定编码器的DateSerializer实例。
   *
   * @param encoder
   *     Date编码器。
   */
  public DateSerializer(final Encoder<Date, String> encoder) {
    super(Date.class, encoder, JsonGenerator::writeString);
  }
}