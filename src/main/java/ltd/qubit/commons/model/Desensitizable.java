////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import ltd.qubit.commons.lang.CloneableEx;

/**
 * This interface indicates that the entity class can perform desensitization operations.
 *
 * @author Haixing Hu
 */
public interface Desensitizable<T> extends CloneableEx<T> {

  T clone();

  /**
   * Desensitize this object.
   */
  void desensitize();

  /**
   * Gets a desensitized clone of this object.
   *
   * @return
   *     Desensitized clone of this subject.
   */
  @SuppressWarnings("unchecked")
  default T desensitizedClone() {
    final T result = this.clone();
    ((Desensitizable<T>) result).desensitize();
    return result;
  }
}
