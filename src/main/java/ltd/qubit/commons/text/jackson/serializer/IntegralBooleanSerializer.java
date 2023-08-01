////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.IntegralBooleanCodec;

import com.fasterxml.jackson.core.JsonGenerator;

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
