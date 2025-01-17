////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Helper class that allows for accessing a Spring {@link MessageSource} as a
 * {@link java.util.ResourceBundle}. Used for example to expose a Spring
 * MessageSource to JSTL web views.
 * <p>
 * This class is a copy of {@code org.springframework.context.support.MessageSourceResourceBundle}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see MessageSource
 * @see java.util.ResourceBundle
 */
public class MessageSourceResourceBundle extends ResourceBundle {

  private final MessageSource messageSource;

  private final Locale locale;

  /**
   * Create a new MessageSourceResourceBundle for the given MessageSource and
   * Locale.
   *
   * @param source
   *     the MessageSource to retrieve messages from
   * @param locale
   *     the Locale to retrieve messages for
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
   * Create a new MessageSourceResourceBundle for the given MessageSource and
   * Locale.
   *
   * @param source
   *     the MessageSource to retrieve messages from
   * @param locale
   *     the Locale to retrieve messages for
   * @param parent
   *     the parent ResourceBundle to delegate to if no local message found
   */
  public MessageSourceResourceBundle(final MessageSource source,
      final Locale locale, final ResourceBundle parent) {
    this(source, locale);
    setParent(parent);
  }


  /**
   * This implementation resolves the code in the MessageSource. Returns
   * {@code null} if the message could not be resolved.
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
   * This implementation checks whether the target MessageSource can resolve a
   * message for the given key, translating {@code NoSuchMessageException}
   * accordingly. In contrast to ResourceBundle's default implementation in JDK
   * 1.6, this does not rely on the capability to enumerate message keys.
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
   * This implementation throws {@code UnsupportedOperationException}, as a
   * MessageSource does not allow for enumerating the defined message codes.
   */
  @Override
  public Enumeration<String> getKeys() {
    throw new UnsupportedOperationException("MessageSourceResourceBundle does not support enumerating its keys");
  }

  /**
   * This implementation exposes the specified Locale for introspection through
   * the standard {@code ResourceBundle.getLocale()} method.
   */
  @Override
  public Locale getLocale() {
    return this.locale;
  }
}
