////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JACKSON key deserializer of a {@link LocalDateTime} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class LocalDateTimeKeyDeserializer extends DecoderKeyDeserializer<LocalDateTime> {

  private static final long serialVersionUID = -912874654699073643L;

  public LocalDateTimeKeyDeserializer(final Decoder<String, LocalDateTime> decoder) {
    super(LocalDateTime.class, decoder);
  }
}
