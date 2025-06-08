////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.datastructure.IterableArray;
import ltd.qubit.commons.datastructure.LazyIterable;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.util.clock.TimeMeter;

/**
 * 提供并行执行任务的工具。
 *
 * @author 胡海星
 */
public class Parallel {
  /**
   * 并行执行任务的默认线程数。
   * <p>
   * 此常量的值是当前计算机的可用处理器数。
   */
  public static final int DEFAULT_THREADS = Runtime.getRuntime().availableProcessors();

  /**
   * 默认的单线程阈值。
   * <p>
   * 如果集合的大小小于或等于此值，则任务将在单个线程中执行。
   * <p>
   * 此常量的值为{@value}。
   */
  public static final int DEFAULT_SINGLE_THREAD_THRESHOLD = 1;

  /**
   * 默认报告间隔（以秒为单位）。
   * <p>
   * 此常量的值为{@value}。
   */
  public static final int DEFAULT_REPORT_INTERVAL = 5;

  /**
   * 默认的进度报告器。
   */
  public static final ProgressReporter DEFAULT_PROGRESS_REPORTER = ConsoleProgressReporter.INSTANCE;


  private static final Logger logger = LoggerFactory.getLogger(Parallel.class);

  /**
   * 使用固定数量的线程并行执行给定的任务集合。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param tasks
   *     要执行的任务的集合。
   */
  public static void execute(final Collection<? extends Runnable> tasks) {
    execute(tasks, DEFAULT_THREADS, DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER);
  }

  /**
   * 使用固定数量的线程并行执行给定的任务数组。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param <T>
   *     任务的类型。
   * @param tasks
   *     要执行的任务数组。
   */
  public static <T extends Runnable> void execute(final T[] tasks) {
    execute(tasks, DEFAULT_THREADS, DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER);
  }

  /**
   * 使用固定数量的线程并行执行给定的任务集合。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param tasks
   *     要执行的任务的集合。
   * @param threads
   *     用于执行任务的线程数。
   */
  public static void execute(final Collection<? extends Runnable> tasks, final int threads) {
    execute(tasks, threads, DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER);
  }

  /**
   * 使用固定数量的线程并行执行给定的任务数组。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param tasks
   *     要执行的任务数组。
   * @param threads
   *     用于执行任务的线程数。
   */
  public static <T extends Runnable> void execute(final T[] tasks, final int threads) {
    execute(tasks, threads, DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER);
  }

  /**
   * 使用固定数量的线程并行执行给定的任务集合。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param tasks
   *     要执行的任务的集合。
   * @param threads
   *     用于执行任务的线程数。
   * @param reportInterval
   *     打印任务进度的间隔（以秒为单位）。 如果它小于或等于零，则视为1。
   */
  public static void execute(final Collection<? extends Runnable> tasks,
      final int threads, final int reportInterval) {
    execute(tasks, threads, reportInterval, DEFAULT_PROGRESS_REPORTER);
  }


  /**
   * 使用固定数量的线程并行执行给定的任务数组。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param tasks
   *     要执行的任务数组。
   * @param threads
   *     用于执行任务的线程数。
   * @param reportInterval
   *     打印任务进度的间隔（以秒为单位）。 如果它小于或等于零，则视为1。
   */
  public static <T extends Runnable> void execute(final T[] tasks,
      final int threads, final int reportInterval) {
    execute(tasks, threads, reportInterval, DEFAULT_PROGRESS_REPORTER);
  }

  /**
   * 使用固定数量的线程并行执行给定的任务集合。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param tasks
   *     要执行的任务的集合。
   * @param threads
   *     用于执行任务的线程数。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   */
  public static void execute(final Collection<? extends Runnable> tasks,
      final int threads, @Nullable final ProgressReporter reporter) {
    execute(tasks, threads, DEFAULT_REPORT_INTERVAL, reporter);
  }

  /**
   * 使用固定数量的线程并行执行给定的任务数组。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param tasks
   *     要执行的任务数组。
   * @param threads
   *     用于执行任务的线程数。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   */
  public static <T extends Runnable> void execute(final T[] tasks, final int threads,
      @Nullable final ProgressReporter reporter) {
    execute(tasks, threads, DEFAULT_REPORT_INTERVAL, reporter);
  }

  /**
   * 使用固定数量的线程并行执行给定的任务集合。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param tasks
   *     要执行的任务的集合。
   * @param threads
   *     用于执行任务的线程数。
   * @param reportInterval
   *     打印任务进度的间隔（以秒为单位）。 如果它小于或等于零，则视为1。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   */
  public static void execute(final Collection<? extends Runnable> tasks,
      final int threads, final int reportInterval,
      @Nullable final ProgressReporter reporter) {
    execute(tasks, tasks.size(), threads, reportInterval, reporter);
  }

