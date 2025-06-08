////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JACKSON key deserializer of a {@link LocalDate} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class LocalDateKeyDeserializer extends DecoderKeyDeserializer<LocalDate> {

  private static final long serialVersionUID = 5679718569124837426L;

  public LocalDateKeyDeserializer(final Decoder<String, LocalDate> decoder) {
    super(LocalDate.class, decoder);
  }
}