////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.PeriodCodec;

import java.time.Period;
import javax.annotation.concurrent.Immutable;

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
