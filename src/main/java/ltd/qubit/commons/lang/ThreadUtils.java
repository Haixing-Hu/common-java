////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide utility functions for threading.
 *
 * @author Haixing Hu
 */
public class ThreadUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtils.class);

  public static void sleep(final long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (final InterruptedException e) {
      LOGGER.warn("The thread sleeping was interrupted.", e);
      // ignore
    }
  }
}
