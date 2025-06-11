////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.IOException;
import java.io.Serial;

/**
 * 此异常表示源对象和目标对象是同一个I/O操作对象.
 *
 * @author 胡海星
 */
public class SameSourceDestinationException extends IOException {

  @Serial
  private static final long serialVersionUID = - 840013128808391562L;

  /**
   * 构造一个{@code SameSourceDestinationException}对象.
   */
  public SameSourceDestinationException() {
    super("The source and destination are the same file or directory. ");
  }

  /**
   * 构造一个{@code SameSourceDestinationException}对象.
   *
   * @param path
   *     作为相同源和目标的路径.
   */
  public SameSourceDestinationException(final String path) {
    super("The source and destination are the same file or directory: " + path);
  }
}