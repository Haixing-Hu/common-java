////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.Year;
import java.time.format.DateTimeParseException;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;

@Immutable
public class YearCodec implements Codec<Year, String> {

  public static final YearCodec INSTANCE = new YearCodec();

  @Override
  public Year decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (isEmpty(text)) {
      return null;
    }
    try {
      return Year.parse(text);
    } catch (final DateTimeParseException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final Year source) {
    return (source == null ? null : source.toString());
  }
}
