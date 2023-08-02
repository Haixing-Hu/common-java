////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * Provides the interface to decode an object to another.
 *
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

}
