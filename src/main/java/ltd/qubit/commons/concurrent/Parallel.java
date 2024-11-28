////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Provides utilities for executing tasks in parallel.
 *
 * @author Haixing Hu
 */
public class Parallel {
  /**
   * The default number of threads for executing tasks in parallel.
   * <p>
   * The value of this constant is the number of available processors of the
   * current machine.
   */
  public static final int DEFAULT_THREADS = Runtime.getRuntime().availableProcessors();

  /**
   * The default single thread threshold.
   * <p>
   * If the size of the collection is less than or equal to this value,
   * the tasks will be executed in a single thread.
   * <p>
   * The value of this constant is {@value}.
   */
  public static final int DEFAULT_SINGLE_THREAD_THRESHOLD = 1;

  /**
   * The default report interval in seconds.
   * <p>
   * The value of this constant is {@value}.
   */
  public static final int DEFAULT_REPORT_INTERVAL = 5;

  /**
   * The default progress reporter.
   */
  public static final ProgressReporter DEFAULT_PROGRESS_REPORTER = ConsoleProgressReporter.INSTANCE;


  private static final Logger logger = LoggerFactory.getLogger(Parallel.class);

  /**
   * Executes a given collection of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param tasks
   *     The collection of tasks to be executed.
   */
  public static void execute(final Collection<Runnable> tasks) {
    execute(tasks, DEFAULT_THREADS, DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER);
  }

  /**
   * Executes a given array of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param tasks
   *     The array of tasks to be executed.
   */
  public static void execute(final Runnable[] tasks) {
    execute(tasks, DEFAULT_THREADS, DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER);
  }

  /**
   * Executes a given collection of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param tasks
   *     The collection of tasks to be executed.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   */
  public static void execute(final Collection<Runnable> tasks, final int threads) {
    execute(tasks, threads, DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER);
  }

  /**
   * Executes a given array of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param tasks
   *     The array of tasks to be executed.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   */
  public static void execute(final Runnable[] tasks, final int threads) {
    execute(tasks, threads, DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER);
  }

  /**
   * Executes a given collection of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param tasks
   *     The collection of tasks to be executed.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reportInterval
   *     The interval in seconds to print the progress of the tasks. If it is
   *    *     less than or equal to zero, it is considered as 1.
   */
  public static void execute(final Collection<Runnable> tasks, final int threads,
      final int reportInterval) {
    execute(tasks, threads, reportInterval, DEFAULT_PROGRESS_REPORTER);
  }


  /**
   * Executes a given array of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param tasks
   *     The array of tasks to be executed.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reportInterval
   *     The interval in seconds to print the progress of the tasks. If it is
   *    *     less than or equal to zero, it is considered as 1.
   */
  public static void execute(final Runnable[] tasks, final int threads, final int reportInterval) {
    execute(tasks, threads, reportInterval, DEFAULT_PROGRESS_REPORTER);
  }

  /**
   * Executes a given collection of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be reported
   * to the specified {@link ProgressReporter}.
   *
   * @param tasks
   *     The collection of tasks to be executed.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   */
  public static void execute(final Collection<Runnable> tasks,
      final int threads, @Nullable final ProgressReporter reporter) {
    execute(tasks, threads, DEFAULT_REPORT_INTERVAL, reporter);
  }

  /**
   * Executes a given array of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be reported
   * to the specified {@link ProgressReporter}.
   *
   * @param tasks
   *     The array of tasks to be executed.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   */
  public static void execute(final Runnable[] tasks, final int threads,
      @Nullable final ProgressReporter reporter) {
    execute(tasks, threads, DEFAULT_REPORT_INTERVAL, reporter);
  }

  /**
   * Executes a given collection of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be reported
   * to the specified {@link ProgressReporter}.
   *
   * @param tasks
   *     The collection of tasks to be executed.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reportInterval
   *     The interval in seconds to print the progress of the tasks. If it is
   *     less than or equal to zero, it is considered as 1.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   */
  public static void execute(final Collection<Runnable> tasks,
      final int threads, final int reportInterval,
      @Nullable final ProgressReporter reporter) {
    execute(tasks, tasks.size(), threads, reportInterval, reporter);
  }

  /**
   * Executes a given array of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be reported
   * to the specified {@link ProgressReporter}.
   *
   * @param tasks
   *     The array of tasks to be executed.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reportInterval
   *     The interval in seconds to print the progress of the tasks. If it is
   *     less than or equal to zero, it is considered as 1.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   */
  public static void execute(final Runnable[] tasks, final int threads,
      final int reportInterval, @Nullable final ProgressReporter reporter) {
    execute(new IterableArray<>(tasks), tasks.length, threads, reportInterval, reporter);
  }

