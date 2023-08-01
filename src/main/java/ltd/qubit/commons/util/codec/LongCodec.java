////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * The codec which encode/decode Long values to/from strings.
 *
 * @author Haixing Hu
 */
@Immutable
public class LongCodec implements Codec<Long, String> {

  public static final LongCodec INSTANCE = new LongCodec();

  @Override
  public Long decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (isEmpty(text)) {
      return null;
    }
    try {
      return Long.parseLong(text);
    } catch (final NumberFormatException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final Long source) throws EncodingException {
    if (source == null) {
      return null;
    } else {
      return source.toString();
    }
  }
}
