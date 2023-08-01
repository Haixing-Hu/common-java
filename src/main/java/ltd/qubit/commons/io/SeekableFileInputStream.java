////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import ltd.qubit.commons.io.error.AlreadyClosedException;
import ltd.qubit.commons.lang.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * A {@link SeekableInputStream} which reads data from a file.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class SeekableFileInputStream extends SeekableInputStream {

  private static final int JVM_32BIT_READ_LIMIT = 64 * 1024 * 1024;

  private RandomAccessFile in;

  public SeekableFileInputStream(final File file) throws IOException {
    in = new RandomAccessFile(file, "r");
  }

  @Override
  public int read() throws IOException {
    if (in == null) {
      throw new AlreadyClosedException();
    }
    return in.read();
  }

  @Override
  public int read(final byte[] buf, final int off, final int len) throws IOException {
    if (in == null) {
      throw new AlreadyClosedException();
    }
    // note that in 32-bit JVM an OutOfMemoryException may be thrown if
    // the length to be read is too large, which is a known bug of 32-bit JVM.
    // But in 64-bit JVM it's safe to read directly.
    int n = len;
    if (SystemUtils.IS_JAVA_32BIT && (n > JVM_32BIT_READ_LIMIT)) {
      // for 32-bit JVM, read at most JVM_32BIT_READ_LIMIT bytes.
      n = JVM_32BIT_READ_LIMIT;
    }
    return in.read(buf, off, n);
  }

  @Override
  public long length() throws IOException {
    return in.length();
  }

  @Override
  public long position() throws IOException {
    return in.getFilePointer();
  }

  @Override
  public void seek(final long pos) throws IOException {
    in.seek(pos);
  }

  @Override
  public void close() throws IOException {
    if (in != null) {
      in.close();
      in = null;
    }
  }
}
