////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.bundle;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Nullable;

import ltd.qubit.commons.error.NoSuchMessageException;
import ltd.qubit.commons.i18n.message.MessageSource;

/**
 * 辅助类，允许将 Spring {@link MessageSource} 作为 {@link java.util.ResourceBundle} 访问。
 * 例如，用于将 Spring MessageSource 公开给 JSTL 网页视图。
 * <p>
 * 此类是 {@code org.springframework.context.support.MessageSourceResourceBundle} 的副本，
 * 稍作修改。它用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 * @see MessageSource
 * @see java.util.ResourceBundle
 */
public class MessageSourceResourceBundle extends ResourceBundle {

  private final MessageSource messageSource;

  private final Locale locale;

  /**
   * 为给定的 MessageSource 和 Locale 创建新的 MessageSourceResourceBundle。
   *
   * @param source
   *     用于检索消息的 MessageSource
   * @param locale
   *     用于检索消息的 Locale
   */
  public MessageSourceResourceBundle(final MessageSource source,
      final Locale locale) {
    if (source == null) {
      throw new IllegalArgumentException("MessageSource must not be null");
    }
    this.messageSource = source;
    this.locale = locale;
  }

  /**
   * 为给定的 MessageSource 和 Locale 创建新的 MessageSourceResourceBundle。
   *
   * @param source
   *     用于检索消息的 MessageSource
   * @param locale
   *     用于检索消息的 Locale
   * @param parent
   *     如果未找到本地消息时委托给的父 ResourceBundle
   */
  public MessageSourceResourceBundle(final MessageSource source,
      final Locale locale, final ResourceBundle parent) {
    this(source, locale);
    setParent(parent);
  }


  /**
   * 此实现解析 MessageSource 中的代码。如果无法解析消息，则返回 {@code null}。
   */
  @Override
  @Nullable
  protected Object handleGetObject(final String key) {
    try {
      return this.messageSource.getMessage(key, null, this.locale);
    } catch (final NoSuchMessageException ex) {
      return null;
    }
  }

  /**
   * 此实现检查目标 MessageSource 是否可以解析给定键的消息，相应地转换 {@code NoSuchMessageException}。
   * 与 JDK 1.6 中 ResourceBundle 的默认实现不同，这不依赖于枚举消息键的能力。
   */
  @Override
  public boolean containsKey(final String key) {
    try {
      this.messageSource.getMessage(key, null, this.locale);
      return true;
    } catch (final NoSuchMessageException ex) {
      return false;
    }
  }

  /**
   * 此实现抛出 {@code UnsupportedOperationException}，因为 MessageSource 不允许枚举已定义的消息代码。
   */
  @Override
  public Enumeration<String> getKeys() {
    throw new UnsupportedOperationException("MessageSourceResourceBundle does not support enumerating its keys");
  }

  /**
   * 此实现通过标准的 {@code ResourceBundle.getLocale()} 方法公开指定的 Locale 以供反省。
   */
  @Override
  public Locale getLocale() {
    return this.locale;
  }
}