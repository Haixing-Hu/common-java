////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.IntegralBooleanCodec;

/**
 * 布尔类 {@link Boolean} 的 JSON 序列化器，此序列化器将 {@code false} 和
 * {@code true} 分别映射到数字 "0" 和 "1"。
 *
 * @author 胡海星
 */
public class IntegralBooleanSerializer extends BooleanSerializer {

  private static final long serialVersionUID = 8682174067747568247L;

  public IntegralBooleanSerializer() {
    super(new IntegralBooleanCodec(), JsonGenerator::writeRawValue);
  }
}