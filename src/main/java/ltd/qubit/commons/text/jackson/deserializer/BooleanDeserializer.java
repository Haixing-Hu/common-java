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

/**
 * {@link Boolean}对象的Jackson反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class BooleanDeserializer extends DecoderDeserializer<Boolean> {

  @Serial
  private static final long serialVersionUID = -8623054434907870733L;

  /**
   * 构造Boolean反序列化器。
   *
   * @param decoder
   *     用于解码的解码器。
   */
  public BooleanDeserializer(final Decoder<String, Boolean> decoder) {
    super(Boolean.class, decoder);
  }
}