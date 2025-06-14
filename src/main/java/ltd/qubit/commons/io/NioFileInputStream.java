////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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

import ltd.qubit.commons.io.error.AlreadyClosedException;

/**
 * A {@link SeekableInputStream} which reads data from a file using the
 * {@code java.nio} APIs.
 *
 * @author Haixing Hu
 */
public class NioFileInputStream extends SeekableInputStream {

  public static final int DEFAULT_BUFFER_SIZE = 8192;

  private RandomAccessFile descriptor;
  private FileChannel channel;
  private ByteBuffer buffer;
  private long offset;
  private long length;

  public NioFileInputStream(final File file) throws IOException {
    this(file, DEFAULT_BUFFER_SIZE);
  }

  public NioFileInputStream(final File file, final int bufferSize)
      throws IOException {
    descriptor = new RandomAccessFile(file, "r");
    channel = descriptor.getChannel();
    buffer = ByteBuffer.allocate(bufferSize);
    offset = 0;
    length = descriptor.length();
  }

  @Override
  public int read() throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    if (! buffer.hasRemaining()) {
      if (! fillBuffer()) {
        return - 1; // EOF
      }
    }
    return (buffer.get() & 0xFF);
  }

  @Override
  public int read(final byte[] buf, final int offset, final int length)
      throws IOException {
    if ((offset < 0) || (length < 0) || (offset > buf.length - length)) {
      throw new IndexOutOfBoundsException();
    }
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    if (length == 0) {
      return 0;
    }
    final int available = buffer.remaining();
    if (length <= available) {
      // the buffer contains enough data to satisfy this request
      buffer.get(buf, offset, length);
      return length;
    }
    // the buffer does not have enough data.
    assert (length > 0);
    int count = 0;
    int len = length;
    int off = offset;
    // First copy all bytes remained in the buffer
    if (available > 0) {
      buffer.get(buf, off, available);
      off += available;
      len -= available;
      count = available;
    }
    // and now, try to read the remaining 'len' bytes:
    if (len < buffer.capacity()) {
      // If the amount left to read is small enough, and
      // we are allowed to use our buffer, do it in the usual
      // buffered way: fill the buffer once and copy from it:
      if (! fillBuffer()) {
        // EOF has encountered
        return (count == 0 ? - 1 : count);
      }
      int n = buffer.remaining();
      if (n > len) {
        n = len;
      }
      buffer.get(buf, off, n);
      return count + n;
    } else { // len >= buffer.length
      // The amount left to read is larger than the buffer
      // or we've been asked to not use our buffer -
      // there's no performance reason not to read it all
      // at once. Note that unlike the previous code of
      // this function, there is no need to do a seek
      // here, because there's no need to reread what we
      // had in the buffer.
      assert (! buffer.hasRemaining()); // the buffer should be empty
      final ByteBuffer tempBuffer = ByteBuffer.wrap(buf, off, len);
      final int n = channel.read(tempBuffer);
      if (n < 0) { // EOF
        return (count == 0 ? - 1 : count);
      } else {
        // fix the start offset of the next buffer
        this.offset += (buffer.position() + n);
        buffer.position(0);
        buffer.limit(0);
        return count + n;
      }
    }
  }

  private boolean fillBuffer() throws IOException {
    // fix the start offset of the buffer
    offset += buffer.position();
    // next fill the buffer
    buffer.clear();
    int n;
    do {
      n = channel.read(buffer);
    } while (n == 0);
    buffer.flip();
    return (n > 0);
  }

  @Override
  public long length() {
    return length;
  }

  @Override
  public long position() throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    return offset + buffer.position();
  }

  @Override
  public void seek(final long newPos) throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    if ((newPos >= offset) && (newPos < (offset + buffer.limit()))) {
      // seek within the buffer
      buffer.position((int) (newPos - offset));
    } else {
      // seek the channel to the new position
      channel.position(newPos);
      // abandon the current data in buffer
      offset = newPos;
      buffer.position(0);
      buffer.limit(0);
    }
  }

  @Override
  public void close() throws IOException {
    if (descriptor != null) {
      try {
        descriptor.close();
      } finally {
        buffer = null;
        channel = null;
        descriptor = null;
        length = 0;
      }
    }
  }

}