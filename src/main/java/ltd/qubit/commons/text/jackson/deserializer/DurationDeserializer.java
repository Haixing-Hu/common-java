////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.Duration;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.DurationCodec;

/**
 * The JSON deserializer of a {@link Duration} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class DurationDeserializer extends DecoderDeserializer<Duration> {

  private static final long serialVersionUID = -3238345675489526453L;

  public static final DurationDeserializer INSTANCE =
      new DurationDeserializer();

  public DurationDeserializer() {
    super(Duration.class, DurationCodec.INSTANCE);
  }

  public DurationDeserializer(final Decoder<String, Duration> decoder) {
    super(Duration.class, decoder);
  }
}
