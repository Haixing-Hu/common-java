////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.config.impl.DefaultConfig;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 实现 Configurable 接口的基类。
 *
 * <p>该类的子类可以覆盖 {@link #onConfigChanged} 方法，以便在配置更改后执行一些额外的操作。
 *
 * <p>注意：此类未实现接受 {@link Config} 作为参数的构造函数，因为如果这样做，
 * {@link #onConfigChanged()} 将在构造函数中被调用，而此时子类可能尚未构造完成。
 *
 * @author 胡海星
 */
@NotThreadSafe
public class Configured implements Configurable {

  /**
   * 配置对象。
   */
  protected Config config;

  /**
   * 构造一个 {@link Configured} 对象。
   */
  public Configured() {
    config = new DefaultConfig();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Config getConfig() {
    return config;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setConfig(final Config config) {
    this.config = requireNonNull("config", config);
    onConfigChanged();
  }

  /**
   * 此函数在 {@link #setConfig(Config)} 函数的最后被调用。
   *
   * <p>{@link Configured} 的子类可以覆盖此函数，以便在配置更改后提供额外的操作。
   */
  protected void onConfigChanged() {
    // do nothing, but it could be override by subclasses.
  }

  @Override
  public int hashCode() {
    return config.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Configured other = (Configured) obj;
    return config.equals(other.config);
  }

}