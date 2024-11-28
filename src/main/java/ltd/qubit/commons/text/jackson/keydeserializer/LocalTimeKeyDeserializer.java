////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JACKSON key deserializer of a {@link LocalTime} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class LocalTimeKeyDeserializer extends DecoderKeyDeserializer<LocalTime> {

  private static final long serialVersionUID = -450559835040646903L;

  public LocalTimeKeyDeserializer(final Decoder<String, LocalTime> decoder) {
    super(LocalTime.class, decoder);
  }
}
