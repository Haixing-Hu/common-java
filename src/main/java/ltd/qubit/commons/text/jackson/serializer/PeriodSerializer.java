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
import java.time.Period;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.PeriodCodec;

/**
 * {@link Period}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class PeriodSerializer extends EncoderSerializer<Period> {

  @Serial
  private static final long serialVersionUID = -8354331200251458534L;

  /**
   * PeriodSerializer的单例实例。
   */
  public static final PeriodSerializer INSTANCE = new PeriodSerializer();

  /**
   * 构造一个使用默认编码器的PeriodSerializer实例。
   */
  public PeriodSerializer() {
    this(PeriodCodec.INSTANCE);
  }

  /**
   * 构造一个使用指定编码器的PeriodSerializer实例。
   *
   * @param encoder
   *     Period编码器。
   */
  public PeriodSerializer(final Encoder<Period, String> encoder) {
    super(Period.class, encoder, JsonGenerator::writeString);
  }
}