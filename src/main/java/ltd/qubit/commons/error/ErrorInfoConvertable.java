////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * The interface which provide a method to convert this object to an
 * {@link ErrorInfo} object.
 *
 * @author Haixing Hu
 */
public interface ErrorInfoConvertable {

  /**
   * Converts this object to an {@link ErrorInfo} object.
   *
   * @return
   *    an {@link ErrorInfo} object converted from this object.
   */
  ErrorInfo toErrorInfo();
}
