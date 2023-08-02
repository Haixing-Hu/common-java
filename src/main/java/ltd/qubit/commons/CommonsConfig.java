////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons;

import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.config.ConfigUtils;

/**
 * Provides functions to get the configuration of the commons module and defines
 * the names and default values of properties.
 *
 * @author Haixing Hu
 */
public final class CommonsConfig {

  /**
   * The system property name for the XML resource of the configuration of commons module.
   */
  public static final String PROPERTY_RESOURCE = "CommonsConfig";

  /**
   * The default name of XML resource of the configuration of commons module.
   */
  public static final String DEFAULT_RESOURCE = "common-java.xml";

  /**
   * The static {@link Config} object.
   */
  private static volatile Config config = null;

  /**
   * Gets the configuration of the commons module.
   *
   * <p>The function will first try to search in the system's properties to find
   * the XML resource name of the configuration, if no such system properties
   * exists, it will use the default XML resource. Then it will try to load
   * the configuration from the XML file, and return the configuration if
   * success, or return an empty configuration if failed.
   *
   * @return the configuration of the commons module, or an empty configuration
   *         if failed.
   */
  public static Config get() {
    // use the double checked locking
    if (config == null) {
      synchronized (CommonsConfig.class) {
        if (config == null) {
          config = ConfigUtils.loadXmlConfig(PROPERTY_RESOURCE,
              DEFAULT_RESOURCE, CommonsConfig.class);
        }
      }
    }
    return config;
  }
}
