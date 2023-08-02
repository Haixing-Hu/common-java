////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalDateTime;

import ltd.qubit.commons.util.codec.Encoder;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * The JSON serializer of a {@link LocalDateTime} object.
 *
 * @author Haixing Hu
 */
public class LocalDateTimeSerializer extends EncoderSerializer<LocalDateTime> {

  private static final long serialVersionUID = -3899350055750189523L;

  public LocalDateTimeSerializer(final Encoder<LocalDateTime, String> encoder) {
    super(LocalDateTime.class, encoder, JsonGenerator::writeString);
  }
}
