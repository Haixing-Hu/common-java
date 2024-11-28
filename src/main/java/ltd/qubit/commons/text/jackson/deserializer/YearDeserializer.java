////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.Year;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.YearCodec;

/**
 * The JACKSON deserializer of a {@link Year} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class YearDeserializer extends DecoderDeserializer<Year> {

  private static final long serialVersionUID = 7444615730588838387L;

  public static final YearDeserializer INSTANCE = new YearDeserializer();

  public YearDeserializer() {
    super(Year.class, YearCodec.INSTANCE);
  }

  public YearDeserializer(final Decoder<String, Year> decoder) {
    super(Year.class, decoder);
  }
}
