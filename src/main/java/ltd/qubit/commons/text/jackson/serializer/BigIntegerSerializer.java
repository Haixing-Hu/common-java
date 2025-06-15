////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.Serial;
import java.math.BigInteger;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.BigIntegerCodec;

/**
 * {@link BigInteger}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class BigIntegerSerializer extends EncoderSerializer<BigInteger> {

  @Serial
  private static final long serialVersionUID = 2886486450566185022L;

  /**
   * BigIntegerSerializer的单例实例。
   */
  public static final BigIntegerSerializer INSTANCE = new BigIntegerSerializer();

  /**
   * 构造一个默认的BigIntegerSerializer实例。
   */
  public BigIntegerSerializer() {
    this(BigIntegerCodec.INSTANCE);
  }

  /**
   * 构造一个使用指定编解码器的BigIntegerSerializer实例。
   *
   * @param codec
   *     BigInteger编解码器。
   */
  public BigIntegerSerializer(final BigIntegerCodec codec) {
    super(BigInteger.class, codec, JsonGenerator::writeRawValue);
  }
}