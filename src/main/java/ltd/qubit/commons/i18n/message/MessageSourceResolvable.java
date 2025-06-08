////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.message;

import javax.annotation.Nullable;

/**
 * Interface for objects that are suitable for message resolution in a
 * {@link org.springframework.context.MessageSource}.
 * <p>
 * Spring's own validation error classes implement this interface.
 * <p>
 * This class is a copy of {@code org.springframework.context.MessageSourceResolvable}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see MessageSource#getMessage(MessageSourceResolvable, java.util.Locale)
 */
@FunctionalInterface
public interface MessageSourceResolvable {

  /**
   * Return the codes to be used to resolve this message, in the order that they
   * should get tried. The last code will therefore be the default one.
   *
   * @return a String array of codes which are associated with this message
   */
  @Nullable
  String[] getCodes();

  /**
   * Return the array of arguments to be used to resolve this message.
   * <p>The default implementation simply returns {@code null}.
   *
   * @return an array of objects to be used as parameters to replace
   *     placeholders within the message text
   * @see java.text.MessageFormat
   */
  @Nullable
  default Object[] getArguments() {
    return null;
  }

  /**
   * Return the default message to be used to resolve this message.
   * <p>The default implementation simply returns {@code null}.
   * Note that the default message may be identical to the primary message code
   * ({@link #getCodes()}), which effectively enforces
   * {@link AbstractMessageSource#setUseCodeAsDefaultMessage}
   * for this particular message.
   *
   * @return the default message, or {@code null} if no default.
   */
  @Nullable
  default String getDefaultMessage() {
    return null;
  }
}