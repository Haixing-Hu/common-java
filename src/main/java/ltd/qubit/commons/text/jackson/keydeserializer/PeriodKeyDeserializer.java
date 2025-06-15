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
import java.time.Period;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.PeriodCodec;

/**
 * {@link Period} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class PeriodKeyDeserializer extends DecoderKeyDeserializer<Period> {

  @Serial
  private static final long serialVersionUID = 284772844258670286L;

  /**
   * 默认实例。
   */
  public static final PeriodKeyDeserializer INSTANCE = new PeriodKeyDeserializer();

  /**
   * 构造一个 {@link PeriodKeyDeserializer}。
   */
  public PeriodKeyDeserializer() {
    super(Period.class, PeriodCodec.INSTANCE);
  }

  /**
   * 构造一个使用指定解码器的 {@link PeriodKeyDeserializer}。
   *
   * @param decoder
   *     字符串到 Period 的解码器。
   */
  public PeriodKeyDeserializer(final Decoder<String, Period> decoder) {
    super(Period.class, decoder);
  }
}