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
 * 此接口为输入流提供随机寻道功能。
 *
 * @author 胡海星
 */
public interface Seekable {

  /**
   * 获取流的长度（以字节为单位）。
   *
   * @return 流的长度（以字节为单位）。
   * @throws IOException
   *           如果发生任何 I/O 错误。
   */
  long length() throws IOException;

  /**
   * 获取流中的当前位置。
   *
   * @return 流中的当前位置。
   * @throws IOException
   *           如果发生任何 I/O 错误。
   */
  long position() throws IOException;

  /**
   * 寻道到指定位置。
   *
   * @param pos
   *          新位置，从文件开头以字节为单位测量，用于设置当前位置。
   * @throws IOException
   *           如果发生任何 I/O 错误。
   */
  void seek(long pos) throws IOException;
}