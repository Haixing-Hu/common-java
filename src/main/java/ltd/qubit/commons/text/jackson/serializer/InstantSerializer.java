////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.Encoder;

import com.fasterxml.jackson.core.JsonGenerator;

import java.time.Instant;
import javax.annotation.concurrent.Immutable;

/**
 * The JSON serializer of a {@link Instant} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class InstantSerializer extends EncoderSerializer<Instant> {

  private static final long serialVersionUID = -7990656902566434345L;

  public InstantSerializer(final Encoder<Instant, String> encoder) {
    super(Instant.class, encoder, JsonGenerator::writeString);
  }
}
