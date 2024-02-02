////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n;

import java.util.Locale;

import javax.annotation.Nullable;

/**
 * Simple implementation of the {@link LocaleContext} interface,
 * always returning a specified {@code Locale}.
 * <p>
 * This class is a copy of {@code org.springframework.context.i18n.SimpleLocaleContext}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see LocaleContextHolder#setLocaleContext
 * @see LocaleContextHolder#getLocale()
 * @see SimpleTimeZoneAwareLocaleContext
 */
public class SimpleLocaleContext implements LocaleContext {

  @Nullable
  private final Locale locale;

  /**
   * Create a new {@code SimpleLocaleContext} that exposes the specified {@link Locale}.
   * <p>Every {@link #getLocale()} call will return this locale.
   * @param locale the {@code Locale} to expose, or {@code null} for no specific one
   */
  public SimpleLocaleContext(@Nullable final Locale locale) {
    this.locale = locale;
  }

  @Override
  @Nullable
  public Locale getLocale() {
    return this.locale;
  }

  @Override
  public String toString() {
    return (this.locale != null ? this.locale.toString() : "-");
  }
}
