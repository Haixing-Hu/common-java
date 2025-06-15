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
import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

/**
 * {@link LocalDateTime} 对象的 JACKSON 键反序列化器，使用 ISO-8601 格式
 * "yyyy-mm-dd HH:mm:ss"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalDateTimeKeyDeserializer extends LocalDateTimeKeyDeserializer {

  @Serial
  private static final long serialVersionUID = -3884818036521351155L;

  /**
   * 默认实例。
   */
  public static final IsoLocalDateTimeKeyDeserializer INSTANCE = new IsoLocalDateTimeKeyDeserializer();

  /**
   * 构造一个 {@link IsoLocalDateTimeKeyDeserializer}。
   */
  public IsoLocalDateTimeKeyDeserializer() {
    super(new IsoLocalDateTimeCodec());
  }
}