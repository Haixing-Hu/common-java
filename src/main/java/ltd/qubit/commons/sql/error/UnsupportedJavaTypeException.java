////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql.error;

import java.sql.SQLException;

/**
 * Thrown to indicate an un-mappable java type.
 *
 * @author Haixing Hu
 */
public class UnsupportedJavaTypeException extends SQLException {

  private static final long serialVersionUID = 3385604018895539464L;

  public UnsupportedJavaTypeException(final Class<?> javaType) {
    super("The Java type " + javaType.getName()
        + " is not supported.");
  }

}