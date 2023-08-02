////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

public class ThrowableUtils {

  /**
   * Gets the root cause of a throwable object.
   *
   * @param original
   *     the specified throwable object.
   * @return
   *     the root cause of the throwable object.
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
   * Gets the message of the root cause of a throwable object.
   *
   * @param original
   *     the specified throwable object.
   * @return
   *     the message of the root cause of the throwable object.
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
