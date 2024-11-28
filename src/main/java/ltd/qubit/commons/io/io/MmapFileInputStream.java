////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.io.io.error.AlreadyClosedException;
import ltd.qubit.commons.io.io.error.InvalidSeekPositionException;
import ltd.qubit.commons.lang.SystemUtils;

/**
 * A {@link SeekableInputStream} to read data from a file using a mmap.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class MmapFileInputStream extends SeekableInputStream {

  private ByteBuffer buffer;

  public MmapFileInputStream(final File file) throws IOException {
    final RandomAccessFile raf = new RandomAccessFile(file, "r");
    try {
      buffer = raf.getChannel().map(MapMode.READ_ONLY, 0, raf.length());
    } finally {
      raf.close();
    }
  }

  @Override
  public int available() {
    return buffer.remaining();
  }

  @Override
  public int read() throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    if (buffer.position() < buffer.limit()) {
      return buffer.get();
    } else {
      return -1;
    }
  }

  @Override
  public int read(final byte[] buf, final int off, final int len)
      throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    int count = buffer.remaining();
    if (count == 0) {
      return -1;
    } else {
      if (len < count) {
        count = len;
      }
      buffer.get(buf, off, count);
      return count;
    }
  }

  @Override
  public long length() throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    return buffer.limit();
  }

  @Override
  public long position() throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    return buffer.position();
  }

  @Override
  public void seek(final long pos) throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    if (pos > buffer.limit()) {
      throw new InvalidSeekPositionException(pos);
    }
    buffer.position((int) pos);
  }

  @Override
  public void close() throws IOException {
    if (buffer != null) {
      try {
        SystemUtils.cleanupMmapping(buffer);
      } finally {
        buffer = null;
      }
    }
  }

}
