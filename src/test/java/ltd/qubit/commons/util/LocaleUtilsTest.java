////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.i18n.LocaleUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@link LocaleUtils} class.
 *
 * @author Haixing Hu
 */
public class LocaleUtilsTest {

  @Test
  public void testFromPosixLocale() {
    Locale actual = null;
    Locale expected = null;

    actual = LocaleUtils.fromPosixLocale(null);
    assertEquals(null, actual);

    actual = LocaleUtils.fromPosixLocale("");
    assertEquals(null, actual);

    actual = LocaleUtils.fromPosixLocale("XXXXX");
    assertEquals(null, actual);

    actual = LocaleUtils.fromPosixLocale("zh_abc");
    expected = new Locale("zh", "", "abc");
    assertEquals(expected, actual);

    actual = LocaleUtils.fromPosixLocale("zh_CN");
    expected = new Locale("zh", "CN");
    assertEquals(expected, actual);

    actual = LocaleUtils.fromPosixLocale("en_us");
    expected = new Locale("en", "us");
    assertEquals(expected, actual);

    actual = LocaleUtils.fromPosixLocale("zh_CN_variant");
    expected = new Locale("zh", "CN", "variant");
    assertEquals(expected, actual);

    actual = LocaleUtils.fromPosixLocale("zh_variant");
    expected = new Locale("zh", "", "variant");
    assertEquals(expected, actual);

    actual = LocaleUtils.fromPosixLocale("zh");
    expected = new Locale("zh");
    assertEquals(expected, actual);
  }

  public void testToPosixLocale() {
    Locale locale = null;

    assertEquals(null, LocaleUtils.toPosixLocale(null));

    locale = new Locale("");
    assertEquals("", LocaleUtils.toPosixLocale(locale));

    locale = new Locale("zh");
    assertEquals("zh", LocaleUtils.toPosixLocale(locale));

    locale = new Locale("zh", "cn");
    assertEquals("zh_CN", LocaleUtils.toPosixLocale(locale));

    locale = new Locale("zh", "", "var");
    assertEquals("zh_var", LocaleUtils.toPosixLocale(locale));
  }
}
