////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.AlphabetBooleanCodec;

/**
 * 布尔类{@link Boolean}的JSON反序列化器，此反序列化器将字符串"N"和"Y"分别映射到
 * {@code false}和{@code true}。
 *
 * @author 胡海星
 */
@Immutable
public class AlphabetBooleanDeserializer extends BooleanDeserializer {

  /**
   * 序列化版本号。
   */
  private static final long serialVersionUID = -991903202758631427L;

  /**
   * 单例实例。
   */
  public static final AlphabetBooleanDeserializer INSTANCE =
      new AlphabetBooleanDeserializer();

  /**
   * 构造字母布尔反序列化器。
   */
  public AlphabetBooleanDeserializer() {
    super(new AlphabetBooleanCodec());
  }
}