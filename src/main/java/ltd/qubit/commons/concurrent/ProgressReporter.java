////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import ltd.qubit.commons.util.clock.TimeMeter;

/**
 * A progress reporter that reports the progress of a task.
 *
 * @author Haixing Hu
 */
public interface ProgressReporter {
  /**
   * Reports the starting of a task.
   *
   * @param totalCount
   *     the total number of tasks.
   */
  void start(long totalCount);

  /**
   * Reports the progress of a task.
   *
   * @param totalCount
   *     the total number of tasks.
   * @param activeCount
   *     the number of active tasks.
   * @param completedCount
   *     the number of completed tasks.
   * @param meter
   *     the time meter of the task.
   */
  void process(long totalCount, long activeCount, long completedCount, TimeMeter meter);

  /**
   * Reports the finish of a task.
   *
   * @param totalCount
   *     the total number of tasks.
   * @param meter
   *     the time meter of the task, which must be stopped.
   */
  void finish(long totalCount, TimeMeter meter);

  /**
   * Reports the interruption of a task.
   *
   * @param totalCount
   *     the total number of tasks.
   * @param completedCount
   *     the number of completed tasks.
   * @param meter
   *     the time meter of the task, which must be stopped.
   */
  void interrupt(long totalCount, long completedCount, TimeMeter meter);
}
