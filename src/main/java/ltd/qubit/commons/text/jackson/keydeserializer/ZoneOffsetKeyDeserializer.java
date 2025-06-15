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
import java.time.ZoneOffset;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.ZoneOffsetCodec;

/**
 * {@link ZoneOffset} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class ZoneOffsetKeyDeserializer extends DecoderKeyDeserializer<ZoneOffset> {

  @Serial
  private static final long serialVersionUID = 2457561833385507287L;

  /**
   * 默认实例。
   */
  public static final ZoneOffsetKeyDeserializer INSTANCE = new ZoneOffsetKeyDeserializer();

  /**
   * 构造一个 {@link ZoneOffsetKeyDeserializer}。
   */
  public ZoneOffsetKeyDeserializer() {
    super(ZoneOffset.class, ZoneOffsetCodec.INSTANCE);
  }
}