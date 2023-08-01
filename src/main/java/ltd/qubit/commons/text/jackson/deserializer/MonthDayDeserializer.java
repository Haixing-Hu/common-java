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
import ltd.qubit.commons.util.codec.MonthDayCodec;

import java.time.MonthDay;
import javax.annotation.concurrent.Immutable;

/**
 * The JSON deserializer of a {@link MonthDay} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class MonthDayDeserializer extends DecoderDeserializer<MonthDay> {

  private static final long serialVersionUID = -3300132072526075258L;

  public static final MonthDayDeserializer INSTANCE =
      new MonthDayDeserializer();

  public MonthDayDeserializer() {
    super(MonthDay.class, MonthDayCodec.INSTANCE);
  }

  public MonthDayDeserializer(final Decoder<String, MonthDay> decoder) {
    super(MonthDay.class, decoder);
  }
}
