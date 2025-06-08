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

import ltd.qubit.commons.concurrent.NamedInheritableThreadLocal;
import ltd.qubit.commons.concurrent.NamedThreadLocal;
import ltd.qubit.commons.i18n.message.MessageSourceAccessor;

/**
 * 简单的持有者类，将 LocaleContext 实例与当前线程关联。如果 {@code inheritable} 标志设置为 {@code true}，
 * LocaleContext 将被当前线程生成的任何子线程继承。
 * <p>
 * 在 Spring 中用作当前 Locale 的中央持有者，在需要的地方：例如，在 MessageSourceAccessor 中。
 * DispatcherServlet 自动将其当前 Locale 暴露在这里。其他应用程序也可以暴露它们的 Locale，
 * 以使 MessageSourceAccessor 等类自动使用该 Locale。
 * <p>
 * 此类是 {@code org.springframework.context.i18n.LocaleContextHolder} 的副本，
 * 稍作修改。它用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author Nicholas Williams
 * @author 胡海星
 * @see LocaleContext
 * @see MessageSourceAccessor
 */
public final class LocaleContextHolder {

  private static final ThreadLocal<LocaleContext> localeContextHolder =
      new NamedThreadLocal<>("LocaleContext");

  private static final ThreadLocal<LocaleContext> inheritableLocaleContextHolder =
      new NamedInheritableThreadLocal<>("LocaleContext");

  /**
   * 共享的默认区域设置。
   */
  @Nullable
  private static Locale defaultLocale = null;

  /**
   * 共享的默认时区。
   */
  @Nullable
  private static TimeZone defaultTimeZone = null;

  /**
   * 构造一个表示 LocaleContextHolder 的类。
   */
  private LocaleContextHolder() {}

  /**
   * 重置当前线程的 LocaleContext。
   */
  public static void resetLocaleContext() {
    localeContextHolder.remove();
    inheritableLocaleContextHolder.remove();
  }

  /**
   * 将给定的 LocaleContext 与当前线程关联，<i>不</i>将其作为子线程的可继承属性暴露。
   * <p>
   * 给定的 LocaleContext 可能是一个 {@link TimeZoneAwareLocaleContext}，
   * 包含具有关联时区信息的区域设置。
   *
   * @param localeContext
   *     当前的 LocaleContext，或 {@code null} 以重置线程绑定的上下文
   * @see SimpleLocaleContext
   * @see SimpleTimeZoneAwareLocaleContext
   */
  public static void setLocaleContext(
      @Nullable final LocaleContext localeContext) {
    setLocaleContext(localeContext, false);
  }

  /**
   * 将给定的 LocaleContext 与当前线程关联。
   * <p>
   * 给定的 LocaleContext 可能是一个 {@link TimeZoneAwareLocaleContext}，
   * 包含具有关联时区信息的区域设置。
   *
   * @param localeContext
   *     当前的 LocaleContext，或 {@code null} 以重置线程绑定的上下文
   * @param inheritable
   *     是否将 LocaleContext 暴露为子线程的可继承属性
   *     （使用 {@link InheritableThreadLocal}）
   * @see SimpleLocaleContext
   * @see SimpleTimeZoneAwareLocaleContext
   */
  public static void setLocaleContext(@Nullable final LocaleContext localeContext,
      final boolean inheritable) {
    if (localeContext == null) {
      resetLocaleContext();
    } else {
      if (inheritable) {
        inheritableLocaleContextHolder.set(localeContext);
        localeContextHolder.remove();
      } else {
        localeContextHolder.set(localeContext);
        inheritableLocaleContextHolder.remove();
      }
    }
  }

  /**
   * 返回与当前线程关联的 LocaleContext（如果有）。
   *
   * @return 当前的 LocaleContext，如果没有则返回 {@code null}
   */
  @Nullable
  public static LocaleContext getLocaleContext() {
    LocaleContext localeContext = localeContextHolder.get();
    if (localeContext == null) {
      localeContext = inheritableLocaleContextHolder.get();
    }
    return localeContext;
  }

  /**
   * 将给定的 Locale 与当前线程关联，保留可能已设置的任何 TimeZone。
   * <p>
   * 将为给定的 Locale 隐式创建一个 LocaleContext，
   * <i>不</i>将其作为子线程的可继承属性暴露。
   *
   * @param locale
   *     当前的 Locale，或 {@code null} 以重置线程绑定上下文的区域设置部分
   * @see #setTimeZone(TimeZone)
   * @see SimpleLocaleContext#SimpleLocaleContext(Locale)
   */
  public static void setLocale(@Nullable final Locale locale) {
    setLocale(locale, false);
  }

