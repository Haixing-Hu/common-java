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

}
