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
import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link Date} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class DateKeyDeserializer extends DecoderKeyDeserializer<Date> {

  @Serial
  private static final long serialVersionUID = -3876931545882471731L;

  /**
   * 构造一个 {@link DateKeyDeserializer}。
   *
   * @param decoder
   *     指定的字符串到日期的解码器。
   */
  public DateKeyDeserializer(final Decoder<String, Date> decoder) {
    super(Date.class, decoder);
  }
}