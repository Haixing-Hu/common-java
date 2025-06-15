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

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.LongCodec;

/**
 * 将{@link Long}值编码为字符串的Jackson序列化器。
 *
 * @author 胡海星
 */
public class LongAsStringSerializer extends EncoderSerializer<Long> {

  @Serial
  private static final long serialVersionUID = 7392297823492881718L;

  /**
   * 构造一个使用默认编码器的LongAsStringSerializer实例。
   */
  public LongAsStringSerializer() {
    this(LongCodec.INSTANCE);
  }

  /**
   * 构造一个使用指定编码器的LongAsStringSerializer实例。
   *
   * @param encoder
   *     Long编码器。
   */
  public LongAsStringSerializer(final Encoder<Long, String> encoder) {
    super(Long.class, encoder, JsonGenerator::writeString);
  }
}