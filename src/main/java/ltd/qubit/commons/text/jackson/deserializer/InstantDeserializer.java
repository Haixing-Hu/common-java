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
import java.time.Instant;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link Instant} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
public class InstantDeserializer extends DecoderDeserializer<Instant> {

  @Serial
  private static final long serialVersionUID = -2730539001574693758L;

  /**
   * 构造一个 {@link InstantDeserializer} 对象。
   *
   * @param decoder
   *     指定的解码器。
   */
  public InstantDeserializer(final Decoder<String, Instant> decoder) {
    super(Instant.class, decoder);
  }
}
