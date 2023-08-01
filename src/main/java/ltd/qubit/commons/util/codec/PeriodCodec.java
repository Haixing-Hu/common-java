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

import java.time.Period;
import java.time.format.DateTimeParseException;
import javax.annotation.concurrent.Immutable;

/**
 * The codec which encode/decode {@link Period} objects to/from strings.
 *
 * @author Haixing Hu
 */
@Immutable
public class PeriodCodec implements Codec<Period, String> {

  public static final PeriodCodec INSTANCE = new PeriodCodec();

  @Override
  public Period decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    }
    try {
      return Period.parse(text);
    } catch (final DateTimeParseException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final Period source) throws EncodingException {
    if (source == null) {
      return null;
    } else {
      return source.toString();
    }
  }
}
