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

import ltd.qubit.commons.config.WritableConfig;
import ltd.qubit.commons.config.impl.DefaultConfig;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 重试执行器的构建器。
 * <p>
 * <b>注意:</b> 此类<b>不是</b>线程安全的。但由此类构建的重试执行器是线程安全的。
 *
 * @author 胡海星
 */
public class RetryBuilder implements RetryOptions {

  @Serial
  private static final long serialVersionUID = -8559936643904509389L;

  private Logger logger;

  private RetryOptions options;

  /**
   * 构造一个重试构建器。
   */
  public RetryBuilder() {
    this(LoggerFactory.getLogger(RetryBuilder.class), new DefaultConfig());
  }

  /**
   * 构造一个重试构建器。
   *
   * @param logger
   *     重试构建器使用的记录器。
   */
  public RetryBuilder(final Logger logger) {
    this(logger, new DefaultConfig());
  }

  /**
   * 构造一个重试构建器。
   *
   * @param logger
   *     重试构建器使用的记录器。
   * @param config
   *     重试构建器使用的配置。
   */
  public RetryBuilder(final Logger logger, final WritableConfig config) {
    this(logger, new DefaultRetryOptions(config));
  }

  /**
   * 构造一个重试构建器。
   *
   * @param logger
   *     重试构建器使用的记录器。
   * @param options
   *     重试构建器使用的重试选项。
   */
  public RetryBuilder(final Logger logger, final RetryOptions options) {
    this.logger = requireNonNull("logger", logger);
    this.options = requireNonNull("options", options);
  }

  /**
   * 获取此构建器使用的记录器。
   *
   * @return 此构建器使用的记录器。
   */
  public Logger getLogger() {
    return logger;
  }

  /**
   * 设置此构建器使用的记录器。
   *
   * @param logger
   *     新的记录器。
   * @return 此构建器。
   */
  public RetryBuilder setLogger(final Logger logger) {
    this.logger = requireNonNull("logger", logger);
    return this;
  }

  /**
   * 获取此构建器使用的重试选项。
   *
   * @return 此构建器使用的重试选项。
   */
  public RetryOptions getOptions() {
    return options;
  }

  /**
   * 设置此构建器使用的重试选项。
   *
   * @param options
   *     新的重试选项。
   * @return 此构建器。
   */
  public RetryBuilder setOptions(final RetryOptions options) {
    this.options = requireNonNull("options", options);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaxRetryAttempts() {
    return options.getMaxRetryAttempts();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RetryBuilder setMaxRetryAttempts(final int maxRetryAttempts) {
    options.setMaxRetryAttempts(maxRetryAttempts);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMinRetryDelay() {
    return options.getMinRetryDelay();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RetryBuilder setMinRetryDelay(final int minRetryDelay) {
    options.setMinRetryDelay(minRetryDelay);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaxRetryDelay() {
    return options.getMaxRetryDelay();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RetryBuilder setMaxRetryDelay(final int maxRetryDelay) {
    options.setMaxRetryDelay(maxRetryDelay);
    return this;
  }

  /**
   * 为指定的异常构建重试执行器。
   *
   * @param <T>
   *     操作结果的类型。
   * @param exceptions
   *     应重试的异常。
   * @return
   *     重试执行器。
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
   * 为指定的异常构建重试执行器。
   *
   * @param <T>
   *     操作结果的类型。
   * @param exceptions
   *     应重试的异常。
   * @return
   *     重试执行器。
   */
  @SuppressWarnings("unchecked")
  public <T> FailsafeExecutor<T> build(final Class<? extends Throwable> ... exceptions) {
    return build(List.of(exceptions));
  }

  /**
   * 为所有异常构建重试执行器。
   *
   * @param <T>
   *     操作结果的类型。
   * @return
   *     重试执行器。
   */
  public <T> FailsafeExecutor<T> build() {
    return build(List.of(Exception.class));
  }
}