////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JACKSON deserializer of a {@link OffsetTime} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class OffsetTimeDeserializer extends DecoderDeserializer<OffsetTime> {

  private static final long serialVersionUID = -8505259894994451472L;

  public OffsetTimeDeserializer(final Decoder<String, OffsetTime> decoder) {
    super(OffsetTime.class, decoder);
  }
}