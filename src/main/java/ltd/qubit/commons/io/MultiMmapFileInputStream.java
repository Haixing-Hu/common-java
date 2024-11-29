////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.io.error.AlreadyClosedException;
import ltd.qubit.commons.lang.SystemUtils;

import static ltd.qubit.commons.lang.Argument.requirePositive;

/**
 * A {@link SeekableInputStream} to read data from a file using mutiple mmaps.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class MultiMmapFileInputStream extends SeekableInputStream {

  private final int        maxBufferSize;
  private ByteBuffer[]     buffers;
  private final long             length;
  private int              index;
  private ByteBuffer       buffer;       // buffers[current]
  private int              available;    // bufferSizes[current] - buffer.position()

  public MultiMmapFileInputStream(final File file, final int maxBufferSize)
      throws IOException {
    requirePositive("maxBufferSize", maxBufferSize);
    this.maxBufferSize = maxBufferSize;
    final RandomAccessFile raf = new RandomAccessFile(file, "r");
    try {
      length = raf.length();
      if ((length / maxBufferSize) > Integer.MAX_VALUE) {
        throw new IOException("The file is too big for maximum buffer size "
            + maxBufferSize + ": " + file);
      }
      int n = (int) (length / maxBufferSize);
      if (((long) n * maxBufferSize) < length) {
        ++n;
      }
      buffers = new ByteBuffer[n];
      long pos = 0;
      final FileChannel channel = raf.getChannel();
      for (int i = 0; i < n; ++i) {
        int size = maxBufferSize;
        if (length <= (pos + maxBufferSize)) {
          size = (int) (length - pos);
        }
        assert (size > 0);
        buffers[i] = channel.map(MapMode.READ_ONLY, pos, size);
        pos += size;
      }
    } finally {
      raf.close();
    }
    index = 0;
    buffer = buffers[0];
    buffer.position(0);
    available = buffer.remaining();
  }

  @Override
  public int available() throws IOException {
    final long result = length - position();
    if (result > Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    } else {
      return (int) result;
    }
  }

  @Override
  public int read() throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    // Performance might be improved by reading ahead into an array of
    // e.g. 128 bytes and read() from there.
    if (available == 0) {
      ++index;
      if (index >= buffers.length) {
        return -1;    // EOF
      }
      buffer = buffers[index];
      buffer.position(0);
      available = buffer.remaining();
    }
    assert (available > 0);
    --available;
    return buffer.get();
  }

  @Override
  public int read(final byte[] buf, final int offset, final int length)
      throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    if ((available == 0) && (index >= buffers.length)) {
      return -1; // EOF
    }
    int count = 0;
    int len = length;
    int off = offset;
    while (len > available) {
      buffer.get(buf, off, available);
      len -= available;
      off += available;
      count += available;
      available = 0;
      ++index;
      if (index >= buffers.length) { // EOF
        return (count == 0 ? -1 : count);
      }
      buffer = buffers[index];
      buffer.position(0);
      available = buffer.remaining();
    }
    buffer.get(buf, off, len);
    available -= len;
    return count + len;
  }

  @Override
  public long length() {
    return length;
  }

  @Override
  public long position() throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    return ((long) index * maxBufferSize) + buffer.position();
  }

  @Override
  public void seek(final long newPos) throws IOException {
    if (buffer == null) {
      throw new AlreadyClosedException();
    }
    index = (int) (newPos / maxBufferSize);
    buffer = buffers[index];
    final int bufferPos = (int) (newPos - ((long) index * maxBufferSize));
    buffer.position(bufferPos);
    available = buffer.remaining();
  }

  @Override
  public void close() throws IOException {
    if (buffer != null) {
      try {
        for (int i = 0; i < buffers.length; ++i) {
          try {
            SystemUtils.cleanupMmapping(buffers[i]);
          } finally {
            buffers[i] = null;
          }
        }
      } finally {
        buffers = null;
        buffer = null;
      }
    }
  }
}
