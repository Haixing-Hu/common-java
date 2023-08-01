////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import ltd.qubit.commons.lang.StringUtils;

import java.util.Locale;
import javax.annotation.Nullable;

/**
 * Provide utilities functions deal with locale.
 *
 * @author Haixing Hu
 */
public class LocaleUtils {

  /**
   * Converts a POSIX locale ID into the corresponding Java {@link Locale}
   * object.
   *
   * <p>The POSIX locale ID is a string of the following format:
   * <pre><code>
   *  locale_id ::= language ("_" country)? ("_" variant)?
   * </code></pre>
   *
   * <p>where
   * <ul>
   * <li>{@code language} is a 2-letter ISO-639 codes, in lower case.</li>
   * <li>{@code country} is a 2-letter ISO-3166 codes, in upper case.</li>
   * <li>{@code variant} specify particular variants of the locale,
   * typically with special options. </li>
   * </ul>
   *
   * @param localeId
   *     a POSIX locale ID.
   * @return the corresponding Java {@link Locale} object; returns null if the
   *     POSIX locale ID is invalid or the locale is not supported.
   */
  public static Locale fromPosixLocale(@Nullable final String localeId) {
    if ((localeId == null) || (localeId.length() == 0)) {
      return null;
    }
    final String language;
    String country = StringUtils.EMPTY;
    String variant = StringUtils.EMPTY;
    final int n = localeId.length();
    int index = localeId.indexOf('_');
    if (index < 0) {
      // the whole locale ID is the language code
      if (n != 2) {   // invalid length of the language code
        return null;
      } else {
        return new Locale(localeId);
      }
    } else if (index != 2) {
      // invalid length of the language code
      return null;
    }
    // extract 2-letter language code
    language = localeId.substring(0, 2);
    // locate the next '_'
    final int start = index + 1;
    index = localeId.indexOf('_', start);
    if (index < 0) {
      // now the localeId[start, n) is either the 2-letter country code, or a variant.
      if ((start + 2) == n) {
        // it's a 2-letter country code
        country = localeId.substring(start, n);
      } else {
        // it's a variant
        variant = localeId.substring(start, n);
      }
    } else {
      // now the localeId[start, current) must be the 2-letter country code, and
      // localeId[current + 1, n) must be the variant
      if ((start + 2) != index) {
        // invalid length  of the country code
        return null;
      }
      country = localeId.substring(start, index);
      variant = localeId.substring(index + 1, n);
    }
    return new Locale(language, country, variant);
  }

  /**
   * Convert a Java {@link Locale} object into the corresponding POSIX locale
   * ID.
   *
   * <p>The POSIX locale ID is a string of the following format:
   * <pre><code>
   *  locale_id      ::= "C" | base_locale_id options?
   *  base_locale_id ::= language ("_" script)? ("_" territory)? ("_" variant)? ("." encoding)?
   *  options        ::= "@" key "=" value ("," key "=" value )*
   * </code></pre>
   *
   * <p>where
   * <ul>
   * <li>the string "C" is used to represent a unspecified locale id. It acts as
   * the current default locale of the current process.</li>
   * <li>{@code language} is a 2-letter ISO-639 codes, in lower case</li>
   * <li>{@code script} is a 4-letter ISO-15924 codes, in title case.
   * <li>{@code script} is a 2-letter ISO-3166 codes, in upper case. Also
   * known as a country code, although the territories may not be countries.</li>
   * <li>{@code variant} specify particular variants of the locale,
   * typically with special options. They cannot overlap with script or
   * territory codes, so they must have either one letter or have more than 4
   * letters.</li>
   * <li>{@code encoding} is the text encoding for ANSI applications.</li>
   * <li>{@code key}, {@code value} pair is the user predefined
   * configuration items.</li>
   * </ul>
   *
   * <p>Since the Java {@link Locale} object only support the language, country,
   * and variant fields, the other fields will be ignored during conversion.
   *
   * @param locale
   *     a Java {@link Locale} object. If it is null, a null string is
   *     returned.
   * @return the corresponding POSIX locale ID; or null if the locale is null.
   */
  public static String toPosixLocale(@Nullable final Locale locale) {
    if (locale == null) {
      return null;
    }
    String language = locale.getLanguage();
    if ((language == null) || (language.length() == 0)) {
      language = locale.getISO3Language();
    }
    String country = locale.getCountry();
    if ((country == null) || (country.length() == 0)) {
      country = locale.getISO3Country();
    }
    final String variant = locale.getVariant();
    final StringBuilder builder = new StringBuilder();
    // build the POSIX local ID
    if ((language != null) && (language.length() > 0)) {
      builder.append(language);
    }
    if ((country != null) && (country.length() > 0)) {
      if (builder.length() > 0) {
        builder.append('_');
      }
      builder.append(country);
    }
    if ((variant != null) && (variant.length() > 0)) {
      if (builder.length() > 0) {
        builder.append('_');
      }
      builder.append(variant);
    }
    return builder.toString();
  }
}
