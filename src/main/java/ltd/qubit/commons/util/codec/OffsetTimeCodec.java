////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.DateTimeException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;

@Immutable
public class OffsetTimeCodec implements Codec<OffsetTime, String> {

  protected final DateTimeFormatter formatter;

  public OffsetTimeCodec(final DateTimeFormatter formatter) {
    this.formatter = requireNonNull("formatter", formatter);
  }

  @Override
  public OffsetTime decode(final String source) throws DecodingException {
    final String str = new Stripper().strip(source);
    if (isEmpty(str)) {
      return null;
    }
    try {
      return OffsetTime.parse(str, formatter);
    } catch (final DateTimeException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final OffsetTime source) {
    return (source == null ? null : source.format(formatter));
  }
}
