////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.CompactDateTimeCodec;

import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;

/**
 * 本地日期类 {@link LocalDateTime} 的 JSON 反序列化器，该反序列器将编码为
 *  "yyyyMMddHHmmss"形式的日期转换为  {@link LocalDateTime} 对象。
 *
 * @author Haixing Hu
 */
@Immutable
public class CompactDateTimeDeserializer extends LocalDateTimeDeserializer {

  private static final long serialVersionUID = -462246651244711734L;

  public static final CompactDateTimeDeserializer INSTANCE =
      new CompactDateTimeDeserializer();

  public CompactDateTimeDeserializer() {
    super(new CompactDateTimeCodec());
  }
}
