////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.Writer;

import javax.annotation.concurrent.Immutable;

/**
 * 一个将所有数据写入到著名的 <b>/dev/null</b> 的 {@link Writer}。
 *
 * <p>这个 {@code Writer} 没有目标（文件/套接字等），所有写入的字符都被忽略和丢弃。
 *
 * @author 胡海星
 */
@Immutable
public final class NullWriter extends Writer {

  /**
   * NullWriter 的单例实例。
   */
  public static final NullWriter INSTANCE = new NullWriter();

  /**
   * 限制性构造函数。
   */
  private NullWriter() {
    // empty
  }

  /**
   * 什么都不做 - 输出到 {@code /dev/null}。
   *
   * @param idx
   *          要写入的字符
   */
  @Override
  public void write(final int idx) {
    // to /dev/null
  }

  /**
   * 什么都不做 - 输出到 {@code /dev/null}。
   *
   * @param chr
   *          要写入的字符数组
   */
  @Override
  public void write(final char[] chr) {
    // to /dev/null
  }

  /**
   * 什么都不做 - 输出到 {@code /dev/null}。
   *
   * @param chr
   *          要写入的字符数组
   * @param st
   *          起始偏移量
   * @param end
   *          要写入的字符数
   */
  @Override
  public void write(final char[] chr, final int st, final int end) {
    // to /dev/null
  }

  /**
   * 什么都不做 - 输出到 {@code /dev/null}。
   *
   * @param str
   *          要写入的字符串
   */
  @Override
  public void write(final String str) {
    // to /dev/null
  }

  /**
   * 什么都不做 - 输出到 {@code /dev/null}。
   *
   * @param str
   *          要写入的字符串
   * @param st
   *          起始偏移量
   * @param end
   *          要写入的字符数
   */
  @Override
  public void write(final String str, final int st, final int end) {
    // to /dev/null
  }

  /**
   *  {@inheritDoc}
   */
  @Override
  public void flush() {
    // to /dev/null
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {
    // to /dev/null
  }

}