////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.CompactDateCodec;

/**
 * 本地日期类 {@link LocalDate} 的 JSON 反序列化器，该反序列器将编码为
 * "yyyyMMdd"形式的日期转换为  {@link LocalDate} 对象。
 *
 * @author 胡海星
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
