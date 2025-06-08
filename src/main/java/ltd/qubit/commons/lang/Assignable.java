////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * This interface impose a same type assignment operations of a class T.
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
   *     the other object, which is of the same class as this object.
   */
  void assign(T other);
}