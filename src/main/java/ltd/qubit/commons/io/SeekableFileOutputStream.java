////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.io.error.AlreadyClosedException;


/**
 * A {@link SeekableOutputStream} which writes data into a file.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class SeekableFileOutputStream extends SeekableOutputStream {

  private RandomAccessFile out;

  public SeekableFileOutputStream(final File file) throws IOException {
    out = new RandomAccessFile(file, "w");
  }

  public SeekableFileOutputStream(final File file, final boolean append) throws IOException {
    out = new RandomAccessFile(file, "w");
    if (append) {
      out.seek(out.length());
    }
  }

  @Override
  public void write(final int data) throws IOException {
    if (out == null) {
      throw new AlreadyClosedException();
    }
    out.write(data);
  }

  @Override
  public void write(final byte[] data, final int off, final int len) throws IOException {
    if (out == null) {
      throw new AlreadyClosedException();
    }
    out.write(data, off, len);
  }

  @Override
  public void flush() throws IOException {
    if (out == null) {
      throw new AlreadyClosedException();
    }
    out.getFD().sync();
  }

  @Override
  public void close() throws IOException {
    if (out != null) {
      out.close();
      out = null;
    }
  }

  @Override
  public long length() throws IOException {
    return out.length();
  }

  @Override
  public long position() throws IOException {
    return out.getFilePointer();
  }

  @Override
  public void seek(final long pos) throws IOException {
    out.seek(pos);
  }
}
