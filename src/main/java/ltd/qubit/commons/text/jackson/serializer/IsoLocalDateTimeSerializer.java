////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

/**
 * 符合 ISO-8601 的本地日期时间类 {@link LocalDateTime} 的 JSON 序列化器，其编码格
 * 式为 "yyyy-mm-dd HH:mm:ss"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalDateTimeSerializer extends LocalDateTimeSerializer {

  private static final long serialVersionUID = -1860496449765162843L;

  public static final IsoLocalDateTimeSerializer INSTANCE =
      new IsoLocalDateTimeSerializer();

  public IsoLocalDateTimeSerializer() {
    super(new IsoLocalDateTimeCodec());
  }
}
