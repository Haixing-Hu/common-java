////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 提供任务执行相关的工具函数。
 *
 * @author 胡海星
 */
public class TaskUtils {

  private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

  /**
   * 以可能的延迟异步执行给定的任务，并返回一个{@link CompletableFuture}。
   * <p>
   * 此方法允许在单独的线程中执行任务，然后可能进行延迟，然后通过
   * {@link CompletableFuture}提供结果。如果在任务执行期间抛出异常，
   * 它将通过{@link CompletableFuture}传播。
   * <p>
   * 总延迟时间取决于指定的任务最短执行时间。如果任务执行时间小于指定的最短执行时间，
   * 则延迟时间将为最短执行时间与任务执行时间之差。否则，延迟将为零。
   * <p>
   * 使用示例:
   * <pre><code>
   * executeWithDelay(
   *     () -> {
   *         // Task logic that returns a result
   *         return "Result";
   *     },
   *     2000 // Min execution time in milliseconds
   * ).thenAccept(result -> {
   *     // Handle the result
   *     System.out.println("Task completed with result: " + result);
   * }).exceptionally(e -> {
   *     // Handle any exceptions thrown by the task
   *     e.printStackTrace();
   *     return null;
   * });
   * </code></pre>
   *
   * @param <T>
   *     任务产生的结果的类型。
   * @param task
   *     要执行的任务。这是一个可以返回结果的{@link Callable}。
   * @param minExecutionTime
   *     任务的指定最短执行时间，以毫秒为单位。如果任务执行时间小于指定的最短执行时间，
   *     则延迟时间将是最短执行时间与任务执行时间之差。否则，延迟将为零。
   * @return
   *     一个{@link CompletableFuture}，它将在指定的延迟后完成并返回任务的结果。
   *     如果任务抛出异常，则{@link CompletableFuture}将以该异常的方式完成。
   * @deprecated 请改用 {@link Delay#execute(Callable, long)}。
   */
  @Deprecated
  public static <T> CompletableFuture<T> executeWithDelay(final Callable<T> task,
      final long minExecutionTime) {
    final CompletableFuture<T> future = new CompletableFuture<>();
    EXECUTOR_SERVICE.submit(() -> {
      try {
        final long startTime = System.currentTimeMillis();
        final T result = task.call();
        final long elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime < minExecutionTime) {
          Thread.sleep(minExecutionTime - elapsedTime);
        }
        future.complete(result);
      } catch (final Exception e) {
        future.completeExceptionally(e);
      }
    });
    return future;
  }


  /**
   * 以可能的延迟异步执行给定的任务，并返回一个{@link CompletableFuture}。
   * <p>
   * 此方法允许在单独的线程中执行任务，然后可能进行延迟，然后通过
   * {@link CompletableFuture}提供结果。如果在任务执行期间抛出异常，
   * 它将通过{@link CompletableFuture}传播。
   * <p>
   * 总延迟时间取决于指定的任务最短执行时间。如果任务执行时间小于指定的最短执行时间，
   * 则延迟时间将为最短执行时间与任务执行时间之差。否则，延迟将为零。
   * <p>
   * 使用示例:
   * <pre><code>
   * executeWithDelay(
   *     () -> {
   *         // Task logic that has no return
   *     },
   *     2000 // Min execution time in milliseconds
   * ).thenAccept(() -> {
   *     // Handle the result
   *     System.out.println("Task completed without result.");
   * }).exceptionally(e -> {
   *     // Handle any exceptions thrown by the task
   *     e.printStackTrace();
   *     return null;
   * });
   * </code></pre>
   *
   * @param <T>
   *     任务产生的结果的类型。
   * @param task
   *     要执行的任务。这是一个没有返回值的{@link Runnable}。
   * @param minExecutionTime
   *     任务的指定最短执行时间，以毫秒为单位。如果任务执行时间小于指定的最短执行时间，
   *     则延迟时间将是最短执行时间与任务执行时间之差。否则，延迟将为零。
   * @return
   *     一个{@link CompletableFuture}，它将在指定的延迟后完成并返回任务的结果。
   *     如果任务抛出异常，则{@link CompletableFuture}将以该异常的方式完成。
   * @deprecated 请改用 {@link Delay#execute(Runnable, long)}。
   */
  @Deprecated
  public static <T> CompletableFuture<T> executeWithDelay(final Runnable task,
      final long minExecutionTime) {
    return executeWithDelay(Executors.callable(task, null), minExecutionTime);
  }

}