////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.math.BigInteger;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;

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
