////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.OutputStream;

import javax.annotation.concurrent.Immutable;

/**
 * {@code NullOutputStream} 对象将所有数据写入著名的 <b>/dev/null</b>。
 *
 * <p>此输出流没有目标（文件/套接字等），所有写入它的字节都将被忽略和丢失。</p>
 *
 * @author 胡海星
 */
@Immutable
public final class NullOutputStream extends OutputStream {

  /**
   * NullOutputStream 的单例实例。
   */
  public static final NullOutputStream INSTANCE = new NullOutputStream();

  /**
   * 限制性构造函数。
   */
  private NullOutputStream() {
    // empty
  }

  /**
   * 什么都不做 - 输出到 {@code /dev/null}。
   *
   * @param b
   *          要写入的字节。
   * @param off
   *          起始偏移量。
   * @param len
   *          要写入的字节数。
   */
  @Override
  public void write(final byte[] b, final int off, final int len) {
    // to /dev/null
  }

  /**
   * 什么都不做 - 输出到 {@code /dev/null}。
   *
   * @param b
   *          要写入的字节。
   */
  @Override
  public void write(final int b) {
    // to /dev/null
  }

  /**
   * 什么都不做 - 输出到 {@code /dev/null}。
   *
   * @param b
   *          要写入的字节。
   */
  @Override
  public void write(final byte[] b) {
    // to /dev/null
  }
}