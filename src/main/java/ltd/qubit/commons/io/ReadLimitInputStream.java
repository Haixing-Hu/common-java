////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * 另一个输入流的包装器，限制可以从底层输入流读取的字节数。
 *
 * @author 胡海星
 */
@NotThreadSafe
public class ReadLimitInputStream extends FilterInputStream {

  private final int limit;
  private int count;
  private int markedCount;

  /**
   * 构造一个限制读取字节数的输入流。
   *
   * @param in 底层的输入流。
   * @param limit 最大可读取的字节数。
   */
  public ReadLimitInputStream(final InputStream in, final int limit) {
    super(in);
    this.limit = limit;
    this.count = 0;
    this.markedCount = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException {
    int result = -1;
    if (count < limit) {
      result = in.read();
      ++count;
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(final byte[] buffer) throws IOException {
    if (count >= limit) {
      return -1;
    }
    final int length = Math.min(buffer.length, limit - count);
    final int bytesRead = in.read(buffer, 0, length);
    if (bytesRead > 0) {
      count += bytesRead;
    }
    return bytesRead;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(final byte[] buffer, final int off, final int length)
      throws IOException {
    if (count >= limit) {
      return -1;
    }
    final int len = Math.min(length, limit - count);
    final int bytesRead = in.read(buffer, off, len);
    if (bytesRead > 0) {
      count += bytesRead;
    }
    return bytesRead;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long skip(final long n) throws IOException {
    if (count >= limit) {
      return 0;
    }
    long result = Math.min(n, (limit - count));
    result = in.skip(result);
    count += result;
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int available() throws IOException {
    if (count >= limit) {
      return 0;
    }
    final int result = in.available();
    return Math.min(result, limit - count);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {
    in.close();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void mark(final int readlimit) {
    in.mark(readlimit);
    markedCount = count;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void reset() throws IOException {
    in.reset();
    count = markedCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean markSupported() {
    return in.markSupported();
  }

}