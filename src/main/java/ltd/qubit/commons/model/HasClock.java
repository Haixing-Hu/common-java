////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import ltd.qubit.commons.annotation.NoAutoTest;

/**
 * This interface indicates that the model has a clock object.
 *
 * @author Haixing Hu
 */
public interface HasClock {

  /**
   * Get the clock object this model has.
   *
   * @return
   *     The clock object that this model has.
   */
  @NoAutoTest
  Clock getClock();

  /**
   * Get the current time of the internal clock, accurate to the second.
   *
   * <p><b>Note:</b> Because we store timestamps internally in the database
   * using MySQL&#39;s {@code DATETIME} type, all object timestamps must be
   * truncated to seconds. Otherwise, you&#39;ll have trouble comparing
   * timestamps when unit testing.</p>
   *
   * @return
   *     The current time of the internal clock, accurate to the second.
   */
  @NoAutoTest
  default Instant now() {
    final Clock clock = getClock();
    return Instant.ofEpochMilli(clock.millis())
                  .truncatedTo(ChronoUnit.SECONDS);
  }
}
