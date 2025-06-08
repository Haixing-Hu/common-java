////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.Duration;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.DurationCodec;
import ltd.qubit.commons.util.codec.Encoder;

/**
 * The JACKSON serializer of a {@link Duration} object.
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