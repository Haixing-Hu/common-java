////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * The JACKSON serializer of a enumeration class.
 *
 * @author Haixing Hu
 */
public class EnumSerializer<T extends Enum<T>> extends EncoderSerializer<T> {

  private static final long serialVersionUID = -6894346294188257598L;

  public EnumSerializer(final Class<T> cls, final Encoder<T, String> encoder) {
    super(cls, encoder, JsonGenerator::writeString);
  }
}