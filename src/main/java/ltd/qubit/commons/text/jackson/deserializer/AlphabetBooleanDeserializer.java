////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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

  private static final long serialVersionUID = -991903202758631427L;

  public static final AlphabetBooleanDeserializer INSTANCE =
      new AlphabetBooleanDeserializer();

  public AlphabetBooleanDeserializer() {
    super(new AlphabetBooleanCodec());
  }
}
