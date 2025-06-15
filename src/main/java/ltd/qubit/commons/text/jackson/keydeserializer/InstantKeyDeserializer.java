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
import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link Instant} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class InstantKeyDeserializer extends DecoderKeyDeserializer<Instant> {

  @Serial
  private static final long serialVersionUID = 564308276221268369L;

  /**
   * 构造一个使用指定解码器的 {@link InstantKeyDeserializer}。
   *
   * @param decoder
   *     字符串到 Instant 的解码器。
   */
  public InstantKeyDeserializer(final Decoder<String, Instant> decoder) {
    super(Instant.class, decoder);
  }
}