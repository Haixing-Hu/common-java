////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n;

import java.util.TimeZone;

import javax.annotation.Nullable;

/**
 * Extension of {@link LocaleContext}, adding awareness of the current time
 * zone.
 * <p>
 * Having this variant of LocaleContext set to {@link LocaleContextHolder} means
 * that some TimeZone-aware infrastructure has been configured, even if it may
 * not be able to produce a non-null TimeZone at the moment.
 * <p>
 * This class is a copy of {@code org.springframework.context.i18n.TimeZoneAwareLocaleContext}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Nicholas Williams
 * @author Haixing Hu
 * @see LocaleContextHolder#getTimeZone()
 */
public interface TimeZoneAwareLocaleContext extends LocaleContext {

  /**
   * Return the current TimeZone, which can be fixed or determined dynamically,
   * depending on the implementation strategy.
   *
   * @return the current TimeZone, or {@code null} if no specific TimeZone
   *     associated
   */
  @Nullable
  TimeZone getTimeZone();
}
