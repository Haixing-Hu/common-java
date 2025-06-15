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
import java.time.Duration;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.DurationCodec;

/**
 * {@link Duration}对象的Jackson反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class DurationDeserializer extends DecoderDeserializer<Duration> {

  @Serial
  private static final long serialVersionUID = -3238345675489526453L;

  /**
   * 单例实例。
   */
  public static final DurationDeserializer INSTANCE = new DurationDeserializer();

  /**
   * 构造Duration反序列化器。
   */
  public DurationDeserializer() {
    super(Duration.class, DurationCodec.INSTANCE);
  }

  /**
   * 构造具有指定解码器的Duration反序列化器。
   *
   * @param decoder
   *     用于解码的解码器。
   */
  public DurationDeserializer(final Decoder<String, Duration> decoder) {
    super(Duration.class, decoder);
  }
}