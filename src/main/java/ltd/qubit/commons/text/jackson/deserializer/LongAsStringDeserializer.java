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

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.LongCodec;

/**
 * 从字符串解码 {@link Long} 值的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class LongAsStringDeserializer extends DecoderDeserializer<Long> {

  @Serial
  private static final long serialVersionUID = -5333449846330022646L;

  /**
   * 单例实例。
   */
  public static final LongAsStringDeserializer INSTANCE =
      new LongAsStringDeserializer();

  /**
   * 构造一个 {@link LongAsStringDeserializer} 对象。
   */
  public LongAsStringDeserializer() {
    super(Long.class, new LongCodec());
  }

  /**
   * 构造一个 {@link LongAsStringDeserializer} 对象。
   *
   * @param decoder
   *     指定的解码器。
   */
  public LongAsStringDeserializer(final Decoder<String, Long> decoder) {
    super(Long.class, decoder);
  }
}
