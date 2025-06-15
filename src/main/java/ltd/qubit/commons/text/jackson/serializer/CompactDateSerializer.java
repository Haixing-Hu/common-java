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
import java.time.LocalDate;

import ltd.qubit.commons.util.codec.CompactDateCodec;

/**
 * 本地日期类 {@link LocalDate} 的 JSON 序列化器，该序列化器将{@link LocalDate} 对象
 * 编码为"yyyyMMdd"形式的日期。
 *
 * @author 胡海星
 */
public class CompactDateSerializer extends LocalDateSerializer {

  @Serial
  private static final long serialVersionUID = -6912208016616357373L;

  /**
   * 构造一个新的CompactDateSerializer实例。
   */
  public CompactDateSerializer() {
    super(new CompactDateCodec());
  }
}