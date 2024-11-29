////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.IOException;
import java.nio.InvalidMarkException;

/**
 * The {@link AbstractSeekableInputStream} is an abstract base class for
 * implementing the {@link SeekableInputStream} interface.
 *
 * @author Haixing Hu
 */
public abstract class AbstractSeekableInputStream extends SeekableInputStream {

  protected long markPos;
  protected int markLimit;

  protected AbstractSeekableInputStream() {
    markPos = -1;
    markLimit = -1;
  }

  @Override
  public int available() throws IOException {
    final long result = length() - position();
    if (result > Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    } else {
      return (int) result;
    }
  }

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

  @Override
  public boolean markSupported() {
    return true;
  }

  @Override
  public void mark(final int readLimit) {
    try {
      markPos = position();
      markLimit = readLimit;
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

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
