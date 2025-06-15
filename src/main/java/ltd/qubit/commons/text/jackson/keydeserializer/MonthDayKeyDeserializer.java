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
import java.time.MonthDay;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.MonthDayCodec;

/**
 * {@link MonthDay} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class MonthDayKeyDeserializer extends DecoderKeyDeserializer<MonthDay> {

  @Serial
  private static final long serialVersionUID = -1096409578194019573L;

  /**
   * 默认实例。
   */
  public static final MonthDayKeyDeserializer INSTANCE = new MonthDayKeyDeserializer();

  /**
   * 构造一个 {@link MonthDayKeyDeserializer}。
   */
  public MonthDayKeyDeserializer() {
    super(MonthDay.class, MonthDayCodec.INSTANCE);
  }
}