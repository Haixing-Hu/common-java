////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalDate;

import ltd.qubit.commons.util.codec.Encoder;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * The JSON serializer of a {@link LocalDate} object.
 *
 * @author Haixing Hu
 */
public class LocalDateSerializer extends EncoderSerializer<LocalDate> {

  private static final long serialVersionUID = -3069712202687578743L;

  public LocalDateSerializer(final Encoder<LocalDate, String> encoder) {
    super(LocalDate.class, encoder, JsonGenerator::writeString);
  }
}
