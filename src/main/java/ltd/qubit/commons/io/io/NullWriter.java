////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io;

import java.io.Writer;

import javax.annotation.concurrent.Immutable;

/**
 * This {@link Writer} writes all data to the famous <b>/dev/null</b>.
 *
 * <p>This {@code Writer} has no destination (file/socket etc.) and all
 * characters written to it are ignored and lost.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NullWriter extends Writer {

  /**
   * The singleton instance of the NullWriter.
   */
  public static final NullWriter INSTANCE = new NullWriter();

  /**
   * The restrictive constructor.
   */
  private NullWriter() {
    // empty
  }

  /**
   * Does nothing - output to {@code /dev/null}.
   *
   * @param idx
   *          The character to write
   */
  @Override
  public void write(final int idx) {
    // to /dev/null
  }

  /**
   * Does nothing - output to {@code /dev/null}.
   *
   * @param chr
   *          The characters to write
   */
  @Override
  public void write(final char[] chr) {
    // to /dev/null
  }

  /**
   * Does nothing - output to {@code /dev/null}.
   *
   * @param chr
   *          The characters to write
   * @param st
   *          The start offset
   * @param end
   *          The number of characters to write
   */
  @Override
  public void write(final char[] chr, final int st, final int end) {
    // to /dev/null
  }

  /**
   * Does nothing - output to {@code /dev/null}.
   *
   * @param str
   *          The string to write
   */
  @Override
  public void write(final String str) {
    // to /dev/null
  }

  /**
   * Does nothing - output to {@code /dev/null}.
   *
   * @param str
   *          The string to write
   * @param st
   *          The start offset
   * @param end
   *          The number of characters to write
   */
  @Override
  public void write(final String str, final int st, final int end) {
    // to /dev/null
  }

  /**
   *  @see Writer#flush()
   */
  @Override
  public void flush() {
    // to /dev/null
  }

  /**
   * @see Writer#close()
   */
  @Override
  public void close() {
    // to /dev/null
  }

}
