////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.Serial;
import java.time.Duration;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.DurationCodec;
import ltd.qubit.commons.util.codec.Encoder;

/**
 * {@link Duration}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class DurationSerializer extends EncoderSerializer<Duration> {

  @Serial
  private static final long serialVersionUID = -8354331200251458534L;

  /**
   * DurationSerializer的单例实例。
   */
  public static final DurationSerializer INSTANCE = new DurationSerializer();

  /**
   * 构造一个默认的DurationSerializer实例。
   */
  public DurationSerializer() {
    this(DurationCodec.INSTANCE);
  }

  /**
   * 构造一个使用指定编码器的DurationSerializer实例。
   *
   * @param encoder
   *     Duration编码器。
   */
  public DurationSerializer(final Encoder<Duration, String> encoder) {
    super(Duration.class, encoder, JsonGenerator::writeString);
  }
}