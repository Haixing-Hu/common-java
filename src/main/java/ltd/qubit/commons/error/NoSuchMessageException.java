////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.util.Locale;

import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * This exception is thrown when a message can't be resolved.
 * <p>
 * This class is a copy of {@code org.springframework.context.NoSuchMessageException}
 * with a slight modification: the {@code code} and {@code locale} fields are added.
 * It is used to avoid the dependency of Spring Framework.
 *
 * @author Haixing Hu
 */
public class NoSuchMessageException extends RuntimeException implements ErrorInfoConvertable  {

  private static final long serialVersionUID = 7603035025205517388L;

  private final String code;
  private final Locale locale;

  /**
   * Create a new exception.
   * @param code the code that could not be resolved for given locale
   * @param locale the locale that was used to search for the code within
   */
  public NoSuchMessageException(final String code, final Locale locale) {
    super("No message found under code '" + code + "' for locale '" + locale + "'.");
    this.code = code;
    this.locale = locale;
  }

  /**
   * Create a new exception.
   * @param code the code that could not be resolved for given locale
   */
  public NoSuchMessageException(final String code) {
    super("No message found under code '" + code + "' for locale '" + Locale.getDefault() + "'.");
    this.code = code;
    this.locale = Locale.getDefault();
  }

  public String getCode() {
    return code;
  }

  public Locale getLocale() {
    return locale;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "NO_SUCH_MESSAGE",
        KeyValuePairList.of("code", code, "locale", locale));
  }
}
