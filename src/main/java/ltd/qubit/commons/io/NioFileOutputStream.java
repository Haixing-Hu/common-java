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
 * 一个使用 {@code java.nio} API 向文件写入数据的 {@link SeekableOutputStream}。
 *
 * @author 胡海星
 */
public class NioFileOutputStream extends SeekableOutputStream {

  /**
   * 默认的缓冲区大小。
   */
  public static final int DEFAULT_BUFFER_SIZE = 8192;

  private RandomAccessFile descriptor;
  private FileChannel channel;
  private ByteBuffer buffer;
  private long offset;

  /**
   * 构造一个使用默认缓冲区大小且不追加模式的 NIO 文件输出流。
   *
   * @param file 要写入的文件
   * @throws IOException 如果发生 I/O 错误
   */
  public NioFileOutputStream(final File file) throws IOException {
    this(file, DEFAULT_BUFFER_SIZE, false);
  }

  /**
   * 构造一个使用默认缓冲区大小的 NIO 文件输出流。
   *
   * @param file 要写入的文件
   * @param append 是否以追加模式打开文件
   * @throws IOException 如果发生 I/O 错误
   */
  public NioFileOutputStream(final File file, final boolean append)
      throws IOException {
    this(file, DEFAULT_BUFFER_SIZE, append);
  }

  /**
   * 构造一个使用指定缓冲区大小且不追加模式的 NIO 文件输出流。
   *
   * @param file 要写入的文件
   * @param bufferSize 缓冲区大小
   * @throws IOException 如果发生 I/O 错误
   */
  public NioFileOutputStream(final File file, final int bufferSize)
      throws IOException {
    this(file, bufferSize, false);
  }

  /**
   * 构造一个使用指定缓冲区大小的 NIO 文件输出流。
   *
   * @param file 要写入的文件
   * @param bufferSize 缓冲区大小
   * @param append 是否以追加模式打开文件
   * @throws IOException 如果发生 I/O 错误
   */
  public NioFileOutputStream(final File file, final int bufferSize,
      final boolean append) throws IOException {
    descriptor = new RandomAccessFile(file, "rws");
    channel = descriptor.getChannel();
    buffer = ByteBuffer.allocate(bufferSize);
    offset = 0;
    if (append) {
      final long len = descriptor.length();
      channel.position(len);
      offset = len;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(final int ch) throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    if (! buffer.hasRemaining()) {
      flush();
    }
    buffer.put((byte) ch);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(final byte[] buf, final int offset, final int length)
      throws IOException {
    if ((offset < 0) || (length < 0) || (length > buf.length - offset)) {
      throw new IndexOutOfBoundsException();
    }
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    if (length == 0) {
      return;
    }
    int room = buffer.remaining();
    // is there enough space in the buffer?
    if (room >= length) {
      // we add the data to the end of the buffer
      buffer.put(buf, offset, length);
      // if the buffer is full, flush it
      if (! buffer.hasRemaining()) {
        flush();
      }
    } else { // room < len
      if (length > buffer.capacity()) {
        // flush the buffer
        flush();
        // and write data directly
        final ByteBuffer tempBuffer = ByteBuffer.wrap(buf, offset, length);
        channel.write(tempBuffer);
        this.offset += length;
      } else {
        // we fill/flush the buffer (until the input is written)
        int len = length;
        int off = offset;
        while (len > 0) {
          final int n = (len < room ? len : room);
          buffer.put(buf, off, n);
          off += n;
          len -= n;
          room = buffer.remaining();
          if (room == 0) {
            flush();
            room = buffer.remaining();
          }
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long length() throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    return descriptor.length();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long position() throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    return offset + buffer.position();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void seek(final long newPos) throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    final long oldPos = offset + buffer.position();
    if (newPos != oldPos) {
      flush();
      channel.position(newPos);
      offset = newPos;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flush() throws IOException {
    if (descriptor == null) {
      throw new AlreadyClosedException();
    }
    final int pos = buffer.position();
    if (pos > 0) {
      buffer.flip();
      channel.write(buffer);
      buffer.clear();
      offset += pos;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {
    if (descriptor != null) {
      // Close the channel & file
      try {
        channel.close();
      } finally {
        try {
          descriptor.close();
        } finally {
          buffer = null;
          channel = null;
          descriptor = null;
        }
      }
    }
  }
}