////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提供线程相关的工具函数。
 *
 * @author 胡海星
 */
public class ThreadUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtils.class);

  /**
   * 使当前线程休眠指定的毫秒数。
   *
   * @param milliseconds
   *     要休眠的毫秒数。
   */
  public static void sleep(final long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (final InterruptedException e) {
      LOGGER.warn("The thread sleeping was interrupted.", e);
      // ignore
    }
  }
}