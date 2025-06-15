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
import java.time.Period;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.PeriodCodec;

/**
 * {@link Period} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class PeriodDeserializer extends DecoderDeserializer<Period> {

  @Serial
  private static final long serialVersionUID = -3238345675489526453L;

  /**
   * 单例实例。
   */
  public static final PeriodDeserializer INSTANCE = new PeriodDeserializer();

  /**
   * 构造一个 {@link PeriodDeserializer} 对象。
   */
  public PeriodDeserializer() {
    super(Period.class, PeriodCodec.INSTANCE);
  }

  /**
   * 构造一个 {@link PeriodDeserializer} 对象。
   *
   * @param decoder
   *     指定的解码器。
   */
  public PeriodDeserializer(final Decoder<String, Period> decoder) {
    super(Period.class, decoder);
  }
}
