////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.io.Serial;
import java.io.Serializable;

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
public class DefaultRetryOptions implements RetryOptions, WritableConfigurable, Serializable {

  @Serial
  private static final long serialVersionUID = -2754856329120638742L;

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
  public void setConfig(final WritableConfig config) {
    this.config = requireNonNull("config", config);
  }

  @Override
  public int getMaxAttempts() {
    return config.getInt(KEY_MAX_ATTEMPTS, DEFAULT_MAX_ATTEMPTS);
  }

  @Override
  public RetryOptions setMaxAttempts(final int maxAttempts) {
    config.setInt(KEY_MAX_ATTEMPTS, maxAttempts);
    return this;
  }

  @Override
  public int getRetryMinDelay() {
    return config.getInt(KEY_RETRY_MIN_DELAY, DEFAULT_RETRY_MIN_DELAY);
  }

  @Override
  public RetryOptions setRetryMinDelay(final int retryMinDelay) {
    config.setInt(KEY_RETRY_MIN_DELAY, retryMinDelay);
    return this;
  }

  @Override
  public int getRetryMaxDelay() {
    return config.getInt(KEY_RETRY_MAX_DELAY, DEFAULT_RETRY_MAX_DELAY);
  }

  @Override
  public RetryOptions setRetryMaxDelay(final int retryMaxDelay) {
    config.setInt(KEY_RETRY_MAX_DELAY, retryMaxDelay);
    return this;
  }
}