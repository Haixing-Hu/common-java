////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql.error;

import java.io.Serial;
import java.sql.SQLException;

/**
 * 当遇到不可映射的 Java 类型时抛出此异常。
 *
 * @author 胡海星
 */
public class UnsupportedJavaTypeException extends SQLException {

  @Serial
  private static final long serialVersionUID = 3385604018895539464L;

  /**
   * 使用指定的 Java 类型创建一个新的 {@code UnsupportedJavaTypeException} 实例。
   *
   * @param javaType
   *      不支持的 Java 类型
   */
  public UnsupportedJavaTypeException(final Class<?> javaType) {
    super("The Java type " + javaType.getName()
        + " is not supported.");
  }

}