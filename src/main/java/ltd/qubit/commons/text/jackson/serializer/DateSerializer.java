////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.util.Date;

import ltd.qubit.commons.util.codec.Encoder;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * The JSON serializer of a {@link Date} object.
 *
 * @author Haixing Hu
 */
public class DateSerializer extends EncoderSerializer<Date> {

  private static final long serialVersionUID = -6691972410224992988L;

  public DateSerializer(final Encoder<Date, String> encoder) {
    super(Date.class, encoder, JsonGenerator::writeString);
  }
}
