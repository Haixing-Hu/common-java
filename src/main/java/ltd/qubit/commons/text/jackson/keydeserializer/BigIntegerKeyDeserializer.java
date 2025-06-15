////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.io.Serial;
import java.math.BigInteger;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.BigIntegerCodec;

/**
 * {@link BigInteger} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class BigIntegerKeyDeserializer extends DecoderKeyDeserializer<BigInteger> {

  @Serial
  private static final long serialVersionUID = -3816214397682444311L;

  /**
   * {@link BigIntegerKeyDeserializer} 的单例实例。
   */
  public static final BigIntegerKeyDeserializer INSTANCE = new BigIntegerKeyDeserializer();

  /**
   * 构造一个 {@link BigIntegerKeyDeserializer}。
   */
  public BigIntegerKeyDeserializer() {
    super(BigInteger.class, BigIntegerCodec.INSTANCE);
  }
}
