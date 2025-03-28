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
import ltd.qubit.commons.config.WritableConfig;
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

  private Logger logger;

  private final DefaultRetryOptions options;

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
    this.options = new DefaultRetryOptions(requireNonNull("config", config));
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
    if (options instanceof DefaultRetryOptions) {
      this.options = (DefaultRetryOptions) options;
    } else {
      this.options = new DefaultRetryOptions();
      this.options.setMaxAttempts(options.getMaxAttempts());
      this.options.setRetryMinDelay(options.getRetryMinDelay());
      this.options.setRetryMaxDelay(options.getRetryMaxDelay());
    }
  }

  public Logger getLogger() {
    return logger;
  }

  public RetryBuilder setLogger(final Logger logger) {
    this.logger = requireNonNull("logger", logger);
    return this;
  }

  public Config getConfig() {
    return options.getConfig();
  }

  public RetryBuilder setConfig(final WritableConfig config) {
    options.setConfig(requireNonNull("config", config));
    return this;
  }

  @Override
  public int getMaxAttempts() {
    return options.getMaxAttempts();
  }

  @Override
  public RetryBuilder setMaxAttempts(final int maxAttempts) {
    options.setMaxAttempts(maxAttempts);
    return this;
  }

  @Override
  public int getRetryMinDelay() {
    return options.getRetryMinDelay();
  }

  @Override
  public RetryBuilder setRetryMinDelay(final int retryMinDelay) {
    options.setRetryMinDelay(retryMinDelay);
    return this;
  }

  @Override
  public int getRetryMaxDelay() {
    return options.getRetryMaxDelay();
  }

  @Override
  public RetryBuilder setRetryMaxDelay(final int retryMaxDelay) {
    options.setRetryMaxDelay(retryMaxDelay);
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