////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.message;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;

/**
 * Default implementation of the {@link MessageSourceResolvable} interface.
 * <p>
 * Offers an easy way to store all the necessary values needed to resolve
 * a message via a {@link MessageSource}.
 * <p>
 * This class is a copy of {@code org.springframework.context.support.DefaultMessageSourceResolvable}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see MessageSource#getMessage(MessageSourceResolvable, Locale)
 */
public class DefaultMessageSourceResolvable implements MessageSourceResolvable, Serializable {

  private static final long serialVersionUID = 4017298790454588239L;

  @Nullable
  private final String[] codes;

  @Nullable
  private final Object[] arguments;

  @Nullable
  private final String defaultMessage;

  /**
   * Create a new DefaultMessageSourceResolvable.
   *
   * @param code
   *     the code to be used to resolve this message
   */
  public DefaultMessageSourceResolvable(final String code) {
    this(new String[]{code}, null, null);
  }

  /**
   * Create a new DefaultMessageSourceResolvable.
   *
   * @param codes
   *     the codes to be used to resolve this message
   */
  public DefaultMessageSourceResolvable(final String[] codes) {
    this(codes, null, null);
  }

  /**
   * Create a new DefaultMessageSourceResolvable.
   *
   * @param codes
   *     the codes to be used to resolve this message
   * @param defaultMessage
   *     the default message to be used to resolve this message
   */
  public DefaultMessageSourceResolvable(final String[] codes,
      final String defaultMessage) {
    this(codes, null, defaultMessage);
  }

  /**
   * Create a new DefaultMessageSourceResolvable.
   *
   * @param codes
   *     the codes to be used to resolve this message
   * @param arguments
   *     the array of arguments to be used to resolve this message
   */
  public DefaultMessageSourceResolvable(final String[] codes,
      final Object[] arguments) {
    this(codes, arguments, null);
  }

  /**
   * Create a new DefaultMessageSourceResolvable.
   *
   * @param codes
   *     the codes to be used to resolve this message
   * @param arguments
   *     the array of arguments to be used to resolve this message
   * @param defaultMessage
   *     the default message to be used to resolve this message
   */
  public DefaultMessageSourceResolvable(@Nullable final String[] codes,
      @Nullable final Object[] arguments,
      @Nullable final String defaultMessage) {
    this.codes = codes;
    this.arguments = arguments;
    this.defaultMessage = defaultMessage;
  }

  /**
   * Copy constructor: Create a new instance from another resolvable.
   *
   * @param resolvable
   *     the resolvable to copy from
   */
  public DefaultMessageSourceResolvable(final MessageSourceResolvable resolvable) {
    this(resolvable.getCodes(), resolvable.getArguments(), resolvable.getDefaultMessage());
  }

  /**
   * Return the default code of this resolvable, that is, the last one in the
   * codes array.
   */
  @Nullable
  public String getCode() {
    if (ArrayUtils.isEmpty(codes)) {
      return null;
    } else {
      return codes[codes.length - 1];
    }
  }

  @Override
  @Nullable
  public String[] getCodes() {
    return this.codes;
  }

  @Override
  @Nullable
  public Object[] getArguments() {
    return this.arguments;
  }

  @Override
  @Nullable
  public String getDefaultMessage() {
    return this.defaultMessage;
  }

  /**
   * Indicate whether the specified default message needs to be rendered for
   * substituting placeholders and/or {@link MessageFormat} escaping.
   *
   * @return {@code true} if the default message may contain argument
   *     placeholders; {@code false} if it definitely does not contain
   *     placeholders or custom escaping and can therefore be simply exposed
   *     as-is
   * @see #getDefaultMessage()
   * @see #getArguments()
   * @see AbstractMessageSource#renderDefaultMessage
   * @since 5.1.7
   */
  public boolean shouldRenderDefaultMessage() {
    return true;
  }

  /**
   * Build a default String representation for this MessageSourceResolvable:
   * including codes, arguments, and default message.
   */
  protected final String resolvableToString() {
    final StringBuilder result = new StringBuilder(64);
    result.append("codes [")
          .append(StringUtils.join(',', codes))
          .append("]; arguments [")
          .append(StringUtils.join(',', arguments))
          .append("]; default message [")
          .append(defaultMessage)
          .append(']');
    return result.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final DefaultMessageSourceResolvable other = (DefaultMessageSourceResolvable) o;
    return Equality.equals(codes, other.codes)
        && Equality.equals(arguments, other.arguments)
        && Equality.equals(defaultMessage, other.defaultMessage);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, codes);
    result = Hash.combine(result, multiplier, arguments);
    result = Hash.combine(result, multiplier, defaultMessage);
    return result;
  }

  /**
   * The default implementation exposes the attributes of this
   * MessageSourceResolvable.
   * <p>
   * To be overridden in more specific subclasses, potentially including the
   * resolvable content through {@code resolvableToString()}.
   *
   * @see #resolvableToString
   */
  @Override
  public String toString() {
    return getClass().getName() + ": " + resolvableToString();
  }
}