  /**
   * 使用固定数量的线程并行执行给定的任务数组。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param tasks
   *     要执行的任务数组。
   * @param threads
   *     用于执行任务的线程数。
   * @param reportInterval
   *     打印任务进度的间隔（以秒为单位）。 如果它小于或等于零，则视为1。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   */
  public static <T extends Runnable> void execute(final T[] tasks, final int threads,
      final int reportInterval, @Nullable final ProgressReporter reporter) {
    execute(new IterableArray<>(tasks), tasks.length, threads, reportInterval, reporter);
  }

  /**
   * 使用固定数量的线程并行执行给定的任务可迭代对象。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param tasks
   *     要执行的任务的可迭代对象。
   * @param taskCount
   *     任务总数。
   * @param threads
   *     用于执行任务的线程数。
   * @param reportInterval
   *     打印任务进度的间隔（以秒为单位）。 如果它小于或等于零，则视为1。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   */
  public static void execute(final Iterable<? extends Runnable> tasks,
      final int taskCount, final int threads, final int reportInterval,
      @Nullable final ProgressReporter reporter) {
    if (taskCount == 0) {
      logger.info("No task to execute.");
      return;
    } else if (taskCount == 1) {
      logger.info("Only one task to execute.");
      for (final Runnable task : tasks) {
        try {
          task.run();
        } catch (final Throwable e) {
          logger.error("An error occurred while executing the task: {}", e.getMessage(), e);
        }
      }
      return;
    }
    final int maxThreads = Math.min(threads, taskCount);
    final ThreadPoolExecutor executor =
        (ThreadPoolExecutor) Executors.newFixedThreadPool(maxThreads);
    logger.info("Start executing {} tasks in parallel in {} threads.", taskCount, maxThreads);
    final int interval = Math.max(1, reportInterval);
    final TimeMeter meter = new TimeMeter();
    meter.start();
    if (reporter != null) {
      reporter.start(taskCount);
    }
    logger.info("Start submitting tasks...");
    for (final Runnable task : tasks) {
      executor.submit(() -> {
        try {
          // The task must be executed in a try-catch block to prevent the
          // exception from being swallowed.
          task.run();
        } catch (final Throwable e) {
          logger.error("An error occurred while executing the task: {}", e.getMessage(), e);
        }
      });
    }
    logger.info("All {} tasks have been submitted.", taskCount);
    executor.shutdown();
    logger.info("Waiting for the completion of all tasks...");
    // wait until all tasks are finished
    try {
      while (!executor.awaitTermination(interval, TimeUnit.SECONDS)) {
        if (reporter != null) {
          final long activeCount = executor.getActiveCount();
          final long completedCount = executor.getCompletedTaskCount();
          reporter.process(taskCount, activeCount, completedCount, meter);
        }
      }
      meter.stop();
      if (reporter != null) {
        reporter.finish(taskCount, meter);
      }
      logger.info("All {} tasks have been finished in {}.", taskCount, meter.readableDuration());
      logger.info("Average speed is: {} or {}", meter.speedPerSecond(taskCount),
          meter.speedPerMinute(taskCount));
    } catch (final InterruptedException e) {
      meter.stop();
      if (reporter != null) {
        reporter.interrupt(taskCount, executor.getCompletedTaskCount(), meter);
      }
      logger.warn("The execution of all tasks has been interrupted in {}.", meter.readableDuration());
    }
  }

