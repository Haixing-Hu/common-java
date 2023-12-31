////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;

/**
 * 符合 ISO-8601 的本地日期时间类 {@link LocalDateTime} 的 JSON 反序列化器，其编
 * 码格式为 "yyyy-mm-dd HH:mm:ss"。
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

  private static final long serialVersionUID = 2232065032680091828L;

  public static final IsoLocalDateTimeDeserializer INSTANCE =
      new IsoLocalDateTimeDeserializer();

  public IsoLocalDateTimeDeserializer() {
    super(new IsoLocalDateTimeCodec());
  }
}
