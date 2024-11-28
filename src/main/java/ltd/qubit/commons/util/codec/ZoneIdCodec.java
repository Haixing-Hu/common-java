////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.ZoneId;
import java.time.format.DateTimeParseException;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;

@Immutable
public class ZoneIdCodec implements Codec<ZoneId, String> {

  public static final ZoneIdCodec INSTANCE = new ZoneIdCodec();

  @Override
  public ZoneId decode(final String source) throws DecodingException {
    final String text = new Stripper().strip(source);
    if (isEmpty(text)) {
      return null;
    }
    try {
      return ZoneId.of(text);
    } catch (final DateTimeParseException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final ZoneId source) {
    return (source == null ? null : source.toString());
  }
}
