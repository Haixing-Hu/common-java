////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.retry;

import java.io.Serial;
import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeExecutor;
import dev.failsafe.RetryPolicy;

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
public class RetryBuilder implements RetryOptions {

  @Serial
  private static final long serialVersionUID = -8559936643904509389L;

  private Logger logger;

  private RetryOptions options;

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
    this(logger, new DefaultRetryOptions(config));
  }

  /**
   * Constructs a retry builder.
   *
   * @param logger
   *     the logger used by the retry builder.
   * @param options
   *     the retry options used by the retry builder.
   */
  public RetryBuilder(final Logger logger, final RetryOptions options) {
    this.logger = requireNonNull("logger", logger);
    this.options = requireNonNull("options", options);
  }

  public Logger getLogger() {
    return logger;
  }

  public RetryBuilder setLogger(final Logger logger) {
    this.logger = requireNonNull("logger", logger);
    return this;
  }

  public RetryOptions getOptions() {
    return options;
  }

  public RetryBuilder setOptions(final RetryOptions options) {
    this.options = options;
    return this;
  }

  @Override
  public int getMaxRetryAttempts() {
    return options.getMaxRetryAttempts();
  }

  @Override
  public RetryBuilder setMaxRetryAttempts(final int maxRetryAttempts) {
    options.setMaxRetryAttempts(maxRetryAttempts);
    return this;
  }

  @Override
  public int getMinRetryDelay() {
    return options.getMinRetryDelay();
  }

  @Override
  public RetryBuilder setMinRetryDelay(final int minRetryDelay) {
    options.setMinRetryDelay(minRetryDelay);
    return this;
  }

  @Override
  public int getMaxRetryDelay() {
    return options.getMaxRetryDelay();
  }

  @Override
  public RetryBuilder setMaxRetryDelay(final int maxRetryDelay) {
    options.setMaxRetryDelay(maxRetryDelay);
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
        .withMaxAttempts(getMaxRetryAttempts())
        .withBackoff(Duration.ofSeconds(getMinRetryDelay()), Duration.ofSeconds(getMaxRetryDelay()))
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