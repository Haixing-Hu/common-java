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

import java.time.LocalTime;

/**
 * The JSON serializer of a {@link LocalTime} object.
 *
 * @author Haixing Hu
 */
public class LocalTimeSerializer extends EncoderSerializer<LocalTime> {

  private static final long serialVersionUID = -8533742385136266762L;

  public LocalTimeSerializer(final Encoder<LocalTime, String> encoder) {
    super(LocalTime.class, encoder, JsonGenerator::writeString);
  }
}
