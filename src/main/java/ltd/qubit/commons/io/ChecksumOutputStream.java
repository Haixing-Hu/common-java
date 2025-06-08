////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static ltd.qubit.commons.io.OutputUtils.writeLong;

/**
 * 通过主要的 {@link OutputStream} 写入字节，在写入过程中计算校验和。
 * 注意，您不能使用 seek()。
 *
 * @author 胡海星
 */
public class ChecksumOutputStream extends FilterOutputStream {

  private final ChecksumAlgorithm algorithm;
  private final Checksum digest;

  /**
   * 构造一个表示校验和输出流的异常。
   *
   * @param out
   *     输出流。
   * @param algorithm
   *     校验和算法。
   */
  public ChecksumOutputStream(final OutputStream out,
      final ChecksumAlgorithm algorithm) {
    super(out);
    this.algorithm = algorithm;
    switch (algorithm) {
      case ADLER32:
        digest = new Adler32();
        break;
      case CRC32:
        digest = new CRC32();
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
  public void write(final int ch) throws IOException {
    out.write(ch);
    digest.update(ch);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(final byte[] buffer, final int off, final int len)
      throws IOException {
    out.write(buffer, off, len);
    digest.update(buffer, off, len);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flush() throws IOException {
    out.flush();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {
    out.close();
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
   * 签名。
   *
   * @throws IOException
   *     如果签名失败。
   */
  public void sign() throws IOException {
    final long signature = digest.getValue();
    writeLong(out, signature);
  }
}