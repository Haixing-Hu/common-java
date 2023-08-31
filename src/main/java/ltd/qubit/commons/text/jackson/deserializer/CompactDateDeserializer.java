////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.CompactDateCodec;

import javax.annotation.concurrent.Immutable;
import java.time.LocalDate;

/**
 * 本地日期类 {@link LocalDate} 的 JSON 反序列化器，该反序列器将编码为
 * "yyyyMMdd"形式的日期转换为  {@link LocalDate} 对象。
 *
 * @author Haixing Hu
 */
@Immutable
public class CompactDateDeserializer extends LocalDateDeserializer {

  private static final long serialVersionUID = -3048975391564949515L;

  public static final CompactDateDeserializer INSTANCE =
      new CompactDateDeserializer();

  public CompactDateDeserializer() {
    super(new CompactDateCodec());
  }
}
