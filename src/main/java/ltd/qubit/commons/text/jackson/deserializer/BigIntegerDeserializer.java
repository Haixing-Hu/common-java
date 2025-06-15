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
import java.math.BigInteger;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.BigIntegerCodec;

/**
 * {@link BigInteger}对象的Jackson反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class BigIntegerDeserializer extends DecoderDeserializer<BigInteger> {

  @Serial
  private static final long serialVersionUID = 6725546891166714282L;

  /**
   * 单例实例。
   */
  public static final BigIntegerDeserializer INSTANCE =
      new BigIntegerDeserializer();

  /**
   * 构造BigInteger反序列化器。
   */
  public BigIntegerDeserializer() {
    super(BigInteger.class, BigIntegerCodec.INSTANCE);
  }
}