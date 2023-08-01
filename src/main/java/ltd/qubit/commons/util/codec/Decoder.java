////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
