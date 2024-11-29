////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

import static ltd.qubit.commons.lang.DoubleUtils.roundToString;
import static ltd.qubit.commons.lang.StringUtils.formatPercent;

/**
 * A {@link ProgressReporter} that reports the progress to a {@link Printer}.
 *
 * @author Haixing Hu
 */
public class PrinterProgressReporter implements ProgressReporter {

  protected final Printer printer;

  PrinterProgressReporter(final Printer printer) {
    this.printer = printer;
  }

  @Override
  public void start(final long totalCount) {
    printer.println("Starting " + totalCount + " tasks...");
  }

  @Override
  public void process(final long totalCount, final long activeCount,
      final long completedCount, final TimeMeter meter) {
    printer.println();
    printer.println("--------------------------------------------------");
    printer.println("Waiting for all parallel tasks to finish...");
    printer.println("Total tasks: " + totalCount);
    printer.println("Current active tasks: " + activeCount);
    printer.println("Current completed tasks: " + completedCount);
    printer.println("Current tasks in queue: " + (totalCount - completedCount - activeCount));
    final double percent = (double) completedCount / totalCount;
    printer.println("Progress: " + formatPercent(percent, 2));
    printProcessSpeed(completedCount, totalCount - completedCount, meter);
  }

  private void printProcessSpeed(final long completeCount,
      final long remainedCount, final TimeMeter meter) {
    final long duration = meter.duration();
    if (completeCount == 0) {
      printer.println("No task processed.");
    } else {
      final double speed = (double) duration / completeCount;
      printer.println("Average speed: " + roundToString(speed, 2)
          + " ms/task, i.e., "
          + roundToString(completeCount * 60.0 * 1000.0 / duration, 2)
          + " tasks/min");
      // 计算预估剩余时间
      final long remainedTime = (long) (remainedCount * speed / 1000.0);
      printer.println("Estimated remaining time: "
          + HumanReadable.formatDuration(remainedTime, TimeUnit.SECONDS));
    }
  }

  @Override
  public void finish(final long totalCount, final TimeMeter meter) {
    printer.println("All parallel tasks are finished.");
    printer.println("Processed " + totalCount + " tasks in "
        + meter.readableDuration());
  }

  @Override
  public void interrupt(final long totalCount, final long completedCount, final TimeMeter meter) {
    printer.println("Task execution was interrupted.");
    printer.println("Processed " + completedCount + " of "
        + totalCount + " tasks in " + meter.readableDuration());
  }
}