  /**
   * Executes a given collection of tasks in parallel, with a fixed number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be reported
   * to the specified {@link ProgressReporter}.
   *
   * @param tasks
   *     The iterable tasks to be executed.
   * @param taskCount
   *     The total number of tasks to be executed.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reportInterval
   *     The interval in seconds to print the progress of the tasks. If it is
   *     less than or equal to zero, it is considered as 1.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   */
  public static void execute(final Iterable<Runnable> tasks,
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
   * Performs an action to each element of a list in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param col
   *     The collection of elements to be iterated.
   * @param action
   *     The action to be executed for each element.
   */
  public static <T> void forEach(final Collection<T> col, final Consumer<T> action) {
    forEach(col, DEFAULT_THREADS, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * Performs an action to each element of an array in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param array
   *     The array of elements to be iterated.
   * @param action
   *     The action to be executed for each element.
   */
  public static <T> void forEach(final T[] array, final Consumer<T> action) {
    forEach(array, DEFAULT_THREADS, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * Performs an action to each element of a list in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param col
   *     The collection of elements to be iterated.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param action
   *     The action to be executed for each element.
   */
  public static <T> void forEach(final Collection<T> col, final int threads,
      final Consumer<T> action) {
    forEach(col, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * Performs an action to each element of an array in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param array
   *     The array of elements to be iterated.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param action
   *     The action to be executed for each element.
   */
  public static <T> void forEach(final T[] array, final int threads,
      final Consumer<T> action) {
    forEach(array, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * Performs an action to each element of a list in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param col
   *     The collection of elements to be iterated.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param singleThreadThreshold
   *     If the size of the collection is less than or equal to this value,
   *     the tasks will be executed in a single thread. If it is less than or
   *     equal to zero, it is considered as 0.
   * @param action
   *     The action to be executed for each element.
   */
  public static <T> void forEach(final Collection<T> col, final int threads,
      final int singleThreadThreshold, final Consumer<T> action) {
    forEach(col, threads, singleThreadThreshold, DEFAULT_REPORT_INTERVAL,
        DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * Performs an action to each element of an array in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param array
   *     The array of elements to be iterated.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param singleThreadThreshold
   *     If the size of the collection is less than or equal to this value,
   *     the tasks will be executed in a single thread. If it is less than or
   *     equal to zero, it is considered as 0.
   * @param action
   *     The action to be executed for each element.
   */
  public static <T> void forEach(final T[] array, final int threads,
      final int singleThreadThreshold, final Consumer<T> action) {
    forEach(array, threads, singleThreadThreshold, DEFAULT_REPORT_INTERVAL,
        DEFAULT_PROGRESS_REPORTER, action);
  }

  /**
   * Performs an action to each element of a list in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param col
   *     The collection of elements to be iterated.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   * @param action
   *     The action to be executed for each element.
   */
  public static <T> void forEach(final Collection<T> col, final int threads,
      @Nullable final ProgressReporter reporter, final Consumer<T> action) {
    forEach(col, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, reporter, action);
  }

  /**
   * Performs an action to each element of an array in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param array
   *     The array of elements to be iterated.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   * @param action
   *     The action to be executed for each element.
   */
  public static <T> void forEach(final T[] array, final int threads,
      @Nullable final ProgressReporter reporter, final Consumer<T> action) {
    forEach(array, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        DEFAULT_REPORT_INTERVAL, reporter, action);
  }

  /**
   * Performs an action to each element of a list in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param col
   *     The collection of elements to be iterated.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reportInterval
   *     The interval in seconds to print the progress of the tasks. If it is
   *    *     less than or equal to zero, it is considered as 1.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   * @param action
   *     The action to be executed for each element.
   */
  public static <T> void forEach(final Collection<T> col, final int threads,
      final int reportInterval, @Nullable final ProgressReporter reporter,
      final Consumer<T> action) {
    forEach(col, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        reportInterval, reporter, action);
  }

  /**
   * Performs an action to each element of an array in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param array
   *     The array of elements to be iterated.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param reportInterval
   *     The interval in seconds to print the progress of the tasks. If it is
   *    *     less than or equal to zero, it is considered as 1.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   * @param action
   *     The action to be executed for each element.
   */
  public static <T> void forEach(final T[] array, final int threads,
      final int reportInterval, @Nullable final ProgressReporter reporter,
      final Consumer<T> action) {
    forEach(array, threads, DEFAULT_SINGLE_THREAD_THRESHOLD,
        reportInterval, reporter, action);
  }

  /**
   * Performs an action to each element of a list in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param col
   *     The collection of elements to be iterated.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param singleThreadThreshold
   *     If the size of the collection is less than or equal to this value,
   *     the tasks will be executed in a single thread. If it is less than or
   *     equal to zero, it is considered as 0.
   * @param reportInterval
   *     The interval in seconds to print the progress of the tasks. If it is
   *     less than or equal to zero, it is considered as 1.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   * @param action
   *     The action to be executed for each element.
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
   * Performs an action to each element of an array in parallel, with a fixed
   * number of threads.
   * <p>
   * The calling thread will be blocked until all tasks are finished.
   * <p>
   * During the execution of the tasks, the progress of the tasks will be printed
   * to the {@code System.out}.
   *
   * @param array
   *     The array of elements to be iterated.
   * @param threads
   *     The number of threads to be used for executing the tasks.
   * @param singleThreadThreshold
   *     If the size of the collection is less than or equal to this value,
   *     the tasks will be executed in a single thread. If it is less than or
   *     equal to zero, it is considered as 0.
   * @param reportInterval
   *     The interval in seconds to print the progress of the tasks. If it is
   *     less than or equal to zero, it is considered as 1.
   * @param reporter
   *     The reporter to report the progress of the tasks. If it is {@code null},
   *     no progress will be reported.
   * @param action
   *     The action to be executed for each element.
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
