////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.math.BigInteger;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.BigIntegerCodec;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * The JSON serializer of a {@link BigInteger} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class BigIntegerJsonSerializer extends EncoderSerializer<BigInteger> {

  private static final long serialVersionUID = 2886486450566185022L;

  public static final BigIntegerJsonSerializer INSTANCE =
      new BigIntegerJsonSerializer();

  public BigIntegerJsonSerializer() {
    this(BigIntegerCodec.INSTANCE);
  }

  public BigIntegerJsonSerializer(final BigIntegerCodec codec) {
    super(BigInteger.class, codec, JsonGenerator::writeRawValue);
  }
}
