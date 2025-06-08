////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.message;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.Nullable;

import ltd.qubit.commons.error.NoSuchMessageException;
import ltd.qubit.commons.lang.ArrayUtils;

/**
 * {@link HierarchicalMessageSource} 接口的抽象实现，实现了消息变体的通用处理，
 * 使得为具体的 MessageSource 实现特定策略变得容易。
 *
 * <p>子类必须实现抽象的 {@link #resolveCode} 方法。为了高效解析没有参数的消息，
 * 也应该重写 {@link #resolveCodeWithoutArguments} 方法，在不涉及 MessageFormat 的情况下解析消息。
 *
 * <p><b>注意：</b> 默认情况下，只有在为消息传入参数时，消息文本才会通过 MessageFormat 解析。
 * 在没有参数的情况下，消息文本将按原样返回。因此，您应该只对具有实际参数的消息使用 MessageFormat 转义，
 * 并保持所有其他消息不转义。如果您希望转义所有消息，请将 "alwaysUseMessageFormat" 标志设置为 "true"。
 *
 * <p>不仅支持 {@link MessageSourceResolvable} 作为主要消息，还支持解析本身也是
 * {@link MessageSourceResolvable} 的消息参数。
 *
 * <p>此类不实现按代码缓存消息，因此子类可以随时间动态更改消息。鼓励子类以修改感知的方式缓存其消息，
 * 允许热部署更新的消息。
 * <p>
 * 此类是 {@code org.springframework.context.support.AbstractMessageSource} 的副本，
 * 稍作修改。它用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author 胡海星
 * @see #resolveCode(String, java.util.Locale)
 * @see #resolveCodeWithoutArguments(String, java.util.Locale)
 * @see #setAlwaysUseMessageFormat
 * @see java.text.MessageFormat
 */
