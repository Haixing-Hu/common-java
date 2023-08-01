////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.util.Locale;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import ltd.qubit.commons.util.LocaleUtils;

/**
 * The customized JAXB data type adaptor for the {@link Locale} type.
 *
 * <p>The locale will be marshaled to and unmarshaled from the POSIX locale
 * string.
 *
 * @author Haixing Hu
 * @see LocaleUtils#fromPosixLocale(String)
 * @see LocaleUtils#toPosixLocale(Locale)
 */
public class PosixLocaleXmlAdapter extends XmlAdapter<String, Locale> {

  @Override
  public Locale unmarshal(final String v) {
    if (v == null) {
      return null;
    } else {
      return LocaleUtils.fromPosixLocale(v);
    }
  }

  @Override
  public String marshal(final Locale v) {
    if (v == null) {
      return null;
    } else {
      return LocaleUtils.toPosixLocale(v);
    }
  }
}
