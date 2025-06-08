////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n;

import java.util.TimeZone;

import javax.annotation.Nullable;

/**
 * {@link LocaleContext} 的扩展，增加了对当前时区的感知。
 * <p>
 * 将此 LocaleContext 变体设置为 {@link LocaleContextHolder} 意味着已配置了一些时区感知的基础设施，
 * 即使它目前可能无法产生非空的 TimeZone。
 * <p>
 * 此类是 {@code org.springframework.context.i18n.TimeZoneAwareLocaleContext} 的副本，
 * 稍作修改。它用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author Nicholas Williams
 * @author 胡海星
 * @see LocaleContextHolder#getTimeZone()
 */
public interface TimeZoneAwareLocaleContext extends LocaleContext {

  /**
   * 返回当前的 TimeZone，可以是固定的或动态确定的，具体取决于实现策略。
   *
   * @return 当前的 TimeZone，如果没有关联特定的 TimeZone 则返回 {@code null}
   */
  @Nullable
  TimeZone getTimeZone();
}