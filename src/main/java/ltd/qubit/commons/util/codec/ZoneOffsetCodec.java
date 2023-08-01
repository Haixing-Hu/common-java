////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;

@Immutable
public class ZoneOffsetCodec implements Codec<ZoneOffset, String> {

  public static final ZoneOffsetCodec INSTANCE = new ZoneOffsetCodec();

  @Override
  public ZoneOffset decode(final String source) throws DecodingException {
    final String text = new Stripper().strip(source);
    if (isEmpty(text)) {
      return null;
    }
    try {
      return ZoneOffset.of(text);
    } catch (final DateTimeParseException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final ZoneOffset source) {
    return (source == null ? null : source.toString());
  }
}
