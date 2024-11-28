////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.Serial;
import java.time.Period;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.PeriodCodec;

/**
 * The JACKSON serializer of a {@link Period} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class PeriodSerializer extends EncoderSerializer<Period> {

  @Serial
  private static final long serialVersionUID = -8354331200251458534L;

  public static final PeriodSerializer INSTANCE = new PeriodSerializer();

  public PeriodSerializer() {
    this(PeriodCodec.INSTANCE);
  }

  public PeriodSerializer(final Encoder<Period, String> encoder) {
    super(Period.class, encoder, JsonGenerator::writeString);
  }
}
