////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.OutputStream;

import javax.annotation.concurrent.Immutable;

/**
 * A {@code NullOutputStream} object writes all data to the famous <b>/dev/null</b>.
 *
 * <p>This output stream has no destination (file/socket etc.) and all bytes written to
 * it are ignored and lost.</p>
 *
 * @author Haixing Hu
 */
@Immutable
public final class NullOutputStream extends OutputStream {

  /**
   * The singleton instance of the NullOutputStream.
   */
  public static final NullOutputStream INSTANCE = new NullOutputStream();

  /**
   * The restrictive constructor.
   */
  private NullOutputStream() {
    // empty
  }

  /**
   * Does nothing - output to {@code /dev/null}.
   *
   * @param b
   *          The bytes to write
   * @param off
   *          The start offset
   * @param len
   *          The number of bytes to write
   */
  @Override
  public void write(final byte[] b, final int off, final int len) {
    // to /dev/null
  }

  /**
   * Does nothing - output to {@code /dev/null}.
   *
   * @param b
   *          The byte to write
   */
  @Override
  public void write(final int b) {
    // to /dev/null
  }

  /**
   * Does nothing - output to {@code /dev/null}.
   *
   * @param b
   *          The bytes to write
   */
  @Override
  public void write(final byte[] b) {
    // to /dev/null
  }
}
