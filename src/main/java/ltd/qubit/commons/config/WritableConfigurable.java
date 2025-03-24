////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

/**
 * Something that may be configured with a {@link WritableConfig}.
 *
 * @author Haixing Hu
 */
public interface WritableConfigurable {

  /**
   * Gets the configuration used by this object.
   *
   * @return the configuration used by this object. It should never return null.
   */
  WritableConfig getConfig();

  /**
   * Sets the configuration to be used by this object.
   *
   * @param config
   *     the configuration to be set to this object. It can't be null.
   */
  void setConfig(WritableConfig config);

}