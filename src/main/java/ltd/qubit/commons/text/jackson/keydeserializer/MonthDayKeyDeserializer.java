////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.MonthDay;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.MonthDayCodec;

/**
 * The JACKSON key deserializer of a {@link MonthDay} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class MonthDayKeyDeserializer extends DecoderKeyDeserializer<MonthDay> {

  private static final long serialVersionUID = -1096409578194019573L;

  public static final MonthDayKeyDeserializer INSTANCE = new MonthDayKeyDeserializer();

  public MonthDayKeyDeserializer() {
    super(MonthDay.class, MonthDayCodec.INSTANCE);
  }
}
