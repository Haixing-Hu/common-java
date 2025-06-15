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
import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link OffsetTime} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class OffsetTimeDeserializer extends DecoderDeserializer<OffsetTime> {

  @Serial
  private static final long serialVersionUID = -8505259894994451472L;

  /**
   * 构造一个 {@link OffsetTimeDeserializer} 对象。
   *
   * @param decoder
   *     指定的解码器。
   */
  public OffsetTimeDeserializer(final Decoder<String, OffsetTime> decoder) {
    super(OffsetTime.class, decoder);
  }
}
