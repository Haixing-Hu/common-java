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
import java.nio.InvalidMarkException;

/**
 * {@link AbstractSeekableInputStream} 是实现 {@link SeekableInputStream} 接口的抽象基类。
 *
 * @author 胡海星
 */
public abstract class AbstractSeekableInputStream extends SeekableInputStream {

  protected long markPos;
  protected int markLimit;

  /**
   * 构造一个表示抽象的 seekable 输入流。
   */
  protected AbstractSeekableInputStream() {
    markPos = -1;
    markLimit = -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int available() throws IOException {
    final long result = length() - position();
    if (result > Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    } else {
      return (int) result;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long skip(final long n) throws IOException {
    final long len = length();
    final long pos = position();
    long skipped = len - pos;
    if (skipped > n) {
      skipped = n;
    }
    final long newpos = pos + skipped;
    seek(newpos);
    if (markPos >= 0) {
      if ((newpos < markPos) || (markPos < (newpos - markLimit))) {
        //  invalid the mark
        markPos = -1;
        markLimit = -1;
      }
    }
    return skipped;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean markSupported() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mark(final int readLimit) {
    try {
      markPos = position();
      markLimit = readLimit;
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reset() throws IOException {
    if (markPos < 0) {
      throw new InvalidMarkException();
    }
    final long pos = position();
    if ((markPos + markLimit) < pos) {
      markPos = -1;
      throw new InvalidMarkException();
    }
    seek(markPos);
  }
}
