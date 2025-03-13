////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeExecutor;
import dev.failsafe.RetryPolicy;

import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.config.impl.DefaultConfig;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A build of retry executors.
 * <p>
 * <b>NOTE:</b> This class is <b>NOT</b> thread-safe. But the retry executors
 * built by this class are thread-safe.
 *
 * @author Haixing Hu
 */
public class RetryBuilder {
  /**
   * The configuration key of the maximum number of attempts.
   * <p>
   * Note that the number of attempts is one more than the number of retries.
   * That is, if the number of attempts is 1, then there is no retry. If the
   * number of attempts is 2, then there is one retry. If the number of attempts
   * is 3, then there are two retries, and so on.
   */
  public static final String KEY_MAX_ATTEMPTS = "retry.max_attempts";

  /**
   * The configuration key of the minimum delay between retries, in seconds.
   */
  public static final String KEY_RETRY_MIN_DELAY = "retry.min_delay";

  /**
   * The configuration key of the maximum delay between retries, in seconds.
   */
  public static final String KEY_RETRY_MAX_DELAY = "retry.max_delay";

  /**
   * The default maximum number of attempts for the retry mechanism.
   */
  public static final int DEFAULT_MAX_ATTEMPTS = 5;

  /**
   * The default minimum interval between retries in seconds.
   */
  public static final int DEFAULT_RETRY_MIN_DELAY = 1;

  /**
   * The default maximum interval between retries in seconds.
   */
  public static final int DEFAULT_RETRY_MAX_DELAY = 60;

  private Logger logger;

  private DefaultConfig config;

  /**
   * Constructs a retry builder.
   */
  public RetryBuilder() {
    this(LoggerFactory.getLogger(RetryBuilder.class), new DefaultConfig());
  }

  /**
   * Constructs a retry builder.
   *
   * @param logger
   *     the logger used by the retry builder.
   */
  public RetryBuilder(final Logger logger) {
    this(logger, new DefaultConfig());
  }

  /**
   * Constructs a retry builder.
   *
   * @param logger
   *     the logger used by the retry builder.
   * @param config
   *     the configuration used by the retry builder.
   */
  public RetryBuilder(final Logger logger, final DefaultConfig config) {
    this.logger = requireNonNull("logger", logger);
    this.config = requireNonNull("config", config);
  }

  public Logger getLogger() {
    return logger;
  }

  public RetryBuilder setLogger(final Logger logger) {
    this.logger = requireNonNull("logger", logger);
    return this;
  }

  public Config getConfig() {
    return config;
  }

  public RetryBuilder setConfig(final DefaultConfig config) {
    this.config = requireNonNull("config", config);
    return this;
  }

  public int getMaxAttempts() {
    return config.getInt(KEY_MAX_ATTEMPTS, DEFAULT_MAX_ATTEMPTS);
  }

  public RetryBuilder setMaxAttempts(final int maxAttempts) {
    config.setInt(KEY_MAX_ATTEMPTS, maxAttempts);
    return this;
  }

  public int getRetryMinDelay() {
    return config.getInt(KEY_RETRY_MIN_DELAY, DEFAULT_RETRY_MIN_DELAY);
  }

  public RetryBuilder setRetryMinDelay(final int retryMinDelay) {
    config.setInt(KEY_RETRY_MIN_DELAY, retryMinDelay);
    return this;
  }

  public int getRetryMaxDelay() {
    return config.getInt(KEY_RETRY_MAX_DELAY, DEFAULT_RETRY_MAX_DELAY);
  }

  public RetryBuilder setRetryMaxDelay(final int retryMaxDelay) {
    config.setInt(KEY_RETRY_MAX_DELAY, retryMaxDelay);
    return this;
  }

  /**
   * Builds a retry executor for the specified exceptions.
   *
   * @param <T>
   *     the type of the result of the operation.
   * @param exceptions
   *     the exceptions that should be retried.
   * @return
   *     the retry executor.
   */
  public <T> FailsafeExecutor<T> build(final List<Class<? extends Throwable>> exceptions) {
    final RetryPolicy<T> policy = RetryPolicy
        .<T>builder()
        .withMaxAttempts(getMaxAttempts())
        .withBackoff(Duration.ofSeconds(getRetryMinDelay()), Duration.ofSeconds(getRetryMaxDelay()))
        .handle(exceptions)
        .onRetry((event) -> {
          final Throwable lastError = event.getLastException();
          logger.error("Failure #{}. Reason: {}. Retrying...", event.getAttemptCount(),
              lastError.getMessage(), lastError);
        })
        .onFailure((event) -> {
          logger.error("Failure {} times. Giving up...", event.getAttemptCount());
        })
        .build();
    return Failsafe.with(policy);
  }

  /**
   * Builds a retry executor for the specified exceptions.
   *
   * @param <T>
   *     the type of the result of the operation.
   * @param exceptions
   *     the exceptions that should be retried.
   * @return
   *     the retry executor.
   */
  @SuppressWarnings("unchecked")
  public <T> FailsafeExecutor<T> build(final Class<? extends Throwable> ... exceptions) {
    return build(List.of(exceptions));
  }

  /**
   * Builds a retry executor for all exceptions.
   *
   * @param <T>
   *     the type of the result of the operation.
   * @return
   *     the retry executor.
   */
  public <T> FailsafeExecutor<T> build() {
    return build(List.of(Exception.class));
  }
}