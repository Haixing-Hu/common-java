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
import java.time.Year;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.YearCodec;

/**
 * {@link Year} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class YearDeserializer extends DecoderDeserializer<Year> {

  @Serial
  private static final long serialVersionUID = 7444615730588838387L;

  /**
   * 单例实例。
   */
  public static final YearDeserializer INSTANCE = new YearDeserializer();

  /**
   * 构造一个 {@link YearDeserializer} 对象。
   */
  public YearDeserializer() {
    super(Year.class, YearCodec.INSTANCE);
  }

  /**
   * 构造一个 {@link YearDeserializer} 对象。
   *
   * @param decoder
   *     指定的解码器。
   */
  public YearDeserializer(final Decoder<String, Year> decoder) {
    super(Year.class, decoder);
  }
}
