////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.Serial;
import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

/**
 * {@link LocalDateTime}对象的Jackson序列化器，使用ISO-8601格式"yyyy-MM-dd HH:mm:ss"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalDateTimeSerializer extends LocalDateTimeSerializer {

  @Serial
  private static final long serialVersionUID = -1860496449765162843L;

  /**
   * IsoLocalDateTimeSerializer的单例实例。
   */
  public static final IsoLocalDateTimeSerializer INSTANCE = new IsoLocalDateTimeSerializer();

  /**
   * 构造一个新的IsoLocalDateTimeSerializer实例。
   */
  public IsoLocalDateTimeSerializer() {
    super(new IsoLocalDateTimeCodec());
  }
}