////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.Period;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.PeriodCodec;

/**
 * The JSON deserializer of a {@link Period} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class PeriodDeserializer extends DecoderDeserializer<Period> {

  private static final long serialVersionUID = -3238345675489526453L;

  public static final PeriodDeserializer INSTANCE = new PeriodDeserializer();

  public PeriodDeserializer() {
    super(Period.class, PeriodCodec.INSTANCE);
  }

  public PeriodDeserializer(final Decoder<String, Period> decoder) {
    super(Period.class, decoder);
  }
}
