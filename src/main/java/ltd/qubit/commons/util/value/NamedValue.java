////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.value;

/**
 * A {@link NamedMultiValues} is an {@link Value} with a name.
 *
 * @author Haixing Hu
 */
public interface NamedValue extends Value {

  /**
   * Gets the name of this object.
   *
   * @return the name of this object, which can not be null.
   */
  String getName();

  /**
   * Sets the name of this object.
   *
   * @param name
   *          the new name to be set to this object, which can not be null.
   */
  void setName(String name);
}
