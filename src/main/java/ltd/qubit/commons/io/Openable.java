////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.IOException;

/**
 * 可打开对象的接口。
 *
 * @author 胡海星
 */
public interface Openable {

  /**
   * 测试此对象是否已打开。
   *
   * @return 如果此对象已打开则返回 true，否则返回 false。
   */
  boolean isOpened();

  /**
   * 打开此对象。如果此对象已经打开，可能会抛出异常。
   *
   * @throws IOException
   *           如果发生任何 I/O 错误。
   */
  void open() throws IOException;

}