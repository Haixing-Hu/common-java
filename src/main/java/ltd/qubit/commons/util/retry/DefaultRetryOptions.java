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
 * {@link RetryOptions} 接口的默认实现,它使用{@link WritableConfig}来存储所有配置值。
 *
 * @author 胡海星
 */
@ThreadSafe
public class DefaultRetryOptions implements RetryOptions, WritableConfigurable {

  @Serial
  private static final long serialVersionUID = 7533987052518912462L;

  private WritableConfig config;

  /**
   * 使用默认配置创建一个新的 {@link DefaultRetryOptions} 实例。
   */
  public DefaultRetryOptions() {
    this(new DefaultConfig());
  }

  /**
   * 使用指定的配置创建一个新的 {@link DefaultRetryOptions} 实例。
   *
   * @param config
   *     要使用的配置,不得为{@code null}。
   */
  public DefaultRetryOptions(final WritableConfig config) {
    this.config = requireNonNull("config", config);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WritableConfig getConfig() {
    return config;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultRetryOptions setConfig(final WritableConfig config) {
    this.config = requireNonNull("config", config);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaxRetryAttempts() {
    return config.getInt(KEY_MAX_RETRY_ATTEMPTS, DEFAULT_MAX_RETRY_ATTEMPTS);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RetryOptions setMaxRetryAttempts(final int maxRetryAttempts) {
    config.setInt(KEY_MAX_RETRY_ATTEMPTS, maxRetryAttempts);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMinRetryDelay() {
    return config.getInt(KEY_MIN_RETRY_DELAY, DEFAULT_MIN_RETRY_DELAY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RetryOptions setMinRetryDelay(final int minRetryDelay) {
    config.setInt(KEY_MIN_RETRY_DELAY, minRetryDelay);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaxRetryDelay() {
    return config.getInt(KEY_MAX_RETRY_DELAY, DEFAULT_MAX_RETRY_DELAY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RetryOptions setMaxRetryDelay(final int maxRetryDelay) {
    config.setInt(KEY_MAX_RETRY_DELAY, maxRetryDelay);
    return this;
  }
}