////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * The JACKSON serializer of a {@link Date} object.
 *
 * @author Haixing Hu
 */
public class DateSerializer extends EncoderSerializer<Date> {

  private static final long serialVersionUID = -6691972410224992988L;

  public DateSerializer(final Encoder<Date, String> encoder) {
    super(Date.class, encoder, JsonGenerator::writeString);
  }
}