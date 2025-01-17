////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.error;

/**
 * Thrown to indicate an error in the XML configuration.
 *
 * @author Haixing Hu
 */
public class XmlConfigurationError extends ConfigurationError {

  private static final long serialVersionUID = -1685548290362488987L;

  public XmlConfigurationError(final String resource, final Throwable cause) {
    super("An error occurs while loading the XML configuration "
        + resource + ": " + cause.toString());
  }

}
