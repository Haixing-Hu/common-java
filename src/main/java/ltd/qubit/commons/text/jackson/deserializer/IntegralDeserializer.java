////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.IntegralBooleanCodec;

import javax.annotation.concurrent.Immutable;

/**
 * 布尔类 {@link Boolean} 的 JSON 反序列化器，此反序列化器将字符串 "0" 和 "1"
 * 分别映射到 {@code false} 和 {@code true}。
 *
 * @author Haixing Hu
 */
@Immutable
public class IntegralDeserializer extends BooleanDeserializer {

  private static final long serialVersionUID = -4596041015724358924L;

  public static final IntegralDeserializer INSTANCE =
      new IntegralDeserializer();

  public IntegralDeserializer() {
    super(new IntegralBooleanCodec());
  }
}