  /**
   * 对集合中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param col
   *     包含要操作元素的集合。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final Collection<T> col, final Consumer<T> action) {
    forEach(col, DEFAULT_THREADS, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * 对数组中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param array
   *     包含要操作元素的数组。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final T[] array, final Consumer<T> action) {
    forEach(array, DEFAULT_THREADS, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * 对集合中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param col
   *     包含要操作元素的集合。
   * @param threads
   *     用于执行任务的线程数。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final Collection<T> col, final int threads,
      final Consumer<T> action) {
    forEach(col, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * 对数组中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param array
   *     包含要操作元素的数组。
   * @param threads
   *     用于执行任务的线程数。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final T[] array, final int threads,
      final Consumer<T> action) {
    forEach(array, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER, action);
  }


  /**
   * 对集合中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param col
   *     包含要操作元素的集合。
   * @param threads
   *     用于执行任务的线程数。
   * @param singleThreadThreshold
   *     如果集合的大小小于或等于此值，则任务将在单个线程中执行。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final Collection<T> col, final int threads,
      final int singleThreadThreshold, final Consumer<T> action) {
    forEach(col, threads, singleThreadThreshold, DEFAULT_REPORT_INTERVAL,
        DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * 对数组中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将被打印到{@code System.out}。
   *
   * @param array
   *     包含要操作元素的数组。
   * @param threads
   *     用于执行任务的线程数。
   * @param singleThreadThreshold
   *     如果数组的大小小于或等于此值，则任务将在单个线程中执行。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final T[] array, final int threads,
      final int singleThreadThreshold, final Consumer<T> action) {
    forEach(array, threads, singleThreadThreshold, DEFAULT_REPORT_INTERVAL,
        DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * 对集合中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param col
   *     包含要操作元素的集合。
   * @param threads
   *     用于执行任务的线程数。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final Collection<T> col, final int threads,
      @Nullable final ProgressReporter reporter, final Consumer<T> action) {
    forEach(col, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, reporter, action);
  }

  /**
   * 对数组中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param array
   *     包含要操作元素的数组。
   * @param threads
   *     用于执行任务的线程数。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final T[] array, final int threads,
      @Nullable final ProgressReporter reporter, final Consumer<T> action) {
    forEach(array, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, reporter, action);
  }

  /**
   * 对集合中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param col
   *     包含要操作元素的集合。
   * @param threads
   *     用于执行任务的线程数。
   * @param reportInterval
   *     打印任务进度的间隔（以秒为单位）。 如果它小于或等于零，则视为1。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final Collection<T> col, final int threads,
      final int reportInterval, @Nullable final ProgressReporter reporter,
      final Consumer<T> action) {
    forEach(col, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        reportInterval, reporter, action);
  }

  /**
   * 对数组中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param array
   *     包含要操作元素的数组。
   * @param threads
   *     用于执行任务的线程数。
   * @param reportInterval
   *     打印任务进度的间隔（以秒为单位）。 如果它小于或等于零，则视为1。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final T[] array, final int threads,
      final int reportInterval, @Nullable final ProgressReporter reporter,
      final Consumer<T> action) {
    forEach(array, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        reportInterval, reporter, action);
  }

  /**
   * 对集合中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param col
   *     包含要操作元素的集合。
   * @param threads
   *     用于执行任务的线程数。
   * @param singleThreadThreshold
   *     如果集合的大小小于或等于此值，则任务将在单个线程中执行。
   * @param reportInterval
   *     打印任务进度的间隔（以秒为单位）。 如果它小于或等于零，则视为1。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final Collection<T> col, final int threads,
      final int singleThreadThreshold, final int reportInterval,
      @Nullable final ProgressReporter reporter, final Consumer<T> action) {
    if ((threads <= 1) || (col.size() <= singleThreadThreshold)) {
      logger.info("Execute the {} tasks in a single thread.", col.size());
      col.forEach(action);
    } else {
      final int cores = Runtime.getRuntime().availableProcessors();
      final int theThreads = Math.max(threads, cores);
      // in order to save memory and optimize performance, we use a lazy
      // iterable to generate the tasks
      final LazyIterable<Runnable> tasks = new LazyIterable<>(col, (e) -> (() -> action.accept(e)));
      execute(tasks, col.size(), theThreads, reportInterval, reporter);
    }
  }

  /**
   * 对数组中的每个元素并行执行给定的操作。
   * <p>
   * 调用线程将被阻塞，直到所有任务都完成。
   * <p>
   * 在任务执行期间，任务的进度将报告给指定的{@link ProgressReporter}。
   *
   * @param array
   *     包含要操作元素的数组。
   * @param threads
   *     用于执行任务的线程数。
   * @param singleThreadThreshold
   *     如果数组的大小小于或等于此值，则任务将在单个线程中执行。
   * @param reportInterval
   *     打印任务进度的间隔（以秒为单位）。 如果它小于或等于零，则视为1。
   * @param reporter
   *     报告任务进度的报告器。如果为{@code null}，将不报告任何进度。
   * @param action
   *     要对每个元素执行的操作。
   */
  public static <T> void forEach(final T[] array, final int threads,
      final int singleThreadThreshold, final int reportInterval,
      @Nullable final ProgressReporter reporter, final Consumer<T> action) {
    if ((threads <= 1) || (array.length <= singleThreadThreshold)) {
      logger.info("Execute the {} tasks in a single thread.", array.length);
      ArrayUtils.forEach(array, action);
    } else {
      final int cores = Runtime.getRuntime().availableProcessors();
      final int theThreads = Math.max(threads, cores);
      // in order to save memory and optimize performance, we use a lazy
      // iterable to generate the tasks
      final LazyIterable<Runnable> tasks = new LazyIterable<>(array, (e) -> (() -> action.accept(e)));
      execute(tasks, array.length, theThreads, reportInterval, reporter);
    }
  }
}