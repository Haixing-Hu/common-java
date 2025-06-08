////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n;

import java.util.Locale;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.StringUtils;

/**
 * 提供处理区域设置的实用函数。
 *
 * @author 胡海星
 */
public class LocaleUtils {

  /**
   * 将 POSIX 区域设置 ID 转换为相应的 Java {@link Locale} 对象。
   *
   * <p>POSIX 区域设置 ID 是以下格式的字符串：
   * <pre><code>
   *  locale_id ::= language ("_" country)? ("_" variant)?
   * </code></pre>
   *
   * <p>其中
   * <ul>
   * <li>{@code language} 是 2 字母的 ISO-639 代码，小写。</li>
   * <li>{@code country} 是 2 字母的 ISO-3166 代码，大写。</li>
   * <li>{@code variant} 指定区域设置的特定变体，通常具有特殊选项。</li>
   * </ul>
   *
   * @param localeId
   *     POSIX 区域设置 ID。
   * @return 相应的 Java {@link Locale} 对象；如果 POSIX 区域设置 ID 无效或不支持该区域设置，则返回 null。
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
   * 将 Java {@link Locale} 对象转换为相应的 POSIX 区域设置 ID。
   *
   * <p>POSIX 区域设置 ID 是以下格式的字符串：
   * <pre><code>
   *  locale_id      ::= "C" | base_locale_id options?
   *  base_locale_id ::= language ("_" script)? ("_" territory)? ("_" variant)? ("." encoding)?
   *  options        ::= "@" key "=" value ("," key "=" value )*
   * </code></pre>
   *
   * <p>其中
   * <ul>
   * <li>字符串 "C" 用于表示未指定的区域设置 ID。它充当当前进程的当前默认区域设置。</li>
   * <li>{@code language} 是 2 字母的 ISO-639 代码，小写</li>
   * <li>{@code script} 是 4 字母的 ISO-15924 代码，标题大小写。
   * <li>{@code script} 是 2 字母的 ISO-3166 代码，大写。也称为国家代码，尽管领土可能不是国家。</li>
   * <li>{@code variant} 指定区域设置的特定变体，通常具有特殊选项。它们不能与脚本或领土代码重叠，
   * 因此它们必须有一个字母或超过 4 个字母。</li>
   * <li>{@code encoding} 是 ANSI 应用程序的文本编码。</li>
   * <li>{@code key}、{@code value} 对是用户预定义的配置项。</li>
   * </ul>
   *
   * <p>由于 Java {@link Locale} 对象仅支持语言、国家和变体字段，因此在转换期间将忽略其他字段。
   *
   * @param locale
   *     Java {@link Locale} 对象。如果为 null，则返回 null 字符串。
   * @return 相应的 POSIX 区域设置 ID；如果区域设置为 null，则返回 null。
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