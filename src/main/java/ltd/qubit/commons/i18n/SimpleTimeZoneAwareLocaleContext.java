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
import java.util.TimeZone;

import javax.annotation.Nullable;

/**
 * {@link TimeZoneAwareLocaleContext} 接口的简单实现，
 * 始终返回指定的 {@code Locale} 和 {@code TimeZone}。
 * <p>
 * 注意：当只设置 Locale 而不设置 TimeZone 时，优先使用 {@link SimpleLocaleContext}。
 *
 * @author Juergen Hoeller
 * @author Nicholas Williams
 * @author 胡海星
 * @see LocaleContextHolder#setLocaleContext
 * @see LocaleContextHolder#getTimeZone()
 */
public class SimpleTimeZoneAwareLocaleContext extends SimpleLocaleContext
    implements TimeZoneAwareLocaleContext {

  @Nullable
  private final TimeZone timeZone;

  /**
   * 创建一个新的 SimpleTimeZoneAwareLocaleContext，暴露指定的 Locale 和 TimeZone。
   * 每次 {@link #getLocale()} 调用都将返回给定的 Locale，
   * 每次 {@link #getTimeZone()} 调用都将返回给定的 TimeZone。
   *
   * @param locale
   *     要暴露的 Locale
   * @param timeZone
   *     要暴露的 TimeZone
   */
  public SimpleTimeZoneAwareLocaleContext(@Nullable final Locale locale,
      @Nullable final TimeZone timeZone) {
    super(locale);
    this.timeZone = timeZone;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Nullable
  public TimeZone getTimeZone() {
    return this.timeZone;
  }

  @Override
  public String toString() {
    return super.toString() + " "
        + (this.timeZone != null ? this.timeZone : "-");
  }
}