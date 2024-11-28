////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.Period;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.PeriodCodec;

/**
 * The JACKSON key deserializer of a {@link Period} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class PeriodKeyDeserializer extends DecoderKeyDeserializer<Period> {

  private static final long serialVersionUID = 284772844258670286L;

  public static final PeriodKeyDeserializer INSTANCE = new PeriodKeyDeserializer();

  public PeriodKeyDeserializer() {
    super(Period.class, PeriodCodec.INSTANCE);
  }

  public PeriodKeyDeserializer(final Decoder<String, Period> decoder) {
    super(Period.class, decoder);
  }
}
