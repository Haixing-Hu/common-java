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
import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * {@link OffsetTime} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class OffsetTimeKeyDeserializer extends DecoderKeyDeserializer<OffsetTime> {

  @Serial
  private static final long serialVersionUID = -3294986922172750656L;

  /**
   * 构造一个使用指定解码器的 {@link OffsetTimeKeyDeserializer}。
   *
   * @param decoder
   *     字符串到 OffsetTime 的解码器。
   */
  public OffsetTimeKeyDeserializer(final Decoder<String, OffsetTime> decoder) {
    super(OffsetTime.class, decoder);
  }
}