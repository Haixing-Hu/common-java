////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * Provides the interface to encode a source object to another target object.
 *
 * @param <FROM>
 *     the type of the source object to be encoded.
 * @param <TO>
 *     the type of the target object to be encoded to.
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

  /**
   * Encodes an object to another, and throws a {@link RuntimeException} if any
   * encoding error occurred.
   *
   * @param source
   *     the source object to be encoded.
   * @return
   *     the encoding result.
   * @throws RuntimeException
   *     if any encoding error occurred.
   */
  default TO encodeThrowRuntime(final FROM source) {
    try {
      return encode(source);
    } catch (final EncodingException e) {
      throw new RuntimeException(e);
    }
  }
}