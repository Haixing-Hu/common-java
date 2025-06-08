////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JACKSON key deserializer of a {@link Instant} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class InstantKeyDeserializer extends DecoderKeyDeserializer<Instant> {

  private static final long serialVersionUID = 564308276221268369L;

  public InstantKeyDeserializer(final Decoder<String, Instant> decoder) {
    super(Instant.class, decoder);
  }
}