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
import java.time.Year;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.YearCodec;

/**
 * {@link Year} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class YearKeyDeserializer extends DecoderKeyDeserializer<Year> {

  @Serial
  private static final long serialVersionUID = -3081548615222217255L;

  /**
   * 默认实例。
   */
  public static final YearKeyDeserializer INSTANCE = new YearKeyDeserializer();

  /**
   * 构造一个 {@link YearKeyDeserializer}。
   */
  public YearKeyDeserializer() {
    super(Year.class, YearCodec.INSTANCE);
  }
}