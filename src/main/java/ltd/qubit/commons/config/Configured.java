////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.config.impl.DefaultConfig;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A base class implements the Configurable interface.
 *
 * <p>The subclass of this class could override the {@link #onConfigChanged}
 * method, in order to do some additional operations after the configuration was
 * changed.
 *
 * <p>NOTE: This class does not implement a constructor which accept a {@link
 * Config} as argument, since if it does, the {@link #onConfigChanged()} will be
 * called in the constructor, while the subclass may not have been
 * constructed.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class Configured implements Configurable {

  protected Config config;

  public Configured() {
    config = new DefaultConfig();
  }

  @Override
  public final Config getConfig() {
    return config;
  }

  @Override
  public final void setConfig(final Config config) {
    this.config = requireNonNull("config", config);
    onConfigChanged();
  }

  /**
   * This function is called at the last of the functions
   * {@link #setConfig(Config)}.
   *
   * <p>The subclass of {@link Configured} may override this function in order
   * to provide additional operations after the configuration is changed.
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
