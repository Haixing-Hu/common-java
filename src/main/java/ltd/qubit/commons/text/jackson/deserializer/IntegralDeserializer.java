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

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IntegralBooleanCodec;

/**
 * 布尔类 {@link Boolean} 的 JSON 反序列化器，此反序列化器将字符串 "0" 和 "1"
 * 分别映射到 {@code false} 和 {@code true}。
 *
 * @author 胡海星
 */
@Immutable
public class IntegralDeserializer extends BooleanDeserializer {

  @Serial
  private static final long serialVersionUID = -4596041015724358924L;

  /**
   * 单例实例。
   */
  public static final IntegralDeserializer INSTANCE =
      new IntegralDeserializer();

  /**
   * 构造一个 {@link IntegralDeserializer} 对象。
   */
  public IntegralDeserializer() {
    super(new IntegralBooleanCodec());
  }
}
