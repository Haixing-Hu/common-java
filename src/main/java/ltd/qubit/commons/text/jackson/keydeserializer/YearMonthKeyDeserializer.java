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
import java.time.YearMonth;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.YearMonthCodec;

/**
 * {@link YearMonth} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class YearMonthKeyDeserializer extends DecoderKeyDeserializer<YearMonth> {

  @Serial
  private static final long serialVersionUID = -3696402088404922586L;

  /**
   * 默认实例。
   */
  public static final YearMonthKeyDeserializer INSTANCE = new YearMonthKeyDeserializer();

  /**
   * 构造一个 {@link YearMonthKeyDeserializer}。
   */
  public YearMonthKeyDeserializer() {
    super(YearMonth.class, YearMonthCodec.INSTANCE);
  }
}