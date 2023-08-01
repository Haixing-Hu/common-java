////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.Instant;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JSON deserializer of a {@link Instant} object.
 *
 * @author Haixing Hu
 */
public class InstantDeserializer extends DecoderDeserializer<Instant> {

  private static final long serialVersionUID = -2730539001574693758L;

  public InstantDeserializer(final Decoder<String, Instant> decoder) {
    super(Instant.class, decoder);
  }
}
