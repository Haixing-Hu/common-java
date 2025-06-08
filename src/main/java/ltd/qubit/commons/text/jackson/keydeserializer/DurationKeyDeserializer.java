////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.Duration;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.DurationCodec;

/**
 * The JACKSON key deserializer of a {@link Duration} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class DurationKeyDeserializer extends DecoderKeyDeserializer<Duration> {

  private static final long serialVersionUID = -5038177225248543742L;

  public static final DurationKeyDeserializer INSTANCE = new DurationKeyDeserializer();

  public DurationKeyDeserializer() {
    super(Duration.class, DurationCodec.INSTANCE);
  }

  public DurationKeyDeserializer(final Decoder<String, Duration> decoder) {
    super(Duration.class, decoder);
  }
}