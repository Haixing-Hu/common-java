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
import java.time.MonthDay;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.MonthDayCodec;

/**
 * {@link MonthDay} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class MonthDayDeserializer extends DecoderDeserializer<MonthDay> {

  @Serial
  private static final long serialVersionUID = -3300132072526075258L;

  /**
   * 单例实例。
   */
  public static final MonthDayDeserializer INSTANCE =
      new MonthDayDeserializer();

  /**
   * 构造一个 {@link MonthDayDeserializer} 对象。
   */
  public MonthDayDeserializer() {
    super(MonthDay.class, MonthDayCodec.INSTANCE);
  }

  /**
   * 构造一个 {@link MonthDayDeserializer} 对象。
   *
   * @param decoder
   *     指定的解码器。
   */
  public MonthDayDeserializer(final Decoder<String, MonthDay> decoder) {
    super(MonthDay.class, decoder);
  }
}
