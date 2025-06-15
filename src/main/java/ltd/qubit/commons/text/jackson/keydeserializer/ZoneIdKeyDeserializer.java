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
import java.time.ZoneId;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.ZoneIdCodec;

/**
 * {@link ZoneId} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class ZoneIdKeyDeserializer extends DecoderKeyDeserializer<ZoneId> {

  @Serial
  private static final long serialVersionUID = 2056166166657379211L;

  /**
   * 默认实例。
   */
  public static final ZoneIdKeyDeserializer INSTANCE = new ZoneIdKeyDeserializer();

  /**
   * 构造一个 {@link ZoneIdKeyDeserializer}。
   */
  public ZoneIdKeyDeserializer() {
    super(ZoneId.class, ZoneIdCodec.INSTANCE);
  }
}