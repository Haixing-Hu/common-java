////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.math.BigInteger;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.BigIntegerCodec;

/**
 * The JACKSON key deserializer of a {@link BigInteger} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class BigIntegerKeyDeserializer extends DecoderKeyDeserializer<BigInteger> {

  private static final long serialVersionUID = -3816214397682444311L;

  public static final BigIntegerKeyDeserializer INSTANCE = new BigIntegerKeyDeserializer();

  public BigIntegerKeyDeserializer() {
    super(BigInteger.class, BigIntegerCodec.INSTANCE);
  }
}
