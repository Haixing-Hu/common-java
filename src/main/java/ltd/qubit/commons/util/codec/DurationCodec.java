////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.Duration;
import java.time.format.DateTimeParseException;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;

/**
 * The codec which encode/decode {@link Duration} objects to/from strings.
 *
 * @author Haixing Hu
 */
@Immutable
public class DurationCodec implements Codec<Duration, String> {

  public static final DurationCodec INSTANCE = new DurationCodec();

  @Override
  public Duration decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    }
    try {
      return Duration.parse(text);
    } catch (final DateTimeParseException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final Duration source) throws EncodingException {
    if (source == null) {
      return null;
    } else {
      return source.toString();
    }
  }
}
