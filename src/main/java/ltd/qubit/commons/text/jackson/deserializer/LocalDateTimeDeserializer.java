////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.Decoder;

import java.time.LocalDateTime;
import javax.annotation.concurrent.Immutable;

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
