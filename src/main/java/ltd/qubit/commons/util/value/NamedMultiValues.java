////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.value;

/**
 * A {@link NamedMultiValues} is an {@link MultiValues} with a name.
 *
 * @author Haixing Hu
 */
public interface NamedMultiValues extends MultiValues {

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
