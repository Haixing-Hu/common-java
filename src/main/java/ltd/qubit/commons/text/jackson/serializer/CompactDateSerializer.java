////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalDate;

import ltd.qubit.commons.util.codec.CompactDateCodec;

/**
 * 本地日期类 {@link LocalDate} 的 JSON 反序列化器，该反序列器将{@link LocalDate} 对象
 * 编码为"yyyyMMdd"形式的日期。
 *
 * @author 胡海星
 */
public class CompactDateSerializer extends LocalDateSerializer {

  private static final long serialVersionUID = -6912208016616357373L;

  public CompactDateSerializer() {
    super(new CompactDateCodec());
  }
}
