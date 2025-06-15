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
import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link Date}对象的Jackson反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class DateDeserializer extends DecoderDeserializer<Date> {

  @Serial
  private static final long serialVersionUID = 6425216688398047328L;

  /**
   * 构造Date反序列化器。
   *
   * @param decoder
   *     用于解码的解码器。
   */
  public DateDeserializer(final Decoder<String, Date> decoder) {
    super(Date.class, decoder);
  }
}