  /**
   * 将给定的 Locale 与当前线程关联，保留可能已设置的任何 TimeZone。
   * <p>
   * 将为给定的 Locale 隐式创建一个 LocaleContext。
   *
   * @param locale
   *     当前的 Locale，或 {@code null} 以重置线程绑定上下文的区域设置部分
   * @param inheritable
   *     是否将 LocaleContext 暴露为子线程的可继承属性
   *     （使用 {@link InheritableThreadLocal}）
   * @see #setTimeZone(TimeZone, boolean)
   * @see SimpleLocaleContext#SimpleLocaleContext(Locale)
   */
  public static void setLocale(@Nullable final Locale locale,
      final boolean inheritable) {
    LocaleContext localeContext = getLocaleContext();
    final TimeZone timeZone;
    if (localeContext instanceof TimeZoneAwareLocaleContext) {
      final TimeZoneAwareLocaleContext timeZoneAware = (TimeZoneAwareLocaleContext) localeContext;
      timeZone = timeZoneAware.getTimeZone();
    } else {
      timeZone = null;
    }
    if (timeZone != null) {
      localeContext = new SimpleTimeZoneAwareLocaleContext(locale, timeZone);
    } else if (locale != null) {
      localeContext = new SimpleLocaleContext(locale);
    } else {
      localeContext = null;
    }
    setLocaleContext(localeContext, inheritable);
  }

  /**
   * 在框架级别设置共享的默认区域设置，作为 JVM 范围默认区域设置的替代方案。
   * <p>
   * <b>注意：</b>这对于设置与 JVM 范围默认区域设置不同的应用程序级别
   * 默认区域设置很有用。但是，这要求每个此类应用程序都针对本地部署的
   * Spring Framework jar 进行操作。在这种情况下，不要在服务器级别
   * 将 Spring 部署为共享库！
   *
   * @param locale
   *     默认区域设置（或 {@code null} 表示无，让查找回退到
   *     {@link Locale#getDefault()}）
   * @see #getLocale()
   * @see Locale#getDefault()
   * @since 4.3.5
   */
  public static void setDefaultLocale(@Nullable final Locale locale) {
    defaultLocale = locale;
  }

  /**
   * 返回与当前线程关联的 Locale（如果有），否则返回系统默认 Locale。
   * 这实际上是 {@link java.util.Locale#getDefault()} 的替代方案，
   * 能够可选地尊重用户级别的 Locale 设置。
   * <p>
   * 注意：此方法具有对共享默认 Locale 的回退，无论是在框架级别还是在
   * JVM 范围系统级别。如果您想检查原始 LocaleContext 内容
   *（可能通过 {@code null} 指示没有特定区域设置），
   * 请使用 {@link #getLocaleContext()} 并调用
   * {@link LocaleContext#getLocale()}
   *
   * @return 当前的 Locale，如果没有特定的 Locale 与当前线程关联，
   *     则返回系统默认 Locale
   * @see #getLocaleContext()
   * @see LocaleContext#getLocale()
   * @see #setDefaultLocale(Locale)
   * @see java.util.Locale#getDefault()
   */
  public static Locale getLocale() {
    return getLocale(getLocaleContext());
  }

  /**
   * 返回与给定用户上下文关联的 Locale（如果有），否则返回系统默认 Locale。
   * 这实际上是 {@link java.util.Locale#getDefault()} 的替代方案，
   * 能够可选地尊重用户级别的 Locale 设置。
   *
   * @param localeContext
   *     要检查的用户级别区域设置上下文
   * @return 当前的 Locale，如果没有特定的 Locale 与当前线程关联，
   *     则返回系统默认 Locale
   * @see #getLocale()
   * @see LocaleContext#getLocale()
   * @see #setDefaultLocale(Locale)
   * @see java.util.Locale#getDefault()
   * @since 5.0
   */
  public static Locale getLocale(@Nullable final LocaleContext localeContext) {
    if (localeContext != null) {
      final Locale locale = localeContext.getLocale();
      if (locale != null) {
        return locale;
      }
    }
    return (defaultLocale != null ? defaultLocale : Locale.getDefault());
  }

  /**
   * 将给定的 TimeZone 与当前线程关联，保留可能已设置的任何 Locale。
   * <p>
   * 将为给定的 Locale 隐式创建一个 LocaleContext，
   * <i>不</i>将其作为子线程的可继承属性暴露。
   *
   * @param timeZone
   *     当前的 TimeZone，或 {@code null} 以重置线程绑定上下文的时区部分
   * @see #setLocale(Locale)
   * @see SimpleTimeZoneAwareLocaleContext#SimpleTimeZoneAwareLocaleContext(Locale,TimeZone)
   */
  public static void setTimeZone(@Nullable final TimeZone timeZone) {
    setTimeZone(timeZone, false);
  }

