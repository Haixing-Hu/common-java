////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.util.Locale;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.i18n.LocaleUtils;
import ltd.qubit.commons.text.Stripper;

@Immutable
public class PosixLocaleCodec implements Codec<Locale, String> {

  public static final PosixLocaleCodec INSTANCE = new PosixLocaleCodec();

  @Override
  public Locale decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    }
    final Locale result = LocaleUtils.fromPosixLocale(text);
    if (result == null) {
      throw new DecodingException("Invalid POSIX locale ID: " + text);
    }
    return result;
  }

  @Override
  public String encode(final Locale source) {
    return LocaleUtils.toPosixLocale(source);
  }
}
