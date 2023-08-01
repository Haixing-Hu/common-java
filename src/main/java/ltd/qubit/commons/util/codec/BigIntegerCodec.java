////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.text.Stripper;

import java.math.BigInteger;
import javax.annotation.concurrent.Immutable;

/**
 * The codec of the {@link BigInteger} class.
 *
 * @author Haixing Hu
 */
@Immutable
public class BigIntegerCodec implements Codec<BigInteger, String> {

  public static final BigIntegerCodec INSTANCE = new BigIntegerCodec();

  @Override
  public BigInteger decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    }
    try {
      return new BigInteger(text);
    } catch (final NumberFormatException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final BigInteger value) {
    return (value == null ? null : value.toString());
  }
}
