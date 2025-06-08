////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * The JACKSON serializer of a {@link LocalDate} object.
 *
 * @author Haixing Hu
 */
public class LocalDateSerializer extends EncoderSerializer<LocalDate> {

  private static final long serialVersionUID = -3069712202687578743L;

  public LocalDateSerializer(final Encoder<LocalDate, String> encoder) {
    super(LocalDate.class, encoder, JsonGenerator::writeString);
  }
}