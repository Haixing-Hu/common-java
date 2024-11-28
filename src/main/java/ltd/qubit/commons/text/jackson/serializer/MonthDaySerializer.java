////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.MonthDay;
import java.time.Year;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.MonthDayCodec;

/**
 * The JACKSON serializer of a {@link Year} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class MonthDaySerializer extends EncoderSerializer<MonthDay> {

  private static final long serialVersionUID = 537655584599361987L;

  public static final MonthDaySerializer INSTANCE = new MonthDaySerializer();

  public MonthDaySerializer() {
    this(MonthDayCodec.INSTANCE);
  }

  public MonthDaySerializer(final Encoder<MonthDay, String> encoder) {
    super(MonthDay.class, encoder, JsonGenerator::writeString);
  }
}
