////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import javax.annotation.Nullable;

/**
 * Provides the interface to decode a source object to another target object.
 *
 * @param <FROM>
 *     the type of the source object to be decoded.
 * @param <TO>
 *     the type of the target object to be decoded to.
 * @author Haixing Hu
 */
public interface Decoder<FROM, TO> {

  /**
   * Decodes an object to another.
   *
   * @param source
   *          the source object to be decoded.
   * @return the decoding result.
   * @throws DecodingException
   *           if any decoding error occurred.
   */
  TO decode(FROM source) throws DecodingException;

  /**
   * Decodes an object to another, and returns {@code null} if any decoding error
   * occurred.
   *
   * @param source
   *     the source object to be decoded.
   * @return
   *     the decoding result, or {@code null} if any decoding error occurred.
   */
  @Nullable
  default TO decodeNoThrow(final FROM source) {
    try {
      return decode(source);
    } catch (final DecodingException e) {
      return null;
    }
  }

  /**
   * Decodes an object to another, and throws a {@link RuntimeException} if any
   * decoding error occurred.
   *
   * @param source
   *     the source object to be decoded.
   * @return
   *     the decoding result.
   * @throws RuntimeException
   *     if any decoding error occurred.
   */
  @Nullable
  default TO decodeThrowRuntime(final FROM source) {
    try {
      return decode(source);
    } catch (final DecodingException e) {
      throw new RuntimeException(e);
    }
  }
}