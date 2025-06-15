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

import ltd.qubit.commons.util.codec.CompactDateTimeCodec;

/**
 * 本地日期时间类 {@link LocalDateTime} 的 JSON 序列化器，该序列化器将
 * {@link LocalDateTime} 对象编码为"yyyyMMddHHmmss"形式的日期时间。
 *
 * @author 胡海星
 */
public class CompactDateTimeSerializer extends LocalDateTimeSerializer {

  @Serial
  private static final long serialVersionUID = 5877272480813523412L;

  /**
   * 构造一个新的CompactDateTimeSerializer实例。
   */
  public CompactDateTimeSerializer() {
    super(new CompactDateTimeCodec());
  }
}