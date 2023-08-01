////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * This interface impose a assignment operations of a class T.
 *
 * @author Haixing Hu
 */
public interface Assignable<T> extends CloneableEx<T> {

  /**
   * Assigns another object to this object.
   *
   * <p>The function will clone all fields of the other object into the fields
   * of this object.
   *
   * @param other
   *          the other object.
   */
  void assign(T other);
}
