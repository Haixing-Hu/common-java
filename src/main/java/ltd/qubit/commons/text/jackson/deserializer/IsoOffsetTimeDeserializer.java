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
import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoOffsetTimeCodec;

/**
 * {@link OffsetTime} 对象的 JACKSON 反序列化器，采用 ISO-8601 格式。
 *
 * @author 胡海星
 */
@Immutable
public class IsoOffsetTimeDeserializer extends OffsetTimeDeserializer {

  @Serial
  private static final long serialVersionUID = -2608618436291577502L;

  /**
   * 单例实例。
   */
  public static final IsoOffsetTimeDeserializer INSTANCE = new IsoOffsetTimeDeserializer();

  /**
   * 构造一个 {@link IsoOffsetTimeDeserializer} 对象。
   */
  public IsoOffsetTimeDeserializer() {
    super(IsoOffsetTimeCodec.INSTANCE);
  }
}
