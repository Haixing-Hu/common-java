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
import java.time.Duration;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.DurationCodec;

/**
 * {@link Duration} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class DurationKeyDeserializer extends DecoderKeyDeserializer<Duration> {

  @Serial
  private static final long serialVersionUID = -5038177225248543742L;

  /**
   * 默认实例。
   */
  public static final DurationKeyDeserializer INSTANCE = new DurationKeyDeserializer();

  /**
   * 构造一个 {@link DurationKeyDeserializer}。
   */
  public DurationKeyDeserializer() {
    super(Duration.class, DurationCodec.INSTANCE);
  }

  /**
   * 构造一个使用指定解码器的 {@link DurationKeyDeserializer}。
   *
   * @param decoder
   *     字符串到 Duration 的解码器。
   */
  public DurationKeyDeserializer(final Decoder<String, Duration> decoder) {
    super(Duration.class, decoder);
  }
}