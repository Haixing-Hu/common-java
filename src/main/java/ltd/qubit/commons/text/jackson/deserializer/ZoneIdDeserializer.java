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
import java.time.ZoneId;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.ZoneIdCodec;

/**
 * {@link ZoneId} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class ZoneIdDeserializer extends DecoderDeserializer<ZoneId> {

  @Serial
  private static final long serialVersionUID = -2316865805523121328L;

  /**
   * 单例实例。
   */
  public static final ZoneIdDeserializer INSTANCE = new ZoneIdDeserializer();

  /**
   * 构造一个 {@link ZoneIdDeserializer} 对象。
   */
  public ZoneIdDeserializer() {
    super(ZoneId.class, ZoneIdCodec.INSTANCE);
  }
}
