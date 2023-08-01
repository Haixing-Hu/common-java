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

import java.util.Date;
import javax.annotation.concurrent.Immutable;

/**
 * The JSON deserializer of a {@link Date} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class DateDeserializer extends DecoderDeserializer<Date> {

  private static final long serialVersionUID = 6425216688398047328L;

  public DateDeserializer(final Decoder<String, Date> decoder) {
    super(Date.class, decoder);
  }
}
