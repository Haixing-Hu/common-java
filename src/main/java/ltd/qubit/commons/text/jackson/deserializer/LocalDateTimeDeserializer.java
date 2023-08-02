////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JSON deserializer of a {@link LocalDateTime} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class LocalDateTimeDeserializer extends DecoderDeserializer<LocalDateTime> {

  private static final long serialVersionUID = 566573999418927054L;

  public LocalDateTimeDeserializer(final Decoder<String, LocalDateTime> decoder) {
    super(LocalDateTime.class, decoder);
  }
}