public abstract class AbstractMessageSource extends MessageSourceSupport
    implements HierarchicalMessageSource {

  @Nullable
  private MessageSource parentMessageSource;

  @Nullable
  private Properties commonMessages;

  private boolean useCodeAsDefaultMessage = false;


  @Override
  public void setParentMessageSource(@Nullable final MessageSource parent) {
    this.parentMessageSource = parent;
  }

  @Override
  @Nullable
  public MessageSource getParentMessageSource() {
    return this.parentMessageSource;
  }

  /**
   * Specify locale-independent common messages, with the message code as key
   * and the full message String (may contain argument placeholders) as value.
   * <p>
   * May also link to an externally defined Properties object, e.g. defined
   * through a {@link org.springframework.beans.factory.config.PropertiesFactoryBean}.
   */
  public void setCommonMessages(@Nullable final Properties commonMessages) {
    this.commonMessages = commonMessages;
  }

  /**
   * Return a Properties object defining locale-independent common messages, if any.
   */
  @Nullable
  protected Properties getCommonMessages() {
    return this.commonMessages;
  }

  /**
   * Set whether to use the message code as default message instead of
   * throwing a NoSuchMessageException. Useful for development and debugging.
   * Default is "false".
   * <p>Note: In case of a MessageSourceResolvable with multiple codes
   * (like a FieldError) and a MessageSource that has a parent MessageSource,
   * do <i>not</i> activate "useCodeAsDefaultMessage" in the <i>parent</i>:
   * Else, you'll get the first code returned as message by the parent,
   * without attempts to check further codes.
   * <p>To be able to work with "useCodeAsDefaultMessage" turned on in the parent,
   * AbstractMessageSource and AbstractApplicationContext contain special checks
   * to delegate to the internal {@link #getMessageInternal} method if available.
   * In general, it is recommended to just use "useCodeAsDefaultMessage" during
   * development and not rely on it in production in the first place, though.
   * @see #getMessage(String, Object[], Locale)
   */
  public void setUseCodeAsDefaultMessage(final boolean useCodeAsDefaultMessage) {
    this.useCodeAsDefaultMessage = useCodeAsDefaultMessage;
  }

  /**
   * Return whether to use the message code as default message instead of
   * throwing a NoSuchMessageException. Useful for development and debugging.
   * Default is "false".
   * <p>Alternatively, consider overriding the {@link #getDefaultMessage}
   * method to return a custom fallback message for an unresolvable code.
   * @see #getDefaultMessage(String)
   */
  protected boolean isUseCodeAsDefaultMessage() {
    return this.useCodeAsDefaultMessage;
  }

  @Override
  public final String getMessage(final String code, @Nullable final Object[] args,
      @Nullable final String defaultMessage, final Locale locale) {
    final String msg = getMessageInternal(code, args, locale);
    if (msg != null) {
      return msg;
    }
    if (defaultMessage == null) {
      return getDefaultMessage(code);
    }
    return renderDefaultMessage(defaultMessage, args, locale);
  }

  @Override
  public final String getMessage(final String code, @Nullable final Object[] args,
      final Locale locale) throws NoSuchMessageException {
    final String msg = getMessageInternal(code, args, locale);
    if (msg != null) {
      return msg;
    }
    final String fallback = getDefaultMessage(code);
    if (fallback != null) {
      return fallback;
    }
    throw new NoSuchMessageException(code, locale);
  }

  @Override
  public final String getMessage(final MessageSourceResolvable resolvable,
      final Locale locale) throws NoSuchMessageException {
    final String[] codes = resolvable.getCodes();
    if (codes != null) {
      for (final String code : codes) {
        final String message = getMessageInternal(code, resolvable.getArguments(), locale);
        if (message != null) {
          return message;
        }
      }
    }
    final String defaultMessage = getDefaultMessage(resolvable, locale);
    if (defaultMessage != null) {
      return defaultMessage;
    }
    final String code = (ArrayUtils.isEmpty(codes) ?  "" : codes[codes.length - 1]);
    throw new NoSuchMessageException(code, locale);
  }

  /**
   * Resolve the given code and arguments as message in the given Locale,
   * returning {@code null} if not found. Does <i>not</i> fall back to the code
   * as default message. Invoked by {@code getMessage} methods.
   *
   * @param code
   *     the code to lookup up, such as 'calculator.noRateSet'
   * @param args
   *     array of arguments that will be filled in for params within the
   *     message
   * @param locale
   *     the locale in which to do the lookup
   * @return the resolved message, or {@code null} if not found
   * @see #getMessage(String, Object[], String, Locale)
   * @see #getMessage(String, Object[], Locale)
   * @see #getMessage(MessageSourceResolvable, Locale)
   * @see #setUseCodeAsDefaultMessage
   */
  @Nullable
  protected String getMessageInternal(@Nullable final String code,
      @Nullable final Object[] args, @Nullable Locale locale) {
    if (code == null) {
      return null;
    }
    if (locale == null) {
      locale = Locale.getDefault();
    }
    Object[] argsToUse = args;
    if (!isAlwaysUseMessageFormat() && ArrayUtils.isEmpty(args)) {
      // Optimized resolution: no arguments to apply,
      // therefore no MessageFormat needs to be involved.
      // Note that the default implementation still uses MessageFormat;
      // this can be overridden in specific subclasses.
      final String message = resolveCodeWithoutArguments(code, locale);
      if (message != null) {
        return message;
      }
    } else {
      // Resolve arguments eagerly, for the case where the message
      // is defined in a parent MessageSource but resolvable arguments
      // are defined in the child MessageSource.
      argsToUse = resolveArguments(args, locale);
      final MessageFormat messageFormat = resolveCode(code, locale);
      if (messageFormat != null) {
        synchronized (messageFormat) {
          return messageFormat.format(argsToUse);
        }
      }
    }
    // Check locale-independent common messages for the given message code.
    if (commonMessages != null) {
      final String commonMessage = commonMessages.getProperty(code);
      if (commonMessage != null) {
        return formatMessage(commonMessage, args, locale);
      }
    }
    // Not found -> check parent, if any.
    return getMessageFromParent(code, argsToUse, locale);
  }

  /**
   * Try to retrieve the given message from the parent {@code MessageSource}, if
   * any.
   *
   * @param code
   *     the code to lookup up, such as 'calculator.noRateSet'
   * @param args
   *     array of arguments that will be filled in for params within the
   *     message
   * @param locale
   *     the locale in which to do the lookup
   * @return the resolved message, or {@code null} if not found
   * @see #getParentMessageSource()
   */
  @Nullable
  protected String getMessageFromParent(final String code,
      @Nullable final Object[] args, final Locale locale) {
    final MessageSource parent = getParentMessageSource();
    if (parent != null) {
      if (parent instanceof AbstractMessageSource) {
        final AbstractMessageSource ams = (AbstractMessageSource) parent;
        // Call internal method to avoid getting the default code back
        // in case of "useCodeAsDefaultMessage" being activated.
        return ams.getMessageInternal(code, args, locale);
      } else {
        // Check parent MessageSource, returning null if not found there.
        // Covers custom MessageSource impls and DelegatingMessageSource.
        return parent.getMessage(code, args, null, locale);
      }
    }
    // Not found in parent either.
    return null;
  }

  /**
   * Get a default message for the given {@code MessageSourceResolvable}.
   * <p>
   * This implementation fully renders the default message if available,
   * or just returns the plain default message {@code String} if the primary
   * message code is being used as a default message.
   *
   * @param resolvable
   *     the value object to resolve a default message for
   * @param locale
   *     the current locale
   * @return the default message, or {@code null} if none
   * @see #renderDefaultMessage(String, Object[], Locale)
   * @see #getDefaultMessage(String)
   */
  @Nullable
  protected String getDefaultMessage(final MessageSourceResolvable resolvable,
      final Locale locale) {
    final String defaultMessage = resolvable.getDefaultMessage();
    final String[] codes = resolvable.getCodes();
    if (defaultMessage != null) {
      if (resolvable instanceof DefaultMessageSourceResolvable) {
        final DefaultMessageSourceResolvable rs = (DefaultMessageSourceResolvable) resolvable;
        if (!rs.shouldRenderDefaultMessage()) {
          // Given default message does not contain any argument placeholders
          // (and isn't escaped for alwaysUseMessageFormat either) -> return as-is.
          return defaultMessage;
        }
      }
      if (!ArrayUtils.isEmpty(codes) && defaultMessage.equals(codes[0])) {
        // Never format a code-as-default-message, even with alwaysUseMessageFormat=true
        return defaultMessage;
      }
      return renderDefaultMessage(defaultMessage, resolvable.getArguments(), locale);
    }
    return ArrayUtils.isEmpty(codes) ? null : getDefaultMessage(codes[0]);
  }

  /**
   * Return a fallback default message for the given code, if any.
   * <p>Default is to return the code itself if "useCodeAsDefaultMessage" is
   * activated,
   * or return no fallback else. In case of no fallback, the caller will usually
   * receive a {@code NoSuchMessageException} from {@code getMessage}.
   *
   * @param code
   *     the message code that we couldn't resolve and that we didn't receive an
   *     explicit default message for
   * @return the default message to use, or {@code null} if none
   * @see #setUseCodeAsDefaultMessage
   */
  @Nullable
  protected String getDefaultMessage(final String code) {
    if (isUseCodeAsDefaultMessage()) {
      return code;
    }
    return null;
  }


  /**
   * Searches through the given array of objects, finds any
   * MessageSourceResolvable objects and resolves them.
   * <p>
   * Allows for messages to have {@code MessageSourceResolvable}s as arguments.
   *
   * @param args
   *     array of arguments for a message
   * @param locale
   *     the locale to resolve through
   * @return an array of arguments with any {@code MessageSourceResolvable}s resolved
   */
  @Override
  protected Object[] resolveArguments(@Nullable final Object[] args, final Locale locale) {
    if (ArrayUtils.isEmpty(args)) {
      return super.resolveArguments(args, locale);
    }
    final List<Object> resolvedArgs = new ArrayList<>(args.length);
    for (final Object arg : args) {
      if (arg instanceof MessageSourceResolvable) {
        final MessageSourceResolvable msr = (MessageSourceResolvable) arg;
        resolvedArgs.add(getMessage(msr, locale));
      } else {
        resolvedArgs.add(arg);
      }
    }
    return resolvedArgs.toArray();
  }

  /**
   * Subclasses can override this method to resolve a message without arguments
   * in an optimized fashion, i.e. to resolve without involving a
   * MessageFormat.
   * <p>
   * The default implementation <i>does</i> use MessageFormat, through
   * delegating to the {@link #resolveCode} method. Subclasses are encouraged to
   * replace this with optimized resolution.
   * <p>
   * Unfortunately, {@code java.text.MessageFormat} is not implemented
   * in an efficient fashion. In particular, it does not detect that a message
   * pattern doesn't contain argument placeholders in the first place.
   * Therefore, it is advisable to circumvent MessageFormat for messages without
   * arguments.
   *
   * @param code
   *     the code of the message to resolve
   * @param locale
   *     the locale to resolve the code for (subclasses are encouraged to
   *     support internationalization)
   * @return the message String, or {@code null} if not found
   * @see #resolveCode
   * @see java.text.MessageFormat
   */
  @Nullable
  protected String resolveCodeWithoutArguments(final String code,
      final Locale locale) {
    final MessageFormat messageFormat = resolveCode(code, locale);
    if (messageFormat != null) {
      synchronized (messageFormat) {
        return messageFormat.format(new Object[0]);
      }
    }
    return null;
  }

  /**
   * Subclasses must implement this method to resolve a message.
   * <p>Returns a MessageFormat instance rather than a message String,
   * to allow for appropriate caching of MessageFormats in subclasses.
   * <p>
   * <b>Subclasses are encouraged to provide optimized resolution
   * for messages without arguments, not involving MessageFormat.</b>
   * See the {@link #resolveCodeWithoutArguments} javadoc for details.
   *
   * @param code
   *     the code of the message to resolve
   * @param locale
   *     the locale to resolve the code for (subclasses are encouraged to
   *     support internationalization)
   * @return the MessageFormat for the message, or {@code null} if not found
   * @see #resolveCodeWithoutArguments(String, java.util.Locale)
   */
  @Nullable
  protected abstract MessageFormat resolveCode(String code, Locale locale);

}