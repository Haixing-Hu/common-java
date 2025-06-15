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
import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * {@link Instant}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class InstantSerializer extends EncoderSerializer<Instant> {

  @Serial
  private static final long serialVersionUID = -7990656902566434345L;

  /**
   * 构造一个使用指定编码器的InstantSerializer实例。
   *
   * @param encoder
   *     Instant编码器。
   */
  public InstantSerializer(final Encoder<Instant, String> encoder) {
    super(Instant.class, encoder, JsonGenerator::writeString);
  }
}