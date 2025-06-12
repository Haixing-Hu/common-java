////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * 此类提供了处理异常对象的实用工具方法。
 *
 * @author 胡海星
 */
public class ThrowableUtils {

  /**
   * 获取异常对象的根本原因。
   *
   * @param original
   *     指定的异常对象。
   * @return
   *     该异常对象的根本原因。
   */
  public static Throwable getRootCause(final Throwable original) {
    if (original == null) {
      return null;
    }
    Throwable e = original;
    while (e.getCause() != null) {
      e = e.getCause();
    }
    return e;
  }

  /**
   * 获取异常对象根本原因的消息。
   *
   * @param original
   *     指定的异常对象。
   * @return
   *     该异常对象根本原因的消息。
   */
  public static String getRootCauseMessage(final Throwable original) {
    if (original == null) {
      return null;
    }
    Throwable e = original;
    while (e.getCause() != null) {
      e = e.getCause();
    }
    return e.getMessage();
  }
}