////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.math.BigInteger;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.BigIntegerCodec;

/**
 * The JACKSON serializer of a {@link BigInteger} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class BigIntegerSerializer extends EncoderSerializer<BigInteger> {

  private static final long serialVersionUID = 2886486450566185022L;

  public static final BigIntegerSerializer INSTANCE = new BigIntegerSerializer();

  public BigIntegerSerializer() {
    this(BigIntegerCodec.INSTANCE);
  }

  public BigIntegerSerializer(final BigIntegerCodec codec) {
    super(BigInteger.class, codec, JsonGenerator::writeRawValue);
  }
}
