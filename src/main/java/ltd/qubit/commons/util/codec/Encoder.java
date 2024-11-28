////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * Provides the interface to encode an object to another.
 *
 * @author Haixing Hu
 */
public interface Encoder<FROM, TO> {

  /**
   * Encodes an object to another.
   *
   * @param source
   *          the source object to be encoded.
   * @return the encoding result.
   * @throws EncodingException
   *           if any encoding error occurred.
   */
  TO encode(FROM source) throws EncodingException;

  /**
   * Encodes an object to another, and returns {@code null} if any encoding
   * error occurred.
   *
   * @param source
   *     the source object to be encoded.
   * @return
   *     the encoding result, or {@code null} if any encoding error occurred.
   */
  default TO encodeNoThrow(final FROM source) {
    try {
      return encode(source);
    } catch (final EncodingException e) {
      return null;
    }
  }
}
