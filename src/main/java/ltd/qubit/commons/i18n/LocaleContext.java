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
 * Strategy interface for determining the current Locale.
 * <p>
 * A LocaleContext instance can be associated with a thread
 * via the LocaleContextHolder class.
 * <p>
 * This class is a copy of {@code org.springframework.context.i18n.LocaleContext}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see LocaleContextHolder#getLocale()
 * @see TimeZoneAwareLocaleContext
 */
public interface LocaleContext {

  /**
   * Return the current Locale, which can be fixed or determined dynamically,
   * depending on the implementation strategy.
   *
   * @return the current Locale, or {@code null} if no specific Locale
   *     associated
   */
  @Nullable
  Locale getLocale();
}
