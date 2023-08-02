////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JSON deserializer of a {@link LocalTime} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class LocalTimeDeserializer extends DecoderDeserializer<LocalTime> {

  private static final long serialVersionUID = -8851269510603079378L;

  public LocalTimeDeserializer(final Decoder<String, LocalTime> decoder) {
    super(LocalTime.class, decoder);
  }
}
