////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JACKSON key deserializer of a {@link OffsetTime} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class OffsetTimeKeyDeserializer extends DecoderKeyDeserializer<OffsetTime> {

  private static final long serialVersionUID = -3294986922172750656L;

  public OffsetTimeKeyDeserializer(final Decoder<String, OffsetTime> decoder) {
    super(OffsetTime.class, decoder);
  }
}