////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import ltd.qubit.commons.util.clock.TimeMeter;

/**
 * 报告任务进度的进度报告器。
 *
 * @author 胡海星
 */
public interface ProgressReporter {
  /**
   * 报告任务的开始。
   *
   * @param totalCount
   *     任务总数。
   */
  void start(long totalCount);

  /**
   * 报告任务的进度。
   *
   * @param totalCount
   *     任务总数。
   * @param activeCount
   *     活动任务数。
   * @param completedCount
   *     已完成任务数。
   * @param meter
   *     任务的时间计量器。
   */
  void process(long totalCount, long activeCount, long completedCount, TimeMeter meter);

  /**
   * 报告任务的完成。
   *
   * @param totalCount
   *     任务总数。
   * @param meter
   *     任务的时间计量器，必须已停止。
   */
  void finish(long totalCount, TimeMeter meter);

  /**
   * 报告任务的中断。
   *
   * @param totalCount
   *     任务总数。
   * @param completedCount
   *     已完成任务数。
   * @param meter
   *     任务的时间计量器，必须已停止。
   */
  void interrupt(long totalCount, long completedCount, TimeMeter meter);
}