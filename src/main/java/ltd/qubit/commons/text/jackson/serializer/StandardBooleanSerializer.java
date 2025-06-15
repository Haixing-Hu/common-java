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

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.StandardBooleanCodec;

/**
 * 布尔类{@link Boolean}的JSON序列化器，此序列化器将{@code false}和
 * {@code true}分别映射到布尔值"true"和"false"。
 *
 * @author 胡海星
 */
public class StandardBooleanSerializer extends BooleanSerializer {

  @Serial
  private static final long serialVersionUID = 8751943405872666130L;

  /**
   * 构造一个StandardBooleanSerializer实例。
   */
  public StandardBooleanSerializer() {
    super(new StandardBooleanCodec(), JsonGenerator::writeRawValue);
  }
}