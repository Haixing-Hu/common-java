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

/**
 * {@link LocaleContext} 接口的简单实现，始终返回指定的 {@code Locale}。
 * <p>
 * 此类是 {@code org.springframework.context.i18n.SimpleLocaleContext} 的副本，
 * 稍作修改。它用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 * @see LocaleContextHolder#setLocaleContext
 * @see LocaleContextHolder#getLocale()
 * @see SimpleTimeZoneAwareLocaleContext
 */
public class SimpleLocaleContext implements LocaleContext {

  @Nullable
  private final Locale locale;

  /**
   * 创建一个新的 {@code SimpleLocaleContext}，暴露指定的 {@link Locale}。
   * <p>每次 {@link #getLocale()} 调用都将返回此区域设置。
   * @param locale 要暴露的 {@code Locale}，或 {@code null} 表示没有特定的区域设置
   */
  public SimpleLocaleContext(@Nullable final Locale locale) {
    this.locale = locale;
  }

  /**
   * {@inheritDoc}
   */
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