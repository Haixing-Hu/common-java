////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.YearMonth;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.YearMonthCodec;

/**
 * The JACKSON deserializer of a {@link YearMonth} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class YearMonthDeserializer extends DecoderDeserializer<YearMonth> {

  private static final long serialVersionUID = 7444615730588838387L;

  public static final YearMonthDeserializer INSTANCE =
      new YearMonthDeserializer();

  public YearMonthDeserializer() {
    super(YearMonth.class, YearMonthCodec.INSTANCE);
  }

  public YearMonthDeserializer(final Decoder<String, YearMonth> decoder) {
    super(YearMonth.class, decoder);
  }
}