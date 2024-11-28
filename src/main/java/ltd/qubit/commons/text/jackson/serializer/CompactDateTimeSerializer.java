////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalDateTime;

import ltd.qubit.commons.util.codec.CompactDateTimeCodec;

/**
 * 本地日期类 {@link LocalDateTime} 的 JSON 反序列化器，该反序列器将
 * {@link LocalDateTime} 对象编码为"yyyyMMddHHmmss"形式的日期。
 *
 * @author 胡海星
 */
public class CompactDateTimeSerializer extends LocalDateTimeSerializer {

  private static final long serialVersionUID = 5877272480813523412L;

  public CompactDateTimeSerializer() {
    super(new CompactDateTimeCodec());
  }
}
