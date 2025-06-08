////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.util.Locale;

import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 当无法解析消息时抛出此异常。
 * <p>
 * 此类是 {@code org.springframework.context.NoSuchMessageException} 的副本，
 * 稍作修改：添加了 {@code code} 和 {@code locale} 字段。
 * 它用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 */
public class NoSuchMessageException extends RuntimeException implements ErrorInfoConvertable  {

  private static final long serialVersionUID = -5829713310041359851L;

  private final String code;
  private final Locale locale;

  /**
   * 创建一个新的异常。
   *
   * @param code
   *     无法为给定区域设置解析的代码。
   * @param locale
   *     用于搜索代码的区域设置。
   */
  public NoSuchMessageException(final String code, final Locale locale) {
    super("No message found under code '" + code + "' for locale '" + locale + "'.");
    this.code = code;
    this.locale = locale;
  }

  /**
   * 创建一个新的异常。
   *
   * @param code
   *     无法为给定区域设置解析的代码。
   */
  public NoSuchMessageException(final String code) {
    super("No message found under code '" + code + "' for locale '" + Locale.getDefault() + "'.");
    this.code = code;
    this.locale = Locale.getDefault();
  }

  /**
   * 获取无法解析的消息代码。
   *
   * @return
   *     无法解析的消息代码。
   */
  public String getCode() {
    return code;
  }

  /**
   * 获取用于搜索消息代码的区域设置。
   *
   * @return
   *     用于搜索消息代码的区域设置。
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "NO_SUCH_MESSAGE",
        KeyValuePairList.of("code", code, "locale", locale));
  }
}