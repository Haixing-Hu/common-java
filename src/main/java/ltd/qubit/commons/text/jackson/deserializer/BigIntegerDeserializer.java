////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.BigIntegerCodec;

import java.math.BigInteger;
import javax.annotation.concurrent.Immutable;

/**
 * The JSON deserializer of a {@link BigInteger} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class BigIntegerDeserializer extends DecoderDeserializer<BigInteger> {

  private static final long serialVersionUID = 6725546891166714282L;

  public static final BigIntegerDeserializer INSTANCE =
      new BigIntegerDeserializer();

  public BigIntegerDeserializer() {
    super(BigInteger.class, BigIntegerCodec.INSTANCE);
  }
}
