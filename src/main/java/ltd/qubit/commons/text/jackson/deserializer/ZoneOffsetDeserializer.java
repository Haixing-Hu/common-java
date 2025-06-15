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
import java.time.ZoneOffset;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.ZoneOffsetCodec;

/**
 * {@link ZoneOffset} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class ZoneOffsetDeserializer extends DecoderDeserializer<ZoneOffset> {

  @Serial
  private static final long serialVersionUID = 1108390597460523182L;

  /**
   * 单例实例。
   */
  public static final ZoneOffsetDeserializer INSTANCE =
      new ZoneOffsetDeserializer();

  /**
   * 构造一个 {@link ZoneOffsetDeserializer} 对象。
   */
  public ZoneOffsetDeserializer() {
    super(ZoneOffset.class, ZoneOffsetCodec.INSTANCE);
  }
}
