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

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.config.WritableConfig;
import ltd.qubit.commons.config.WritableConfigurable;
import ltd.qubit.commons.config.impl.DefaultConfig;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A default implementation of {@link RetryOptions} interface that uses a
 * {@link WritableConfig} to store all configuration values.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class DefaultRetryOptions implements RetryOptions, WritableConfigurable {

  @Serial
  private static final long serialVersionUID = 7533987052518912462L;

  private WritableConfig config;

  /**
   * Creates a new instance of {@link DefaultRetryOptions} with a default
   * configuration.
   */
  public DefaultRetryOptions() {
    this(new DefaultConfig());
  }

  /**
   * Creates a new instance of {@link DefaultRetryOptions} with the specified
   * configuration.
   *
   * @param config
   *     the configuration to use, must not be {@code null}.
   */
  public DefaultRetryOptions(final WritableConfig config) {
    this.config = requireNonNull("config", config);
  }

  @Override
  public WritableConfig getConfig() {
    return config;
  }

  @Override
  public DefaultRetryOptions setConfig(final WritableConfig config) {
    this.config = requireNonNull("config", config);
    return this;
  }

  @Override
  public int getMaxRetryAttempts() {
    return config.getInt(KEY_MAX_RETRY_ATTEMPTS, DEFAULT_MAX_RETRY_ATTEMPTS);
  }

  @Override
  public RetryOptions setMaxRetryAttempts(final int maxRetryAttempts) {
    config.setInt(KEY_MAX_RETRY_ATTEMPTS, maxRetryAttempts);
    return this;
  }

  @Override
  public int getMinRetryDelay() {
    return config.getInt(KEY_MIN_RETRY_DELAY, DEFAULT_MIN_RETRY_DELAY);
  }

  @Override
  public RetryOptions setMinRetryDelay(final int minRetryDelay) {
    config.setInt(KEY_MIN_RETRY_DELAY, minRetryDelay);
    return this;
  }

  @Override
  public int getMaxRetryDelay() {
    return config.getInt(KEY_MAX_RETRY_DELAY, DEFAULT_MAX_RETRY_DELAY);
  }

  @Override
  public RetryOptions setMaxRetryDelay(final int maxRetryDelay) {
    config.setInt(KEY_MAX_RETRY_DELAY, maxRetryDelay);
    return this;
  }
}