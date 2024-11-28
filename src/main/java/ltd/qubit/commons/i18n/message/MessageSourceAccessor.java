////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.message;

import java.util.Locale;

import javax.annotation.Nullable;

import ltd.qubit.commons.error.NoSuchMessageException;
import ltd.qubit.commons.i18n.LocaleContextHolder;

/**
 * Helper class for easy access to messages from a MessageSource, providing
 * various overloaded getMessage methods.
 * <p>
 * Available from ApplicationObjectSupport, but also reusable as a standalone
 * helper to delegate to in application objects.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 */
public class MessageSourceAccessor {

  private final MessageSource messageSource;

  @Nullable
  private final Locale defaultLocale;

  /**
   * Create a new MessageSourceAccessor, using LocaleContextHolder's locale as
   * default locale.
   *
   * @param messageSource
   *     the MessageSource to wrap
   * @see LocaleContextHolder#getLocale()
   */
  public MessageSourceAccessor(final MessageSource messageSource) {
    this.messageSource = messageSource;
    this.defaultLocale = null;
  }

  /**
   * Create a new MessageSourceAccessor, using the given default locale.
   *
   * @param messageSource
   *     the MessageSource to wrap
   * @param defaultLocale
   *     the default locale to use for message access
   */
  public MessageSourceAccessor(final MessageSource messageSource,
      @Nullable final Locale defaultLocale) {
    this.messageSource = messageSource;
    this.defaultLocale = defaultLocale;
  }


  /**
   * Return the default locale to use if no explicit locale has been given.
   * <p>The default implementation returns the default locale passed into the
   * corresponding constructor, or LocaleContextHolder's locale as fallback. Can
   * be overridden in subclasses.
   *
   * @see #MessageSourceAccessor(MessageSource, java.util.Locale)
   * @see LocaleContextHolder#getLocale()
   */
  protected Locale getDefaultLocale() {
    return (this.defaultLocale != null ? this.defaultLocale : LocaleContextHolder.getLocale());
  }

  /**
   * Retrieve the message for the given code and the default Locale.
   *
   * @param code
   *     the code of the message
   * @param defaultMessage
   *     the String to return if the lookup fails
   * @return the message
   */
  public String getMessage(final String code, final String defaultMessage) {
    final String msg = this.messageSource.getMessage(code, null,
        defaultMessage, getDefaultLocale());
    return (msg != null ? msg : "");
  }

  /**
   * Retrieve the message for the given code and the given Locale.
   *
   * @param code
   *     the code of the message
   * @param defaultMessage
   *     the String to return if the lookup fails
   * @param locale
   *     the Locale in which to do lookup
   * @return the message
   */
  public String getMessage(final String code, final String defaultMessage, final Locale locale) {
    final String msg = this.messageSource.getMessage(code, null, defaultMessage, locale);
    return (msg != null ? msg : "");
  }

  /**
   * Retrieve the message for the given code and the default Locale.
   *
   * @param code
   *     the code of the message
   * @param args
   *     arguments for the message, or {@code null} if none
   * @param defaultMessage
   *     the String to return if the lookup fails
   * @return the message
   */
  public String getMessage(final String code, @Nullable final Object[] args,
      final String defaultMessage) {
    final String msg = this.messageSource.getMessage(code, args,
        defaultMessage, getDefaultLocale());
    return (msg != null ? msg : "");
  }

  /**
   * Retrieve the message for the given code and the given Locale.
   *
   * @param code
   *     the code of the message
   * @param args
   *     arguments for the message, or {@code null} if none
   * @param defaultMessage
   *     the String to return if the lookup fails
   * @param locale
   *     the Locale in which to do lookup
   * @return the message
   */
  public String getMessage(final String code, @Nullable final Object[] args,
      final String defaultMessage, final Locale locale) {
    final String msg = this.messageSource.getMessage(code, args,
        defaultMessage, locale);
    return (msg != null ? msg : "");
  }

  /**
   * Retrieve the message for the given code and the default Locale.
   *
   * @param code
   *     the code of the message
   * @return the message
   * @throws NoSuchMessageException
   *     if not found
   */
  public String getMessage(final String code) throws NoSuchMessageException {
    return this.messageSource.getMessage(code, null, getDefaultLocale());
  }

  /**
   * Retrieve the message for the given code and the given Locale.
   *
   * @param code
   *     the code of the message
   * @param locale
   *     the Locale in which to do lookup
   * @return the message
   * @throws NoSuchMessageException
   *     if not found
   */
  public String getMessage(final String code, final Locale locale)
      throws NoSuchMessageException {
    return this.messageSource.getMessage(code, null, locale);
  }

  /**
   * Retrieve the message for the given code and the default Locale.
   *
   * @param code
   *     the code of the message
   * @param args
   *     arguments for the message, or {@code null} if none
   * @return the message
   * @throws NoSuchMessageException
   *     if not found
   */
  public String getMessage(final String code, @Nullable final Object[] args)
      throws NoSuchMessageException {
    return this.messageSource.getMessage(code, args, getDefaultLocale());
  }

  /**
   * Retrieve the message for the given code and the given Locale.
   *
   * @param code
   *     the code of the message
   * @param args
   *     arguments for the message, or {@code null} if none
   * @param locale
   *     the Locale in which to do lookup
   * @return the message
   * @throws NoSuchMessageException
   *     if not found
   */
  public String getMessage(final String code, @Nullable final Object[] args,
      final Locale locale) throws NoSuchMessageException {
    return this.messageSource.getMessage(code, args, locale);
  }

  /**
   * Retrieve the given MessageSourceResolvable (e.g. an ObjectError instance)
   * in the default Locale.
   *
   * @param resolvable
   *     the MessageSourceResolvable
   * @return the message
   * @throws NoSuchMessageException
   *     if not found
   */
  public String getMessage(final MessageSourceResolvable resolvable)
      throws NoSuchMessageException {
    return this.messageSource.getMessage(resolvable, getDefaultLocale());
  }

  /**
   * Retrieve the given MessageSourceResolvable (e.g. an ObjectError instance)
   * in the given Locale.
   *
   * @param resolvable
   *     the MessageSourceResolvable
   * @param locale
   *     the Locale in which to do lookup
   * @return the message
   * @throws NoSuchMessageException
   *     if not found
   */
  public String getMessage(final MessageSourceResolvable resolvable, final Locale locale)
      throws NoSuchMessageException {
    return this.messageSource.getMessage(resolvable, locale);
  }

}
