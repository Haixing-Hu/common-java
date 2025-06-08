////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.YearMonth;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.YearMonthCodec;

/**
 * The JACKSON key deserializer of a {@link YearMonth} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class YearMonthKeyDeserializer extends DecoderKeyDeserializer<YearMonth> {

  private static final long serialVersionUID = -3696402088404922586L;

  public static final YearMonthKeyDeserializer INSTANCE = new YearMonthKeyDeserializer();

  public YearMonthKeyDeserializer() {
    super(YearMonth.class, YearMonthCodec.INSTANCE);
  }
}