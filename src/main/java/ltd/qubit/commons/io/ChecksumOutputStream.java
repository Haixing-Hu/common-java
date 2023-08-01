////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
 * Writes bytes through to a primary {@link OutputStream}, computing checksum as
 * it goes. Note that you cannot use seek().
 *
 * @author Haixing Hu
 */
public class ChecksumOutputStream extends FilterOutputStream {

  private final ChecksumAlgorithm algorithm;
  private final Checksum digest;

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

  public ChecksumAlgorithm algorithm() {
    return algorithm;
  }

  @Override
  public void write(final int ch) throws IOException {
    out.write(ch);
    digest.update(ch);
  }

  @Override
  public void write(final byte[] buffer, final int off, final int len)
      throws IOException {
    out.write(buffer, off, len);
    digest.update(buffer, off, len);
  }

  @Override
  public void flush() throws IOException {
    out.flush();
  }

  @Override
  public void close() throws IOException {
    out.close();
  }

  public long getChecksum() {
    return digest.getValue();
  }

  public void sign() throws IOException {
    final long signature = digest.getValue();
    writeLong(out, signature);
  }
}
