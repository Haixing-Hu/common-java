////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * A wrap of another input stream which limit the number of bytes could be read
 * from the underlying input stream.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class ReadLimitInputStream extends FilterInputStream {

  private final int limit;
  private int count;
  private int markedCount;

  public ReadLimitInputStream(final InputStream in, final int limit) {
    super(in);
    this.limit = limit;
    this.count = 0;
    this.markedCount = 0;
  }

  @Override
  public int read() throws IOException {
    int result = -1;
    if (count < limit) {
      result = in.read();
      ++count;
    }
    return result;
  }

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

  @Override
  public int available() throws IOException {
    if (count >= limit) {
      return 0;
    }
    final int result = in.available();
    return Math.min(result, limit - count);
  }

  @Override
  public void close() throws IOException {
    in.close();
  }

  @Override
  public synchronized void mark(final int readlimit) {
    in.mark(readlimit);
    markedCount = count;
  }

  @Override
  public synchronized void reset() throws IOException {
    in.reset();
    count = markedCount;
  }

  @Override
  public boolean markSupported() {
    return in.markSupported();
  }

}
