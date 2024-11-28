////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;

/**
 * The JACKSON serializer of a {@link LocalTime} object.
 *
 * @author Haixing Hu
 */
public class LocalTimeSerializer extends EncoderSerializer<LocalTime> {

  private static final long serialVersionUID = -8533742385136266762L;

  public LocalTimeSerializer(final Encoder<LocalTime, String> encoder) {
    super(LocalTime.class, encoder, JsonGenerator::writeString);
  }
}
