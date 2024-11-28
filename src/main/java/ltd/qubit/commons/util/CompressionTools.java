////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.StringUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Simple utility class providing static methods to compress and decompress
 * binary data. This class uses {@link Deflater} and {@link Inflater} classes to
 * compress and decompress.
 *
 * @author Haixing Hu
 */
public final class CompressionTools {

  public static final int BUFFER_SIZE = 4096;

  /**
   * Compresses the specified byte range using the specified compressionLevel
   * (constants are defined in {@link Deflater} class).
   *
   * @param data
   *          the data to be compressed.
   * @param offset
   *          the offset of the byte array where to start compression.
   * @param n
   *          the number of bytes to be compressed.
   * @param compressionLevel
   *          the level of compression. It should be a constant defined in
   *          {@link Deflater} class.
   * @return the compressed byte array.
   */
  public static byte[] compress(final byte[] data, final int offset,
      final int n, final int compressionLevel) {
    if (n == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    // Create an expandable byte array to hold the compressed data. You cannot
    // use an array that's the same size as the original because there is no
    // guarantee that the compressed data will be smaller than the uncompressed
    // data.
    final ByteArrayOutputStream bos = new ByteArrayOutputStream(n);
    final Deflater compressor = new Deflater();
    try {
      compressor.setLevel(compressionLevel);
      compressor.setInput(data, offset, n);
      compressor.finish();
      // Compress the data
      final byte[] buffer = new byte[BUFFER_SIZE];
      while (! compressor.finished()) {
        final int count = compressor.deflate(buffer);
        bos.write(buffer, 0, count);
      }
    } finally {
      compressor.end();
    }
    return bos.toByteArray();
  }

  public static byte[] compressString(final String str, final int compressionLevel) {
    if (str.length() == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else {
      final byte[] bytes = str.getBytes(UTF_8);
      return compress(bytes, 0, bytes.length, compressionLevel);
    }
  }

  /**
   * Decompress the byte array previously returned by compress.
   *
   * @param data
   *     the data to be decompressed.
   * @param offset
   *     the offset of the byte array where to start decompression.
   * @param n
   *     the number of bytes to be decompressed.
   * @return the decompressed result of the original values.
   * @throws DataFormatException
   *     if the compressed data has errors.
   */
  public static byte[] decompress(final byte[] data, final int offset, final int n)
      throws DataFormatException {
    if (n == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    final ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
    final Inflater decompressor = new Inflater();
    try {
      decompressor.setInput(data, offset, n);
      // Decompress the data
      final byte[] buffer = new byte[BUFFER_SIZE];
      while (! decompressor.finished()) {
        final int count = decompressor.inflate(buffer);
        bos.write(buffer, 0, count);
      }
    } finally {
      decompressor.end();
    }
    return bos.toByteArray();
  }

  public static String decompressString(final byte[] data, final int offset,
      final int n) throws DataFormatException {
    if (n == 0) {
      return StringUtils.EMPTY;
    } else {
      final byte[] bytes = decompress(data, offset, n);
      return new String(bytes, UTF_8);
    }
  }
}
