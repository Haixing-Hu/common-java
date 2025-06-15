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
import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

/**
 * {@link LocalDateTime} 对象的 JACKSON 反序列化器，采用 ISO-8601 格式 "yyyy-mm-dd HH:mm:ss"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

  @Serial
  private static final long serialVersionUID = 2232065032680091828L;

  /**
   * 单例实例。
   */
  public static final IsoLocalDateTimeDeserializer INSTANCE =
      new IsoLocalDateTimeDeserializer();

  /**
   * 构造一个 {@link IsoLocalDateTimeDeserializer} 对象。
   */
  public IsoLocalDateTimeDeserializer() {
    super(new IsoLocalDateTimeCodec());
  }
}
