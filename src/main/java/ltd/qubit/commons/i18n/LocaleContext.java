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
 * 用于确定当前 Locale 的策略接口。
 * <p>
 * LocaleContext 实例可以通过 LocaleContextHolder 类与线程关联。
 * <p>
 * 此类是 {@code org.springframework.context.i18n.LocaleContext} 的副本，
 * 稍作修改。它用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 * @see LocaleContextHolder#getLocale()
 * @see TimeZoneAwareLocaleContext
 */
public interface LocaleContext {

  /**
   * 返回当前的 Locale，可以是固定的或动态确定的，具体取决于实现策略。
   *
   * @return 当前的 Locale，如果没有关联特定的 Locale 则返回 {@code null}
   */
  @Nullable
  Locale getLocale();
}