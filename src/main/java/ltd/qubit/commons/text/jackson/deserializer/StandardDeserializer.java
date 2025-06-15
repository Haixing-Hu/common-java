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

import ltd.qubit.commons.util.codec.StandardBooleanCodec;

/**
 * 布尔类 {@link Boolean} 的 JSON 反序列化器，此反序列化器将字符串 "false" 和
 * "true" 分别映射到 {@code false} 和 {@code true}。
 *
 * @author 胡海星
 */
@Immutable
public class StandardDeserializer extends BooleanDeserializer {

  @Serial
  private static final long serialVersionUID = -991903202758631427L;

  /**
   * 单例实例。
   */
  public static final StandardDeserializer INSTANCE =
      new StandardDeserializer();

  /**
   * 构造一个 {@link StandardDeserializer} 对象。
   */
  public StandardDeserializer() {
    super(new StandardBooleanCodec());
  }
}
