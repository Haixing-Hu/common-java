////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JACKSON deserializer of an enumeration class.
 *
 * @author Haixing Hu
 */
public class EnumDeserializer<T extends Enum<T>> extends DecoderDeserializer<T> {

  private static final long serialVersionUID = 8423949645823757703L;

  public EnumDeserializer(final Class<T> cls, final Decoder<String, T> decoder) {
    super(cls, decoder);
  }
}