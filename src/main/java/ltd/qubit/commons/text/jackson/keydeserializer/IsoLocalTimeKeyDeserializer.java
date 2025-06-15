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
import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

/**
 * {@link LocalTime} 对象的 JACKSON 键反序列化器，使用 ISO-8601 格式
 * "HH:mm:ss"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalTimeKeyDeserializer extends LocalTimeKeyDeserializer {

  @Serial
  private static final long serialVersionUID = -4386919975438873483L;

  /**
   * 默认实例。
   */
  public static final IsoLocalTimeKeyDeserializer INSTANCE = new IsoLocalTimeKeyDeserializer();

  /**
   * 构造一个 {@link IsoLocalTimeKeyDeserializer}。
   */
  public IsoLocalTimeKeyDeserializer() {
    super(new IsoLocalTimeCodec());
  }
}