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

  @Serial
  private static final long serialVersionUID = -3048975391564949515L;

  /**
   * 单例实例。
   */
  public static final CompactDateDeserializer INSTANCE =
      new CompactDateDeserializer();

  /**
   * 构造紧凑日期反序列化器。
   */
  public CompactDateDeserializer() {
    super(new CompactDateCodec());
  }
}