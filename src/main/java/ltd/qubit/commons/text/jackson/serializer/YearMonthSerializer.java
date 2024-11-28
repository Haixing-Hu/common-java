////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.YearMonth;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.YearMonthCodec;

/**
 * The JACKSON serializer of a {@link YearMonth} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class YearMonthSerializer extends EncoderSerializer<YearMonth> {

  private static final long serialVersionUID = 1291011980095251350L;

  public static final YearMonthSerializer INSTANCE = new YearMonthSerializer();

  public YearMonthSerializer() {
    this(YearMonthCodec.INSTANCE);
  }

  public YearMonthSerializer(final Encoder<YearMonth, String> encoder) {
    super(YearMonth.class, encoder, JsonGenerator::writeString);
  }
}
