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
import java.time.YearMonth;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.YearMonthCodec;

/**
 * {@link YearMonth} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class YearMonthDeserializer extends DecoderDeserializer<YearMonth> {

  @Serial
  private static final long serialVersionUID = 7444615730588838387L;

  /**
   * 单例实例。
   */
  public static final YearMonthDeserializer INSTANCE =
      new YearMonthDeserializer();

  /**
   * 构造一个 {@link YearMonthDeserializer} 对象。
   */
  public YearMonthDeserializer() {
    super(YearMonth.class, YearMonthCodec.INSTANCE);
  }

  /**
   * 构造一个 {@link YearMonthDeserializer} 对象。
   *
   * @param decoder
   *     指定的解码器。
   */
  public YearMonthDeserializer(final Decoder<String, YearMonth> decoder) {
    super(YearMonth.class, decoder);
  }
}
