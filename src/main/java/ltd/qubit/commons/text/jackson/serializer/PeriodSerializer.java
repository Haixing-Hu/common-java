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
import ltd.qubit.commons.util.codec.PeriodCodec;

import com.fasterxml.jackson.core.JsonGenerator;

import java.time.Period;
import javax.annotation.concurrent.Immutable;

/**
 * The JSON serializer of a {@link Period} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class PeriodSerializer extends EncoderSerializer<Period> {

  private static final long serialVersionUID = -8354331200251458534L;

  public static final PeriodSerializer INSTANCE = new PeriodSerializer();

  public PeriodSerializer() {
    this(PeriodCodec.INSTANCE);
  }

  public PeriodSerializer(final Encoder<Period, String> encoder) {
    super(Period.class, encoder, JsonGenerator::writeString);
  }
}