  /**
   * 将给定的 TimeZone 与当前线程关联，保留可能已设置的任何 Locale。
   * <p>
   * 将为给定的 Locale 隐式创建一个 LocaleContext。
   *
   * @param timeZone
   *     当前的 TimeZone，或 {@code null} 以重置线程绑定上下文的时区部分
   * @param inheritable
   *     是否将 LocaleContext 暴露为子线程的可继承属性
   *     （使用 {@link InheritableThreadLocal}）
   * @see #setLocale(Locale, boolean)
   * @see SimpleTimeZoneAwareLocaleContext#SimpleTimeZoneAwareLocaleContext(Locale, TimeZone)
   */
  public static void setTimeZone(@Nullable final TimeZone timeZone, final boolean inheritable) {
    LocaleContext localeContext = getLocaleContext();
    final Locale locale = (localeContext != null ? localeContext.getLocale() : null);
    if (timeZone != null) {
      localeContext = new SimpleTimeZoneAwareLocaleContext(locale, timeZone);
    }
    else if (locale != null) {
      localeContext = new SimpleLocaleContext(locale);
    }
    else {
      localeContext = null;
    }
    setLocaleContext(localeContext, inheritable);
  }

  /**
   * 在框架级别设置共享的默认时区，作为 JVM 范围默认时区的替代方案。
   * <p>
   * <b>注意：</b>这对于设置与 JVM 范围默认时区不同的应用程序级别
   * 默认时区很有用。但是，这要求每个此类应用程序都针对本地部署的
   * Spring Framework jar 进行操作。在这种情况下，不要在服务器级别
   * 将 Spring 部署为共享库！
   *
   * @param timeZone
   *     默认时区（或 {@code null} 表示无，让查找回退到
   *     {@link TimeZone#getDefault()}）
   * @see #getTimeZone()
   * @see TimeZone#getDefault()
   * @since 4.3.5
   */
  public static void setDefaultTimeZone(@Nullable final TimeZone timeZone) {
    defaultTimeZone = timeZone;
  }

  /**
   * 返回与当前线程关联的 TimeZone（如果有），否则返回系统默认 TimeZone。
   * 这实际上是 {@link java.util.TimeZone#getDefault()} 的替代方案，
   * 能够可选地尊重用户级别的 TimeZone 设置。
   * <p>
   * 注意：此方法具有对共享默认 TimeZone 的回退，无论是在框架级别还是在
   * JVM 范围系统级别。如果您想检查原始 LocaleContext 内容
   *（可能通过 {@code null} 指示没有特定时区），
   * 请使用 {@link #getLocaleContext()} 并在向下转型为
   * {@link TimeZoneAwareLocaleContext} 后调用
   * {@link TimeZoneAwareLocaleContext#getTimeZone()}。
   *
   * @return 当前的 TimeZone，如果没有特定的 TimeZone 与当前线程关联，
   *     则返回系统默认 TimeZone
   * @see #getLocaleContext()
   * @see TimeZoneAwareLocaleContext#getTimeZone()
   * @see #setDefaultTimeZone(TimeZone)
   * @see java.util.TimeZone#getDefault()
   */
  public static TimeZone getTimeZone() {
    return getTimeZone(getLocaleContext());
  }

  /**
   * 返回与给定用户上下文关联的 TimeZone（如果有），否则返回系统默认 TimeZone。
   * 这实际上是 {@link java.util.TimeZone#getDefault()} 的替代方案，
   * 能够可选地尊重用户级别的 TimeZone 设置。
   *
   * @param localeContext
   *     要检查的用户级别区域设置上下文
   * @return 当前的 TimeZone，如果没有特定的 TimeZone 与当前线程关联，
   *     则返回系统默认 TimeZone
   * @see #getTimeZone()
   * @see TimeZoneAwareLocaleContext#getTimeZone()
   * @see #setDefaultTimeZone(TimeZone)
   * @see java.util.TimeZone#getDefault()
   * @since 5.0
   */
  public static TimeZone getTimeZone(@Nullable final LocaleContext localeContext) {
    if (localeContext instanceof TimeZoneAwareLocaleContext) {
      final TimeZoneAwareLocaleContext timeZoneAware = (TimeZoneAwareLocaleContext) localeContext;
      final TimeZone timeZone = timeZoneAware.getTimeZone();
      if (timeZone != null) {
        return timeZone;
      }
    }
    return (defaultTimeZone != null ? defaultTimeZone : TimeZone.getDefault());
  }
}
