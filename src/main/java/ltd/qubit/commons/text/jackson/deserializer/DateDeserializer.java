////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JACKSON deserializer of a {@link Date} object.
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
