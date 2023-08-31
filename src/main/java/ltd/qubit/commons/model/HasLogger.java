////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import ltd.qubit.commons.annotation.NoAutoTest;

import org.slf4j.Logger;

/**
 * This interface indicates that an object has a logger.
 *
 * @author Haixing Hu
 */
public interface HasLogger {

  /**
   * Get the logger of this object.
   *
   * @return
   *     the logger for this object.
   */
  @NoAutoTest
  Logger getLogger();
}
