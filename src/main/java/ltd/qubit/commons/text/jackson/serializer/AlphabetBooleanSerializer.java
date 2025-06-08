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

import ltd.qubit.commons.util.codec.AlphabetBooleanCodec;

/**
 * 布尔类 {@link Boolean} 的 JSON 序列化器，此序列化器将 {@code false} 和
 * {@code true} 分别映射到字符串 "N" 和 "Y"。
 *
 * @author 胡海星
 */
public class AlphabetBooleanSerializer extends BooleanSerializer {

  private static final long serialVersionUID = 8751943405872666130L;

  public AlphabetBooleanSerializer() {
    super(new AlphabetBooleanCodec(), JsonGenerator::writeString);
  }
}