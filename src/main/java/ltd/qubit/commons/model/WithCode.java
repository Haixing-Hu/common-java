////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * This interface represents an entity class with code properties.
 *
 * @author Haixing Hu
 */
public interface WithCode {

  /**
   * Get the code of the current object.
   *
   * @return
   *     The code of the current object.
   */
  String getCode();

  /**
   * Sets the code of the current object.
   *
   * @param code
   *     The new code to be set.
   */
  void setCode(String code);
}
