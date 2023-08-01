////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.Encoder;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * The JSON serializer of a enumeration class.
 *
 * @author Haixing Hu
 */
public class EnumSerializer<T extends Enum<T>> extends EncoderSerializer<T> {

  private static final long serialVersionUID = -6894346294188257598L;

  public EnumSerializer(final Class<T> cls, final Encoder<T, String> encoder) {
    super(cls, encoder, JsonGenerator::writeString);
  }
}
