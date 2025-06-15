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

import ltd.qubit.commons.util.codec.IsoOffsetTimeCodec;

/**
 * {@link OffsetTime} 对象的 JACKSON 键反序列化器，使用 ISO-8601 格式。
 *
 * @author 胡海星
 */
@Immutable
public class IsoOffsetTimeKeyDeserializer extends OffsetTimeKeyDeserializer {

  @Serial
  private static final long serialVersionUID = 6530978579305551410L;

  /**
   * 默认实例。
   */
  public static final IsoOffsetTimeKeyDeserializer INSTANCE = new IsoOffsetTimeKeyDeserializer();

  /**
   * 构造一个 {@link IsoOffsetTimeKeyDeserializer}。
   */
  public IsoOffsetTimeKeyDeserializer() {
    super(IsoOffsetTimeCodec.INSTANCE);
  }
}