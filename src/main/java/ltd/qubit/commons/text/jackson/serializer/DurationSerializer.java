////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.Duration;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.DurationCodec;
import ltd.qubit.commons.util.codec.Encoder;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * The JSON serializer of a {@link Duration} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class DurationSerializer extends EncoderSerializer<Duration> {

  private static final long serialVersionUID = -8354331200251458534L;

  public static final DurationSerializer INSTANCE = new DurationSerializer();

  public DurationSerializer() {
    this(DurationCodec.INSTANCE);
  }

  public DurationSerializer(final Encoder<Duration, String> encoder) {
    super(Duration.class, encoder, JsonGenerator::writeString);
  }
}
