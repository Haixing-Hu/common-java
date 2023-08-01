////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.BigIntegerCodec;

import com.fasterxml.jackson.core.JsonGenerator;

import java.math.BigInteger;
import javax.annotation.concurrent.Immutable;

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
