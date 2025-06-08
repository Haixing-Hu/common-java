////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.MonthDay;
import java.time.format.DateTimeParseException;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;

@Immutable
public class MonthDayCodec implements Codec<MonthDay, String> {

  public static final MonthDayCodec INSTANCE = new MonthDayCodec();

  @Override
  public MonthDay decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    }
    try {
      return MonthDay.parse(text);
    } catch (final DateTimeParseException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final MonthDay source) throws EncodingException {
    return (source == null ? null : source.toString());
  }
}