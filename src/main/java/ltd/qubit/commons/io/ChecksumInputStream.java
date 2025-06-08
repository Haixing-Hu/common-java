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
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * 通过主要的 {@link InputStream} 读取字节，在读取过程中计算校验和。
 * 注意，您不能使用 seek()。
 *
 * @author 胡海星
 */
public final class ChecksumInputStream extends FilterInputStream {

  private final ChecksumAlgorithm algorithm;
  private final Checksum digest;

  /**
   * 构造一个表示校验和输入流的异常。
   *
   * @param in
   *     输入流。
   * @param algorithm
   *     校验和算法。
   */
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

  /**
   * 获取校验和算法。
   *
   * @return
   *     校验和算法。
   */
  public ChecksumAlgorithm algorithm() {
    return algorithm;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int available() throws IOException {
    return in.available();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException {
    final int ch = in.read();
    if (ch >= 0) {
      digest.update(ch);
    }
    return ch;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(final byte[] buffer, final int offset, final int len)
      throws IOException {
    final int n = in.read(buffer, offset, len);
    if (n >= 0) {
      digest.update(buffer, offset, n);
    }
    return n;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long skip(final long n) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean markSupported() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mark(final int readLimit) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reset() {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {
    in.close();
  }

  /**
   * 获取校验和。
   *
   * @return
   *     校验和。
   */
  public long getChecksum() {
    return digest.getValue();
  }

  /**
   * 验证校验和。
   *
   * @return
   *     如果校验和匹配，则返回 true；否则返回 false。
   * @throws IOException
   *     如果读取校验和失败。
   */
  public boolean verify() throws IOException {
    final long expectedChecksum = InputUtils.readLong(in);
    return (expectedChecksum == digest.getValue());
  }

  /**
   * 验证校验和。
   *
   * @param expectedChecksum
   *     期望的校验和。
   * @return
   *     如果校验和匹配，则返回 true；否则返回 false。
   */
  public boolean verify(final long expectedChecksum) {
    return (expectedChecksum == digest.getValue());
  }
}