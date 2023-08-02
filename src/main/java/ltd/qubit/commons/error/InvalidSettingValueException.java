////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * Thrown to indicates the value or values of an name is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidSettingValueException extends RuntimeException {

  private static final long serialVersionUID = -2108303826996770048L;

  private final String name;
  private final String value;

  public InvalidSettingValueException(final String name, final String value) {
    super("The value of the setting '" + name + "' is invalid: " + value);
    this.name = name;
    this.value = value;
  }

  /**
   * Gets the name of the setting.
   *
   * @return the name of the setting.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the value of the setting.
   *
   * @return the value of the setting.
   */
  public String getValue() {
    return value;
  }

}
