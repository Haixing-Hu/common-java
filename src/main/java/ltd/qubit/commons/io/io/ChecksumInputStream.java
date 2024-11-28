////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Read bytes through to a primary {@link InputStream}, computing checksum as it
 * goes. Note that you cannot use seek().
 *
 * @author Haixing Hu
 */
public final class ChecksumInputStream extends FilterInputStream {

  private final ChecksumAlgorithm algorithm;
  private final Checksum digest;

  public ChecksumInputStream(final InputStream in,
      final ChecksumAlgorithm algorithm) {
    super(in);
    this.algorithm = algorithm;
    switch (algorithm) {
      case ADLER32:
        this.digest = new Adler32();
        break;
      case CRC32:
        this.digest = new CRC32();
        break;
      default:
        throw new IllegalArgumentException("Unsupported checksum algorithm: "
            + algorithm.name());
    }
  }

  public ChecksumAlgorithm algorithm() {
    return algorithm;
  }

  @Override
  public int available() throws IOException {
    return in.available();
  }

  @Override
  public int read() throws IOException {
    final int ch = in.read();
    if (ch >= 0) {
      digest.update(ch);
    }
    return ch;
  }

  @Override
  public int read(final byte[] buffer, final int offset, final int len)
      throws IOException {
    final int n = in.read(buffer, offset, len);
    if (n >= 0) {
      digest.update(buffer, offset, n);
    }
    return n;
  }

  @Override
  public long skip(final long n) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean markSupported() {
    return false;
  }

  @Override
  public void mark(final int readLimit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void reset() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void close() throws IOException {
    in.close();
  }

  public long getChecksum() {
    return digest.getValue();
  }

  public boolean verify() throws IOException {
    final long expectedChecksum = InputUtils.readLong(in);
    return (expectedChecksum == digest.getValue());
  }

  public boolean verify(final long expectedChecksum) {
    return (expectedChecksum == digest.getValue());
  }
}
