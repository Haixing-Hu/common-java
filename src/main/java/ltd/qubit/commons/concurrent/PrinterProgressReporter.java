////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.io.Printer;
import ltd.qubit.commons.util.HumanReadable;
import ltd.qubit.commons.util.clock.TimeMeter;

/**
 * 将进度报告给{@link Printer}的{@link ProgressReporter}。
 *
 * @author 胡海星
 */
public class PrinterProgressReporter implements ProgressReporter {

  protected final Printer printer;

  /**
   * 构造一个{@link PrinterProgressReporter}。
   *
   * @param printer
   *     指定的{@link Printer}。
   */
  PrinterProgressReporter(final Printer printer) {
    this.printer = printer;
  }

  @Override
  public void start(final long totalCount) {
    printer.println("Starting %d tasks...", totalCount);
  }

  @Override
  public void process(final long totalCount, final long activeCount,
      final long completedCount, final TimeMeter meter) {
    printer.println();
    printer.println("--------------------------------------------------");
    printer.println("Waiting for all parallel tasks to finish...");
    printer.println("Total tasks: %s", totalCount);
    printer.println("Current active tasks: %d", activeCount);
    printer.println("Current completed tasks: %d", completedCount);
    printer.println("Current tasks in queue: %d", (totalCount - completedCount - activeCount));
    printer.println("Progress: %.2f%%", completedCount * 100.0 / totalCount);
    printProcessSpeed(completedCount, totalCount - completedCount, meter);
  }

  private void printProcessSpeed(final long completeCount,
      final long remainedCount, final TimeMeter meter) {
    final long duration = meter.duration();
    if (completeCount == 0) {
      printer.println("No task processed.");
    } else {
      final double speed = (double) duration / completeCount;
      printer.println("Average speed: %.2f ms/task, i.e., %.2f tasks/min",
          speed, (completeCount * 60.0 * 1000.0 / duration));
      // 计算预估剩余时间
      final long remainedTime = (long) (remainedCount * speed / 1000.0);
      printer.println("Estimated remaining time: %s",
          HumanReadable.formatDuration(remainedTime, TimeUnit.SECONDS));
    }
  }

  @Override
  public void finish(final long totalCount, final TimeMeter meter) {
    printer.println("All parallel tasks are finished.");
    printer.println("Processed %d tasks in %s.", totalCount, meter.readableDuration());
  }

  @Override
  public void interrupt(final long totalCount, final long completedCount, final TimeMeter meter) {
    printer.println("Task execution was interrupted.");
    printer.println("Processed %d of %d tasks in %s", completedCount, totalCount,
        meter.readableDuration());
  }
}