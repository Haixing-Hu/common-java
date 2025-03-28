////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2024 - 2025.
//    Nanjing Xinglin Digital Technology Co., Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

import ltd.qubit.commons.io.IoUtils;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

/**
 * Provides utility methods for encoding and decoding data in Base64 format.
 *
 * @author Haixing Hu
 */
public class Base64Utils {

  /**
   * Encodes bytes read from an input stream into a Base64 string.
   * <p>
   * <b>NOTE:</b> This function does not close the input stream.
   *
   * @param input
   *     the input stream to read from.
   * @return
   *     the encoded Base64 string.
   * @throws IOException
   *     if an I/O error occurs.
   */
  public static String encodeToString(final InputStream input) throws IOException {
    final Base64.Encoder encoder = Base64.getEncoder();
    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    IoUtils.copy(input, encoder.wrap(output));
    return output.toString(ISO_8859_1);
  }

  /**
   * Encodes bytes read from an input stream into a Base64 byte array.
   * <p>
   * <b>NOTE:</b> This function does not close the input stream.
   *
   * @param input
   *     the input stream to read from.
   * @return
   *     the encoded Base64 byte array.
   * @throws IOException
   *     if an I/O error occurs.
   */
  public static byte[] encodeToBytes(final InputStream input) throws IOException {
    final Base64.Encoder encoder = Base64.getEncoder();
    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    IoUtils.copy(input, encoder.wrap(output));
    return output.toByteArray();
  }

  /**
   * Encodes bytes into a Base64 string.
   * <p>
   * <b>NOTE:</b> This function does not close the input stream.
   *
   * @param input
   *     the input data.
   * @return
   *     the encoded Base64 string.
   * @throws IOException
   *     if an I/O error occurs.
   */
  public static String encodeToString(final byte[] input) throws IOException {
    final Base64.Encoder encoder = Base64.getEncoder();
    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    IoUtils.copy(input, encoder.wrap(output));
    return output.toString(ISO_8859_1);
  }

  /**
   * Encodes bytes into a Base64 byte array.
   * <p>
   * <b>NOTE:</b> This function does not close the input stream.
   *
   * @param input
   *     the input data.
   * @return
   *     the encoded Base64 byte array.
   * @throws IOException
   *     if an I/O error occurs.
   */
  public static byte[] encodeToBytes(final byte[] input) throws IOException {
    final Base64.Encoder encoder = Base64.getEncoder();
    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    IoUtils.copy(input, encoder.wrap(output));
    return output.toByteArray();
  }

  /**
   * Decodes a Base64 string into bytes and writes them to an output stream.
   *
   * @param source
   *     the Base64 string to decode.
   * @param output
   *     the output stream to write the decoded bytes to.
   * @return
   *     the number of bytes written to the output stream.
   * @throws IOException
   *     if an I/O error occurs.
   */
  public static long decodeToOutput(final String source, final OutputStream output)
      throws IOException {
    final Base64.Decoder decoder = Base64.getDecoder();
    final ByteArrayInputStream input = new ByteArrayInputStream(source.getBytes(ISO_8859_1));
    return IoUtils.copy(decoder.wrap(input), output);
  }

  /**
   * Decodes a Base64 bytes array into bytes and writes them to an output stream.
   *
   * @param source
   *     the Base64 bytes array to decode.
   * @param output
   *     the output stream to write the decoded bytes to.
   * @return
   *     the number of bytes written to the output stream.
   * @throws IOException
   *     if an I/O error occurs.
   */
  public static long decodeToOutput(final byte[] source, final OutputStream output)
      throws IOException {
    final Base64.Decoder decoder = Base64.getDecoder();
    final ByteArrayInputStream input = new ByteArrayInputStream(source);
    return IoUtils.copy(decoder.wrap(input), output);
  }
}