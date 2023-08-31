////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import javax.annotation.Nullable;

/**
 * This interface represents entity classes with token properties.
 *
 * @author Haixing Hu
 */
public interface WithToken {

  /**
   * Get the token of the current object.
   *
   * @return
   *     The token of the current object, which may be {@code null}.
   */
  @Nullable
  Token getToken();

  /**
   * Sets the token of the current object.
   *
   * @param token
   *     The new token to be set, can be {@code null}.
   */
  void setToken(@Nullable Token token);
}
