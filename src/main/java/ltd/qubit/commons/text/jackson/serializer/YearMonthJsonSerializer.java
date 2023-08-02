////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.YearMonth;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.YearMonthCodec;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * The JSON serializer of a {@link YearMonth} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class YearMonthJsonSerializer extends EncoderSerializer<YearMonth> {

  private static final long serialVersionUID = 1291011980095251350L;

  public static final YearMonthJsonSerializer INSTANCE = new YearMonthJsonSerializer();

  public YearMonthJsonSerializer() {
    this(YearMonthCodec.INSTANCE);
  }

  public YearMonthJsonSerializer(final Encoder<YearMonth, String> encoder) {
    super(YearMonth.class, encoder, JsonGenerator::writeString);
  }
}
